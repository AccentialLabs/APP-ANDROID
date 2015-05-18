package com.accential.trueone.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Vector;

public class HttpRequestUtil {
	private String postParameters = "";
	private String webPage;
	private Vector<String>names;
	private Vector<String>values;

	public HttpRequestUtil(){
		values = new Vector<String>();
		names = new Vector<String>();
	}

	/**
	 * Adds a post variable (page.php?name=value)
	 *
	 * @param name the variable name
	 * @param value the variable value, can be set to null, the url will simply become &name instead of &name=value
	 * null
	 * @throws UnsupportedEncodingException 
	 */
	public void addPostValue(String name, String value) throws UnsupportedEncodingException {
		if (value == null) {
			try {
				postParameters += "&" + URLEncoder.encode(name, "UTF-8");
				names.add(name);
				values.add("");
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		} else {
			postParameters += "&" + URLEncoder.encode(name, "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8");
			names.add(name);
			values.add(value);
		}
	}

	/**
	 * Send post data without waiting for site output
	 *
	 * @return true if sending data terminated succesfully
	 */
	public boolean sendPost() {
		try {
			if (webPage == null || webPage.equals("")) {
				throw new Exception("Empty url");
			}
			URL url = new URL(webPage);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(postParameters);
			wr.flush();
			wr.close();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		postParameters = "";
		return true;
	}

	/**
	 * Sends data, then waits for site output
	 *
	 * @return null if no data is received, or a String containing the data
	 */
	public String sendPostWithReturnValue() {

		String returnValue = "";
		try {
			if (webPage == null || webPage.equals("")) {
				throw new Exception("Empty url");
			}
			URL url = new URL(webPage);
			URLConnection conn =
					url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter wr =
					new OutputStreamWriter(conn.getOutputStream());
			wr.write(postParameters);
			wr.flush();
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				returnValue += line + "\n";
			}
			wr.close();
			rd.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		postParameters = "";
		values = null;
		names=null;
		values = new Vector<String>();
		names = new Vector<String>();
		return returnValue;
	}

	/**
	 * Sets the page to point at for sending post variables
	 *
	 * @param webPageToPointAt the page that will receive your post data
	 */
	public void setWebPageToPointAt(String webPageToPointAt) {
		webPage = webPageToPointAt;
	}

	/**
	 * @returns A Nx2 matrix containing all parameters name and values
	 */
	public String[][] getParameters() {
		String[][] str = new String[names.size()][2];
		for (int i = 0; i < str.length; i++) {
			str[i][0] = names.get(i);
			str[i][1] = values.get(i);
		}
		return str;
	}

}
