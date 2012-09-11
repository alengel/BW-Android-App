package com.brandwatchmobile.utils;

import java.util.HashMap;
import java.util.Map;

// Class used to cache query and mentions data.
public class DataCache
{
	static private Map<String, Object> queryData = new HashMap<String, Object>();
	
	static public Object get(String key)
	{
		if(queryData.containsKey(key))
		{
			return queryData.get(key);
		}
		
		return null;
	}
	
	static public void add(String key, Object value)
	{
		queryData.put(key, value);
	}
}
