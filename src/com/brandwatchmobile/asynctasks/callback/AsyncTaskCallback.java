package com.brandwatchmobile.asynctasks.callback;

import java.util.List;

import com.brandwatchmobile.adapter.item.ListItem;

public interface AsyncTaskCallback
{
	void onSuccess(List<ListItem> projects);
	void onFailure();
}
