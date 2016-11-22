package com.kcom.javatest;

import com.kcom.javatest.exception.ChangeNotGivenException;
import com.kcom.javatest.vo.Coin;

import java.util.Collection;

/**
 * This is the main class that is used to invoke the application. This takes in 1 or 2 command line params
 * where param 1 is the amount of pence that need to be changed in coins, second is an optional parameter
 * which is path to the properties file with the file name
 */
public class CoinChanger {
	public static void main(String[] args) {
		// Regex to identify the inputs
		String penceRegex = "\\d+";
		// Filename regex. this takes care of both Windows style and unix style paths and both absolute and
		// relative paths
		String fileRegex = "(^([a-zA-Z]://){0,1}(.*/)*([^/]*)$|^[a-zA-Z]:\\\\(((?![<>:\"/\\\\|?*]).)+((?<![ .])\\\\)?)*$)";

		Change change = new Change();
		Collection<Coin> coins = null;
		try {
			// identify the number of input and validate, depending of which we call the get change method
			if (args.length == 1 && args[0].matches(penceRegex)) {
				coins = change.getOptimalChange(Integer.parseInt(args[0]));
			} else if (args.length == 2 && args[0].matches(penceRegex) && args[1].matches(fileRegex)) {
				coins = change.getChangeFor(Integer.parseInt(args[0]), args[1]);
			} else {
				// for invalid input, show the usage
				System.out.println("Usage: CoinChanger <pence as integer value> [<coin properties file name>]");
				System.out.println("example: CoinChanger 135");
				System.out.println("or: CoinChanger 135 coins.properties");
			}
		} catch (ChangeNotGivenException ce) {
			System.out.println("Can't change coins: " + ce.getLocalizedMessage());
		}

		// Display the coins exchanged for the pence
		if(coins != null) {
			for (Coin coin : coins) {
				System.out.println(coin.getDenomination() + ": " + coin.getNumberOfCoins());
			}
		}
	}
}
