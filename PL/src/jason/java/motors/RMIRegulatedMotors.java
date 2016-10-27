package motors;

import java.rmi.RemoteException;

import lejos.hardware.lcd.LCD;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.utility.Delay;

public class RMIRegulatedMotors {
	private static RMIRegulatedMotor leftMotor ;
	private static RMIRegulatedMotor rightMotor ;

	public static void setMotors(RMIRegulatedMotor left, RMIRegulatedMotor right){
		
		leftMotor = left;
		rightMotor = right ;
	}

	

	public static void movebackward () {
		movebackward(2000);
	}
	
	public static void moveForward () {
		moveForward(2000);
	}

	public static void moveForward (int speed) {
		
		try {
			leftMotor.setSpeed(speed);
			rightMotor.setSpeed(speed);
			leftMotor.forward();
			rightMotor.forward();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LCD.drawString("je démare", 0, 3) ;
	}
	
	public static void movebackward (int speed ) {
		try {
			leftMotor.setSpeed(speed);
			rightMotor.setSpeed(speed);
			leftMotor.backward();
			rightMotor.backward();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	public static void stopMoving () {
		try {
			leftMotor.stop(true);
			rightMotor.stop(true);
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public static void turnleft (float angle) {
		int angle_tmp = (int)(angle * 1300 / 90) ;
		try {
			leftMotor.setSpeed(300);
			rightMotor.setSpeed(300);
			leftMotor.backward() ;
			rightMotor.forward() ;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Delay.msDelay(angle_tmp) ;
		stopMoving() ;
	} 


	public static void turnright (float angle)	{
		int angle_tmp = (int)(angle * 1300 / 90) ;
		try {
			leftMotor.setSpeed(300);
			rightMotor.setSpeed(300);
			rightMotor.backward() ;
			leftMotor.forward() ;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Delay.msDelay(angle_tmp) ;
		stopMoving() ;
	}

}
