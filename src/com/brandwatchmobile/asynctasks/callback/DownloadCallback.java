package com.brandwatchmobile.asynctasks.callback;

import org.apache.http.HttpResponse;

public interface DownloadCallback 
{
	   void onSuccess(HttpResponse response);
	   void onFailure(int responseCode);
}
