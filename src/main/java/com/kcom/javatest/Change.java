package com.kcom.javatest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

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
	 * @param pence
	 * @return - the amount of change for the pence passed. This is a collection of the Coin class
	 */
	public Collection<Coin> getOptimalChange(int pence) {
		List<Coin> coinsList = new ArrayList<Coin>();

		for (int coinDenomination: denominations) {
			// if the number of pennies passed are 0 or -ve, or during the loop the amount becomes 0
			// then break out of the loop.
			if (pence <= 0) {
				break;
			}
			// process the entered pence and add to the coins collection
			int numberOfCoins = pence/coinDenomination;
			if (numberOfCoins > 0) {
				Coin coin = new Coin();
				coin.setDenomination(coinDenomination);
				coin.setNumberOfCoins(numberOfCoins);
				coinsList.add(coin);
				pence = pence%coinDenomination;
			}
		}
		return coinsList;
	}
}
