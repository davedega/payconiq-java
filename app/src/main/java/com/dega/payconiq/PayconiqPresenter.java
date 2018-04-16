package com.dega.payconiq;

import com.dega.payconiq.api.ApiService;
import com.dega.payconiq.infrastructure.schedulers.BaseSchedulerProvider;
import com.dega.payconiq.model.DataHelper;
import com.dega.payconiq.model.RealmRepo;
import com.dega.payconiq.model.Repository;

import java.net.UnknownHostException;
import java.util.List;

import javax.inject.Inject;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.adapter.rxjava.HttpException;
import rx.Observable;
import rx.Observer;

/**
 * Created by davedega on 11/04/18.
 */

public class PayconiqPresenter implements PayconiqContract.Presenter {

    // Replacement for AndroidSchedulers since it is not available on JUnit test environment
    @Inject
    BaseSchedulerProvider schedulerProvider;
    @Inject
    ApiService apiService;
    @Inject
    DataHelper dataHelper;

    private PayconiqContract.View view;

    private long currentPage = 0;

    private boolean endOfResults = false;
    private RealmResults<RealmRepo> realmRepos;
    private boolean cacheAvailable = false;


    PayconiqPresenter(PayconiqContract.View view) {
        this.view = view;
        Application.getComponent().inject(this);
        realmRepos = dataHelper.getAllRepos();
        currentPage = dataHelper.countRepos() / 15 == 0 ? 1 : dataHelper.countRepos() / 15;
        cacheAvailable = dataHelper.countRepos() > 0;
    }


    PayconiqPresenter(ApiService apiService, BaseSchedulerProvider mSchedulerProvider,
                      PayconiqContract.View mView, DataHelper datahelper) {
        this.apiService = apiService;
        this.schedulerProvider = mSchedulerProvider;
        this.view = mView;
        realmRepos = dataHelper.getAllRepos();
        currentPage = dataHelper.countRepos() / 15 == 0 ? 1 : dataHelper.countRepos() / 15;
        cacheAvailable = dataHelper.countRepos() > 0;    }

    @Override
    public void loadRepos() {
        if (!endOfResults) {
            if (!cacheAvailable) {
                Observable<List<Repository>> ibashiResponse = apiService.loadRepositories(currentPage, 15);
                if (currentPage > 1) {
                    view.showLoading();
                }
                ibashiResponse
                        .subscribeOn(schedulerProvider.computation())
                        .observeOn(schedulerProvider.ui())
                        .subscribe(new Observer<List<Repository>>() {
                            @Override
                            public void onCompleted() {
                                currentPage = ++currentPage;
                                view.hideLoading();
                                view.hideOfflineMode();
                                if (currentPage == 1) {
                                    view.showLastUpdateTime();
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                currentPage = currentPage++;
                                view.hideLoading();
                                System.out.println("Presenter onError(): " + e.getMessage());
                                if (e instanceof UnknownHostException) {
                                    if (dataHelper.countRepos() > 0) {
                                        view.showMessage(R.string.no_internet_connection);
                                        view.showOfflineMode();
                                    } else {
                                        view.showErrorMessage(R.string.no_internet_connection);
                                    }
                                } else if (e instanceof HttpException) {
                                    view.showErrorMessage(R.string.not_found);
                                } else {
                                    view.showErrorMessage(R.string.expection_message);
                                }
                            }

                            @Override
                            public void onNext(List<Repository> repositoriesResponse) {
                                view.hideLoading();
                                // if is the first request
                                if (currentPage == 1) {
                                    if (repositoriesResponse != null && repositoriesResponse.size() > 0) {
                                        dataHelper.saveRepositories(repositoriesResponse);
                                        view.showRepos(realmRepos, false);
                                    } else {
                                        view.showEmptyList();
                                    }
                                } else {
                                    if (repositoriesResponse != null && repositoriesResponse.size() > 0) {
                                        if (!cacheAvailable) {
                                            dataHelper.saveRepositories(repositoriesResponse);
                                        } else {
                                            view.showRepos(realmRepos, true);
                                        }
                                    } else {
                                        endOfResults = true;
                                    }
                                }
                            }
                        });
            } else {
                cacheAvailable = false;
                currentPage = ++currentPage;
                view.showRepos(realmRepos, true);
            }
        }
    }

    @Override
    public void onDestroy() {
        dataHelper.close();
        view.onDestroy();
    }
}
