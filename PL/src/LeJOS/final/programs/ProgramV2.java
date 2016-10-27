package programs;

import behaviour.Adjust;
import behaviour.Catch;
import behaviour.Exit_on_cancel;
import behaviour.Move;
import behaviour.PreRelease;
import behaviour.Release;
import sensors.ColorSensors;
import utils.Configs;
import utils.Controller;
import lejos.hardware.Audio;
import lejos.hardware.Button;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Arbitrator;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class ProgramV2 {

	public static void main(String[] args) {
		EV3 ev3 = Configs.InitAll();
		Controller.setAllToFalse();
		Behavior[] behaviourList =
				{ Move.instance(), Adjust.instance(), Release.instance(), PreRelease.instance(), Catch.instance(),
						Exit_on_cancel.instance() };
		Arbitrator arbitrator = new Arbitrator(behaviourList);
		arbitrator.start();
		// take care of the number of colors !
//		while(true){
//			LCD.drawString(ColorSensors.getLeftColorName(), 2, 2);
//			LCD.drawString(ColorSensors.getRightColorName(), 2, 4);
//			Delay.msDelay(1000);
//			if(Button.waitForAnyPress(2) == Button.ID_ESCAPE){
//				break;
//			}
//		}
		ev3.getAudio().systemSound(Audio.BEEP);
	}
}
