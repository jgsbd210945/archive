/**
 * Jason Gordon
 * jagordon@brandeis.edu
 * March 24, 2021
 * PA4
 * Explanation of the class:
 * The constructor here creates a card with an initial value and an initial suit. This works in tandem with the Deck class, which
 * does the initializing.
 * The method getValue returns the number value, the method getColor returns the color, and the method getSuit returns the suit.
 * The toString method returns the card as a string.
 * 
 * Known bugs: none
 */

public class Card {
	int value;				//The two parts of the Card class: value and suit
	String suit;
	
	public Card (int initialvalue, String initialsuit) { //Initializes a single card
		value = initialvalue;
		suit = initialsuit;
	}
	public int getValue() { //Returns the value of the card (A-K, aka 1-13)
		return value;
	}
	public String getColor() { //Returns the color of the card. Diamonds and Hearts (beginning with D and H, respectively) are red.
		if (suit.charAt(0) == 'D' || suit.charAt(0) == 'H') {
			return "Red";
		}else {
			return "Black";		//The other two suits are Clubs and Spades, and they are black.
		}
	}
	public String getSuit() {	//Returns the suit of the card
		return suit;
	}
	public String toString() { //Returns the string. Builds it off of the value and suit
		if (value == 11) {	//Because 1, 11, 12, and 13 have special names, we need separate statements for them.
			return "Jack of " + suit;
		}else if (value == 12) {
			return "Queen of " + suit;
		}else if (value == 13) {
			return "King of " + suit;
		}else if (value == 1) {
			return "Ace of " + suit;
		}else {
			return value + " of " + suit;
		}
	}
}