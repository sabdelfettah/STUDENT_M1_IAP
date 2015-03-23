package robot;

import java.io.IOException;
import java.rmi.NotBoundException;

import lejos.hardware.Audio;
import lejos.hardware.BrickFinder;
import lejos.hardware.BrickInfo;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.lcd.TextLCD;
import lejos.remote.ev3.RemoteEV3;
import lejos.util.Delay;

public class Move implements Runnable{
	
	//private EV3 ev3;
	
	public Move(){
		try {
			BrickInfo [] ev3s = BrickFinder.discover();
			RemoteEV3 ev3 = new RemoteEV3(ev3s[0].getIPAddress());
			TextLCD lcd = ev3.getTextLCD();
			Audio audio = ev3.getAudio();
			lcd.clear();
			lcd.drawString("Execution", 2, 2);
			Delay.msDelay(5000);
			lcd.clear();
			audio.systemSound(Audio.BEEP);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NotBoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("I'AM HERE !");
	}

	public void run() {
		// TODO Auto-generated method stub
		System.out.println("Here");
	}

}
