package com.dega.payconiq;

import com.dega.payconiq.api.ApiService;
import com.dega.payconiq.infrastructure.schedulers.BaseSchedulerProvider;
import com.dega.payconiq.model.Repository;

import java.net.UnknownHostException;
import java.util.List;

import javax.inject.Inject;

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

    private PayconiqContract.View view;

    private int currentPage = 0;
    private boolean endOfResults = false;

    PayconiqPresenter(PayconiqContract.View view) {
        this.view = view;
        Application.getComponent().inject(this);
    }


    PayconiqPresenter(ApiService apiService, BaseSchedulerProvider mSchedulerProvider,
                      PayconiqContract.View mView) {
        this.apiService = apiService;
        this.schedulerProvider = mSchedulerProvider;
        this.view = mView;
    }

    @Override
    public void loadRepos() {
        if (!endOfResults) {
            Observable<List<Repository>> ibashiResponse = apiService.loadRepositories(currentPage++, 15);
            if (currentPage != 1) {
                view.showLoading();
            }
            ibashiResponse
                    .subscribeOn(schedulerProvider.computation())
                    .observeOn(schedulerProvider.ui())
                    .subscribe(new Observer<List<Repository>>() {
                        @Override
                        public void onCompleted() {
                            view.hideLoading();
                            if (currentPage == 1) {
                                view.showLastUpdateTime();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            view.hideLoading();
                            System.out.println("Presenter onError(): " + e.getMessage());
                            if (e instanceof UnknownHostException) {
                                view.showErrorMessage(R.string.no_internet_connection);
                            } else if (e instanceof HttpException) {
                                view.showErrorMessage(R.string.not_found);
                            } else {
                                view.showErrorMessage(R.string.expection_message);
                            }
                        }

                        @Override
                        public void onNext(List<Repository> repositoriesResponse) {
                            view.hideLoading();

                            if (currentPage == 1) {
                                if (repositoriesResponse != null && repositoriesResponse.size() > 0)
                                    view.showRepos(repositoriesResponse);
                                else {
                                    view.showEmptyList();
                                }
                            } else {
                                if (repositoriesResponse != null && repositoriesResponse.size() > 0) {
                                    view.updateList(repositoriesResponse);
                                } else {
                                    endOfResults = true;
                                }
                            }
                        }
                    });
        }
    }
}
