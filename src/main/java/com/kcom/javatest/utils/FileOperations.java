package com.kcom.javatest.utils;

import com.kcom.javatest.exception.ChangeNotGivenException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 * The various file operations to be performed, for example loading the properties to the file
 * and saving the properties back to the properties file.
 */
public class FileOperations {

	/**
	 * This method is responsible for loading the properties from the properties file with the filename
	 * specified in the method parameter.
	 *
	 * @param propsFileName - the properties file to load
	 * @return - the Properties object
	 * @throws ChangeNotGivenException
	 */
	public static Properties loadPropertiesFromPropertiesFile(String propsFileName)
			throws ChangeNotGivenException {
		InputStream inputStream;
		Properties properties = new Properties();
		try {
			inputStream = new FileInputStream(new File(propsFileName));
			properties.load(inputStream);
			inputStream.close();
			return properties;
		} catch (FileNotFoundException e) {
			throw new ChangeNotGivenException("No change given, Properties File Not Found", e);
		} catch (IOException e) {
			throw new ChangeNotGivenException("No change given, some IO Exception occurred", e);
		}
	}

	/**
	 * This method is used to store the properties object to the persistent Properties file. The file
	 * name is passed in the method parameter along with the properties object.
	 *
	 * @param propsFileName - the properties file name
	 * @param properties - the properties object
	 * @return
	 * @throws ChangeNotGivenException
	 */
	public static Properties savePropertiesToPropertiesFile(String propsFileName, Properties properties)
			throws ChangeNotGivenException {
		OutputStream outputStream;
		try {
			outputStream = new FileOutputStream(new File(propsFileName));
			properties.store(outputStream, "The Coins File");
			outputStream.close();
			return properties;
		} catch (FileNotFoundException e) {
			throw new ChangeNotGivenException("No change given, Properties File Not Found", e);
		} catch (IOException e) {
			throw new ChangeNotGivenException("No change given, some IO Exception occurred", e);
		}
	}
}
