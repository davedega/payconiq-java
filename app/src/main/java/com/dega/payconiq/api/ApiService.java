package com.dega.payconiq.api;

import com.dega.payconiq.model.Repository;

import retrofit2.Retrofit;
import rx.Observable;

/**
 * Created by davedega on 11/04/18.
 */

public class ApiService implements ReposApi {
    private final ReposApi api;

    public ApiService(Retrofit retrofit) {
        this.api = retrofit.create(ReposApi.class);
    }

    @Override
    public Observable<Repository> loadRepositories(String token) {
        return null;
    }
}
