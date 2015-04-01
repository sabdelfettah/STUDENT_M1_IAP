package behaviour;

import sensors.TouchSensor;
import lejos.robotics.subsumption.Behavior;
import motors.HandMotor;

public class TestBehaviour implements Behavior{
	
	private static TestBehaviour behaviour = null;
	
	public static TestBehaviour instance(){
		if(behaviour == null)
			behaviour = new TestBehaviour();
		return behaviour;
	}
	
	private TestBehaviour(){
		
	}

	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return TouchSensor.isPressed();
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		HandMotor.catchObject();
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		HandMotor.releaseObject();
	}

}
