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

public class Projects extends SearchableListActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		downloadProjects();
	}

	private void downloadProjects()
	{
		String key = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("key", "");

		// Create callback that will be called by the async task when it is
		// finished downloading and processing project data.
		AsyncTaskCallback dc = new AsyncTaskCallback()
		{
			public void onSuccess(List<ListItem> projects)
			{
				onProjectsLoaded(projects);
			}

			public void onFailure()
			{
				// onLoginError(responseCode);
			}
		};
		
		// Prepare projects request.
		HttpRequestParameters requestParams = new HttpRequestParameters();
		requestParams.url = "http://bwjsonapi.nodejitsu.com/projects/";
		requestParams.methodName = "GET";
		requestParams.header = key;

		new AsyncTaskProject(dc, this, "Getting projects ...").execute(requestParams);
	}

	
	private void onProjectsLoaded(List<ListItem> projects)
	{
		listItems = projects;
		
		// Update items to display.
		ListView lv = (ListView) findViewById(R.id.listView);
		ListAdapter adapter = new ListAdapter(this, R.layout.listitem, listItems);
		lv.setAdapter(adapter);
	}

	@Override
	protected void onItemSelected(AdapterView<?> parent, View view, int position)
	{
		// Show selected project.
		ListItem item = (ListItem) parent.getAdapter().getItem(position);

		// Set selected project id and name in preferences. This is used in the landing activity to show the last selected project.
		Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
		editor.putString("projectId", item.id);
		editor.putString("projectName", item.name);
		editor.commit();

		Intent myIntent = new Intent(view.getContext(), Queries.class);
		myIntent.putExtra("projectId", item.id);
		startActivity(myIntent);
	}
}
