package utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.util.Properties;

public class ConfigReader{
	private static Properties properties = new Properties();
	static {
		try {
			FileInputStream fileInput = new FileInputStream("src/test/resources/config.properties");
            properties.load(fileInput);

		} catch(IOException e){
			//throw new RuntimeException("Failed to load configuration file: " + e.getMessage());
			System.err.println("Failed to load configuration file");
		}
	}

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}
}
