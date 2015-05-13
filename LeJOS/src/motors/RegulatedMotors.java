package motors;

import sensors.ColorSensors;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.utility.Delay;

public class RegulatedMotors {
	private static NXTRegulatedMotor leftMotor;
	private static NXTRegulatedMotor rightMotor;

	public static void setMotors(NXTRegulatedMotor left, NXTRegulatedMotor right) {
		leftMotor = left;
		rightMotor = right;
	}

	public static void moveBackward() {
		moveBackward(2000);
	}
	
	public static void moveForward() {
		moveForward(300);
	}

	public static void moveForward(int speed) {
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
		leftMotor.forward();
		rightMotor.forward();
		
	}

	public static void moveBackward(int speed) {
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
		leftMotor.backward();
		rightMotor.backward();
	}

	public static void stopMoving() {
		leftMotor.stop(true);
		rightMotor.stop(true);

	}

	
	public static void turnleft () {
		leftMotor.setSpeed(300);
		rightMotor.setSpeed(300);
		leftMotor.backward();
		rightMotor.forward();
	}
	
	
	public static void turnleft(float angle) {
		int angle_tmp = (int) (angle * 1300 / 90);
		leftMotor.setSpeed(300);
		rightMotor.setSpeed(300);
		leftMotor.backward();
		rightMotor.forward();
		Delay.msDelay(angle_tmp);
		stopMoving();

	}
	
	
	public static void turnright () {
		leftMotor.setSpeed(300);
		rightMotor.setSpeed(300);
		rightMotor.backward();
		leftMotor.forward();
	}
	
	public static void turnright(float angle) {
		int angle_tmp = (int) (angle * 1300 / 90);
		leftMotor.setSpeed(300);
		rightMotor.setSpeed(300);
		rightMotor.backward();
		leftMotor.forward();
		Delay.msDelay(angle_tmp);
		stopMoving();

	}

	
	private static void regler (boolean c) {
		if (c) {
			while (ColorSensors.getLeftColorId() != 0  ) {
				turnright() ;
			} 
			RegulatedMotors.stopMoving() ;
		}
		else  {
			while (ColorSensors.getRightColorId() != 0  ) {
				turnleft() ;
			} 
			RegulatedMotors.stopMoving() ;
		}
		
		
	}
	
	public static void suivre_ligne () {
		
		while (true) {
			RegulatedMotors.moveForward() ;
			while ( ColorSensors.getLeftColorId() == 0 && ColorSensors.getRightColorId() == 0) {} ;
			if ( ColorSensors.getLeftColorId() != 0) regler (true) ;
			else regler (false) ;
		}
	}
	
	
	
	
}
