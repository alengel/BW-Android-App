package com.brandwatchmobile.utils;

import java.net.URI;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class CustomHttpClient
{
	/** The time it takes for our client to timeout */
	public static final int HTTP_TIMEOUT = 30 * 1000; // milliseconds

	/** Single instance of our HttpClient */
	private static HttpClient mHttpClient;
	
	static public HttpResponse doHttpRequest(HttpRequestParameters param)
	{
		HttpResponse response = null;
		try 
		{
			if(param.methodName.equals("GET"))
			{
				response = CustomHttpClient.executeHttpGet(param.url, param.header);
			}
			else if(param.methodName.equals("POST"))
			{
				response = CustomHttpClient.executeHttpPost(param.url, param.params);
			}
		} 
		catch (Exception e){}

		return response;
	}

	/**
	 * Get our single instance of our HttpClient object.
	 * 
	 * @return an HttpClient object with connection parameters set
	 */
	private static HttpClient getHttpClient()
	{
		if (mHttpClient == null)
		{
			mHttpClient = new DefaultHttpClient();
			final HttpParams params = mHttpClient.getParams();
			HttpConnectionParams.setConnectionTimeout(params, HTTP_TIMEOUT);
			HttpConnectionParams.setSoTimeout(params, HTTP_TIMEOUT);
			ConnManagerParams.setTimeout(params, HTTP_TIMEOUT);
		}
		return mHttpClient;
	}

	/**
	 * Performs an HTTP Post request to the specified url with the specified
	 * parameters.
	 * 
	 * @param url
	 *            The web address to post the request to
	 * @param postParameters
	 *            The parameters to send via the request
	 * @return The result of the request
	 * @throws Exception
	 */
	public static HttpResponse executeHttpPost(String url,
			ArrayList<NameValuePair> postParameters) throws Exception
	{

		try
		{
			HttpClient client = getHttpClient();
			HttpPost request = new HttpPost(url);
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(
					postParameters);
			request.setEntity(formEntity);
			HttpResponse response = client.execute(request);

			return response;
		}

		finally
		{

		}
	}

	/**
	 * Performs an HTTP GET request to the specified url.
	 * 
	 * @param url
	 *            The web address to post the request to
	 * @return The result of the request
	 * @throws Exception
	 */
	public static HttpResponse executeHttpGet(String url, String key)
			throws Exception
	{

		try
		{
			HttpClient client = getHttpClient();
			HttpGet request = new HttpGet();
			request.addHeader("x-bw-auth", key);
			request.setURI(new URI(url));
			HttpResponse response = client.execute(request);

			return response;
		} finally
		{

		}
	}
}