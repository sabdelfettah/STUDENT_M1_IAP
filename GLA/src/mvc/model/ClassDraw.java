package mvc.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.ArrayList;

import visit.FigureVisitor;

public class ClassDraw extends Element {
	public static final short NO_ABSTRACT_NO_INTERFACE_STEREOTYPE = 0;
	public static final short ABSTRACT_STEREOTYPE = 1;
	public static final short INTERFACE_STEREOTYPE = 2;
	private short stereotype;
	private short visibility;
	private ArrayList<Attribute> attributes;
	private ArrayList<Method> methods;

	public ClassDraw(String name, int type, short stereotype, int x, int y, int width, int height) {
		super(name, type, x, y, width, height);
		this.stereotype = stereotype;
		attributes = new ArrayList<Attribute>();
		methods = new ArrayList<Method>();
	}

	public static String stereotypeString(short stereotype){
		switch(stereotype){
		case ABSTRACT_STEREOTYPE: return "abstract";
		case INTERFACE_STEREOTYPE: return "interface";
		default: return "";
		}
	}
	
	public static short stereotypeShort(String stereotype){
		if(stereotype.equals("abstract")){
			return ABSTRACT_STEREOTYPE;
		}else if(stereotype.equals("interface")){
			return INTERFACE_STEREOTYPE;
		}else{
			return NO_ABSTRACT_NO_INTERFACE_STEREOTYPE;
		}
	}

	public short getStereotype() {
		return stereotype;
	}

	public void setStereotype(short stereotype) {
		this.stereotype = stereotype;
	}
	
	public short getVisibility() {
		return visibility;
	}

	public void setVisibility(short visibility) {
		this.visibility = visibility;
	}

	public void addAttribute(Attribute attr) {
		attributes.add(attr);
	}

	public void addAttribute(String name, int type, short visibility, boolean Static, boolean _final) {
		attributes.add(new Attribute(name, type, visibility, Static, _final));
	}

	public ArrayList<Attribute> getAttributes() {
		return attributes;
	}

	public ArrayList<Method> getMethods() {
		return methods;
	}

	public void addMethod(Method meth) {
		methods.add(meth);
	}

	public void addMethod(String name, int type, short visibility, boolean _static, boolean _abstract) {
		methods.add(new Method(name, type, visibility, _static, _abstract));
	}

	// public void setmethods(ArrayList<Method> methods) {
	// this.methods = methods;
	// }

	@Override
	public void drawWithSizeAt(Graphics g, int x, int y, int width, int height, Color color) {
		int maxX = 0;
		int maxY = 0;
		// to update
		g.setColor(color);
		int x1 = x - width / 2;
		int y1 = y - height / 2;
		g.drawString(getName(), x1 + 1, y1 + 12);
		maxX = g.getFontMetrics(g.getFont()).stringWidth(getName());
		int i = 0;
		if (attributes.size() > 0) {
			for (i = 0; i < attributes.size(); i++) {
				g.drawString(attributes.get(i).toString(), x1 + 1, y1 + 27 + 15 * i);
				maxX = Math.max(maxX, g.getFontMetrics(g.getFont()).stringWidth(attributes.get(i).toString()));
			}
		}
		
		if (methods.size() > 0) {
			for (int j = 0; j < methods.size(); j++) {
				g.drawString(methods.get(j).toString(), x1 + 1, y1 + 27 + (15 * (j + i)));
				maxX = Math.max(maxX, g.getFontMetrics(g.getFont()).stringWidth(methods.get(j).toString()));
			}
		}
		g.drawRect(x1, y1, maxX + 2, height);
		g.drawLine(x1, y1 + 15, x1 + maxX + 2, y1 + 15);
		g.drawLine(x1, y1 + 15 * (i + 1), x1 + maxX + 2, y1 + 15 * (i + 1));
	}

	@Override
	public boolean isInMyBounds(int X, int Y) {
		int x1 = bounds.getX() - bounds.getWIDTH() / 2;
		int x2 = bounds.getX() + bounds.getWIDTH() / 2;
		int y1 = bounds.getY() - bounds.getHEIGHT() / 2;
		int y2 = bounds.getY() + bounds.getHEIGHT() / 2;
		return (X >= x1 && X <= x2 && Y >= y1 && Y <= y2);
	}

	@Override
	protected int getDefaultWidth() {
		return 80;
	}

	@Override
	protected int getDefaultHeight() {
		return 100;
	}

	@Override
	public void accept(FigureVisitor figureVisitor) {
		figureVisitor.visitClass(this);
	}
	
	@Override
	public boolean isInSelection(int x1, int y1, int x2, int y2) {
		return isInSelection(x1, y1, x2, y2, new Rectangle(bounds.getX() - bounds.getWIDTH() / 2, bounds.getY()
				- bounds.getHEIGHT() / 2, bounds.getWIDTH(), bounds.getHEIGHT()));
	}

	@Override
	public Figure clone() {
		ClassDraw newClass =
				new ClassDraw(name, type, stereotype, bounds.getX(), bounds.getY(), bounds.getWIDTH(),
						bounds.getHEIGHT());
		// to improve
		return newClass;
	}

}
