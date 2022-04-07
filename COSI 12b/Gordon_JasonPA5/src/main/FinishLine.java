/**
 * Jason Gordon
 * jagordon@brandeis.edu
 * April 4 2021
 * PA5
 * Explanation of the class: This class is the finish line for the cars. It will also notify the do-while loop that it is
 * time to end the loop.
 * Known bugs: N/A
 */

package main;

public class FinishLine {
	RaceCar[] rcfinished;
	FormulaOne[] fofinished;
	
	public FinishLine() {
		rcfinished=new RaceCar[10];		//I must make a constructor for this class or else FinishLine will be null.
		fofinished=new FormulaOne[10];
	}
	
	public void enterFinishLine(RaceCar rc) {
		for (int i=0; i<rcfinished.length; i++) {
			if (rcfinished[i] == null) {	//Checks to ensure I'm not overwriting a RaceCar here.
				rcfinished[i] = rc;
			}
		}
	}
	
	public void enterFinishLine(FormulaOne fo) {
		for (int i=0; i<fofinished.length; i++) {
			if (fofinished[i] == null) {	//Checks to ensure I'm not overwriting a FormulaOne here.
				fofinished[i] = fo;
			}
		}
	}
	

	
	
	public boolean finished(RaceCar[] racecars, FormulaOne[] formulaOnes) {
		int counter=0;
		for (int i=0; i<racecars.length; i++) {
			if (racecars[i] != null) {	//This goes through both arrays and checks to make sure both arrays are null.
				counter++;				//If they're all null, the counter will be 0
			}							//Otherwise, the counter will be more than 0.
		}	
		for (int i=0; i<formulaOnes.length; i++) {	
			if (formulaOnes[i] != null) {
				counter++;
			}
		}
		if (counter==0){
			return true;
		}else {
			return false;
		}
	}
}

