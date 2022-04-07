package main;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

public class PA5tester {
	RaceCar[] racecars=new RaceCar[10];	//RaceTrack class
	FormulaOne[] formulaOnes=new FormulaOne[10];
	
	
	public void setCars(RaceCar[] racecars) { //SetCars #1
		for (int i=0; i<racecars.length; i++) {
			this.racecars[i] = racecars[i];	
		}
	}
	
	public void setCars(RaceCar[] racecars, FormulaOne[] formulaOnes) { //SetCars #2
		this.racecars = racecars;
		for (int i=0; i<racecars.length; i++) {
			this.racecars[i] = racecars[i];
		}
		this.formulaOnes = formulaOnes;
		for (int j=0; j<formulaOnes.length; j++) {
			this.formulaOnes[j] = formulaOnes[j];
		}
	}
	
	RaceCar[] rcinpitstop=new RaceCar[10];	//Pitstop class

	public void enterPitStop(RaceCar rc) {	//EnterPitStop method
		for (int i=0; i<rcinpitstop.length; i++) {
			if (rcinpitstop[i] == null) {
				rcinpitstop[i] = rc;
			}
		}
	}
	
	@Test
	void SetCarsTest() {	//Checks to see if all the RaceCars are valid.
		
		setCars(SimulationDriver.getSomeRaceCars());
		for (int i=0; i<10; i++) {
			if (racecars[i] != null) {	//Preventing NullPointerExceptions by making sure it won't check if the value is null.
				assertTrue(racecars[i].speed>=30 && racecars[i].speed<=55);
				assertTrue(racecars[i].strength>=2 && racecars[i].strength<=4);
			}
		}
		for (int i=0; i<formulaOnes.length; i++) {
			if (formulaOnes[i] != null) {
				assertTrue(formulaOnes[i].speed>=30 && formulaOnes[i].speed<=70);
				assertTrue(formulaOnes[i].strength>=3 && formulaOnes[i].strength<=5);
			}
		}
	}
	
	@Test
	void EnterPitStopTest() {	//Fills up a rcinpitstop array and checks to make sure it adds all the cars.
		
		setCars(SimulationDriver.getSomeRaceCars());
		int counter = 0;
		for (int i=0; i<10; i++) {
			if (racecars[i] != null) {
				enterPitStop(racecars[i]);
				counter++;	//Here, I bring a counter up, and it should go one down for every one it goes up, making it 0.
			}
		}
		for (int i=0; i<10; i++) {
			if (racecars[i] != null) {
				counter--;
			}
		}
		assertTrue(counter == 0);
	}
}