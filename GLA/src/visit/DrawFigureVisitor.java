package visit;

import java.awt.Color;

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
import mvc.model.Relation;
import mvc.model.UseCase;
import mvc.view.DrawZone;

public class DrawFigureVisitor implements FigureVisitor {

	private static Color drawingColor = Color.black;
	private static Color selectionColor = Color.blue;
	private static Color cutColor = Color.gray;
	//
	private static boolean groupSelection = false;
	private static boolean cutSelection = false;
	private static Figure groupSelectionFigureLuncher = null;

	private void visitSimpleElement(Element figureVisited) {
		if (cutSelection
				|| (ModelController.canPaste() && ModelController.isCutted() && ModelController.getClipboard()
						.contains(figureVisited))) {
			if (DrawZone.getCurrentPoint() != null) {
				int x =
						Figure.getNewLocation(((Element) figureVisited).getBox().getX(), DrawZone.getStartingPoint().x,
								DrawZone.getCurrentPoint().x);
				int y =
						Figure.getNewLocation(((Element) figureVisited).getBox().getY(), DrawZone.getStartingPoint().y,
								DrawZone.getCurrentPoint().y);
				figureVisited.drawAt(DrawZone.getInstance().getGraphics(), x, y, cutColor);
			} else {
				figureVisited.draw(DrawZone.getInstance().getGraphics(), cutColor);
			}
		} else if (groupSelection
				|| (ModelController.getGroupedFigures() != null && ModelController.getGroupedFigures().contains(
						figureVisited))) {
			if (DrawZone.getCurrentPoint() != null) {
				int x =
						Figure.getNewLocation(((Element) figureVisited).getBox().getX(), DrawZone.getStartingPoint().x,
								DrawZone.getCurrentPoint().x);
				int y =
						Figure.getNewLocation(((Element) figureVisited).getBox().getY(), DrawZone.getStartingPoint().y,
								DrawZone.getCurrentPoint().y);
				// System.out.println("x = " + x + " ; y = "+y);
				figureVisited.drawAt(DrawZone.getInstance().getGraphics(), x, y, selectionColor);
			} else {
				figureVisited.draw(DrawZone.getInstance().getGraphics(), selectionColor);
			}
		} else if (figureVisited.equals(ModelController.getSelectedFigure())) {
			if (DrawZone.getCurrentPoint() != null) {
				figureVisited.drawAt(DrawZone.getInstance().getGraphics(), DrawZone.getCurrentPoint().x,
						DrawZone.getCurrentPoint().y, selectionColor);
			} else {
				figureVisited.draw(DrawZone.getInstance().getGraphics(), selectionColor);
			}
		} else {
			figureVisited.draw(DrawZone.getInstance().getGraphics(), drawingColor);
		}
	}

	@Override
	public void visitClass(ClassDraw figureVisited) {
		visitSimpleElement(figureVisited);
	}

	@Override
	public void visitActor(Actor figureVisited) {
		visitSimpleElement(figureVisited);
	}

	@Override
	public void visitAction(Action figureVisited) {
		visitSimpleElement(figureVisited);
	}

	@Override
	public void visitPackage(Package figureVisited) {
		if (ModelController.canPaste() && ModelController.isCutted()
				&& ModelController.getClipboard().contains(figureVisited)) {
			cutSelection = true;
		} else if (figureVisited.equals(ModelController.getSelectedFigure())
				|| (ModelController.isFiguresSelected() && ModelController.getGroupedFigures().contains(figureVisited))) {
			groupSelection = true;
			groupSelectionFigureLuncher = figureVisited;
		}
		for (Figure child : figureVisited.getChildFigures()) {
			child.accept(this);
		}
		if (figureVisited.equals(groupSelectionFigureLuncher)) {
			groupSelection = false;
			cutSelection = false;
		}
	}

	@Override
	public void visitUseCase(UseCase figureVisited) {
		visitSimpleElement(figureVisited);
	}

	private void visitRelation(Relation relation, int type) {
		// ...
	}

	@Override
	public void visitAgregation(Agregation figureVisited) {
		visitRelation(figureVisited, figureVisited.getType());
	}

	@Override
	public void visitComposition(Composition figureVisited) {
		visitRelation(figureVisited, figureVisited.getType());
	}

	@Override
	public void visitAssociation(Association figureVisited) {
		visitRelation(figureVisited, figureVisited.getType());
	}

	@Override
	public void visitDependency(Dependency figureVisited) {
		visitRelation(figureVisited, figureVisited.getType());
	}

	@Override
	public void visitHeritage(Heritage figureVisited) {
		visitRelation(figureVisited, figureVisited.getType());
	}

	@Override
	public void visitInterfaceRealisation(InterfaceRealisation figureVisited) {
		visitRelation(figureVisited, figureVisited.getType());
	}

	@Override
	public void visitComment(Comment figureVisited) {
		// ...
	}
}
