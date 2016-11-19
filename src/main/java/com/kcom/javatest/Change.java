package com.kcom.javatest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
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

	static final int[] denominations = {100, 50, 20, 10, 5, 2, 1};

	/**
	 * Returns the optimal change for the amount of pence entered
	 *
	 * @param pence - the amount to be changed into coins
	 * @return - the amount of change for the pence passed. This is a collection of the Coin class
	 */
	public Collection<Coin> getOptimalChange(int pence) {
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
	public Collection<Coin> getChangeFor(int pence, String propsFileName) {
		InputStream inputStream;
		Properties properties = new Properties();
		try {
			inputStream = new FileInputStream(new File(propsFileName));
			properties.load(inputStream);
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return getCoins(pence, properties);
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
	private Collection<Coin> getCoins(int pence, Properties properties) {
		List<Coin> coinsList = new ArrayList<Coin>();

		try {
			for (int coinDenomination: denominations) {
				// if the number of pennies passed are 0 or -ve, or during the loop the amount becomes 0
				// then break out of the loop.
				if (pence <= 0) {
					break;
				}
				// process the entered pence and add to the coins collection
				int numberOfCoins = pence/coinDenomination;

				if(properties != null) {
					int numberOfCoinsInBank = Integer.parseInt(properties.getProperty(String.valueOf(coinDenomination)));
					if(numberOfCoins > numberOfCoinsInBank) {
						numberOfCoins = numberOfCoinsInBank;
					}
				}

				if (numberOfCoins > 0) {
					Coin coin = new Coin();
					coin.setDenomination(coinDenomination);
					coin.setNumberOfCoins(numberOfCoins);
					coinsList.add(coin);
					pence -= numberOfCoins*coinDenomination;
				}
			}
			if(pence > 0) {
				throw new NumberFormatException("not enough amount");
			}
		} catch(NumberFormatException nfe) {
			System.out.println(nfe.getLocalizedMessage());
		}
		return coinsList;
	}

	// TODO: Save back in the properties file the number of coins left
}
