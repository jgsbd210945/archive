//Jason Gordon
//PA1-Part 3
//Submitted 17 February 2021

//This code encodes a message using a Caesarian cypher.

import java.util.*;

public class PA1P3 {
	public static void main (String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.print("Your message? ");
		String a = scan.nextLine();
		System.out.print("Encoding key? ");
		int b = scan.nextInt();
		a = a.toUpperCase();		//Moves to uppercase, as the example does
		int l = a.length();
		String output = "";
		for(int i=0;i<l;i++){		//Going through the string and changing the character one at a time
			char c = a.charAt(i);
			c += b;
			if (c > 90){		//90 is where Z is on the ASCII table
				c-=26;		//Moving it to A if needed
			}
			output += c;
		}
		System.out.print("Your message: " + output);
	}
}