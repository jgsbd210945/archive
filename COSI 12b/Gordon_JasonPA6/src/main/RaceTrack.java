/**
 * Jason Gordon
 * jagordon@brandeis.edu
 * April 18 2021
 * PA6
 * Explanation of the class: This class is the "hub" of the assignment. It will call the other classes if need be and
 * send the results to the track logger. It is also where the simulation is run.
 * This class is updated for PA6 and has fixed the bugs I got in PA5.
 * 
 * Known bugs: This might be where it throws, but when testing, it double-prints all events sorted by tick in brackets
 * once the whole race is over.
 * For instance, it prints this:
 * "[RaceCar30/3 has been damaged., FormulaOne60/5 has been damaged.]
 * [RaceCar30/3 has been damaged., FormulaOne60/5 has been damaged.]"
 * right after the race score is printed. It does the same for subsequent events.
 * However, it passes the JUnit test.
 */


package main;

public class RaceTrack {
	Car[] cars=new Car[10];		//I am initializing racecars and formulaOnes here so as to not call them later.
	PitStop pitstop=new PitStop();
	FinishLine finishline=new FinishLine();
	int placement;
	int score;
	
	/**
	 * DO NOT REMOVE THIS - you should be using this to log this track's events. For more see the assignment PDF / documentation for TrackLoggerB.java
	 */
	private TrackLoggerC logger;
	
	public RaceTrack() {
		logger = new TrackLoggerC(); // DO NOT REMOVE THIS LINE
	}
	
	/**
	 * setCars initializes the cars in the race.
	 * We can assume there will not be more than 10 cars per race.
	 * We must also run the for loop in order to initialize all the cars,
	 * for arrays are reference types.
	 */
	public void setCars(Car[] cars) {
		for (int i=0; i<cars.length; i++) {
			this.cars[i] = cars[i];
		}
	}
	
	/**
	 * This method will run one tick.
	 * It does so by moving every car that is not finished and are not in the pitstop up the amount of speed they have.
	 * Then, it checks to see if any damaged cars have passed the pitstop and puts them into the pitstop.
	 * For the cars that are already in the pitstop, it decreases the pitstop timer.
	 * Once that pitstop timer is zero, it immediately removes them from the pitstop and moves them.
	 * If a car finishes, it finishes the car and changes its "isFinished" characteristic to true.
	 */
	public void tick() {
		for (int i=0; i<cars.length; i++) {
			if ((cars[i] != null) && (!(cars[i].isFinished))) { //null checker
				if (cars[i].pittimer == 0) {
					cars[i].location+=cars[i].speed;	//moves the car
					if ((cars[i].speed != cars[i].initialspeed) && (((cars[i].getLocation() % 100) >= 75) || (((cars[i].getLocation() % 100) < ((75 + cars[i].speed) % 100))) && (cars[i].speed >25))) {
					//The above checks to see if the car can enter the pitstop (must be damaged and pass the pitstop at that time)
						logger.logEnterPit(cars[i]);
						cars[i].pittimer+=2;	//Initializing the pitstop timer.
						pitstop.enterPitStop (cars[i]);
						cars[i].location-=((cars[i].getLocation()-75) % 100); //changes the location to x75.
					}
				}else {
					cars[i].pittimer--;	//If the car is in the pitstop, it will decrease its timer
					if (cars[i].pittimer == 0) {	//If the pittimer is 0, it is in the next round
						logger.logExitPit(cars[i]);
						pitstop.exitPitStop(cars[i]);
						cars[i].isDamaged=false;
						cars[i].speed = cars[i].initialspeed;
						cars[i].location+=cars[i].speed;	//If I don't move them immediately, a crash will happen as they leave the pit stop.
					}
				}
				if (cars[i].getLocation() >= 1000) {	//finish sequence
					finishline.enterFinishLine(cars[i]);
					placement++;
					score+=cars[i].finished();
					logger.logFinish(cars[i], placement);
				}
			}
		}
		checkCollision();
	}
	
	/**
	 * This method checks for collisions and does the necessary adjustments for it.
	 * It does this by comparing the array against itself.
	 * If it is not itself and the locations are the same (% 100 because it will be the same regardless of lap)
	 * and neither are in the pit stop or are finished, it will "damage" the car.
	 */
	public void checkCollision() {
		for (int i=0; i<cars.length; i++) {		//Because we are not allowed to use inheritance, we must go through both arrays, which takes 4 for loops to do.
			if (cars[i] != null) {				//The outside for loop will run through once, and the inside loop will run for the amount of times the outside loop runs, comparing each value to the outside loop 
				for (int j=0; j<cars.length; j++) {		//the i != j statement is to catch it comparing it to itself
					if (cars [j] != null) {				//We also make sure to filter for nulls.
						if (i != j && ((cars[i].getLocation() % 100) == (cars[j].getLocation() % 100)) && pitstop.IsNotInPitStop(cars[i]) && pitstop.IsNotInPitStop(cars[j]) && !(cars[i].isFinished) && !(cars[j].isFinished)) {
							if (!(cars[i].isDamaged)) { 	//However, we must check if the car is damaged, for damage is not stacking.
								cars[i].speed-=cars[i].strength * 5;
								cars[i].isDamaged=true;
								logger.logDamaged(cars[i]);
							}
							if (!(cars[j].isDamaged)) {
								cars[j].speed-=cars[j].strength * 5;
								cars[j].isDamaged=true;
								logger.logDamaged(cars[j]);
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * This method runs the ticks and determines if it's finished.
	 * Once the game is finished, it will calculate the score.
	 */
	public void run() {
		int ticks=0;
		score=0;
		do {
			ticks++;
			logger.logNewTick();
			tick();
		}while(!(finishline.finished(cars)));
		logger.logScore(calculatorScore(ticks));
	}
	
	/**
	 * This method calculates the score
	 * Becuase the cars return different scores, we use a "score" counter that adds when cars finish.
	 */
	public int calculatorScore(int ticks) {	//returns the game's score.
		return (1000-(ticks*20)+score);
	}
	
	/**
	 * This method returns the logger instance used by this RaceTrack. You <b>SHOULD NOT</b> be using this method. 
	 * @return logger with this track's events 
	 */
	public TrackLoggerC getLogger() {
		return logger;
	}

}
