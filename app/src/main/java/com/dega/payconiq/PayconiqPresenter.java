package com.dega.payconiq;

import android.util.Log;

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

    PayconiqContract.View view;

    public PayconiqPresenter(PayconiqContract.View view) {
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
        Observable<List<Repository>> ibashiResponse = apiService.loadRepositories();

        ibashiResponse
                .subscribeOn(schedulerProvider.computation())
                .observeOn(schedulerProvider.ui())
                .subscribe(new Observer<List<Repository>>() {
                    @Override
                    public void onCompleted() {
                        view.showLastUpdateTime();
                    }

                    @Override
                    public void onError(Throwable e) {
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
                        if (repositoriesResponse != null && repositoriesResponse.size() > 0)
                            view.showRepos(repositoriesResponse);
                        else {
                            view.showEmptyList();
                        }
                    }
                });
    }
}
