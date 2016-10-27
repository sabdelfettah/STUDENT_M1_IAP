package test;

import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.EV3;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.filter.LinearCalibrationFilter;
import lejos.utility.Delay;

public class ThirdProgramV2 {

	
	public static void main(String[] args) {
		EV3 ev3 = (EV3) BrickFinder.getLocal();
		EV3ColorSensor colorSensor = new EV3ColorSensor(ev3.getPort("S2"));
		//colorSensor.setCurrentMode("RGB");
		SampleProvider sp = colorSensor.getMode("RGB");
		LinearCalibrationFilter lcf = new LinearCalibrationFilter(sp);
		lcf.setCalibrationType(LinearCalibrationFilter.OFFSET_AND_SCALE_CALIBRATION);
		float[] tmp = new float[sp.sampleSize()];
		System.out.println("O="+lcf.getOffsetCorrection()[0]+"_"+lcf.getOffsetCorrection()[1]+"_"+lcf.getOffsetCorrection()[2]);
		System.out.println("S="+lcf.getScaleCorrection()[0]+"_"+lcf.getScaleCorrection()[1]+"_"+lcf.getScaleCorrection()[2]);
		//System.out.println("O = "+lcf.getOffsetCorrection()[0]+" ; t = "+lcf.getOffsetCorrection().length);
		//System.out.println("S = "+lcf.getScaleCorrection()[0]+" ; t = "+lcf.getScaleCorrection().length);
		lcf.setTimeConstant(0);
		float[] colorMin = {(float) 1.0, (float) 0.0, (float) 0.0};
		float[] colorMax = {(float) 1.0, (float) 0.0, (float) 0.0}; 
		lcf.setScaleCalibration(colorMin, colorMax);
		sp.fetchSample(tmp, 0);
		System.out.println("V = "+tmp[0]+" ; "+tmp[1]+" ; "+tmp[2]);
		Delay.msDelay(5000);
		lcf.startCalibration();
		lcf.fetchSample(tmp, 0);
		lcf.stopCalibration();
		Delay.msDelay(5000);
		//System.out.println("O = "+lcf.getOffsetCorrection()[0]);
		//System.out.println("S = "+lcf.getScaleCorrection()[0]);
		System.out.println("O="+lcf.getOffsetCorrection()[0]+"_"+lcf.getOffsetCorrection()[1]+"_"+lcf.getOffsetCorrection()[2]);
		System.out.println("S="+lcf.getScaleCorrection()[0]+"_"+lcf.getScaleCorrection()[1]+"_"+lcf.getScaleCorrection()[2]);
		Delay.msDelay(5000);
		sp.fetchSample(tmp, 0);
		//System.out.println("V = "+tmp.length);
		System.out.println("V = "+tmp[0]+" ; "+tmp[1]+" ; "+tmp[2]);
		Delay.msDelay(10000);
	}
}
