package behaviour;

import sensors.ColorSensors;
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
		if(ColorSensors.leftColorEqualsTo(ColorSensors.WHITE) && ColorSensors.rightColorEqualsTo(ColorSensors.WHITE)){
			return true;
		}
		return false;
	}

	@Override
	public void action() {
		suppress = false;
		Configs.drawProcessing("Moving");
		RegulatedMotors.moveForward(200);
		while(!suppress){
			Thread.yield();
		}
		RegulatedMotors.stopMoving();
	}

	@Override
	public void suppress() {
		suppress = true;
	}

}
