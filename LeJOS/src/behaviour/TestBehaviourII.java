package behaviour;

import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import motors.HandMotor;
import motors.RegulatedMotors;
import sensors.TouchSensor;

public class TestBehaviourII implements Behavior{
	
	private static TestBehaviourII behaviour = null;
	
	public static TestBehaviourII instance(){
		if(behaviour == null)
			behaviour = new TestBehaviourII();
		return behaviour;
	}
	
	private TestBehaviourII(){
		
	}

	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return !TouchSensor.isPressed() && HandMotor.getState()!=HandMotor.CLOSE;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		RegulatedMotors.moveForward(200);
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		RegulatedMotors.stopMoving();
		RegulatedMotors.movebackward();
		Delay.msDelay(2000);
		RegulatedMotors.stopMoving();
	}

}
