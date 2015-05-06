package programs;

import behaviour.Adjust;
import behaviour.Move;
import utils.Configs;
import utils.Controller;
import lejos.hardware.Audio;
import lejos.hardware.ev3.EV3;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;

public class ProgramV2 {

	public static void main(String[] args) {
		EV3 ev3 = Configs.InitAll();
		Controller.setAllToFalse();
//		Behavior[] behaviourList =
//				{ Move.instance(), FollowLine.instance(), Adjust.instance(), Shift.instance(), Release.instance(), Catch.instance() };
		Behavior[] behaviourList =	{ Move.instance(), Adjust.instance() };
		Arbitrator arbitrator = new Arbitrator(behaviourList);
		arbitrator.start();
		// LCD.drawString("1", 2, 2);
		// Delay.msDelay(1000);
		// HandMotor.catchObject();
		// LCD.drawString("2", 2, 4);
		// Delay.msDelay(1000);
		ev3.getAudio().systemSound(Audio.BEEP);
		;
	}

}
