/**
 * Jason Gordon
 * jagordon@brandeis.edu
 * April 18 2021
 * PA6
 * Explanation of the class: This class is the finish line for the cars. It will also notify the do-while loop that it is
 * time to end the loop. This was in PA5.
 * Changes since PA5: Modified the class to have one array of type Car.
 * Known bugs: N/A
 */

package main;

public class FinishLine {
	Car[] carfinished;
	
/**
 * This instantiates the class.
 */
	public FinishLine() {
		carfinished=new Car[10];		//I must make a constructor for this class or else FinishLine will be null.
	}
	
	/**
	 * This moves the racecar to the finish line.
	 */
	public void enterFinishLine(Car car) {
		for (int i=0; i<carfinished.length; i++) {
			if (carfinished[i] == null) {	//Checks to ensure I'm not overwriting a RaceCar here.
				carfinished[i] = car;
			}
			
		}
	}

	
	/**
	 * This is more or less the most important method in the class. It will return true if all the cars are finished
	 * or not.
	 */
	public boolean finished(Car[] cars) {
		int counter=0;
		for (int i=0; i<cars.length; i++) {
			if (cars[i] != null) {
				if (!(cars[i].isFinished)) {	//This goes through the array and checks to see if the cars are finished.
					counter++;				//If they're all finished, the counter will be 0
				}							//Otherwise, the counter will be more than 0.
			}
		}
		if (counter==0){
			return true;
		}else {
			return false;
		}
	}
}

