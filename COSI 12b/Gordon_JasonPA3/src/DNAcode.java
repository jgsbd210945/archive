/**
 * Jason Gordon
 * jagordon@brandeis.edu
 * March 8, 2021
 * PA3
 * Explanation of the Program: This program takes a file that has names and nucleotide sequences and analyzes them.
 * It checks to see if they are a protein, the % of mass each nucleotide has, and the codons in it.
 * It then creates a new file (or prints over an existing file if it is given), where it prints this information.
 * Known Bugs: N/A
 */

import java.io.*;
import java.util.*;

public class DNAcode{
	public static void main (String[] args) throws FileNotFoundException {
		Scanner scan = new Scanner(System.in);
		System.out.println("This program reports information about DNA\nnucleotide sequences that may encode proteins.");
		System.out.print("Input file name? ");
		String infile=scan.next();
		System.out.print("Output file name? ");
		String outfile=scan.next();
		//The above six lines of code introduce the program, get the fine names, and set up the scanner.
		PrintStream out= new PrintStream(outfile);
		Scanner input = new Scanner (new File (infile));
		int mincodons = 5;
		double pctcg = 0.3;
		int nucltides = 4;
		int nuclpercodon = 3;
		double[] masses = {135.128, 111.103, 151.128, 125.107}; //provided
		//The above lines of code set up the variables needed.
		//The while loop below will read every two lines (the name and the DNA) and preform the proper processes on them.
		//It is the main workhorse of the code, as it calls all other functions.
		while (input.hasNextLine()) {
			String title = reader (input);
			String code = reader (input);
			String x = code.toUpperCase();
			int[] numbers = counts (x, nucltides);
			double totalmass = mass (x, masses);
			double[] pct = percentages (numbers, nucltides, masses, totalmass);
			String[] cdns = codons (x, nuclpercodon);
			String protein = isprotein (cdns, mincodons, pctcg, pct);
			printer (out, title, x, numbers, pct, totalmass, cdns, protein);
		}
		out.close();
		scan.close();
		//Closing the Scanner and PrintStream once the program is done working.
	}
	//The reader function is needed, for we are repeating a process and need it to return two different places.
	//This will be the only place we move the input along.
	public static String reader(Scanner input) {
		String line = input.nextLine();
		return line;
	}
	//The following function counts the numbers of As, Cs, Gs, and Ts in the DNA, adding them to an array of length nucltides.
	public static int[] counts (String x, int nucltides) {
		int[] numbers = new int[nucltides];
		for (int i=0; i<x.length(); i++) {
			if (x.charAt(i) == 'A') {
				numbers[0]++;
				//I increment them because each value will start at 0.
			}else if (x.charAt(i) == 'C') {
				numbers[1]++;
			}else if (x.charAt(i) == 'G') {
				numbers[2]++;	
			}else if (x.charAt(i) == 'T') {
				numbers[3]++;
			}
		}
		return numbers;
	}
	//The following function totals the mass (in g/mol) of the DNA we're given, dashes included.
	public static double mass (String x, double[] masses) {
		double totalmass = 0;
		for (int i=0; i<x.length(); i++) {
			if (x.charAt(i) == 'A') {
				totalmass+=masses[0];
			}else if (x.charAt(i) == 'C') {
				totalmass+=masses[1];
			}else if (x.charAt(i) == 'G') {
				totalmass+=masses[2];	
			}else if (x.charAt(i) == 'T') {
				totalmass+=masses[3];
			}else if (x.charAt(i) == '-') {
				totalmass+=100.0; //value provided
			}
		}
		totalmass = Math.round(totalmass*10);
		totalmass /= 10;
		return totalmass;
	}
	//The following function takes the percentages of A, C, G, and T in the DNA, using the results from the previous functions.
	public static double[] percentages (int[] numbers, int nucltides, double[] masses, double totalmass) {
		double[] pct= new double[nucltides];
		for (int i=0; i<nucltides; i++) {
			pct[i] = (numbers[i]*masses[i])/totalmass;
			pct[i] = Math.round(pct[i]*1000);
			pct[i] /= 10;
		}
		return pct;
	}
	//The following function gives us a list of the codons in the DNA.
	public static String[] codons (String x, int nuclpercodon) {
		String y = "";
		//Because x could have dashes in it, we need to remove them to get just the letters.
		//This is the only place where this is needed, for the dashes do not matter when we use x elsewhere.
		for (int i=0; i<x.length(); i++) {
			if (x.charAt(i) != '-') {
				y+=x.charAt(i);
				//Building a new string y to use in this case.
			}
		}
		String[] cdns = new String[y.length()/nuclpercodon];
		//I'm using nuclpercodon here because it will make it more flexible.
		//I need to use it throughout this for loop to ensure it will stay the length we want it to.
		for (int i=0; i<(y.length()/nuclpercodon); i++) {
			cdns[i]=y.substring((i*nuclpercodon), (((i+1)*nuclpercodon)));
		}
		return cdns;
	}
	//The following function will take the data we have and use it to determine if the DNA is for a protein.
	public static String isprotein (String[] cdns, int mincodons, double pctcg, double[] pct) {
		//Because I am using .equals, I need to have them all return True. Hence, I'll use a series of if statements.
		//If any of them return false, it will return "NO", which is at the end of the if statements.
		//The first test is if the codons length is long enough.
		if (cdns.length >= mincodons) {
		//Then, we use the .equals method to check to see if the first codon is correct.
			if (cdns[0].equals("ATG")) {
		//Then, we check the last codon, using || (or) so that if one of them is correct, we move to the final test.
				if (cdns[cdns.length-1].equals("TAA") || cdns[cdns.length-1].equals("TAG") || cdns[cdns.length-1].equals("TGA")) {
		//Checking if the percentage of C and G is high enough. These will always be the second and third spots on the pct array.
					if ((pct[1]+pct[2]) >= pctcg) {
						return "YES";
					}
				}
			}
		}
		return "NO";
	}
	//Finally, we have the print function, which will print this all to the output .txt file.
	public static void printer(PrintStream out, String title, String x, int[] numbers, double[] pct, double totalmass, String[] cdns, String protein) {
		out.println("Region Name: " + title);
		out.println("Nucleotides: " + x);
		out.println("Nuc. Counts: " + Arrays.toString(numbers));
		out.println("Total Mass%: " + Arrays.toString(pct) + " of " + totalmass);
		out.println("Codons List: " + Arrays.toString(cdns));
		out.println("Is Protein? " + protein);
		out.println();
	}
}