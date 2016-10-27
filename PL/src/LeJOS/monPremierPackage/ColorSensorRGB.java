

import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.BaseSensor;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.SensorModes;
import lejos.robotics.ColorAdapter;

public class ColorSensor3 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		EV3 ev3 =(EV3) BrickFinder.getLocal();
		SensorModes sm = new EV3ColorSensor(ev3.getPort("S1"));
		ColorAdapter Col = new ColorAdapter((BaseSensor) sm); 
		lejos.robotics.Color coul ; 
		int button ;
		LCD.drawString("Bonjour", 5, 4)	;

		while (true) {
			
			coul = Col.getColor();
			int R = coul.getRed() ;
			int B = coul.getBlue() ;
			int V = coul.getGreen() ; 
			
			if (( R >=  && R <= 107 ) && ( V >= 30 && V <= 82 )  && ( B >= 2 && B <= 38 ) ) {
				LCD.clear();
				LCD.drawString("Jaune ", 0, 0);
				LCD.drawString("R = "+R, 2, 2);
		    	LCD.drawString("V = "+V, 2, 4);
				LCD.drawString("B = "+B, 2, 6);
				}  
			else if ( ( R >= 30 && R <=100 ) && ( V >= 1 && V <= 13 )  && ( B >= 1 && B <= 8 ) ) {
				LCD.clear();
				LCD.drawString("Rouge", 4, 0);
				LCD.drawString("R = "+R, 2, 2);
				LCD.drawString("V = "+V, 2, 4);
			    LCD.drawString("B = "+B, 2, 6);
				}
			else {
				LCD.clear();
				LCD.drawString("R = "+R, 2, 2);
				LCD.drawString("V = "+V, 2, 4);
				LCD.drawString("B = "+B, 2, 6);
				
				}
			
			
			
			button = Button.waitForAnyPress(2);
			 if (button == Button.ID_ESCAPE ) break ;
			
			}

			LCD.clear();
			LCD.drawString("Fini", 7, 4);
			Delay.msDelay(3000);


		}
		
	}
	

