package behaviour;

import lejos.hardware.lcd.LCD;
import lejos.robotics.subsumption.Behavior;
import motors.HandMotor;
import motors.RegulatedMotors;
import sensors.TouchSensor;
import utils.Configs;
import utils.Controller;

public class Catch implements Behavior{
	
	private static Catch behaviour = null;
	
	public static Catch instance(){
		if(behaviour==null)
			behaviour = new Catch();
		return behaviour;
	}
	
	private Catch(){
		
	}

	@Override
	public boolean takeControl() {
		LCD.clear() ;
		LCD.drawString("ouiiiiiiiiii", 0, 1) ;
		//Delay.msDelay(2000) ;
		//if( TouchSensor.isPressed() ){
		return TouchSensor.isPressed() && !Controller.isCatched();
	}

	@Override
	public void action() {
		RegulatedMotors.stopMoving();
		Configs.drawProcessing("Catching");
		HandMotor.catchObject();
		Controller.setOkToRealease(false);
	}

	@Override
	public void suppress() {
		
	}

}
