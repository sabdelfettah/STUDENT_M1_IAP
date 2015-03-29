package robot;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

import lejos.hardware.Audio;
import lejos.hardware.BrickFinder;
import lejos.hardware.BrickInfo;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.NXTRegulatedMotor;
import lejos.remote.ev3.RMIRegulatedMotor;
import lejos.remote.ev3.RemoteEV3;
import lejos.util.Delay;
import motors.NXTRegulatedMotors;
import motors.RMIRegulatedMotors;

public class Move implements Runnable{
	
	private RemoteEV3 ev3;
	private TextLCD lcd;
	private Audio audio;
	private RMIRegulatedMotor left;
	private RMIRegulatedMotor right;
	private boolean executed = false;
	private boolean closed = false;
	
	public Move(){
		try {
			BrickInfo [] ev3s = BrickFinder.discover();
			ev3 = new RemoteEV3(ev3s[0].getIPAddress());
			ev3.setDefault();
			lcd = ev3.getTextLCD();
			audio = ev3.getAudio();
			left = ev3.createRegulatedMotor(Config.LeftMotorPort, 'L');
			right = ev3.createRegulatedMotor(Config.RightMotorPort, 'L');
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Move instaciation finished");
	}
	
	private void rubOnNXT(){
		lcd.drawString("Execution on NXT mode", 2, 2);
		NXTRegulatedMotors.setMotors((NXTRegulatedMotor) left, (NXTRegulatedMotor) right);
		NXTRegulatedMotors.moveForward(300);
		Delay.msDelay(5000);
		NXTRegulatedMotors.stopMoving();
		executed = true;
	}
	
	private void runOnRMI(){
		lcd.drawString("Execution on RMI mode", 2, 2);
		RMIRegulatedMotors.setMotors(left, right);
		RMIRegulatedMotors.moveForward(300);
		Delay.msDelay(5000);
		RMIRegulatedMotors.stopMoving();
		executed = true;
	}

	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Start run");
		lcd.clear();
		rubOnNXT();
		runOnRMI();
		lcd.clear();
		audio.systemSound(Audio.BEEP);
		closeAll();
		System.out.println("finished run");
	}
	
	public boolean wasExecuted(){
		return executed;
	}
	
	public boolean wasClosed(){
		return closed;
	}
	
	private void closeAll(){
		try {
			left.close();
			right.close();
			closed = true;
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
