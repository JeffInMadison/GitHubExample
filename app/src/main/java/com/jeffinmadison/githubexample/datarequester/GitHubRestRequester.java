package com.jeffinmadison.githubexample.datarequester;

import com.jeffinmadison.githubexample.model.GitHubEvent;
import com.jeffinmadison.githubexample.model.GitHubGist;
import com.jeffinmadison.githubexample.model.GitHubRepository;
import com.jeffinmadison.githubexample.model.GitHubUser;
import com.squareup.okhttp.OkHttpClient;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Jeff on 11/7/13.
 * Copyright Noble Applicaitons 2013
 */
public class GitHubRestRequester {
    private static final String TAG = GitHubRestRequester.class.getSimpleName();

    static final String GITHUB_URL = "https://api.github.com";
    private static GitHubRestRequester mGitHubRestRequester;

    public interface GitHubEndpoints {

        @GET("/users/{user}/repos")
        List<GitHubRepository> listRepos(@Path("user") String user);

        @GET("/users/{user}/repos")
        void listRepos(@Path("user") String user, Callback<List<GitHubRepository>> callback);

        @GET("/users/{user}/gists")
        List<GitHubGist> listGists(@Path("user") String user);

        @GET("/users/{user}/gists")
        void listGists(@Path("user") String user, Callback<List<GitHubGist>> callback);

        @GET("/users/{user}/events")
        List<GitHubEvent> listEvents(@Path("user") String user);

        @GET("/users/{user}/events")
        void listEvents(@Path("user") String user, Callback<List<GitHubEvent>> callback);

        @GET("/users/{user}")
        void getUser(@Path("user") String user, Callback<GitHubUser> callback);

        @GET("/users/{user}")
        GitHubUser getUser(@Path("user") String user);
    }

    private static GitHubEndpoints mRestEndpoint;

    public static GitHubRestRequester getInstance() {
        if (mGitHubRestRequester == null) {
            mGitHubRestRequester = new GitHubRestRequester();
        }

        return mGitHubRestRequester;
    }

    private GitHubRestRequester() {
        OkHttpClient client = new OkHttpClient();
        Executor executor = Executors.newCachedThreadPool();

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(GITHUB_URL)
                .setExecutors(executor,executor)
                .setClient(new OkClient(client))
                .build();

        mRestEndpoint = restAdapter.create(GitHubEndpoints.class);
    }

//    protected static void handleServerError(RetrofitError e) throws InternalServerErrorException, ServerUnreachableException {
//        Response response = e.getResponse();
//        if (response != null && response.getStatus() == 500) {
//            String restResult = (String) e.getBodyAs(String.class);
//            throw new InternalServerErrorException(restResult);
//        } else {
//            Throwable cause = e.getCause();
//            if (cause != null && e.getCause() instanceof SocketTimeoutException) {
//                throw new ServerUnreachableException();
//            } else {
//                throw e;
//            }
//        }
//    }

    public List<GitHubRepository> getRepositoryList(String username) {
        return mRestEndpoint.listRepos(username);
    }

    public void getRepositoryList(String username, Callback<List<GitHubRepository>> callback) {
        mRestEndpoint.listRepos(username, callback);
    }

    public List<GitHubGist> getGistList(String username) {
        return mRestEndpoint.listGists(username);
    }

    public void getGistList(String username, Callback<List<GitHubGist>> callback) {
        mRestEndpoint.listGists(username, callback);
    }

    public List<GitHubEvent> getGistEvents(String username) {
        return mRestEndpoint.listEvents(username);
    }

    public void getGistEvents(String username, Callback<List<GitHubEvent>> callback) {
        mRestEndpoint.listEvents(username, callback);
    }

    public GitHubUser getUser(String username) {
        return mRestEndpoint.getUser(username);
    }

    public void getUser(String username, Callback<GitHubUser> callback) {
        mRestEndpoint.getUser(username, callback);
    }
}
