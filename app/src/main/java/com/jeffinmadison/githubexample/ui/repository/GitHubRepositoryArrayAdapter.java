package com.jeffinmadison.githubexample.ui.repository;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jeffinmadison.githubexample.R;
import com.jeffinmadison.githubexample.model.GitHubRepository;

import java.util.List;

/**
 * Created by Jeff on 7/8/2014.
 * Copyright JeffInMadison.com 2014
 */
public class GitHubRepositoryArrayAdapter extends ArrayAdapter<GitHubRepository> {
    private static final String TAG = GitHubRepositoryArrayAdapter.class.getSimpleName();

    public GitHubRepositoryArrayAdapter(final Context context, final List<GitHubRepository> objects) {
        super(context, -1, objects);
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(getContext());
            convertView = layoutInflater.inflate(R.layout.listview_repository, parent, false);
            assert convertView != null;

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.nameTextView = (TextView) convertView.findViewById(R.id.nameTextView);
            viewHolder.descriptionTextView = (TextView) convertView.findViewById(R.id.descriptionTextView);

            convertView.setTag(viewHolder);
        }
        GitHubRepository gitHubRepository = getItem(position);
        final ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        viewHolder.nameTextView.setText(gitHubRepository.getName());
        viewHolder.descriptionTextView.setText(gitHubRepository.getDescription());

        return convertView;
    }

    private class ViewHolder {
        TextView nameTextView;
        TextView descriptionTextView;
    }
}
