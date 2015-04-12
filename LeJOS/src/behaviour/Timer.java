package behaviour;

import utils.Controller;
import lejos.robotics.subsumption.Behavior;

public class Timer implements Behavior{
	
	private static Timer timer = null;
	private boolean suppress = false;
	private long time = System.currentTimeMillis();
	
	public static Timer instance(){
		if(timer == null)
			timer = new Timer();
		return timer;
	}
	
	private Timer(){
		
	}

	@Override
	public boolean takeControl() {
		return (Controller.isCatched() && (System.currentTimeMillis() - time > 0) && (System.currentTimeMillis() - time < 200));
	}

	@Override
	public void action() {
		time = System.currentTimeMillis();
		while(!suppress)
			Thread.yield();
		Controller.setOkToRealease(true);
	}

	@Override
	public void suppress() {
		suppress = true;
	}

}
