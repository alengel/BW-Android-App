package com.brandwatchmobile.adapter;

import java.util.List;

import com.brandwatchmobile.R;
import com.brandwatchmobile.adapter.item.MentionItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MentionsAdapter extends ArrayAdapter<MentionItem>
{
	private LayoutInflater inflater;

	public MentionsAdapter(Context context, int textViewResourceId, List<MentionItem> objects)
	{
		super(context, textViewResourceId, objects);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{

		MentionItem item = getItem(position);

		ViewHolder holder;
		if (convertView == null)
		{
			// Create new view.
			convertView = inflater.inflate(R.layout.mentions, null);
			holder = new ViewHolder();
			
			// Cache views so that we don't have to call findViewById everytime.
			holder.txtDate = (TextView) convertView.findViewById(R.id.date);
			holder.txtTitle = (TextView) convertView.findViewById(R.id.title);
			holder.txtSnippet = (TextView) convertView.findViewById(R.id.snippet);

			convertView.setTag(holder);
		} 
		else
		{
			// Reuse view if not recycled.
			holder = (ViewHolder) convertView.getTag();
		}

		holder.txtDate.setText(item.getDate());
		holder.txtTitle.setText(item.getTitle());
		holder.txtSnippet.setText(item.getShortSnippetWithoutHTMLTags());

		return convertView;
	}

	static class ViewHolder
	{
		TextView txtDate;
		TextView txtTitle;
		TextView txtSnippet;
	}
}
