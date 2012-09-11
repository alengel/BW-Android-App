package com.brandwatchmobile.activity;

import java.util.List;

import com.brandwatchmobile.History;
import com.brandwatchmobile.Mentions;
import com.brandwatchmobile.Pagetype;
import com.brandwatchmobile.R;

import com.brandwatchmobile.Sentiment;
import com.brandwatchmobile.Topsites;
import com.brandwatchmobile.adapter.item.MentionItem;
import com.brandwatchmobile.adapter.item.QueryData;
import com.brandwatchmobile.utils.DataCache;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;

public class SummaryView extends Activity
{
	
	public enum SummaryViewType
	{
		SENTIMENT, HISTORY, PAGETYPE, TOPTWEETERS, TOPSITES, MENTIONS
	}

	View mCurrentView;

	View mSentimentView;
	View mHistoryView;
	View mPagetypeView;
	ListView mTopsitesView;
	ListView mMentionsView;

	private QueryData queryData = null;
	private List<MentionItem> mentions = null;

	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sentiment);

		Bundle extras = getIntent().getExtras();
		SummaryViewType selectedView = SummaryViewType.SENTIMENT;
		if (extras != null)
		{
			String queryId = (String) extras.get("queryId");
			queryData = (QueryData)DataCache.get(queryId);
			mentions = (List<MentionItem>)DataCache.get(queryId + "mentions");
			selectedView = (SummaryViewType) extras.get("selectedView");
		}
		
		// Initialise view.
		switch (selectedView)
		{
			case  SENTIMENT: showSentiment();
							 break;
			case  HISTORY: showHistory();
			 			   break;
			case  PAGETYPE: showPageType();
			                break;
			case  TOPTWEETERS: showTopSites();
			                   break;
			case  TOPSITES: showTopSites();
			                break;
			case  MENTIONS: showMentions();
			                break;
		}

		setupButtonClick();
	}

	private void setupButtonClick()
	{
		final ImageButton sentimentButton = (ImageButton) findViewById(R.id.sentimentbutton);
		sentimentButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				// Perform action on clicks
				showSentiment();
			}
		});

		final ImageButton historyButton = (ImageButton) findViewById(R.id.historybutton);
		historyButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				// Perform action on clicks
				showHistory();
			}
		});

		final ImageButton pagetypeButton = (ImageButton) findViewById(R.id.pagetypebutton);
		pagetypeButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				// Perform action on clicks
				showPageType();
			}
		});

		final ImageButton topsitesButton = (ImageButton) findViewById(R.id.topsitesbutton);
		topsitesButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				// Perform action on clicks
				showTopSites();
			}
		});

		final ImageButton toptweetersButton = (ImageButton) findViewById(R.id.toptweetersbutton);
		toptweetersButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				// Perform action on clicks
				showTopSites();
			}
		});

		final ImageButton mentionsButton = (ImageButton) findViewById(R.id.mentionsbutton);
		mentionsButton.setOnClickListener(new View.OnClickListener()
		{
			public void onClick(View v)
			{
				// Perform action on clicks
				showMentions();
			}
		});
	}

	private void showSentiment()
	{
		LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
		layout.removeAllViews();
		
		if (mSentimentView == null)
		{
			mSentimentView = Sentiment.getSentimentView(this,queryData.sentiment);
		}

		mCurrentView = mSentimentView;
		layout.addView(mCurrentView);
	}

	private void showHistory()
	{
		LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
		layout.removeAllViews();

		if (mHistoryView == null)
		{
			mHistoryView = History.getHistoryView(this, queryData.history);
		}

		mCurrentView = mHistoryView;
		layout.addView(mCurrentView);
	}

	private void showPageType()
	{
		LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
		layout.removeAllViews();

		if (mPagetypeView == null)
		{
			mPagetypeView = Pagetype.getPageTypeView(this,
					queryData.pageTypes);
		}

		mCurrentView = mPagetypeView;
		layout.addView(mCurrentView);
	}

	private void showTopSites()
	{
		LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
		layout.removeAllViews();

		if (mTopsitesView == null)
		{
			mTopsitesView = Topsites.getTopSitesView(this, queryData.topSites);
		}

		mCurrentView = mTopsitesView;
		layout.addView(mCurrentView);
	}

	private void showMentions()
	{
		LinearLayout layout = (LinearLayout) findViewById(R.id.chart);
		layout.removeAllViews();

		if (mMentionsView == null)
		{
			mMentionsView = Mentions.getMentionsView(this, mentions);
		}

		mCurrentView = mMentionsView;
		layout.addView(mCurrentView);
	}

	protected void onResume()
	{
		super.onResume();
	}
}