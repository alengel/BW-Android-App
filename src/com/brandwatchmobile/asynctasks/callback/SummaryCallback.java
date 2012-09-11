package com.brandwatchmobile.asynctasks.callback;

import com.brandwatchmobile.adapter.item.QueryData;

public interface SummaryCallback
{
	void onSuccess(QueryData queryData);
	void onFailure();
}
