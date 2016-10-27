package XMLParser;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import mvc.model.Action;
import mvc.model.Actor;
import mvc.model.ClassDraw;
import mvc.model.Comment;
import mvc.model.Declaration;
import mvc.model.Figure;
import mvc.model.Method;
import mvc.model.ModelController;
import mvc.model.UseCase;

import org.jdom2.DataConversionException;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import utils.WindowManaging;

public class ImportXML {

	private static void importADiagram(Element root, boolean isFreeDiagram, String path) {
		try {
			String diagramName = root.getAttributeValue("name");
			int diagramType = root.getAttribute("type").getIntValue();
			ModelController.createNewDiagram(diagramType, diagramName);
			ModelController.getFile().getWorkingOnDiagram().setPath(path);
			for (Element child : root.getChildren()) {
				ModelController.getFile().getWorkingOnDiagram().getFigures().add(importFigure(child));
			}
		} catch (DataConversionException e) {
			WindowManaging.showErrorMessageWhenImportFile();
			e.printStackTrace();
		}
	}

	private static void importAProject(Element root, String path) {
		ModelController.createNewProject(root.getAttributeValue("name"));
		ModelController.getFile().setPath(path);
		for (Element diagramElement : root.getChildren("gloDiagram")) {
			String filePath = diagramElement.getText();
			File f = new File(filePath);
			SAXBuilder builder = new SAXBuilder();
			try {
				Document document = (Document) builder.build(f);
				Element diagramRoot = document.getRootElement();
				importADiagram(diagramRoot, false, filePath);
			} catch (JDOMException e) {
				WindowManaging.showErrorMessageWhenImportFile();
				e.printStackTrace();
			} catch (IOException e) {
				WindowManaging.showErrorMessageWhenImportFile();
				e.printStackTrace();
			}
		}
	}

	public static void importUMLFile(boolean isProject) {
		ModelController.close();
		File f = WindowManaging.chooseFileForXMLImport(isProject);
		if (f != null) {
			System.out.println("f = " + f.toString());
			SAXBuilder builder = new SAXBuilder();
			try {
				Document document = (Document) builder.build(f);
				Element root = document.getRootElement();
				String name = root.getName();
				if (name.equals("gloProject") || name.equals("gloDiagram")) {
					if ((isProject && name.equals("gloDiagram")) || (!isProject && name.equals("gloProject"))) {
						WindowManaging.showWarningMessageWhenImportFile();
					}
					if (name.equals("gloProject")) {
						importAProject(root, f.getAbsolutePath());
					} else {
						importADiagram(root, true, f.getAbsolutePath());
					}
				} else {
					WindowManaging.showErrorMessageWhenImportFile();
				}
			} catch (JDOMException e) {
				e.printStackTrace();
				WindowManaging.showErrorMessageWhenImportFile();
			} catch (IOException e) {
				WindowManaging.showErrorMessageWhenImportFile();
				e.printStackTrace();
			}
		}
	}

	private static Figure importFigure(Element figureElement) {
		if (figureElement.getName().equals("package")) {
			return importPackage(figureElement);
		} else if (figureElement.getName().equals("comment")) {
			return importComment(figureElement);
		} else if (figureElement.getName().equals("class")) {
			return importClass(figureElement);
		} else if (figureElement.getName().equals("actor")) {
			return importActor(figureElement);
		} else if (figureElement.getName().equals("action")) {
			return importAction(figureElement);
		} else if (figureElement.getName().equals("useCase")) {
			return importUseCase(figureElement);
		} else {
			return null;
		}
	}

	private static mvc.model.Package importPackage(Element packageElement) {
		ArrayList<Figure> figureList = new ArrayList<Figure>();
		for (Element child : packageElement.getChildren()) {
			if (child.getName().equals("box")) {
				//
			} else {
				figureList.add(importFigure(child));
			}
		}
		return new mvc.model.Package(figureList);
	}

	private static Comment importComment(Element commentElement) {
		Comment comment = null;
		int classID;
		try {
			classID = commentElement.getAttribute("id").getIntValue();
			int x = commentElement.getAttribute("x").getIntValue();
			int y = commentElement.getAttribute("y").getIntValue();
			int width = commentElement.getAttribute("width").getIntValue();
			int height = commentElement.getAttribute("height").getIntValue();
			String text = commentElement.getAttributeValue("text");
			comment = new Comment(classID, x, y, width, height, text);
		} catch (DataConversionException e) {
			WindowManaging.showErrorMessageWhenImportFile();
			e.printStackTrace();
		}
		return comment;
	}

	private static ClassDraw importClass(Element classElement) {
		String name = classElement.getAttributeValue("name");
		ClassDraw theClass = null;
		try {
			short stereotype = ClassDraw.stereotypeShort(classElement.getAttributeValue("stereotype"));
			short visibility = Declaration.visibilityShort(classElement.getAttributeValue("visibility"));
			Element box = classElement.getChild("box");
			int x = box.getAttribute("x").getIntValue();
			int y = box.getAttribute("y").getIntValue();
			int width = box.getAttribute("width").getIntValue();
			int height = box.getAttribute("height").getIntValue();
			theClass = new ClassDraw(name, Figure.CLASS, stereotype, x, y, width, height);
			theClass.setVisibility(visibility);
			for (Element attributeElement : classElement.getChildren("attribute")) {
				String nameAttr = attributeElement.getAttributeValue("name");
				int type = Declaration.typeIntWithoutVoid(attributeElement.getAttributeValue("type"));
				short visibilityAttr = Declaration.visibilityShort(attributeElement.getAttributeValue("visibility"));
				boolean _static = Declaration.StringToIs(attributeElement.getAttributeValue("static"));
				boolean _final = Declaration.StringToIs(attributeElement.getAttributeValue("const"));
				theClass.addAttribute(nameAttr, type, visibilityAttr, _static, _final);
			}
			for (Element methodElement : classElement.getChildren("method")) {
				String nameMeth = methodElement.getAttributeValue("name");
				int type = Declaration.typeIntWithoutVoid(methodElement.getAttributeValue("type"));
				short visibilityMeth = Declaration.visibilityShort(methodElement.getAttributeValue("visibility"));
				boolean _static = Declaration.StringToIs(methodElement.getAttributeValue("static"));
				boolean _abstract = Declaration.StringToIs(methodElement.getAttributeValue("abstract"));
				Method method = new Method(nameMeth, type, visibilityMeth, _static, _abstract);
				for (Element parameterElement : methodElement.getChildren("param")) {
					String nameParam = parameterElement.getAttributeValue("name");
					int typeParam = Declaration.typeIntWithoutVoid(parameterElement.getAttributeValue("type"));
					method.addParameter(nameParam, typeParam);
				}
				theClass.addMethod(method);
			}
		} catch (DataConversionException e) {
			WindowManaging.showErrorMessageWhenImportFile();
			e.printStackTrace();
		}
		System.out.print("theClass = " + theClass);
		return theClass;
	}

	private static Actor importActor(Element actorElement) {
		Actor actor = null;
		String name = actorElement.getAttributeValue("name");
		Element box = actorElement.getChild("box");
		try {
			int id = actorElement.getAttribute("id").getIntValue();
			int x = box.getAttribute("x").getIntValue();
			int y = box.getAttribute("y").getIntValue();
			int width = box.getAttribute("width").getIntValue();
			int height = box.getAttribute("height").getIntValue();
			actor = new Actor(name, Figure.ACTOR, x, y, width, height);
			actor.setID(id);
		} catch (DataConversionException e) {
			e.printStackTrace();
		}
		return actor;
	}

	private static Action importAction(Element actionElement) {
		Action action = null;
		String name = actionElement.getAttributeValue("name");
		Element box = actionElement.getChild("box");
		try {
			int id = actionElement.getAttribute("id").getIntValue();
			int x = box.getAttribute("x").getIntValue();
			int y = box.getAttribute("y").getIntValue();
			int width = box.getAttribute("width").getIntValue();
			int height = box.getAttribute("height").getIntValue();
			action = new Action(name, Figure.ACTOR, x, y, width, height);
			action.setID(id);
		} catch (DataConversionException e) {
			e.printStackTrace();
		}
		return action;
	}
	
	private static UseCase importUseCase(Element useCaseElement) {
		UseCase useCase = null;
		String name = useCaseElement.getAttributeValue("name");
		Element box = useCaseElement.getChild("box");
		try {
			int id = useCaseElement.getAttribute("id").getIntValue();
			int x = box.getAttribute("x").getIntValue();
			int y = box.getAttribute("y").getIntValue();
			int width = box.getAttribute("width").getIntValue();
			int height = box.getAttribute("height").getIntValue();
			useCase = new UseCase(name, Figure.ACTOR, x, y, width, height);
			useCase.setID(id);
		} catch (DataConversionException e) {
			e.printStackTrace();
		}
		return useCase;
	}

}
