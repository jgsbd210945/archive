/**
 * Jason Gordon
 * jagordon@brandeis.edu
 * 13 September 2021
 * PA0
 * Explanation of the program/class: This tests the Family class.
 * Known Bugs: N/A
 * 
 */

package test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.Family;
import main.Person;
import main.Pet;

class FamilyTest {

	Family testFamily = new Family(2, 2);
	
	@Test
	void testFamilyInit() {
		assertEquals(0, testFamily.NumberOfHouses());
		assertEquals(2, testFamily.numberOfPeople());
		assertEquals(2, testFamily.numberOfPets());
		Person testPerson1 = new Person ("Test 1", 50, 50000);
		Person testPerson2 = new Person ("Test 2", 12, 2000);
		Pet testPet1 = new Pet ("Test 3", "Dog", 2);
		Pet testPet2 = new Pet ("Test 4", "Cat", 5);
		testFamily.addMember(testPerson1);
		testFamily.addMember(testPerson2);
		testFamily.addPet(testPet1);
		testFamily.addPet(testPet2);
		assertEquals(52000, testFamily.getBudget());
		testToString();		//Needed because we initialize the family here
	}
	
	void testToString() {
		assertEquals("People:\nTest 1\nAge: 50\nSalary: 50000\n\nTest 2\nAge: 12\nSalary: 2000\n\nPets:\nTest 3\nSpecies: Dog\nAge: 2\n\nTest 4\nSpecies: Cat\nAge: 5\n\n", testFamily.toString());
	}

}
