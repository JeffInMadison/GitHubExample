package com.jeffinmadison.githubexample.ui.event;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.jeffinmadison.githubexample.R;
import com.jeffinmadison.githubexample.model.GitHubEvent;

import java.util.ArrayList;

/**
 * Created by Jeff on 7/8/2014.
 * Copyright JeffInMadison.com 2014
 */
public class GitHubEventListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = GitHubEventListFragment.class.getSimpleName();

    private static final String ARG_USERNAME = "ARG_USERNAME";
    private static final int PROGRESS_VIEW = 0;
    private static final int LIST_VIEW = 1;

    private static final int LIST_SHOWN = 0;
    private static final int EMPTY_SHOWN = 1;

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
        retrieveList();
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        Log.i(TAG, "onViewCreated");
        super.onViewCreated(view, savedInstanceState);
        mEmptyTextView.setText("no Events to display");
        mListView.setAdapter(mArrayAdapter);
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState) {
        Log.i(TAG, "onCreateView");
        setRetainInstance(true);
        View fragmentView = inflater.inflate(R.layout.list_content, container, false);
        mProgressViewSwitcher = (ViewSwitcher) fragmentView.findViewById(R.id.viewSwitcher);
        mListViewSwitcher = (ViewSwitcher) fragmentView.findViewById(R.id.listContainer);
        mListView = (ListView) fragmentView.findViewById(android.R.id.list);
        mEmptyTextView = (TextView) fragmentView.findViewById(R.id.internalEmpty);
        mSwipeRefreshLayout = (SwipeRefreshLayout) fragmentView.findViewById(R.id.swipeContainer);
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorScheme(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        return fragmentView;
    }

    private void retrieveList() {
        new AsyncTask<Void,Void,Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                if (mProgressViewSwitcher != null) {
                    mProgressViewSwitcher.setDisplayedChild(PROGRESS_VIEW);
                }
            }

            @Override
            protected Void doInBackground(final Void... params) {
                SystemClock.sleep(3000);
                return null;
            }

            @Override
            protected void onPostExecute(final Void aVoid) {
                if (mProgressViewSwitcher != null) {
                    mProgressViewSwitcher.setDisplayedChild(LIST_VIEW);
                }

                if (mListViewSwitcher != null) {
                    if (mGitHubEventArrayList.size() > 0) {
                        mListViewSwitcher.setDisplayedChild(LIST_SHOWN);
                    } else {
                        mListViewSwitcher.setDisplayedChild(EMPTY_SHOWN);
                    }
                }
                mSwipeRefreshLayout.setRefreshing(false);
                super.onPostExecute(aVoid);
            }
        }.execute(null,null,null);
    }

    @Override
    public void onRefresh() {
        mSwipeRefreshLayout.setRefreshing(true);
        retrieveList();
    }
}
