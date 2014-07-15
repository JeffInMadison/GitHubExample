GitHubExample
=============

Quick sample app that talks to the GitHub API. 

Some of the things it shows off:

 - Utilizes Android support packages to run on devices down to API 10 (Gingerbread).
 - The ActionBar and ViewPager tabs are combined with a custom view for the ActionBar.
 - Using Square's Retrofit to easily talk to GitHub's API (in the datarequester package).
 - The FragmentList is a nice way to encapulate a listView but doesn't support the SwipeToRefresh Library. Created custom SwipeToRefreshListFragment (Appears to not swipe on emptyList, need to figure out how to enable that yet).
 - Using the LoaderManager and AsyncTaskLoader to retreive data from GitHub and populate lists.
 - Using Square's Picasso to download images into views (Seems to be currupting images. Not sure if it's OkHttp's cache or what but I need to either roll back to previous versions or figure this out).
 

TODO:
 - Add some UI to enable user to change username being loaded.
 - Add mechanism to write through sqllite to persist when no data connection is available.
 - Update Settings page (its stock one you get when using Android Studio to add one).
 - Add UI elements for forks/commits/comments/edits.
 
