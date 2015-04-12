package programs;

import behaviour.TestBehaviour;
import behaviour.TestBehaviourII;
import utils.Configs;
import lejos.hardware.Audio;
import lejos.hardware.ev3.EV3;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class TestProgram {

	public static void main(String[] args)  {
		EV3 ev3 = Configs.InitAll();
		Behavior[] behaviourList = {TestBehaviour.instance(), TestBehaviourII.instance()};
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
