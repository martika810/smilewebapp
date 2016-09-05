package com.madcoding.smile.conf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SmileProperties {
	private static SmileProperties INSTANCE = null;
	Properties properties = null;
	
	private SmileProperties(){
		try {
			init();
		} catch (IOException e) {
			e.printStackTrace();
			//e.printStackTrace("Error loading properties");
		}
	}
	
	public static SmileProperties getInstance(){
		if(INSTANCE == null){
			INSTANCE = new SmileProperties();
		}
		return INSTANCE;
	}
	
	public void init() throws IOException{
		properties = new Properties();
		String propFileName = "config.properties";
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
		if(inputStream != null){
			properties.load(inputStream);
		}
		inputStream.close();
	}
	
	public String getProperty(String value){
		return properties.getProperty(value);
	}

}
