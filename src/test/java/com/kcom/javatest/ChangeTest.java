package com.kcom.javatest;

import com.kcom.javatest.exception.ChangeNotGivenException;
import com.kcom.javatest.utils.FileOperations;
import com.kcom.javatest.vo.Coin;
import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Properties;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

/**
 * This class is used to test the Change class. This uses Theories and DataPoints to test the Change class
 */
@RunWith(PowerMockRunner.class)
@PowerMockRunnerDelegate(Theories.class)
@PrepareForTest(FileOperations.class)
public class ChangeTest {

	private Change change;

	Properties properties;

	@Before
	public void setup() {
		change = new Change();
		properties = spy(new Properties());
	}

	/**
	 * This defines the data structure for the test data to get the change with unlimited coins
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
	 * This defines the data structure for the test data to test get the change with limiting the
	 * coins with properties file.
	 * pence = input
	 * inputProps = the input from the properties file
	 * coinsCollection = expected number of change
	 * outputProps = the output to be stored in the properties file
	 */
	public final static class ChangeTestDataWithProps {
		private int pence;
		private String[] inputProps;
		private Collection<Coin> coinsCollection;
		private String[] outputProps;

		protected ChangeTestDataWithProps(int pence, String[] inputProps,
		                                  Collection<Coin> coinsCollection, String[] outputProps) {
			this.pence = pence;
			this.inputProps = inputProps;
			this.coinsCollection = coinsCollection;
			this.outputProps = outputProps;
		}
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
	 * The datapoints to test the getChangeFor functionality
	 */
	@DataPoints
	public static final ChangeTestDataWithProps[] changeTestDataWithProps = {
			new ChangeTestDataWithProps(100, new String[] {"5", "5", "5", "5", "5", "5", "5"},
					Arrays.asList(new Coin(100, 1)), new String[] {"4", "5", "5", "5", "5", "5", "5"}),
			new ChangeTestDataWithProps(143, new String[] {"5", "5", "5", "5", "5", "5", "5"},
					Arrays.asList(new Coin(100, 1),
					new Coin(20, 2),
					new Coin(2, 1),
					new Coin(1, 1)), new String[] {"4", "5", "3", "5", "5", "4", "4"}),
			new ChangeTestDataWithProps(658, new String[] {"5", "5", "5", "5", "5", "5", "5"},
					Arrays.asList(new Coin(100, 5),
					new Coin(50, 1),
					new Coin(5, 1),
					new Coin(2, 1),
					new Coin(1, 1)), new String[] {"0", "2", "5", "5", "4", "4", "4"}),
			new ChangeTestDataWithProps(940, new String[] {"5", "5", "5", "5", "5", "5", "5"},
					Arrays.asList(new Coin(100, 5),
							new Coin(50, 5),
							new Coin(20, 5),
							new Coin(10, 5),
							new Coin(5, 5),
							new Coin(2, 5),
							new Coin(1, 5)), new String[] {"0", "0", "0", "0", "0", "0", "0"}),
			new ChangeTestDataWithProps(0, new String[] {"5", "5", "5", "5", "5", "5", "5"},
					new ArrayList<Coin>(), new String[] {"5", "5", "5", "5", "5", "5", "5"}),
			new ChangeTestDataWithProps(-1, new String[] {"5", "5", "5", "5", "5", "5", "5"},
					new ArrayList<Coin>(), new String[] {"5", "5", "5", "5", "5", "5", "5"})
	};

	/**
	 * The method that actually tests the getOptimalChange functionality. This is taking in the
	 * data from the datapoints object, both for input and output.
	 *
	 * @param changeTestData
	 */
	@Theory
	@Test
	public void checkForOptimalChangeToRunWithDataPoints(ChangeTestData changeTestData) throws ChangeNotGivenException {
		Collection<Coin> coinsList = change.getOptimalChange(changeTestData.pence);
		assertThat("The returned coin collection is not the same", isReturnedCoinCollectionCorrect(coinsList,
				changeTestData.coinsCollection), is(true));
	}

	/**
	 * The method that actually tests the getChangeFor functionality. This is taking in the
	 * data from the datapoints object, both for input and output.
	 * Mocking file operations here.
	 *
	 * @param changeTestDataWithProps
	 * @throws Exception
	 */
	@Theory
	@Test
	public void checkGetChangeForWithDatapoints(ChangeTestDataWithProps changeTestDataWithProps) throws Exception {

		Properties newProperties = new Properties();
		// Defining the expected output
		newProperties.setProperty("100", changeTestDataWithProps.outputProps[0]);
		newProperties.setProperty("50", changeTestDataWithProps.outputProps[1]);
		newProperties.setProperty("20", changeTestDataWithProps.outputProps[2]);
		newProperties.setProperty("10", changeTestDataWithProps.outputProps[3]);
		newProperties.setProperty("5", changeTestDataWithProps.outputProps[4]);
		newProperties.setProperty("2", changeTestDataWithProps.outputProps[5]);
		newProperties.setProperty("1", changeTestDataWithProps.outputProps[6]);

		// Mocking the Given, When and Then
		mockStatic(FileOperations.class);
		when(FileOperations.loadPropertiesFromPropertiesFile(anyString())).thenReturn(properties);
		when(FileOperations.savePropertiesToPropertiesFile(anyString(), eq(properties))).thenReturn(newProperties);
		when(properties.getProperty("100")).thenReturn(changeTestDataWithProps.inputProps[0]);
		when(properties.getProperty("50")).thenReturn(changeTestDataWithProps.inputProps[1]);
		when(properties.getProperty("20")).thenReturn(changeTestDataWithProps.inputProps[2]);
		when(properties.getProperty("10")).thenReturn(changeTestDataWithProps.inputProps[3]);
		when(properties.getProperty("5")).thenReturn(changeTestDataWithProps.inputProps[4]);
		when(properties.getProperty("2")).thenReturn(changeTestDataWithProps.inputProps[5]);
		when(properties.getProperty("1")).thenReturn(changeTestDataWithProps.inputProps[6]);

		// Executing the method
		Collection<Coin> coinCollection = change.getChangeFor(changeTestDataWithProps.pence, "any.properties");

		// Verifying whether the correct number of coins are deducted from the properties object (which will then be
		// written to the file.
		for(Coin coin: coinCollection) {
			assertThat(newProperties.getProperty(String.valueOf(coin.getDenomination())), is(
					String.valueOf(Integer.parseInt(properties.getProperty(String.valueOf(coin.getDenomination())))
							-coin.getNumberOfCoins())));
		}
	}

	/**
	 * Testing the expected exception functionality from the getChangeFor. The identified scenario is where there
	 * are less coins than are asked for in the change.
	 *
	 * @throws Exception
	 */
	@Test (expected = ChangeNotGivenException.class)
	public void checkChangeForPenceMoreThanAvailable() throws Exception {

		// Mocking classes and setting the When and Then
		mockStatic(FileOperations.class);
		when(FileOperations.loadPropertiesFromPropertiesFile(anyString())).thenReturn(properties);
		when(properties.getProperty("100")).thenReturn("5");
		when(properties.getProperty("50")).thenReturn("5");
		when(properties.getProperty("20")).thenReturn("5");
		when(properties.getProperty("10")).thenReturn("5");
		when(properties.getProperty("5")).thenReturn("5");
		when(properties.getProperty("2")).thenReturn("5");
		when(properties.getProperty("1")).thenReturn("5");

		// Executing the method where an exception is expected
		Collection<Coin> coinCollection = change.getChangeFor(941, "any.properties");
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
