package programs;





import sensors.ColorSensor;
import lejos.hardware.BrickFinder;
import lejos.hardware.Button;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.LCD;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.utility.Delay;


public class FirstP {


	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args)  {
		// TODO Auto-generated method stub
			
		
		
		EV3 ev3 = (EV3) BrickFinder.getLocal() ;
		ColorSensor.SetColorSensor(new EV3ColorSensor(ev3.getPort("S2")), false) ;
		int button ;
		
		while (true) {
			
			switch ( ColorSensor.getColorId())
		      {
		        case 0:
		          LCD.drawString("Rouge", 3, 4);
		          break;
		        case 1:
			      LCD.drawString("Vert", 3, 4);
			      break;
		        case 2:
				  LCD.drawString("Jaune", 3, 4);
				  break;
		        case 3:
		          LCD.drawString("Blanc", 3, 4);
				  break;
		        case 4:
				  LCD.drawString("Noire", 3, 4);
				  break;
		        default:
		          LCD.drawString("autre chose  ", 3, 4);
		         
		      }	

				Delay.msDelay(1000);
				button = Button.waitForAnyPress(2);
				if(button == Button.ID_ESCAPE) break;
	
		}
		
	}
