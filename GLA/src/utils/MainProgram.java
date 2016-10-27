package utils;

import java.util.Locale;

import javax.swing.JFrame;

import lang.Langage;
import mvc.model.Diagram;
import mvc.model.Element;
import mvc.model.ModelController;
import mvc.view.MainFrame;

public class MainProgram {
	
	// for test
	private static void init(){
		ModelController.createNewProject(null);
//		ModelController.createNewDiagram(Diagram.USERCASE_DIAGRAM);
//		ModelController.setElementToDraw(Element.ACTOR);
		ModelController.createNewDiagram(Diagram.CLASS_DIAGRAM, null);
		ModelController.setElementToDraw(Element.CLASS);
		ModelController.addElementToWorkingDiagram(280, 230);
	}

	// MAIN
	public static void main (String[] args){
		MainFrame.getInstance().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		WindowManaging.setWindowListener();
		//
		Langage.ModifieLangage(Locale.FRENCH);
		MainFrame.getInstance().setVisible(true);
		//
		//init();
		//ModelController.openAProject();
	}

}
