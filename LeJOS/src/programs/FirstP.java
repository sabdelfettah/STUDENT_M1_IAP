package programs;



import sensors.ColorSensors;
import utils.Configs;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.utility.Delay;
import motors.RegulatedMotors;


public class FirstP {


	/**
	 * @param args
	 * @throws InterruptedException 
	 * @throws IOException 
	 */
	public static void main(String[] args) throws InterruptedException  {
		// TODO Auto-generated method stub
				
		
		EV3 ev3 = Configs.InitAll() ;
		
		//RegulatedMotors.setMotors(Motor.A, Motor.D);
		//RegulatedMotors.moveForward() ;
		//RegulatedMotors.suivre_ligne() ;
		
	
			RegulatedMotors.moveForward(300) ;
			while (ColorSensors.getLeftColorId() == 0 && ColorSensors.getRightColorId() == 0)  ;
			RegulatedMotors.stopMoving() ;
			boolean c = ColorSensors.getLeftColorId() != 0 ;
			if (c) {
				while (ColorSensors.getLeftColorId() != 0) RegulatedMotors.turnleft() ;
				RegulatedMotors.stopMoving() ;
			}
			else  {
				while (ColorSensors.getRightColorId() != 0) RegulatedMotors.turnright() ;
				RegulatedMotors.stopMoving() ;
			}
		
		
		
		//Thread.sleep (2000);
		//Motor.A.stop();
		//Motor.D.stop();
		
//		
//		while (true) {
//			LCD.clear() ;
//			LCD.drawString( ColorSensors.getRightColorName() + " " , 2, 2 ) ; 
//			button = Button.waitForAnyEvent(1000) ;	
//			if (button == Button.ID_ENTER) break ;	
//		} 
	}
}