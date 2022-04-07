/**
 * Jason Gordon
 * jagordon@brandeis.edu
 * 13 September 2021
 * PA0
 * Explanation of the program/class: This tests the Pet class.
 * Known Bugs: N/A
 * 
 */

package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.Pet;

class PetTest {

	Pet testDog = new Pet ("Test Dog", "Dog", 3);
	Pet testCat = new Pet ("Test Cat", "Cat", 7);
	Pet testGeneralPet = new Pet ("Test Pet", "Bird", 1);
	
	
	@Test
	void testPetInit() {
		assertEquals("Test Dog", testDog.getName());
		assertEquals("Dog", testDog.getSpecies());
		assertEquals(3, testDog.getAge());
	}
	
	@Test
	void testMakeSound() {
		assertEquals("bark!", testDog.makeSound());
		assertEquals("meow!", testCat.makeSound());
		assertEquals("squak!", testGeneralPet.makeSound());
	}
	
	@Test
	void testToString() {
		assertEquals("Test Dog\nSpecies: Dog\nAge: 3", testDog.toString());
	}

}
