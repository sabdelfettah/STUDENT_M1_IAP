package behaviour;

import sensors.TouchSensor;
import utils.Configs;
import utils.Controller;
import lejos.robotics.subsumption.Behavior;
import motors.HandMotor;
import motors.RegulatedMotors;

public class Catch implements Behavior{
	
	private static Catch behaviour = null;
	
	public static Catch instance(){
		if(behaviour==null)
			behaviour = new Catch();
		return behaviour;
	}
	
	private Catch(){
		
	}

	@Override
	public boolean takeControl() {
		if( TouchSensor.isPressed() ){
			return true;
		}
		return false;
	}

	@Override
	public void action() {
		RegulatedMotors.stopMoving();
		Controller.setCatching(true);
		Configs.drawProcessing("Catching");
		HandMotor.catchObject();
		Controller.setCatching(false);
		Controller.setCatched(true);
	}

	@Override
	public void suppress() {
		
	}

}
