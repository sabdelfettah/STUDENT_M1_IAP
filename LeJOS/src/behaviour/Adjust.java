package behaviour;

import sensors.ColorSensors;
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
		int colorLeft = ColorSensors.getLeftColorId(); 
		int colorRight = ColorSensors.getRightColorId();
		if(colorLeft != ColorSensors.WHITE && colorRight != ColorSensors.WHITE){
			Delay.msDelay(3000);
			// a completer
		}else if(colorLeft != ColorSensors.WHITE){
			RegulatedMotors.turnleft();
			while(!ColorSensors.leftColorEqualsTo(ColorSensors.WHITE)) ;
			RegulatedMotors.stopMoving();
		}else if(colorRight != ColorSensors.WHITE){
			RegulatedMotors.turnright();
			while( ! ColorSensors.rightColorEqualsTo(ColorSensors.WHITE) ) ;
			RegulatedMotors.stopMoving();
		}
	}
	
	@Override
	public void suppress() {
		
	}

}
