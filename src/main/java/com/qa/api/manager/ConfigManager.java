package com.qa.api.manager;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigManager {

	private static Properties properties = new Properties();

	static {

		// mvn clean install -Denv=qa/dev/uat/prod
		// env -- environment variable
		// mvn clean install - - if env is not given then run on the testcases on prod

		String envName = System.getProperty("env", "prod");
		System.out.println("Running the tests on env : " + envName);

		String fileName = "config_" + envName + ".properties";

		// InputStream input =
		// ConfigManager.class.getClassLoader().getResourceAsStream("config.properties");
		InputStream input = ConfigManager.class.getClassLoader().getResourceAsStream(fileName);

		if (input != null) {
			try {
				properties.load(input);
				System.out.println("Properties ====> " + properties);

			} catch (IOException e) {
				e.printStackTrace();

			}
		}
	}

	public static String get(String key) {
		return properties.getProperty(key).trim();
	}

	public static void set(String key, String value) {
		properties.setProperty(key, value);
	}

}
