package behaviour;

import sensors.ColorSensors;
import utils.Controller;
import lejos.robotics.subsumption.Behavior;
import motors.RegulatedMotors;

public class SpecialAdjust implements Behavior {

	private static SpecialAdjust behaviour = null;

	public static SpecialAdjust instance() {
		if (behaviour == null)
			behaviour = new SpecialAdjust();
		return behaviour;
	}

	private SpecialAdjust() {

	}

	@Override
	public boolean takeControl() {
		//return Controller.notExit() && Controller.isOkToAdjust() && ColorSensors.isColorsDifferent();
		return !Controller.isCatched() || ColorSensors.leftOrRightEquals(ColorSensors.BLACK);
	}

	@Override
	public void action() {
		if(ColorSensors.leftColorEqualsTo(ColorSensors.BLACK)){
			RegulatedMotors.turnright();
			while(!ColorSensors.leftColorEqualsTo(ColorSensors.WHITE));
		}else{
			RegulatedMotors.turnleft();
			while(!ColorSensors.rightColorEqualsTo(ColorSensors.WHITE));
		}
		RegulatedMotors.stopMoving();
	}

	@Override
	public void suppress() {

	}

}
