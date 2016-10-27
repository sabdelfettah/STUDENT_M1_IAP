package mvc.model;

import java.awt.Color;
import java.awt.Graphics;

import visit.FigureVisitor;

public class Dependency extends Relation {

	public Dependency(Element from, Element to) {
		super(from, to, Figure.DEPENDENCY);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(FigureVisitor figureVisitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void drawWithSizeAt(Graphics g, int x, int y, int width, int height, Color color) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updatePosition(int newX, int newY) {
		// TODO Auto-generated method stub

	}

	@Override
	public void updateSize(int newWidth, int newHeight) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isInMyBounds(int X, int Y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isInSelection(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Figure clone() {
		// TODO Auto-generated method stub
		return null;
	}

}
