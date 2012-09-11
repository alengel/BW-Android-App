package com.brandwatchmobile.asynctasks;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;


import com.brandwatchmobile.adapter.item.MentionItem;
import com.brandwatchmobile.asynctasks.callback.MentionsCallback;
import com.brandwatchmobile.jsonparsers.JsonMentionParser;
import com.brandwatchmobile.utils.CustomHttpClient;
import com.brandwatchmobile.utils.HttpRequestParameters;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

//Async task used to retrieve mention data.
public final class AsyncTaskMention extends AsyncTask<HttpRequestParameters, Boolean , List<MentionItem> > 
{
	private MentionsCallback callback = null;
	private Activity context = null;
	private ProgressDialog dialog = null;
	private String progressMessage = "";
	
	public AsyncTaskMention(MentionsCallback callback, Activity context, String progressMessage)
	{
		this.callback = callback;
		this.context = context;
		this.progressMessage = progressMessage;
	}
	
	@Override
	protected void onPreExecute()
	{
		// Show progress dialog.
		dialog = ProgressDialog.show(context, progressMessage, "Please wait", true, false);
	}
	
	@Override
	protected List<MentionItem> doInBackground(HttpRequestParameters... params) 
	{
		// Get data..
		HttpResponse mentionsResponse = CustomHttpClient.doHttpRequest(params[0]);
		
		List<MentionItem> mentions = new ArrayList<MentionItem>();
		
		int responseCode = mentionsResponse.getStatusLine().getStatusCode();
		if(responseCode == HttpStatus.SC_OK)
		{
			try
			{
				String mentionsResponseText = EntityUtils.toString(mentionsResponse.getEntity());
				mentions =    JsonMentionParser.parse(mentionsResponseText);
			}
			catch (Exception e){}			
		}
		
		return mentions;
	}
	
	@Override
	protected void onPostExecute(List<MentionItem> mentions) 
	{
		// Hide progress dialog.
        if (dialog.isShowing()) 
        {
            dialog.dismiss();
        }

        // Return result.
		if(mentions != null)
		{
			callback.onSuccess(mentions);
		}
		else
		{
			callback.onFailure();
		}
	}
}
