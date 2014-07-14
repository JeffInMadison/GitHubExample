package com.jeffinmadison.githubexample.ui.gist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jeffinmadison.githubexample.R;
import com.jeffinmadison.githubexample.model.GitHubGist;
import com.jeffinmadison.githubexample.model.GitHubGistFile;

import java.util.List;
import java.util.Map;

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
            viewHolder.fileNameTextView = (TextView) convertView.findViewById(R.id.fileNameTextView);
            viewHolder.descriptionTextView = (TextView) convertView.findViewById(R.id.descriptionTextView);
            viewHolder.documentCountTextView = (TextView) convertView.findViewById(R.id.documentCountTextView);
            viewHolder.commentCountTextView = (TextView) convertView.findViewById(R.id.commentCountTextView);

            convertView.setTag(viewHolder);
        }
        GitHubGist gitHubGist = getItem(position);
        String fileName = "";
        int fileCount = 1;
        final ViewHolder viewHolder = (ViewHolder) convertView.getTag();
        if (!gitHubGist.getFiles().isEmpty()) {
            Map<String, GitHubGistFile> map = gitHubGist.getFiles();
            fileCount = map.size();
            Map.Entry<String, GitHubGistFile> set = map.entrySet().iterator().next();
            fileName = set.getValue().getFilename();
        }
        viewHolder.fileNameTextView.setText(fileName);
        viewHolder.documentCountTextView.setText(String.valueOf(fileCount));
        viewHolder.descriptionTextView.setText(gitHubGist.getDescription());
        viewHolder.commentCountTextView.setText(String.valueOf(gitHubGist.getComments()));
        return convertView;
    }

    private class ViewHolder {
        TextView fileNameTextView;
        TextView descriptionTextView;
        TextView documentCountTextView;
        TextView commentCountTextView;
    }
}
