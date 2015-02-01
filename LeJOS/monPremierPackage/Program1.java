package monPremierPackage;

import lejos.hardware.Audio;
import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.EV3;
import lejos.hardware.lcd.TextLCD;
import lejos.utility.Delay;

// Affiche "Hello !", émet un son (beep),
// rajoute "My name is ..." à l'affichage
// et enfin attend 4 secondes avant de finir
public class Program1 {

	public static void main(String[] args) {
		EV3 ev3 = (EV3) BrickFinder.getLocal();
		Audio audio = ev3.getAudio();
		TextLCD text = ev3.getTextLCD();
		text.drawString("Hello !", 2, 2);
		audio.systemSound(Audio.BEEP);
		text.drawString("My name is "+ev3.getName(), 2, 4);
		Delay.msDelay(4000);
	}

}
