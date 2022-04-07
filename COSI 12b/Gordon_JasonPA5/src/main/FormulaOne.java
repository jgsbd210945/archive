/**
 * Jason Gordon
 * jagordon@brandeis.edu
 * April 4 2021
 * PA5
 * Explanation of the class: This class manages objects of type FormulaOne and their attributes.
 * Known bugs: N/A
 */

package main;

public class FormulaOne {		//Initializes the four things we need in this class.
	int speed;
	int strength;
	int initialspeed;				//initialspeed and initialstrength are needed because we are calling them in toString, and
	int initialstrength;			//inispeed/inistrength won't go outside of the method FormulaOne.
	double location;				//Needed to return location
	int pittimer;

	public FormulaOne(int initialspeed, int initialstrength) {
		if (initialspeed>=70) {		//This tests for invalid inputs and corrects the initialspeed/initialstrength to the max or min.
			initialspeed=70;		//I re-assign the variable instead of using an else statement for both because it slightly condenses it.
		}else if  (initialspeed<=30){
			initialspeed=30;
		}
		speed=initialspeed;
		this.initialspeed=initialspeed;
		if (initialstrength>=5) {
			initialstrength=5;
		}else if  (initialstrength<=3){
			initialstrength=3;
		}
		strength=initialstrength;
		this.initialstrength=initialstrength;
	}
	
	public FormulaOne() {	//This method initialized FormulaOne as 50/4 as the default.
		speed=50;			//Because we know 50 and 4 are in the bounds, there is no need to test for an invalid input.
		initialspeed=50;
		strength=4;
		initialstrength=4;
	}

	public double getLocation() {
		return location;
	}

	public String toString() {	//This method constructs the string version of FormulaOne and returns it.
		String output="FormulaOne" + initialspeed + "/" + initialstrength;
		return output;
	}
}
