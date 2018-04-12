package com.dega.payconiq;

import com.dega.payconiq.model.Repository;

import java.util.List;

/**
 * Created by davedega on 11/04/18.
 */

public interface PayconiqContract {

    interface Presenter {

        void loadRepos();

    }

    interface View {

        void setPresenter(Presenter presenter);

        void showRepos(List<Repository> repositories);

        void updateList(List<Repository> repositories);

        void showLastUpdateTime();

        void showEmptyList();

        void showErrorMessage(int string);

        void showLoading();

        void hideLoading();


    }
}
