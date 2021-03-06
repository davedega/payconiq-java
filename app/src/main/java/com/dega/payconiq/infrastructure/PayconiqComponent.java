package com.dega.payconiq.infrastructure;

import com.dega.payconiq.PayconiqPresenter;
import com.dega.payconiq.model.DataHelper;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by davedega on 11/04/18.
 */
@Singleton
@Component(modules = PayconiqModule.class)
public interface PayconiqComponent {

    void inject(PayconiqPresenter presenter);

    void inject(DataHelper helper);

}
