package monPremierPackage;

import lejos.hardware.Audio;
import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.lcd.TextLCD;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class Program4 {

	public static void main(String[] args) {
		EV3 ev3 = (EV3) BrickFinder.getLocal();
		// Color Sensor & sample
		EV3ColorSensor sensorColor = new EV3ColorSensor(ev3.getPort("S1"));
		SampleProvider color= sensorColor.getRGBMode();
		float[] sampleColor = new float[color.sampleSize()];
		// Touch Sensor & sample
		EV3TouchSensor sensorTouch = new EV3TouchSensor(ev3.getPort("S2"));
		SampleProvider touch= sensorTouch.getMode("Touch");
		float[] sampleTouch = new float[touch.sampleSize()];
		touch.fetchSample(sampleTouch, 0);
		while(sampleTouch[0]!=1.){
			touch.fetchSample(sampleTouch, 0);
		}
		LCD.drawString("Taille = "+color.sampleSize(), 2, 0);
		Delay.msDelay(1000);
		while(sampleTouch[0]!=0.){
			color.fetchSample(sampleColor, 0);
			//color.fetchSample(sampleColor, 1);
			//color.fetchSample(sampleColor, 2);
			LCD.clear();
			LCD.drawString("V_1 = "+sampleColor[0], 2, 0);
			LCD.drawString("V_2 = "+sampleColor[1], 2, 2);
			LCD.drawString("V_3 = "+sampleColor[2], 2, 4);
		}
	}

}
