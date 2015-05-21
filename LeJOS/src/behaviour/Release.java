package behaviour;

import utils.Configs;
import utils.Controller;
import lejos.robotics.subsumption.Behavior;
import motors.HandMotor;

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
		//return Controller.notExit() && Controller.isOkToRelease();
		return Controller.isOkToRelease();
	}

	@Override
	public void action() {
		Configs.drawProcessing("Releasing");
		HandMotor.releaseObject();
		Controller.setAdjustAfterRelease(true);
	}

	@Override
	public void suppress() {

	}

}
