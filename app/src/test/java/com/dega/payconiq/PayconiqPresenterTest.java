package com.dega.payconiq;

import com.dega.payconiq.api.ApiService;
import com.dega.payconiq.infrastructure.schedulers.BaseSchedulerProvider;
import com.dega.payconiq.infrastructure.schedulers.ImmediateSchedulerProvider;
import com.dega.payconiq.model.Repository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import rx.Observable;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 *  * The purpose of class is to test the following behavior:
 * <p>
 * 1. Given an empty list of repositories, show no repositories screen
 * 2. Given a filled list of repositories, show repositories in a list
 * 3. Notify the user if a request failed
 * <p>
 * Created by davedega on 11/04/18.
 */
public class PayconiqPresenterTest {
    @Mock
    private ApiService apiService;
    @Mock
    private PayconiqContract.View mView;

    private InOrder inOrder;

    private PayconiqPresenter presenter;
    private Repository emptyResponse;
    private Repository validResponse;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        inOrder = Mockito.inOrder(mView);
        BaseSchedulerProvider mSchedulerProvider = new ImmediateSchedulerProvider();

        presenter = new PayconiqPresenter(apiService, mSchedulerProvider, mView);

        emptyResponse = new Repository();
        validResponse = new Repository();

    }

    /**
     * 2. Given an empty list of repositories, show no repositories screen
     ***/
    @Test
    public void showEmptyScreen() {
        when(apiService.loadRepositories())
                .thenReturn(rx.Observable.just(emptyResponse));
        presenter.loadRepos();
        inOrder.verify(mView).showEmptyList();
    }

    /**
     * 2. Given a filled list of repositories, show repositories in a list
     ***/
    @Test
    public void showRepositories() {
        when(apiService.loadRepositories())
                .thenReturn(rx.Observable.just(validResponse));

        presenter.loadRepos();

        inOrder.verify(mView).showRepos(new ArrayList<Repository>());
        inOrder.verify(mView).showLastUpdateTime();
    }

    /**
     * 3. Inform the user when there is not internet connection
     ***/
    @Test
    public void informConnectionLost() {
        when(apiService.loadRepositories())
                .thenReturn(Observable.<Repository>error(new UnknownHostException("No internet!")));
        presenter.loadRepos();
        inOrder.verify(mView).showErrorMessage(R.string.no_internet_connection);
    }

}