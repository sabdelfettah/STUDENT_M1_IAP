package robot;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.hardware.Audio;
import lejos.hardware.BrickFinder;
import lejos.hardware.BrickInfo;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3IRSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;
import lejos.util.Delay;
import motors.NXTRegulatedMotors;
import motors.RMIRegulatedMotors;

public class EV3Class {
	
	private static EV3Class singleton = null;
	private static RemoteEV3 ev3 = null;
	private static RMIRegulatedMotor left = null;
	private static RMIRegulatedMotor right = null;
	private static EV3TouchSensor touchSensor = null;
	private static EV3ColorSensor colorSensor = null;
	private static EV3IRSensor IRSensor = null ;
	private static TextLCD lcd = null;
	private static Audio audio = null;
	private static boolean initializedMotors = false;
	
	private EV3Class(){
		BrickInfo[] ev3s;
		try {
			ev3s = BrickFinder.discover();
			ev3 = new RemoteEV3(ev3s[0].getIPAddress());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(ev3 != null){
			ev3.setDefault();
			lcd = ev3.getTextLCD();
			audio = ev3.getAudio();
			left = ev3.createRegulatedMotor(Config.LeftMotorPort, 'L');
			right = ev3.createRegulatedMotor(Config.RightMotorPort, 'L');
		}
	}
	
	public void rubOnNXT(){
		instance();
		lcd.drawString("Execution on NXT mode", 2, 2);
		if(!initializedMotors)
			NXTRegulatedMotors.setMotors((NXTRegulatedMotor) left, (NXTRegulatedMotor) right);
		NXTRegulatedMotors.moveForward(300);
		Delay.msDelay(5000);
		NXTRegulatedMotors.stopMoving();
	}
	
	public void runOnRMI(){
		instance();
		lcd.drawString("Execution on RMI mode", 2, 2);
		if(!initializedMotors)
			RMIRegulatedMotors.setMotors(left, right);
		RMIRegulatedMotors.moveForward(300);
		Delay.msDelay(5000);
		RMIRegulatedMotors.stopMoving();
	}
	
	private static void closeAll(){
		try {
			left.close();
			right.close();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static EV3Class instance(){
		if(singleton==null){
			singleton = new EV3Class();
		}
		return singleton;
	}

}
