package com.dega.payconiq.infrastructure;

import android.content.Context;

import com.dega.payconiq.api.ApiService;
import com.dega.payconiq.infrastructure.schedulers.BaseSchedulerProvider;
import com.dega.payconiq.infrastructure.schedulers.SchedulerProvider;
import com.dega.payconiq.model.Constants;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by davedega on 11/04/18.
 */
@Module
public class PayconiqModule {

    private Context context;

    public PayconiqModule(Context context) {
        this.context = context;
    }

    @Provides
    @Singleton
    Context provideContext() {
        return context;
    }

    @Singleton
    @Provides
    ApiService provideRemoteDataSource() {
        return new ApiService(new Retrofit.Builder()
                .baseUrl(Constants.API_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build());
    }

    @Provides
    @Singleton
    BaseSchedulerProvider provideSchedulerProvider() {
        return new SchedulerProvider();
    }
}