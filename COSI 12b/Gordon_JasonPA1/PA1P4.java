//Jason Gordon
//PA1-Part 4
//Submitted 17 February 2021

//This code changes a name into Pig Latin.

import java.util.*;

public class PA1P4 {
	public static void main (String[] args) {
		Scanner scan = new Scanner (System.in);
		System.out.print("First Name: ");
		String first = scan.next();
		System.out.print("Last Name: ");
		String last = scan.next();
		first = first.toLowerCase();
		last = last.toLowerCase();
		String f1 = first.substring (1, 2);	//single-character substrings instead of chars because I cannot use the .toUpperCase on chars
		String l1 = last.substring(1, 2);
		System.out.print(f1.toUpperCase() + first.substring(2) + first.charAt(0) + "ay " + l1.toUpperCase() + last.substring(2) + last.charAt(0) + "ay");
	}
}