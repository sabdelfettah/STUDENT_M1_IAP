package programs;

import behaviour.Adjust;
import behaviour.Catch;
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
		Behavior[] behaviourList =	{ Move.instance(), Adjust.instance(), Catch.instance() };
		Arbitrator arbitrator = new Arbitrator(behaviourList);
		arbitrator.start();
		ev3.getAudio().systemSound(Audio.BEEP);
	}
}
