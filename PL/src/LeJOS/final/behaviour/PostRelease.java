package behaviour;

import java.util.Random;

import sensors.ColorSensors;
import utils.Controller;
import lejos.robotics.subsumption.Behavior;
import motors.RegulatedMotors;

public class PostRelease implements Behavior {

	@Override
	public boolean takeControl() {
		return Controller.isAdjustAfterRelease();
	}

	@Override
	public void action() {
		
		switch ((new Random()).nextInt(2)) {
			case 0 :
				RegulatedMotors.turnleft() ;
				while (ColorSensors.getLeftColorId() != ColorSensors.BLACK) ;
				RegulatedMotors.stopMoving() ;
				
				RegulatedMotors.turnleft() ;
				while (ColorSensors.getLeftColorId() != ColorSensors.WHITE) ;
				RegulatedMotors.stopMoving() ;
				
				return ;
				
			case 1 :
				RegulatedMotors.turnright() ;
				while (ColorSensors.getRightColorId() != ColorSensors.BLACK) ;
				RegulatedMotors.stopMoving() ;
				
				RegulatedMotors.turnright() ;
				while (ColorSensors.getRightColorId() != ColorSensors.WHITE) ;
				RegulatedMotors.stopMoving() ;
				
				return ;
			
			default : return ;
		}
	}

	@Override
	public void suppress() {

	}

}
