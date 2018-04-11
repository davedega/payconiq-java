package com.dega.payconiq.api;

import com.dega.payconiq.model.Repository;

import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;

/**
 * Created by davedega on 11/04/18.
 */

public interface ReposApi {
    @GET("/users/JakeWharton/repos?page=1")
    Observable<Repository> loadRepositories();
}
