package mvc.controller;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import mvc.model.Element;
import mvc.model.Figure;
import mvc.model.ModelController;
import mvc.model.Package;
import mvc.model.Relation;
import mvc.model.UMLFile;
import mvc.view.DrawZone;
import mvc.view.MainFrame;
import mvc.view.Popup;

public class MouseController implements MouseListener, MouseMotionListener {

	private static MouseController instance = null;
	private boolean rightClick = false;
	private boolean FigureChanged = false;

	public static MouseController getInstance() {
		if (instance == null)
			instance = new MouseController();
		return instance;
	}

	private MouseController() {

	}

	// other mothods
	private int whichFigureIsSelected(int X, int Y) {
		int selectedIndex = -1;
		UMLFile file = ModelController.getFile();
		if (file.getFigures() != null) {
			ArrayList<Figure> listOfFigures = file.getFigures();
			int len = listOfFigures.size();
			int index = len - 1;
			while (index >= 0 && !listOfFigures.get(index).isInMyBounds(X, Y))
				index--;
			if (index == -1) {
				selectedIndex = -1;
			} else {
				selectedIndex = index;
			}
		} else {
			selectedIndex = -1;
		}
		// ModelController.setSelectedFigureIndex(selectedIndex);
		return selectedIndex;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {

	}

	@Override
	public void mouseExited(MouseEvent arg0) {

	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if (ModelController.getFile() != null) {
			if (ModelController.getFile().getWorkingOnDiagram() != null) {
				FigureChanged = false;
				int selected = whichFigureIsSelected(arg0.getX(), arg0.getY());
				boolean changeSelected = true;
				if (selected != -1
						&& ((ModelController.isAFigureSelected() && ModelController.getSelectedFigureIndex() == selected) || (ModelController
								.isFiguresSelected() && ModelController.getGroupedFigures().contains(
								ModelController.getFile().getWorkingOnDiagram().getFigures().get(selected))))) {
					changeSelected = false;
					// System.out.println("");
				}
				if (changeSelected) {
					if (ModelController.isAFigureSelected() || ModelController.isFiguresSelected()) {
						FigureChanged = true;
					}
					ModelController.setSelectedFigureIndex(selected);
					ModelController.getFile().notifyChanges();
				}
				if (arg0.getButton() == MouseEvent.BUTTON3) {
					rightClick = true;
					if (ModelController.isAFigureSelected() || ModelController.isFiguresSelected()) {
						if (ModelController.isAFigureSelected()) {
							Figure figure = ModelController.getSelectedFigure();
							if (figure instanceof Package) {
								Popup.getInstanceForPackage().show(DrawZone.getInstance(), arg0.getX(), arg0.getY());
							} else {
								int type = -1;
								if (figure instanceof Element) {
									type = ((Element) figure).getType();
								} else if (figure instanceof Relation) {
									type = ((Relation) figure).getType();
								}
								Popup.getInstanceForFigureOfType(type).show(DrawZone.getInstance(), arg0.getX(),
										arg0.getY());
							}
						} else {
							Popup.getInstanceForGroup().show(DrawZone.getInstance(), arg0.getX(), arg0.getY());
						}

					} else {
						Popup.getNoFigureSelectionInstance().show(DrawZone.getInstance(), arg0.getX(), arg0.getY());
					}
				} else {
					rightClick = false;
					DrawZone.setStartingPoint(new Point(arg0.getX(), arg0.getY()));
					DrawZone.setDragged(false);
				}

			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (ModelController.getFile() != null) {
			if (ModelController.getFile().getWorkingOnDiagram() != null && !rightClick) {
				if (DrawZone.isDragged()) {
					if (ModelController.isFiguresSelected() || ModelController.isAFigureSelected()) {
						Figure.updatePositionGroupOfFigures(ModelController.getGroupedFigures(),
								DrawZone.getStartingPoint().x, DrawZone.getStartingPoint().y, arg0.getX(), arg0.getY());
						ModelController.getFile().getWorkingOnDiagram().setChanged(true);
					} else {
						ArrayList<Figure> crossingFigures = new ArrayList<Figure>();
						int x1 = DrawZone.getStartingPoint().x;
						int y1 = DrawZone.getStartingPoint().y;
						int x2 = DrawZone.getCurrentPoint().x;
						int y2 = DrawZone.getCurrentPoint().y;
						for (Figure currentFigure : ModelController.getFile().getFigures()) {
							if (currentFigure.isInSelection(x1, y1, x2, y2)) {
								crossingFigures.add(currentFigure);
							}
						}
						ModelController.setGroupedFigures(crossingFigures);
						ModelController.getFile().notifyChanges();
					}
				} else {
					if (ModelController.isFiguresSelected() || ModelController.isAFigureSelected()) {
						if (whichFigureIsSelected(arg0.getX(), arg0.getY()) == -1) {
							ModelController.reinitDrawZone();
						}
					} else {
						if (!FigureChanged) {
							if (ModelController.getElementToDraw() != -1) {
								ModelController.addElementToWorkingDiagram(arg0.getX(), arg0.getY());
							} else if (ModelController.getRelationToDraw() != -1) {
								ModelController.addRelationToWorkingDiagram(arg0.getX(), arg0.getY());
							} else if (ModelController.getCommentToDraw() != -1) {
								ModelController.addCommentToWorkingDiagram(arg0.getX(), arg0.getY(), -1);
							} else {
								JOptionPane.showMessageDialog(null,
										"L'objet que vous souhaiter dessiner n'est pas reconnu",
										"Erreur lors de l'insertion", 0);
							}
							ModelController.reinitDrawZone();
						}
					}
				}
				DrawZone.setCurrentPoint(null);
				ModelController.getFile().notifyChanges();
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		if (!rightClick) {
			DrawZone.setDragged(true);
			DrawZone.setCurrentPoint(arg0.getPoint());
			MainFrame.getInstance().repaint();
		}
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {

	}

}
