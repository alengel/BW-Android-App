package com.brandwatchmobile.adapter;

import com.brandwatchmobile.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class OverviewAdapter extends BaseAdapter
{
	private Context context;

	public OverviewAdapter(Context context)
	{
		this.context = context;
	}

	public int getCount()
	{
		return icons.length;
	}

	public Object getItem(int position)
	{
		return null;
	}

	public long getItemId(int position)
	{
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent)
	{
		ImageView imageView;
		if (convertView == null)
		{ 
			// Create new view.
			imageView = new ImageView(context);
			imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setPadding(8, 8, 8, 8);
		} 
		else
		{
			// Reuse view if not recycled.
			imageView = (ImageView) convertView;
		}

		imageView.setImageResource(icons[position]);
		return imageView;
	}

	// Overview icons.
	private Integer[] icons = { R.drawable.sentiment,
								R.drawable.history,
								R.drawable.pagetype,
								R.drawable.topsites,
								R.drawable.toptweeters,
								R.drawable.mentions };
}
