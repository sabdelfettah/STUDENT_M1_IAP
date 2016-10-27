package mvc.view;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.Locale;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import lang.Langage;
import mvc.controller.ActionController;
import mvc.model.Diagram;
import mvc.model.ModelController;
import mvc.model.UMLFile;

public class MenuBar extends JMenuBar implements Observer, ViewActionController{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5555470722482290885L;
	private static MenuBar instance = null;
	private JMenu file = new JMenu(Langage.getString("MenuBar.file")); //$NON-NLS-1$
	private JMenu edition = new JMenu(Langage.getString("MenuBar.edition")); //$NON-NLS-1$
	private JMenu tools = new JMenu(Langage.getString("MenuBar.tools")); //$NON-NLS-1$
	private JMenu preferences = new JMenu(Langage.getString("MenuBar.preferences")); //$NON-NLS-1$
	private JMenu help = new JMenu(Langage.getString("MenuBar.help")); //$NON-NLS-1$
	// file
	private JMenu newThing = new JMenu(Langage.getString("MenuBar.new")); //$NON-NLS-1$
	private JMenuItem newProject = new JMenuItem(Langage.getString("MenuBar.project")); //$NON-NLS-1$
	private JMenuItem newCDiagram = new JMenuItem(Langage.getString("MenuBar.classDiagram")); //$NON-NLS-1$
	private JMenuItem newUCDiagram = new JMenuItem(Langage.getString("MenuBar.useCaseDiagram")); //$NON-NLS-1$
	private JMenuItem openProject = new JMenuItem(Langage.getString("MenuBar.OpenProject")); //$NON-NLS-1$
	private JMenuItem openDiagram = new JMenuItem(Langage.getString("MenuBar.OpenDiagram")); //$NON-NLS-1$
	private JMenuItem save = new JMenuItem(Langage.getString("MenuBar.Save")); //$NON-NLS-1$
	private JMenuItem saveAsProject = new JMenuItem(Langage.getString("MenuBar.SaveAs")); //$NON-NLS-1$
	private JMenuItem saveAsDiagram = new JMenuItem(Langage.getString("MenuBar.SaveDiagramAs")); //$NON-NLS-1$
	private JMenuItem close = new JMenuItem(Langage.getString("MenuBar.CloseProject")); //$NON-NLS-1$
	// edition
	private JMenuItem undo = new JMenuItem(Langage.getString("MenuBar.Undo")); //$NON-NLS-1$
	private JMenuItem redo = new JMenuItem(Langage.getString("MenuBar.Redo")); //$NON-NLS-1$
	private JMenuItem selectAll = new JMenuItem(Langage.getString("MenuBar.SelectAll")); //$NON-NLS-1$
	private JMenuItem cut = new JMenuItem(Langage.getString("MenuBar.Cut")); //$NON-NLS-1$
	private JMenuItem copy = new JMenuItem(Langage.getString("MenuBar.Copy")); //$NON-NLS-1$
	private JMenuItem paste = new JMenuItem(Langage.getString("MenuBar.Paste")); //$NON-NLS-1$
	private JMenuItem delete = new JMenuItem(Langage.getString("MenuBar.Delete")); //$NON-NLS-1$
	// tools
	private JMenu generateCode = new JMenu(Langage.getString("MenuBar.GenerateCode")); //$NON-NLS-1$
	private JMenuItem JavaCode = new JMenuItem("Java"); //$NON-NLS-1$
	private JMenuItem CppCode = new JMenuItem("C++"); //$NON-NLS-1$
	private JMenu export = new JMenu(Langage.getString("MenuBar.Export")); //$NON-NLS-1$
	private JMenuItem PNG = new JMenuItem("PNG"); //$NON-NLS-1$
	private JMenuItem PDF = new JMenuItem("PDF"); //$NON-NLS-1$
	private JMenuItem quit = new JMenuItem(Langage.getString("MenuBar.Quit")); //$NON-NLS-1$
	// preferences
	private JMenu langage = new JMenu(Langage.getString("MenuBar.langage")); //$NON-NLS-1$
	private JCheckBoxMenuItem fr = new JCheckBoxMenuItem(Langage.getString("MenuBar.french"));
	private JCheckBoxMenuItem en = new JCheckBoxMenuItem(Langage.getString("MenuBar.english"));
	private JCheckBoxMenuItem es = new JCheckBoxMenuItem(Langage.getString("MenuBar.spanish"));
	// help
	private JMenuItem helpMe = new JMenuItem(Langage.getString("MenuBar.HelpMe")); //$NON-NLS-1$
	private JMenuItem about = new JMenuItem(Langage.getString("MenuBar.About")); //$NON-NLS-1$
	
	public static MenuBar getInstance(){
		if(instance == null)
			instance = new MenuBar();
		return instance;
	}
	
	private MenuBar(){
		super();
		//
		openProject.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
		save.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
		//
		undo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, InputEvent.CTRL_MASK));
		redo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, InputEvent.CTRL_MASK));
		selectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));
		cut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_MASK));
		copy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
		paste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, InputEvent.CTRL_MASK));
		delete.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		quit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
		//
		newThing.add(newProject);
		newThing.add(newCDiagram);
		newThing.add(newUCDiagram);
		//
		generateCode.add(JavaCode);
		generateCode.add(CppCode);
		//
		export.add(PNG);
		export.add(PDF);
		////
		file.add(newThing);
		file.add(openProject);
		file.add(openDiagram);
		file.add(save);
		file.add(saveAsProject);
		file.add(saveAsDiagram);
		file.add(close);
		file.add(new JSeparator());
		file.add(quit);
		add(file);
		//
		edition.add(undo);
		edition.add(redo);
		edition.add(new JSeparator());
		edition.add(selectAll);
		edition.add(new JSeparator());
		edition.add(cut);
		edition.add(copy);
		edition.add(paste);
		edition.add(new JSeparator());
		edition.add(delete);
		add(edition);
		//
		tools.add(generateCode);
		tools.add(export);
		add(tools);
		//
		langage.add(fr);
		langage.add(en);
		langage.add(es);
		preferences.add(langage);
		add(preferences);
		//
		help.add(helpMe);
		help.add(about);
		add(help);
		//
		setEnabledProjectButtons(false);
		setEnabledDiagramButtons(false);
		setEnabledGeneratingCodeButtons(false);
		setEnabledClipboardButtons(false);
		undo.setEnabled(false);
		redo.setEnabled(false);
		selectAll.setEnabled(false);
		close.setEnabled(false);
		save.setEnabled(false);
		this.linkComponentsWithTheActionController(ActionController.getInstance());
	}

	private void setEnabledProjectButtons(boolean enabled){
		saveAsProject.setEnabled(enabled);
	}
	
	private void setEnabledDiagramButtons(boolean enabled){
		saveAsDiagram.setEnabled(enabled);
		PDF.setEnabled(enabled);
		PNG.setEnabled(enabled);
	}
	
	private void setEnabledClipboardButtons(boolean enabled){
		cut.setEnabled(enabled);
		copy.setEnabled(enabled);
		paste.setEnabled(enabled && ModelController.canPaste());
		delete.setEnabled(enabled);
	}
	
	private void setEnabledGeneratingCodeButtons(boolean enabled){
		JavaCode.setEnabled(enabled);
		CppCode.setEnabled(enabled);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		UMLFile file = (UMLFile) ModelController.getFile();
		if(file != null){
			save.setEnabled(ModelController.filesChanged().size() > 0);
			close.setEnabled(true);
			setEnabledProjectButtons(file.getChilds() != null);
			setEnabledDiagramButtons(file.getChilds() == null || file.getWorkingOnDiagram() != null);
			setEnabledClipboardButtons(ModelController.isFiguresSelected() || ModelController.isAFigureSelected());
			if(file.getWorkingOnDiagram() != null && file.getWorkingOnDiagram().getType() == Diagram.CLASS_DIAGRAM){
				setEnabledGeneratingCodeButtons(true);
			}else{
				setEnabledGeneratingCodeButtons(false);
			}
			undo.setEnabled(file.getWorkingOnDiagram() != null && file.getWorkingOnDiagram().canUndo());
			redo.setEnabled(file.getWorkingOnDiagram() != null && file.getWorkingOnDiagram().canRedo());
			selectAll.setEnabled(file.getWorkingOnDiagram() != null && file.getWorkingOnDiagram().getFigures().size() > 0);
		}else{
			close.setEnabled(false);
			undo.setEnabled(false);
			redo.setEnabled(false);
			selectAll.setEnabled(false);
			setEnabledProjectButtons(false);
			setEnabledDiagramButtons(false);
			setEnabledClipboardButtons(false);
			setEnabledGeneratingCodeButtons(false);
		}
	}

	@Override
	public void linkComponentsWithTheActionController(ActionController controller) {
		newProject.addActionListener(controller);
		newProject.setActionCommand(ActionController.CREATE_NEW_PROJECT);
		newCDiagram.addActionListener(controller);
		newCDiagram.setActionCommand(ActionController.ADD_C_DIAGRAM);
		newUCDiagram.addActionListener(controller);
		newUCDiagram.setActionCommand(ActionController.ADD_UC_DIAGRAM);
		openProject.addActionListener(controller);
		openProject.setActionCommand(ActionController.OPEN_PROJECT);
		openDiagram.addActionListener(controller);
		openDiagram.setActionCommand(ActionController.OPEN_DIAGRAM);
		save.addActionListener(controller);
		save.setActionCommand(ActionController.SAVE_WORK);
		saveAsProject.addActionListener(controller);
		saveAsProject.setActionCommand(ActionController.SAVE_AS_PROJECT);
		saveAsDiagram.addActionListener(controller);
		saveAsDiagram.setActionCommand(ActionController.SAVE_AS_DIAGRAM);
		close.addActionListener(controller);
		close.setActionCommand(ActionController.CLOSE);
		//
		undo.addActionListener(controller);
		undo.setActionCommand(ActionController.UNDO);
		redo.addActionListener(controller);
		redo.setActionCommand(ActionController.REDO);
		selectAll.addActionListener(controller);
		selectAll.setActionCommand(ActionController.SELECT_ALL);
		cut.addActionListener(controller);
		cut.setActionCommand(ActionController.CUT);
		copy.addActionListener(controller);
		copy.setActionCommand(ActionController.COPY);
		paste.addActionListener(controller);
		paste.setActionCommand(ActionController.PASTE);
		delete.addActionListener(controller);
		delete.setActionCommand(ActionController.DELETE);
		//
		JavaCode.addActionListener(controller);
		JavaCode.setActionCommand(ActionController.GENERATE_JAVA_CODE);
		CppCode.addActionListener(controller);
		CppCode.setActionCommand(ActionController.GENERATE_CPP_CODE);
		PNG.addActionListener(controller);
		PNG.setActionCommand(ActionController.EXPORT_AS_PNG);
		PDF.addActionListener(controller);
		PDF.setActionCommand(ActionController.EXPORT_AS_PDF);
		//
		fr.addActionListener(controller);
		fr.setActionCommand(ActionController.SET_LANGAGE_TO_FR);
		en.addActionListener(controller);
		en.setActionCommand(ActionController.SET_LANGAGE_TO_EN);
		es.addActionListener(controller);
		es.setActionCommand(ActionController.SET_LANGAGE_TO_ES);
		//
		quit.addActionListener(controller);
		quit.setActionCommand(ActionController.QUIT);
		helpMe.addActionListener(controller);
		helpMe.setActionCommand(ActionController.HELP);
		about.addActionListener(controller);
		about.setActionCommand(ActionController.ABOUT);
	}
	
	private void disableLangCheckBoxItems(){
		fr.setSelected(false);
		en.setSelected(false);
		es.setSelected(false);
	}
	
	public void setText(Locale locale){
		disableLangCheckBoxItems();
		if(locale.equals(Locale.FRENCH)){
			fr.setSelected(true);
		}else if(locale.equals(Locale.ENGLISH)){
			en.setSelected(true);
		}else if(locale.equals(new Locale("ES"))){
			es.setSelected(true);
		}
		file.setText(Langage.getString("MenuBar.file")); //$NON-NLS-1$
		edition.setText(Langage.getString("MenuBar.edition")); //$NON-NLS-1$
		tools.setText(Langage.getString("MenuBar.tools")); //$NON-NLS-1$
		preferences.setText(Langage.getString("MenuBar.preferences")); //$NON-NLS-1$
		help.setText(Langage.getString("MenuBar.help")); //$NON-NLS-1$
		//
		newThing.setText(Langage.getString("MenuBar.new")); //$NON-NLS-1$
		newProject.setText(Langage.getString("MenuBar.project")); //$NON-NLS-1$
		newCDiagram.setText(Langage.getString("MenuBar.classDiagram")); //$NON-NLS-1$
		newUCDiagram.setText(Langage.getString("MenuBar.useCaseDiagram")); //$NON-NLS-1$
		openProject.setText(Langage.getString("MenuBar.OpenProject")); //$NON-NLS-1$
		openDiagram.setText(Langage.getString("MenuBar.OpenDiagram")); //$NON-NLS-1$
		save.setText(Langage.getString("MenuBar.Save")); //$NON-NLS-1$
		saveAsProject.setText(Langage.getString("MenuBar.SaveAs")); //$NON-NLS-1$
		saveAsDiagram.setText(Langage.getString("MenuBar.SaveDiagramAs")); //$NON-NLS-1$
		close.setText(Langage.getString("MenuBar.Close")); //$NON-NLS-1$
		//
		undo.setText(Langage.getString("MenuBar.Undo")); //$NON-NLS-1$
		redo.setText(Langage.getString("MenuBar.Redo")); //$NON-NLS-1$
		selectAll.setText(Langage.getString("MenuBar.SelectAll")); //$NON-NLS-1$
		cut.setText(Langage.getString("MenuBar.Cut")); //$NON-NLS-1$
		copy.setText(Langage.getString("MenuBar.Copy")); //$NON-NLS-1$
		paste.setText(Langage.getString("MenuBar.Paste")); //$NON-NLS-1$
		delete.setText(Langage.getString("MenuBar.Delete")); //$NON-NLS-1$
		//
		generateCode.setText(Langage.getString("MenuBar.GenerateCode")); //$NON-NLS-1$
		export.setText(Langage.getString("MenuBar.Export")); //$NON-NLS-1$
		quit.setText(Langage.getString("MenuBar.Quit")); //$NON-NLS-1$
		//
		langage.setText(Langage.getString("MenuBar.langage")); //$NON-NLS-1$
		fr.setText(Langage.getString("MenuBar.french")); //$NON-NLS-1$
		en.setText(Langage.getString("MenuBar.english")); //$NON-NLS-1$
		es.setText(Langage.getString("MenuBar.spanish")); //$NON-NLS-1$
		//
		helpMe.setText(Langage.getString("MenuBar.HelpMe")); //$NON-NLS-1$
		about.setText(Langage.getString("MenuBar.About")); //$NON-NLS-1$
	}

}
