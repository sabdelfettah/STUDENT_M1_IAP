package factory;

import java.util.ArrayList;

import lang.Langage;
import mvc.model.Action;
import mvc.model.Actor;
import mvc.model.Agregation;
import mvc.model.Association;
import mvc.model.ClassDraw;
import mvc.model.Comment;
import mvc.model.Composition;
import mvc.model.Dependency;
import mvc.model.Element;
import mvc.model.Figure;
import mvc.model.Heritage;
import mvc.model.InterfaceRealisation;
import mvc.model.ModelController;
import mvc.model.Package;
import mvc.model.UseCase;

public class FigureFactory {

	public Figure createNewFigure(int[] args) {
		Figure figure = null;
		if (args[0] == Figure.PACKAGE) {
			ArrayList<Figure> listOfFiguresToGroup = ModelController.getGroupedFigures();
			ModelController.getFile().getWorkingOnDiagram().removeFigures(listOfFiguresToGroup);
			figure = new Package(listOfFiguresToGroup);
		} else if (args[0] == Figure.COMMENT) {
			int classID = args[1];
			int X = args[2];
			int Y = args[3];
			int width = 20;
			int height = 20;
			figure = new Comment(classID, X, Y, width, height, "");
		} else if (args[0] >= Figure.FIRST_ELEMENT && args[0] <= Figure.LAST_ELEMENT) {
			int type = args[0];
			int X = args[1];
			int Y = args[2];
			switch (type) {
			case Element.CLASS:
				figure =
						new ClassDraw(
								Langage.getString("FigureFactory.new_Class"), type, ClassDraw.NO_ABSTRACT_NO_INTERFACE_STEREOTYPE, X, Y, //$NON-NLS-1$
								-1, -1);
				break;
			case Element.ACTOR:
				figure = new Actor(Langage.getString("FigureFactory.new_Actor"), type, X, Y, -1, -1); //$NON-NLS-1$
				break;
			case Element.ACTION:
				figure = new Action(Langage.getString("FigureFactory.naw_Actor"), type, X, Y, -1, -1); //$NON-NLS-1$
				break;
			case Element.USECASE:
				figure = new UseCase(Langage.getString("FigureFactory.new_Action"), type, X, Y, -1, -1); //$NON-NLS-1$
				break;
			}

		} else if (args[0] >= Figure.FIRST_RELATION && args[0] <= Figure.LAST_RELATION) {
			int type = args[0];
			//int from = args[1];
			//int to = args[2];
			Element fromElement = (Element) ModelController.getFile().getFigures().get(0);
			Element toElement = (Element) ModelController.getFile().getFigures().get(1);
			switch (type) {
			case Figure.AGREGATION:
				figure = new Agregation(fromElement, toElement);
				break;
			case Figure.COMPOSITION:
				figure = new Composition(fromElement, toElement);
				break;
			case Figure.ASSOCIATION:
				figure = new Association(fromElement, toElement);
				break;
			case Figure.DEPENDENCY:
				figure = new Dependency(fromElement, toElement);
				break;
			case Figure.INTERFACE_REALISATION:
				figure = new InterfaceRealisation(fromElement, toElement);
				break;
			case Figure.HERITAGE:
				figure = new Heritage(fromElement, toElement);
				break;
			}
		} else {
			figure = null;
		}
		return figure;
	}
}
