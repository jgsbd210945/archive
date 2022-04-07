/**
 * Jason Gordon
 * jagordon@brandeis.edu
 * February 26, 2021
 * PA2, Problem 1
 * This problem plays a number guessing game with the user. To change the max (set at 100), just change the variable's value at line 22.
 */

import java.util.*; //I would also use import java.io.*, but Eclipse didn't let me, as it said it was unnecessary.

public class Problem1{
	public static void main (String[] args){
		Scanner scan = new Scanner(System.in);
		Random rand = new Random(); //setting up Random and Scanner
		System.out.println("This program allows you to play a guessing game.\nThink of a number between 1 and 100\nand I will guess until I get it.\nFor each guess, tell me if the\nright answer is higher or lower than your guess, or if it is correct.");
		String cont = "Yes"; //To start the while loop
		int totalgames = 0;
		int totalguesses = 0; //total stats
		int maxguesses = 0;
		while (cont.toLowerCase().charAt(0) == 'y'){
			totalgames++;
			int max = 100; //max that it can go
			int adder = 0;
			System.out.println("");
			System.out.println("Think of a number...");
			String guess = "higher"; //again, starting the while loop
			int n = 0;
			int x = 0;
			int y = 0; //setting values to x, y, and temp, as I need to compare them early on.
			int temp = 0;
			while (guess.charAt(0) != 'c'){
				n++;
				while (x == temp){ //preventing duplicate answers
					y = rand.nextInt(max) + 1; //I need to do this now, as adding 1 to adder would provide incorrect bounds.
					x = y + adder;
				}
				System.out.println("My guess: " + x);
				guess = scan.next();
				temp = x;
				if (guess.charAt(0) == 'h'){ //A way to check for higher/lower/correct
					max -= y;
					adder += y;	//Bringing down the max because it can only guess from 0 to x. To offset this, I add the same value to the adder.
				}
				if (guess.charAt(0) == 'l'){
					max = y; //If it needs to be lower, I just need to set the value at the upper bound.
				}
			}
			totalguesses += n;
			if (n > maxguesses){
				maxguesses = n; //Finding the max and getting all of the end-of-game stats.
			}
			System.out.println("I got it right in " + n + " guesses");
			System.out.println("");
			System.out.println("Do you want to play again? ");
			cont = scan.next();
		}
		System.out.println("");
		System.out.println("Overall results:");
		System.out.println("\ttotal games\t= " + totalgames);
		System.out.println("\ttotal guesses\t= " + totalguesses);
		System.out.println("\tguesses/game\t= " + (totalguesses/totalgames));
		System.out.println("\tmax guesses\t= " + maxguesses);
		scan.close(); //closing the scanner, as again, Eclipse made me do it
	}
}