package com.dega.payconiq.infrastructure.schedulers;

import android.support.annotation.NonNull;

import rx.Scheduler;
import rx.schedulers.Schedulers;

/**
 * Created by davedega on 11/04/18.
 */

public class ImmediateSchedulerProvider implements BaseSchedulerProvider {

    @NonNull
    @Override
    public Scheduler computation() {
        return Schedulers.immediate();
    }

    @NonNull
    @Override
    public Scheduler ui() {
        return Schedulers.immediate();
    }
}
