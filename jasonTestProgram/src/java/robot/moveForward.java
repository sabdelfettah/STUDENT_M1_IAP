package robot;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.StringTermImpl;
import jason.asSyntax.Term;

public class moveForward extends DefaultInternalAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2204311393918647788L;
	Move move;
	Thread thread;
	
	public moveForward(){
		move = new Move();
		thread = new Thread(move);
		thread.start();
	}
	
	public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
		return un.unifies(new StringTermImpl("The model is "+System.getProperty("sun.arch.data.model")), args[0]);
	}

}
