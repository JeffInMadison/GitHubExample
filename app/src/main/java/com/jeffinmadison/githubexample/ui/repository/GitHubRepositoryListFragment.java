package com.jeffinmadison.githubexample.ui.repository;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jeffinmadison.githubexample.datarequester.GitHubRestRequester;
import com.jeffinmadison.githubexample.loaderwrapper.AbstractAsyncTaskLoader;
import com.jeffinmadison.githubexample.loaderwrapper.WrappedLoaderCallbacks;
import com.jeffinmadison.githubexample.loaderwrapper.WrappedLoaderResult;
import com.jeffinmadison.githubexample.model.GitHubRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeff on 7/8/2014.
 * Copyright JeffInMadison.com 2014
 */
public class GitHubRepositoryListFragment extends ListFragment implements WrappedLoaderCallbacks<List<GitHubRepository>> {
    private static final String TAG = GitHubRepositoryListFragment.class.getSimpleName();

    private static final String ARG_USERNAME = "ARG_USERNAME";
    private static final int LOADER_ID = 101;

    List<GitHubRepository> mGitHubRepositoryList;
    GitHubRepositoryArrayAdapter mArrayAdapter;
    private String mUsername;

    public GitHubRepositoryListFragment() {}

    public static GitHubRepositoryListFragment newInstance(String username) {
        GitHubRepositoryListFragment fragment = new GitHubRepositoryListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUsername = getArguments().getString(ARG_USERNAME);
        }
        mGitHubRepositoryList = new ArrayList<GitHubRepository>();
        mArrayAdapter = new GitHubRepositoryArrayAdapter(getActivity(), mGitHubRepositoryList);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);
        return fragmentView;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setEmptyText("no repositories to display");
        setListAdapter(mArrayAdapter);
        getLoaderManager().initLoader(LOADER_ID, null, this);
    }

    @Override
    public Loader<WrappedLoaderResult<List<GitHubRepository>>> onCreateLoader(final int id, final Bundle args) {
        setListShown(false);
        return new AbstractAsyncTaskLoader<List<GitHubRepository>>(getActivity()) {
            @Override
            public List<GitHubRepository> load() throws Exception {
                return GitHubRestRequester.getInstance().getRepositoryList(getActivity(), mUsername);
            }
        };
    }

    @Override
    public void onLoadFinished(final Loader<WrappedLoaderResult<List<GitHubRepository>>> loader, final WrappedLoaderResult<List<GitHubRepository>> data) {
        setListShown(true);
        if (!data.hasException()) {
            mGitHubRepositoryList.clear();
            mGitHubRepositoryList.addAll(data.getWrappedData());
            mArrayAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(final Loader<WrappedLoaderResult<List<GitHubRepository>>> loader) {
        // release any held references
    }
}
