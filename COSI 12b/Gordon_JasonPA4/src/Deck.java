/**
 * Jason Gordon
 * jagordon@brandeis.edu
 * March 24, 2021
 * PA4
 * Explanation of the class:
 * This class creates the deck and does a few core functions with it.
 * First, we have a constructor, that makes an array of Cards with each card in it. This is then shuffled (using the shuffle method)
 * and used as the deck. An empty discard pile is also made.
 * The shuffle method shuffles the deck as per the modern version of the Fischer and Yates algorithm, randomizing the cards.
 * The drawNextCard method returns the next card, and it moves through the deck by using a while loop, ensuring null is not returned
 * and we do not get duplicate cards. It is also in charge of shuffling the discard pile once it is out of cards.
 * The discard method adds the card to the discard array.
 * 
 * Known bugs: none
 */

import java.util.*;

public class Deck {
	Card[] deck;
	Card[] discard;
	
	public Deck() { //Initializes a new deck. I more or less chose the suit order randomly, so I started with Hearts and ended with Spades.
		deck = new Card[52];
		for (int i=0; i<52; i++) { //Using a for loop to get all the cards.
			if (i<13) {
				deck[i] = new Card(i+1, "Hearts"); //I need to add one because otherwise it will  be 0-12, not 1-13.
			}else if (i<26) {
				deck[i] = new Card(i-12, "Diamonds");
			}else if (i<39) {
				deck[i] = new Card(i-25, "Clubs");
			}else {
				deck[i] = new Card(i-38, "Spades");
			}
		}
		shuffle();		//shuffles the deck
		discard = new Card[52];		//makes a discard pile
	}
	public void shuffle() {
		Random rand = new Random();
		for (int j=51; j>0; j--) {
			//While I could re-use i, using j makes it a bit clearer to me. j also cannot be 0 for using rand.nextInt.
			int k = rand.nextInt(j)+1;	//j is added because we use j, though it excludes the top number.
			Card temp = deck[j]; //Randomizes the cards, as per the modern version of the Fischer and Yates algorithm
			deck[j] = deck[k];
			deck[k] = temp;
		}
		//However, we still need to run it because deck[0] will always be the 1 of Hearts otherwise.
		int k = rand.nextInt(51);	//Switches with a random card in the deck
		Card temp = deck[0];
		deck[0] = deck[k];
		deck[k] = temp;
	}
	public Card drawNextCard() { //draws the next card, ensuring that it is not a null value
		int countup = 0;
		while (deck[countup] == null) {
			countup++;
			if (countup == 52) {	//If the deck is full of null values, then it is time to reshuffle the deck.
				deck=discard.clone(); //This block copies the discard deck, shuffles it, then clears the discard pile and counter.
				shuffle();
				//Theoretically, there should not be any null objects in the deck because there are two players, which will mean
				//all 52 cards will be used before starting the deck again.
				//Regardless, drawing cards will skip nulls.
				discard=new Card[52];
				countup=0;
			}
		}
		Card x = deck[countup];	//Saves the value so it doesn't return null
		deck[countup] = null;	//Makes the value null so as to ensure it's not drawn multiple times
		return x;
	}
	public void discard (Card c) {
		int count = 0;
		while (discard[count] != null) { //Ensures that we're not overwriting an already existent value
			count++;
		}
		discard[count] = c;
	}
}