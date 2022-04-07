/**
 * Jason Gordon
 * jagordon@brandeis.edu
 * April 18 2021
 * PA6
 * Explanation of the class: This class manages the objects of RaceCar type and their attributes.
 * This class is modified to extend Car and overwrite some of its methods for PA6.
 * It also adds a "finished" method.
 * Known bugs: N/A
 */

package main;

public class RaceCar extends Car {	//Because we have all of the attributes of RaceCar in Car, we do not need to put them here.

	
	/**
	 * This tests for invalid inputs and corrects the initialspeed/initialstrength to the max or min.
	 * It also instantiates the object with a specific speed and strength.
	 */
	public RaceCar(int initialspeed, int initialstrength) {
		if (initialspeed>=55) {
			initialspeed=55;		//I re-assign the variable instead of using an else statement for both because it slightly condenses it.
		}else if  (initialspeed<=30){
			initialspeed=30;
		}
		speed=initialspeed;
		this.initialspeed=initialspeed;
		if (initialstrength>=4) {
			initialstrength=4;
		}else if  (initialstrength<=2){
			initialstrength=2;
		}
		strength=initialstrength;
		this.initialstrength=initialstrength;
		isInPitstop=false;
		isFinished=false;
		isDamaged=false;
	}
	
	/**
	 * This method initializes RaceCar as 40/3 as the default.
	 * Because we know 40 and 3 are in the bounds, there is no need to test for an invalid input.
	 */
	public RaceCar() {
		speed=40;
		initialspeed=40;
		strength=3;
		initialstrength=3;
		isInPitstop=false;
		isFinished=false;
		isDamaged=false;
	}
	
	/**
	 * This method is overwriting the Car class and doing what is necessary to classify the car as finished.
	 * It also returns the proper score for this type of car.
	 */
	public int finished() {
		isFinished=true;
		return 150;
	}
	
	/**
	 * This method is overwriting the Car class and returning a string.
	 */
	public String toString() {
		String output="RaceCar" + initialspeed + "/" + initialstrength;
		return output;
	}
}
