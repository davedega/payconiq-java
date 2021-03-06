package com.dega.payconiq;

import com.dega.payconiq.infrastructure.DaggerPayconiqComponent;
import com.dega.payconiq.infrastructure.PayconiqComponent;
import com.dega.payconiq.infrastructure.PayconiqModule;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by davedega on 11/04/18.
 */

public class Application extends android.app.Application {

    static PayconiqComponent component;

    public static PayconiqComponent getComponent() {
        return component;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        component = DaggerPayconiqComponent.builder().payconiqModule(new PayconiqModule(this)).build();
        Realm.init(this);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder()
                .initialData(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                    }
                })
                .build();
        Realm.setDefaultConfiguration(realmConfig);
    }
}
