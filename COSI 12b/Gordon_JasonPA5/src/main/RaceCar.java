/**
 * Jason Gordon
 * jagordon@brandeis.edu
 * April 4 2021
 * PA5
 * Explanation of the class: This class manages the objects of RaceCar type and their attributes.
 * Known bugs: N/A
 */

package main;

public class RaceCar {	//Initializes the four things we need in this class.
		int speed;
		int strength;
		int initialspeed;		//initialspeed and initialstrength are needed because we are calling them in toString, and
		int initialstrength;	//the other initialspeed/initialstrength won't go outside of the method RaceCar.
		double location;		//Needed to return location
		int pittimer;			//For the time the car is in the pit stop

	public RaceCar(int initialspeed, int initialstrength) {
		if (initialspeed>=55) {		//This tests for invalid inputs and corrects the initialspeed/initialstrength to the max or min.
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
	}
	
	public RaceCar() {		//This method initialized RaceCar as 40/3 as the default.
		speed=40;			//Because we know 40 and 3 are in the bounds, there is no need to test for an invalid input.
		initialspeed=40;
		strength=3;
		initialstrength=3;
	}
	
	public double getLocation() {
		return location;
	}
	
	public String toString() {	//This method constructs the string version of RaceCar and returns it.
		String output="RaceCar" + initialspeed + "/" + initialstrength;
		return output;
	}
}
