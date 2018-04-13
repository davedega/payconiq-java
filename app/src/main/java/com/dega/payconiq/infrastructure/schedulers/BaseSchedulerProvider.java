package com.dega.payconiq.infrastructure.schedulers;

import android.support.annotation.NonNull;

import rx.Scheduler;

/**
 * Since we can not use AndroidSchedullers when running a unit test we make
 * use of this interface wich delivers Rx Shedulers
 * <p>
 * Created by davedega on 11/04/18.
 */

public interface BaseSchedulerProvider {

    @NonNull
    Scheduler computation();

    @NonNull
    Scheduler ui();
}
