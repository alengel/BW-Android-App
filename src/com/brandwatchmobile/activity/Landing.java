package com.brandwatchmobile.activity;

import java.util.Arrays;
import java.util.List;

import com.brandwatchmobile.R;
import com.brandwatchmobile.adapter.LandingItemAdapter;
import com.brandwatchmobile.adapter.item.LandingItem;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.view.View;


public class Landing extends ListActivity 
{
	static  List<LandingItem> CONTENT = Arrays.asList(
			
			new LandingItem("Projects", "", true),
			new LandingItem("Last Project", "", false),
			new LandingItem("Last Query", "", false),
			new LandingItem("Alerts", "", true)
		);
	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
	  super.onCreate(savedInstanceState);
	  	  
	  setListAdapter(new LandingItemAdapter(this, R.layout.landing, CONTENT));
	  
	  // Update list items.
	  updateItems();

	  getListView().setOnItemClickListener(new OnItemClickListener() 
	  {
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
		{
			clicked(view, position);
		}
	  });
	}
	
	@Override
	public void onResume()
	{
		updateItems();
		super.onResume();
	}
	
	private void updateItems()
	{
		// Update last project item.
		String projectName = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("projectName", "");
		LandingItem lastProjectItem = CONTENT.get(1);
		if(projectName.isEmpty() == false)
		{
			// Display last project information.
			lastProjectItem.clickable = true;
			lastProjectItem.narrative = projectName;
		}
		else
		{
			// No previous project, reset item.
			lastProjectItem.clickable = false;
			lastProjectItem.narrative = "";
		}
		
		// Update last query item.
		String queryName = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("queryName", "");
		LandingItem lastQueryItem = CONTENT.get(2);
		if(queryName.isEmpty() == false)
		{
			// Display last query information.
			lastQueryItem.clickable = true;
			lastQueryItem.narrative = queryName;
		}
		else
		{
			// No previous query, reset item.
			lastQueryItem.clickable = false;
			lastQueryItem.narrative = "";
		}
		
		setListAdapter(new LandingItemAdapter(this, R.layout.landing, CONTENT));
	}

	private void clicked(View view, int position)
	{
		switch (position) 
		{
		  case 0: 
			  // Go to projects view.
			  Intent toProjects = new Intent(view.getContext(), Projects.class);
		      startActivity(toProjects);
		      break;
		    
		  case 1: 
			  // Go to last selected project.
			  Intent toLastProject = new Intent(view.getContext(), Queries.class);
			  
			  // Retrieve last project ID an add it to intent.
			  String projectId = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("projectId", "");
			  toLastProject.putExtra("projectId", projectId);
			  
		      startActivity(toLastProject);
		      break;
		    
		  case 2: 
			  // Go to last selected query.
			  Intent toLastQuery = new Intent(view.getContext(), Overview.class);
			  
			// Retrieve last query ID an add it to intent.
			  String queryId = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("queryId", "");
			  toLastQuery.putExtra("queryId", queryId);
			  
		      startActivity(toLastQuery);
		      break;
		    
		  case 3: 
			  // Go to alerts.
			  Intent toAlerts = new Intent(view.getContext(), Alerts.class);
		      startActivity(toAlerts);
		      break;
		}
	}
}
