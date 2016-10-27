package mvc.model;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

import com.itextpdf.awt.PdfGraphics2D;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfTemplate;
import com.itextpdf.text.pdf.PdfWriter;

import XMLParser.ExportXML;
import XMLParser.ImportXML;
import mvc.view.DrawZone;
import mvc.view.Modifier;
import factory.FigureFactory;
import utils.History;
import utils.WindowManaging;

public class ModelController {

	private static int elementToDraw = -1; // what element to draw (when
											// clicking on DrawZone)
	private static int relationToDraw = -1; // what kind of relation to draw
											// (when clicking on DrawZone)
	private static int commentToDraw = -1;
	//
	private static int selectedFigureIndex = -1; // the index (figures) of the
													// selected
	//
	private static ArrayList<Figure> groupedFigures = null;
	//
	private static Object selectedObject;
	//
	private static ArrayList<Figure> clipboard = null;
	private static Diagram sourceClipboard = null;
	private static boolean cutted = false;
	//
	private static FigureFactory factory = new FigureFactory();
	//
	// private static boolean changed = false;
	private static UMLFile file;

	// private static Factory

	// Getters && Setters
	public static UMLFile getFile() {
		return file;
	}

	public static void setFile(UMLFile file) {
		ModelController.file = file;
	}

	public static boolean isChanged() {
		if (file == null || file.getWorkingOnDiagram() == null)
			return false;
		else
			return file.getWorkingOnDiagram().isChanged();
	}

	public static ArrayList<UMLFile> filesChanged() {
		ArrayList<UMLFile> UMLFiles = new ArrayList<UMLFile>();
		if (file != null) {
			if (file.isChanged()) {
				UMLFiles.add(file);
			}
			if (file.getChilds() != null) {
				for (Diagram diagram : file.getChilds()) {
					if (diagram.isChanged()) {
						UMLFiles.add(diagram);
					}
				}
			}
		}
		System.out.println("file = "+UMLFiles); 
		return UMLFiles;
	}

	public static int getSelectedFigureIndex() {
		return selectedFigureIndex;
	}

	public static void setSelectedFigureIndex(int selectedFigureIndex) {
		ModelController.selectedFigureIndex = selectedFigureIndex;
		if (selectedFigureIndex == -1) {
			groupedFigures = null;
		} else {
			groupedFigures = new ArrayList<Figure>();
			groupedFigures.add(ModelController.getFile().getFigures().get(selectedFigureIndex));
		}
	}

	public static boolean isAFigureSelected() {
		return groupedFigures != null && groupedFigures.size() == 1;
	}

	public static boolean isFiguresSelected() {
		return groupedFigures != null && groupedFigures.size() > 1;
	}

	public static Figure getSelectedFigure() {
		if (groupedFigures != null && groupedFigures.size() == 1)
			return groupedFigures.get(0);
		else
			return null;
	}

	public static ArrayList<Figure> getGroupedFigures() {
		return groupedFigures;
	}

	public static void setGroupedFigures(ArrayList<Figure> groupedFigures) {
		if (groupedFigures != null) {
			setSelectedFigureIndex(-1);
		}
		ModelController.groupedFigures = groupedFigures;
	}

	// public static void setChanged(boolean changed) {
	// ModelController.changed = changed;
	// }

	public static void reinitToolBoxConfigs() {
		elementToDraw = -1;
		relationToDraw = -1;
		commentToDraw = -1;
	}

	public static int getRelationToDraw() {
		return relationToDraw;
	}

	public static void setRelationToDraw(int newRelationToDraw) {
		reinitToolBoxConfigs();
		relationToDraw = newRelationToDraw;
	}

	public static int getElementToDraw() {
		return elementToDraw;
	}

	public static void setElementToDraw(int newElementToDraw) {
		reinitToolBoxConfigs();
		elementToDraw = newElementToDraw;
	}

	public static int getCommentToDraw() {
		return commentToDraw;
	}

	public static void setCommentToDraw(int newCommentToDraw) {
		reinitToolBoxConfigs();
		commentToDraw = newCommentToDraw;
	}

	public static ArrayList<Figure> getClipboard() {
		return clipboard;
	}

	public static void setClipboard(ArrayList<Figure> clipboard) {
		ModelController.clipboard = clipboard;
	}

	public static Object getSelectedObject() {
		return selectedObject;
	}

	public static void setSelectedObject(Object selectedObject) {
		ModelController.selectedObject = selectedObject;
	}

	public static boolean canPaste() {
		return clipboard != null;
	}

	public static boolean isCutted() {
		return cutted;
	}

	public static void setCutted(boolean cutted) {
		ModelController.cutted = cutted;
	}

	// method of control
	private static String askForCreatingNewElement(String what) {
		return JOptionPane.showInputDialog(null, "Veuillez introduire son nom :", "Création d'un/une " + what, 3);
	}

	public static void createNewProject(String projectName) {
		String newProjectName = "";
		if (projectName == null) {
			while (newProjectName != null && newProjectName.length() == 0) {
				newProjectName = askForCreatingNewElement("projet");
			}
		} else
			newProjectName = projectName;
		if (newProjectName != null) {
			file = new Project(newProjectName);
			file.notifyChanges();
		}
	}

	public static void createNewDiagram(int type, String diagramName) {
		String newDiagramName = "";
		if (diagramName == null) {
			while (newDiagramName != null && newDiagramName.length() == 0) {
				newDiagramName = askForCreatingNewElement("diagramme");
			}
		} else
			newDiagramName = diagramName;
		if (newDiagramName != null) {
			// getChild() return null when it's a diagram and not when it's a
			// project
			if (file != null && file.getChilds() != null) {
				((Project) file).addDiagramOnProjet(new Diagram(newDiagramName, file == null, type));
			} else {
				file = new Diagram(newDiagramName, file == null, type);
			}
			reinitToolBoxConfigs();
			file.notifyChanges();
		}
	}

	public static void saveProjectAs() {
		Project project = (Project) file;
		File f = WindowManaging.chooseFileForXMLExport(true);
		if (f != null) {
			ExportXML.ExportProject(project, f, false);
		}
		file.getWorkingOnDiagram().setChanged(false);
		file.notifyChanges();
	}

	public static void saveDiagramAs() {
		Diagram diagram = (Diagram) file;
		File f = WindowManaging.chooseFileForXMLExport(false);
		if (f != null) {
			ExportXML.ExportDiagram(diagram, f);
		}
		file.getWorkingOnDiagram().setChanged(false);
		file.notifyChanges();
	}

	public static boolean save(UMLFile theFile, boolean withDiagrams) {
		boolean cancel = false;
		if (theFile.getPath() == null) {
			if (file.getPath() == null) {
				File f = WindowManaging.chooseFileForXMLExport(theFile.getChilds() != null);
				if (f == null)
					cancel = true;
				else
					theFile.setPath(f.getAbsolutePath());
			} else {
				File f = new File(file.getPath());
				theFile.setPath(f.getParent() + "\\" + theFile.getName() + ".gloD");
			}
			//System.out.println("file ? = " + (theFile.equals(file)) + " ; path = " + file.getPath());
		}
		if (theFile.getPath() != null) {
			File exportFile = new File(theFile.getPath());
			if (theFile.getChilds() == null) {
				ExportXML.ExportDiagram(theFile.asDiagram(), exportFile);
			} else {
				ExportXML.ExportProject(theFile.asProject(), exportFile, withDiagrams);
			}
			theFile.setChanged(false);
		}
		return !cancel;
	}

	public static boolean saveCurrentWork() {
		boolean cancel = false;
		ArrayList<UMLFile> changedFiles = filesChanged();
		if (changedFiles.size() > 0) {
			if(file.getPath() == null){
				if (!save(file, true))
					cancel = true;
			}else{
				for (UMLFile changedFile : changedFiles) {
					if (!save(changedFile, false))
						cancel = true;
				}
			}
		}
		file.getWorkingOnDiagram().setChanged(false);
		file.notifyChanges();
		return !cancel;
	}

	public static void openAProject() {
		// UMLFile oldFile = file;
		ImportXML.importUMLFile(true);
		file.getWorkingOnDiagram().setChanged(false);
		file.notifyChanges();
	}

	public static void openADiagram() {
		ImportXML.importUMLFile(false);
		file.getWorkingOnDiagram().setChanged(false);
		if (file != null)
			file.notifyChanges();
	}

	public static void close() {
		UMLFile tempFile = file;
		boolean setToNull = false;
		ArrayList<UMLFile> changedFiles = filesChanged();
		if (changedFiles.size() > 0) {
			int answer = 0;
			if (file.getChilds() == null)
				answer = WindowManaging.answerToSaveBeforeCloseDiagram(file.getName());
			else
				answer = WindowManaging.answerToSaveBeforeCloseProject(file.getName());
			if (answer == JOptionPane.NO_OPTION)
				setToNull = saveCurrentWork();
		} else {
			setToNull = true;
		}
		if (setToNull) {
			reinitDrawZone();
			file = null;
			if (tempFile != null)
				tempFile.notifyChanges();
		}
	}

	public static void addElementToWorkingDiagram(int X, int Y) {
		reinitDrawZone();
		int[] params = { elementToDraw, X, Y };
		Figure elementToAdd = factory.createNewFigure(params);
		file.getWorkingOnDiagram().addFigure(elementToAdd);
		file.getWorkingOnDiagram().addHistory(History.INSERT_FIGURES, Figure.ArrayListFromFigure(elementToAdd),
				file.getWorkingOnDiagram());
		file.getWorkingOnDiagram().setChanged(true);
		file.notifyChanges();
	}

	public static void addRelationToWorkingDiagram(int from, int to) {
		reinitDrawZone();
		int[] params = { relationToDraw, from, to };
		file.getWorkingOnDiagram().addFigure(factory.createNewFigure(params));
		file.getWorkingOnDiagram().setChanged(true);
		file.notifyChanges();
	}

	public static void addCommentToWorkingDiagram(int X, int Y, int of) {
		reinitDrawZone();
		int[] params = { Figure.CLASS, X, Y, of };
		file.getWorkingOnDiagram().addFigure(factory.createNewFigure(params));
		file.getWorkingOnDiagram().setChanged(true);
		file.notifyChanges();
	}

	public static void groupFigures() {
		int[] params = { Figure.PACKAGE };
		file.getWorkingOnDiagram().addFigure(factory.createNewFigure(params));
		file.getWorkingOnDiagram().setChanged(true);
		reinitDrawZone();
		file.notifyChanges();
	}

	public static void ungroupFigures() {
		// System.out.println("Grouping");
		Package thePackage = (Package) ModelController.getSelectedFigure();
		ArrayList<Figure> listOfFigures = thePackage.getChildFigures();
		file.getWorkingOnDiagram().removeFigureAt(selectedFigureIndex);
		for (Figure child : listOfFigures) {
			file.getWorkingOnDiagram().getFigures().add(child);
		}
		file.getWorkingOnDiagram().setChanged(true);
		reinitDrawZone();
		file.notifyChanges();
	}

	public static void cut() {
		cutted = true;
		copy();
	}

	public static void copy() {
		sourceClipboard = file.getWorkingOnDiagram();
		clipboard = groupedFigures;
		file.notifyChanges();
	}

	public static void paste() {
		if (cutted) {
			if (!file.getWorkingOnDiagram().equals(sourceClipboard)) {
				sourceClipboard.removeFigures(clipboard);
				for (Figure currentFigure : clipboard) {
					file.getWorkingOnDiagram().addFigure(currentFigure);
				}
			}
			file.getWorkingOnDiagram().addHistory(History.MOVE_FIGURES, clipboard, sourceClipboard,
					file.getWorkingOnDiagram());
			cutted = false;
		} else {
			ArrayList<Figure> newFigures = new ArrayList<Figure>();
			for (Figure currentFigure : clipboard) {
				Figure currentClonedFigure = currentFigure.clone();
				newFigures.add(currentClonedFigure);
				file.getWorkingOnDiagram().addFigure(currentClonedFigure);
			}
			Figure.updatePositionGroupOfFigures(newFigures, 0, 0, 20, 20);
			file.getWorkingOnDiagram().addHistory(History.INSERT_FIGURES, newFigures, file.getWorkingOnDiagram());
		}
		sourceClipboard.setChanged(true);
		file.getWorkingOnDiagram().setChanged(true);
		cutted = false;
		groupedFigures = null;
		clipboard = null;
		sourceClipboard = null;
		file.notifyChanges();
	}

	public static void selectAllFigures() {
		groupedFigures = file.getFigures();
		file.notifyChanges();
	}

	public static void deleteSelectedFigures() {
		file.getWorkingOnDiagram().addHistory(History.DELETE_FIGURES, groupedFigures, file.getWorkingOnDiagram());
		file.getWorkingOnDiagram().removeFigures(groupedFigures);
		groupedFigures = null;
		file.getWorkingOnDiagram().setChanged(true);
		file.notifyChanges();
	}

	// method of control of a class
	public static void cancelEditing() {
		Modifier.setMode(-1);
		file.notifyChanges();
	}

	private static boolean wellConstructedMethod(Method method) {
		if (method.getName().length() > 0) {
			boolean nonEmptyElement = true;
			int index = 0;
			while (nonEmptyElement && index < method.getArguments().size()) {
				if (method.getArguments().get(index).getName().length() > 0) {
					index++;
				} else {
					return false;
				}
			}
			return true;
		} else {
			return false;
		}
	}

	public static void addAnAttribute() {
		Attribute attribute = (Attribute) Modifier.getInstance().getObject();
		if (attribute.getName().length() > 0) {
			ClassDraw theClass = (ClassDraw) getSelectedFigure();
			theClass.addAttribute(attribute);
			cancelEditing();
			file.getWorkingOnDiagram().setChanged(true);
		} else {
			WindowManaging.showWarningMessageOnAttributeName();
		}
	}

	public static void addAMethod() {
		Method method = (Method) Modifier.getInstance().getObject();
		if (wellConstructedMethod(method)) {
			ClassDraw theClass = (ClassDraw) getSelectedFigure();
			theClass.addMethod(method);
			cancelEditing();
			file.getWorkingOnDiagram().setChanged(true);
		} else {
			WindowManaging.showWarningMessageOnMathodOrParametersName();
		}

	}

	public static void alterAttribute(Attribute updated) {
		if (updated.getName().length() > 0) {
			Attribute origin = (Attribute) selectedObject;
			origin.setName(updated.getName());
			origin.setType(updated.getType());
			origin.setVisibility((short) updated.getVisibility());
			origin.setStatic(updated.isStatic());
			origin.set_final(updated.is_final());
			selectedObject = null;
			cancelEditing();
			file.getWorkingOnDiagram().setChanged(true);
		} else {
			WindowManaging.showWarningMessageOnAttributeName();
		}

	}

	public static void alterMethod(Method updated) {
		if (wellConstructedMethod(updated)) {
			Method origin = (Method) selectedObject;
			origin.setName(updated.getName());
			origin.setType(updated.getType());
			origin.setVisibility((short) updated.getVisibility());
			origin.setStatic(updated.isStatic());
			origin.set_abstract(updated.is_abstract());
			origin.getArguments().clear();
			for (Parameter parameter : updated.getArguments()) {
				origin.addParameter(parameter);
			}
			selectedObject = null;
			cancelEditing();
			file.getWorkingOnDiagram().setChanged(true);
		} else {
			WindowManaging.showWarningMessageOnMathodOrParametersName();
		}
	}

	// Export
	public static void exportPDF() {
		File f = WindowManaging.chooseFileForPDFExport();
		if (f != null) {
			Document document = new Document(PageSize.A3);
			PdfWriter writer;
			try {
				writer = PdfWriter.getInstance(document, new FileOutputStream(f.getAbsoluteFile()));
				document.open();
				PdfContentByte cb = writer.getDirectContent();
				float width = DrawZone.getInstance().getWidth();
				float height = DrawZone.getInstance().getHeight();
				PdfTemplate resultsPanelPdfTemplate = cb.createTemplate(width, height);
				Graphics2D g = new PdfGraphics2D(cb, width, height);
				DrawZone.getInstance().paint(g);
				g.dispose();
				cb.addTemplate(resultsPanelPdfTemplate, 0, 0);
				document.close();
			} catch (FileNotFoundException e) {
				WindowManaging.showErrorMessageWhenExportToPDF();
				e.printStackTrace();
			} catch (DocumentException e) {
				WindowManaging.showErrorMessageWhenExportToPDF();
				e.printStackTrace();
			}
		}
	}

	public static void exportPNG() {
		File f = WindowManaging.chooseFileForPDFExport();
		if (f != null) {
			int width = DrawZone.getInstance().getWidth();
			int height = DrawZone.getInstance().getHeight();
			BufferedImage buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
			Graphics2D g2 = buffer.createGraphics();
			DrawZone.getInstance().printAll(g2);
			try {
				ImageIO.write(buffer, "png", f);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	private static void saveText(String text, String path) {
		File f = new File(path);
		try {
			FileWriter fw = new FileWriter(f);
			fw.write(text);
			fw.close();
		} catch (IOException exception) {
			System.out.println("Erreur lors de la lecture : " + exception.getMessage());
		}
	}

	private static String generateCode(Object convert, String from, String to) {
		String result = "";
		try {
			Class tempClass = Class.forName(from);
			Class[] methodParameters = new Class[] {};
			java.lang.reflect.Method conversionMethod = tempClass.getMethod(to, methodParameters);
			Object[] params = new Object[] {};
			result = (String) conversionMethod.invoke(convert, params);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	public static void generateJavaCode() {
		Diagram diagram = file.getWorkingOnDiagram();
		String location = WindowManaging.chooseFolderForGeneratingCode();
		if (location != null) {
			for (Figure figure : diagram.getFigures()) {
				if (figure instanceof ClassDraw) {
					ClassDraw theClass = (ClassDraw) figure;
					ClassCode classCode = new ClassCode(theClass);
					saveText(classCode.Interpret("JavaCode", false), location + "\\" + theClass.getName() + ".java");
				}
			}
		}
	}

	public static void generateCppCode() {
		Diagram diagram = file.getWorkingOnDiagram();
		String location = WindowManaging.chooseFolderForGeneratingCode();
		if (location != null) {
			for (Figure figure : diagram.getFigures()) {
				if (figure instanceof ClassDraw) {
					ClassDraw theClass = (ClassDraw) figure;
					ClassCode classCode = new ClassCode(theClass);
					saveText(classCode.Interpret("CppCode", false), location + "\\" + theClass.getName() + ".cpp");
					saveText(classCode.Interpret("CppCode", true), location + "\\" + theClass.getName() + ".h");
				}
			}
		}
	}

	// other methods
	public static void reinitDrawZone() {
		setSelectedFigureIndex(-1);
	}

	public static Object[] listFromObject(Object object) {
		Object[] objetcList = { object };
		return objetcList;
	}

}