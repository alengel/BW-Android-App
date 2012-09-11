package com.brandwatchmobile.activity;

import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.brandwatchmobile.R;
import com.brandwatchmobile.asynctasks.AsyncHttpRequest;
import com.brandwatchmobile.asynctasks.callback.DownloadCallback;
import com.brandwatchmobile.utils.HttpRequestParameters;
import com.brandwatchmobile.utils.Utils;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Login extends Activity {

	EditText login, password;
	TextView error;
	Button ok;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.main);
		
		// Allow network operation on UI thread.
		if (android.os.Build.VERSION.SDK_INT > 9) 
		{
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		
		retrieveViews();
		
		ok.setOnClickListener(new View.OnClickListener() 
		{
			@Override
			public void onClick(View view) 
			{	
				onLogin(login.getText().toString(), password.getText().toString());
			}
		});
	}

	private void retrieveViews()
	{
		// Retrieve references to activity views.
		login = (EditText) findViewById(R.id.loginLabel);
		password = (EditText) findViewById(R.id.passwordLabel);
		ok = (Button) findViewById(R.id.btn_login);
		error = (TextView) findViewById(R.id.tv_error);
	}
	
	private void onLogin(String username, String password)
	{
		// Make sure the phone is connected to the Internet.
		if(Utils.isOnline(this) == false)
		{
			Utils.showAlert(this, "Network", "Your device is not connected to the Internet");
			return;
		}
		
		ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
		postParameters.add(new BasicNameValuePair("username", username));
		postParameters.add(new BasicNameValuePair("password", password));

		DownloadCallback dc = new DownloadCallback() 
		{
			public void onSuccess(HttpResponse response) 
			{
				try 
				{
					// Parse response.
					String responseText = EntityUtils.toString(response.getEntity());
					JSONObject json = new JSONObject(responseText);

					String key = json.get("key").toString();

					onLoginSucces(key);
				} 
				catch (Exception e) 
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			public void onFailure(int responseCode) 
			{
				onLoginError(responseCode);
			}
		};
		
		// Prepare HTTP request parameters.
		HttpRequestParameters requestParams = new HttpRequestParameters();
		requestParams.url = "http://bwjsonapi.nodejitsu.com/login/";
		requestParams.methodName = "POST";
		requestParams.params = postParameters;

		new AsyncHttpRequest(dc, Login.this, "Logging you in...").execute(requestParams);
	}


	private void onLoginSucces(String key) 
	{
		// Store login key.
		Editor editor = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
		editor.putString("key", key);
		editor.commit();
		
		// Reset error text.
		error.setText("");

		// Move to next screen.
		Intent myIntent = new Intent(getApplicationContext(), Landing.class);
		startActivity(myIntent);
	}

	private void onLoginError(int responseCode) 
	{
		// Notify user about login error.
		switch (responseCode) 
		{
			case HttpStatus.SC_FORBIDDEN:
				error.setText("Incorrect Username or Password.");
				break;
	
			case HttpStatus.SC_NOT_FOUND:
				error.setText("Brandwatch will be back shortly.");
				break;
		}
	}
}
