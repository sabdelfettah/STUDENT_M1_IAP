package mvc.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;

import javax.swing.JPanel;

import mvc.controller.MouseController;
import mvc.model.Diagram;
import mvc.model.Figure;
import mvc.model.ModelController;
import visit.DrawFigureVisitor;

public class DrawZone extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5984926580310601787L;
	private static DrawZone instance;
	//
	private Graphics graphics;
	//
	private static Color backgroundColor = Color.lightGray;
	private static Color selectionColor = Color.blue;
	//
	private static Point startingPoint = null;
	private static Point currentPoint = null;
	private static boolean dragged = false;
	
	private DrawZone() {
		super();
		this.setFocusable(true);
		this.requestFocusInWindow(true);
		this.addMouseListener(MouseController.getInstance());
		this.addMouseMotionListener(MouseController.getInstance());
	}

	// Getters & Setters
	public static DrawZone getInstance() {
		if (instance == null)
			instance = new DrawZone();
		return instance;
	}

	public static Point getCurrentPoint() {
		return currentPoint;
	}

	public static void setCurrentPoint(Point currentPoint) {
		DrawZone.currentPoint = currentPoint;
	}

	public static boolean isDragged() {
		return dragged;
	}

	public static void setDragged(boolean dragged) {
		DrawZone.dragged = dragged;
	}

	public Graphics getGraphics() {
		return graphics;
	}

	public static Point getStartingPoint() {
		return startingPoint;
	}

	public static void setStartingPoint(Point startingPoint) {
		DrawZone.startingPoint = startingPoint;
	}

	private void drawRectFromPointToPoint(Graphics g, int x1, int y1, int x2, int y2) {
		int[] rect = Figure.computeRect(x1, y1, x2, y2);
		g.drawRect(rect[0], rect[1], rect[2], rect[3]);
	}

	//
	public void paintComponent(Graphics g) {
		graphics = g;
		if (ModelController.getFile() != null && ModelController.getFile().getWorkingOnDiagram() != null) {
			Diagram diagramToDraw = ModelController.getFile().getWorkingOnDiagram();
			if (diagramToDraw != null) {
				g.setColor(Color.black);
				g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
				g.setColor(backgroundColor);
				g.fillRect(1, 1, getWidth() - 2, getHeight() - 2);
				int len = diagramToDraw.getFigures().size();
				DrawFigureVisitor dfv = new DrawFigureVisitor();
				for (int index = 0; index < len; index++) {
					diagramToDraw.getFigures().get(index).accept(dfv);
				}
				if (ModelController.getSelectedFigure() == null && startingPoint != null && currentPoint != null && ModelController.getGroupedFigures() == null) {
					g.setColor(selectionColor);
					drawRectFromPointToPoint(g, startingPoint.x, startingPoint.y, currentPoint.x, currentPoint.y);
				}
			} else {
				g.setColor(Color.white);
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		}

	}

}
