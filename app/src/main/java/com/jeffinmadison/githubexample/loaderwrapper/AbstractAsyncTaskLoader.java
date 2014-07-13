package com.jeffinmadison.githubexample.loaderwrapper;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

/**
 * Created by Jeff on 11/11/13.
 * Copyright JeffInMadison.com 2014
 */
public abstract class AbstractAsyncTaskLoader<WrappedData> extends AsyncTaskLoader<WrappedLoaderResult<WrappedData>> {
    private static final String TAG = AbstractAsyncTaskLoader.class.getSimpleName();

    private WrappedLoaderResult<WrappedData> mResult;
    private Exception mException;

    /**
     * Do the actual loading of data. This is called from within onLoadResult().
     * @return data
     * @throws Exception exceptions will be handled in base class.
     */
    public abstract WrappedData load() throws Exception;

    public AbstractAsyncTaskLoader(Context context) {
        super(context);
    }

    @Override
    public WrappedLoaderResult<WrappedData> loadInBackground() {
        WrappedLoaderResult<WrappedData> newResult = new WrappedLoaderResult<WrappedData>();
        // This method is called on a background thread and should generate a
        // new set of data to be delivered back to the client.
        try {
            // load the data and add it to result wrapper
            WrappedData wrappedData = load();
            newResult.setWrappedData(wrappedData);

            // set exception added via method
            if (mException != null) {
                newResult.setException(mException);
            }
        } catch (Exception e) {
            // add the exception to the result wrapper
            Log.e(TAG, "Query failed", e);
            newResult.setException(e);
        }

        return newResult;
    }

    @Override
    public void deliverResult(WrappedLoaderResult<WrappedData> data) {
        // Hold a reference to the old data so it doesn't get garbage collected.
        // The old data may still be in use (i.e. bound to an adapter, etc.), so
        // we must protect it until the new data has been delivered.
        WrappedLoaderResult<WrappedData> oldData = mResult;
        mResult = data;

        if (isStarted()) {
            // If the Loader is in a started state, deliver the results to the
            // client. The superclass method does this for us.
            super.deliverResult(data);
        }

        // Invalidate the old data as we don't need it any more.
        if (oldData != null && oldData != data) {
            onReleaseResources(oldData);
        }
    }

    @Override
    protected void onStartLoading() {
        if (mResult != null) {
            deliverResult(mResult);
        }
        else {
            forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        // The Loader is in a stopped state, so we should attempt to cancel the
        // current load (if there is one).
        cancelLoad();
    }

    @Override
    protected void onReset() {
        // Ensure the loader has been stopped.
        onStopLoading();

        // At this point we can release the resources associated with 'mData'.
        if (mResult != null) {
            releaseResources(mResult);
            mResult = null;
        }
    }

    @Override
    public void onCanceled(WrappedLoaderResult<WrappedData> result) {
        // Attempt to cancel the current asynchronous load.
        super.onCanceled(result);

        // The load has been canceled, so we should release the resources
        // associated with 'data'.
        releaseResources(result);
    }

    /**
     * Sets exception in loader result, use in complex loaders
     * that can't use default caching on error.
     * @param exception Exception that occurred
     */
    protected void setException(Exception exception) {
        this.mException = exception;
    }

    /**
     * Override me if any data cleanup is necessary.
     * @param data WrappedData to release
     */
    @SuppressWarnings("UnusedParameters")
    public void onReleaseResources(WrappedLoaderResult<WrappedData> data) {
        // by default do nothing
    }


    @SuppressWarnings("UnusedParameters")
    private void releaseResources(WrappedLoaderResult<WrappedData> data) {
        // For a simple List, there is nothing to do. For something like a Cursor, we
        // would close it in this method. All resources associated with the Loader
        // should be released here.
    }
}