package sensors;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

import lejos.hardware.Button;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.BaseSensor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.Color;
import lejos.robotics.ColorAdapter;
import lejos.utility.Delay;

public class ColorSensors {

	public static final int WHITE = 0;
	public static final int BLACK = 1;
	public static final int BLUE = 2;
	public static final int RED = 3;
	public static final int GREEN = 4;
	public static final int YELLOW = 5;
	private static ColorAdapter LeftColorAdapter;
	private static ColorAdapter RightColorAdapter;
	private static File fileColorLeft;
	private static File fileColorRight;
	private static BufferedWriter wr;
	private static BufferedReader br;
	private static Color color;
	private static final int nbrTest = 20;
	private static final int nbrColor = 2;
	private static HashMap<Integer, float[]> mapLeftSensor;
	private static HashMap<Integer, float[]> mapRightSensor;

	
	public static void SetColorSensors(EV3ColorSensor leftColorSensor, EV3ColorSensor rightColorSensor) {
		fileColorLeft = new File("FileColorLeft.txt");
		fileColorRight = new File("FileColorRight.txt");
		LeftColorAdapter = new ColorAdapter((BaseSensor) leftColorSensor);
		RightColorAdapter = new ColorAdapter((BaseSensor) rightColorSensor);
		mapLeftSensor = new HashMap<Integer, float[]>();
		mapRightSensor = new HashMap<Integer, float[]>();
	}

	private static void InitCalibrate(String sensor) {
		int button;
		LCD.clear();
		LCD.drawString("Voulez vous ", 1, 2);
		LCD.drawString("calibrer le", 1, 3);
		if (sensor.equals("Left"))
			LCD.drawString("capteur gauche ", 1, 4);
		else
			LCD.drawString("capteur droit ", 1, 4);
		while (true) {
			Delay.msDelay(500);
			button = Button.waitForAnyPress(2);
			if ((button == Button.ID_ESCAPE) || (button == Button.ID_ENTER))
				break;
		}

		if (button == Button.ID_ENTER) {
			if (sensor.equals("Left"))
				calibrate("Left");
			else
				calibrate("Right") ;
		}
		parse_file(sensor) ;
	}

	public static void calibrate() {
		InitCalibrate("Left");
		InitCalibrate("Right");
	}

	private static void calibrate(String sensor) {
		File theFile;
		int button = 0;
		if (sensor.equals("Left")) 
			theFile = fileColorLeft;
		else
			theFile = fileColorRight;

		if (!theFile.exists()) {
			InitColor(sensor);
		} else {
			LCD.clear() ;
			LCD.drawString("Fichier_existant", 1, 1) ;
			LCD.drawString("Voulez vous", 2, 2) ;
			LCD.drawString("Calibrer", 3, 3) ;
			while (true) {
				Delay.msDelay(1000);
				button = Button.waitForAnyPress(2);
				if ((button == Button.ID_ESCAPE) || (button == Button.ID_ENTER))
					break;
			}
			if (button == Button.ID_ENTER)
				InitColor(sensor);
		}
		
	}


	public static double getDistane(float[] c1, float[] c2) {

		return (double) Math.sqrt((Math.pow(c1[0] - c2[0], 2)) + (Math.pow(c1[1] - c2[1], 2))
				+ (Math.pow(c1[2] - c2[2], 2)));
	}

	
	private static String getColorName(String sensor) {
		int c;
		if (sensor.equals("Left"))
			c = getLeftColorId();
		else
			c = getRightColorId();

		switch (c) {
		case RED:
			return "Rouge";
		case GREEN:
			return "Vert";
		case YELLOW:
			return "Jaune";
		case WHITE:
			return "Blanc";
		case BLACK:
			return "Noir";
		case BLUE:
			return "Bleu";
		default:
			return null;
		}
	}
	
	
	
	public static String getLeftColorName() {
		return getColorName("Left");
	}

	public static String getRightColorName() {
		return getColorName("Right");
	}

	private static int getColorId(String sensor) {
		if (sensor.equals("Left"))
			color = LeftColorAdapter.getColor();
		else
			color = RightColorAdapter.getColor();
		
		int R = color.getRed();
		int V = color.getGreen();
		int B = color.getBlue();
		
		
		float[] rvb = { R, V, B };
		int Couleur = 0;
		double distance;
		double distmin;
		if (sensor.equals("Left"))
			distmin = getDistane(rvb, mapLeftSensor.get(0));
		else
			distmin = getDistane(rvb, mapRightSensor.get(0));

		for (int i = 1; i < nbrColor; i++) {
			if (sensor.equals("Left"))
				distance = getDistane(rvb, mapLeftSensor.get(i));
			else
				distance = getDistane(rvb, mapRightSensor.get(i));
			if (distance < distmin) {
				distmin = distance;
				Couleur = i;
			}
		}
		return Couleur;
	}

	public static int getLeftColorId() {
		return getColorId("Left");
	}

	public static int getRightColorId() {
		return getColorId("Right");
	}

	private static void InitColor(String sensor) {
		try {
			LCD.clear();
			String capteur;
			File theFile;
			if (sensor.equals("Left")) {
				theFile = fileColorLeft;
				capteur = "Gauche";
			} else {
				theFile = fileColorRight;
				capteur = "Droite";
			}
			theFile.delete();
			theFile.createNewFile();
			wr = new BufferedWriter(new FileWriter(theFile, true));
			LCD.drawString("Calibrage du", 1, 3);
			LCD.drawString("Capteur " + capteur, 1, 4);
			Delay.msDelay(2000);
			LCD.clear();
			LCD.drawString("Detection des", 1, 3);
			LCD.drawString("Couleurs", 3, 4);
			Delay.msDelay(2000);
			Detecte_color("Blanc", sensor);
			Detecte_color("Noir", sensor);
			Detecte_color ("Blue", sensor) ;
			Detecte_color("Rouge", sensor);
			Detecte_color("Vert", sensor);
			Detecte_color("Jaune", sensor);
			wr.close();
			LCD.clear();
		} catch (IOException e) {
		}
	}
	
	
	private static void pars (File theFile, String sensor) {
		LCD.clear();
		try {
		br = new BufferedReader(new FileReader(theFile));
		String ligne;
		br.readLine();
		br.readLine();
		String[] s;
		int[] rvb = { 0, 0, 0 };
		float[] tab_moy_rvb;
		int i = 0;

		while ((ligne = br.readLine()) != null) {
			if (!ligne.equals("}")) {
				s = ligne.split(" ");
				for (int k = 0; k < 3; k++)
					rvb[k] = rvb[k] + Integer.parseInt(s[k]);
			} else {
				br.readLine();
				br.readLine();

				tab_moy_rvb = new float[3];
				for (int j = 0; j < 3; j++)
					tab_moy_rvb[j] = rvb[j] / nbrTest;
				if (sensor.equals("Left"))
					mapLeftSensor.put(i, tab_moy_rvb);
				else
					mapRightSensor.put(i, tab_moy_rvb);
				i++;
				for (int y = 0; y < 3; y++)
					rvb[y] = 0;
			}
		}
		br.close();
	} catch (Exception e) {

	}

	LCD.clear();
	LCD.drawString("Fin de ", 3, 3);
	LCD.drawString("Calibrage ", 2, 4);
	Delay.msDelay(2000);
		
	}

	
	private static void parse_file(String sensor) {
		
			if (sensor.equals("Left")) {
				pars(fileColorLeft, sensor) ;
			} else {
				pars(fileColorRight, sensor) ;
			}

		
	}

	private static void Detecte_color(String c, String sensor) throws IOException {
		LCD.clear();
		LCD.drawString("Detection du ", 3, 1);
		LCD.drawString(c, 6, 2);
		Delay.msDelay(2000);
		wr.write(c + "\n" + "{" + "\n");

		int button = 0;
		// wait for pressing enter button
		LCD.drawString("Appuyer sur ok ", 2, 4);
		LCD.drawString("pour commencer", 2, 5);
		while (true) {
			button = Button.waitForAnyPress(2);
			if (button == Button.ID_ENTER)
				break;
		}
		LCD.clear();
		Delay.msDelay(3000);

		for (int i = 0; i < nbrTest; i++) {
			LCD.clear();
			if (sensor.equals("Left"))
				color = LeftColorAdapter.getColor();
			else
				color = RightColorAdapter.getColor();
			int R = color.getRed();
			int V = color.getGreen();
			int B = color.getBlue();
			wr.write(R + " " + V + " " + B + "\n");
			LCD.drawInt((i + 1), 4, 4);
			Delay.msDelay(500);
		}
		wr.write("}" + "\n");
	}

	public static boolean leftColorEqualsTo(int ColorID) {
		return getLeftColorId() == ColorID;
	}

	public static boolean rightColorEqualsTo(int ColorID) {
		return getRightColorId() == ColorID;
	}

	public static boolean isColorsDifferent() {
		return getLeftColorId() != getRightColorId();
	}

}




