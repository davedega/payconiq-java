package com.dega.payconiq.api;

import com.dega.payconiq.model.Repository;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by davedega on 11/04/18.
 */

public interface ReposApi {
    @GET("/users/JakeWharton/repos")
    Observable<List<Repository>> loadRepositories(@Query("page") long page, @Query("per_page") int perPage);
}
