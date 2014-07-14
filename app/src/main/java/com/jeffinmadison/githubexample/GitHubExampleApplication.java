package com.jeffinmadison.githubexample;

import com.squareup.picasso.Picasso;

/**
 * Created by Jeff on 7/12/2014.
 * Copyright JeffInMadison.com 2014
 */
public class GitHubExampleApplication extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Picasso.with(this).setIndicatorsEnabled(true);
        }
    }
}
