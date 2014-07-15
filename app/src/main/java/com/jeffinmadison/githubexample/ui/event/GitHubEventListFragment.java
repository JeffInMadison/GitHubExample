package com.jeffinmadison.githubexample.ui.event;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.jeffinmadison.githubexample.datarequester.GitHubRestRequester;
import com.jeffinmadison.githubexample.loaderwrapper.AbstractAsyncTaskLoader;
import com.jeffinmadison.githubexample.loaderwrapper.WrappedLoaderCallbacks;
import com.jeffinmadison.githubexample.loaderwrapper.WrappedLoaderResult;
import com.jeffinmadison.githubexample.model.GitHubEvent;
import com.jeffinmadison.githubexample.ui.fragment.SwipeRefreshListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeff on 7/8/2014.
 * Copyright JeffInMadison.com 2014
 */
public class GitHubEventListFragment extends SwipeRefreshListFragment implements SwipeRefreshLayout.OnRefreshListener, WrappedLoaderCallbacks<List<GitHubEvent>> {
    private static final String TAG = GitHubEventListFragment.class.getSimpleName();

    private static final String ARG_USERNAME = "ARG_USERNAME";
    private static final int ID_LOADER_EVENT = 104;

    ArrayList<GitHubEvent> mGitHubEventArrayList;
    GitHubEventArrayAdapter mArrayAdapter;

    private String mUsername;
    private ViewSwitcher mProgressViewSwitcher;
    private ViewSwitcher mListViewSwitcher;
    private ListView mListView;
    private TextView mEmptyTextView;
    private SwipeRefreshLayout mSwipeRefreshLayout;

    public GitHubEventListFragment() {}

    public static GitHubEventListFragment newInstance(String username) {
        GitHubEventListFragment fragment = new GitHubEventListFragment();
        Bundle args = new Bundle();
        args.putString(ARG_USERNAME, username);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUsername = getArguments().getString(ARG_USERNAME);
        }
        mGitHubEventArrayList = new ArrayList<GitHubEvent>();
        mArrayAdapter = new GitHubEventArrayAdapter(getActivity(), mGitHubEventArrayList);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        View fragmentView = super.onCreateView(inflater, container, savedInstanceState);

        setOnRefreshListener(this);
        return fragmentView;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setEmptyText("no Events to display");
        setListAdapter(mArrayAdapter);
        getLoaderManager().initLoader(ID_LOADER_EVENT, null, this);
    }

    @Override
    public void onRefresh() {
        setRefreshing(true);
        getLoaderManager().restartLoader(ID_LOADER_EVENT, null, this);
    }

    @Override
    public Loader<WrappedLoaderResult<List<GitHubEvent>>> onCreateLoader(final int id, final Bundle args) {
        setListShown(false);
        return new AbstractAsyncTaskLoader<List<GitHubEvent>>(getActivity()) {
            @Override
            public List<GitHubEvent> load() throws Exception {
                return GitHubRestRequester.getInstance().getGistEvents(mUsername);
            }
        };

    }

    @Override
    public void onLoadFinished(final Loader<WrappedLoaderResult<List<GitHubEvent>>> loader, final WrappedLoaderResult<List<GitHubEvent>> data) {
        setListShown(true);
        setRefreshing(false);
        if (!data.hasException()) {
            mGitHubEventArrayList.clear();
            mGitHubEventArrayList.addAll(data.getWrappedData());
            mArrayAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(final Loader<WrappedLoaderResult<List<GitHubEvent>>> loader) { }

}
