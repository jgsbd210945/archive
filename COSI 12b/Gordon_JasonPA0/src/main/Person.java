/**
 * Jason Gordon
 * jagordon@brandeis.edu
 * 13 September 2021
 * PA0
 * Explanation of the program/class: This class initializes a Person object and performs actions on it. It gives the
 * person a name, age, and salary, as well as give a string version of the person and allows the person to speak if needed
 * Known Bugs: N/A
 * 
 */

package main;

public class Person {
	
	private String name;
	private int age;
	private int salary;
	
	/**
	 * Creates a Person object with the given name, age, and salary
	 * @param name the name of the Person to be created
	 * @param age the age of the Person to be created
	 * @param salary the salary of the Person to be created
	 */
	public Person(String name, int age, int salary) {
		this.name=name;
		this.age=age;
		this.salary=salary;
	}
	
	/**
	 * returns the name of this Person 
	 * @return a String representing the name of this Person
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * returns the age of this Person
	 * @return an integer representing the age of this Person
	 */
	public int getAge() {
		return age;
	}

	/**
	 * returns the salary of this Person
	 * @return an integer representing the salary of this Person
	 */
	public int getSalary() {
		return salary;
	}
	
	/**
	 * returns the string said by this Person when they speak
	 * @return String representation of what this Person says
	 */
	public String speak() {
		if (age <=18) {
			return "I want a bigger house!";
		} else {
			return "This house does not have enough rooms to accommodate my family. I would like my family to be assigned to a house with more rooms.";
		}
	}
	
	/**
	 * returns a String representation of this Person, including...
	 * @return a String representation of this Person 
	 */
	@Override
	public String toString() {
		return name + "\nAge: " + age + "\nSalary: " + salary;
	}
}

