package robot;

import jason.asSemantics.DefaultInternalAction;
import jason.asSemantics.TransitionSystem;
import jason.asSemantics.Unifier;
import jason.asSyntax.StringTermImpl;
import jason.asSyntax.Term;

public class distance extends DefaultInternalAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2085244531209350947L;
	Thread thread;
	
	public distance(){
		
	}
	
	public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
		return un.unifies(new StringTermImpl(), args[0]);
	}
	

}
