package mvc.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import visit.FigureVisitor;

public class Action extends Element {

	public Action(String name, int type, int X, int Y, int WIDTH, int HEIGHT) {
		super(name, type, X, Y, WIDTH, HEIGHT);
	}

	@Override
	public void drawWithSizeAt(Graphics g, int x, int y, int width, int height, Color color) {
		g.setColor(color);
		g.drawString(name, x, y - 2);
		g.drawOval(x, y, width, height);
	}

	@Override
	public boolean isInMyBounds(int X, int Y) {
		Shape oval = new Ellipse2D.Float(bounds.getX(), bounds.getY(), bounds.getWIDTH(), bounds.getHEIGHT());
		return oval.contains(X, Y);
	}

	@Override
	protected int getDefaultWidth() {
		return 40;
	}

	@Override
	protected int getDefaultHeight() {
		return 20;
	}

	@Override
	public void accept(FigureVisitor figureVisitor) {
		figureVisitor.visitAction(this);
	}

	@Override
	public boolean isInSelection(int x1, int y1, int x2, int y2) {
		return isInSelection(x1, y1, x2, y2, new Ellipse2D.Float(bounds.getX(), bounds.getY(), bounds.getWIDTH(),
				bounds.getHEIGHT()));
	}

	@Override
	public Figure clone() {
		return new Action(name, type, bounds.getX(), bounds.getY(), bounds.getWIDTH(), bounds.getHEIGHT());
	}

}
