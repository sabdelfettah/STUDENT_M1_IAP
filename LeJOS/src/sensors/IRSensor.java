package sensors;

import lejos.hardware.sensor.EV3IRSensor;

public class IRSensor {
	
	private static EV3IRSensor IRSensor ;
	private static float[] sample ;
	private static byte[] commands = new byte[EV3IRSensor.IR_CHANNELS];
	
	public static void setIRSesor(EV3IRSensor IRSen) {
		IRSensor = IRSen;
		sample = new float[IRSensor.sampleSize()];
	}
	
	public static float getDistance () {	
		IRSensor.getRemoteCommands(commands, 0, commands.length);
		IRSensor.fetchSample(sample, 0);
		return (sample[0]) ;
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
