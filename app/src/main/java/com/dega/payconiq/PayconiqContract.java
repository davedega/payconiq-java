package com.dega.payconiq;

import com.dega.payconiq.model.RealmRepo;

import io.realm.RealmResults;

/**
 * Created by davedega on 11/04/18.
 */

public interface PayconiqContract {

    interface Presenter {

        void loadRepos();

        void onDestroy();

    }

    interface View {

        void setPresenter(Presenter presenter);

        void showRepos(RealmResults<RealmRepo> repositories, boolean fromCache);

        void showLastUpdateTime();

        void showEmptyList();

        void showErrorMessage(int string);

        void showLoading();

        void hideLoading();

        void onDestroy();

        void showMessage(int string);

        void showOfflineMode();

        void hideOfflineMode();
    }
}
