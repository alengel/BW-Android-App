package com.brandwatchmobile.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import com.brandwatchmobile.R;
import com.brandwatchmobile.adapter.MentionsAdapter;
import com.brandwatchmobile.adapter.item.ListItem;
import com.brandwatchmobile.adapter.item.MentionItem;
import com.brandwatchmobile.asynctasks.AsyncTaskMention;
import com.brandwatchmobile.asynctasks.callback.MentionsCallback;
import com.brandwatchmobile.utils.HttpRequestParameters;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class AlertsMentions extends Activity
{
	public static MentionItem[] alertsMentionsAdapter = null;

	Calendar startDate = new GregorianCalendar();
	Calendar endDate = new GregorianCalendar();
	String alertId = "";
	String alertSearchTerms = "";

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.alertsmentions);
		
		// Set start date to last week.
		startDate.add(Calendar.DAY_OF_YEAR, -7);

		// Retrieve parameters passed by calling activity.
		Bundle extras = getIntent().getExtras();
		if (extras != null)
		{
			alertId = (String) extras.get("alert");
			alertSearchTerms = (String) extras.get("alertSearch");
		}

		downloadMentions();
		
		ListView listView = (ListView)findViewById(R.id.list);
		listView.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> ds, View view, int position, long id)
			{
				MentionItem item = (MentionItem) ds.getAdapter().getItem(position);

				Intent myIntent = new Intent(view.getContext(), SingleMention.class);
				// Pass information about the mention selected.
				myIntent.putExtra("mention", item);
				
				startActivity(myIntent);
			}
		});
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
				onAlertMentions(mentions);
			}

			public void onFailure()
			{
			}
		};
		
		// Prepare HTTP request.
		int startIndex = 0;
		int maxResults = 10;
		String url2 = "http://bwjsonapi.nodejitsu.com/mentions?queryId="
				+ alertId + "&startDate=" + startDateString + "&endDate="
				+ endDateString + "&startIndex=" + startIndex + "&maxResults="
				+ maxResults + "&" + alertSearchTerms;
		

		HttpRequestParameters requestParams2 = new HttpRequestParameters();
		requestParams2.url = url2;
		requestParams2.methodName = "GET";
		requestParams2.header = key;
		
		new AsyncTaskMention(dc, this, "Getting data ...").execute(requestParams2);
	}

	private void onAlertMentions(List<MentionItem> mentions)
	{
		MentionsAdapter adapter = new MentionsAdapter(this,R.layout.mentions, mentions);
		ListView listView = (ListView)findViewById(R.id.list);
		listView.setAdapter(adapter);
	}
}
