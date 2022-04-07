/**
 * Jason Gordon
 * jagordon@brandeis.edu
 * April 18 2021
 * PA6
 * Explanation of the class: This class manages the PitStop array. It will add and remove cars from the pitstop.
 * This class is exactly the same as in PA5, but changed to accommodate Car objects.
 * Known bugs: N/A
 */

package main;

public class PitStop {
	Car[] carinpitstop;

	public PitStop() {
		carinpitstop=new Car[10];		//I must make a constructor for this class or else PitStop will be null.
	}
	
	/**
	 * This makes a car "enter" the pitstop, putting it in the array.
	 */
	public void enterPitStop(Car car) {
		for (int i=0; i<carinpitstop.length; i++) {
			if (carinpitstop[i] == null) {	//Checks to insure I'm not overwriting a Car here.
				carinpitstop[i] = car;
			}
			car.isInPitstop=true;
		}
	}
	
	/**
	 * When checking for collisions, I need to check to make sure the car is not in the pitstop.
	 * This is how I do it.
	 */
	public boolean IsNotInPitStop(Car car) {
		if (car.isInPitstop) {
			return false;
		}else {
			return true;
		}
	}
	
	
	/**
	 * This method makes a car "exit" the pitstop.
	 * It basically reverses the enterPitStop method.
	 */
	public void exitPitStop(Car car) {
		for (int i=0; i<carinpitstop.length; i++) {
			if (carinpitstop[i] == car) {
				carinpitstop[i] = null;
			}
			car.isInPitstop=false;
		}
	}
}
