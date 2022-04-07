/**
 * Jason Gordon
 * jagordon@brandeis.edu
 * April 4 2021
 * PA5
 * Explanation of the class: This class manages the PitStop array. It will add and remove cars from the pitstop.
 * Known bugs: N/A
 */

package main;	//This and finishLine are small and confined. Not much interaction

public class PitStop {
	RaceCar[] rcinpitstop;
	FormulaOne[] foinpitstop;

	public PitStop() {
		rcinpitstop=new RaceCar[10];		//I must make a constructor for this class or else PitStop will be null.
		foinpitstop=new FormulaOne[10];
	}
	
	public void enterPitStop(RaceCar rc) {
		for (int i=0; i<rcinpitstop.length; i++) {
			if (rcinpitstop[i] == null) {	//Checks to insure I'm not overwriting a RaceCar here.
				rcinpitstop[i] = rc;
			}
		}
	}
	
	public void enterPitStop(FormulaOne fo) {
		for (int i=0; i<foinpitstop.length; i++) {
			if (foinpitstop[i] == null) {	//Checks to insure I'm not overwriting a FormulaOne here.
				foinpitstop[i] = fo;
			}
		}
	}
	public boolean IsNotInPitStop(RaceCar rc) {	//When checking for collisions, I need to check to make sure the car is not in the pitstop
		for (int i=0; i<rcinpitstop.length; i++) {	//to avoid collisions at x75 between cars in the pitstop and cars not in the pitstop.
			if (rcinpitstop[i] != null && rcinpitstop[i] == rc) {
				return false;
			}
		}
		return true;
	}
	
	public boolean IsNotInPitStop(FormulaOne fo) {	//When checking for collisions, I need to check to make sure the car is not in the pitstop
		for (int i=0; i<foinpitstop.length; i++) {	//to avoid collisions at x75 between cars in the pitstop and cars not in the pitstop.
			if (foinpitstop[i] != null && foinpitstop[i] == fo) {
				return false;
			}
		}
		return true;
	}
	
	public void exitPitStop(RaceCar rc) { //These methods remove a car from a pitstop to reduce clutter.
		for (int i=0; i<rcinpitstop.length; i++) {		//They basically reverses the enterPitStop method.
			if (rcinpitstop[i] == rc) {
				rcinpitstop[i] = null;
			}
		}
	}
	public void exitPitStop(FormulaOne fo) {
		for (int i=0; i<foinpitstop.length; i++) {
			if (foinpitstop[i] == fo) {
				foinpitstop[i] = null;
			}
		}
	}
}
