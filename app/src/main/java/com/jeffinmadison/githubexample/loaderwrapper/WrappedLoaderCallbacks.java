package com.jeffinmadison.githubexample.loaderwrapper;

import android.support.v4.app.LoaderManager;

/**
 * Created by Jeff on 11/11/13.
 * Copyright JeffInMadison 2014
 */
public interface WrappedLoaderCallbacks<WrappedData>
        extends LoaderManager.LoaderCallbacks<WrappedLoaderResult<WrappedData>> {
}
