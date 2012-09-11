package com.brandwatchmobile.jsonparsers;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.brandwatchmobile.adapter.item.HistoryItem;
import com.brandwatchmobile.adapter.item.ListItem;
import com.brandwatchmobile.adapter.item.PagetypeItem;
import com.brandwatchmobile.adapter.item.SentimentItem;

// Helper class to parse JSON summary data.
public class JsonSummaryParser
{
	// Parse sentiment data.
	static public SentimentItem parseSentiment(JSONObject json)
	{
		SentimentItem adapter = null;

		try
		{
			// Parse sentiment.
			int total = json.getJSONObject("metrics").getInt("mentions");
			int negative = json.getJSONObject("metrics").getInt("negative");
			int positive = json.getJSONObject("metrics").getInt("positive");
			int neutral = json.getJSONObject("metrics").getInt("neutral");

			adapter = new SentimentItem(total, negative, positive, neutral);
		} 
		catch (JSONException e){}

		return adapter;
	}
	
	// Parse history data.
	static public List<HistoryItem> parseHistory(JSONObject json)
	{
		List<HistoryItem> historyItems = new ArrayList<HistoryItem>();

		try
		{
			// Get history data node.
			JSONArray days = json.getJSONObject("brandData").getJSONArray("days");
			
			for (int i = 0; i < days.length(); i++)
			{
				JSONObject jsonObject = days.getJSONObject(i);

				if (jsonObject == null)
					continue;

				Date date = new Date();
				
				// Parse date.
				try
				{
					String str_date = jsonObject.getString("date").toString();
					DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
					date = (Date) formatter.parse(str_date);
				} 
				catch (ParseException e){}
				
				// Parse mentions count.
				int total = jsonObject.getInt("mentions");
				int negative = jsonObject.getInt("negative");
				int positive = jsonObject.getInt("positive");
				int neutral = jsonObject.getInt("neutral");

				HistoryItem item = new HistoryItem(date,
												   total,
												   negative,
												   positive,
												   neutral);

				historyItems.add(item);
			}
		} catch (JSONException e){}

		return historyItems;
	}

	// Parse page type data.
	static public List<PagetypeItem> parsePageType(JSONObject json)
	{
		List<PagetypeItem> pagetypeItems = new ArrayList<PagetypeItem>();

		try
		{
			// Parse page type.
			int blog = json.getJSONObject("siteTypes").getInt("blog");
			int forum = json.getJSONObject("siteTypes").getInt("forum");
			int general = json.getJSONObject("siteTypes").getInt("general");
			int news = json.getJSONObject("siteTypes").getInt("news");
			int twitter = json.getJSONObject("siteTypes").getInt("twitter");
			int video = json.getJSONObject("siteTypes").getInt("video");

			pagetypeItems.add(new PagetypeItem("blog", blog));
			pagetypeItems.add(new PagetypeItem("forum", forum));
			pagetypeItems.add(new PagetypeItem("general", general));
			pagetypeItems.add(new PagetypeItem("news", news));
			pagetypeItems.add(new PagetypeItem("twitter", twitter));
			pagetypeItems.add(new PagetypeItem("video", video));
		} 
		catch (JSONException e){}

		return pagetypeItems;
	}

	// Parse top sites data.
	static public List<ListItem> parseTopSites(JSONObject json)
	{
		ArrayList<ListItem> items = new ArrayList<ListItem>();

		try
		{
			JSONArray jsonArray = json.getJSONArray("topDomains");

			for (int i = 0; i < jsonArray.length(); i++)
			{
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				if (jsonObject == null)
					continue;

				// Parse top sites.
				String id = jsonObject.getString("volume").toString();
				String name = jsonObject.getString("name").toString();

				ListItem item = new ListItem(id, name);
				items.add(item);
			}
		} 
		catch (JSONException e){}
		
		return items;
	}

	static public List<ListItem> parseTopTweeters(JSONObject json)
	{
		// TO-DO: API call is missing
		return parseTopSites(json);
	}

}
