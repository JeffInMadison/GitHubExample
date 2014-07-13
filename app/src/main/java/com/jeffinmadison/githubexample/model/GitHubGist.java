package com.jeffinmadison.githubexample.model;

import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Jeff on 7/8/2014.
 * Copyright JeffInMadison.com 2014
 */
public class GitHubGist {

    @SerializedName("url")
    private String mUrl;

    @SerializedName("id")
    private String mId;

    @SerializedName("description")
    private String mDescription;

    @SerializedName("owner")
    private GitHubOwner mOwner;

    private Map<String, GitHubGistFile> files = new HashMap<String, GitHubGistFile>();

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(final String url) {
        mUrl = url;
    }

    public String getId() {
        return mId;
    }

    public void setId(final String id) {
        mId = id;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(final String description) {
        mDescription = description;
    }

    public GitHubOwner getOwner() {
        return mOwner;
    }

    public void setOwner(final GitHubOwner owner) {
        mOwner = owner;
    }

    public Map<String, GitHubGistFile> getFiles() {
        return files;
    }

    public void setFiles(final Map<String, GitHubGistFile> files) {
        this.files = files;
    }
}
