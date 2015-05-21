package test;

import behaviour.Catch;
import behaviour.Move;
import behaviour.Release;
import utils.Configs;
import utils.Controller;
import lejos.hardware.Audio;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import motors.HandMotor;

public class TestProgramII {

	public static void main(String[] args)  {
		EV3 ev3 = Configs.InitAll();
		Controller.setAllToFalse();
		Behavior[] behaviourList = {Move.instance(), Release.instance(), Catch.instance()};
		//Behavior[] behaviourList = {Release.instance(), Catch.instance(), Move.instance()};
		//Behavior[] behaviourList = {Move.instance(), Catch.instance()};
		Arbitrator arbitrator = new Arbitrator(behaviourList);
		arbitrator.start();
//		LCD.drawString("1", 2, 2);
//		Delay.msDelay(1000);
//		HandMotor.catchObject();
//		LCD.drawString("2", 2, 4);
//		Delay.msDelay(1000);
//		RegulatedMotors.moveForward(200);
//		while(!TouchSensor.isPressed());
//		RegulatedMotors.stopMoving();
//		HandMotor.catchObject();
//		RegulatedMotors.moveForward(200);
//		Delay.msDelay(2000);
//		RegulatedMotors.stopMoving();
//		HandMotor.releaseObject();
		ev3.getAudio().systemSound(Audio.BEEP);;
	}
	
	
}
