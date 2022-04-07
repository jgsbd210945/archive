/**
 * Jason Gordon
 * jagordon@brandeis.edu
 * 13 September 2021
 * PA0
 * Explanation of the program/class: This class initializes a family with both people and pets-both as two arrays of the
 * Person and Pet types. It also has the number of houses a family has in order to ensure the family only has one house.
 * Moreover, it also gives the budget of the family and can return as a string.
 * Known Bugs: N/A
 * 
 */

package main;

public class Family {
	
	private Person[] familyMembers;
	private Pet[] familyPets;
	private int NumberOfHouses;
	
	/**
	 * Family initializes the family, with two arrays for the people needed and setting the number of houses at 0.
	 * @param humans: the number of people in the family
	 * @param pets: the number of pets in the family
	 */
	public Family (int humans, int pets) {
		familyMembers = new Person[humans];
		familyPets = new Pet[pets];
		NumberOfHouses=0;
	}
	
	/**
	 * getPeople returns the members of the family.
	 * @return the Person array of the members in the family.
	 */
	public Person[] getPeople() {
		return familyMembers;
	}
	
	/**
	 * getPets returns the pets of the family
	 * @return the Pet array of the pets in the family.
	 */
	public Pet[] getPets() {
		return familyPets;
	}
	
	/**
	 * NumberOfHouses returns the number of houses the family has
	 * @return the amount of houses the family has.
	 */
	public int NumberOfHouses() {
		return NumberOfHouses;
	}
	
	/**
	 * This adds a house to the amount of houses a family has.
	 */
	public void AddHouse() {
		NumberOfHouses++;
	}
	
	/**
	 * getBudget adds all the salaries of the family and returns how much the total budget is
	 * @return the amount of money the family can spend on the house (salaries added together=budget)
	 */
	public int getBudget() {
		int x = 0;
		for (int i=0; i<familyMembers.length; i++) {
			x += familyMembers[i].getSalary();
		}
		return x;
	}
	
	/**
	 * This returns the number of people in a family.
	 * @return the length of the familyMembers array-also known as the number of people in the family.
	 */
	public int numberOfPeople() {
		return familyMembers.length;
	}
	
	/**
	 * This returns the number of pets in a family.
	 * @return the length of the familyPets array-also known as the number of pets in the family.
	 */
	public int numberOfPets() {
		return familyPets.length;
	}
	
	/**
	 * This adds a person to the family
	 * @param p: The person we want to add
	 * @return true/false: whether or not the addition of the person is successful.
	 */
	public boolean addMember (Person p) {
		for (int i=0; i<familyMembers.length; i++) {
			if (familyMembers[i] == null) {
				familyMembers[i] = p;
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * This adds a pet to the family
	 * @param p: The pet we want to add
	 * @return true/false: whether or not the addition of the pet is successful.
	 */
	public boolean addPet (Pet p) {
		for (int i=0; i<familyPets.length; i++) {
			if (familyPets[i] == null) {
				familyPets[i] = p;
				return true;
			}
		}
		return false;
	}
	
	/**
	 * This returns a string representation of the family.
	 * @return the string representation of the family.
	 */
	public String toString() {
		String ret = "People:\n";
		for (int i=0; i<familyMembers.length; i++) {
			ret += familyMembers[i].toString();
			ret += "\n\n";
		}
		if (familyPets.length > 0) {
			ret += "Pets:\n";
		}
		for (int i=0; i<familyPets.length; i++) {
			ret += familyPets[i].toString();
			ret += "\n\n";
		}
		return ret;
	}
	
	
}

