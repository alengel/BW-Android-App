package com.brandwatchmobile.asynctasks;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.util.EntityUtils;

import com.brandwatchmobile.adapter.item.ListItem;
import com.brandwatchmobile.asynctasks.callback.AsyncTaskCallback;
import com.brandwatchmobile.jsonparsers.JsonQueryParser;
import com.brandwatchmobile.utils.CustomHttpClient;
import com.brandwatchmobile.utils.HttpRequestParameters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

//Async task used to retrieve query data.
public final class AsyncTaskQuery extends AsyncTask<HttpRequestParameters, Boolean , List<ListItem> > 
{
	private AsyncTaskCallback callback = null;
	private Activity context = null;
	private ProgressDialog dialog = null;
	private String progressMessage = "";
	
	public AsyncTaskQuery(AsyncTaskCallback callback, Activity context, String progressMessage)
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
	protected List<ListItem> doInBackground(HttpRequestParameters... params) 
	{
		// Request data.
		HttpResponse response = CustomHttpClient.doHttpRequest(params[0]);
		
		List<ListItem> items = new ArrayList<ListItem>();
		
		int responseCode = response.getStatusLine().getStatusCode();
		if(responseCode == HttpStatus.SC_OK)
		{
			// Parse response.
			try
			{
				String responseText = EntityUtils.toString(response.getEntity());
				items = JsonQueryParser.parse(responseText);
			}
			catch (Exception e){}
			
		}
		
		return items;
	}	
	
	@Override
	protected void onPostExecute(List<ListItem> projects) 
	{
		// Hide progress dialog.
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
