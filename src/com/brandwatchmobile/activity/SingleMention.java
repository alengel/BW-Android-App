package com.brandwatchmobile.activity;

import com.brandwatchmobile.R;
import com.brandwatchmobile.adapter.item.MentionItem;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.widget.TextView;

public class SingleMention extends Activity 
{	
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
	  super.onCreate(savedInstanceState);
	  
	  setContentView(R.layout.singlemention);
	  
	  Bundle extras = getIntent().getExtras();
	  if(extras !=null) 
	  {
		  MentionItem value = extras.getParcelable("mention");
		  
		  TextView dateField = (TextView) findViewById(R.id.date) ;
		  TextView titleField = (TextView) findViewById(R.id.title);
		  WebView snippetField = (WebView) findViewById(R.id.snippet);
		  
		  dateField.setText(value.getDate());
		  titleField.setText(value.getTitle());
		  snippetField.loadData(value.getSnippet(), "text/html", null);
	  }
	}
}
