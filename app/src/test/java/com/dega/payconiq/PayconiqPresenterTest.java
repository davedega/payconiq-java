package com.dega.payconiq;

import com.dega.payconiq.api.ApiService;
import com.dega.payconiq.infrastructure.schedulers.BaseSchedulerProvider;
import com.dega.payconiq.infrastructure.schedulers.ImmediateSchedulerProvider;
import com.dega.payconiq.model.DataHelper;
import com.dega.payconiq.model.Repository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;

import static org.mockito.Mockito.when;

/**
 * * The purpose of class is to test the following behavior:
 * <p>
 * 1. Given an empty list of repos, show no repos screen
 * 2. Given a valid list of repos, show repos in a list
 * 3. Notify the user if a request failed
 * <p>
 * Created by davedega on 11/04/18.
 */
public class PayconiqPresenterTest {
    @Mock
    private ApiService apiService;
    @Mock
    private PayconiqContract.View mView;

    @Mock
    private DataHelper dataHelper;

    private InOrder inOrder;

    private PayconiqPresenter presenter;
    private List<Repository> emptyResponse;
    private List<Repository> validResponse;


    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        inOrder = Mockito.inOrder(mView);
        BaseSchedulerProvider mSchedulerProvider = new ImmediateSchedulerProvider();

        presenter = new PayconiqPresenter(apiService, mSchedulerProvider, mView, dataHelper);

        emptyResponse = new ArrayList<>();
        validResponse = new ArrayList<>();

        Repository repository1 = new Repository();
        repository1.setName("api with nodejs");

        Repository repository2 = new Repository();
        repository2.setName("api with nodejs");

        Repository repository3 = new Repository();
        repository3.setName("api with nodejs");

        validResponse.add(repository1);
        validResponse.add(repository2);
        validResponse.add(repository3);

    }

    /**
     * 2. Given an empty list of repos, show no repos screen
     ***/
    @Test
    public void showEmptyScreen() {
        when(apiService.loadRepositories(1, 15))
                .thenReturn(rx.Observable.just(emptyResponse));
        presenter.loadRepos();
        inOrder.verify(mView).showEmptyList();
    }

    /**
     * 2. Given a filled list of repos, show repos in a list
     ***/
//    @Test
//    public void showRepositories() {
//        when(apiService.loadRepositories(1,15))
//                .thenReturn(rx.Observable.just(validResponse));
//
//        presenter.loadRepos();
//
//        data
//
//        inOrder.verify(mView).showRepos(validResponse);
//        inOrder.verify(mView).showLastUpdateTime();
//    }

    /**
     * 3. Inform the user when there is not internet connection
     ***/
    @Test
    public void informConnectionLost() {
        when(apiService.loadRepositories(1, 15))
                .thenReturn(Observable.<List<Repository>>error(new UnknownHostException()));
        presenter.loadRepos();
        inOrder.verify(mView).showErrorMessage(R.string.no_internet_connection);
    }

}