package programs;


import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.port.IOPort;
import lejos.hardware.port.Port;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.navigation.DifferentialPilot;
import lejos.utility.Delay;
import motors.*;

import sensors.*;

import java.lang.Math;

public class FirstP {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EV3 ev3 = (EV3) BrickFinder.getLocal();
		LCD.drawString("J'attend !", 2, 2);
		IRSensor.setIRSesor(new EV3IRSensor(ev3.getPort("S3"))) ; 
		RegulatedMotors.setMotors(Motor.A, Motor.C) ;
		
		float distance = IRSensor.getDistance() ;
		
		RegulatedMotors.moveForward(500) ;
		 distance = IRSensor.getDistance() ;
		while (distance > 10.) {
		Delay.msDelay(500) ;
		LCD.clear() ;
		LCD.drawString("Distance : "+distance, 0, 3) ;
		distance = IRSensor.getDistance() ;
		}
		RegulatedMotors.stopMoving() ;
		RegulatedMotors.turnright(180) ;
		
	}
}
