package com.brandwatchmobile.jsonparsers;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.brandwatchmobile.adapter.item.ListItem;

//Helper class to parse JSON query data.
public class JsonQueryParser
{
	static public List<ListItem> parse(String json)
	{
		List<ListItem> items = new ArrayList<ListItem>();
		
		try
		{
			JSONArray jsonArray = new JSONArray(json);

			for (int i = 0; i < jsonArray.length(); i++)
			{
				JSONObject jsonObject = jsonArray.getJSONObject(i);

				if (jsonObject == null)
					continue;
				
				// Parse query.
				String name = jsonObject.getString("name").toString();
				String id = jsonObject.getString("id").toString();

				ListItem item = new ListItem(id, name);
				items.add(item);
			}
		} 
		catch (Exception e){}
		
		return items;
	}
}
