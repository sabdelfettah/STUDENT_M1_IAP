package behaviour;

import utils.Controller;
import lejos.hardware.Button;
import lejos.robotics.subsumption.Behavior;

public class Exit_on_cancel implements Behavior {

	private static Exit_on_cancel instance = null;

	public static Exit_on_cancel instance() {
		if (instance == null)
			instance = new Exit_on_cancel();
		return instance;
	}

	private Exit_on_cancel() {

	}

	@Override
	public boolean takeControl() {
		return Button.waitForAnyPress(2) == Button.ID_ESCAPE;
	}

	@Override
	public void action() {
		Controller.setExit(true);
	}

	@Override
	public void suppress() {

	}

}
