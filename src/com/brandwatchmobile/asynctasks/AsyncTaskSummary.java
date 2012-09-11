package com.brandwatchmobile.asynctasks;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;


import com.brandwatchmobile.adapter.item.QueryData;
import com.brandwatchmobile.asynctasks.callback.SummaryCallback;
import com.brandwatchmobile.jsonparsers.JsonSummaryParser;
import com.brandwatchmobile.utils.CustomHttpClient;
import com.brandwatchmobile.utils.HttpRequestParameters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

// Async task used to retrieve summary data.
public final class AsyncTaskSummary extends AsyncTask<HttpRequestParameters, Boolean , QueryData > 
{
	private SummaryCallback callback = null;
	private Activity context = null;
	private ProgressDialog dialog = null;
	private String progressMessage = "";
	
	public AsyncTaskSummary(SummaryCallback callback, Activity context, String progressMessage)
	{
		this.callback = callback;
		this.context = context;
		this.progressMessage = progressMessage;
	}
	
	@Override
	protected void onPreExecute()
	{
		// Start progress dialog.
		dialog = ProgressDialog.show(context, progressMessage, "Please wait", true, false);
	}
	
	@Override
	protected QueryData doInBackground(HttpRequestParameters... params) 
	{
		// Request data.
		HttpResponse summaryResponse = CustomHttpClient.doHttpRequest(params[0]);
		
		QueryData queryData = new QueryData();
		
		if(summaryResponse == null)
			return queryData;
		
		int responseCode = summaryResponse.getStatusLine().getStatusCode();
		if(responseCode == HttpStatus.SC_OK)
		{
			// Parse results.
			try
			{
				String summaryResponseText = EntityUtils.toString(summaryResponse.getEntity());
				JSONObject json = new JSONObject(summaryResponseText);
								
				queryData.sentiment =   JsonSummaryParser.parseSentiment(json);
				queryData.history = 	   JsonSummaryParser.parseHistory(json);
				queryData.pageTypes =    JsonSummaryParser.parsePageType(json);
				queryData.topSites =    JsonSummaryParser.parseTopSites(json);
				queryData.topTweeters = JsonSummaryParser.parseTopTweeters(json);
			}
			catch (Exception e){}
		}
		
		return queryData;
	}
	
	@Override
	protected void onPostExecute(QueryData queryData) 
	{
		// Hide progress dialog.
        if (dialog.isShowing()) 
        {
            dialog.dismiss();
        }

        // Return result.
		if(queryData != null)
		{
			callback.onSuccess(queryData);
		}
		else
		{
			callback.onFailure();
		}
	}
}
