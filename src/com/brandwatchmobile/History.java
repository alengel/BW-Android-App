package com.brandwatchmobile;

import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import com.brandwatchmobile.adapter.item.HistoryItem;

import android.app.Activity;
import android.graphics.Color;

public class History
{	
	static final private int series_count = 4;
	
	// Build history graph. This graph shows how positive, neutral and negative mentions evolve overtime.
	static public GraphicalView getHistoryView(Activity parent, List<HistoryItem> historyItems)
	{
		// First create the dataset.
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

		// Create data series.
		TimeSeries totalSeries = new TimeSeries("total");
		TimeSeries negativeSeries = new TimeSeries("negative");
		TimeSeries positiveSeries = new TimeSeries("positive");
		TimeSeries neutralSeries = new TimeSeries("neutral");
		for (int i = 0; i < historyItems.size(); i++)
		{
			totalSeries.add(historyItems.get(i).date, historyItems.get(i).total);
			negativeSeries.add(historyItems.get(i).date, historyItems.get(i).negative);
			positiveSeries.add(historyItems.get(i).date, historyItems.get(i).positive);
			neutralSeries.add(historyItems.get(i).date, historyItems.get(i).neutral);
		}
		
		// Add series to dataset.
		dataset.addSeries(totalSeries);
		dataset.addSeries(negativeSeries);
		dataset.addSeries(positiveSeries);
		dataset.addSeries(neutralSeries);

		// Setup graph style.
		
		// Set series colour.
		int[] colors = new int[] { Color.rgb(29, 64, 159),
								   Color.rgb(202, 39, 45),
								   Color.rgb(105, 191, 47),
								   Color.rgb(170, 170, 170) };
		// Set series point style.
		PointStyle[] styles = new PointStyle[] { PointStyle.CIRCLE,
												 PointStyle.CIRCLE,
												 PointStyle.CIRCLE,
												 PointStyle.CIRCLE };
		
		// Create graph renderer.
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		for (int i = 0; i < series_count; i++)
		{
			XYSeriesRenderer r = new XYSeriesRenderer();
			r.setColor(colors[i]);
			r.setPointStyle(styles[i]);
			r.setFillPoints(true);
			renderer.addSeriesRenderer(r);
		}
		
		renderer.setPanEnabled(false, false);
		renderer.setApplyBackgroundColor(true);
		renderer.setBackgroundColor(Color.WHITE);
		renderer.setMarginsColor(Color.WHITE);

		// Create chart view.
		return ChartFactory.getTimeChartView(parent, dataset, renderer, "MMM d");
	}
}