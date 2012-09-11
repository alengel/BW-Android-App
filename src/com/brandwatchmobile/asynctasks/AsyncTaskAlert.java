package com.brandwatchmobile.asynctasks;

import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;

import com.brandwatchmobile.adapter.item.ListItem;
import com.brandwatchmobile.asynctasks.callback.AsyncTaskCallback;
import com.brandwatchmobile.jsonparsers.JsonAlertParser;
import com.brandwatchmobile.utils.CustomHttpClient;
import com.brandwatchmobile.utils.HttpRequestParameters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

//Async task used to retrieve project data.
public final class AsyncTaskAlert extends AsyncTask<HttpRequestParameters, Boolean , List<ListItem> > 
{
	private AsyncTaskCallback callback = null;
	private Activity context = null;
	private ProgressDialog dialog = null;
	private String progressMessage = "";
	
	public AsyncTaskAlert(AsyncTaskCallback callback, Activity context, String progressMessage)
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
	protected List<ListItem> doInBackground(HttpRequestParameters... params) 
	{
		HttpResponse response = CustomHttpClient.doHttpRequest(params[0]);
		
		List<ListItem> items = null;
		
		int responseCode = response.getStatusLine().getStatusCode();
		if(responseCode == HttpStatus.SC_OK)
		{
			try
			{
				String responseText = EntityUtils.toString(response.getEntity());
				items = JsonAlertParser.parse(responseText);
			}
			catch (Exception e){}
		}
		
		return items;
	}
	
	@Override
	protected void onPostExecute(List<ListItem> projects) 
	{
		// Hide progress bar.
        if (dialog.isShowing()) 
        {
            dialog.dismiss();
        }

        // Return result.
		if(projects != null)
		{
			callback.onSuccess(projects);
		}
		else
		{
			callback.onFailure();
		}
	}
}
