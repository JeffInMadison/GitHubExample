package com.jeffinmadison.githubexample.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Jeff on 7/9/2014.
 * Copyright JeffInMadison.com 2014
 */
public class GitHubEvent {

    @SerializedName("id")
    private String mId;

    @SerializedName("type")
    // TODO make an enum for this...
    private String mType;

    @SerializedName("created_at")
    private Date mDate;

    public String getId() {
        return mId;
    }

    public void setId(final String id) {
        mId = id;
    }

    public String getType() {
        return mType;
    }

    public void setType(final String type) {
        mType = type;
    }

    public Date getDate() {
        return mDate;
    }

    public void setDate(final Date date) {
        mDate = date;
    }
}
