package com.brandwatchmobile.activity;

import java.util.List;

import com.brandwatchmobile.R;
import com.brandwatchmobile.adapter.ListAdapter;
import com.brandwatchmobile.adapter.item.AlertListItem;
import com.brandwatchmobile.adapter.item.ListItem;
import com.brandwatchmobile.asynctasks.AsyncTaskAlert;
import com.brandwatchmobile.asynctasks.callback.AsyncTaskCallback;
import com.brandwatchmobile.utils.HttpRequestParameters;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.view.View;

public class Alerts extends SearchableListActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		downloadAlerts();
	}

	private void downloadAlerts()
	{
		AsyncTaskCallback dc = new AsyncTaskCallback()
		{
			public void onSuccess(List<ListItem> alerts)
			{
				onAlertsLoaded(alerts);
			}

			public void onFailure()
			{
			}
		};
		
		// Prepare http request.
		String key = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("key", "");
		
		HttpRequestParameters requestParams = new HttpRequestParameters();
		requestParams.url = "http://bwjsonapi.nodejitsu.com/alerts/";
		requestParams.methodName = "GET";
		requestParams.header = key;

		new AsyncTaskAlert(dc, this, "Getting alerts ...").execute(requestParams);
	}

	private void onAlertsLoaded(List<ListItem> alerts)
	{
		listItems = alerts;
		
		ListView lv = (ListView) findViewById(R.id.listView);
		ListAdapter adapter = new ListAdapter(this, R.layout.listitem, listItems);
		lv.setAdapter(adapter);
	}
	
	@Override
	protected void onItemSelected(AdapterView<?> parent, View view, int position)
	{
		AlertListItem item = (AlertListItem) parent.getAdapter().getItem(position);

		Intent myIntent = new Intent(view.getContext(), AlertsMentions.class);
		
		// Pass alert parameters to new activity.
		myIntent.putExtra("alert", item.id);
		myIntent.putExtra("alertSearch", item.searchTerms);
		
		startActivity(myIntent);
	}
}
