package com.jeffinmadison.githubexample.ui.repository;

import android.os.Bundle;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jeffinmadison.githubexample.R;
import com.jeffinmadison.githubexample.datarequester.GitHubRestRequester;
import com.jeffinmadison.githubexample.loaderwrapper.AbstractAsyncTaskLoader;
import com.jeffinmadison.githubexample.loaderwrapper.WrappedLoaderCallbacks;
import com.jeffinmadison.githubexample.loaderwrapper.WrappedLoaderResult;
import com.jeffinmadison.githubexample.model.GitHubRepository;
import com.jeffinmadison.githubexample.model.GitHubUser;
import com.jeffinmadison.githubexample.ui.fragment.SwipeRefreshListFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeff on 7/8/2014.
 * Copyright JeffInMadison.com 2014
 */
public class GitHubRepositoryListFragment extends SwipeRefreshListFragment implements SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = GitHubRepositoryListFragment.class.getSimpleName();

    private static final String ARG_USERNAME = "ARG_USERNAME";
    private static final int ID_LOADER_REPO = 101;
    private static final int ID_LOADER_USER = 102;

    GitHubUser mGitHubUser = null;
    List<GitHubRepository> mGitHubRepositoryList;
    GitHubRepositoryLoaderWrapper mGitHubRepositoryLoaderWrapper = new GitHubRepositoryLoaderWrapper();
    GitHubUserLoaderWrapper mGitHubUserLoaderWrapper = new GitHubUserLoaderWrapper();
    GitHubRepositoryArrayAdapter mArrayAdapter;

    private String mUsername;
    private View mHeaderView;
    private ImageView mUserImageView;
    private TextView mRepoOwnerTextView;
    private TextView mUserNameTextView;
    private TextView mCompanyTextView;

    public GitHubRepositoryListFragment() {
    }

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
        mHeaderView = View.inflate(getActivity(), R.layout.listview_header_repository, null);
        mRepoOwnerTextView = (TextView) mHeaderView.findViewById(R.id.repoOwnerTextView);
        mUserNameTextView = (TextView) mHeaderView.findViewById(R.id.usernameTextView);
        mCompanyTextView = (TextView) mHeaderView.findViewById(R.id.companyTextView);
        mUserImageView = (ImageView) mHeaderView.findViewById(R.id.gravatarImageView);
        mRepoOwnerTextView.setText(mUsername);
        setOnRefreshListener(this);
        return fragmentView;
    }

    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().addHeaderView(mHeaderView);
        setListAdapter(mArrayAdapter);

        setEmptyText(String.format("No repositories to display for GitHub login: %s", mUsername));
        setListShown(false);
        getLoaderManager().initLoader(ID_LOADER_REPO, null, mGitHubRepositoryLoaderWrapper);
        getLoaderManager().initLoader(ID_LOADER_USER, null, mGitHubUserLoaderWrapper);
    }

    @Override
    public void onRefresh() {
        setRefreshing(true);
        getLoaderManager().restartLoader(ID_LOADER_REPO, null, mGitHubRepositoryLoaderWrapper);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // this will all you to re-add the adapter with a header in the onCreateView()
        setListAdapter(null);
    }

    private class GitHubRepositoryLoaderWrapper implements WrappedLoaderCallbacks<List<GitHubRepository>> {

        @Override
        public Loader<WrappedLoaderResult<List<GitHubRepository>>> onCreateLoader(final int id, final Bundle args) {
            return new AbstractAsyncTaskLoader<List<GitHubRepository>>(getActivity()) {
                @Override
                public List<GitHubRepository> load() throws Exception {
                    return GitHubRestRequester.getInstance().getRepositoryList(mUsername);
                }
            };
        }

        @Override
        public void onLoadFinished(final Loader<WrappedLoaderResult<List<GitHubRepository>>> loader, final WrappedLoaderResult<List<GitHubRepository>> data) {
            setListShown(true);
            setRefreshing(false);
            if (!data.hasException()) {
                mGitHubRepositoryList.clear();
                mGitHubRepositoryList.addAll(data.getWrappedData());
                mArrayAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onLoaderReset(final Loader<WrappedLoaderResult<List<GitHubRepository>>> loader) {
        }
    }

    private class GitHubUserLoaderWrapper implements WrappedLoaderCallbacks<GitHubUser> {
        @Override
        public Loader<WrappedLoaderResult<GitHubUser>> onCreateLoader(final int id, final Bundle args) {
            return new AbstractAsyncTaskLoader<GitHubUser>(getActivity()) {
                @Override
                public GitHubUser load() throws Exception {
                    return GitHubRestRequester.getInstance().getUser(mUsername);
                }
            };
        }

        @Override
        public void onLoadFinished(final Loader<WrappedLoaderResult<GitHubUser>> loader, final WrappedLoaderResult<GitHubUser> data) {

            if (!data.hasException()) {
                mGitHubUser = data.getWrappedData();
                if (mUserImageView != null) {
                    Picasso.with(getActivity())
                            .load(mGitHubUser.getAvatarUrl())
                            .skipMemoryCache()
                            .into(mUserImageView);
                }
                if (mUserNameTextView != null) {
                    mUserNameTextView.setText(mGitHubUser.getName());
                }
                if (mCompanyTextView != null) {
                    mCompanyTextView.setText(mGitHubUser.getCompany());
                }
            }
        }

        @Override
        public void onLoaderReset(final Loader<WrappedLoaderResult<GitHubUser>> loader) {

        }
    }
}