/**
 * Jason Gordon
 * jagordon@brandeis.edu
 * April 18 2021
 * PA6
 * Explanation of the class: This class manages the objects of SportsCar type and their attributes.
 * It acts as a subclass of Car as well.
 * Known bugs: N/A
 */

package main;

public class SportsCar extends Car {	//Because we have all of the attributes of SportsCar in Car, we do not need to put them here.
	
	/**
	 * This tests for invalid inputs and corrects the initialspeed/initialstrength to the max or min.
	 * It also instantiates the object with a specific speed and strength.
	 */	
	public SportsCar(int initialspeed, int initialstrength) {
		if (initialspeed>=45) {	
			initialspeed=45;		//I re-assign the variable instead of using an else statement for both because it slightly condenses it.
		}else if  (initialspeed<=20){
			initialspeed=20;
		}
		speed=initialspeed;
		this.initialspeed=initialspeed;
		if (initialstrength>=3) {
			initialstrength=3;
		}else if  (initialstrength<=1){
			initialstrength=1;
		}
		strength=initialstrength;
		this.initialstrength=initialstrength;
		isInPitstop=false;
		isFinished=false;
		isDamaged=false;
	}
	
	/**
	 * This method initializes SportsCar as 30/2 as the default.
	 * Because we know 30 and 2 are in the bounds, there is no need to test for an invalid input.
	 */
	public SportsCar() {
		speed=30;
		initialspeed=30;
		strength=2;
		initialstrength=2;
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
		return 200;
	}
	
	/**
	 * This method is overwriting the Car class and returning a string.
	 */
	public String toString() {
		String output="SportsCar" + initialspeed + "/" + initialstrength;
		return output;
	}
	
}