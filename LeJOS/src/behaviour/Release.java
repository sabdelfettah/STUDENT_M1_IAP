package behaviour;

import sensors.ColorSensors;
import utils.Configs;
import utils.Controller;
import lejos.robotics.subsumption.Behavior;
import motors.HandMotor;
import motors.RegulatedMotors;

public class Release implements Behavior {

	private static Release behaviour = null;

	public static Release instance() {
		if (behaviour == null)
			behaviour = new Release();
		return behaviour;
	}

	private Release() {

	}

	@Override
	public boolean takeControl() {
		return Controller.notExit() && Controller.isOkToRelease();
	}

	@Override
	public void action() {
		RegulatedMotors.moveForward();
		while (ColorSensors.leftOrRightEquals(ColorSensors.BLACK))
			;
		RegulatedMotors.stopMoving();
		Configs.drawProcessing("Releasing");
		HandMotor.releaseObject();
		Controller.setOktoAdjust(true);
	}

	@Override
	public void suppress() {

	}

}
