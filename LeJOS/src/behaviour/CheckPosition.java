package behaviour;

import utils.Controller;
import lejos.robotics.subsumption.Behavior;

public class CheckPosition implements Behavior{
	
	private static CheckPosition behaviour = null;
	
	public static CheckPosition instance(){
		if(behaviour==null)
			behaviour = new CheckPosition();
		return behaviour;
	}
	
	private CheckPosition(){
		
	}

	@Override
	public boolean takeControl() {
		return Controller.isNeedToCheckPosition();
	}

	@Override
	public void action() {
	}

	@Override
	public void suppress() {
	}

}
