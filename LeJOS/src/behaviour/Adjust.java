package behaviour;

import sensors.ColorSensors;
import utils.Controller;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import motors.RegulatedMotors;

public class Adjust implements Behavior{
	
	private static Adjust behaviour = null;
	
	public static Adjust instance(){
		if(behaviour==null)
			behaviour = new Adjust();
		return behaviour;
	}
	
	private Adjust(){
		
	}

	@Override
	public boolean takeControl() {
		if( Controller.isOkToMove() && ColorSensors.getLeftColorId() != ColorSensors.getRightColorId()){
			return true;
		}
		return false;
	}

	@Override
	public void action() {
		RegulatedMotors.stopMoving();
		int colorLeft = ColorSensors.getLeftColorId(); 
		int colorRight = ColorSensors.getRightColorId();
		if(colorLeft != ColorSensors.WHITE && colorRight != ColorSensors.WHITE){
			// a completer
		}else if(colorLeft != ColorSensors.WHITE){
			RegulatedMotors.turnLeft();
			while( ColorSensors.getLeftColorId() != ColorSensors.WHITE ) ;
			Delay.msDelay(200);
			RegulatedMotors.stopMoving();
		}else if(colorRight != ColorSensors.WHITE){
			RegulatedMotors.turnRight();
			while( ColorSensors.getRightColorId() != ColorSensors.WHITE ) ;
			Delay.msDelay(200);
			RegulatedMotors.stopMoving();
		}
	}
	
	private void lookFor(){
		
	}

	@Override
	public void suppress() {
		
	}

}
