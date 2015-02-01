package monPremierPackage;

import lejos.hardware.Audio;
import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

// Affiche "J'attend !" et attend que l'on
// appuye sur le bouton du détécteur ;
// lorsque cela est fait il affiche "Recu !"
// et beep deux fois
public class Program3 {
	
	public static void main(String[] args) {
		EV3 ev3 = (EV3) BrickFinder.getLocal();
		LCD.drawString("J'attend !", 2, 2);
		SensorModes sensor = new EV3TouchSensor(ev3.getPort("S2"));
		SampleProvider touch= sensor.getMode("Touch");
		float[] sample = new float[touch.sampleSize()];
		touch.fetchSample(sample, 0);
		while(sample[0]!=1.){
			touch.fetchSample(sample, 0);
		}
		LCD.clear();
		LCD.drawString("Recu !", 2, 2);
		Audio audio = ev3.getAudio();
		audio.systemSound(Audio.DOUBLE_BEEP);
	    Delay.msDelay(5000);
	}

}
