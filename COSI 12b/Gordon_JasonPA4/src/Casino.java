/**
 * Jason Gordon
 * jagordon@brandeis.edu
 * March 24, 2021
 * PA4
 * Explanation of the method:
 * This first starts off with introducing the game and asking for the total money. Then, it asks for a bet between that number and that number divided by 15.
 * After the user makes the correct bet, it runs the game by drawing the first two cards (one for the user and one for the system)
 * and comparing them. If the cards are equal in value, it tries again.
 * If the user's card is higher in value than the systems, it adds the bet to the user's total. If the opposite is true, it subtracts it.
 * This continues until the user is out of money or if the user says no to the "play again?" question.
 * 
 * Known bugs: none
 */

import java.util.*;

public class Casino {
	public static void main (String[] args) {
		Scanner scan = new Scanner (System.in);
		System.out.println("Welcome! This program plays the game War.");
		System.out.print("Please enter your total money: ");	//getting the total amount of money
		int total=scan.nextInt();
		System.out.println(" ");
		total=game(total, scan);
		if (total > 0) {
			System.out.println("Remaining money: " + total);
		}else {
			System.out.println("It seems you have ran out of money, so you cannot play anymore.");
		}
		System.out.print("Thanks for playing! Have a nice rest of your day.");
		scan.close();
	}
	
	public static int better (int total, Scanner scan) {	//This method obtains the bet.
		System.out.println("Betting minimum for this round will be: " + (total/15));
		System.out.println("Betting maximum for this round will be: " + total);
		System.out.print("Please make a whole number bet: ");
		int bet = scan.nextInt();
		System.out.println(" ");
		while (bet > total) {		//Ensuring that they don't bet too much or too little (both while loops)
			System.out.println("You have bet more than you have. Please try again.");
			System.out.print("Please make a whole number bet: ");
			bet = scan.nextInt();
			System.out.println(" ");
		}
		while (bet < (total/15) || bet <= 0) {
			System.out.println("You have bet under the betting minimum. Please try again.");
			System.out.print("Please make a whole number bet: ");
			bet = scan.nextInt();
			System.out.println(" ");
		}
		return bet;
	}
	public static int game (int total, Scanner scan) {	//This is the bulk of the program, and it plays the game.
		String confirm="Yes";
		Deck maindeck = new Deck();
		while (confirm.toUpperCase().charAt(0) == 'Y' && total > 0) {		//While loop to see if they want to continue playing or can continue playing
			int bet=better(total, scan);
			Card usercard = maindeck.drawNextCard();
			System.out.println("Your card: " + usercard.toString());
			Card systemcard = maindeck.drawNextCard();
			System.out.println("Dealer's card: " + systemcard.toString());
			while(usercard.getValue()==systemcard.getValue()) {
				System.out.println("Tie. Drawing again...");	//Ensuring all ties are resolved
				System.out.println(" ");
				usercard = maindeck.drawNextCard();
				System.out.println("Your card: " + usercard.toString());
				systemcard = maindeck.drawNextCard();
				System.out.println("Dealer's card: " + systemcard.toString());
			}
			if (usercard.getValue()>systemcard.getValue()) {
				System.out.println("You win! Adding bet to total...");
				total+=bet;
				System.out.println("New total: " + total);
				System.out.println(" ");
			}else {	//If it's not more, it is less. We eliminated all instances of ties in the while loop.
				System.out.println("You lose! Subtracting bet from total...");
				total-=bet;
				System.out.println("New total: " + total);
				System.out.println(" ");
			}
			maindeck.discard(usercard);
			maindeck.discard(systemcard);
			System.out.print("Would you like to play another round? ");
			confirm=scan.next();
			System.out.println(" ");
		}
		return total;
	}
}