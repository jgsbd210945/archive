//Jason Gordon
//PA1-Part 2
//Submitted 17 February 2021

//This code changes a number less than 5000 into Roman Numerals.

import java.util.*;

public class PA1P2 {
	public static void main (String[] args) {
		Scanner scan = new Scanner(System.in);
		System.out.print("Please enter a number no bigger than 4999: ");
		int x = scan.nextInt();
		while (x >= 5000){			//Ensuring a correct input
			System.out.println("Incorrect input. Please try again. ");
			System.out.print("Please enter a number no bigger than 4999: ");
			x = scan.nextInt();
		}
		while (x < 1){				//Ensuring a correct input
			System.out.println("Incorrect input. Please try again. ");
			System.out.print("Please enter a number no bigger than 4999: ");
			x = scan.nextInt();
		}
		int y = x;		//Making sure I have the beginning number to print at the end
		String end = "";
		while (x >= 1000){	//For 1000, 100, 10, and 1, I will use while statements, as they can occur more than once.
			end += "M";
			x -= 1000;
		}
		if (x >= 900){		//However, everything else can only happen once, so will only be necessary to use an if/else if statement
			end += "CM";
			x -= 900;
		}else if (x >= 500){	//I'll use if/else if here because it 900, 500, and 400 will never happen in occurance with each other.
			end += "D";
			x -= 500;
		}else if (x >= 400){
			end += "CD";
			x -= 400;
		}
		while (x >= 100){
			end += "C";
			x -= 100;
		}
		if (x >= 90){
			end += "XC";
			x -= 90;
		}else if (x >= 50){
			end += "L";
			x -= 50;
		}else if (x >= 40){
			end += "XL";
			x -= 40;
		}
		while (x >= 10){
			end += "X";
			x -= 10;
		}
		if (x == 9){
			end += "IX";
			x -= 9;
		}else if (x >= 5){
			end += "V";
			x -= 5;
		}else if (x == 4){
			end += "IV";
			x -= 4;
		}
		while (x >= 1){
			end += "I";
			x -= 1;
		}
		System.out.println(y + " in Roman Numerals is: " + end);
	}
}