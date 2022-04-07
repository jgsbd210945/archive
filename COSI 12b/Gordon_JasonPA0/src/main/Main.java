/**
 * Jason Gordon
 * jagordon@brandeis.edu
 * 13 September 2021
 * PA0
 * Explanation of the program/class: This is the main workhorse of the package. It reads both files and puts everything
 * in arrays, and then assigns families to houses, prints them, and checks to make sure the assignments are correct.
 * Known Bugs: Family #3 does not get a house-though from the comparison of the two files, that is to be expected.
 * 
 */

package main;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Main {
	
	public static Family[] f;
	public static House[] h;
	public static boolean[][] assignments;

	/**
	 * This is the hub of the program-it calls the other methods in Main as well as reads the file.
	 * @param args: General notation for main methods.
	 * @throws FileNotFoundException: Throws if there is no file of that name.
	 */
	public static void main(String[] args) throws FileNotFoundException {
		Scanner s = new Scanner(new File("familyUnits.txt"));
		int len=s.nextInt();
		f = new Family[len];	//initializing the family array.
		while (s.hasNextLine()) {
			createFamilies(s);
		}
		s = new Scanner(new File("housingUnits.txt")); //switching files
		int houselen = s.nextInt();
		h = new House[houselen];	//initializing the house array.
		while (s.hasNextLine()) {
			createHomes(s);
		}
		assignments=new boolean[len][houselen];
		assignFamiliesToHomes();
		displayAssignments();
		for (int i=0; i<f.length; i++) {
			checkAssignment(i);
		}
		s.close();
	}
	
	/**
	 * Creates the families from the file given.
	 * @param s: The Scanner of the file.
	 */
	public static void createFamilies(Scanner s) {
		boolean alreadydone = false;
		for (int i=0; i<f.length; i++) {
			if (!(alreadydone) && f[i] == null) { 	//Ensures we don't have it replacing a different family and so that it does not duplicate.
				int humans=s.nextInt();
				int pets=s.nextInt();
				f[i] = new Family (humans, pets);
				for (int j=0; j<humans; j++) {
					String name=s.next();
					int age = s.nextInt();
					int salary = s.nextInt();
					f[i].addMember(new Person(name, age, salary));
				}
				for (int k=0; k<pets; k++) {
					String petname=s.next();
					String petspecies = s.next();
					int petage = s.nextInt();
					f[i].addPet(new Pet(petname, petspecies, petage));
				}
				alreadydone=true;
			}
		}
	}
	
	/**
	 * Creates the homes from the file given.
	 * @param s: reads the file.
	 */
	public static void createHomes(Scanner s) {
		boolean alreadydone = false;
		for (int i=0; i<h.length; i++) {
			if (!(alreadydone) && h[i] == null) {	//Ensures we don't have it replacing a different home and so that it does not duplicate.
				int rooms=s.nextInt();
				int price=s.nextInt();
				boolean petsallowed=s.nextBoolean();
				h[i] = new House (rooms, price, petsallowed);
				alreadydone=true;
			}
		}
	}
	
	/**
	 * The main logic in this class: it assignes families to homes in the 2-D array assignments[][].
	 */
	public static void assignFamiliesToHomes() {
		for (int i=0; i<f.length; i++) {
			for (int j=0; j<h.length; j++) {
				if ((f[i].NumberOfHouses() == 0) && (f[i].getBudget() >= h[j].getPrice()) && (f[i].getPeople().length <= h[j].getRooms()) && ((f[i].getPets().length == 0) || (h[j].petsAllowed())) && !(h[j].isOccupied())) {
					//Will only occupy if they do not have a house, they have the budget needed, they have enough rooms, they do not have pets or the house allows pets, and if it is not already occupied.
					assignments[i][j]=true;
					h[j].Occupy();
					f[i].AddHouse();
				}
			}
		}
	}
	
	/**
	 * Prints the assignments or prints if they do not have a house.
	 */
	public static void displayAssignments() {
		for (int i=0; i<f.length; i++) {
			System.out.print("Family #" + (i+1) + ":\n" + f[i].toString());	//Prints the family information.
			if (f[i].NumberOfHouses() == 0) {	//Checks whether or not the person is assigned to a home.
				System.out.print("Not Assigned To Home\n\n");
			} else {	//The else is not really needed here, but it cuts out the for loot, helping runtime.
				for (int j=0; j<h.length; j++) {
					if (assignments[i][j]) {
						System.out.print("House for Family #" + (i+1) + ":\n" + h[j].toString() + "\n");
					}
				}
			}
		}
	}
	
	/**
	 * Checks the assignments to ensure all assignments are correct.
	 * @param familyIndex: The array placement of the family.
	 */
	public static void checkAssignment(int familyIndex) {
		System.out.println("Check for Family #" + (familyIndex + 1) + ":");
		boolean trigger = true;			//Trigger is important to say that the family has no issues.
		if (f[familyIndex].NumberOfHouses() == 0) {	//Test #1-if they do not have a house.
			System.out.println("Family does not have a house.");
			trigger = false;
		} else if (f[familyIndex].NumberOfHouses() > 1) {	//Test #2-If they have too many houses. I put it as an
			System.out.println("Family assigned to more than one house.");	//else-if in order to be concise.
			trigger = false;
		}
		for (int j=0; j<h.length; j++) {
			if (assignments[familyIndex][j]) {
				if (f[familyIndex].getBudget() <= h[j].getPrice()) {	//Test #3-If their house is too expensive.
					System.out.println("House over budget.");
					trigger = false;
				}
				if (f[familyIndex].getPeople().length > h[j].getRooms()) {	//Test #4-If the house has too few rooms.
					for (int k=0; k<f[familyIndex].getPets().length; k++) {
						f[familyIndex].getPeople()[k].speak();
					}
					trigger = false;
				}
				if ((f[familyIndex].getPets().length > 0) && !(h[j].petsAllowed())) {	//Test #5-If the house does not allow pets and the family has pets.
					for (int k=0; k<f[familyIndex].getPets().length; k++) {
						f[familyIndex].getPets()[k].makeSound();
						trigger = false;
					}
				}
			}
		}
		if (trigger) {
			System.out.println("No issue for this family.");
		}
	}
}

