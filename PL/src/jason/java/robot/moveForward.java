// Internal action code for project jasonTestProgram

package robot;

import jason.*;
import jason.asSemantics.*;
import jason.asSyntax.*;

public class moveForward extends DefaultInternalAction {
	
	private static InternalAction singleton = null;
	
	public static InternalAction create(){
		if(singleton==null)
			singleton = new moveForward();
		return singleton;
	}

    @Override
    public Object execute(TransitionSystem ts, Unifier un, Term[] args) throws Exception {
        // execute the internal action
//        ts.getAg().getLogger().info("executing internal action 'robot.moveForward'");
//        if (true) { // just to show how to throw another kind of exception
//            throw new JasonException("not implemented!");
//        }
    	System.out.println("Working ...");
        //EV3Class.instance().runOnRMI();
        //EV3Class.instance().rubOnNXT();
        // everything ok, so returns true
        System.out.println("Finished.");
        return true;
    }
}
