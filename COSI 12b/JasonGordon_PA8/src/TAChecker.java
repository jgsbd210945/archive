/**
 * Jason Gordon
 * jagordon@brandeis.edu
 * May 9 2021
 * PA8
 * Explanation of the class: This class is the main workhorse of the class. It will assign the files to a TARecord
 * and store them in a HashMap. Then, it will look at all cases and see if there is an unstarted job, a shortened job,
 * or a suspicious batch.
 * Known bugs:
 * - The JUnit test seems to not pick up on the output (not sure why), but if you run this program with the
 * proper text files it prints the correct output. I'm not sure why that happens.
 * - The Suspicious Batch code can only work if the person submitted one batch of two jobs and nothing else. If there
 * is no batch in the code, it works as intended.
 */

import java.util.*;
import java.io.*;

public class TAChecker {
	
	/**
	 * This method is the main method, it runs the program. It them references the sortWorkLog method
	 * to run itself.
	 */
	
	public static void main(String [] args) throws FileNotFoundException {	//Because I need the main to be static, I must have all the other methods be static.
		Map<String, TARecord> TAs = new HashMap<String, TARecord>();
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter a work log: ");
		String file=scan.next();
		Scanner input=new Scanner(new File(file));	//Allowing the scanner to read along the input
		sortWorkLog(input, TAs);	//Moves to SortWorkLog, where it will 
		checkValidity(TAs);
		scan.close();

	}
	
	/**
	 * This method sorts all the lines in the file to a HashMap to correlate the name of the TA with
	 * the TARecord.
	 */
	
	public static void sortWorkLog(Scanner input, Map <String, TARecord> TAs) {
		int linenum=0;
		while (input.hasNextLine()) {
			String ln=input.nextLine();
			linenum++;
			String[] lnsections=ln.split(";");
			String[] nums=lnsections[1].split(",");
			for (int i=0; i < nums.length; i++) {
				if (!(TAs.containsKey(lnsections[0]))) {
					TAs.put(lnsections[0], new TARecord (lnsections[0], nums[i], linenum));
				} else {
					TAs.get(lnsections[0]).addtoRecord(nums[i], linenum);
				}
			}	
		}
	}
	
	/**
	 * This method checks the validity of all issues, checking for unstarted jobs, shortened jobs,
	 * and suspicious batches.
	 */
	
	public static void checkValidity(Map <String, TARecord> TAs) {
		for (TARecord tar : TAs.values()) {	//Iterates through the map
			/**
			 * This first section checks for unstarted jobs. It will add one for every "START" and remove one
			 * for every job completed. If the counter is 0 and it reads a job completed, it will print that
			 * there was an unstarted job.
			 */
			int startcounter=0;
			for (int i=0; i<tar.values.size(); i++) {
				if (tar.values.get(i).charAt(0) == 'S') {
					startcounter++;
				} else {
					if (startcounter == 0) {
						System.out.println(tar.lines.get(i) + ";" + tar.name + ";UNSTARTED_JOB");
					} else {
						startcounter--;
					}
				}
			}
			/**
			 * This next section checks for both suspicious batches and shortened jobs.
			 * 
			 * For shortened jobs, has both maps side-by side and iterates through both, skipping when the names
			 * (and thus logs) are the same. To check for shortened jobs, it checks a few things:
			 * - Are both strings numbers? (That is, are both not "START"?)
			 * 		It checks the first character and redirects if one of them is START. It assigns the START line's
			 * 		to a variable, to be used if it finds a shortened job.
			 * - Are both strings' lengths the same?
			 * 		If they are not and one is longer than the other, then the longer one is larger. Since it
			 * 		iterates fully through both maps, it will only need to check one way and not the other.
			 * - If both strings' lengths are the same, are the values incorrect?
			 * 		To check, it iterates through each character and compares. If the latter character is smaller
			 * 		than the first character (starting at the front and going through the number), then it is
			 * 		a shortened job. If an earlier digit confirmed the latter was greater than the former, then
			 * 		it will disregard the string, making the boolean "firstdigitgreater" true.
			 * - Are the lines in the wrong order?
			 * 		To make sure the values are in the wrong order, we check the lines that the tasks started
			 * 		on to ensure that the job is shortened.
			 * - Is tar2start (the one that will be detected if shortened) 0?
			 * 		tar2start is the line, and if it's not assigned to a line (and 0, its starting number), then
			 * 		it is an unstarted job, not a shortened job.
			 * - Has it already been printed?
			 * 		This is to prevent double-printing when multiple characters in a multi-digit number are smaller
			 * 		(and the digits to that point are all smaller).
			 * If all of those return that that is a shortened job, then it will print if there's not a duplicate.
			 * 
			 * For checking suspicious batches/multiple characters in one line:
			 * At the beginning, it will affirm whether or not there is a batch job submission. For shortened jobs,
			 * it will do the same process but increment a duplicate counter instead of directly printing. It will
			 * also keep two lines rather than just one, flipping between the regular start line and the duplicate
			 * start line.
			 * At the end, it will determine how many jobs are shortened.
			 * - If it's 0, it will print nothing.
			 * - If it's 1, it will print that there is a suspicious batch on the line that the duplicate is at.
			 * - If it's 2, it will print both as shortened jobs, noting the respective lines.
			 */
			for (TARecord tar2: TAs.values()) {
				if (tar2.name != tar.name) {
					boolean tar2duplicate=false;
					int duplicateline=0;
					if (tar2.lines.size() > 2) {
						for (int b=1; b<tar2.lines.size(); b++) {
							if (tar2.lines.get(b-1) == tar2.lines.get(b)) {
								tar2duplicate=true;
								duplicateline=tar2.lines.get(b);
							}
						}
					}
					int tarstart=0;
					int tar2start=0;
					int tar2startduplicate=0;
					int duplicatecounter=0;
					boolean startduplicate=false;
					boolean firstdigitgreater=false;
					for (int i=0; i<tar.values.size(); i++) {
						for (int j=0; j<tar2.values.size(); j++) {
							if ((tar.values.get(i).charAt(0) != 'S') && (tar2.values.get(j).charAt(0) != 'S')) {
								if ((tar.values.get(i).length() > tar2.values.get(j).length()) && (tarstart - tar2start < 0) && (tar2start != 0)) {
									if (tar2duplicate) {
										duplicatecounter++;
									} else {
										System.out.println(tar2start + ";" + tar2.name + ";SHORTENED_JOB");
									}
								} else if (tar.values.get(i).length() == tar2.values.get(j).length()) {
									boolean printed=false;
									for (int k=0; k<tar.values.get(i).length(); k++) {
										if ((tar.values.get(i).charAt(k) - tar2.values.get(j).charAt(k) < 0)) {
											firstdigitgreater=true;
										} else if ((!(firstdigitgreater)) && (tarstart - tar2start < 0) && (tar2start != 0) && (!(printed))) {
											if (tar2duplicate) {
												duplicatecounter++;
											} else {
												System.out.println(tar2start + ";" + tar2.name + ";SHORTENED_JOB");
												printed=true;
											}
										}
									}
								}
							} else {
								if (tar.values.get(i).charAt(0) == 'S') {
									tarstart=tar.lines.get(i);
								}
								if (tar2.values.get(j).charAt(0) == 'S') {
									if (!(tar2duplicate) || (tar2start == 0)) {
										tar2start=tar2.lines.get(j);
									} else if (!(startduplicate)) {
										tar2startduplicate=tar2.lines.get(j);
										startduplicate=true;
									} else {
										tar2start=tar2.lines.get(j);
										startduplicate=false;
									}
								}
							}
						}
					}
					if (duplicatecounter == 1) {
						System.out.println(duplicateline + ";" + tar2.name + ";SUSPICIOUS_BATCH");
					} else if (duplicatecounter == 2) {
						if (startduplicate) {
							System.out.println(tar2start + ";" + tar2.name + ";SHORTENED_JOB");
							System.out.println(tar2startduplicate + ";" + tar2.name + ";SHORTENED_JOB");
						} else {
							System.out.println(tar2startduplicate + ";" + tar2.name + ";SHORTENED_JOB");
							System.out.println(tar2start + ";" + tar2.name + ";SHORTENED_JOB");
						}
					}
				}
			}
		}
	}
}
