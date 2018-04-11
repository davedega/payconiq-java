package com.dega.payconiq;

import com.dega.payconiq.api.ApiService;
import com.dega.payconiq.infrastructure.schedulers.BaseSchedulerProvider;

import javax.inject.Inject;

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
    }


    PayconiqPresenter(ApiService apiService, BaseSchedulerProvider mSchedulerProvider,
                      PayconiqContract.View mView) {
        this.apiService = apiService;
        this.schedulerProvider = mSchedulerProvider;
        this.view = mView;
    }

    @Override
    public void loadRepos() {

    }
}
