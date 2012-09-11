package com.brandwatchmobile;

import java.util.List;

import com.brandwatchmobile.activity.SingleMention;
import com.brandwatchmobile.adapter.MentionsAdapter;
import com.brandwatchmobile.adapter.item.MentionItem;

import android.app.Activity;
import android.content.Intent;

import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.view.View;

public class Mentions
{
	// List view showing mentions.
	static public ListView getMentionsView(final Activity parent, List<MentionItem> mentionsAdapter)
	{
		//Create list view.
		ListView mentionsView = new ListView(parent);
		
		MentionsAdapter adapter = new MentionsAdapter(parent, R.id.chart, mentionsAdapter);
		mentionsView.setAdapter(adapter);
		mentionsView.setTextFilterEnabled(true);

		// Setup on click event.
		mentionsView.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> ds, View view, int position, long id)
			{
				MentionItem item = (MentionItem) ds.getAdapter().getItem(position);

				Intent myIntent = new Intent(view.getContext(), SingleMention.class);
				// Pass information about the mention selected.
				myIntent.putExtra("mention", item);
				
				parent.startActivity(myIntent);
			}
		});
		
		return mentionsView;
	}
}
