package test;

import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;

public class TestBehaviourII implements Behavior{
	
	private static TestBehaviourII behaviour = null;
	private boolean suppress;
	
	public static TestBehaviourII instance(){
		if(behaviour == null)
			behaviour = new TestBehaviourII();
		return behaviour;
	}
	
	private TestBehaviourII(){
		TestController.setCount(0);
		suppress = false;
	}

	@Override
	public boolean takeControl() {
		// TODO Auto-generated method stub
		return TestController.getCount() < 10;
	}

	@Override
	public void action() {
		// TODO Auto-generated method stub
//		LCD.clear();
//		LCD.drawString("action Test II", 2, 2);
////		while(!suppress){
////			Controller.pp();
////			Thread.yield();
////			break;
////		}
//		if(!suppress){
//			Controller.pp();
//			LCD.drawString("count = "+Controller.getCount(), 2, 4);
//			Delay.msDelay(1000);
//			if(suppress){
//				LCD.clear();
//				LCD.drawString("fin action", 2, 2);
//				LCD.drawString("Test II", 2, 3);
//				Delay.msDelay(1000);
//			}
//			Thread.yield();
//		}
		suppress = false;
		TestController.setCount(0);
		while(!suppress){
			TestController.pp();
			LCD.clear();
			LCD.drawString("Count = "+TestController.getCount(), 2, 2);
			Delay.msDelay(1000);
			Thread.yield();
		}
		LCD.clear();
		LCD.drawString("FIN", 2, 2);
		Delay.msDelay(1000);
	}

	@Override
	public void suppress() {
		suppress=true;
	}

}
