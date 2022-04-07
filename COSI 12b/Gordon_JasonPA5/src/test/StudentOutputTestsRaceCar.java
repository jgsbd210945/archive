package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import main.*;
import static main.TrackLoggerB.*;

/**
 * This class will hold the JUnit test for the output on the PDF of the Need for Speed 
 * programming assignment (part A) that we will provide to the COSI 12b students.
 * 
 * @author cs12b
 * @version 2.0
 */
class StudentOutputTestsRaceCar {
	
	/**
	 * Testing utility that will be used to pass RaceCar initialization data into the RaceTrack using SimulationDriver.getSomeRaceCars() 
	 * @see RaceTrack#RaceTrack(RaceCar[])
	 * @see SimulationDriver#getSomeRaceCars()
	 */
	private static GenericConsoleTester tester = new GenericConsoleTester();
	
	/**
	 * The RaceTrack that will be used in these tests 
	 */
	private static RaceTrack track = null;
	
	/**
	 * The logger that will be used by track 
	 */
	private static TrackLoggerB logger = null;
	
	/**
	 * The RaceCars that will be used in these tests
	 */
	private static final RaceCar[] CARS = {
		new RaceCar(70, 3),
		new RaceCar(45, 2),
		new RaceCar(50, 1),
		new RaceCar(25, 5)
	};
	
	/**
	 * Sets up the testing utility 
	 */
	@BeforeAll
	static void setUp() { 
		tester.storeOldStreams();
	}
	
	/**
	 * Cleans up the testing utility 
	 */
	@AfterAll
	static void cleanUp() {
		tester.cleanUpStreamsAndFiles();
	}
	
	/**
	 * This method checks to see if the events that occurred at a certain tick matched the expected set of events. This method takes a variable
	 * String argument to be convenient when writing the tests (as seen in testPDFOutput() below). However, the order of events in the tick do not
	 * have to match the order provided in the variable string. 
	 * @param tick a given tick to check the output at 
	 * @param exp the expected output  
	 */
	private static void testTick(int tick, String...exp) {
		Set<String> actEntry = logger.getTickLog(tick); // get the events that occurred at 'tick' from the logger 
		if (exp[0].isEmpty()) { // if entry 0 is empty, that means tick should be empty 
			assertTrue(actEntry.isEmpty(), "There should not have been any events recorded at tick " + tick);
			return;
		}
		// convert expected from var String into set so order-independent check can be made 
		Set<String> expSet = new HashSet<String>();
		for (String event : exp) {
			expSet.add(event);
		}
		int expSize = expSet.size();
		// expected & actual should have the same number of entries  
		assertEquals(expSize, actEntry.size(), String.format("Number of events at tick %d [%d] did not match expected value of [%d].", tick, actEntry.size(), expSize));
		/* at this point, expected and actual have same size. retainAll() is a "1-way intersect" and the following will remove all events
		 * from actual that DO NOT occur in expected. 
		 * 
		 * if retainAll() => true, that means 2 things: 
		 * 1) a subset of actual was not supposed to have happened: otherwise those events would have been in expected and retainAll() wouldn't have
		 * removed them
		 * 2) only a subset of the expected events actually happened: retainAll() => true will result in actual being reduced in size to the subset
		 * of events that occur in both actual and expected  
		 * 
		 * if retainAll() => false, that means retainAll() did not find any events in actual that were not in expected, but more importantly,
		 * since expected and actual have the same size, that means each event in expected was found in actual 
		 */
		assertFalse(actEntry.retainAll(expSet), "Not all of the expected events occurred at tick " + tick + ".");
	}
	
	/**
	 * Convenience method that is used when there are long stretches of ticks where nothing may happen. This method checks to see if ticks in a
	 * certain interval [tickStart, tickEnd] all have nothing occur in them using testTick(). 
	 * @param tickStart start of the interval (note interval is inclusive) 
	 * @param tickEnd end of the interval (note interval is inclusive)
	 * @see #testTick(int, String...)
	 */
	private static void testEmptyTickRange(int tickStart, int tickEnd) {
		for (int i = tickStart; i <= tickEnd; i++) {
			testTick(i, "");
		}
	}
	
	/**
	 * Tests the students' program against the sample input / output shown on the PDF. 
	 */
	@Test
	void testExampleOutput() {
		// pass 4 cars into the track using the system in stream & using provided method in SimulationDriver 
		tester.setUpInputStream("4", "70 3", "45 2", "50 1", "25 5");
		track = new RaceTrack();
		track.setCars(SimulationDriver.getSomeRaceCars());
		// run the race & get logged events 
		track.run();
		logger = track.getLogger();
		// for convenience: here are the cars' array indices given their strength/speed from correct output
		// CARS 0 = 55/3
		// CARS 1 = 45/2
		// CARS 2 = 50/2
		// CARS 3 = 30/4
		testEmptyTickRange(1, 3);
		testTick(4, damagedStr(CARS[0]), damagedStr(CARS[3])); 
		testTick(5, "");
		testTick(6, enterPitStr(CARS[0]));
		testTick(7, damagedStr(CARS[2]));
		testTick(8, exitPitStr(CARS[0]), enterPitStr(CARS[2]), damagedStr(CARS[1]));
		testTick(9, enterPitStr(CARS[1]));
		testTick(10, exitPitStr(CARS[2]), enterPitStr(CARS[3]));
		testTick(11, exitPitStr(CARS[1]));
		testTick(12, exitPitStr(CARS[3]));
		testEmptyTickRange(13,15);
		testTick(16, damagedStr(CARS[2]), damagedStr(CARS[3]));
		testTick(17, "");
		testTick(18, enterPitStr(CARS[2]));
		testTick(19, "");
		testTick(20, exitPitStr(CARS[2]), damagedStr(CARS[1]), damagedStr(CARS[2]));
		testTick(21, finishedStr(CARS[0], 1), enterPitStr(CARS[3]));
		testTick(22, enterPitStr(CARS[1]), enterPitStr(CARS[2]));
		testTick(23, exitPitStr(CARS[3]));
		testTick(24, exitPitStr(CARS[1]), exitPitStr(CARS[2]));
		testTick(25, damagedStr(CARS[1]), damagedStr(CARS[3]));
		testTick(26, finishedStr(CARS[2], 2), enterPitStr(CARS[1]), enterPitStr(CARS[3]));
		testTick(27, "");
		testTick(28, exitPitStr(CARS[1]), finishedStr(CARS[1], 3), exitPitStr(CARS[3]));
		testEmptyTickRange(29,44);
		testTick(45, finishedStr(CARS[3], 4), scoreStr(700));
	}

	
}
