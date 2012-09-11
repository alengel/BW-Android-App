package com.brandwatchmobile.adapter;

import java.util.List;

import com.brandwatchmobile.adapter.item.ListItem;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ListAdapter extends ArrayAdapter<ListItem> 
{
	private Context context;
	public ListAdapter(Context context,
						   int textViewResourceId,
						   List<ListItem> objects) 
	{
		super(context, textViewResourceId, objects);
		this.context = context;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) 
	{	
		ListItem item = getItem(position);
		
		TextView view;
		if(convertView != null)
		{
			// Reuse view.
			view = (TextView) convertView;
		}
		else
		{
			// Create a new view if it has been recycled.
			view = new TextView(context);
		}
		
		view.setText(item.name);
		view.setPadding(10, 10, 10, 10);
		
		return view;
	}
}
