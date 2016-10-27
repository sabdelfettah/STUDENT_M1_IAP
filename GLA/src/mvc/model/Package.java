package mvc.model;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

import mvc.view.DrawZone;
import visit.FigureVisitor;

public class Package extends Figure {

	private ArrayList<Figure> childFigures;

	public Package(ArrayList<Figure> childFigures) {
		this.childFigures = childFigures;
	}

	@Override
	public void accept(FigureVisitor figureVisitor) {
		figureVisitor.visitPackage(this);
	}

	public ArrayList<Figure> getChildFigures() {
		return childFigures;
	}

	@Override
	public void draw(Graphics g, Color color) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawAt(Graphics g, int x, int y, Color color) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawWithSize(Graphics g, int width, int height, Color color) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawWithSizeAt(Graphics g, int x, int y, int width, int height, Color color) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatePosition(int newX, int newY) {
		//System.out.println("UPDATE PACKAGE");
		updatePositionGroupOfFigures(childFigures, DrawZone.getStartingPoint().x, DrawZone.getStartingPoint().y,
				newX, newY);
	}

	@Override
	public void updateSize(int newWidth, int newHeight) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isInMyBounds(int X, int Y) {
		boolean isInChildBounds = false;
		int index = 0;
		while (!isInChildBounds && index < childFigures.size()) {
			if (childFigures.get(index).isInMyBounds(X, Y)) {
				isInChildBounds = true;
			} else {
				index++;
			}
		}
		return isInChildBounds;
	}

	@Override
	public boolean isInSelection(int x1, int y1, int x2, int y2) {
		boolean isChildInSelection = true;
		System.out.println("here");
		int index = 0;
		while (isChildInSelection && index < childFigures.size()) {
			if (childFigures.get(index).isInSelection(x1, y1, x2, y2)) {
				System.out.println("in    : "+childFigures.get(index));
				index++;
			} else {
				System.out.println("not in :"+childFigures.get(index));
				isChildInSelection = false;
			}
		}
		System.out.println("v :"+isChildInSelection);
		return isChildInSelection;
	}

	@Override
	public String toString() {
		return "";
	}

	@Override
	public Figure clone() {
		ArrayList<Figure> childs = new ArrayList<>();
		for(Figure child : childFigures){
			childs.add(child.clone());
		}
		return new Package(childs);
	}

}
