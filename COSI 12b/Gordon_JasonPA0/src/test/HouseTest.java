/**
 * Jason Gordon
 * jagordon@brandeis.edu
 * 13 September 2021
 * PA0
 * Explanation of the program/class: This tests the House class.
 * Known Bugs: N/A
 * 
 */

package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.House;

class HouseTest {

	House testHouse = new House(3, 50000, true);
			
	@Test
	void testHouseInit() {
		assertEquals(3, testHouse.getRooms());
		assertEquals(50000, testHouse.getPrice());
		assertEquals(true, testHouse.petsAllowed());
		assertEquals(false, testHouse.isOccupied());
	}
	
	@Test
	void testToString() {
		assertEquals("Rooms: 3\nPrice: 50000\nPets Allowed? Yes\n", testHouse.toString());
	}

}
