package mvc.view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import lang.Langage;
import mvc.controller.ActionController;
import mvc.model.Attribute;
import mvc.model.Method;
import mvc.model.ModelController;
import mvc.model.Package;
import mvc.model.Parameter;
import mvc.model.UMLFile;

public class Modifier extends JPanel implements Observer, ViewActionController {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2798922273037398221L;
	private static Modifier modifier = null;
	//
	public static final int ADD_ATTRIBUTE_MODE = 0;
	public static final int ADD_METHOD_MODE = 1;
	//
	public static final int ALTER_ATTRIBUTE_MODE = 100;
	public static final int ALTER_METHOD_MODE = 101;
	//
	private JLabel addAttribute = new JLabel(Langage.getString("Popup.AddAttribute")); //$NON-NLS-1$
	private JLabel alterAttribute = new JLabel(Langage.getString("Modifier.AlterAttribute")); //$NON-NLS-1$
	private JLabel addMethod = new JLabel(Langage.getString("Popup.AddMethod")); //$NON-NLS-1$
	private JLabel alterMethod = new JLabel(Langage.getString("Modifier.AlterMethod")); //$NON-NLS-1$
	//
	private JLabel nameLabel = new JLabel(Langage.getString("Modifier.Name")); //$NON-NLS-1$
	private JLabel typeLabel = new JLabel(Langage.getString("Modifier.Type")); //$NON-NLS-1$
	private JLabel visibilityLabel = new JLabel(Langage.getString("Modifier.Visibility")); //$NON-NLS-1$
	//
	private JButton apply = new JButton(Langage.getString("Modifier.Apply")); //$NON-NLS-1$
	private JButton add = new JButton(Langage.getString("Modifier.Add")); //$NON-NLS-1$
	private JButton cancel = new JButton(Langage.getString("Modifier.Cancel")); //$NON-NLS-1$
	private JButton group = new JButton(Langage.getString("Modifier.Group")); //$NON-NLS-1$
	private JButton ungroup = new JButton(Langage.getString("Modifier.Ungroup")); //$NON-NLS-1$
	private JButton addParameter = new JButton(Langage.getString("Modifier.AddParameter")); //$NON-NLS-1$
	//
	private JTextField nameTextField = new JTextField(5);
	//
	private JCheckBox staticCheckBox = new JCheckBox(Langage.getString("Modifier.isStatic")); //$NON-NLS-1$
	private JCheckBox finalCheckBox = new JCheckBox(Langage.getString("Modifier.isFinal")); //$NON-NLS-1$
	private JCheckBox abstractCheckBox = new JCheckBox(Langage.getString("Modifier.isAbstract")); //$NON-NLS-1$
	//
	String[] defaultTypesListWithoutVoid = { "int", "float", "double", "char", "String", "boolean" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
	String[] defaultTypesListWithVoid = { "int", "float", "double", "char", "String", "boolean", "void" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$ //$NON-NLS-6$
	String[] visibilityList = { "public", "protected", "private" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
	private JComboBox<String> typeWithoutVoidComboBox = new JComboBox<String>(defaultTypesListWithoutVoid);
	private JComboBox<String> typeWithVoidComboBox = new JComboBox<String>(defaultTypesListWithVoid);
	private JComboBox<String> visibilityComboBox = new JComboBox<String>(visibilityList);
	//
	private JPanel titlePanel = new JPanel();
	private JPanel dataPanel = new JPanel();
	private JPanel labelsPanel = new JPanel();
	private JPanel actionPanel = new JPanel();
	private JPanel addParameterPanel = new JPanel();
	//
	private JComponent[] attributeComponents = { nameTextField, typeWithoutVoidComboBox, visibilityComboBox,
			staticCheckBox, finalCheckBox };
	private JComponent[] methodComponents = { nameTextField, typeWithVoidComboBox, visibilityComboBox, staticCheckBox,
			abstractCheckBox };
	//
	private JLabel[] attributeLabels = { nameLabel, typeLabel, visibilityLabel };
	private JLabel[] methodLabels = { nameLabel, typeLabel, visibilityLabel };
	//
	private JButton[] groupeList = { group };
	private JButton[] packageList = { ungroup };
	private JButton[] addList = { add, cancel };
	private JButton[] applyList = { apply, cancel };
	//
	private GridLayout attributeLabelsManagingLayout = new GridLayout(5, 1);
	private GridLayout attributeDataManagingLayout = new GridLayout(5, 1);
	//
	private JPanel methodSuperPanel = new JPanel(new BorderLayout());
	private JPanel methodEditingPanel = new JPanel(new BorderLayout());
	private JPanel method_s_parametersEditinPanel = new JPanel(new BorderLayout());
	private JPanel parametersPanel = new JPanel(new BorderLayout());
	private ArrayList<JTextField> nameOfParameters = new ArrayList<JTextField>();
	private ArrayList<JComboBox<String>> typeOfParameters = new ArrayList<JComboBox<String>>();
	private GridLayout methodLabelsManagingLayout = new GridLayout(5, 1);
	private GridLayout methodDataManagingLayout = new GridLayout(5, 1);
	//
	private ArrayList<JLabel> namesOfParameters;
	private ArrayList<JLabel> typesOfParameters;
	//

	private static int oldMode = -1;
	private static int mode = -1;

	public static Modifier getInstance() {
		if (modifier == null)
			modifier = new Modifier();
		return modifier;
	}

	private Modifier() {
		super();
		this.setFocusable(true);
		this.requestFocusInWindow(true);
		//
		apply.setAlignmentX(CENTER_ALIGNMENT);
		add.setAlignmentX(CENTER_ALIGNMENT);
		cancel.setAlignmentX(CENTER_ALIGNMENT);
		group.setAlignmentX(CENTER_ALIGNMENT);
		ungroup.setAlignmentX(CENTER_ALIGNMENT);
		//
		addParameter.setAlignmentX(RIGHT_ALIGNMENT);
		addParameter.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("Ajout_parametre");
				nameOfParameters.add(new JTextField());
				typeOfParameters.add(new JComboBox<String>(defaultTypesListWithoutVoid));
				ModelController.getFile().notifyChanges();
			}
		});
		addParameterPanel.add(addParameter);
		//
		setLayout(new BorderLayout());
		//
		this.linkComponentsWithTheActionController(ActionController.getInstance());
	}

	private void reInitialiseForm() {
		//
		actionPanel.removeAll();
		dataPanel.removeAll();
		labelsPanel.removeAll();
		titlePanel.removeAll();
		parametersPanel.removeAll();
		method_s_parametersEditinPanel.removeAll();
		methodEditingPanel.removeAll();
		methodSuperPanel.removeAll();
		//
		removeAll();
		//
		if (mode != oldMode) {
			nameTextField.setText("");
			typeWithoutVoidComboBox.setSelectedIndex(0);
			typeWithVoidComboBox.setSelectedIndex(0);
			visibilityComboBox.setSelectedIndex(0);
			staticCheckBox.setSelected(false);
			finalCheckBox.setSelected(false);
			abstractCheckBox.setSelected(false);
			//
			oldMode = mode;
		}
	}

	private void loadAttributeContents(Attribute attribute) {
		System.out.println("load " + attribute);
		if (attribute != null) {
			nameTextField.setText(attribute.getName());
			typeWithoutVoidComboBox.setSelectedIndex(attribute.getType());
			visibilityComboBox.setSelectedIndex(attribute.getVisibility());
			staticCheckBox.setSelected(attribute.isStatic());
			finalCheckBox.setSelected(attribute.is_final());
		}
	}

	private void loadMethodContents(Method method) {
		System.out.println("load " + method);
		if (method != null) {
			nameTextField.setText(method.getName());
			typeWithVoidComboBox.setSelectedIndex(method.getType());
			visibilityComboBox.setSelectedIndex(method.getVisibility());
			staticCheckBox.setSelected(method.isStatic());
			abstractCheckBox.setSelected(method.is_abstract());
			for (Parameter parameter : method.getArguments()) {
				nameOfParameters.add(new JTextField(parameter.getName()));
				JComboBox<String> theComboBox = new JComboBox<String>(defaultTypesListWithoutVoid);
				theComboBox.setSelectedIndex(parameter.getType());
				typeOfParameters.add(theComboBox);
			}
		}
	}

	private void loadAttributMenu(boolean addingMode) {
		dataPanel.setLayout(attributeDataManagingLayout);
		labelsPanel.setLayout(attributeLabelsManagingLayout);
		MainFrame.addLabels(labelsPanel, attributeLabels);
		MainFrame.addComponents(dataPanel, attributeComponents);
		if (addingMode) {
			add.setActionCommand(ActionController.ADD_ATTRIBUTE);
			MainFrame.addButtons(actionPanel, addList);
			titlePanel.add(addAttribute);
		} else {
			apply.setActionCommand(ActionController.ALTER_ATTRIBUTE);
			loadAttributeContents((Attribute) ModelController.getSelectedObject());
			MainFrame.addButtons(actionPanel, applyList);
			titlePanel.add(alterAttribute);
		}
		add(labelsPanel, BorderLayout.WEST);
		add(dataPanel, BorderLayout.EAST);
		add(titlePanel, BorderLayout.NORTH);
		add(actionPanel, BorderLayout.SOUTH);
	}

	private void loadMethodMenu(boolean addingMode) {
		dataPanel.setLayout(methodDataManagingLayout);
		labelsPanel.setLayout(methodLabelsManagingLayout);
		MainFrame.addLabels(labelsPanel, methodLabels);
		MainFrame.addComponents(dataPanel, methodComponents);
		if (addingMode) {
			add.setActionCommand(ActionController.ADD_METHOD);
			MainFrame.addButtons(actionPanel, addList);
			titlePanel.add(addMethod);
		} else {
			apply.setActionCommand(ActionController.ALTER_METHOD);
			loadMethodContents((Method) ModelController.getSelectedObject());
			MainFrame.addButtons(actionPanel, applyList);
			titlePanel.add(alterMethod);
		}
		methodEditingPanel.add(titlePanel, BorderLayout.NORTH);
		methodEditingPanel.add(labelsPanel, BorderLayout.WEST);
		methodEditingPanel.add(dataPanel, BorderLayout.EAST);
		//
		if (nameOfParameters.size() > 0) {
			parametersPanel.setLayout(new GridLayout(nameOfParameters.size(), 1));
			namesOfParameters = new ArrayList<JLabel>();
			typesOfParameters = new ArrayList<JLabel>();
			for (int index = 0; index < nameOfParameters.size(); index++) {
				JButton deleteParameter = new JButton("X");
				final JTextField theTextField = nameOfParameters.get(index);
				final JComboBox<String> theComboBox = typeOfParameters.get(index);
				deleteParameter.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						nameOfParameters.remove(theTextField);
						typeOfParameters.remove(theComboBox);
						ModelController.getFile().notifyChanges();
					}
				});
				JPanel linePanel = new JPanel();
				linePanel.setLayout(new BoxLayout(linePanel, BoxLayout.LINE_AXIS));
				JLabel nameOfParameter = new JLabel(Langage.getString("Modifier.Name"));
				namesOfParameters.add(nameOfParameter);
				linePanel.add(nameOfParameter);
				linePanel.add(theTextField);
				JLabel typeOfParameter = new JLabel(Langage.getString("Modifier.Type"));
				typesOfParameters.add(typeOfParameter);
				linePanel.add(typeOfParameter);
				linePanel.add(theComboBox);
				linePanel.add(deleteParameter);
				parametersPanel.add(linePanel);
			}
		}
		//
		method_s_parametersEditinPanel.add(parametersPanel, BorderLayout.CENTER);
		method_s_parametersEditinPanel.add(addParameterPanel, BorderLayout.SOUTH);
		//
		methodSuperPanel.add(methodEditingPanel, BorderLayout.NORTH);
		methodSuperPanel.add(method_s_parametersEditinPanel, BorderLayout.CENTER);
		methodSuperPanel.add(actionPanel, BorderLayout.SOUTH);
		//
		add(methodSuperPanel, BorderLayout.CENTER);
		add(titlePanel, BorderLayout.NORTH);
		add(actionPanel, BorderLayout.SOUTH);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		UMLFile file = (UMLFile) ModelController.getFile();
		reInitialiseForm();
		if (file != null) {
			if (mode == ADD_ATTRIBUTE_MODE || mode == ALTER_ATTRIBUTE_MODE) {
				loadAttributMenu(mode == ADD_ATTRIBUTE_MODE);
			} else if (mode == ADD_METHOD_MODE || mode == ALTER_METHOD_MODE) {
				loadMethodMenu(mode == ADD_METHOD_MODE);
			} else if (ModelController.getGroupedFigures() != null && ModelController.getGroupedFigures().size() > 1) {
				add(actionPanel);
				MainFrame.addButtons(actionPanel, groupeList);
			} else if (ModelController.getSelectedFigure() != null
					&& ModelController.getSelectedFigure() instanceof Package) {
				add(actionPanel);
				MainFrame.addButtons(actionPanel, packageList);
			}
			this.setVisible(true);
		} else {
			this.setVisible(false);
		}
		this.revalidate();
	}

	@Override
	public void linkComponentsWithTheActionController(ActionController controller) {
		group.addActionListener(controller);
		group.setActionCommand(ActionController.GROUP_FIGURES);
		ungroup.addActionListener(controller);
		ungroup.setActionCommand(ActionController.UNGROUP_FIGURES);
		apply.addActionListener(controller);
		apply.setActionCommand(ActionController.APPLY_EDITION);
		cancel.addActionListener(controller);
		cancel.setActionCommand(ActionController.CANCEL_EDITING);
		add.addActionListener(controller);
	}

	public void setText() {
		addAttribute.setText(Langage.getString("Popup.AddAttribute")); //$NON-NLS-1$
		alterAttribute.setText(Langage.getString("Modifier.AlterAttribute")); //$NON-NLS-1$
		addMethod.setText(Langage.getString("Popup.AddMethod")); //$NON-NLS-1$
		alterMethod.setText(Langage.getString("Modifier.AlterMethod")); //$NON-NLS-1$
		//
		nameLabel.setText(Langage.getString("Modifier.Name")); //$NON-NLS-1$
		typeLabel.setText(Langage.getString("Modifier.Type")); //$NON-NLS-1$
		visibilityLabel.setText(Langage.getString("Modifier.Visibility")); //$NON-NLS-1$
		//
		apply.setText(Langage.getString("Modifier.Apply")); //$NON-NLS-1$
		add.setText(Langage.getString("Modifier.Add")); //$NON-NLS-1$
		cancel.setText(Langage.getString("Modifier.Cancel")); //$NON-NLS-1$
		group.setText(Langage.getString("Modifier.Group")); //$NON-NLS-1$
		ungroup.setText(Langage.getString("Modifier.Ungroup")); //$NON-NLS-1$
		addParameter.setText(Langage.getString("Modifier.AddParameter")); //$NON-NLS-1$
		//
		staticCheckBox.setText(Langage.getString("Modifier.isStatic")); //$NON-NLS-1$
		finalCheckBox.setText(Langage.getString("Modifier.isFinal")); //$NON-NLS-1$
		abstractCheckBox.setText(Langage.getString("Modifier.isAbstract")); //$NON-NLS-1$
		//
		if (namesOfParameters != null) {
			for (JLabel nameOfParameterLabel : namesOfParameters) {
				nameOfParameterLabel.setText(Langage.getString("Modifier.Name"));
			}
		}
		if (typesOfParameters != null) {
			for (JLabel typeOfParameterLabel : typesOfParameters) {
				typeOfParameterLabel.setText(Langage.getString("Modifier.Type"));
			}
		}
	}

	public static void setMode(int mode) {
		getInstance().nameOfParameters.clear();
		getInstance().typeOfParameters.clear();
		Modifier.mode = mode;
		ModelController.getFile().notifyChanges();
	}

	public static int getMode() {
		return mode;
	}

	public Object getObject() {
		if (mode == ADD_ATTRIBUTE_MODE || mode == ALTER_ATTRIBUTE_MODE) {
			return new Attribute(nameTextField.getText(), typeWithoutVoidComboBox.getSelectedIndex(),
					(short) visibilityComboBox.getSelectedIndex(), staticCheckBox.isSelected(),
					finalCheckBox.isSelected());
		} else if (mode == ADD_METHOD_MODE || mode == ALTER_METHOD_MODE) {
			Method updatedMethod =
					new Method(nameTextField.getText(), typeWithVoidComboBox.getSelectedIndex(),
							(short) visibilityComboBox.getSelectedIndex(), staticCheckBox.isSelected(),
							abstractCheckBox.isSelected());
			// add parameters
			for (int index = 0; index < nameOfParameters.size(); index++) {
				updatedMethod.addParameter(nameOfParameters.get(index).getText(), typeOfParameters.get(index)
						.getSelectedIndex());
			}
			return updatedMethod;
		}
		return null;
	}

}
