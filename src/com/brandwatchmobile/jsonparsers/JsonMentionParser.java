package com.brandwatchmobile.jsonparsers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.brandwatchmobile.adapter.item.MentionItem;

//Helper class to parse JSON mention data.
public class JsonMentionParser
{
	static public List<MentionItem> parse(String jsons)
	{
		List<MentionItem> items = new ArrayList<MentionItem>();

		try
		{
			JSONObject root = new JSONObject(jsons);
			// Get root mention.
			JSONArray jsonArray = root.getJSONArray("items");

			for (int i = 0; i < jsonArray.length(); i++)
			{
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				if (jsonObject == null)
					continue;

				// Parse mention.
				String title = jsonObject.getString("title");
				String date = jsonObject.getString("formatted_date");
				String snippet = jsonObject.getString("summary");
				String mentionurl = jsonObject.getString("url");
				String sentiment = jsonObject.getString("brandSentiment");
				String domain = jsonObject.getString("domain");
				String pagetype = jsonObject.getString("site_type");

				MentionItem item = new MentionItem(title,
												   date,
												   snippet,
												   mentionurl,
												   sentiment,
												   domain,
												   pagetype);
				items.add(item);
			}
		} 
		catch (Exception e){}

		return items;
	}
}
