package visit;

import mvc.model.Action;
import mvc.model.Actor;
import mvc.model.Agregation;
import mvc.model.Association;
import mvc.model.ClassDraw;
import mvc.model.Comment;
import mvc.model.Composition;
import mvc.model.Dependency;
import mvc.model.Heritage;
import mvc.model.InterfaceRealisation;
import mvc.model.Package;
import mvc.model.UseCase;

public interface FigureVisitor {
	
	// Elements
	public void visitClass(ClassDraw figureVisited);
	public void visitAction(Action figureVisited);
	public void visitActor(Actor figureVisited);
	public void visitUseCase(UseCase figureVisited);
	
	// Relations
	public void visitAgregation(Agregation figureVisited);
	public void visitComposition(Composition figureVisited);
	public void visitAssociation(Association figureVisited);
	public void visitDependency(Dependency figureVisited);
	public void visitHeritage(Heritage figureVisited);
	public void visitInterfaceRealisation(InterfaceRealisation figureVisited);
	
	// Comment
	public void visitComment(Comment figureVisited);
	
	// Package
	public void visitPackage(Package figureVisited);

}
