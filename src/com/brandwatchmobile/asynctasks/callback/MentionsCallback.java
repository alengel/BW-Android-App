package com.brandwatchmobile.asynctasks.callback;

import java.util.List;

import com.brandwatchmobile.adapter.item.MentionItem;

public interface MentionsCallback
{
	void onSuccess(List<MentionItem> mentions);
	void onFailure();
}
