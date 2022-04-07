/**
 * Jason Gordon
 * jagordon@brandeis.edu
 * April 18 2021
 * PA6
 * Explanation of the class: This class manages the objects of FormulaOne type and their attributes.
 * This class is modified to extend Car and overwrite some of its methods for PA6.
 * It also adds a "finished" method.
 * Known bugs: N/A
 */

package main;

public class FormulaOne extends Car {	//Because we have all of the attributes of FormulaOne in Car, we do not need to put them here.
	
	/**
	 * This tests for invalid inputs and corrects the initialspeed/initialstrength to the max or min.
	 * It also instantiates the object with a specific speed and strength.
	 */
	public FormulaOne(int initialspeed, int initialstrength) {
		if (initialspeed>=70) {	
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
		isInPitstop=false;
		isFinished=false;
		isDamaged=false;
	}
	
	/**
	 * This method initializes FormulaOne as 50/4 as the default.
	 * Because we know 50 and 4 are in the bounds, there is no need to test for an invalid input.
	 */
	public FormulaOne() {
		speed=50;
		initialspeed=50;
		strength=4;
		initialstrength=4;
		isInPitstop=false;	//Parameters that were given in recitation.
		isFinished=false;
		isDamaged=false;
	}
	
	/**
	 * This method is overwriting the Car class and doing what is necessary to classify the car as finished.
	 * It also returns the proper score for this type of car.
	 */
	public int finished() {
		isFinished=true;
		return 100;
	}
	
	/**
	 * This method is overwriting the Car class and returning a string.
	 */
	public String toString() {
		String output="FormulaOne" + initialspeed + "/" + initialstrength;
		return output;
	}
}
