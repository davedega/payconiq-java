package com.dega.payconiq;

import android.support.v4.app.Fragment;

import com.dega.payconiq.PayconiqContract.View;
import com.dega.payconiq.model.Repository;

import java.util.List;

/**
 * Created by davedega on 11/04/18.
 */

public class PayconicFragment extends Fragment implements PayconiqContract.View {

    PayconiqContract.View view;

    public PayconicFragment() {

    }

    @Override
    public void setPresenter(PayconiqContract.Presenter presenter) {
        //TODO("not implemented")
    }

    @Override
    public void showRepos(List<Repository> repositories) {
        //TODO("not implemented")

    }

    @Override
    public void showLastUpdateTime() {
        //TODO("not implemented")

    }

    @Override
    public void showEmptyList() {
        //TODO("not implemented")
    }

    @Override
    public void showErrorMessage(int string) {
        //TODO("not implemented")
    }
}
