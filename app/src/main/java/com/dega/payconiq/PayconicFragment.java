package com.dega.payconiq;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.dega.payconiq.model.RealmRepo;
import com.dega.payconiq.model.Repository;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

/**
 * Created by davedega on 11/04/18.
 */

public class PayconicFragment extends Fragment implements PayconiqContract.View {

    private PayconiqContract.Presenter presenter;
    private RecyclerView reposRV;
    private LinearLayout loadingContainer;
    private ProgressBar progressBar;
    private TextView updateTextView;
    private List<Repository> mRepositories;
    private ProgressBar recyclerProgressBar;
    private ReposRealmAdapter adapterTimeTable;
    private LinearLayout offlineMode;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_payconiq, container, false);
        reposRV = rootView.findViewById(R.id.reposRV);
        loadingContainer = rootView.findViewById(R.id.loadingContainer);
        progressBar = rootView.findViewById(R.id.progressBar);
        updateTextView = rootView.findViewById(R.id.updateTextView);
        recyclerProgressBar = rootView.findViewById(R.id.recyclerPB);
        offlineMode = rootView.findViewById(R.id.offlineMode);
        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        reposRV.setAdapter(null);
    }

    @Override
    public void showMessage(int string) {
        Snackbar mySnackbar = Snackbar.make(loadingContainer,
                getString(string), Snackbar.LENGTH_SHORT);
        mySnackbar.setAction(R.string.try_again, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                updateTextView.setVisibility(View.VISIBLE);
                presenter.loadRepos();
            }
        });

        mySnackbar.show();
    }

    @Override
    public void showOfflineMode() {
        offlineMode.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideOfflineMode() {
        offlineMode.setVisibility(View.GONE);
    }

    @Override
    public void setPresenter(PayconiqContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showRepos(RealmResults<RealmRepo> repositories, boolean fromCache) {
        if (fromCache)
            hideLoading();
        reposRV.setVisibility(View.VISIBLE);
        loadingContainer.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        updateTextView.setVisibility(View.GONE);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        reposRV.setLayoutManager(mLayoutManager);
        adapterTimeTable = new ReposRealmAdapter(repositories, true);
        reposRV.setAdapter(adapterTimeTable);
    }

    @Override
    public void showErrorMessage(int string) {
        updateTextView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        reposRV.setVisibility(View.GONE);
        Snackbar mySnackbar = Snackbar.make(loadingContainer,
                getString(string), Snackbar.LENGTH_SHORT);
        mySnackbar.setAction(R.string.try_again, new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                updateTextView.setVisibility(View.VISIBLE);
                presenter.loadRepos();
            }
        });

        mySnackbar.show();
    }

    @Override
    public void showLoading() {
        recyclerProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        recyclerProgressBar.setVisibility(View.GONE);
    }


    @Override
    public void showLastUpdateTime() {
        Date date = new Date();
        String stringDate = DateFormat.getTimeInstance(DateFormat.SHORT).format(date);
        Snackbar.make(loadingContainer,
                getString(R.string.last_update, stringDate), Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void showEmptyList() {
        //todo work together with UX/UI team to design and implement empty screen
    }

    // The Adapter lives within the view since is the only class who access it
    class ReposRealmAdapter extends RealmRecyclerViewAdapter<RealmRepo, ReposRealmAdapter.ViewHolder> {
        private OrderedRealmCollection<RealmRepo> repos;

        ReposRealmAdapter(@Nullable OrderedRealmCollection<RealmRepo> data, boolean autoUpdate) {
            super(data, autoUpdate);
            this.repos = data;
        }


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View root = LayoutInflater.from(getContext()).
                    inflate(R.layout.item_repository, parent, false);
            return new ViewHolder(root);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.setRepo(repos.get(position));
            if (position == repos.size() - 3) {
                presenter.loadRepos();
            }
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView name;
            final TextView description;
            final TextView watchers;
            final TextView stargazers;
            final TextView forks;

            ViewHolder(View itemView) {
                super(itemView);
                this.name = itemView.findViewById(R.id.nameTV);
                this.description = itemView.findViewById(R.id.descriptionTV);
                this.watchers = itemView.findViewById(R.id.watchersTV);
                this.stargazers = itemView.findViewById(R.id.stargazersTV);
                this.forks = itemView.findViewById(R.id.forksTV);
            }

            void setRepo(RealmRepo repository) {
                this.name.setText(repository.getName());
                this.description.setText(repository.getDescription());
                this.watchers.setText(getString(R.string.string_holer, "" + Utils.roundNumber(Long.valueOf(repository.getWatchersCount()))));
                this.stargazers.setText(getString(R.string.string_holer, "" + Utils.roundNumber(Long.valueOf(repository.getStargazers()))));
                this.forks.setText(getString(R.string.string_holer, "" + Utils.roundNumber(Long.valueOf(repository.getForks()))));
            }
        }
    }

}
