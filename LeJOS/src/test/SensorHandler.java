package test;

import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.LinearCalibrationFilter;
import lejos.robotics.filter.MeanFilter;

public class SensorHandler implements Runnable {
	
	private static LinearCalibrationFilter colorCalibrationFilter;
	private static int FILTER_SIZE = 10;
	
	public SensorHandler(EV3ColorSensor colorSensor){
		//colorSensor.setCurrentMode("RGB");
		SampleProvider colorProvider = colorSensor.getMode("RGB");
		SampleProvider colorFilter = new MeanFilter(colorProvider, FILTER_SIZE);;
		colorCalibrationFilter = new LinearCalibrationFilter(colorFilter);
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		colorCalibrationFilter.startCalibration();
		colorCalibrationFilter.setCalibrationType(LinearCalibrationFilter.OFFSET_CALIBRATION);
		//
		float[] tmp = new float[3];
		//
		for(int i = 0 ; i < 500; i++){
			colorCalibrationFilter.fetchSample(tmp, 0);
		}
		colorCalibrationFilter.stopCalibration();

	}
	
	public synchronized float[] getValues(){
		float[] result = new float[3];
		colorCalibrationFilter.fetchSample(result, 0);
		return result;
	}

}
