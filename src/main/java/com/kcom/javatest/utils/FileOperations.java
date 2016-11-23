package com.kcom.javatest.utils;

import com.kcom.javatest.exception.ChangeNotGivenException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

	private static Logger logger = LoggerFactory.getLogger(FileOperations.class);

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
		logger.debug("inside the loadPropertiesFromPropertiesFile method");
		InputStream inputStream;
		Properties properties = new Properties();
		try {
			logger.debug("loading from the properties file " + propsFileName);
			inputStream = new FileInputStream(new File(propsFileName));
			properties.load(inputStream);
			logger.debug("loading from properties file successful");
			inputStream.close();
			return properties;
		} catch (FileNotFoundException e) {
			logger.error("Properties file not found");
			throw new ChangeNotGivenException("No change given, Properties File Not Found", e);
		} catch (IOException e) {
			logger.error("Some IO Exception occurred while loading from properties file");
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
		logger.debug("inside savePropertiesToPropertiesFile method");
		OutputStream outputStream;
		try {
			outputStream = new FileOutputStream(new File(propsFileName));
			logger.debug("storing to properties file " + propsFileName);
			properties.store(outputStream, "The Coins File");
			logger.debug("saving to properties file successful");
			outputStream.close();
			return properties;
		} catch (FileNotFoundException e) {
			logger.error("Properties file not found");
			throw new ChangeNotGivenException("No change given, Properties File Not Found", e);
		} catch (IOException e) {
			logger.error("Some IO Exception occurred while saving to properties file");
			throw new ChangeNotGivenException("No change given, some IO Exception occurred", e);
		}
	}
}
