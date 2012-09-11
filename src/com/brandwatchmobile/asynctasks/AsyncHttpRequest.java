package com.brandwatchmobile.asynctasks;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import com.brandwatchmobile.asynctasks.callback.DownloadCallback;
import com.brandwatchmobile.utils.CustomHttpClient;
import com.brandwatchmobile.utils.HttpRequestParameters;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;

// Asynchronous HTTP to avoid blocking the UI thread.
public final class AsyncHttpRequest extends AsyncTask<HttpRequestParameters, Boolean , HttpResponse > 
{
	private DownloadCallback callback = null;
	private Activity context = null;
	private ProgressDialog dialog = null;
	private String progressMessage = "";
	
	public AsyncHttpRequest(DownloadCallback callback, Activity context, String progressMessage)
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
	protected HttpResponse doInBackground(HttpRequestParameters... params) 
	{
		return CustomHttpClient.doHttpRequest(params[0]);
	}
	
	
	@Override
	protected void onPostExecute(HttpResponse result) 
	{
		// Hide progress dialog.
        if (dialog.isShowing()) 
        {
            dialog.dismiss();
        }

        // Return result.
		int responseCode = result.getStatusLine().getStatusCode();
		if(responseCode == HttpStatus.SC_OK)
		{
			callback.onSuccess(result);
		}
		else
		{
			callback.onFailure(responseCode);
		}
	}
}