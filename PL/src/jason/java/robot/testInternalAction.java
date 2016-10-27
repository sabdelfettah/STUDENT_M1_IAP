// Internal action code for project jasonTestProgram

package robot;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class testInternalAction extends DefaultInternalAction {
	
	private static InternalAction singleton = null;
	private static short nbExecutions = 0;
	
	public static InternalAction create(){
		if(singleton == null)
			singleton = new testInternalAction();
		return singleton;
	}
	
	

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
//        ts.getAg().getLogger().info("executing internal action 'robot.testInternalAction'");
//        if (true) { // just to show how to throw another kind of exception
//            throw new JasonException("not implemented!");
//        }
        // everything ok, so returns true
    	nbExecutions++;
        return un.unifies(new StringTermImpl(""+nbExecutions), args[0]);
    }
}
