package behaviour;

import sensors.ColorSensors;
import utils.Controller;
import lejos.robotics.subsumption.Behavior;
import motors.RegulatedMotors;

public class PostRelease implements Behavior {

	@Override
	public boolean takeControl() {
		return Controller.isOkToAdjust();
	}

	@Override
	public void action() {
		while (!ColorSensors.leftColorEqualsTo(ColorSensors.BLACK))
			RegulatedMotors.turnleft();
		RegulatedMotors.stopMoving();
		while (!ColorSensors.leftColorEqualsTo(ColorSensors.WHITE))
			RegulatedMotors.turnleft();
		RegulatedMotors.stopMoving();
	}

	@Override
	public void suppress() {

	}

}
