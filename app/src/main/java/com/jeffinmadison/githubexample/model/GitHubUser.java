package com.jeffinmadison.githubexample.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jeff on 7/13/2014.
 * Copyright JeffInMadison.com 2014
 */
public class GitHubUser {

    @SerializedName("login")
    private String mLogin;

    @SerializedName("id")
    private String mId;

    @SerializedName("avatar_url")
    private String mAvatarUrl;

    @SerializedName("url")
    private String mUrl;

    @SerializedName("html_url")
    private String mHtmlUrl;

    @SerializedName("name")
    private String mName;

    @SerializedName("company")
    private String mCompany;

    public String getLogin() {
        return mLogin;
    }

    public void setLogin(final String login) {
        mLogin = login;
    }

    public String getId() {
        return mId;
    }

    public void setId(final String id) {
        mId = id;
    }

    public String getAvatarUrl() {
        return mAvatarUrl;
    }

    public void setAvatarUrl(final String avatarUrl) {
        mAvatarUrl = avatarUrl;
    }

    public String getUrl() {
        return mUrl;
    }

    public void setUrl(final String url) {
        mUrl = url;
    }

    public String getHtmlUrl() {
        return mHtmlUrl;
    }

    public void setHtmlUrl(final String htmlUrl) {
        mHtmlUrl = htmlUrl;
    }

    public String getName() {
        return mName;
    }

    public void setName(final String name) {
        mName = name;
    }

    public String getCompany() {
        return mCompany;
    }

    public void setCompany(final String company) {
        mCompany = company;
    }
}
