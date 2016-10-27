package mvc.model;

import java.util.ArrayList;
import java.util.Observable;

import mvc.view.Explorer;
import mvc.view.MainFrame;
import mvc.view.MenuBar;
import mvc.view.Modifier;
import mvc.view.Toolbox;

public abstract class UMLFile extends Observable{
	protected String name;
	protected boolean isRoot;
	protected boolean changed;
	protected String path;
	
	public UMLFile(String name, boolean isRoot){
		this.name = name;
		this.isRoot = isRoot;
		changed = true;
		path = null;
		this.addObserver(Explorer.getInstance());
		this.addObserver(Toolbox.getInstance());
		this.addObserver(MenuBar.getInstance());
		this.addObserver(Modifier.getInstance());
	}

	// Getters & Setters
	public boolean isRoot() {
		return isRoot;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public abstract Diagram getWorkingOnDiagram();
	
	public abstract ArrayList<Figure> getFigures();
	
	public abstract ArrayList<Diagram> getDiagrams();
	
	public abstract ArrayList<Diagram> getChilds();
	
	public abstract void setDiagram(Diagram newDiagram);
	
	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean changed) {
		this.changed = changed;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public abstract String toString();
	public abstract String toExtendedString();
	
	public Project asProject(){
		return (Project) this;
	}
	
	public Diagram asDiagram(){
		return (Diagram) this;
	}
	
	// --> NOTIFYING <--

	public void notifyChanges(){
		//System.out.println("NotifyChanged");
		this.setChanged();
		this.notifyObservers();
		MainFrame.getInstance().updateTitle();
		MainFrame.getInstance().repaint();
	}

}
