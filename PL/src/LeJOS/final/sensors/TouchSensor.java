package sensors;

import lejos.hardware.sensor.EV3TouchSensor;

public class TouchSensor {

	private static EV3TouchSensor touchSensor ;
	private static float[] sample ;
	
	public static void setTouchSesor(EV3TouchSensor touch ) {
		touchSensor = touch;
		sample = new float[touchSensor.sampleSize()];
	}
	
	public static boolean isPressed (){
		touchSensor.fetchSample(sample, 0);
		return (sample[0] == 1.) ;
	}
	
}
