package com.jeffinmadison.githubexample.ui.event;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jeffinmadison.githubexample.R;
import com.jeffinmadison.githubexample.model.GitHubEvent;

import java.util.List;

/**
 * Created by Jeff on 7/8/2014.
 * Copyright JeffInMadison.com 2014
 */
public class GitHubEventArrayAdapter extends ArrayAdapter<GitHubEvent> {
    private static final String TAG = GitHubEventArrayAdapter.class.getSimpleName();

    public GitHubEventArrayAdapter(final Context context, final List<GitHubEvent> objects) {
        super(context, -1, objects);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.listview_event, parent, false);
            assert convertView != null;

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.typeTextView = (TextView) convertView.findViewById(R.id.typeTextView);
            viewHolder.dateTextView = (TextView) convertView.findViewById(R.id.dateTextView);

            convertView.setTag(viewHolder);
        }
        GitHubEvent gitHubEvent = getItem(position);
        final ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.typeTextView.setText(gitHubEvent.getType());
        viewHolder.dateTextView.setText(gitHubEvent.getDate().toString());

        return convertView;
    }

    private class ViewHolder {
        TextView typeTextView;
        TextView dateTextView;
    }
}
