package test;

import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.EV3;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.LinearCalibrationFilter;
import lejos.robotics.filter.MeanFilter;
import lejos.utility.Delay;

public class ThirdProgram {
	
	public static void main(String[] args) {
		EV3 ev3 = (EV3) BrickFinder.getLocal();
		EV3ColorSensor colorSensor = new EV3ColorSensor(ev3.getPort("S2"));
		SensorHandler sh = new SensorHandler(colorSensor);
		Thread thread = new Thread(sh);
		thread.start();
		System.out.println("Vals = "+sh.getValues());
	}
	
}
