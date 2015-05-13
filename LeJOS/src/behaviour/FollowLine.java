package behaviour;

import sensors.ColorSensors;
import utils.Configs;
import utils.Controller;
import lejos.robotics.subsumption.Behavior;
import motors.RegulatedMotors;

public class FollowLine implements Behavior {

	private static FollowLine behaviour = null;
	private boolean suppress = false;

	public static FollowLine instance() {
		if (behaviour == null)
			behaviour = new FollowLine();
		return behaviour;
	}

	private FollowLine() {

	}

	@Override
	public boolean takeControl() {
		return (Controller.isOkToMove() && ColorSensors.lenfAndRightEquals(ColorSensors.WHITE));
	}

	@Override
	public void action() {
		suppress = false;
		Configs.drawProcessing("Moving");
		RegulatedMotors.moveForward(200);
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
