package behaviour;

import sensors.ColorSensors;
import utils.Controller;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import motors.RegulatedMotors;

public class Shift implements Behavior{
	
	private static Shift behaviour = null;
	
	public static Shift instance(){
		if(behaviour==null)
			behaviour = new Shift();
		return behaviour;
	}
	
	private Shift(){
		
	}

	@Override
	public boolean takeControl() {
		if( Controller.isCatched() ){
			return true;
		}
		return false;
	}
	
	private void turning(boolean left){
		if(left){
			RegulatedMotors.turnleft(90);
		}else{
			RegulatedMotors.turnright(90);
		}
		RegulatedMotors.stopMoving();
		RegulatedMotors.moveForward();
		Delay.msDelay(500);
		RegulatedMotors.stopMoving();
		if(left){
			RegulatedMotors.turnright(90);;
		}else{
			RegulatedMotors.turnleft(90);
		}
		RegulatedMotors.stopMoving();
	}

	@Override
	public void action() {
		boolean turnToLeft = true;
		turning(turnToLeft);
		RegulatedMotors.moveForward();
		while(ColorSensors.getLeftColorId() != ColorSensors.BLACK && ColorSensors.getRightColorId() != ColorSensors.BLACK);
		Controller.setOkToRealease(true);
	}

	@Override
	public void suppress() {
		
	}

}
