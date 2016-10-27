package mvc.model;

import java.util.ArrayList;

import utils.History;
import mvc.model.UMLFile;

public class Diagram extends UMLFile{
	
	public static final int CLASS_DIAGRAM = 0;
	public static final int USERCASE_DIAGRAM = 1;
	//
	private ArrayList<Figure> figures;
	private int type;
	//
	private ArrayList<History> myHistory;
	private int historyIndex;
	
	public Diagram(String name, boolean isFreeDiagram, int type){
		super(name, isFreeDiagram);
		this.type = type;
		figures = new ArrayList<Figure>();
		myHistory = new ArrayList<History>();
		historyIndex = -1;
	}
	
	// Getters & Setters
	public int getType() {
		return type;
	}
	
	@Override
	public ArrayList<Diagram> getDiagrams() {
		ArrayList<Diagram> list = new ArrayList<Diagram>();
		list.add(this);
		return list;
	}
	
	@Override
	public ArrayList<Diagram> getChilds() {
		return null;
	}

	@Override
	public ArrayList<Figure> getFigures() {
		return figures;
	}

	public void addFigure(Figure newFigure){
		figures.add(newFigure);
		notifyChanges();
	}
	
	public void removeFigureAt(int index){
		figures.remove(index);
		notifyChanges();
	}
	
	public void removeFigures(ArrayList<Figure> theListOfFiguresToRemove){
		figures.removeAll(theListOfFiguresToRemove);
	}
	
	public void removeFigures(Figure[] theListOfFiguresToRemove){
		for(Figure figure : theListOfFiguresToRemove){
			figures.remove(figure);
		}
	}

	@Override
	public Diagram getWorkingOnDiagram() {
		return this;
	}

	@Override
	public String toString() {
		return name;
	}

	@Override
	public String toExtendedString() {
		return "Diagram : "+name;
	}
	
	// Menubar buttons control (undo & redo)
	public boolean canUndo(){
		return myHistory.size()>0 && historyIndex > -1;
	}
	
	public boolean canRedo(){
		return myHistory.size()>0 && historyIndex < myHistory.size() - 1;
	}
	
	// history control
	public void emptyHistory(){
		myHistory = new ArrayList<History>();
		historyIndex = -1;
	}
	
	private void removeAllUndoHistory(){
		for(int index = myHistory.size() - 1 ; index > historyIndex ; index --){
			myHistory.remove(index);
		}
	}
	
	public void addHistory(int theAction, ArrayList<Figure> objectsConcerned, Diagram source){
		removeAllUndoHistory();
		myHistory.add(new History(theAction, Figure.ArrayListOfFiguresToArray(objectsConcerned), source, null, null, null));
		historyIndex++;
	}
	
	public void addHistory(int theAction, ArrayList<Figure> objectsConcerned, Diagram source, Object[] old_data, Object[] new_data){
		removeAllUndoHistory();
		myHistory.add(new History(theAction, Figure.ArrayListOfFiguresToArray(objectsConcerned), source, null, old_data, new_data));
		historyIndex++;
	}
	
	public void addHistory(int theAction, ArrayList<Figure> objectsConcerned, Diagram source, Diagram destination){
		removeAllUndoHistory();
		myHistory.add(new History(theAction, Figure.ArrayListOfFiguresToArray(objectsConcerned), source, destination, null, null));
		historyIndex++;
	}
	
	public void undo(){
		//System.out.println("UNDO");
		System.out.println("UNDO _ "+historyIndex);
		myHistory.get(historyIndex).undo();
		historyIndex--;
		System.out.println("UNDO _ "+historyIndex);
		myHistory.get(historyIndex).getSource().setChanged(true);
		if(myHistory.get(historyIndex).getDestination() != null)
			myHistory.get(historyIndex).getDestination().setChanged(true);
		ModelController.reinitDrawZone();
		ModelController.getFile().notifyChanges();
	}
	
	public void redo(){
		//System.out.println("REDO");
		System.out.println("REDO _ "+historyIndex);
		myHistory.get(historyIndex + 1).redo();
		historyIndex++;
		System.out.println("REDO _ "+historyIndex);
		if(myHistory.get(historyIndex).getDestination() != null)
			myHistory.get(historyIndex).getDestination().setChanged(true);
		ModelController.reinitDrawZone();
		ModelController.getFile().notifyChanges();
	}

	@Override
	public void setDiagram(Diagram newDiagram) {
		if(! newDiagram.equals(this)){
			System.out.println("Error when define the new diagram");
		}
	}
	
}