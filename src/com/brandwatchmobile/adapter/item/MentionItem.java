package com.brandwatchmobile.adapter.item;

import org.jsoup.Jsoup;

import android.os.Parcel;
import android.os.Parcelable;

public class MentionItem implements Parcelable
{
	private final int snippetSize = 60;

	public MentionItem (String title, 
			String date, 
			String snippet, 
			String mentionurl, 
			String sentiment, 
			String domain, 
			String pagetype)
	{
		this.title = title;
		this.date = date;
		this.snippet = snippet;
		this.mentionurl = mentionurl;
		this.sentiment = sentiment;
		this.domain = domain;
		this.pagetype = pagetype;
	}

	public String title;
	public String date;
	public String snippet;
	public String mentionurl;
	public String sentiment;
	public String domain;
	public String pagetype;

	public String getDate() 
	{
		return date;
	}

	public String getTitle() 
	{
		return title;
	}

	public String getSnippet() 
	{
		return snippet;
	}	

	public String getShortSnippet()
	{
		return snippet.substring(0, snippetSize);
	}

	public String getShortSnippetWithoutHTMLTags()
	{
		String noHTML =  Jsoup.parse(snippet).text();
		int length = Math.min(noHTML.length(), snippetSize);
		return noHTML.substring(0, length);
	}

	public MentionItem(Parcel in)
	{
		this.title = in.readString();
		this.date = in.readString();
		this.snippet = in.readString();
		this.mentionurl = in.readString();
		this.sentiment = in.readString();
		this.domain = in.readString();
		this.pagetype = in.readString();
	}

	@Override
	public int describeContents(){
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) 
	{
		dest.writeString(title);
		dest.writeString(date);
		dest.writeString(snippet);
		dest.writeString(mentionurl);
		dest.writeString(sentiment);
		dest.writeString(domain);
		dest.writeString(pagetype);
	}

	public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
	{
		public MentionItem createFromParcel(Parcel in) 
		{
			return new MentionItem(in); 
		}

		public MentionItem[] newArray(int size) 
		{
			return new MentionItem[size];
		}
	};
}
