package motors;

import utils.Controller;
import lejos.hardware.lcd.LCD;
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
		moveForward(2000);
	}

	public static void moveForward(int speed) {
		Controller.setMoving(true);
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
		leftMotor.forward();
		rightMotor.forward();
		LCD.drawString("starting", 0, 3);
	}

	public static void moveBackward(int speed) {
		Controller.setMoving(true);
		leftMotor.setSpeed(speed);
		rightMotor.setSpeed(speed);
		leftMotor.backward();
		rightMotor.backward();
	}

	public static void stopMoving() {
		leftMotor.stop(true);
		rightMotor.stop(true);
		Controller.setMoving(false);
	}

	public static void turnleft(float angle) {
		Controller.setMoving(true);
		int angle_tmp = (int) (angle * 1300 / 90);
		leftMotor.setSpeed(300);
		rightMotor.setSpeed(300);
		leftMotor.backward();
		rightMotor.forward();
		Delay.msDelay(angle_tmp);
		stopMoving();
		Controller.setMoving(false);
	}

	public static void turnright(float angle) {
		Controller.setMoving(true);
		int angle_tmp = (int) (angle * 1300 / 90);
		leftMotor.setSpeed(300);
		rightMotor.setSpeed(300);
		rightMotor.backward();
		leftMotor.forward();
		Delay.msDelay(angle_tmp);
		stopMoving();
		Controller.setMoving(false);
	}

	public static void turnLeft() {
		leftMotor.setSpeed(300);
		rightMotor.setSpeed(300);
		leftMotor.backward();
		rightMotor.forward();
	}

	public static void turnRight() {
		leftMotor.setSpeed(300);
		rightMotor.setSpeed(300);
		rightMotor.backward();
		leftMotor.forward();
	}

}
