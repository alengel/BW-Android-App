package com.brandwatchmobile.adapter.item;

public class LandingItem
{
	public LandingItem(String name, String narrative, boolean clickable)
	{
		this.name = name;
		this.narrative = narrative;
		this.clickable = clickable;
	}
	
	public String name;
	public String narrative;
	public boolean clickable;
}