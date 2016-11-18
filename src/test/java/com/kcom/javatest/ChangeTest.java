package com.kcom.javatest;

import org.junit.Before;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * This class is used to test the Change class. This uses Theories and DataPoints to test the Change class
 */
@RunWith(Theories.class)
public class ChangeTest {

	private Change change;

	@Before
	public void setup() {
		change = new Change();
	}

	/**
	 * The datapoints to test the getOptimalChange functionality
	 */
	@DataPoints
	public static final ChangeTestData[] changeTestData = {
			new ChangeTestData(100, Arrays.asList(new Coin(100, 1))),
			new ChangeTestData(143, Arrays.asList(new Coin(100, 1),
					new Coin(20, 2),
					new Coin(2, 1),
					new Coin(1, 1))),
			new ChangeTestData(558, Arrays.asList(new Coin(100, 5),
					new Coin(50, 1),
					new Coin(5, 1),
					new Coin(2, 1),
					new Coin(1, 1))),
			new ChangeTestData(5961593, Arrays.asList(new Coin(100, 59615),
					new Coin(50, 1),
					new Coin(20, 2),
					new Coin(2, 1),
					new Coin(1, 1))),
			new ChangeTestData(999, Arrays.asList(new Coin(100, 9),
					new Coin(50, 1),
					new Coin(20, 2),
					new Coin(5, 1),
					new Coin(2, 2),
					new Coin(1, 1))),
			new ChangeTestData(0, new ArrayList<Coin>()),
			new ChangeTestData(-1, new ArrayList<Coin>())
	};

	/**
	 * The method that actually tests the getOptimalChange functionality. This is taking in the
	 * data from the datapoints object, both for input and output.
	 *
	 * @param changeTestData
	 */
	@Theory
	public void checkForOptimalChange(ChangeTestData changeTestData) {
		Collection<Coin> coinsList = change.getOptimalChange(changeTestData.pence);
		assertThat("The returned coin collection is not the same", isReturnedCoinCollectionCorrect(coinsList,
				changeTestData.coinsCollection), is(true));
	}

	/**
	 * This defines the data structure for the test data.
	 * pence = input
	 * coinsCollection = expected output to be compared.
	 */
	public final static class ChangeTestData {
		private int pence;
		private Collection<Coin> coinsCollection;

		protected ChangeTestData(int pence, Collection<Coin> coinsCollection) {
			this.pence = pence;
			this.coinsCollection = coinsCollection;
		}
	}

	/**
	 * Checks whether the two collections are equal or not.
	 *
	 * @param collectionA
	 * @param collectionB
	 * @return
	 */
	private boolean isReturnedCoinCollectionCorrect(Collection<Coin> collectionA, Collection<Coin> collectionB) {
		collectionA.removeAll(collectionB);
		return collectionA.isEmpty();
	}
}
