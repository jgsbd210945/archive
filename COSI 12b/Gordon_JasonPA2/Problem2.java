/**
 * Jason Gordon
 * jagordon@brandeis.edu
 * February 26, 2021
 * PA2, Problem 2
 * This program plays a game of Hangman, where the user gives the length of the word and the word, and the computer attempts to guess it pseudorandomly
 * Known bug: I tried to eliminate duplicates (see below), but if you get it right it can duplicate it.
 */

import java.util.*;

public class Problem2{
	public static void main (String[] args){
		Scanner scan = new Scanner(System.in); //As usual, setting up Scanner and Random
		Random rand = new Random();
		System.out.println("This program plays a game of reverse hangman.\nYou think up a word (by typing it on the computer) and I'll try to guess\nthe letters.");
		System.out.println();
		System.out.print("How many letters are in your word? ");
		int len = scan.nextInt();
		System.out.print("Please enter the word for me to guess (letters only): ");
		String word = scan.next();
		int tries = 7;
		int correct = 0;	//Everything between lines 22 and 31 are setting up the necessary variables I'll need later and cannot define later.
		String guess = "";
		String saved = "";
		for (int i=0; i<len; i++) {
			guess+='-'; //Makes a dashed line of length len
		}
		int x = 0;
		char y = 'A';
		int testerval = 0;
		do{
			System.out.println(guess);
			System.out.println("+--+\n|  |");  //prints out the hangman figure. I attempted to use a function to do this because I need to do it later, but could not figure it out, unfortunately.
			if (tries <= 6) {
				System.out.println("|  O");
			}else {
				System.out.println("|");
			}
			if ((tries <= 5) && (tries >= 3)) {
				System.out.println("|  |"); //changes the hangman based on the amount of tries you have left
			}else if (tries == 2) {			//This will be what we determine and print between lines 35 and 55.
				System.out.println("|  |\\");
			}else if (tries <= 1) {
				System.out.println("| /|\\");
			}else {
				System.out.println("|");
			}
			if (tries == 4) {
				System.out.println("|   \\");
			}else if (tries <= 3) {
				System.out.println("| / \\");
			}else {
				System.out.println("|");
			}
			System.out.println("|\n+-----");
			System.out.println("");
			System.out.println("I've got " + correct + " of the " + len + " letters so far");
			if (saved.length() != 0) { //My attempt to eliminate duplicates, but the code breaks at the start when saved has a length of 0
				Boolean tester = false;
				while (tester != true){
					x = rand.nextInt(26); //runs the random and will run the random every time it pulls a character that was already done.
					y = 'A';
					y += x;
					for (int k=0; k<saved.length(); k++) {
						testerval = 0;
						if (y == saved.charAt(k)){
							testerval++;
						}
					}
					if (testerval == 0) {
						tester = true;
					}	
				}
			}else {
				x = rand.nextInt(26); //Running the random for the first try
				y = 'A';
				y += x;
			}
			System.out.println("I guess: " + y);
			saved += y;
			System.out.print("Is that letter correct? "); //seeing if the letter is correct.
			String checker = scan.next();
			if (checker.charAt(0) == 'y') {
				System.out.print("How many of that letter are in the word? "); //Adding the correct number to the correct counter
				int z = scan.nextInt();
				correct += z;
				y += 32;
				String temp = "";
				for (int j=0; j<len; j++) { //Interestingly, z is not needed here as the for loop goes through the whole word.
					if (guess.charAt(j) == '-') { //Here, I am building a new string with the letters because I cannot replace each - with the letter.
						if (y == word.toLowerCase().charAt(j)) {
							temp += y;
						}else {
							temp += '-';
						}
					}else {
						temp += guess.charAt(j);
					}
				}
				guess = temp; //setting the new string equal to the guess string
			}
			if (checker.charAt(0) == 'n') {
				tries--; //takes one away from tries
			}
		}while ((tries != 0) && (correct != len)); //I tried running this with || and found that that was a bad idea because it creates an infinite loop. && does not.
		System.out.println(guess);
		System.out.println("+--+\n|  |"); //printing it once more as the final, same code as lines 33-56.
		if (tries <= 6) {
			System.out.println("|  O");
		}else {
			System.out.println("|");
		}
		if ((tries <= 5) && (tries >= 3)) {
			System.out.println("|  |");
		}else if (tries == 2) {
			System.out.println("|  |\\");
		}else if (tries <= 1) {
			System.out.println("| /|\\");
		}else {
			System.out.println("|");
		}
		if (tries == 4) {
			System.out.println("|   \\");
		}else if (tries <= 3) {
			System.out.println("| / \\");
		}else {
			System.out.println("|");
		}
		System.out.println("|\n+-----");
		if (tries == 0) {
			System.out.println("You beat me this time");
		}else {
			System.out.println("Huzzah! I won!");
		}
		scan.close(); //closing the scanner to make Eclipse happy and to close the scanner.
	}
}