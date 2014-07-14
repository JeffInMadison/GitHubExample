package com.jeffinmadison.githubexample.ui.repository;

import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.content.Loader;
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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Jeff on 7/8/2014.
 * Copyright JeffInMadison.com 2014
 */
public class GitHubRepositoryListFragment extends ListFragment implements WrappedLoaderCallbacks<List<GitHubRepository>>, Callback<GitHubUser> {
    private static final String TAG = GitHubRepositoryListFragment.class.getSimpleName();

    private static final String ARG_USERNAME = "ARG_USERNAME";
    private static final int ID_LOADER_REPO = 101;

    GitHubUser mGitHubUser = null;
    List<GitHubRepository> mGitHubRepositoryList;
    GitHubRepositoryArrayAdapter mArrayAdapter;
    private String mUsername;
    private View mHeaderView;
    private ImageView mUserImageView;
    private TextView mRepoOwnerTextView;
    private TextView mUserNameTextView;
    private TextView mCompanyTextView;

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
        // retaining the instance skips the onCreate/onDestroy for config changes and allows you
        // to put stuff in onCreate that you only want to happen once.
        setRetainInstance(true);

        if (getArguments() != null) {
            mUsername = getArguments().getString(ARG_USERNAME);
            // go off and get the User's info
            GitHubRestRequester.getInstance().getUser(getActivity(), mUsername, this);
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
        if (mHeaderView == null) {
            mHeaderView = View.inflate(getActivity(), R.layout.listview_header_repository, null);
        }
        // gather controls from headerView
        mRepoOwnerTextView = (TextView) mHeaderView.findViewById(R.id.repoOwnerTextView);
        mUserNameTextView = (TextView) mHeaderView.findViewById(R.id.usernameTextView);
        mCompanyTextView = (TextView) mHeaderView.findViewById(R.id.companyTextView);
        mUserImageView = (ImageView) mHeaderView.findViewById(R.id.gravatarImageView);
        getListView().addHeaderView(mHeaderView);

        mRepoOwnerTextView.setText(mUsername);
        if (mGitHubUser != null) {
            mUserNameTextView.setText(mGitHubUser.getName());
            mCompanyTextView.setText(mGitHubUser.getCompany());
            Picasso.with(getActivity())
                    .load(mGitHubUser.getAvatarUrl())
                    .into(mUserImageView);
        }

        setListAdapter(mArrayAdapter);

        setEmptyText(String.format("No repositories to display for GitHub login: %s", mUsername));
        getLoaderManager().initLoader(ID_LOADER_REPO, null, this);
    }

    @Override
    public void success(final GitHubUser gitHubUser, final Response response) {
        mGitHubUser = gitHubUser;

        // TODO figure this out: I thought this returned an ran on the main thread...
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mUserImageView != null) {
                    float dimen = getResources().getDimension(R.dimen.repository_header_height);
                    Picasso.with(getActivity())
                            .load(mGitHubUser.getAvatarUrl())
                            .into(mUserImageView);
                }
                if (mUserNameTextView != null) {
                    mUserNameTextView.setText(mGitHubUser.getName());
                }
                if (mCompanyTextView != null) {
                    mCompanyTextView.setText(mGitHubUser.getCompany());
                }
            }
        });
    }

    @Override
    public void failure(final RetrofitError error) {

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
