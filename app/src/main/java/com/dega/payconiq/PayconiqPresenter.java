package com.dega.payconiq;

/**
 * Created by davedega on 11/04/18.
 */

public class PayconiqPresenter implements PayconiqContract.Presenter {

    PayconiqContract.View view;

    public PayconiqPresenter(PayconiqContract.View view) {
        this.view = view;
    }

    @Override
    public void loadRepos() {

    }
}
