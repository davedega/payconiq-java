package com.dega.payconiq;

import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.dega.payconiq.model.Repository;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by davedega on 11/04/18.
 */

public class PayconicFragment extends Fragment implements PayconiqContract.View {

    private PayconiqContract.Presenter presenter;
    private RecyclerView timeTableRV;
    private LinearLayout loadingContainer;
    private ProgressBar progressBar;
    private TextView updateTextView;
    private List<Repository> mRepositories;
    private ReposAdapter adapterTimeTable;
    private ProgressBar recyclerProgressBar;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_payconiq, container, false);
        timeTableRV = rootView.findViewById(R.id.reposRV);
        loadingContainer = rootView.findViewById(R.id.loadingContainer);
        progressBar = rootView.findViewById(R.id.progressBar);
        updateTextView = rootView.findViewById(R.id.updateTextView);
        recyclerProgressBar = rootView.findViewById(R.id.recyclerPB);
        return rootView;
    }

    @Override
    public void setPresenter(PayconiqContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showRepos(List<Repository> repositories) {
        this.mRepositories = repositories;
        timeTableRV.setVisibility(View.VISIBLE);
        loadingContainer.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        updateTextView.setVisibility(View.GONE);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        timeTableRV.setLayoutManager(mLayoutManager);
        adapterTimeTable = new ReposAdapter();
        timeTableRV.setAdapter(adapterTimeTable);
    }

    @Override
    public void updateList(List<Repository> repositories) {
        this.mRepositories.addAll(repositories);
        adapterTimeTable.notifyDataSetChanged();
    }

    @Override
    public void showErrorMessage(int string) {
        updateTextView.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
        timeTableRV.setVisibility(View.GONE);
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
                getString(R.string.last_update, stringDate), Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void showEmptyList() {
        //todo work together with UX/UI team to design and implement empty screen
    }

    // The Adapter lives within the view since is the only class who access it
    class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ReposViewHolder> {

        @Override
        public ReposViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View root = LayoutInflater.from(getActivity()).
                    inflate(R.layout.item_repository, parent, false);
            return new ReposViewHolder(root);
        }

        @Override
        public void onBindViewHolder(ReposViewHolder holder, int position) {
            holder.setRepo(mRepositories.get(position));

            if (position == mRepositories.size() - 3) {
                presenter.loadRepos();
            }

        }

        @Override
        public int getItemCount() {
            return mRepositories.size();
        }

        @Override
        public int getItemViewType(int position) {
            return position;
        }

        class ReposViewHolder extends RecyclerView.ViewHolder {

            final TextView name;
            final TextView description;
            final TextView watchers;
            final TextView stargazers;
            final TextView forks;

            ReposViewHolder(View itemView) {
                super(itemView);
                this.name = itemView.findViewById(R.id.nameTV);
                this.description = itemView.findViewById(R.id.descriptionTV);
                this.watchers = itemView.findViewById(R.id.watchersTV);
                this.stargazers = itemView.findViewById(R.id.stargazersTV);
                this.forks = itemView.findViewById(R.id.forksTV);
            }

            void setRepo(Repository repository) {
                this.name.setText(repository.getName());
                this.description.setText(repository.getDescription());
                this.watchers.setText(getString(R.string.string_holer, repository.getWatchersCount().toString()));
                this.stargazers.setText(getString(R.string.string_holer, repository.getWatchersCount().toString()));
                this.forks.setText(getString(R.string.string_holer, repository.getWatchersCount().toString()));
            }
        }
    }
}
