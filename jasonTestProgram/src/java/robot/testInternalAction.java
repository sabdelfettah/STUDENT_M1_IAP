package robot;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.StringTermImpl;
import jason.asSyntax.Term;

public class testInternalAction extends DefaultInternalAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7833373306058727006L;
	TestAction testAction;
	Thread thread;
	
	public testInternalAction(){
		testAction = new TestAction();
		thread = new Thread(testAction);
		thread.start();
	}
	
	public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
		return un.unifies(new StringTermImpl(System.getProperty("sun.arch.data.model")), args[0])
				&& un.unifies(new StringTermImpl(testAction.wasExecuted()+""), args[1])
				&& un.unifies(new StringTermImpl(testAction.wasClosed()+""), args[2]);
	}

}
