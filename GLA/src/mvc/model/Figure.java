package mvc.model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Area;
import java.util.ArrayList;

public abstract class Figure implements FigureVisited {

	// ELEMENTS
	public static final int FIRST_ELEMENT = 0;
	public static final int CLASS = 0;
	public static final int ACTOR = 1;
	public static final int ACTION = 2;
	public static final int USECASE = 3;
	public static final int LAST_ELEMENT = 3;
	// RELATIONS
	public static final int FIRST_RELATION = 0;
	public static final int HERITAGE = 100;
	public static final int AGREGATION = 102;
	public static final int COMPOSITION = 103;
	public static final int ASSOCIATION = 104;
	public static final int DEPENDENCY = 105;
	public static final int INTERFACE_REALISATION = 106;
	public static final int LAST_RELATION = 106;
	//
	public static final int COMMENT = 998;
	public static final int PACKAGE = 999;

	//
	public abstract Figure clone();

	//
	public abstract void draw(Graphics g, Color color);

	public abstract void drawAt(Graphics g, int x, int y, Color color);

	public abstract void drawWithSize(Graphics g, int width, int height, Color color);

	public abstract void drawWithSizeAt(Graphics g, int x, int y, int width, int height, Color color);

	public abstract void updatePosition(int newX, int newY);

	public abstract void updateSize(int newWidth, int newHeight);

	public abstract boolean isInMyBounds(int X, int Y);

	public boolean isInMyBounds(Point p) {
		return isInMyBounds(p.x, p.y);
	}

	public abstract boolean isInSelection(int x1, int y1, int x2, int y2);

	public abstract String toString();

	public static int[] computeRect(int x1, int y1, int x2, int y2) {
		int width = Math.abs(x1 - x2);
		int height = Math.abs(y1 - y2);
		int x = Math.min(x1, x2);
		int y = Math.min(y1, y2);
		int[] result = { x, y, width, height };
		return result;
	}

	public static Rectangle getRectangle(int x1, int y1, int x2, int y2) {
		int[] rect = computeRect(x1, y1, x2, y2);
		return new Rectangle(rect[0], rect[1], rect[2], rect[3]);
	}

	public static boolean isInSelection(int x1, int y1, int x2, int y2, Shape shape) {
		Rectangle rect = getRectangle(x1, y1, x2, y2);
		if (shape.intersects(rect)) {
			return true;
		} else {
			int s = Short.MAX_VALUE;
			Area a = new Area(new Rectangle(-s, -s, s + s, s + s));
			Area sub = new Area(rect);
			a.subtract(sub);
			Area b = new Area(shape);
			b.intersect(a);
			return b.isEmpty();
		}
	}

	public static int getNewLocation(int oldLocation, int oldPosition, int newPosition) {
		return oldLocation - oldPosition + newPosition;
	}

	public static void updatePositionGroupOfFigures(ArrayList<Figure> listOfFigures, int oldX, int oldY, int newX,
			int newY) {
		for (Figure child : listOfFigures) {
			if (child instanceof Package) {
				updatePositionGroupOfFigures(((Package) child).getChildFigures(), oldX, oldY, newX, newY);
			} else {
				int x = getNewLocation(((Element) child).getBox().getX(), oldX, newX);
				int y = getNewLocation(((Element) child).getBox().getY(), oldY, newY);
				child.updatePosition(x, y);
			}
		}
	}

	public static Figure[] ArrayListOfFiguresToArray(ArrayList<Figure> list) {
		Figure[] figuresToDelete = {};
		figuresToDelete = list.toArray(figuresToDelete);
		return figuresToDelete;
	}

	public static ArrayList<Figure> ArrayListFromFigure(Figure figure) {
		ArrayList<Figure> list = new ArrayList<Figure>();
		list.add(figure);
		return list;
	}

}
