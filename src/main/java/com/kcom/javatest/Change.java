package com.kcom.javatest;

import com.kcom.javatest.exception.ChangeNotGivenException;
import com.kcom.javatest.utils.FileOperations;
import com.kcom.javatest.vo.Coin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;

/**
 * This class is the business logic class that contain the logic to calculate
 * the number of coins and of which denomination to return for an amount of
 * pence passed
 */
public class Change {

	private static final Logger logger = LoggerFactory.getLogger(Change.class);
	private static final int[] denominations = {100, 50, 20, 10, 5, 2, 1};

	/**
	 * Returns the optimal change for the amount of pence entered
	 *
	 * @param pence - the amount to be changed into coins
	 * @return - the amount of change for the pence passed. This is a collection of the Coin class
	 */
	public Collection<Coin> getOptimalChange(int pence)
			throws ChangeNotGivenException {
		// null indicates lack of properties file input, which in turn means an unlimited supply of coins
		logger.debug("Inside getOptimalChage, calling getCoins method with " + pence + " pence to be changed");
		return getCoins(pence, null);
	}

	/**
	 * Returns the optimal change for the amount of pence entered and the amount of change is checked
	 * from a properties file.
	 *
	 * @param pence - the amount to be changed into coins
	 * @param propsFileName - The properties file to be read for coins collection
	 * @return - the amount of change for the pence passed. This is a collection of the Coin class
	 */
	public Collection<Coin> getChangeFor(int pence, String propsFileName)
			throws ChangeNotGivenException {

		logger.debug("Inside getOptimalChage, calling getCoins method with " + pence + " pence to be changed");
		logger.debug("the properties file " + propsFileName + " will be limiting the amount of coins");

		// Load the coins in bank from properties file
		Properties properties = FileOperations.loadPropertiesFromPropertiesFile(propsFileName);
		logger.debug("Coins loaded successfully from the properties file");

		// Get the change coins
		Collection<Coin> coinsCollection = getCoins(pence, properties);
		logger.debug("Change acquired successfully");

		// update properties object with returned number of coins in change
		updateProperties(properties, coinsCollection);
		// write back the properties object into the file
		FileOperations.savePropertiesToPropertiesFile(propsFileName, properties);
		logger.debug("coins from the properties file reduced successfully");

		return coinsCollection;
	}

	/**
	 * This method is a generic method which takes in the Properties object as an input along with the
	 * amount in pence to convert the pence in the number of optimal coins. In case a properties file
	 * passed is null, the method will assume an infinite supply of coins, otherwise, it'll pick the
	 * amount of coins per denomination from the passed properties.
	 *
	 * @param pence - the pence to be converted into coins of several denominations
	 * @param properties - The properties object which has the amount of coins in circulation. If passed
	 *                   as null, will ensure an unlimited supply of coins
	 * @return the coins collection with number of coins per denomination.
	 */
	private Collection<Coin> getCoins(int pence, Properties properties) throws ChangeNotGivenException {
		List<Coin> coinsList = new ArrayList<Coin>();

		logger.debug("inside getCoins to change " + pence + " pence");
		logger.debug(properties == null ? "no properties file used" : "max coins acquired from properties");
		try {
			for (int coinDenomination: denominations) {
				// if the number of pennies passed are 0 or -ve, or during the loop the amount becomes 0
				// then break out of the loop.
				if (pence <= 0) {
					logger.debug("No change given, 0 or less amount provided");
					break;
				}
				// process the entered pence and add to the coins collection
				int numberOfCoins = pence/coinDenomination;

				// The same method refactored with properties entry. if the properties are present, then the
				// coins are limited and should be checked if there are coins present in the denomination, then
				// only deducted.
				if(properties != null) {
					logger.debug("checking how many coins for denomination " + coinDenomination + " present");
					int numberOfCoinsInBank = Integer.parseInt(properties.getProperty(String.valueOf(coinDenomination)));
					if(numberOfCoins > numberOfCoinsInBank) {
						numberOfCoins = numberOfCoinsInBank;
					}
				}

				// if the number of coins of the denomination needs to be deducted, then only to deduct from them
				logger.debug("determine the optimal number of coins for " + coinDenomination);
				if (numberOfCoins > 0) {
					Coin coin = new Coin();
					coin.setDenomination(coinDenomination);
					coin.setNumberOfCoins(numberOfCoins);
					coinsList.add(coin);
					pence -= numberOfCoins*coinDenomination;
				}
			}
			// If the amount is still left after taking away from all the denominations, this means there are not
			// enough coins available in the bank
			if(pence > 0) {
				logger.error("not enough coins present to cater for given amount");
				throw new ChangeNotGivenException("not enough amount");
			}
			return coinsList;
		} catch(NumberFormatException nfe) {
			logger.error("Some exception occurred parsing the amount of coins from the properties file");
			// this is to check for any issues encountered while changing numbers from Strings in the file
			throw new ChangeNotGivenException("some exception occurred", nfe);
		}
	}

	/**
	 * This method is used to update the properties object from the given collection of coins. This object will
	 * then be written back to the properties file.
	 *
	 * @param properties - the properties object to update
	 * @param coinCollection - the coins collection to subtract from existing set of coins in the properties
	 */
	private void updateProperties(Properties properties, Collection<Coin> coinCollection) {
		for(Coin coin: coinCollection) {
			logger.debug("updating the properties file, reducing the coins from the bank");
			int numberOfCoinsInProperties =
					Integer.parseInt(properties.getProperty(String.valueOf(coin.getDenomination())));
			int numberOfCoinsToSet = numberOfCoinsInProperties - coin.getNumberOfCoins();
			properties.setProperty(String.valueOf(coin.getDenomination()), String.valueOf(numberOfCoinsToSet));
		}
	}
}
