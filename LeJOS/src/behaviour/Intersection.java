package behaviour;

import sensors.ColorSensors;
import utils.Controller;
import lejos.robotics.subsumption.Behavior;
import motors.RegulatedMotors;

public class Intersection implements Behavior {

	private static Intersection behaviour = null;

	public static Intersection instance() {
		if (behaviour == null)
			behaviour = new Intersection();
		return behaviour;
	}

	private Intersection() {

	}

	@Override
	public boolean takeControl() {
		int[] colors = { ColorSensors.WHITE, ColorSensors.BLACK };
		return (ColorSensors.isColorsEquals() && ColorSensors.leftOrRightNotEquals(colors))
				|| (ColorSensors.leftOrRightNotEquals(colors) && !Controller.isOkToAdjust());
	}

	@Override
	public void action() {
		RegulatedMotors.moveForward();
		while(ColorSensors.leftOrRightEquals(ColorSensors.WHITE));
		RegulatedMotors.stopMoving();
		// IN (Intersection)
		// take decision
		// Decision 1 continue Moving
		RegulatedMotors.moveForward();
		while(ColorSensors.leftOrRightNotEquals(ColorSensors.WHITE));
		Controller.setOktoAdjust(true);
		RegulatedMotors.stopMoving();
	}

	@Override
	public void suppress() {

	}

}
