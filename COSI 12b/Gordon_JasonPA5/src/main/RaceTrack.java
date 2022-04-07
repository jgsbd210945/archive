/**
 * Jason Gordon
 * jagordon@brandeis.edu
 * April 4 2021
 * PA5
 * Explanation of the class: This class is the "hub" of the assignment. It will call the other classes if need be and
 * send the results to the track logger. It is also where the simulation is run.
 * Known bugs: N/A
 */


package main;

public class RaceTrack {
	RaceCar[] racecars=new RaceCar[10];		//I am initializing racecars and formulaOnes here so as to not call them later.
	FormulaOne[] formulaOnes=new FormulaOne[10];
	PitStop pitstop=new PitStop();
	FinishLine finishline=new FinishLine();
	int placement;
	int totalrc;
	int totalfo;
	
	/**
	 * DO NOT REMOVE THIS - you should be using this to log this track's events. For more see the assignment PDF / documentation for TrackLoggerB.java
	 */
	private TrackLoggerB logger;
	
	public RaceTrack() {
		logger = new TrackLoggerB(); // DO NOT REMOVE THIS LINE
	}
	
	public void setCars(RaceCar[] racecars) {		//Both setCars initialize the racecars/formulaOnes in the race.
		for (int i=0; i<racecars.length; i++) {		//We can assume there will not be more than 10 cars per race.
			this.racecars[i] = racecars[i];			//We must also run the for loop in order to initialize all the cars,
		}											//for arrays are reference types.
	}
	
	public void setCars(RaceCar[] racecars, FormulaOne[] formulaOnes) {
		this.racecars = racecars;
		for (int i=0; i<racecars.length; i++) {
			this.racecars[i] = racecars[i];
		}
		this.formulaOnes = formulaOnes;
		for (int j=0; j<formulaOnes.length; j++) {
			this.formulaOnes[j] = formulaOnes[j];
		}
	}
	
	public void tick() {	//This method will run one tick.
		for (int i=0; i<racecars.length; i++) {	//This will be the same for both racecars and formulaOnes.
			if (racecars[i] != null) { //null checker
				if (racecars[i].pittimer == 0) {
					racecars[i].location+=racecars[i].speed;	//moves the racecar
					if ((racecars[i].speed != racecars[i].initialspeed) && (((racecars[i].getLocation() % 100) >= 75) || (((racecars[i].getLocation() % 100) < ((75 + racecars[i].speed) % 100))) && (racecars[i].speed >25))) {
					//The above checks to see if the car can enter the pitstop (must be damaged and pass the pitstop at that time)
						logger.logEnterPit(racecars[i]);
						racecars[i].pittimer+=2;	//Initializing the pitstop timer.
						pitstop.enterPitStop (racecars[i]);
						racecars[i].location-=((racecars[i].getLocation()-75) % 100); //changes the location to x75.
					}
				}else {
					racecars[i].pittimer--;	//If the car is in the pitstop, it will decrease its timer
					if (racecars[i].pittimer == 0) {	//If the pittimer is 0, it is in the next round
						logger.logExitPit(racecars[i]);
						pitstop.exitPitStop(racecars[i]);
						racecars[i].speed = racecars[i].initialspeed;
						racecars[i].location+=racecars[i].speed;	//If I don't move them immediately, a crash will happen as they leave the pit stop.
					}
				}
				if (racecars[i].getLocation() >= 1000) {	//finish sequence
					finishline.enterFinishLine(racecars[i]);
					placement++;
					logger.logFinish(racecars[i], placement);
					totalrc++;
					racecars[i]=null;
				}
			}
		}
		for (int i=0; i<formulaOnes.length; i++) {
			if (formulaOnes[i] != null) {
				if (formulaOnes[i].pittimer == 0) {
					formulaOnes[i].location+=formulaOnes[i].speed;
					if ((formulaOnes[i].speed != formulaOnes[i].initialspeed) && ((formulaOnes[i].getLocation() % 100) > 75 || (formulaOnes[i].getLocation() % 100) < ((75 + formulaOnes[i].speed) % 100))) {
						logger.logEnterPit(formulaOnes[i]);
						formulaOnes[i].pittimer+=2;
						pitstop.enterPitStop (formulaOnes[i]);
						formulaOnes[i].location-=((formulaOnes[i].getLocation()-75) % 100);
					}
				}else {
					formulaOnes[i].pittimer--;
					if (formulaOnes[i].pittimer == 0) {
						logger.logExitPit(formulaOnes[i]);
						pitstop.exitPitStop(formulaOnes[i]);
						formulaOnes[i].speed = formulaOnes[i].initialspeed;
					}
				}
				if (formulaOnes[i].getLocation() >= 1000) {
					finishline.enterFinishLine(formulaOnes[i]);
					placement++;
					logger.logFinish(formulaOnes[i], placement);
					totalfo++;
					formulaOnes[i]=null;
				}
			}
		}
		checkCollision();
	}
	
	public void checkCollision() {			//This method checks for collisions and does the necessary adjustments for it.
		for (int i=0; i<racecars.length; i++) {		//Because we are not allowed to use inheritance, we must go through both arrays, which takes 4 for loops to do.
			if (racecars[i] != null) {				//The outside for loop will run through once, and the inside loop will run for the amount of times the outside loop runs, comparing each value to the outside loop 
				for (int j=0; j<racecars.length; j++) {		//the i != j statement is to catch it comparing it to itself
					if (racecars [j] != null) {				//We also make sure to filter for nulls.
						if (i != j && (racecars[i].getLocation() % 100) == (racecars[j].getLocation() % 100) && pitstop.IsNotInPitStop(racecars[i]) && pitstop.IsNotInPitStop(racecars[j])) {
							//If it is not itself and the locations are the same (% 100 because it will be the same regardless of lap) and neither are in the pit stop, it will "damage" the car.
							if (racecars[i].speed==racecars[i].initialspeed) { 	//However, we must check if the car is damaged, for damage is not stacking.
								racecars[i].speed-=racecars[i].strength * 5;
								logger.logDamaged(racecars[i]);
							}
							if (racecars[j].speed==racecars[j].initialspeed) {
								racecars[j].speed-=racecars[j].strength * 5;
								logger.logDamaged(racecars[j]);
							}
						}
					}
				}
				for (int j=0; j<formulaOnes.length; j++) {	//Because we need to use two arrays, we have to compare racecar to racecar, racecar to formulaOnes, and formulaOnes to formulaOnes.
					if (formulaOnes [j] != null) {			//We don't need to compare formulaOnes to racecar because we're already comparing the two.
						if (i != j && (racecars[i].getLocation() % 100) == (formulaOnes[j].getLocation() % 100) && pitstop.IsNotInPitStop(racecars[i]) && pitstop.IsNotInPitStop(formulaOnes[j])) {
							if (racecars[i].speed==racecars[i].initialspeed) {
								racecars[i].speed-=racecars[i].strength * 5;
								logger.logDamaged(racecars[i]);
							}
							if (formulaOnes[j].speed==formulaOnes[j].initialspeed) {
								formulaOnes[j].speed-=formulaOnes[j].strength * 5;
								logger.logDamaged(formulaOnes[j]);
							}
						}
					}
				}
			}
		}
		for (int i=0; i<formulaOnes.length; i++) {
			if (formulaOnes[i] != null) {
				for (int j=0; j<formulaOnes.length; j++) {
					if (formulaOnes [j] != null) {
						if (i != j && (formulaOnes[i].getLocation() % 100) == (formulaOnes[j].getLocation() % 100) && pitstop.IsNotInPitStop(formulaOnes[i]) && pitstop.IsNotInPitStop(formulaOnes[j])) {
							if (formulaOnes[i].speed==formulaOnes[i].initialspeed) {
								formulaOnes[i].speed-=formulaOnes[i].strength * 5;
								logger.logDamaged(formulaOnes[i]);
							}
							if (formulaOnes[j].speed==formulaOnes[j].initialspeed) {
								formulaOnes[j].speed-=formulaOnes[j].strength * 5;
								logger.logDamaged(formulaOnes[j]);
							}
						}
					}
				}	
			}
		}
	}
	
	public void run() {
		int ticks=0;
		do {
			ticks++;
			logger.logNewTick();
			tick();
		}while(!(finishline.finished(racecars, formulaOnes)));
		logger.logScore(calculatorScore(ticks));
	}
	
	public int calculatorScore(int ticks) {	//returns the game's score.
		return (1000-(ticks*20)+(totalrc*150)+(totalfo*100));
	}
	
	/**
	 * This method returns the logger instance used by this RaceTrack. You <b>SHOULD NOT</b> be using this method. 
	 * @return logger with this track's events 
	 */
	public TrackLoggerB getLogger() {
		return logger;
	}

}
