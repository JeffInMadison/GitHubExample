package com.jeffinmadison.githubexample.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.jeffinmadison.githubexample.R;
import com.jeffinmadison.githubexample.ui.customtabview.CustomTabBarView;
import com.jeffinmadison.githubexample.ui.customtabview.CustomViewTabListener;
import com.jeffinmadison.githubexample.ui.event.GitHubEventListFragment;
import com.jeffinmadison.githubexample.ui.gist.GitHubGistListFragment;
import com.jeffinmadison.githubexample.ui.repository.GitHubRepositoryListFragment;
import com.jeffinmadison.githubexample.ui.settings.SettingsActivity;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity implements CustomViewTabListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    SectionsPagerAdapter mSectionsPagerAdapter;
    List<Fragment> mFragmentList;
    ViewPager mViewPager;
    private CustomTabBarView mCustomTabBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up the action bar.
        final ActionBar actionBar = getSupportActionBar();
        initializeTabBarView(actionBar);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        // When swiping between different sections, select the corresponding
        // tab. We can also use ActionBar.Tab#select() to do this if we have
        // a reference to the Tab.
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                mCustomTabBarView.setSelectedTab(position);
            }
        });
    }

    private void initializeTabBarView(final ActionBar actionBar) {
        actionBar.setDisplayOptions(android.app.ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.tab_bar);

        mCustomTabBarView = (CustomTabBarView) actionBar.getCustomView();
        mCustomTabBarView.setOnTabViewSelectedListener(this);
        mCustomTabBarView.setSelectedTab(0);

        mFragmentList = new ArrayList<Fragment>();
        mFragmentList.add(GitHubRepositoryListFragment.newInstance("jeffinmadison"));
        mFragmentList.add(GitHubGistListFragment.newInstance("jeffinmadison"));
        mFragmentList.add(GitHubEventListFragment.newInstance("jeffinmadison"));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onTabSelected(final int position) {
        if (mViewPager.getCurrentItem() != position) {
            mViewPager.setCurrentItem(position);
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            mFragmentList.size();
            // Show 3 total pages.
            return 3;
        }
    }
}
