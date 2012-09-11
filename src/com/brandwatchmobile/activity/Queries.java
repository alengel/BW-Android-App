package com.brandwatchmobile.activity;

import java.util.List;

import com.brandwatchmobile.R;
import com.brandwatchmobile.adapter.ListAdapter;
import com.brandwatchmobile.adapter.item.ListItem;
import com.brandwatchmobile.asynctasks.AsyncTaskProject;
import com.brandwatchmobile.asynctasks.callback.AsyncTaskCallback;
import com.brandwatchmobile.utils.HttpRequestParameters;

import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.AdapterView;
import android.widget.ListView;
import android.view.View;


public class Queries extends SearchableListActivity 
{
	private String projectId = "";
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		// Get bundle data.
		Bundle extras = getIntent().getExtras();
		if (extras != null)
		{
			projectId = (String) extras.get("projectId");
		}

		downloadQueries();
	}


	private void downloadQueries()
	{
		String key = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("key", "");

		// Create callback that will be called by the async task when it is
		// finished downloading and processing query data.
		AsyncTaskCallback dc = new AsyncTaskCallback()
		{
			public void onSuccess(List<ListItem> projects)
			{
				onQueriesLoaded(projects);
			}

			public void onFailure()
			{
				// onLoginError(responseCode);
			}
		};
		
		// Prepare queries request.
		HttpRequestParameters requestParams = new HttpRequestParameters();
		requestParams.url =  "http://bwjsonapi.nodejitsu.com/queries?projectId=" + projectId;
		requestParams.methodName = "GET";
		requestParams.header = key;

		new AsyncTaskProject(dc, this, "Getting queries ...").execute(requestParams);
	}
	
	private void onQueriesLoaded(List<ListItem> queries)
	{
		listItems = queries;
		
		ListView lv = (ListView) findViewById(R.id.listView);
		ListAdapter adapter = new ListAdapter(this, R.layout.listitem, listItems);
		lv.setAdapter(adapter);
	}

	@Override
	protected void onItemSelected(AdapterView<?> parent, View view, int position)
	{
		// Show selected query.
		ListItem item = (ListItem) parent.getAdapter().getItem(position);

		// Set selected query id and name in preferences. This is used in the landing activity to show the last selected query.
		Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
		editor.putString("queryId", item.id);
		editor.putString("queryName", item.name);
		editor.commit();

		Intent myIntent = new Intent(view.getContext(), Overview.class);
		myIntent.putExtra("queryId", item.id);
		startActivity(myIntent);
	}
}
