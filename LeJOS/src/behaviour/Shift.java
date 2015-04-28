package behaviour;

import utils.Controller;
import lejos.robotics.subsumption.Behavior;

public class Shift implements Behavior{
	
	private static Shift behaviour = null;
	
	public static Shift instance(){
		if(behaviour==null)
			behaviour = new Shift();
		return behaviour;
	}
	
	private Shift(){
		
	}

	@Override
	public boolean takeControl() {
		if( Controller.isCatched() ){
			return true;
		}
		return false;
	}

	@Override
	public void action() {
	}

	@Override
	public void suppress() {
		
	}

}
