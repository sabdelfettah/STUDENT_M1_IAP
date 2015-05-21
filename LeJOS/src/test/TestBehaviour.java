package test;

import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

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
		return TestController.getCount()==10;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
		TestController.pp();
		LCD.clear();
		LCD.drawString("action Test I", 2, 2);
		Delay.msDelay(2000);
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		LCD.clear();
		LCD.drawString("suppress Test I", 2, 2);
		Delay.msDelay(2000);
	}

}
