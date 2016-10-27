package mvc.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;

import lang.Langage;
import mvc.controller.ActionController;
import mvc.model.Attribute;
import mvc.model.ClassDraw;
import mvc.model.Figure;
import mvc.model.Method;
import mvc.model.ModelController;

public class Popup extends JPopupMenu implements ViewActionController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2824941964129548188L;
	private static JMenuItem addAttribute = new JMenuItem(Langage.getString("Popup.AddAttribute")); //$NON-NLS-1$
	private static JMenuItem addMethod = new JMenuItem(Langage.getString("Popup.AddMethod")); //$NON-NLS-1$
	private static JMenu modifieAttributes = new JMenu(Langage.getString("Popup.AlterAttributes")); //$NON-NLS-1$
	private static JMenu modifieMethods = new JMenu(Langage.getString("Popup.AlterMethods")); //$NON-NLS-1$
	private static JMenuItem groupFigures = new JMenuItem(Langage.getString("Modifier.Group")); //$NON-NLS-1$
	private static JMenuItem ungroupFigures = new JMenuItem(Langage.getString("Modifier.Ungroup")); //$NON-NLS-1$
	private static JMenuItem cutFigure = new JMenuItem(Langage.getString("MenuBar.Cut")); //$NON-NLS-1$
	private static JMenuItem copyFigure = new JMenuItem(Langage.getString("MenuBar.Copy")); //$NON-NLS-1$
	private static JMenuItem pasteFigure = new JMenuItem(Langage.getString("MenuBar.Paste")); //$NON-NLS-1$
	private static JMenuItem deleteFigure = new JMenuItem(Langage.getString("MenuBar.Delete")); //$NON-NLS-1$
	private static JMenuItem[] class_list = { addAttribute, addMethod, modifieAttributes, modifieMethods };
	private static JMenuItem[] default_list = { cutFigure, copyFigure, pasteFigure };
	private static JMenuItem[] delete_list = { deleteFigure };
	//
	private static Popup instance = null;
	
	private Popup() {
		super();
		this.linkComponentsWithTheActionController(ActionController.getInstance());
	}

	// loading methods
	private void addMenuItems(JMenuItem[] list, boolean separate) {
		if(separate)
			add(new JSeparator());
		for (JMenuItem item : list) {
			add(item);
		}
	}

	private static void loadDefault(boolean separate) {
		instance.addMenuItems(default_list, separate);
		instance.addMenuItems(delete_list, true);
	}
	
	private static void loadClassComponents(){
		ClassDraw theClass = (ClassDraw) ModelController.getSelectedFigure();
		modifieAttributes.removeAll();
		modifieMethods.removeAll();
		if(theClass.getAttributes().size() > 0){
			modifieAttributes.setEnabled(true);
			for(Attribute attribute : theClass.getAttributes()){
				final Attribute theAttribute = attribute;
				JMenuItem newItem = new JMenuItem(theAttribute.getName());
				newItem.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						System.out.println("Att = "+theAttribute);
						ModelController.setSelectedObject(theAttribute);
						Modifier.setMode(Modifier.ALTER_ATTRIBUTE_MODE);
					}
				});
				modifieAttributes.add(newItem);
			}
		}else{
			modifieAttributes.setEnabled(false);
		}
		//
		if(theClass.getMethods().size() > 0){
			modifieMethods.setEnabled(true);
			for(Method method : theClass.getMethods()){
				final Method theMethod = method;
				JMenuItem newItem = new JMenuItem(theMethod.getName());
				newItem.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent arg0) {
						System.out.println("Meth = "+theMethod);
						ModelController.setSelectedObject(theMethod);
						Modifier.setMode(Modifier.ALTER_METHOD_MODE);
					}
				});
				modifieMethods.add(newItem);
			}
		}else{
			modifieMethods.setEnabled(false);
		}
	}
	
	private static void loadPopup(int type) {
		instance.removeAll();
		boolean separate = false;
		switch (type) {
		case Figure.CLASS: {
			loadClassComponents();
			//
			instance.addMenuItems(class_list, false);
			separate = true;
		}
			;
			break;
		}
		loadDefault(separate);
	}

	private static void loadPopupForGroupSelection() {
		instance.removeAll();
		instance.add(groupFigures);
		loadDefault(true);
	}
	
	private static void loadPopupForPackage() {
		instance.removeAll();
		instance.add(ungroupFigures);
		loadDefault(true);
	}

	//
	public static Popup getDefaultInstance() {
		if (instance == null)
			instance = new Popup();
		instance.removeAll();
		loadDefault(false);
		return instance;
	}
	
	public static Popup getNoFigureSelectionInstance(){
		if (instance == null)
			instance = new Popup();
		instance.removeAll();
		if(ModelController.getClipboard() != null){
			instance.add(pasteFigure);
		}
		return instance;
	}

	public static Popup getInstanceForFigureOfType(int type) {
		if (instance == null)
			instance = new Popup();
		loadPopup(type);
		return instance;

	}

	public static Popup getInstanceForGroup() {
		if (instance == null)
			instance = new Popup();
		loadPopupForGroupSelection();
		return instance;
	}
	
	public static Popup getInstanceForPackage() {
		if (instance == null)
			instance = new Popup();
		loadPopupForPackage();
		return instance;
	}

	@Override
	public void linkComponentsWithTheActionController(ActionController controller) {
		addAttribute.addActionListener(controller);
		addAttribute.setActionCommand(ActionController.ASK_FOR_ADDING_ATTRIBUTE);
		addMethod.addActionListener(controller);
		addMethod.setActionCommand(ActionController.ASK_FOR_ADDING_METHOD);
		cutFigure.addActionListener(controller);
		cutFigure.setActionCommand(ActionController.CUT);
		copyFigure.addActionListener(controller);
		copyFigure.setActionCommand(ActionController.COPY);
		pasteFigure.addActionListener(controller);
		pasteFigure.setActionCommand(ActionController.PASTE);
		deleteFigure.addActionListener(controller);
		deleteFigure.setActionCommand(ActionController.DELETE);
		groupFigures.addActionListener(controller);
		groupFigures.setActionCommand(ActionController.GROUP_FIGURES);
		ungroupFigures.addActionListener(controller);
		ungroupFigures.setActionCommand(ActionController.UNGROUP_FIGURES);
	}

	public void setText() {
		addAttribute.setText(Langage.getString("Popup.AddAttribute")); //$NON-NLS-1$
		addMethod.setText(Langage.getString("Popup.AddMethod")); //$NON-NLS-1$
		modifieAttributes.setText(Langage.getString("Popup.AlterAttributes")); //$NON-NLS-1$
		modifieMethods.setText(Langage.getString("Popup.AlterMethods")); //$NON-NLS-1$
		groupFigures.setText(Langage.getString("Modifier.Group")); //$NON-NLS-1$
		cutFigure.setText(Langage.getString("MenuBar.Cut")); //$NON-NLS-1$
		copyFigure.setText(Langage.getString("MenuBar.Copy")); //$NON-NLS-1$
		pasteFigure.setText(Langage.getString("MenuBar.Paste")); //$NON-NLS-1$
		deleteFigure.setText(Langage.getString("MenuBar.Delete"));
	}

}
