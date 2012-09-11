package com.brandwatchmobile.adapter.item;

public class SentimentItem 
{
	public int total = 0;
	public int negative = 0;
	public int positive = 0;
	public int neutral = 0;

	public SentimentItem(int total,
							int negative,
							int positive,
							int neutral)
	{
		this.total = total;
		this.negative = negative;
		this.positive = positive;
		this.neutral = neutral;
	}
}
