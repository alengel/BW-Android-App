package com.brandwatchmobile.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.brandwatchmobile.R;
import com.brandwatchmobile.adapter.OverviewAdapter;
import com.brandwatchmobile.adapter.item.MentionItem;
import com.brandwatchmobile.adapter.item.QueryData;
import com.brandwatchmobile.asynctasks.AsyncTaskMention;
import com.brandwatchmobile.asynctasks.AsyncTaskSummary;
import com.brandwatchmobile.asynctasks.callback.MentionsCallback;
import com.brandwatchmobile.asynctasks.callback.SummaryCallback;
import com.brandwatchmobile.utils.DataCache;
import com.brandwatchmobile.utils.HttpRequestParameters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.view.*;

public class Overview extends Activity
{
	Calendar startDate = new GregorianCalendar();
	Calendar endDate = new GregorianCalendar();
	
	String queryId = "";
	
	private final int CALENDAR_ACTIVITY_REQUEST_CODE = 0;
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.overview);
		
		// Set start date to last week.
		startDate.add(Calendar.DAY_OF_YEAR, -7);

		GridView gridview = (GridView) findViewById(R.id.gridview);
		gridview.setAdapter(new OverviewAdapter(this));
		
		// Retrieve query id from intent.
		Bundle extras = getIntent().getExtras();
		if (extras != null)
		{
			queryId = (String) extras.get("queryId");
		}

		// Refresh query data.
		downloadQueryData();

		gridview.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parent, View view, int position, long id)
			{
				// Show selected view.
				Intent myIntent = new Intent(view.getContext(), SummaryView.class);
				myIntent.putExtra("queryId", queryId);
				
				switch (position)
				{
				case 0:
					myIntent.putExtra("selectedView", SummaryView.SummaryViewType.SENTIMENT);
					break;
					
				case 1:
					myIntent.putExtra("selectedView", SummaryView.SummaryViewType.HISTORY);
					break;
					
				case 2:
					myIntent.putExtra("selectedView", SummaryView.SummaryViewType.PAGETYPE);
					break;
					
				case 3:
					myIntent.putExtra("selectedView", SummaryView.SummaryViewType.TOPSITES);
					break;
					
				case 4:
					myIntent.putExtra("selectedView", SummaryView.SummaryViewType.TOPTWEETERS);
					break;
					
				case 5:
					myIntent.putExtra("selectedView", SummaryView.SummaryViewType.MENTIONS);
					break;
				}
				
				startActivityForResult(myIntent, 0);

			}
		});
		
		// Show calendar when the user taps the calendar button.
		Button calendarButton = (Button) findViewById(R.id.calendarButton);
		calendarButton.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View view) 
			{
				Intent myIntent = new Intent(getApplicationContext(), CalendarActivity.class);
				myIntent.putExtra("startDate", startDate);
				myIntent.putExtra("endDate", endDate);
				
				startActivityForResult(myIntent, CALENDAR_ACTIVITY_REQUEST_CODE);
			}
		});
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);

		
		switch (requestCode)
		{
			// We are only interested in results coming from the calendar activity.
			case CALENDAR_ACTIVITY_REQUEST_CODE:
			{
				if (resultCode == Activity.RESULT_OK)
				{
					// Retrieve new end data and start date.
					Bundle extras = data.getExtras();
					if (extras != null)
					{
						startDate = (Calendar) extras.get("startDate");
						endDate = (Calendar) extras.get("endDate");
						
						// Refresh query data.
						downloadQueryData();
					}
				}
	
				break;
			}
		}
	}
	
	private void downloadQueryData()
	{
		String key = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("key", "");
		
		// Format start date and end date.
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		String startDateString = dateFormat.format(startDate.getTime());
		String endDateString = dateFormat.format(endDate.getTime());

		// Create callback that will be called by the async task when it is
		// finished downloading and processing mention data.
		SummaryCallback dc = new SummaryCallback()
		{
			public void onSuccess(QueryData queryData)
			{
				DataCache.add(queryId, queryData);
				
				// Now download mentions.
				downloadMentions();
			}

			public void onFailure()
			{
				// onLoginError(responseCode);
			}
		};
		
		// Prepare HTTP request.
		String url = "http://bwjsonapi.nodejitsu.com/summary?queryId="
				+ queryId + "&startDate=" + startDateString + "&endDate="
				+ endDateString;
		
		HttpRequestParameters requestParams = new HttpRequestParameters();
		requestParams.url = url;
		requestParams.methodName = "GET";
		requestParams.header = key;
			

		new AsyncTaskSummary(dc, this, "Getting data ...").execute(requestParams);

	}
	
	private void downloadMentions()
	{
		String key = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("key", "");
		
		// Format start date and end date.
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		
		String startDateString = dateFormat.format(startDate.getTime());
		String endDateString = dateFormat.format(endDate.getTime());
		
		// Create callback that will be called by the async task when it is
		// finished downloading and processing mention data.
		MentionsCallback dc = new MentionsCallback()
		{
			public void onSuccess(List<MentionItem> mentions)
			{
				DataCache.add(queryId + "mentions", mentions);
			}

			public void onFailure()
			{
				// onLoginError(responseCode);
			}
		};
		
		// Prepare HTTP request.
		int startIndex = 0;
		int maxResults = 10;
		String url2 = "http://bwjsonapi.nodejitsu.com/mentions?queryId="
				+ queryId + "&startDate=" + startDateString + "&endDate="
				+ endDateString + "&startIndex=" + startIndex + "&maxResults="
				+ maxResults;
		

		HttpRequestParameters requestParams2 = new HttpRequestParameters();
		requestParams2.url = url2;
		requestParams2.methodName = "GET";
		requestParams2.header = key;
		
		new AsyncTaskMention(dc, this, "Getting data ...").execute(requestParams2);
	}
}
