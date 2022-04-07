//Jason Gordon
//PA1-Part 5
//Submitted 17 February 2021

//This code prints a number vertically without using strings to help.

import java.util.*;

public class PA1P5 {
	public static void main (String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.print("Please enter a positive integer: ");
		int x = scan.nextInt();
		while (x < 0){		//Making sure the user puts in a correct input
			System.out.print("Incorrect input. Try again.");
			System.out.print("Please enter a positive integer: ");
			x = scan.nextInt();
		}
		int y = x;	//Making a separate variable so as to use x later
		int n = 0;
		while (y >= 10){	//Counting digits
			y /= 10;
			n++;
		}
		while (n > 0){
			int a = 1;
			for(int i=0;i<n;i++){		//Making 10^n, but 10^n isn't 10 to the power of n in java.
				a *= 10;
			}
			int z = x/a;
			System.out.println(z);		//Printing a new line
			x -= z*a;			//Updating x
			n--;				//Decrementing n
		}
		System.out.print(x);
	}
}