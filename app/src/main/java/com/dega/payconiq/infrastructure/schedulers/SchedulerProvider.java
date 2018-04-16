package com.dega.payconiq.infrastructure.schedulers;

import android.support.annotation.NonNull;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by davedega on 11/04/18.
 */

public class SchedulerProvider implements BaseSchedulerProvider {

    public SchedulerProvider() {
    }

    @Override
    @NonNull
    public Scheduler computation() {
        return Schedulers.computation();
    }

    @Override
    @NonNull
    public Scheduler ui() {
        return AndroidSchedulers.mainThread();
    }
}