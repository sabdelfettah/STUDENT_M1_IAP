package test;

import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.utility.Delay;
import motors.HandMotor;
import motors.RegulatedMotors;
import sensors.IRSensor;
import sensors.TouchSensor;

public class SecondProg {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EV3 ev3 = (EV3) BrickFinder.getLocal();
		LCD.drawString("J'attend !", 2, 2);
		IRSensor.setIRSesor(new EV3IRSensor(ev3.getPort("S3"))) ;
		TouchSensor.setTouchSesor(new EV3TouchSensor(ev3.getPort("S1")));
		RegulatedMotors.setMotors(Motor.A, Motor.C) ;
		HandMotor.setHand(Motor.B, (short) 0);
		HandMotor.releaseObject();
		float distance = IRSensor.getDistance() ;
		boolean touched = TouchSensor.isPressed();
		RegulatedMotors.moveForward(500) ;
		//distance = IRSensor.getDistance() ;
		while (distance > 10.) {
		Delay.msDelay(500) ;
		LCD.clear() ;
		LCD.drawString("Distance : "+distance, 0, 3) ;
			if(touched){
				HandMotor.catchObject();
			}
			distance = IRSensor.getDistance() ;
			touched = TouchSensor.isPressed();
		}
		RegulatedMotors.stopMoving() ;
		RegulatedMotors.turnright(180) ;
		
	}
}
