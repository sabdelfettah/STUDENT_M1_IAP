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
		return ColorSensors.isColorsDifferent() ;
	}

	@Override
	public void action() {
		RegulatedMotors.stopMoving();
		//Delay.msDelay(1000);
		int colorLeft = ColorSensors.getLeftColorId(); 
		int colorRight = ColorSensors.getRightColorId();
		if(colorLeft != 0 && colorRight != 0){
			Delay.msDelay(3000);
			// a completer
		}else if(colorLeft != 0){
			RegulatedMotors.turnleft();
			while( ! ColorSensors.leftColorEqualsTo(0) ) ;
			RegulatedMotors.stopMoving();
		}else if(colorRight != 0){
			RegulatedMotors.turnright();
			while( ! ColorSensors.rightColorEqualsTo(0) ) ;
			RegulatedMotors.stopMoving();
		}
	}
	
	@Override
	public void suppress() {
		
	}

}
