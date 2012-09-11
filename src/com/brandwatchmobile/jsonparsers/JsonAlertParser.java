package com.brandwatchmobile.jsonparsers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.brandwatchmobile.adapter.item.AlertListItem;
import com.brandwatchmobile.adapter.item.ListItem;

//Helper class to parse JSON alert data.
public class JsonAlertParser
{
	static public List<ListItem> parse(String json)
	{
		List<ListItem> items = new ArrayList<ListItem>();
		
		try
		{
			// Parse alerts.
			JSONArray jsonArray = new JSONArray(json);

			for (int i = 0; i < jsonArray.length(); i++)
			{
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				if (jsonObject == null)
					continue;
				
				// An alert is made up of a name, a queryID and a search string.
				String name = jsonObject.getString("name").toString();
				String id = jsonObject.getString("queryId").toString();
				String searchTerms = jsonObject.getString("searchString").toString();

				AlertListItem item = new AlertListItem(id, name, searchTerms);
				items.add(item);
			}
		} 
		catch (Exception e){}
		
		return items;
	}
}
