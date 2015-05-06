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
	private static final short leftID = 0;
	private static final short rightID = 1;
	private static ColorAdapter LeftColorAdapter;
	private static ColorAdapter RightColorAdapter;
	private static File fileColorLeft;
	private static File fileColorRight;
	private static BufferedWriter wr;
	private static BufferedReader br;
	private static Color color;
	private static final int nbrTest = 20;
	private static final int nbrColor = 5;
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

	// comparaison methods
	public static boolean leftColorEqualsTo(int ColorID) {
		return getLeftColorId() == ColorID;
	}

	public static boolean rightColorEqualsTo(int ColorID) {
		return getRightColorId() == ColorID;
	}

	public static boolean isColorsDifferent() {
		return getLeftColorId() != getRightColorId();
	}

	//

	// calibrating methods
	private static void InitCalibrate(short sensorID) {
		int button;
		LCD.clear();
		LCD.drawString("Voulez vous ", 1, 2);
		LCD.drawString("calibrer le", 1, 3);
		if (sensorID == leftID)
			LCD.drawString("capteur gauche", 1, 4);
		else
			LCD.drawString("capteur droit", 1, 4);
		while (true) {
			Delay.msDelay(1000);
			button = Button.waitForAnyPress(2);
			if ((button == Button.ID_ESCAPE) || (button == Button.ID_ENTER))
				break;
		}

		if (button == Button.ID_ENTER) {
			if (sensorID == leftID)
				calibrateLeft();
			else
				calibrateRight();
		}
	}

	public static void calibrate() {
		InitCalibrate(leftID);
		InitCalibrate(rightID);
	}

	private static void calibrate(short sensorID) {
		File theFile;
		int button = 0;
		if (sensorID == leftID)
			theFile = fileColorLeft;
		else
			theFile = fileColorRight;

		if (!theFile.exists()) {
			InitColor(sensorID);
		} else {
			while (true) {
				Delay.msDelay(1000);
				button = Button.waitForAnyPress(2);
				if ((button == Button.ID_ESCAPE) || (button == Button.ID_ENTER))
					break;
			}
			if (button == Button.ID_ENTER)
				InitColor(sensorID);
		}
		parse_file(sensorID);
	}

	private static void calibrateLeft() {
		calibrate(leftID);
	}

	private static void calibrateRight() {
		calibrate(rightID);
	}

	private static void InitColor(short sensorID) {
		try {
			LCD.clear();
			String capteur;
			File theFile;
			if (sensorID == leftID) {
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
			Detecte_color("Blanc", sensorID);
			Detecte_color("Noir", sensorID);
			// Detecte_color ("Blue", sensorID) ;
			// Detecte_color("Rouge", sensorID);
			// Detecte_color("Vert", sensorID);
			// Detecte_color("Jaune", sensorID);
			wr.close();
			LCD.clear();
		} catch (IOException e) {
		}
	}

	private static void parse_file(short sensorID) {
		if (sensorID == leftID) {
			pars(fileColorLeft, sensorID);
		} else {
			pars(fileColorRight, sensorID);
		}
	}

	private static void pars(File theFile, short sensorID) {
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
					if (sensorID == leftID)
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

	// detecting methods
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

	private static int getColorId(short sensorID) {
		if (sensorID == leftID)
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
		if (sensorID == leftID)
			distmin = getDistane(rvb, mapLeftSensor.get(0));
		else
			distmin = getDistane(rvb, mapRightSensor.get(0));

		for (int i = 1; i < nbrColor; i++) {
			if (sensorID == leftID)
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
		return getColorId(leftID);
	}

	public static int getRightColorId() {
		return getColorId(rightID);
	}

	private static void Detecte_color(String c, short sensorID) throws IOException {
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
			if (sensorID == leftID)
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
}