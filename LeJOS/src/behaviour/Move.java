package behaviour;

import utils.Configs;
import utils.Controller;
import lejos.robotics.subsumption.Behavior;
import motors.RegulatedMotors;

public class Move implements Behavior{
	
	private static Move behaviour = null;
	private boolean suppress = false;
	
	public static Move instance(){
		if(behaviour==null)
			behaviour = new Move();
		return behaviour;
	}
	
	private Move(){
		
	}

	@Override
	public boolean takeControl() {
		if(!Controller.isCatching() && !Controller.isReleasing()){
			suppress = false;
			return true;
		}
		return false;
	}

	@Override
	public void action() {
		Configs.drawProcessing("Moving");
		Controller.setMoving(true);
		RegulatedMotors.moveForward(200);
		while(!suppress){
			Thread.yield();
		}
		RegulatedMotors.stopMoving();
	}

	@Override
	public void suppress() {
		Controller.setMoving(false);
		suppress = true;
	}

}
