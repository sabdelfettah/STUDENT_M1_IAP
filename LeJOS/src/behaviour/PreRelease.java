package behaviour;

import sensors.ColorSensors;
import utils.Controller;
import lejos.robotics.subsumption.Behavior;
import motors.RegulatedMotors;

public class PreRelease implements Behavior {

	private static PreRelease behaviour = null;

	public static PreRelease instance() {
		if (behaviour == null)
			behaviour = new PreRelease();
		return behaviour;
	}

	private PreRelease() {

	}

	@Override
	public boolean takeControl() {
		return Controller.notExit() && Controller.isCatched() && ColorSensors.leftOrRightEquals(ColorSensors.BLACK);
	}

	@Override
	public void action() {
		RegulatedMotors.moveForward();
		while (ColorSensors.leftOrRightEquals(ColorSensors.BLACK))
			;
		RegulatedMotors.stopMoving();
		Controller.setOkToRealease(true);
	}

	@Override
	public void suppress() {

	}

}
