package programs;

import behaviour.Catch;
import behaviour.Move;
import behaviour.Release;
import behaviour.Timer;
import utils.Configs;
import lejos.hardware.Audio;
import lejos.hardware.ev3.EV3;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class TestProgramII {

	public static void main(String[] args)  {
		EV3 ev3 = Configs.InitAll();
		Behavior[] behaviourList = {Move.instance(), Release.instance(), Catch.instance(), Timer.instance()};
		//Behavior[] behaviourList = {Move.instance(), Catch.instance()};
		Arbitrator arbitrator = new Arbitrator(behaviourList);
		arbitrator.start();
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
