package mvc.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

import lang.Langage;
import mvc.model.Attribute;
import mvc.model.Diagram;
import mvc.model.Element;
import mvc.model.Method;
import mvc.model.ModelController;
import mvc.model.Relation;
import mvc.view.Modifier;
import utils.WindowManaging;

public class ActionController implements ActionListener {

	public static final String NOTHING = "Nothing";
	public static final String CREATE_NEW_PROJECT = "createNewProject";
	public static final String ADD_C_DIAGRAM = "addClassDiagram";
	public static final String ADD_UC_DIAGRAM = "addUseCaseDiagram";
	public static final String OPEN_PROJECT = "openProject";
	public static final String OPEN_DIAGRAM = "openDiagram";
	public static final String SAVE_WORK = "saveWork";
	public static final String SAVE_AS_PROJECT = "saveAsProject";
	public static final String SAVE_AS_DIAGRAM = "saveAs_Diagram";
	public static final String CLOSE = "close";
	public static final String QUIT = "quit";
	public static final String ACCESS_DIAGRAM = "accessDiagram";
	public static final String CLOSE_DIAGRAM = "closeDiagram";
	public static final String GENERATE_JAVA_CODE = "generateJavaCode";
	public static final String GENERATE_CPP_CODE = "generateCppCode";
	public static final String EXPORT_AS_PNG = "exportAsPNG";
	public static final String EXPORT_AS_PDF = "exportAsPDF";
	//
	public static final String UNDO = "undo";
	public static final String REDO = "redo";
	public static final String SELECT_ALL = "selectAll";
	public static final String CUT = "cut";
	public static final String COPY = "copy";
	public static final String PASTE = "paste";
	public static final String DELETE = "delete";
	//
	public static final String SET_ELEMENT_TO_CLASS = "setElementToClass";
	public static final String SET_ELEMENT_TO_ACTOR = "setElementToAction";
	public static final String SET_ELEMENT_TO_ACTION = "setElementToActor";
	public static final String SET_ELEMENT_TO_USECASE = "setElementToUseCase";
	//
	public static final String SET_RELATION_TO_HERITAGE = "setRelationToHeritage";
	public static final String SET_RELATION_TO_AGREGATION = "setRelationToAgregation";
	public static final String SET_RELATION_TO_COMPOSITION = "setRelationToComposition";
	//
	public static final String ASK_FOR_ADDING_ATTRIBUTE = "askingForAddingAnAttribute";
	public static final String ASK_FOR_ADDING_METHOD = "askingForAddingAMethod";
	//
	public static final String GROUP_FIGURES = "group";
	public static final String UNGROUP_FIGURES = "ungroup";
	//
	public static final String HELP = "help";
	public static final String ABOUT = "about";
	//
	public static final String SET_LANGAGE_TO_FR = "setLanguageToFr";
	public static final String SET_LANGAGE_TO_EN = "setLanguageToEn";
	public static final String SET_LANGAGE_TO_ES = "setLanguageToEs";
	//
	public static final String ADD_ATTRIBUTE = "addAnAttribute";
	public static final String ADD_METHOD = "addAMethod";
	public static final String ALTER_ATTRIBUTE = "alterAnAttribute";
	public static final String ALTER_METHOD = "alterAMethod";
	public static final String CANCEL_EDITING = "cancelEditing";
	public static final String APPLY_EDITION = "applyEdition";
	//
	private static ActionController instance = null;

	//

	public static ActionController getInstance() {
		if (instance == null)
			instance = new ActionController();
		return instance;
	}

	private ActionController() {

	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand().equals(QUIT)) {
			WindowManaging.onClose();
		} else if (arg0.getActionCommand().equals(ABOUT)) {
			WindowManaging.showAbout();
		} else if (arg0.getActionCommand().equals(HELP)) {
			WindowManaging.lunchHelp();
		} else if (arg0.getActionCommand().equals(CREATE_NEW_PROJECT)) {
			ModelController.createNewProject(null);
		} else if (arg0.getActionCommand().equals(SAVE_WORK)) {
			ModelController.saveCurrentWork();
		} else if (arg0.getActionCommand().equals(SAVE_AS_PROJECT)) {
			ModelController.saveProjectAs();
		} else if (arg0.getActionCommand().equals(SAVE_AS_DIAGRAM)) {
			ModelController.saveDiagramAs();
		} else if (arg0.getActionCommand().equals(OPEN_PROJECT)) {
			ModelController.openAProject();
		} else if (arg0.getActionCommand().equals(OPEN_DIAGRAM)) {
			ModelController.openADiagram();
		} else if (arg0.getActionCommand().equals(CLOSE)) {
			ModelController.close();
		} else if (arg0.getActionCommand().equals(ADD_C_DIAGRAM)) {
			ModelController.createNewDiagram(Diagram.CLASS_DIAGRAM, null);
		} else if (arg0.getActionCommand().equals(ADD_UC_DIAGRAM)) {
			ModelController.createNewDiagram(Diagram.USERCASE_DIAGRAM, null);
		} else if (arg0.getActionCommand().equals(UNDO)) {
			ModelController.getFile().getWorkingOnDiagram().undo();
		} else if (arg0.getActionCommand().equals(REDO)) {
			ModelController.getFile().getWorkingOnDiagram().redo();
		} else if (arg0.getActionCommand().equals(SELECT_ALL)) {
			ModelController.selectAllFigures();
		} else if (arg0.getActionCommand().equals(CUT)) {
			ModelController.cut();
		} else if (arg0.getActionCommand().equals(COPY)) {
			ModelController.copy();
		} else if (arg0.getActionCommand().equals(PASTE)) {
			ModelController.paste();
		} else if (arg0.getActionCommand().equals(DELETE)) {
			ModelController.deleteSelectedFigures();
		} else if (arg0.getActionCommand().equals(GENERATE_JAVA_CODE)) {
			ModelController.generateJavaCode();
		} else if (arg0.getActionCommand().equals(GENERATE_CPP_CODE)) {
			ModelController.generateCppCode();
		} else if (arg0.getActionCommand().equals(SET_ELEMENT_TO_CLASS)) {
			ModelController.setElementToDraw(Element.CLASS);
		} else if (arg0.getActionCommand().equals(SET_ELEMENT_TO_ACTION)) {
			ModelController.setElementToDraw(Element.ACTION);
		} else if (arg0.getActionCommand().equals(SET_ELEMENT_TO_ACTOR)) {
			ModelController.setElementToDraw(Element.ACTOR);
		} else if (arg0.getActionCommand().equals(SET_ELEMENT_TO_USECASE)) {
			ModelController.setElementToDraw(Element.USECASE);
		} else if (arg0.getActionCommand().equals(SET_RELATION_TO_AGREGATION)) {
			ModelController.setRelationToDraw(Relation.AGREGATION);
		} else if (arg0.getActionCommand().equals(SET_RELATION_TO_HERITAGE)) {
			ModelController.setRelationToDraw(Relation.HERITAGE);
		} else if (arg0.getActionCommand().equals(SET_RELATION_TO_COMPOSITION)) {

		} else if (arg0.getActionCommand().equals(ASK_FOR_ADDING_ATTRIBUTE)) {
			Modifier.setMode(Modifier.ADD_ATTRIBUTE_MODE);
		} else if (arg0.getActionCommand().equals(ASK_FOR_ADDING_METHOD)) {
			Modifier.setMode(Modifier.ADD_METHOD_MODE);
		} else if (arg0.getActionCommand().equals(ADD_ATTRIBUTE)) {
			ModelController.addAnAttribute();
		} else if (arg0.getActionCommand().equals(ADD_METHOD)) {
			ModelController.addAMethod();
		} else if (arg0.getActionCommand().equals(ALTER_ATTRIBUTE)) {
			ModelController.alterAttribute((Attribute) Modifier.getInstance().getObject());
		} else if (arg0.getActionCommand().equals(ALTER_METHOD)) {
			ModelController.alterMethod((Method) Modifier.getInstance().getObject());
		} else if (arg0.getActionCommand().equals(CANCEL_EDITING)) {
			ModelController.cancelEditing();
		} else if (arg0.getActionCommand().equals(GROUP_FIGURES)) {
			ModelController.groupFigures();
		} else if (arg0.getActionCommand().equals(UNGROUP_FIGURES)) {
			ModelController.ungroupFigures();
		} else if (arg0.getActionCommand().equals(SET_LANGAGE_TO_FR)) {
			Langage.ModifieLangage(Locale.FRENCH);
		} else if (arg0.getActionCommand().equals(SET_LANGAGE_TO_EN)) {
			Langage.ModifieLangage(Locale.ENGLISH);
		} else if (arg0.getActionCommand().equals(SET_LANGAGE_TO_ES)) {
			Langage.ModifieLangage(new Locale("ES"));
		} else if (arg0.getActionCommand().equals(EXPORT_AS_PDF)) {
			ModelController.exportPDF();
		} else if (arg0.getActionCommand().equals(EXPORT_AS_PNG)) {
			ModelController.exportPNG();
		}
	}

}
