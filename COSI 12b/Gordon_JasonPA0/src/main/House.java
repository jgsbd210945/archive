/**
 * Jason Gordon
 * jagordon@brandeis.edu
 * 13 September 2021
 * PA0
 * Explanation of the program/class: This initializes a house. It has rooms, price, whether or not pets are allowed, and
 * if it is occupied as initializers, the last one being added in order to ensure no house has two families occupying
 * it. It also can return itself as a string.
 * Known Bugs: N/A
 * 
 */

package main;

public class House {
	
	private int rooms;
	private int price;
	private boolean petsAllowed;
	private boolean isOccupied;
	
	/**
	 * This initializes the House object.
	 * @param rooms: The number of rooms in the house-how many people it can accomodate
	 * @param price: How much it costs-compares to budget
	 * @param petsAllowed: Whether or not pets are allowed
	 */
	public House(int rooms, int price, boolean petsAllowed) {
		this.rooms = rooms;
		this.price = price;
		this.petsAllowed = petsAllowed;
		isOccupied = false;
	}
	
	/**
	 * Returns the number of rooms
	 * @return the amount of rooms
	 */
	public int getRooms() {
		return rooms;
	}
	
	/**
	 * Returns the price of the house
	 * @return the price of the house
	 */
	public int getPrice() {
		return price;
	}
	
	/**
	 * Returns whether or not pets are allowed
	 * @return whether or not pets are allowed
	 */
	public boolean petsAllowed() {
		return petsAllowed;
	}
	
	/**
	 * Returns whether or not the house is occupied
	 * @return whether or not the house is occupied
	 */
	public boolean isOccupied() {
		return isOccupied;
	}
	
	/**
	 * Changes the status of the house to Occupied when the match is found.
	 */
	public void Occupy() {
		isOccupied=true;
	}
	
	/**
	 * Returns a string representation of the house.
	 */
	@Override
	public String toString() {
		String x = "No";
		if (petsAllowed) {
			x = "Yes";
		}
		return "Rooms: " + rooms + "\nPrice: " + price + "\nPets Allowed? " + x + "\n";
	}
}

