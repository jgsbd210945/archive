/**
 * Jason Gordon
 * jagordon@brandeis.edu
 * 13 September 2021
 * PA0
 * Explanation of the program/class: This initializes the Pet class-it is very similar to the Person class. It gives
 * the pet its attributes, has a toString method, and allows it to speak.
 * Known Bugs: N/A
 * 
 */

package main;

public class Pet {
	
	private String name;
	private String species;
	private int age;
	
	/**
	 * This initializes the Pet object.
	 * @param name: Name of the pet
	 * @param species: Species of the pet
	 * @param age: Age of the pet
	 */
	public Pet(String name, String species, int age) {
		this.name=name;
		this.age=age;
		this.species=species;
	}
	
	/**
	 * Returns the name of the pet.
	 * @return the name of the pet
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Returns the species of the pet.
	 * @return the species of the pet
	 */
	public String getSpecies() {
		return species;
	}
	
	/**
	 * Returns the age of the pet.
	 * @return the age of the pet
	 */
	public int getAge() {
		return age;
	}
	
	/**
	 * Makes a sound if the house does not allow pets but it is chosen for the family.
	 * @return the sound of each pet
	 */
	public String makeSound() {
		if (species == "Cat") {
			return "meow!";
		} else if (species == "Dog") {
			return "bark!";
		} else {
			return "squak!";
		}
	}
	
	/**
	 * Returns the string representation of the pet.
	 */
	@Override
	public String toString() {
		return name + "\nSpecies: " + species + "\nAge: " + age;
	}
}

