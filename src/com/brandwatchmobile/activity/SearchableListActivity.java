package com.brandwatchmobile.activity;

import java.util.ArrayList;

import java.util.List;

import com.brandwatchmobile.R;
import com.brandwatchmobile.adapter.ListAdapter;
import com.brandwatchmobile.adapter.item.ListItem;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView.OnItemClickListener;

// This activity displays a list and lets the user filter it by pressing the search button.
public class SearchableListActivity extends Activity
{
	protected List<ListItem> listItems = new ArrayList<ListItem>();

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchablelist);

		EditText searchView = (EditText) findViewById(R.id.search);
		
		// Filter elements when the user modifies the search box.
		searchView.addTextChangedListener(new TextWatcher()
		{
			public void afterTextChanged(Editable s){}
			public void beforeTextChanged(CharSequence s, int start, int count, int after){}
			public void onTextChanged(CharSequence s, int start, int before, int count)
			{
				filterList(s);
			}
		});
		
		ListView lv = (ListView) findViewById(R.id.listView);
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				onItemSelected(parent, view, position);
			}
		});
	}


	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// If search key is pressed
		if (event.getKeyCode() == KeyEvent.KEYCODE_SEARCH)
		{
			// Toggle search box visibility.
			EditText search = (EditText) findViewById(R.id.search);
			if (search.getVisibility() == View.GONE)
			{
				search.setVisibility(View.VISIBLE);
				search.requestFocus();
				
				// Show keyboard.
				InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				mgr.showSoftInput(search, InputMethodManager.SHOW_IMPLICIT);
			} 
			else
			{
				search.setVisibility(View.GONE);
				
				// Hide keyboard.
				InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				mgr.hideSoftInputFromWindow(search.getWindowToken(), 0);
			}
		}

		return super.onKeyDown(keyCode, event);
	}


	private void filterList(CharSequence searchTerm)
	{
		List<ListItem> array_sort = new ArrayList<ListItem>();
		String lowerCaseSearchTerm = searchTerm.toString().toLowerCase();

		// Empty search term - show everything.
		if (searchTerm.length() == 0)
		{
			array_sort = listItems;
		} 
		else
		{
			// Search items containing searchTerm/
			for (int i = 0; i < listItems.size(); i++)
			{
				ListItem item = listItems.get(i);
				if (item.name.toLowerCase().contains(lowerCaseSearchTerm))
				{
					array_sort.add(item);
				}
			}
		}

		// Update adapter.
		ListAdapter filteredAdapter = new ListAdapter(SearchableListActivity.this, R.layout.listitem, array_sort);

		ListView listView = (ListView) findViewById(R.id.listView);
		listView.setAdapter(filteredAdapter);
	}
	
	protected void onItemSelected(AdapterView<?> parent, View view, int position){};
}
