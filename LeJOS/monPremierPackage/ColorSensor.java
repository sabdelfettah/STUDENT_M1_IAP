
package Pack;
import lejos.hardware.Audio;
import lejos.hardware.Battery;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.utility.Delay;

public class ColorSensor {
	private static EV3ColorSensor couleur;
				 
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		EV3 ev3 = (EV3)  BrickFinder.getLocal(); 
		Audio audio = ev3.getAudio();
		LCD.drawString("Bonjour", 5, 4)	;
		Delay.msDelay(3000);
		LCD.clear();
		// Baterry check
		float currentVoltage = Battery.getVoltage();
		LCD.drawString("Voltage = "+currentVoltage, 1, 4);
		if(currentVoltage<2.){
			audio.systemSound(Audio.BUZZ);
		}
		Delay.msDelay(3000);
		LCD.clear();
		LCD.drawString("Veuillez appuyer", 1, 2);
		LCD.drawString("sur", 7, 3);
		LCD.drawString("le bouton Entrer", 1, 4);
		couleur = new EV3ColorSensor(ev3.getPort("S1"));
		SampleProvider color= couleur.getRGBMode();
		float[] tabrgb = new float[color.sampleSize()];	
		int button=0;
		// wait for pressing enter button
		while(true){
			button = Button.waitForAnyPress(2);
			if(button == Button.ID_ENTER) break;
		}
		LCD.clear();
		LCD.drawString("Debut de la", 4, 3)	;
		LCD.drawString("detection !", 4, 5)	;
		Delay.msDelay(2000);
		
		// wait for pressing cancel
		while (true) {
			LCD.clear();
			color.fetchSample(tabrgb, 0);
			  switch ( couleur.getColorID())
		      {
		        case 0:
		          LCD.drawString("Rouge ", 2, 0);
		          break;
		        case 1:
		          LCD.drawString("Vert", 2, 0);
		          break;
		        case 2:
		          LCD.drawString("Bleu", 2, 0);
		          break;
		        case 3:
		          LCD.drawString("Jaune", 2, 0);
		          break;
		        case 6:
		          LCD.drawString("Blanc", 2, 0);
		          break;
		        case 7:
		          LCD.drawString("Noire", 2, 0);
		          break;
		        default:
		          LCD.drawString("Id : "+couleur.getColorID(), 2, 2);
		         
		      }	
				LCD.drawString("R = "+(tabrgb[0]), 2, 2);
				LCD.drawString("V = "+(tabrgb[1]), 2, 4);
				LCD.drawString("B = "+(tabrgb[2]), 2, 6);

				Delay.msDelay(1000);
				button = Button.waitForAnyPress(2);
				if(button == Button.ID_ESCAPE) break;
		}
		LCD.clear();
		LCD.drawString("Fini", 7, 4);
		Delay.msDelay(3000);
	}

}