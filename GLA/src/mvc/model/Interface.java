package mvc.model;

import java.awt.Color;
import java.awt.Graphics;

import visit.FigureVisitor;

public class Interface extends Element {

	public Interface(String name, int type, int X, int Y, int WIDTH, int HEIGHT) {
		super(name, type, X, Y, WIDTH, HEIGHT);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void accept(FigureVisitor figureVisitor) {
		// TODO Auto-generated method stub

	}

	@Override
	protected int getDefaultWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	protected int getDefaultHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isInMyBounds(int X, int Y) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Figure clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void drawWithSizeAt(Graphics g, int x, int y, int width, int height, Color color) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isInSelection(int x1, int y1, int x2, int y2) {
		// TODO Auto-generated method stub
		return false;
	}

}
