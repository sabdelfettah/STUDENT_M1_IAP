package motors;

import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.utility.Delay;

public class HandMotor {

	private static NXTRegulatedMotor hand;	
	private static short state ;
	public static final short OPEN = 1 ;
	public static final short CLOSE = 0 ; 



	public static void setHand(NXTRegulatedMotor theHand, short theState){
		hand  = theHand ;
		state =  theState;
	}	


	public static void catchObject () {
		catchObject (1000);
	}


	public static void  catchObject(int speed) {

		if (state == OPEN) {
			hand.setSpeed(speed);
			hand.forward();
			Delay.msDelay(2000);
			hand.stop(true);
			state = CLOSE;
		}
		
		
	}

	public static void releaseObject () {
		releaseObject (1000);
	}


	public static void releaseObject(int speed) {

		if (state == CLOSE) {
			hand.setSpeed(speed);
			hand.backward();
			Delay.msDelay(2000);
			hand.stop(true);	
			state = OPEN;
		}

	}




}
