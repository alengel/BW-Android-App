package com.brandwatchmobile.adapter;

import java.util.List;

import com.brandwatchmobile.R;
import com.brandwatchmobile.adapter.item.LandingItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class LandingItemAdapter extends ArrayAdapter<LandingItem> 
{
	private LayoutInflater inflater;

	public LandingItemAdapter(Context context,
						   int textViewResourceId,
						   List<LandingItem> objects) 
	{
		super(context, textViewResourceId, objects);
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{

		LandingItem item = getItem(position);

		ViewHolder holder;
		if (convertView == null)
		{
			convertView = inflater.inflate(R.layout.landing, null);
			holder = new ViewHolder();
			
			// Cache views in holder so that we don't have to call findViewById
			// everytime.
			holder.textTitle = (TextView) convertView.findViewById(R.id.title);
			holder.textNarrative = (TextView) convertView.findViewById(R.id.narrative);

			convertView.setTag(holder);
		} 
		else
		{
			holder = (ViewHolder) convertView.getTag();
		}

		holder.textTitle.setText(item.name);
		holder.textNarrative.setText(item.narrative);
		
		
		// Hide narrative if empty.
		if(item.narrative.isEmpty())
		{
			holder.textNarrative.setVisibility(View.GONE);
		}
		else
		{
			holder.textNarrative.setVisibility(View.VISIBLE);
		}
		
		convertView.setEnabled(item.clickable);
		convertView.setClickable(!item.clickable);

		return convertView;
	}

	static class ViewHolder
	{
		TextView textTitle;
		TextView textNarrative;
	}
}
