package mvc.model;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Element extends Figure{
	
	protected String name;
	protected int type;
	protected int ID;
	protected Box bounds;
	
	protected abstract int getDefaultWidth();
	protected abstract int getDefaultHeight();
	
	public Element(String name, int type, int X, int Y, int WIDTH, int HEIGHT) {
		this.name = name;
		this.type = type;
		int theWidth = WIDTH;
		int theHeight = HEIGHT;
		if(WIDTH < 0){
			theWidth = getDefaultWidth();
		}
		if(HEIGHT < 0){
			theHeight = getDefaultHeight();
		}
		bounds = new Box(X, Y, theWidth, theHeight);
		ID = 0;
	}
	
	@Override
	public void draw(Graphics g, Color color){
		drawAt(g, bounds.getX(), bounds.getY(), color);
	}
	
	@Override
	public void drawAt(Graphics g, int x, int y, Color color){
		drawWithSizeAt(g, x, y, bounds.getWIDTH(), bounds.getHEIGHT(), color);
	}
	
	@Override
	public void drawWithSize(Graphics g, int width, int height, Color color){
		drawWithSizeAt(g, bounds.getX(), bounds.getY(), width, height, color);
	}
	
	@Override
	public abstract boolean isInMyBounds(int X, int Y);
	


	@Override
	public void updatePosition(int newX, int newY) {
		bounds.setX(newX);
		bounds.setY(newY);
	}

	@Override
	public void updateSize(int newWidth, int newHeight) {
		bounds.setWIDTH(newWidth);
		bounds.setHEIGHT(newHeight);
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	public int getID() {
		return ID;
	}
	
	public void setID(int ID){
		this.ID = ID;
	}

	public Box getBox() {
		return bounds;
	}

	public int getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
}
