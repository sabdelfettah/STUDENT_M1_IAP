package mvc.model;

import java.awt.Color;
import java.awt.Graphics;

import visit.FigureVisitor;

public class Comment extends Figure{
	
	private int figureID;
	private Box bounds;
	private String text;
	
	public Comment(int class_ID, int x, int y, int width, int height, String text){
		figureID = class_ID;
		bounds = new Box(x, y, width, height);
		this.text = text;
	}

	public int getFigureID() {
		return figureID;
	}

	public void setFigureID(int figureID) {
		this.figureID = figureID;
	}

	public Box getBox() {
		return bounds;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	@Override
	public void accept(FigureVisitor figureVisitor) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Figure clone() {
		// TODO Auto-generated method stub
		return null;
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

}
