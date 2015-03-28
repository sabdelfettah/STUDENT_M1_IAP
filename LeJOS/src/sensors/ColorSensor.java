package sensors;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
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
	
	private static ColorAdapter coloradap ; 
	private static File fichier_couleur ;
	private static  BufferedWriter wr;
	private static BufferedReader br;
	private static Color couleur ;
	private static final int nbr_test = 20 ;
	private static HashMap<Integer, float []> map_col ; 
	
	
	public static void SetColorSensor(EV3ColorSensor colorsensor, boolean test) {
		fichier_couleur = new File("Fichier_Color.txt") ;
		coloradap = new ColorAdapter((BaseSensor) colorsensor) ;
		map_col = new HashMap<Integer, float []> () ;
		
		
		if (!fichier_couleur.exists() || test ) {
			InitColor ();
			parse_file() ;
		}
		else parse_file() ;
		
	}
	
	public static double getDistane (float [] c1, float [] c2){
		
		return (double) Math.sqrt((Math.pow(c1[0]-c2[0] , 2))+ 
					             (Math.pow(c1[1]-c2[1] , 2))+ 
					             (Math.pow(c1[2]-c2[2] , 2)));
	}
	
	private static int getColorId() {
		
		couleur = coloradap.getColor() ;
		int R = couleur.getRed() ;
		int V = couleur.getGreen() ;
		int B = couleur.getBlue() ;
		float [] rvb = {R,V,B} ;
		int Couleur = 0 ;
		double distance ;
		double distmin = getDistane(rvb, map_col.get(0)) ; 
		for (int i=0; i < 5 ; i++) {
			distance = getDistane(rvb, map_col.get(i)) ; 
			if (distance < distmin ) {
				
				distmin = distance ;
				Couleur = i ;
			}
		} 
		
		return Couleur  ;		
	}

	
	
	private static void InitColor () {
		try {
			fichier_couleur.delete() ;
			fichier_couleur.createNewFile() ;
			wr = new BufferedWriter(new FileWriter(fichier_couleur,true));	
			LCD.drawString("Detection des", 1, 3) ;
			LCD.drawString("Couleurs", 3, 4) ;
			Delay.msDelay(2000) ;
			Detecte_color ("Rouge") ;
			Detecte_color ("Vert") ;
			Detecte_color ("Jaune") ;
			Detecte_color ("Blanc") ;
			//Detecte_color ("Blue") ;
			Detecte_color ("Noire") ;

			wr.close() ;
			LCD.clear() ;
		}
		catch (IOException e) {} 
	}
	
	
	private static void parse_file ()  {	
	
	try {	
		
		 br = new BufferedReader(new FileReader(fichier_couleur));
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
			  	  for (int j=0; j<3; j++) tab_moy_rvb [j] = rvb [j] / nbr_test ; 
			  	  map_col.put(i, tab_moy_rvb) ;
				  i++ ;
				  for (int y=0; y<3; y++) rvb[y] = 0 ;
			  }
		  }
		  br.close() ;
	} 
	catch (Exception e) {
		
	}
		  
		
		  
	}
	
	
	private static void Detecte_color (String c) throws IOException {
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
			
			for (int i=0; i<nbr_test; i++){
				LCD.clear() ;
				couleur = coloradap.getColor() ;
				int R = couleur.getRed() ;
				int V = couleur.getGreen() ;
				int B = couleur.getBlue() ;
				wr.write(R+" "+V+" "+B+"\n") ;
				LCD.drawInt((i+1), 4, 4) ;
				Delay.msDelay(500) ;
			}
			wr.write("}"+"\n") ;	
	}

}
