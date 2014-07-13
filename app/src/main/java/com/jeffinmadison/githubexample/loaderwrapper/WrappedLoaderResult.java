package com.jeffinmadison.githubexample.loaderwrapper;

/**
 * Created by Jeff on 11/11/13.
 * Copyright JeffInMadison 2014
 */
public class WrappedLoaderResult<WrappedData> {
    private Exception mException;
    private WrappedData mWrappedData;

    public WrappedLoaderResult() {
        mWrappedData = null;
        mException = null;
    }

    public boolean hasException() {
        return mException != null;
    }

    public Exception getException() {
        return mException;
    }

    public void setException(Exception exception) {
        mException = exception;
    }

    public WrappedData getWrappedData() {
        return mWrappedData;
    }

    public void setWrappedData(WrappedData wrappedData) {
        mWrappedData = wrappedData;
    }
}