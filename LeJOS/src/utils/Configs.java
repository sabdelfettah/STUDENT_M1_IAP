package utils;

import sensors.ColorSensors;
import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.utility.Delay;
import motors.RegulatedMotors;

public class Configs {
	
	public static final String TouchSensorPort = "S4";
	public static final String IRSensorPort = "S3";
	public static final String LeftColorSensor = "S1";
	public static final String RightColorSensor = "S2";
	public static final String LeftMotorPort = "A";
	public static final String RightMotorPort = "D";
	public static final String HandMotorPort = "B";
	
	// Returning confihuration pamareters methods
	public static NXTRegulatedMotor getLeftMotor(){
		return getMotorAtPort(LeftMotorPort.charAt(0));
	}
	
	public static NXTRegulatedMotor getRightMotor(){
		return getMotorAtPort(RightMotorPort.charAt(0));
	}
	
	public static NXTRegulatedMotor getHandMotor(){
		return getMotorAtPort(HandMotorPort.charAt(0));
	}
	
	private static NXTRegulatedMotor getMotorAtPort(char port){
		switch(port){
		case 'A': return Motor.A;
		case 'B': return Motor.B;
		case 'C': return Motor.C;
		case 'D': return Motor.D;
		}
		return null;
	}
	
	// Initialising methods
	public static EV3 InitBrick(){
		drawProcessing("Getting EV3");
		EV3 ev3 = (EV3) BrickFinder.getLocal();
		return ev3;
	}

	public static EV3 InitAll(){
		EV3 ev3 = InitBrick();
		drawProcessing("Initialising");
		//TouchSensor.setTouchSesor(new EV3TouchSensor(ev3.getPort(Configs.TouchSensorPort)));
		//HandMotor.setHand(getHandMotor(), HandMotor.CATCHED);
		RegulatedMotors.setMotors(getLeftMotor(), getRightMotor());
		ColorSensors.SetColorSensors(new EV3ColorSensor(ev3.getPort(LeftColorSensor)), new EV3ColorSensor(ev3.getPort(RightColorSensor)));
		drawProcessing("Asking for calibration");
		ColorSensors.calibrate();
//		drawProcessing("Testing the hand motor");
		//HandMotor.releaseObject();
//		drawProcessing("Testing the other motors");
//		//RegulatedMotors.moveForward(200);
//		Delay.msDelay(1000);
//		RegulatedMotors.stopMoving();
		drawString("Initialisation procedure finished");
		Delay.msDelay(1500);
		return ev3;
	}
	
	// Drawing methods
	public static void drawString(String string){
		LCD.clear();
		LCD.drawString(string, 2, 2);
	}
	
	public static void drawProcessing(String string){
		drawString(string+" ...");
	}

}

