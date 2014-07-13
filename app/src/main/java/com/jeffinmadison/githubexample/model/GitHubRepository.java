package com.jeffinmadison.githubexample.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jeff on 7/8/2014.
 * Copyright JeffInMadison.com 2014
 */
public class GitHubRepository {

    @SerializedName("id")
    private int mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("full_name")
    private String mFullName;

    @SerializedName("owner")
    private GitHubOwner mGitHubOwner;

    @SerializedName("private")
    private boolean mPrivate;

    @SerializedName("description")
    private String mDescription;

    public int getId() {
        return mId;
    }

    public void setId(final int id) {
        mId = id;
    }

    public String getName() {
        return mName;
    }

    public void setName(final String name) {
        mName = name;
    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(final String fullName) {
        mFullName = fullName;
    }

    public GitHubOwner getGitHubOwner() {
        return mGitHubOwner;
    }

    public void setGitHubOwner(final GitHubOwner gitHubOwner) {
        mGitHubOwner = gitHubOwner;
    }

    public boolean isPrivate() {
        return mPrivate;
    }

    public void setPrivate(final boolean aPrivate) {
        mPrivate = aPrivate;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(final String description) {
        mDescription = description;
    }
}
