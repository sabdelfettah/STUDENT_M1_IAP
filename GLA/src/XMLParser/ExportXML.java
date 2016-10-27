package XMLParser;

import java.io.File;
import java.io.FileOutputStream;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import mvc.model.Action;
import mvc.model.Actor;
import mvc.model.Box;
import mvc.model.ClassDraw;
import mvc.model.Comment;
import mvc.model.Declaration;
import mvc.model.Diagram;
import mvc.model.Figure;
import mvc.model.Method;
import mvc.model.Package;
import mvc.model.Parameter;
import mvc.model.Project;
import mvc.model.Relation;
import mvc.model.UseCase;

public class ExportXML {

	private static void save(Document document, File file) {
		try {
			// On utilise ici un affichage classique avec getPrettyFormat()
			XMLOutputter sortie = new XMLOutputter(Format.getPrettyFormat());
			sortie.output(document, new FileOutputStream(file));
		} catch (java.io.IOException e) {
			e.printStackTrace();
		}
	}

	public static void ExportDiagram(Diagram diagram, File file) {
		Element diagramElement = new Element("gloDiagram");
		//
		Attribute name = new Attribute("name", diagram.getName());
		Attribute type = new Attribute("type", diagram.getType() + "");
		//
		diagramElement.setAttribute(name);
		diagramElement.setAttribute(type);
		Document document = new Document(diagramElement);
		for (Figure figure : diagram.getFigures()) {
			exportFigure(figure, diagramElement);
		}
		save(document, file);
	}

	public static String ExportDiagram(Diagram diagram, String path) {
		File f = new File(path + "\\" + diagram.getName() + ".gloD");
		ExportDiagram(diagram, f);
		return f.getAbsolutePath();
	}

	public static void ExportProject(Project project, File f, boolean withDiagrams) {
		Element root = new Element("gloProject");
		//
		Attribute name = new Attribute("name", project.getName());
		//
		root.setAttribute(name);
		//
		Document document = new Document(root);
		if (withDiagrams) {
			for (Diagram diagram : project.getDiagrams()) {
				String currentFileName = ExportDiagram(diagram, f.getParent());
				Element diagramElement = new Element("gloDiagram");
				diagramElement.addContent(currentFileName);
				root.addContent(diagramElement);
			}
		}
		save(document, f);
	}

	private static void exportFigure(Figure figure, Element root) {
		if (figure instanceof Package) {
			exportPackage((Package) figure, root);
		} else if (figure instanceof Comment) {
			exportComment((Comment) figure, root);
		} else if (figure instanceof mvc.model.Element) {
			exportElement((mvc.model.Element) figure, root);
		} else if (figure instanceof Relation) {

		}
	}

	private static void exportPackage(Package _package, Element root) {
		Element packageElement = new Element("package");
		//
		Attribute name = new Attribute("name", "mypackage");
		Attribute id = new Attribute("id", "0");
		//
		packageElement.setAttribute(name);
		packageElement.setAttribute(id);
		//
		exportBox(0, 0, 0, 0, packageElement);
		//
		for (Figure figure : _package.getChildFigures()) {
			exportFigure(figure, packageElement);
		}
		root.addContent(packageElement);
	}

	private static void exportComment(Comment comment, Element root) {
		Element commentElement = new Element("comment");
		//
		Attribute id = new Attribute("id", comment.getFigureID() + "");
		//
		commentElement.setAttribute(id);
		//
		exportBox(comment.getBox(), commentElement);
		//
		Element text = new Element("text");
		text.addContent(comment.getText());
		//
		commentElement.addContent(text);
		//
		root.addContent(commentElement);
	}

	private static void exportElement(mvc.model.Element element, Element root) {
		switch (element.getType()) {
		case Figure.CLASS: {
			exportClass((ClassDraw) element, root);
		}
			;
			break;
		case Figure.ACTION: {
			exportAction((Action) element, root);
		}
			;
			break;
		case Figure.ACTOR: {
			exportActor((Actor) element, root);
		}
			;
			break;
		case Figure.USECASE: {
			exportUseCase((UseCase) element, root);
		}
			;
			break;
		}
	}

	private static void exportClass(ClassDraw _class, Element root) {
		Element classElement = new Element("class");
		//
		Attribute name = new Attribute("name", _class.getName());
		Attribute id = new Attribute("id", _class.getID() + "");
		Attribute visibility = new Attribute("visibility", Declaration.visibilityString(_class.getVisibility()));
		Attribute stereotype = new Attribute("stereotype", ClassDraw.stereotypeString(_class.getStereotype()));
		//
		classElement.setAttribute(name);
		classElement.setAttribute(id);
		classElement.setAttribute(stereotype);
		classElement.setAttribute(visibility);
		//
		exportBox(_class.getBox(), classElement);
		//
		for (mvc.model.Attribute attribute : _class.getAttributes()) {
			Element attributeElement = new Element("attribute");
			//
			Attribute attributeName = new Attribute("name", attribute.getName());
			Attribute type = new Attribute("type", Declaration.typeNameWithoutVoid(attribute.getType()));
			Attribute visibilityAttr =
					new Attribute("visibility", Declaration.visibilityString((short) attribute.getVisibility()));
			Attribute _static = new Attribute("static", Declaration.isToString(attribute.isStatic()));
			Attribute _final = new Attribute("const", Declaration.isToString(attribute.is_final()));
			//
			attributeElement.setAttribute(attributeName);
			attributeElement.setAttribute(type);
			attributeElement.setAttribute(visibilityAttr);
			attributeElement.setAttribute(_static);
			attributeElement.setAttribute(_final);
			//
			classElement.addContent(attributeElement);
		}
		for (Method method : _class.getMethods()) {
			Element methodElement = new Element("method");
			//
			Attribute methodName = new Attribute("name", method.getName());
			Attribute type = new Attribute("type", Declaration.typeNameWithVoid(method.getType()));
			Attribute visibilityMeth =
					new Attribute("visibility", Declaration.visibilityString((short) method.getVisibility()));
			Attribute _static = new Attribute("static", Declaration.isToString(method.isStatic()));
			Attribute _abstract = new Attribute("abstract", Declaration.isToString(method.is_abstract()));
			Attribute _const = new Attribute("abstract", "false");
			//
			methodElement.setAttribute(methodName);
			methodElement.setAttribute(type);
			methodElement.setAttribute(visibilityMeth);
			methodElement.setAttribute(_static);
			methodElement.setAttribute(_abstract);
			methodElement.setAttribute(_const);
			//
			for (Parameter parameter : method.getArguments()) {
				Element parameterElement = new Element("param");
				//
				Attribute parameterName = new Attribute("name", parameter.getName());
				Attribute parameterType = new Attribute("type", Declaration.typeNameWithVoid(parameter.getType()));
				//
				parameterElement.setAttribute(parameterName);
				parameterElement.setAttribute(parameterType);
				//
				methodElement.addContent(parameterElement);
			}
			//
			classElement.addContent(methodElement);
		}
		//
		root.addContent(classElement);
	}

	private static void exportActor(Actor actor, Element root) {
		Element actorElement = new Element("actor");
		//
		Attribute name = new Attribute("name", actor.getName());
		Attribute id = new Attribute("id", actor.getID() + "");
		//
		actorElement.setAttribute(name);
		actorElement.setAttribute(id);
		//
		exportBox(actor.getBox(), actorElement);
		//
		root.addContent(actorElement);
	}

	private static void exportUseCase(UseCase useCase, Element root) {
		Element useCaseElement = new Element("useCase");
		//
		Attribute name = new Attribute("name", useCase.getName());
		Attribute id = new Attribute("id", useCase.getID() + "");
		//
		useCaseElement.setAttribute(name);
		useCaseElement.setAttribute(id);
		//
		exportBox(useCase.getBox(), useCaseElement);
		//
		root.addContent(useCaseElement);
	}

	private static void exportAction(Action action, Element root) {
		Element actionElement = new Element("action");
		//
		Attribute name = new Attribute("name", action.getName());
		Attribute id = new Attribute("id", action.getID() + "");
		//
		actionElement.setAttribute(name);
		actionElement.setAttribute(id);
		//
		exportBox(action.getBox(), actionElement);
		//
		root.addContent(actionElement);
	}

	private static void exportBox(Box box, Element root) {
		exportBox(box.getX(), box.getY(), box.getWIDTH(), box.getHEIGHT(), root);
	}

	private static void exportBox(int x, int y, int width, int height, Element root) {
		Element box = new Element("box");
		//
		Attribute xAttr = new Attribute("x", x + "");
		Attribute yAttr = new Attribute("y", y + "");
		Attribute widthAttr = new Attribute("width", width + "");
		Attribute heightAttr = new Attribute("height", height + "");
		//
		box.setAttribute(xAttr);
		box.setAttribute(yAttr);
		box.setAttribute(widthAttr);
		box.setAttribute(heightAttr);
		//
		root.addContent(box);
	}
}
