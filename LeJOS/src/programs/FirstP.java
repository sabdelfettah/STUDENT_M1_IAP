package programs;
import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.utility.Delay;
import motors.*;
import sensors.*;


public class FirstP {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EV3 ev3 = (EV3) BrickFinder.getLocal();
		LCD.drawString("J'attend !", 2, 2);
		IRSensor.setIRSesor( new EV3IRSensor(ev3.getPort("S2"))); 
		RegulatedMotors.setMotors(Motor.A, Motor.C);
		RegulatedMotors.moveForward();
		TouchSensor.setTouchSesor(new EV3TouchSensor (ev3.getPort("S1")));
		while (!TouchSensor.isPressed()) {Delay.msDelay(1000);} ;
		RegulatedMotors.stopMoving();
		HandMotor.setHand(Motor.B, HandMotor.OPEN);
		HandMotor.catchObject();
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
  		
		
		
		
		
		
		
		
		
	}

}
