package com.brandwatchmobile.activity;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import com.brandwatchmobile.R;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

public class CalendarActivity extends Activity
{
	private Calendar startDate = new GregorianCalendar();
	private Calendar endDate = new GregorianCalendar();
	
	private DatePickerDialog.OnDateSetListener startDateListener;
	private DatePickerDialog.OnDateSetListener endDateListener;
	
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar);
		
		Bundle extras = getIntent().getExtras();
		if (extras != null)
		{
			startDate = (Calendar) extras.get("startDate");
			endDate = (Calendar) extras.get("endDate");
		}
		
		startDateListener = new DatePickerDialog.OnDateSetListener() 
		{
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) 
            {
            	startDate.set(year, monthOfYear, dayOfMonth);
                updateDisplay();
            }
		};
		  
		endDateListener = new DatePickerDialog.OnDateSetListener() 
		{
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) 
            {
            	endDate.set(year, monthOfYear, dayOfMonth);
                updateDisplay();
            }
		};
		
		updateDisplay();
		setupDateClick();
	}
	
	private void setupDateClick()
	{
		Button startDateView = (Button) findViewById(R.id.startDateDate);
		Button endDateView = (Button) findViewById(R.id.endDateDate);
		
		Button todayButton = (Button) findViewById(R.id.todayButton);
		Button sevenDaysButton = (Button) findViewById(R.id.sevenDaysButton);
		Button fourteenDaysButton = (Button) findViewById(R.id.fourteenDaysButton);
		Button oneMonthButton = (Button) findViewById(R.id.oneMonthButton);
		Button twoMonthsButton = (Button) findViewById(R.id.twoMonthsButton);
		
		Button applyButton = (Button) findViewById(R.id.calendarApplyButton);
		Button cancelButton = (Button) findViewById(R.id.calendarCancelButton);
		
		startDateView.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View view) 
			{
				onCreateDialog(startDate, startDateListener).show();
			};
		});
		
		endDateView.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View view) 
			{
				onCreateDialog(endDate, endDateListener).show();
			}
		});
		
		todayButton.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View view) 
			{
				endDate = new GregorianCalendar();
				startDate = new GregorianCalendar();
				updateDisplay();
			}
		});
		
		sevenDaysButton.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View view) 
			{
				endDate = new GregorianCalendar();
				startDate = new GregorianCalendar();
				startDate.add(Calendar.DAY_OF_YEAR, -7);
				updateDisplay();
			}
		});
		
		fourteenDaysButton.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View view) 
			{
				endDate = new GregorianCalendar();
				startDate = new GregorianCalendar();
				startDate.add(Calendar.DAY_OF_YEAR, -14);
				updateDisplay();
			}
		});
		
		oneMonthButton.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View view) 
			{
				endDate = new GregorianCalendar();
				startDate = new GregorianCalendar();
				startDate.add(Calendar.MONTH, -1);
				updateDisplay();
			}
		});
		
		twoMonthsButton.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View view) 
			{
				endDate = new GregorianCalendar();
				startDate = new GregorianCalendar();
				startDate.add(Calendar.MONTH, -2);
				updateDisplay();
			}
		});
		
		//---------------------

		applyButton.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View view) 
			{
				Intent resultIntent = new Intent();
				resultIntent.putExtra("startDate", startDate);
				resultIntent.putExtra("endDate", endDate);
				
				setResult(Activity.RESULT_OK, resultIntent);
				finish();
			}
		});

		cancelButton.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View view) 
			{
				Intent resultIntent = new Intent();
				setResult(Activity.RESULT_CANCELED, resultIntent);
				finish();
			}
		});
	}
	
	private void updateDisplay()
	{
		Button startDateView = (Button) findViewById(R.id.startDateDate);
		Button endDateView = (Button) findViewById(R.id.endDateDate);
		
		updateDateTextView(startDate, startDateView);
		updateDateTextView(endDate, endDateView);
	}
	
	private void updateDateTextView(Calendar calendar, TextView textView)
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(calendar.getTime());
		
		textView.setText(date);
	}
	
	private Dialog onCreateDialog(Calendar calendar,  DatePickerDialog.OnDateSetListener listener) 
	{
		int year = calendar.get(Calendar.YEAR);
	    int month = calendar.get(Calendar.MONTH);
		int day = calendar.get(Calendar.DAY_OF_MONTH);
		
		return new DatePickerDialog(this, listener, year, month, day);
	}
}
