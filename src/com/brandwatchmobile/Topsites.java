package com.brandwatchmobile;

import java.util.List;

import com.brandwatchmobile.adapter.ListAdapter;
import com.brandwatchmobile.adapter.item.ListItem;

import android.app.Activity;
import android.widget.ListView;


public class Topsites
{
	public static ListView getTopSitesView(Activity activity, List<ListItem> items)
	{
		ListView view = new ListView(activity);
		ListAdapter adapter = new ListAdapter(activity, R.id.chart, items);
		view.setAdapter(adapter);
		
		return view;
	}
}
