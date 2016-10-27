package utils;

import mvc.model.Diagram;
import mvc.model.Element;
import mvc.model.Figure;
import mvc.model.ModelController;

public class History {

	public static final int INSERT_FIGURES = 0;
	public static final int DELETE_FIGURES = 1;
	public static final int UPDATE_POSITION = 2;
	public static final int UPDATE_SIZE = 3;
	public static final int UPDATE_NAME = 4;
	public static final int MOVE_FIGURES = 5; // from a diagram to another
	//
	private int theAction = -1;
	private Figure[] objectsConcerned = null;
	private Diagram source = null;
	private Diagram destination = null;
	private Object[] old_data = null;
	private Object[] new_data = null;

	public History(int theAction, Figure[] objectsConcerned, Diagram source, Diagram destination, Object[] old_data,
			Object[] new_data) {
		this.theAction = theAction;
		this.objectsConcerned = objectsConcerned;
		this.source = source;
		this.destination = destination;
		this.old_data = old_data;
		this.new_data = new_data;
	}

	public Diagram getSource() {
		return source;
	}

	public Diagram getDestination() {
		return destination;
	}

	private void remove() {
		System.out.println("remove");
		source.removeFigures(objectsConcerned);
		ModelController.getFile().notifyChanges();
	}

	private void reInsert() {
		for (Figure figureDeleted : objectsConcerned) {
			source.addFigure(figureDeleted);
		}
		ModelController.getFile().notifyChanges();
	}

	private void reMoveFigures(boolean undo) {
		if (source != destination) {
			Diagram sourceDiagram = source;
			Diagram destinationDiagram = destination;
			if (!undo) {
				sourceDiagram = destination;
				destinationDiagram = source;
			}
			destinationDiagram.removeFigures(objectsConcerned);
			for (Figure currentFigure : objectsConcerned) {
				sourceDiagram.addFigure(currentFigure);
			}
		}
	}

	private void rename(boolean undo) {
		// System.out.println("Rename _ "+undo);
		String theName = (String) old_data[0];
		if (!undo) {
			theName = (String) new_data[0];
		}
		Element element = (Element) objectsConcerned[0];
		element.setName(theName);
		ModelController.getFile().notifyChanges();
	}

	public void undo() {
		System.out.println("Undo");
		switch (theAction) {
		case INSERT_FIGURES:
			remove();
			break;
		case DELETE_FIGURES:
			reInsert();
			break;
		case UPDATE_POSITION:

			break;
		case UPDATE_SIZE:

			break;
		case UPDATE_NAME:
			rename(true);
			break;
		case MOVE_FIGURES:
			reMoveFigures(true);
			break;
		default:
			break;
		}
	}

	public void redo() {
		System.out.println("Undo");
		switch (theAction) {
		case INSERT_FIGURES:
			reInsert();
			break;
		case DELETE_FIGURES:
			remove();
			break;
		case UPDATE_POSITION:

			break;
		case UPDATE_SIZE:

			break;
		case UPDATE_NAME:
			rename(false);
			break;
		case MOVE_FIGURES:
			reMoveFigures(false);
			break;
		default:
			break;
		}
	}

}
