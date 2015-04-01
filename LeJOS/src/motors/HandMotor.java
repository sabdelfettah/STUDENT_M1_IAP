package motors;

import sensors.TouchSensor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.utility.Delay;

public class HandMotor {

	private static NXTRegulatedMotor hand;	
	private static short state ;
	public static final short OPEN = 1 ;
	public static final short CLOSE = 0 ;
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
		if (state == OPEN) {
			hand.setSpeed(speed);
			hand.backward();
			while(!TouchSensor.isPressed());
			hand.stop(true);
			hand.forward();
			Delay.msDelay(1200);
			hand.stop(true);
			state = CLOSE;
		} 
	}

	public static void releaseObject () {
		releaseObject (deflautSpeed);
	}

	public static void releaseObject(int speed) {
		if (state == CLOSE) {
			hand.setSpeed(speed);
			hand.backward();
			while(!TouchSensor.isPressed());
			hand.stop(true);
			hand.forward();
			while(TouchSensor.isPressed());
			hand.stop(true);
			state = OPEN;
		}
	}

}
