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

public class ColorSensor {
	
	private static ColorAdapter colorAdapLeft ; 
	private static ColorAdapter colorAdapRight ;
	private static File fileColorLeft ;
	private static File fileColorRight ;
	private static  BufferedWriter wr;
	private static BufferedReader br;
	private static Color color ;
	private static final int nbrTest = 20 ;
	private static final int nbrColor = 5 ;
	private static HashMap<Integer, float []> mapColLeft ; 
	private static HashMap<Integer, float []> mapColRight ;
	
	public static void SetColorSensor(EV3ColorSensor colorsensorleft,EV3ColorSensor colorsensorright) {
		fileColorLeft = new File("FileColorLeft.txt") ;
		fileColorRight = new File("FileColorRight.txt") ;
		colorAdapLeft = new ColorAdapter((BaseSensor) colorsensorleft) ;
		colorAdapRight = new ColorAdapter((BaseSensor) colorsensorright) ;
		mapColLeft = new HashMap<Integer, float []> () ;
		mapColRight = new HashMap<Integer, float []> () ;
	}
	
	
	private static void InitCalibrate(String sensor) {
		int button ;
		LCD.clear() ;
		LCD.drawString("Voulez vous ", 1, 2) ;
		LCD.drawString("calibrer le", 1, 3) ;
		if (sensor.equals("Left")) LCD.drawString("capteur gauche", 1, 4) ;
		else LCD.drawString("capteur dtoit", 1, 4) ;
		while (true) {
			Delay.msDelay(1000);
			button = Button.waitForAnyPress(2);
			if ((button == Button.ID_ESCAPE) || 
			    (button == Button.ID_ENTER) ) break;
		}
		
		if (button == Button.ID_ENTER) {
			if (sensor.equals("Left")) calibrateLeft() ;
			else calibrateRight() ;
			}
		}
	
	
	public static void calibrate () {
	InitCalibrate("Left") ;
	InitCalibrate("Right") ;
	}
	
	
	
	private static void calibrate (String sensor) {
		File theFile;
		int button = 0  ;
		if(sensor.equals("Left"))  theFile = fileColorLeft;
		else theFile = fileColorRight;
		
		if (!theFile.exists()) {
			InitColor (sensor) ;
		}
		else {
			while (true) {
				Delay.msDelay(1000);
				button = Button.waitForAnyPress(2);
				if ((button == Button.ID_ESCAPE) || 
				    (button == Button.ID_ENTER) ) break;
			}
		if (button == Button.ID_ENTER) InitColor (sensor) ;  
		}	
		parse_file(sensor) ;
	}
	
	
	public static void calibrateLeft() {
		calibrate ("Left") ;
	}
	
	public static void calibrateRight() {
		calibrate ("Right") ;
	}
	
	
	public static double getDistane (float [] c1, float [] c2){
		
		return (double) Math.sqrt((Math.pow(c1[0]-c2[0] , 2))+ 
					             (Math.pow(c1[1]-c2[1] , 2))+ 
					             (Math.pow(c1[2]-c2[2] , 2)));
	}
	
	private static String getColorName (String sensor ) {
		int c ; 
		if(sensor.equals("Left")) c = getLeftColorId() ;
		else c = getRightColorId() ;
	
		switch (c)
	      {
	        case 0:
	          return "Rouge" ;
	        case 1:
	        	return "Vert" ;
	        case 2:
			  return "Jaune" ;
	        case 3:
	          return "Blanc" ;
	        case 4:
	        	return "Noire" ;
	        case 5:	
	        	return "Bleu" ;
	        default:
	          return null ;
	      }	
	} 
	
	
public static String getLeftColorName() {
	return getColorName("Left") ;
}	
	
public static String getRightColorName() {
	return getColorName("Right") ;
}		
	
	

private static int getColorId(String sensor) {
	
	
	if(sensor.equals("Left")) color = colorAdapLeft.getColor() ;
	else color = colorAdapRight.getColor() ;
	int R = color.getRed() ;
	int V = color.getGreen() ;
	int B = color.getBlue() ;
	float [] rvb = {R,V,B} ;
	int Couleur = 0 ;
	double distance ;
	double distmin ;
	if(sensor.equals("Left")) distmin  = getDistane(rvb, mapColLeft.get(0)) ; 
	else distmin =  getDistane(rvb, mapColRight.get(0)) ; 
	
	for (int i=1; i < nbrColor ; i++) {
		if(sensor.equals("Left")) distance  = getDistane(rvb, mapColLeft.get(0)) ; 
		else distance =  getDistane(rvb, mapColRight.get(0)) ;
		if (distance < distmin ) {
			distmin = distance ;
			Couleur = i ;
		}
	} 
	return Couleur  ;		
}

	
	
	
	
	
public static int getLeftColorId() {
	return getColorId("Left") ;
}	
	

public static int getRightColorId() {
	return getColorId("Right") ;
}	



	

	private static void InitColor (String sensor) {
		try {
			LCD.clear() ;
			String capteur ;
			File theFile;
			if(sensor.equals("Left")) { theFile = fileColorLeft; capteur = "Gauche" ; }
			else { theFile = fileColorRight; capteur = "Droite" ; }
			theFile.delete() ;
			theFile.createNewFile() ;
			wr = new BufferedWriter(new FileWriter(theFile,true));
			LCD.drawString("Calibrage du", 1, 3) ;
			LCD.drawString("Capteur "+capteur, 1, 4) ;
			Delay.msDelay(2000) ;
			LCD.clear() ;
			LCD.drawString("Detection des", 1, 3) ;
			LCD.drawString("Couleurs", 3, 4) ;
			Delay.msDelay(2000) ;
			Detecte_color ("Rouge", sensor) ;
			Detecte_color ("Vert", sensor) ;
			Detecte_color ("Jaune", sensor) ;
			Detecte_color ("Blanc", sensor) ;
			Detecte_color ("Noire", sensor) ;
			//Detecte_color ("Blue", sensor) ;
			wr.close() ;
			LCD.clear() ;
		}
		catch (IOException e) {} 
	}
	
	
	private static void parse_file (String sensor)  {	
		LCD.clear() ;
	try {	
		
		File theFile;
		
		if(sensor.equals("Left")){
			theFile = fileColorLeft;
		}else{
			theFile = fileColorRight;
		}

		
		
		
		 br = new BufferedReader(new FileReader(theFile));
		 String ligne ; 
		 br.readLine() ;
		 br.readLine() ;
		 String[] s ;
		 int [] rvb = {0, 0, 0} ;
		 float [] tab_moy_rvb ; 
		 int i  = 0;
		 
		  while ((ligne=br.readLine())!=null) { 
			  if (!ligne.equals("}")){
				 s = ligne.split(" ") ;
				 for (int k=0; k<3 ; k++) 
					 rvb[k] = rvb[k] + Integer.parseInt(s[k]) ;
			  }
			  else {
				  br.readLine() ;
				  br.readLine() ;
				  
				  tab_moy_rvb = new float [3] ;
			  	  for (int j=0; j<3; j++) tab_moy_rvb [j] = rvb [j] / nbrTest ; 
			  	  if (sensor.equals("Left")) mapColLeft.put(i, tab_moy_rvb) ;
			  	  else mapColRight.put(i, tab_moy_rvb) ;
				  i++ ;
				  for (int y=0; y<3; y++) rvb[y] = 0 ;
			  }
		  }
		  br.close() ;
	} 
	catch (Exception e) {
		
	}
	
	
	LCD.clear() ;
	LCD.drawString("Fin de ", 3, 3) ;
	LCD.drawString("Calibrage ", 2, 4) ;
	Delay.msDelay(2000) ;	
		  
	}
	
	
	
	
	
	
	
	private static void Detecte_color (String c, String sensor) throws IOException {
			LCD.clear() ;
			LCD.drawString("Detection du ", 3, 1);
			LCD.drawString(c, 6, 2);
			Delay.msDelay(2000) ;
			wr.write(c+"\n"+"{"+"\n") ;
						
			int button=0;
			// wait for pressing enter button
			LCD.drawString("Appuyer sur ok ", 2, 4) ;
			LCD.drawString("pour commencer", 2, 5) ;
			while(true){
				button = Button.waitForAnyPress(2);
				if(button == Button.ID_ENTER) break;
			}
			LCD.clear() ;
			Delay.msDelay(3000) ;
			
			for (int i=0; i<nbrTest; i++){
				LCD.clear() ;
				if (sensor.equals("Left")) color = colorAdapLeft.getColor() ;
				else color = colorAdapRight.getColor() ;
				int R = color.getRed() ;
				int V = color.getGreen() ;
				int B = color.getBlue() ;
				wr.write(R+" "+V+" "+B+"\n") ;
				LCD.drawInt((i+1), 4, 4) ;
				Delay.msDelay(500) ;
			}
			wr.write("}"+"\n") ;	
	}

}
