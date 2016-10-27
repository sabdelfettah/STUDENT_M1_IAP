package mvc.model;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Relation extends Figure{
	
	private Element from;
	private Element to;
	private int type;
	
	public Relation(Element from, Element to, int type){
		this.setFrom(from);
		this.setTo(to);
	}

	public Element getFrom() {
		return from;
	}

	public void setFrom(Element from) {
		this.from = from;
	}

	public Element getTo() {
		return to;
	}

	public void setTo(Element to) {
		this.to = to;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	//
	
	@Override
	public void draw(Graphics g, Color color){
		drawAt(g, 0, 0, color);
	}
	
	@Override
	public void drawAt(Graphics g, int x, int y, Color color){
		drawWithSizeAt(g, x, y, 0, 0, color);
	}
	
	@Override
	public void drawWithSize(Graphics g, int width, int height, Color color){
		drawWithSizeAt(g, 0, 0, width, height, color);
	}
}
