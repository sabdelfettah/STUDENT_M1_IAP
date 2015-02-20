package motors;

import lejos.hardware.motor.NXTRegulatedMotor;

public class RegulatedMotors {
	private static NXTRegulatedMotor leftMotor;
	private static NXTRegulatedMotor rightMotor;

	public static void setMotors(NXTRegulatedMotor left, NXTRegulatedMotor right){
		leftMotor = left;
		rightMotor = right ;
	}

	public static void moveForward () {
		moveForward(2000);
	}

	public static void movebackward () {
		movebackward(2000);
	}

	public static void moveForward (int speed ) {
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
		leftMotor.forward();
		rightMotor.forward();
	}
	
	public static void movebackward (int speed ) {
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
		leftMotor.forward();
		rightMotor.forward();
	}
	

	public static void stopMoving () {
		leftMotor.stop(true);
		rightMotor.stop(true);

	}


	public static void turnleft () {



	} 


	public static void turnright ()	{




	}

	
	
	
	

}
