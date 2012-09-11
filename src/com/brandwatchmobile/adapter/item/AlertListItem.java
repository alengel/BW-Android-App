package com.brandwatchmobile.adapter.item;


public class AlertListItem extends ListItem
{
	public AlertListItem(String id, String name, String searchTerms)
	{
		super(id, name);
		
		this.searchTerms = searchTerms;
	}
	
	public String searchTerms;
}
