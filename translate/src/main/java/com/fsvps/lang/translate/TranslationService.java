package com.fsvps.lang.translate;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import flexjson.JSONDeserializer;

public class TranslationService {

	private String basePath;
	private String inputPath;
	private String outputPath;
	private String fileName;
	private String serviceURL;
	
	public void setBasePath(String basePath) {
		this.basePath = basePath;
	}

	public void setInputPath(String inputPath) {
		this.inputPath = inputPath;
	}

	public void setOutputPath(String outputPath) {
		this.outputPath = outputPath;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setServiceURL(String serviceURL) {
		this.serviceURL = serviceURL;
	}

	public void translate(String lang) {
		Properties inputProperties = getInputProperties();
		
		System.out.println("Fetched properties from Input properties file '" + fileName + ".properties'");
		
		String postParameters = getPostParameters(inputProperties);
		
		System.out.println("Calling service for translation");
		
		String output = executePost(serviceURL + lang, postParameters);

		System.out.println("Translation from service is completed.");
		
		Response response = new JSONDeserializer<Response>().deserialize(output, Response.class);
		
		System.out.println("Saving translated text(s) to output file");
		
		createMessagesFile(inputProperties, response.getText(), lang);
		
		System.out.println("Saved properties to output file '" + fileName + "_" + lang + ".properties'");
	}
	
	private String executePost(String targetURL, String urlParameters) {
		HttpURLConnection connection = null;

		try {
			// Create connection
			URL url = new URL(targetURL);
			connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("POST");
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

			connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
			connection.setRequestProperty("Content-Language", "en-US");

			connection.setUseCaches(false);
			connection.setDoOutput(true);

			// Send request
			DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
			wr.writeBytes(urlParameters);
			wr.close();

			// Get Response
			InputStream is = connection.getInputStream();
			BufferedReader rd = new BufferedReader(new InputStreamReader(is));
			StringBuilder response = new StringBuilder(); // or StringBuffer if
															// Java version 5+
			String line;
			while ((line = rd.readLine()) != null) {
				response.append(line);
				response.append('\r');
			}
			rd.close();
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
		}
	}

	private String getPostParameters(Properties properties) {
		List<String> params = new ArrayList<String>();
		for (Object item : properties.values()) {
			params.add("text=" + item);
		}
		return String.join("&", params);
	}
	
	private Properties getInputProperties() {
		Properties properties = new Properties();
		InputStream inputStream = null;

		try {
			inputStream = new FileInputStream(basePath + "/" + inputPath + "/" + fileName + ".properties");
			properties.load(inputStream);

			return properties;
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (inputStream != null) {
				try {
					inputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}
	
	private void createMessagesFile(Properties properties, List<String> converted, String lang) {
		Properties props = new Properties();
		OutputStream output = null;

		try {

			output = new FileOutputStream(basePath + "/" + outputPath + "/" + fileName + "_" + lang + ".properties");
			int count = 0;
			
			for (Object key : properties.keySet()) {
				props.setProperty((String) key, converted.get(count));
				count++;
			}

			props.store(output, null);

		} catch (IOException io) {
			io.printStackTrace();
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
	}
	
	
}
