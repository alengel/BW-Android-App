package com.brandwatchmobile;

import java.util.ArrayList;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.MultipleCategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import com.brandwatchmobile.adapter.item.SentimentItem;

import android.app.Activity;
import android.graphics.Color;

public class Sentiment
{
	// Show percentage of positive, neutral and negative sentiments as a pie chart.
	static public GraphicalView getSentimentView(Activity parent, SentimentItem sentiment)
	{
		// Pie char values.
		List<double[]> values = new ArrayList<double[]>();
		values.add(new double[] { sentiment.negative,
								  sentiment.positive,
								  sentiment.neutral });
		
		// Pie chart sectors name.
		List<String[]> titles = new ArrayList<String[]>();
		titles.add(new String[] { "negative",
								  "positive",
								  "neutral" });
		
		// Pie chart sectors colour.
		int[] colors = new int[] { Color.rgb(202, 39, 45),
								   Color.rgb(105, 191, 47),
								   Color.rgb(170, 170, 170)};
		
		DefaultRenderer renderer = buildPieChartRenderer(colors);

		return ChartFactory.getDoughnutChartView(parent,
												 buildPieChart("Sentiment", titles, values),
												 renderer);
	}

	static protected MultipleCategorySeries buildPieChart(String chartTitle,
														  List<String[]> titles,
														  List<double[]> values)
	{
		MultipleCategorySeries series = new MultipleCategorySeries(chartTitle);

		for (int i = 0; i < values.size(); i++)
		{
			String[] title = titles.get(i);
			double[] value = values.get(i);
			
			series.add(title, value);
		}
		return series;
	}

	static protected DefaultRenderer buildPieChartRenderer(int[] colors)
	{
		DefaultRenderer renderer = new DefaultRenderer();
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		renderer.setMargins(new int[] { 20, 30, 15, 0 });
		renderer.setApplyBackgroundColor(true);
		renderer.setBackgroundColor(Color.rgb(247, 247, 247));
		renderer.setLabelsColor(Color.GRAY);
		
		for (int color : colors)
		{
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			renderer.addSeriesRenderer(r);
		}
		
		return renderer;
	}
}
