package behaviour;

import lejos.robotics.subsumption.Behavior;
import motors.HandMotor;
import motors.RegulatedMotors;
import sensors.TouchSensor;
import utils.Configs;
import utils.Controller;

public class Catch implements Behavior {

	private static Catch behaviour = null;

	public static Catch instance() {
		if (behaviour == null)
			behaviour = new Catch();
		return behaviour;
	}

	private Catch() {

	}

	@Override
	public boolean takeControl() {
		//return Controller.notExit() && !Controller.isCatched() && TouchSensor.isPressed();
		return !Controller.isCatched() && TouchSensor.isPressed();
	}

	@Override
	public void action() {
		RegulatedMotors.stopMoving();
		Configs.drawProcessing("Catching");
		HandMotor.catchObject();
		Controller.setOkToRealease(false);
	}

	@Override
	public void suppress() {

	}

}
