package behaviour;

import sensors.ColorSensors;
import utils.Configs;
import utils.Controller;
import lejos.robotics.subsumption.Behavior;
import motors.RegulatedMotors;

public class Move implements Behavior {

	private static Move behaviour = null;
	private boolean suppress = false;

	public static Move instance() {
		if (behaviour == null)
			behaviour = new Move();
		return behaviour;
	}

	private Move() {

	}

	@Override
	public boolean takeControl() {
		return Controller.notExit() && !Controller.isCatchingOrReleasing()
				&& ColorSensors.lenfAndRightEquals(ColorSensors.WHITE);
	}

	@Override
	public void action() {
		suppress = false;
		Controller.setOktoAdjust(true);
		Configs.drawProcessing("Moving");
		RegulatedMotors.moveForward(300);
		while (!suppress) {
			Thread.yield();
		}
		RegulatedMotors.stopMoving();
	}

	@Override
	public void suppress() {
		suppress = true;
	}

}
