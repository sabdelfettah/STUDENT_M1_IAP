package monPremierPackage;

import lejos.hardware.BrickFinder;
import lejos.hardware.ev3.EV3;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.MotorPort;
import lejos.robotics.RegulatedMotor;
import lejos.utility.Delay;

// tourne le moteur 3 fois dans le sens
// des aiguilles d'une montre à une vitesse
// de 2 tours/s (720 degres/s),
// attend 1 seconde puis retourne 3 fois
// mais cette fois dans le sens contraire
// des aiguilles d'une montre
public class Program2 {
	
	public static void main(String[] args) {
		EV3 ev3 = (EV3) BrickFinder.getLocal();
		// Faire attention au port utilisé.
		RegulatedMotor m = new EV3LargeRegulatedMotor(MotorPort.A);
		// max 900
		m.setSpeed(720);
		m.rotate(1080);
		Delay.msDelay(1000);
		m.setSpeed(720);
		// le - indique le sens contraire des
		// aiguilles d'une montre
		m.rotate(-1080);
	}

}
