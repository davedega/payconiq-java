package com.dega.payconiq;

import com.dega.payconiq.infrastructure.DaggerPayconiqComponent;
import com.dega.payconiq.infrastructure.PayconiqComponent;
import com.dega.payconiq.infrastructure.PayconiqModule;

/**
 * Created by davedega on 11/04/18.
 */

public class Application extends android.app.Application {
    static PayconiqComponent component;

    static PayconiqComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerPayconiqComponent.builder().payconiqModule(new PayconiqModule(this)).build();
    }

}
