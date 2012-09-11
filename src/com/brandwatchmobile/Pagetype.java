package com.brandwatchmobile;

import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.SimpleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYMultipleSeriesRenderer.Orientation;

import com.brandwatchmobile.adapter.item.PagetypeItem;

import android.app.Activity;
import android.graphics.Color;

public class Pagetype
{
	// Create bar chart showing the number of mentions per type of website.
	static public GraphicalView getPageTypeView(Activity activity, List<PagetypeItem> pageTypes)
	{
		//Create dataset.
		XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
		XYMultipleSeriesRenderer renderer = buildBarRenderer();
		CategorySeries series = new CategorySeries("");
		for (int i = 0; i < pageTypes.size(); i++)
		{
			int value = pageTypes.get(i).value;
			String name = pageTypes.get(i).name;
			
			series.add(value);
			renderer.addXTextLabel(i+1, name);
		}

		dataset.addSeries(series.toXYSeries());

		// Create bar chart.
		return ChartFactory.getBarChartView(activity, dataset, renderer,Type.DEFAULT);
	}

	static private XYMultipleSeriesRenderer buildBarRenderer()
	{
		XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
		
		renderer.setAxisTitleTextSize(16);
		renderer.setBarSpacing(1);
		renderer.setChartTitleTextSize(20);
		renderer.setLabelsTextSize(15);
		renderer.setLegendTextSize(15);
		renderer.setXAxisMin(0);
		renderer.setXAxisMax(7);
		renderer.setYAxisMin(0);
		renderer.setOrientation(Orientation.HORIZONTAL);
		renderer.setXLabels(0);
		renderer.setApplyBackgroundColor(true);
		renderer.setBackgroundColor(Color.WHITE);
		renderer.setMarginsColor(Color.WHITE);
		renderer.setMargins(new int[] { 20, 30, 15, 0 });

		// Setup series renderer.
		SimpleSeriesRenderer r = new SimpleSeriesRenderer();
		r.setColor(Color.rgb(29, 64, 159));
		r.setDisplayChartValues(true);
		renderer.addSeriesRenderer(r);

		return renderer;
	}

}