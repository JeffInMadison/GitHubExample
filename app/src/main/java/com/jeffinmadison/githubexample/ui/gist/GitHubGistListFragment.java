package com.jeffinmadison.githubexample.ui.gist;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jeffinmadison.githubexample.model.GitHubGist;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeff on 7/8/2014.
 * Copyright JeffInMadison.com 2014
 */
public class GitHubGistListFragment extends ListFragment {
    private static final String TAG = GitHubGistListFragment.class.getSimpleName();

    private static final String ARG_USERNAME = "ARG_USERNAME";

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
        retrieveList();
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

    private void retrieveList() {
        new AsyncTask<Void,Void,Void>() {

            @Override
            protected void onPreExecute() {
                setListShown(false);
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(final Void... params) {
                SystemClock.sleep(3000);
                return null;
            }

            @Override
            protected void onPostExecute(final Void aVoid) {
                setListShown(true);
                super.onPostExecute(aVoid);
            }
        }.execute(null,null,null);
    }
}
