package motors;

import sensors.TouchSensor;
import utils.Controller;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.utility.Delay;

public class HandMotor {

	private static NXTRegulatedMotor hand;	
	private static short state ;
	public static final short RELEASED = 1 ;
	public static final short CATCHED = 0 ;
	private static final int deflautSpeed = 400;

	public static void setHand(NXTRegulatedMotor theHand, short theState){
		hand  = theHand ;
		state =  theState;
	}	

	public static void catchObject () {
		catchObject (deflautSpeed);
	}
	
	public static short getState(){
		return state;
	}

	public static void  catchObject(int speed) {
		if (state == RELEASED) {
			Controller.setCatching(true);
			hand.setSpeed(speed);
			hand.backward();
			//while(!TouchSensor.isPressed());
			Delay.msDelay(1000);
			hand.stop(true);
			state = CATCHED;
			Controller.setCatching(false);
			Controller.setCatched(true);
		} 
	}

	public static void releaseObject () {
		releaseObject (deflautSpeed);
	}

	public static void releaseObject(int speed) {
		if (state == CATCHED) {
			Controller.setReleasing(true);
			hand.setSpeed(speed);
			hand.backward();
			while(!TouchSensor.isPressed());
			hand.stop(true);
			hand.forward();
			while(TouchSensor.isPressed());
			hand.stop(true);
			state = RELEASED;
			Controller.setReleasing(false);
			Controller.setCatched(false);
		}
	}

}
