/**
 * Jason Gordon
 * jagordon@brandeis.edu
 * April 18 2021
 * PA6
 * Explanation of the class: This class is the abstract Car class RaceCar, FormulaOne, and SportsCar will draw from.
 * It lays out the base framework for the classes.
 * Known bugs: N/A
 */

package main;

public abstract class Car {
	int speed;				//These are the general types. Because getLocation is the only one that needs location,
	boolean isDamaged;		//we can remove location and getLocation from lower classes that have it.
	boolean isInPitstop;
	boolean isFinished;
	int strength;
	double location;
	int initialspeed;		//initialspeed and initialstrength are needed because we are calling them in toString
	int initialstrength;	//while the car is damaged.
	int pittimer;
	
	/**
	 * We will write this method in the superclass because it is the same for all methods. Simply, it returns the location.
	 */
	public double getLocation() {
		return location;
	}
	
	/**
	 * The other two methods are abstract because they're different for the subclasses and there's no need for
	 * them to have any code here.
	 */
	public abstract int finished();
	
	public abstract String toString();
}