package com.dega.payconiq;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class PayconiqActivity extends AppCompatActivity {
    private PayconiqContract.View view;
    private PayconiqContract.Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payconiq);
        view = (PayconicFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);
        presenter = new PayconiqPresenter(view);
        view.setPresenter(presenter);
        presenter.loadRepos();
    }
}
