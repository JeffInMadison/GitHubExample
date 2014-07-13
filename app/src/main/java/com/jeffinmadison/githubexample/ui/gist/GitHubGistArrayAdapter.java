package com.jeffinmadison.githubexample.ui.gist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jeffinmadison.githubexample.R;
import com.jeffinmadison.githubexample.model.GitHubGist;

import java.util.List;

/**
 * Created by Jeff on 7/8/2014.
 * Copyright JeffInMadison.com 2014
 */
public class GitHubGistArrayAdapter extends ArrayAdapter<GitHubGist> {
    private static final String TAG = GitHubGistArrayAdapter.class.getSimpleName();

    public GitHubGistArrayAdapter(final Context context, final List<GitHubGist> objects) {
        super(context, -1, objects);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.listview_gist, parent, false);
            assert convertView != null;

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.filesTextView = (TextView) convertView.findViewById(R.id.filesTextView);
            viewHolder.descriptionTextView = (TextView) convertView.findViewById(R.id.descriptionTextView);

            convertView.setTag(viewHolder);
        }
        GitHubGist gitHubGist = getItem(position);
        final ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        String filesCount = String.format("File(s): %d", gitHubGist.getFiles().size());
        viewHolder.filesTextView.setText(filesCount);
        viewHolder.descriptionTextView.setText(gitHubGist.getDescription());

        return convertView;
    }

    private class ViewHolder {
        TextView filesTextView;
        TextView descriptionTextView;
    }
}
