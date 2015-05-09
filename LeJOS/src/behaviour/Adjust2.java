package behaviour;

import sensors.ColorSensors;
import utils.Controller;
import lejos.robotics.subsumption.Behavior;
import motors.RegulatedMotors;

public class Adjust2 implements Behavior {

	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return Controller.isOkToAdjust() ;	
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		while (! ColorSensors.leftColorEqualsTo (ColorSensors.BLACK)) RegulatedMotors.turnleft() ;
		RegulatedMotors.stopMoving() ;
		while (! ColorSensors.leftColorEqualsTo (ColorSensors.WHITE)) RegulatedMotors.turnleft() ;
		RegulatedMotors.stopMoving() ;
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}

}
