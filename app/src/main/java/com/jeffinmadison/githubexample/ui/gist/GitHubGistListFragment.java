package com.jeffinmadison.githubexample.ui.gist;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jeffinmadison.githubexample.datarequester.GitHubRestRequester;
import com.jeffinmadison.githubexample.loaderwrapper.AbstractAsyncTaskLoader;
import com.jeffinmadison.githubexample.loaderwrapper.WrappedLoaderCallbacks;
import com.jeffinmadison.githubexample.loaderwrapper.WrappedLoaderResult;
import com.jeffinmadison.githubexample.model.GitHubGist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeff on 7/8/2014.
 * Copyright JeffInMadison.com 2014
 */
public class GitHubGistListFragment extends ListFragment implements WrappedLoaderCallbacks<List<GitHubGist>> {
    private static final String TAG = GitHubGistListFragment.class.getSimpleName();

    private static final String ARG_USERNAME = "ARG_USERNAME";
    private static final int ID_LOADER_GIST = 102;

    List<GitHubGist> mHubGistList;
    GitHubGistArrayAdapter mArrayAdapter;
    private String mUsername;

    public GitHubGistListFragment() {}

    public static GitHubGistListFragment newInstance(String username) {
        GitHubGistListFragment fragment = new GitHubGistListFragment();
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
        mHubGistList = new ArrayList<GitHubGist>();
        mArrayAdapter = new GitHubGistArrayAdapter(getActivity(), mHubGistList);
        setRetainInstance(true);
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        Log.i(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        setEmptyText("no gists to display");
        setListAdapter(mArrayAdapter);
        getLoaderManager().initLoader(ID_LOADER_GIST, null, this);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void setUserVisibleHint(final boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public Loader<WrappedLoaderResult<List<GitHubGist>>> onCreateLoader(final int id, final Bundle args) {
        setListShown(false);
        return new AbstractAsyncTaskLoader<List<GitHubGist>>(getActivity()) {
            @Override
            public List<GitHubGist> load() throws Exception {
                return GitHubRestRequester.getInstance().getGistList(getActivity(), mUsername);
            }
        };
    }

    @Override
    public void onLoadFinished(final Loader<WrappedLoaderResult<List<GitHubGist>>> loader, final WrappedLoaderResult<List<GitHubGist>> data) {
        setListShown(true);
        if (!data.hasException()) {
            mHubGistList.clear();
            mHubGistList.addAll(data.getWrappedData());
            mArrayAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(final Loader<WrappedLoaderResult<List<GitHubGist>>> loader) {

    }
}
