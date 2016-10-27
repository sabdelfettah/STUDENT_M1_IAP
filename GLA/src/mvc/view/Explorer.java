package mvc.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.EventObject;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreeSelectionModel;

import utils.History;
import visit.ExplorerFigureVisitor;
import lang.Langage;
import mvc.controller.ActionController;
import mvc.model.Diagram;
import mvc.model.Element;
import mvc.model.Figure;
import mvc.model.ModelController;
import mvc.model.UMLFile;

public class Explorer extends JPanel implements Observer, ViewActionController{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5380962354375906557L;
	private static Explorer instance;
	private JTree projectTree;
	private DefaultMutableTreeNode theRoot;
	private DefaultMutableTreeNode currentNode = null;
	private DefaultTreeModel treeModel;
	private MyCellRenderer cellRenderer = new MyCellRenderer();
	private MyTreeCellEditor cellEditor;
	private JLabel titleLabel = new JLabel(Langage.getString("Explorer.text")); //$NON-NLS-1$
	//
	private ExplorerFigureVisitor efv = new ExplorerFigureVisitor();
	//
	private int selectedItem = -1;
	//
	public static final ImageIcon[] PROJECT_ICONS = { new ImageIcon("img/project_icon.png"), //$NON-NLS-1$
			new ImageIcon("img/project_icon_selected.png") }; //$NON-NLS-1$
	public static final ImageIcon[] CLASS_DIAGRAM_ICONS = { new ImageIcon("img/class_diagram_icon.png"), //$NON-NLS-1$
			new ImageIcon("img/class_diagram_icon_selected.png") }; //$NON-NLS-1$
	public static final ImageIcon[] USECASE_DIAGRAM_ICONS = { new ImageIcon("img/usecase_diagram_icon.png"), //$NON-NLS-1$
			new ImageIcon("img/usecase_diagram_icon_selected.png") }; //$NON-NLS-1$
	public static final ImageIcon[] CLASS_ICONS = { new ImageIcon("img/class_icon.png"), //$NON-NLS-1$
			new ImageIcon("img/class_icon_selected.png") }; //$NON-NLS-1$
	public static final ImageIcon[] ACTOR_ICONS = { new ImageIcon("img/actor_icon.png"), //$NON-NLS-1$
			new ImageIcon("img/actor_icon_selected.png") }; //$NON-NLS-1$
	public static final ImageIcon[] ACTION_ICONS = { new ImageIcon("img/action_icon.png"), //$NON-NLS-1$
			new ImageIcon("img/action_icon_selected.png") }; //$NON-NLS-1$
	private static ImageIcon[] currentIcons;

	public static Explorer getInstance() {
		if (instance == null)
			instance = new Explorer();
		return instance;
	}

	private Explorer() {
		super();
		this.setFocusable(true);
		this.requestFocusInWindow(true);
		this.linkComponentsWithTheActionController(ActionController.getInstance());
		Object[] empty = {};
		projectTree = new JTree(empty);
		setLayout(new BorderLayout());
		add(titleLabel, BorderLayout.NORTH);
		add(projectTree, BorderLayout.CENTER);
	}

	public int getSelectedItem() {
		return selectedItem;
	}

	public void setSelectedItem(int selectedItem) {
		this.selectedItem = selectedItem;
	}

	public DefaultMutableTreeNode getCurrentNode() {
		return currentNode;
	}

	public void setCurrentNode(DefaultMutableTreeNode currentNode) {
		this.currentNode = currentNode;
	}

	public static ImageIcon[] getCurrentIcons() {
		return currentIcons;
	}

	public static void setCurrentIcons(ImageIcon[] currentIcons) {
		Explorer.currentIcons = currentIcons;
	}

	private void browseDiagram(Diagram theDiagram, DefaultMutableTreeNode root) {
		for (Figure currentFigure : theDiagram.getFigures()) {
			currentNode = root;
			currentFigure.accept(efv);
		}
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		remove(projectTree);
		// System.out.println("Notified!");
		UMLFile file = (UMLFile) ModelController.getFile();
		if (file != null) {
			this.setVisible(true);
			if (file.getChilds() != null) {
				theRoot = new DefaultMutableTreeNode(new RessourceNode(PROJECT_ICONS, file));
				for (int index = 0; index < file.getDiagrams().size(); index++) {
					switch (file.getDiagrams().get(index).getType()) {
					case Diagram.CLASS_DIAGRAM:
						currentIcons = CLASS_DIAGRAM_ICONS;
						break;
					case Diagram.USERCASE_DIAGRAM:
						currentIcons = USECASE_DIAGRAM_ICONS;
						break;
					}
					DefaultMutableTreeNode newNode =
							addNode(theRoot, new DefaultMutableTreeNode(new RessourceNode(currentIcons, file
									.getDiagrams().get(index))), 0);
					browseDiagram(file.getDiagrams().get(index), newNode);
				}
			} else {
				switch (file.getWorkingOnDiagram().getType()) {
				case Diagram.CLASS_DIAGRAM:
					currentIcons = CLASS_DIAGRAM_ICONS;
					break;
				case Diagram.USERCASE_DIAGRAM:
					currentIcons = USECASE_DIAGRAM_ICONS;
					break;
				}
				theRoot = new DefaultMutableTreeNode(new RessourceNode(currentIcons, file));
				browseDiagram(file.getDiagrams().get(0), theRoot);
			}
			treeModel = new DefaultTreeModel(theRoot);
			projectTree = new JTree(treeModel);
			projectTree.addTreeSelectionListener(new TreeSelectionListener() {
				
				@Override
				public void valueChanged(TreeSelectionEvent arg0) {
					DefaultMutableTreeNode node = (DefaultMutableTreeNode) projectTree.getLastSelectedPathComponent();
					if(node != null){
						RessourceNode ressource = (RessourceNode) node.getUserObject();
						if(ressource.getObject() instanceof Diagram){
							ModelController.getFile().setDiagram((Diagram) ressource.getObject());
							ModelController.getFile().notifyChanges();
						}else{
							
						}
					}
					
				}
			});
			//
			projectTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
			projectTree.setShowsRootHandles(true);
			projectTree.setEditable(true);
			projectTree.setCellRenderer(cellRenderer);
			//
			cellEditor = new MyTreeCellEditor(projectTree, cellRenderer);
			projectTree.setCellEditor(cellEditor);
			projectTree.setInvokesStopCellEditing(true);
			//
			add(projectTree);
			revalidate();
		} else {
			this.setVisible(false);
		}
	}

	// Adding methods
	private DefaultMutableTreeNode addNode(DefaultMutableTreeNode father, DefaultMutableTreeNode child, int index) {
		father.insert(child, index);
		if(treeModel != null){
			treeModel.reload();
		}
		return child;
	}

	public DefaultMutableTreeNode add(String name) {
		return addNode(currentNode, new DefaultMutableTreeNode(name), 0);
	}

	public DefaultMutableTreeNode addFigure(Figure figure) {
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(new RessourceNode(currentIcons, figure));
		currentNode.insert(newNode, 0);
		if(treeModel != null){
			treeModel.reload();
		}
		return newNode;
	}

	public DefaultMutableTreeNode add(DefaultMutableTreeNode currentRoot, String name) {
		DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(name);
		currentRoot.insert(newNode, 0);
		treeModel.reload();
		return newNode;
	}

	@Override
	public void linkComponentsWithTheActionController(ActionController controller) {
		//
	}

	public void setText(){
		titleLabel.setText(Langage.getString("Explorer.text")); //$NON-NLS-1$
	}

}

// adding a rendrer for the cells
// TreeCellRenderer : Defines the requirements for an object that displays a
// tree node.
class MyCellRenderer extends DefaultTreeCellRenderer {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2490255185828968312L;

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded,
			boolean leaf, int row, boolean hasFocus) {
		// value == userObject
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
		Object object = node.getUserObject();
		setText(object.toString());
		if (object instanceof RessourceNode) {
			if (selected) {
				setIcon(((RessourceNode) object).getSelectedIcon());
			} else {
				setIcon(((RessourceNode) object).getIcon());
			}
		} else {
			setIcon(null);
		}
		setFont(tree.getFont());
		setForeground(selected ? UIManager.getColor("Tree.selectionForeground") : UIManager //$NON-NLS-1$
				.getColor("Tree.textForeground")); //$NON-NLS-1$
		setBackground(selected ? UIManager.getColor("Tree.selectionBackground") : UIManager //$NON-NLS-1$
				.getColor("Tree.textBackground")); //$NON-NLS-1$
		this.selected = selected;
		return this;
	}

}

class MyTreeCellEditor extends DefaultTreeCellEditor{
	private RessourceNode theEditedNode;

	public MyTreeCellEditor(JTree tree, DefaultTreeCellRenderer renderer) {
		super(tree, renderer);
		addCellEditorListener(new CellEditorListener() {
			
			@Override
			public void editingStopped(ChangeEvent arg0) {
				String text = (String) getCellEditorValue();
				if(text.length() > 0){
					theEditedNode.setName(text);
				}
				ModelController.getFile().notifyChanges();
			}
			
			@Override
			public void editingCanceled(ChangeEvent arg0) {
				System.out.println("editingCanceled"); //$NON-NLS-1$
			}
		});
	}

	@Override
	public Component getTreeCellEditorComponent(JTree tree, Object value, boolean selected, boolean expanded,
			boolean leaf, int row) {
		Component c = super.getTreeCellEditorComponent(tree, value, selected, expanded, leaf, row);
		EditorContainer e = (EditorContainer) c;
		if (value instanceof DefaultMutableTreeNode) {
			DefaultMutableTreeNode theNode = (DefaultMutableTreeNode) value;
			Object object = theNode.getUserObject();
			if (object instanceof RessourceNode) {
				theEditedNode = (RessourceNode) object;
				editingIcon = theEditedNode.getSelectedIcon();
				e.setFont(tree.getFont());
				return e;
			}
		}
		System.out.println("Error on CellEditor"); //$NON-NLS-1$
		return null;
	}

	@Override
	public boolean isCellEditable(EventObject e) {
		boolean editable = true;
		if (lastPath != null) {
			Object object = (TreeNode) lastPath.getLastPathComponent();
			if (object instanceof DefaultMutableTreeNode) {
				DefaultMutableTreeNode theNode = (DefaultMutableTreeNode) object;
				Object userObject = theNode.getUserObject();
				if (userObject instanceof RessourceNode) {
					RessourceNode ressourceNode = (RessourceNode) userObject;
					if (ressourceNode.getObject() instanceof UMLFile) {
						editable = false;
						if(ressourceNode.getObject() instanceof Diagram){
							ModelController.getFile().setDiagram((Diagram) ressourceNode.getObject());
						}
					}
				}
			}
		}
		return editable && super.isCellEditable(e);
	}
	
}

class RessourceNode {
	protected Icon icon;
	protected Icon selectedIcon;
	protected Object nodeObject;

	public RessourceNode(Icon[] icons, Object nodeObject) {
		this.icon = icons[0];
		this.selectedIcon = icons[1];
		this.nodeObject = nodeObject;
	}

	public Icon getIcon() {
		return icon;
	}

	public Icon getSelectedIcon() {
		return selectedIcon;
	}

	public Object getObject() {
		return nodeObject;
	}

	public String toString() {
		return nodeObject.toString();
	}

	public void setName(String name) {
		if (nodeObject instanceof Element) {
			Element element = (Element) nodeObject;
			Object[] old_data = {element.getName()};
			Object[] new_data = {name};
			ModelController.getFile().getWorkingOnDiagram().addHistory(History.UPDATE_NAME, Figure.ArrayListFromFigure(element), ModelController.getFile().getWorkingOnDiagram(), old_data, new_data);
			element.setName(name);
		} else {
			// ?
		}
	}
}