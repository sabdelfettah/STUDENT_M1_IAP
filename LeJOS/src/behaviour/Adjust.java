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
		if(ColorSensors.isColorsDifferent()){
			return true;
		}
		return false;
	}

	@Override
	public void action() {
		RegulatedMotors.stopMoving();
		Delay.msDelay(1000);
		int colorLeft = ColorSensors.getLeftColorId(); 
		int colorRight = ColorSensors.getRightColorId();
		if(colorLeft != ColorSensors.WHITE && colorRight != ColorSensors.WHITE){
			Delay.msDelay(3000);
			// a completer
		}else if(colorLeft != ColorSensors.WHITE){
			RegulatedMotors.turnLeft();
			while( ! ColorSensors.leftColorEqualsTo(ColorSensors.WHITE) ) ;
			RegulatedMotors.stopMoving();
		}else if(colorRight != ColorSensors.WHITE){
			RegulatedMotors.turnRight();
			while( ! ColorSensors.rightColorEqualsTo(ColorSensors.WHITE) ) ;
			RegulatedMotors.stopMoving();
		}
	}
	
	@Override
	public void suppress() {
		
	}

}
