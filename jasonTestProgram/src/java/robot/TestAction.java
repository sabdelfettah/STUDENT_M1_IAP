package robot;

public class TestAction implements Runnable{
	
	private boolean executed = false;
	private boolean closed = false;
	
	public boolean wasExecuted(){
		return executed;
	}
	
	public boolean wasClosed(){
		return closed;
	}

	public void run() {
		// TODO Auto-generated method stub
		System.out.println("I'm running...");
		executed = true;
		System.out.println("Ended");
	}

}
