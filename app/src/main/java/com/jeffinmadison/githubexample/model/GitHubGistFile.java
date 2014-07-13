package com.jeffinmadison.githubexample.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Jeff on 7/8/2014.
 * Copyright JeffInMadison.com 2014
 */
public class GitHubGistFile {

    @SerializedName("filename")
    private String mFilename;

    @SerializedName("type")
    private String mMimeType;

    @SerializedName("raw_url")
    private String mRawUrl;

    @SerializedName("size")
    private int mSize;


    public String getFilename() {
        return mFilename;
    }

    public void setFilename(final String filename) {
        mFilename = filename;
    }

    public String getMimeType() {
        return mMimeType;
    }

    public void setMimeType(final String mimeType) {
        mMimeType = mimeType;
    }

    public String getRawUrl() {
        return mRawUrl;
    }

    public void setRawUrl(final String rawUrl) {
        mRawUrl = rawUrl;
    }

    public int getSize() {
        return mSize;
    }

    public void setSize(final int size) {
        mSize = size;
    }
}
