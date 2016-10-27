package mvc.view;

import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import lang.Langage;
import mvc.controller.ActionController;
import mvc.model.ModelController;
import mvc.model.UMLFile;

public class Toolbox extends JPanel implements Observer, ViewActionController{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5810870597080661122L;
	private static Toolbox instance = null;
	private JTabbedPane tabePane = new JTabbedPane(JTabbedPane.TOP);
	private JPanel elementsPanel = new JPanel();
	private JPanel relationsPanel = new JPanel();
	// Elements
	private JButton Class = new JButton(Langage.getString("Toolbox.class")); //$NON-NLS-1$
	private JButton Interface = new JButton(Langage.getString("Toolbox.interface")); //$NON-NLS-1$
	private JButton Action = new JButton(Langage.getString("Toolbox.action")); //$NON-NLS-1$
	private JButton Usecase = new JButton(Langage.getString("Toolbox.usecase")); //$NON-NLS-1$
	private JButton Actor = new JButton(Langage.getString("Toolbox.actor")); //$NON-NLS-1$
	// Relations
	private JButton Agregation = new JButton(Langage.getString("Toolbox.agregation")); //$NON-NLS-1$
	private JButton Composition = new JButton(Langage.getString("Toolbox.composition")); //$NON-NLS-1$
	private JButton Heritage = new JButton(Langage.getString("Toolbox.heritage")); //$NON-NLS-1$
	private JButton InterfaceRealisation = new JButton(Langage.getString("Toolbox.interface")); //$NON-NLS-1$
	private JButton Association = new JButton(Langage.getString("Toolbox.association")); //$NON-NLS-1$
	private JButton Dependency = new JButton(Langage.getString("Toolbox.dependency")); //$NON-NLS-1$
	//
	private JButton Comment = new JButton(Langage.getString("Toolbox.comment")); //$NON-NLS-1$
	private JButton[] class_diagram_object_button_list = { Class, Interface, Comment }; // L1
	private JButton[] class_diagram_relation_button_list = { Agregation, Composition, Heritage, InterfaceRealisation,
			Association, Dependency }; // L2
	private JButton[] use_case_diagram_object_button_list = { Actor, Action, Usecase, Comment }; // L3
	private JButton[] use_case_diagram_relation_button_list = { Heritage }; // L4
	private JButton[][] object_buttons_lists =
			{ class_diagram_object_button_list, use_case_diagram_object_button_list };
	private JButton[][] relation_buttons_lists = { class_diagram_relation_button_list,
			use_case_diagram_relation_button_list };

	public static Toolbox getInstance() {
		if (instance == null)
			instance = new Toolbox();
		return instance;
	}

	private Toolbox() {
		super();
		this.setFocusable(true);
		this.requestFocusInWindow(true);
		tabePane.add(Langage.getString("Toolbox.elements"), elementsPanel); //$NON-NLS-1$
		tabePane.add(Langage.getString("Toolbox.relations"), relationsPanel); //$NON-NLS-1$
		Class.setAlignmentX(CENTER_ALIGNMENT);
		Interface.setAlignmentX(CENTER_ALIGNMENT);
		Action.setAlignmentX(CENTER_ALIGNMENT);
		Actor.setAlignmentX(CENTER_ALIGNMENT);
		Usecase.setAlignmentX(CENTER_ALIGNMENT);
		//
		Agregation.setAlignmentX(CENTER_ALIGNMENT);
		Composition.setAlignmentX(CENTER_ALIGNMENT);
		Heritage.setAlignmentX(CENTER_ALIGNMENT);
		InterfaceRealisation.setAlignmentX(CENTER_ALIGNMENT);
		Dependency.setAlignmentX(CENTER_ALIGNMENT);
		Association.setAlignmentX(CENTER_ALIGNMENT);
		//
		Comment.setAlignmentX(CENTER_ALIGNMENT);
		//
		elementsPanel.setLayout(new BoxLayout(elementsPanel, BoxLayout.Y_AXIS));
		relationsPanel.setLayout(new BoxLayout(relationsPanel, BoxLayout.Y_AXIS));
		add(tabePane);
		this.linkComponentsWithTheActionController(ActionController.getInstance());
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		UMLFile file = (UMLFile) ModelController.getFile();
		if (file != null && file.getWorkingOnDiagram() != null) {
			this.setVisible(true);
			elementsPanel.removeAll();
			relationsPanel.removeAll();
			int type = file.getWorkingOnDiagram().getType();
			// elementsPanel.setLayout(object_layout_list[type]);
			MainFrame.addButtons(elementsPanel, object_buttons_lists[type]);
			MainFrame.addButtons(relationsPanel, relation_buttons_lists[type]);
			revalidate();
		} else {
			this.setVisible(false);
		}
	}

	@Override
	public void linkComponentsWithTheActionController(ActionController controller) {
		Action.addActionListener(controller);
		Action.setActionCommand(ActionController.SET_ELEMENT_TO_ACTION);
		Actor.addActionListener(controller);
		Actor.setActionCommand(ActionController.SET_ELEMENT_TO_ACTOR);
		Usecase.addActionListener(controller);
		Usecase.setActionCommand(ActionController.SET_ELEMENT_TO_USECASE);
		Class.addActionListener(controller);
		Class.setActionCommand(ActionController.SET_ELEMENT_TO_CLASS);
	}

	public void setText() {
		Class.setText(Langage.getString("Toolbox.class")); //$NON-NLS-1$
		Interface.setText(Langage.getString("Toolbox.interface")); //$NON-NLS-1$
		Action.setText(Langage.getString("Toolbox.action")); //$NON-NLS-1$
		Usecase.setText(Langage.getString("Toolbox.usecase")); //$NON-NLS-1$
		Actor.setText(Langage.getString("Toolbox.actor")); //$NON-NLS-1$
		//
		Agregation.setText(Langage.getString("Toolbox.agregation")); //$NON-NLS-1$
		Composition.setText(Langage.getString("Toolbox.composition")); //$NON-NLS-1$
		Heritage.setText(Langage.getString("Toolbox.heritage")); //$NON-NLS-1$
		InterfaceRealisation.setText(Langage.getString("Toolbox.interface")); //$NON-NLS-1$
		Association.setText(Langage.getString("Toolbox.association")); //$NON-NLS-1$
		Dependency.setText(Langage.getString("Toolbox.dependency")); //$NON-NLS-1$
		//
		Comment.setText(Langage.getString("Toolbox.comment")); //$NON-NLS-1$
		//
		tabePane.setTitleAt(0, Langage.getString("Toolbox.elements"));
		tabePane.setTitleAt(1, Langage.getString("Toolbox.relations"));
	}

}
