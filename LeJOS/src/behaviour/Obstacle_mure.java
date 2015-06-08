package behaviour;

import lejos.robotics.subsumption.Behavior;
import motors.RegulatedMotors;
import sensors.ColorSensors;
import sensors.IRSensor;

public class Obstacle_mure implements Behavior {

	private static Obstacle_mure behaviour = null;

	public static Obstacle_mure instance() {
		if (behaviour == null)
			behaviour = new Obstacle_mure();
		return behaviour;
	}

	private Obstacle_mure() {

	}

	@Override
	public boolean takeControl() {
		return (IRSensor.getDistance() < 10 ) ;
	}

	@Override
	public void action() {
		RegulatedMotors.stopMoving();
		
		RegulatedMotors.turnleft() ;
		while (ColorSensors.getLeftColorId() != ColorSensors.WHITE) ;
		RegulatedMotors.stopMoving() ;
		
		RegulatedMotors.turnleft() ;
		while (ColorSensors.getLeftColorId() == ColorSensors.WHITE) ;
		RegulatedMotors.stopMoving() ;
	}

	@Override
	public void suppress() {

	}

}
