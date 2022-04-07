//Jason Gordon
//PA1-Part 1
//Submitted 17 February 2021

//This code plays a number game, where the player is to input a number.
//Then, the code multiplies it and increments it if odd and divides it by two if even.
//This continues until x is one, and it prints how many iterations it takes.

import java.util.*;

public class PA1P1 {
	public static void main (String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.print("Initial value is: ");
		int x = scan.nextInt();
		int n = 0;
		if (x > 1) {
			while (x != 1) {
				if (x % 2 == 1) {
					x *= 3;
					x += 1;
				}
				else {
					x /= 2;
				}
				n++;
				if (x != 1) {		//This checks to ensure it's not 1 after the changes. I could use a fencepost algorithm here.
					System.out.println("Next value is: " + x);
				}
			}
			System.out.println("Final value " + x + ", number of operations performed " + n);
		}
		else {
			System.out.println("Error");	//In case I have a number less than 1
		}
	}
}