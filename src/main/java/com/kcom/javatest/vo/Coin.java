package com.kcom.javatest.vo;

/**
 * This class is used as a value object that represent a bank of a denomination of coin
 * which stores a number of coins of a certain denomination.
 */
public class Coin {
	// The Denomination of a coin.
	private int denomination;

	// The number of coins of the denomination
	private int numberOfCoins;

	/**
	 * Default Constructor
	 */
	public Coin() {}

	/**
	 * Argument Constructor
	 * @param denomination
	 * @param numberOfCoins
	 */
	public Coin(int denomination, int numberOfCoins) {
		this.denomination = denomination;
		this.numberOfCoins = numberOfCoins;
	}
	/**
	 * Getter
	 */
	public int getDenomination() {
		return denomination;
	}

	/**
	 * Setter
	 */
	public void setDenomination(int denomination) {
		this.denomination = denomination;
	}

	/**
	 * Getter
	 */
	public int getNumberOfCoins() {
		return numberOfCoins;
	}

	/**
	 * Setter
	 */
	public void setNumberOfCoins(int numberOfCoins) {
		this.numberOfCoins = numberOfCoins;
	}

	/**
	 * Overridden equals method
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		return ((Coin)obj).denomination == this.denomination && ((Coin) obj).numberOfCoins == this.numberOfCoins;
	}

	/**
	 * Overridden hashcode method
	 * @return
	 */
	@Override
	public int hashCode() {
		return denomination*numberOfCoins;
	}
}
