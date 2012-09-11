package com.brandwatchmobile.adapter.item;

import java.util.Date;

public class HistoryItem 
{
	public Date date = null;
	public int total = 0;
	public int negative = 0;
	public int positive = 0;
	public int neutral = 0;

	public HistoryItem	   (Date date,
							int total,
							int negative,
							int positive,
							int neutral)
	{
		this.date = date;
		this.total = total;
		this.negative = negative;
		this.positive = positive;
		this.neutral = neutral;
	}
}