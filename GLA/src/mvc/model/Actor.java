package mvc.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import visit.FigureVisitor;

public class Actor extends Element {

	public Actor(String name, int type, int X, int Y, int WIDTH, int HEIGHT) {
		super(name, type, X, Y, WIDTH, HEIGHT);
	}

	@Override
	public void drawWithSizeAt(Graphics g, int x, int y, int width, int height, Color color) {
		g.setColor(color);
		g.drawString(name, x - name.length() * 3, y - (2 * height) - 10);
		g.drawOval(x - (width / 2), y - (height * 2), width, height);
		g.drawLine(x, y - height, x, y + height);
		g.drawLine(x - width, y - height, x + width, y - height);
		g.drawLine(x, y + height, x + (width / 2), y + (height * 2));
		g.drawLine(x, y + height, x - (width / 2), y + (height * 2));
	}

	@Override
	public boolean isInMyBounds(int X, int Y) {
		int x1 = bounds.getX() - bounds.getWIDTH();
		int x2 = bounds.getX() + bounds.getWIDTH();
		int y1 = bounds.getY() - 2 * bounds.getHEIGHT();
		int y2 = bounds.getY() + 2 * bounds.getHEIGHT();
		return (X >= x1 && X <= x2 && Y >= y1 && Y <= y2);
	}

	@Override
	protected int getDefaultWidth() {
		return 10;
	}

	@Override
	protected int getDefaultHeight() {
		return 10;
	}

	@Override
	public void accept(FigureVisitor figureVisitor) {
		figureVisitor.visitActor(this);
	}

	// @Override
	// public boolean intersect(int x1, int y1, int x2, int y2) {
	// Rectangle myRectangle = new Rectangle(bounds.getX() - bounds.getWIDTH(),
	// y1 = bounds.getY() - 2 * bounds.getHEIGHT(), bounds.getWIDTH() * 2,
	// bounds.getHEIGHT() * 4);
	// Rectangle rect = getRectangle(x1, y1, x2, y2);
	// return myRectangle.intersects(rect) || myRectangle.contains(rect);
	// }

	@Override
	public boolean isInSelection(int x1, int y1, int x2, int y2) {
		return isInSelection(x1, y1, x2, y2, new Rectangle(bounds.getX() - bounds.getWIDTH(), bounds.getY() - 2
				* bounds.getHEIGHT(), bounds.getWIDTH() * 2, bounds.getHEIGHT() * 4));
	}

	@Override
	public Figure clone() {
		return new Actor(name, type, bounds.getX(), bounds.getY(), bounds.getWIDTH(), bounds.getHEIGHT());
	}

}
