package motors;

import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.utility.Delay;

public class RegulatedMotors {
	private static NXTRegulatedMotor leftMotor ;
	private static NXTRegulatedMotor rightMotor ;

	public static void setMotors(NXTRegulatedMotor left, NXTRegulatedMotor right){
		
		leftMotor = left;
		rightMotor = right ;
	}

	

	public static void movebackward () {
		movebackward(2000);
	}

	public static void moveForward (int speed) {
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
		leftMotor.forward();
		rightMotor.forward();
		LCD.drawString("je démare", 0, 3) ;
	}
	
	public static void movebackward (int speed ) {
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
		leftMotor.backward();
		rightMotor.backward();
	}
	

	public static void stopMoving () {
		leftMotor.stop(true);
		rightMotor.stop(true);

	}


	public static void turnleft (float angle) {
		int angle_tmp = (int)(angle * 1300 / 90) ;
		leftMotor.setSpeed(300);
		rightMotor.setSpeed(300);
		leftMotor.backward() ;
		rightMotor.forward() ;
		Delay.msDelay(angle_tmp) ;
		stopMoving() ;


	} 


	public static void turnright (float angle)	{
		int angle_tmp = (int)(angle * 1300 / 90) ;
		leftMotor.setSpeed(300);
		rightMotor.setSpeed(300);
		rightMotor.backward() ;
		leftMotor.forward() ;
		Delay.msDelay(angle_tmp) ;
		stopMoving() ;


	}

	
	
	
	

}
