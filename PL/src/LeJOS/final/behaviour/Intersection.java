package behaviour;

import java.util.Random;

import sensors.ColorSensors;
import utils.Controller;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import motors.RegulatedMotors;

public class Intersection implements Behavior {

	private static Intersection behaviour = null;

	public static Intersection instance() {
		if (behaviour == null)
			behaviour = new Intersection();
		return behaviour;
	}

	private Intersection() {

	}

	@Override
	public boolean takeControl() {
		int[] colors = { ColorSensors.WHITE, ColorSensors.BLACK };
		return (ColorSensors.isColorsEquals() && ColorSensors.leftOrRightNotEquals(colors))
				|| (ColorSensors.leftOrRightNotEquals(colors) && !Controller.isOkToAdjust());
	}

	@Override
	public void action() {
		
		//récupérer la couleur de l'intersection 
		int couleur_intersection ;
		if ((couleur_intersection = ColorSensors.getLeftColorId()) != ColorSensors.WHITE) ;
		else couleur_intersection = ColorSensors.getRightColorId() ;
		
		RegulatedMotors.moveForward();
		while(ColorSensors.leftOrRightEquals(couleur_intersection));
		Delay.msDelay(200) ;
		RegulatedMotors.stopMoving();
		
		if (Controller.isCatched()) return ;
		
		switch ((new Random()).nextInt(3)) { 
			case 0 :  
				return ;
			case 1 :
				RegulatedMotors.turnleft() ;
				while (ColorSensors.getLeftColorId() != couleur_intersection) ;
				RegulatedMotors.stopMoving() ;
				
				RegulatedMotors.turnleft() ;
				while (ColorSensors.getLeftColorId() != ColorSensors.WHITE) ;
				RegulatedMotors.stopMoving() ;
				
				return ;
				
			case 2 :
				RegulatedMotors.turnright() ;
				while (ColorSensors.getRightColorId() != couleur_intersection) ;
				RegulatedMotors.stopMoving() ;
				
				RegulatedMotors.turnright() ;
				while (ColorSensors.getRightColorId() != ColorSensors.WHITE) ;
				RegulatedMotors.stopMoving() ;
				
				return ;

			default : return ;
		}
		
	}

	@Override
	public void suppress() {

	}

}
