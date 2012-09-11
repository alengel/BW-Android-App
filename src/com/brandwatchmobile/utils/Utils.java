package com.brandwatchmobile.utils;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;

// Various utility functions.
public class Utils
{
	// Check if phone is connected to the Internet.
	static public boolean isOnline(Context context)
	{
		ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

		if (cm.getActiveNetworkInfo() == null)
			return false;

		return cm.getActiveNetworkInfo().isConnectedOrConnecting();
	}

	// Show alert dialog box.
	static public void showAlert(Context context, String title, String message)
	{
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle(title);
		alertDialog.setMessage(message);
		
		alertDialog.setButton("OK", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which){}
		});

		alertDialog.show();
	}
}
