package com.qa.api.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

	private static Properties properties = new Properties();

	static {

		InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream("config/config.properties");
		
		if (input !=null) {
			try {
				properties.load(input);
				
			} catch (IOException e) {
				e.printStackTrace();

			}
		}
	}
	
	
	public static String get(String key) {
		return properties.getProperty(key).trim();
	}
	
	public static void set(String key,String value) {
		properties.setProperty(key, value);
	}

}
