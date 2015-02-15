import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.Sound;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.RegulatedMotorListener;
import lejos.utility.Delay;

public class TestMotor {
	private static BaseRegulatedMotor leftMotor;
	private static BaseRegulatedMotor rightMotor;
	private final static int speedMotor = 900;
	private final static int accelerationMotor = 800;
	
	public static void main(String[] args){

			/*
			-affiche sur l'ecran " Bonjour"	
			-avance en effectuant  6 tours   	
			-toure vers la droite 
			-avance en effectuant  6 tours   
			-tourne vers la gauche
			-avance en effectuant  6 tours   
			-attend 2 seconde
			-recule  en effectuant  6 tours  
			-affiche sur l'ecran " Fini"
			*/

		 EV3 ev3 = (EV3)  BrickFinder.getLocal(); 

		
		leftMotor = new EV3LargeRegulatedMotor(ev3.getPort("A"));
		rightMotor = new EV3LargeRegulatedMotor(ev3.getPort("C"));
		leftMotor.setSpeed(speedMotor);
		rightMotor.setSpeed(speedMotor); 
		leftMotor.setAcceleration(accelerationMotor);
		rightMotor.setAcceleration(accelerationMotor);
		rightMotor.synchronizeWith(new RegulatedMotor[] {leftMotor} ); 	
		LCD.drawString("Bonjour", 3, 3);	
		rightMotor.startSynchronization();
			leftMotor.rotate(2160,true);
			rightMotor.rotate(2160,true);
		rightMotor.endSynchronization();
			leftMotor.waitComplete();
			rightMotor.waitComplete();
		rightMotor.startSynchronization();
			leftMotor.rotate(360,true);
			rightMotor.rotate(0,true);
		rightMotor.endSynchronization();
			leftMotor.waitComplete();
			rightMotor.waitComplete();
		rightMotor.startSynchronization();
			leftMotor.rotate(2160,true);
			rightMotor.rotate(2160,true);
		rightMotor.endSynchronization();
			leftMotor.waitComplete();
			rightMotor.waitComplete();
		rightMotor.startSynchronization();
			leftMotor.rotate(0,true);
			rightMotor.rotate(360,true);
		rightMotor.endSynchronization();
			leftMotor.waitComplete();
			rightMotor.waitComplete();	
		rightMotor.startSynchronization();
			leftMotor.rotate(2160,true);
			rightMotor.rotate(2160,true);
		rightMotor.endSynchronization();
			leftMotor.waitComplete();
			rightMotor.waitComplete();	
		try { Thread.sleep(1000); } catch (Exception e) {} 
		rightMotor.startSynchronization();
			leftMotor.rotate(-2160,true);
			rightMotor.rotate(-2160,true);
		rightMotor.endSynchronization();
			leftMotor.waitComplete();
			rightMotor.waitComplete(); 

		LCD.drawString("fini", 3, 3);	
		
		
		}

}


