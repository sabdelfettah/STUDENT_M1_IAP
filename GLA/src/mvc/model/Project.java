package mvc.model;

import java.util.ArrayList;

public class Project extends UMLFile{
	
	private Diagram workingOnDiagram = null;
	private ArrayList<Diagram> diagramList;
	
	public Project(String name){
		super(name, true);
		diagramList = new ArrayList<Diagram>();
		setWorkingOnDiagram(null);
	}
	
	@Override
	public ArrayList<Figure> getFigures() {
		if(workingOnDiagram != null){
			return workingOnDiagram.getFigures();
		}else{
			return null;
		}
	}
	
	@Override
	public ArrayList<Diagram> getDiagrams() {
		return diagramList;
	}
	
	@Override
	public ArrayList<Diagram> getChilds() {
		return diagramList;
	}
	
	public void addDiagramOnProjet(Diagram newDiagram){
		diagramList.add(newDiagram);
		workingOnDiagram = newDiagram;
	}
	
	public void removeDiagramAt(int index){
		diagramList.remove(index);
	}

	public void setWorkingOnDiagram(Diagram workingOnDiagram) {
		this.workingOnDiagram = workingOnDiagram;
	}

	@Override
	public Diagram getWorkingOnDiagram() {
		return workingOnDiagram;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public String toExtendedString() {
		return "Projet : "+name;
	}

	@Override
	public void setDiagram(Diagram newDiagram) {
		workingOnDiagram = newDiagram;
	}

}
