package visit;

import mvc.model.Action;
import mvc.model.Actor;
import mvc.model.Agregation;
import mvc.model.Association;
import mvc.model.ClassDraw;
import mvc.model.Comment;
import mvc.model.Composition;
import mvc.model.Dependency;
import mvc.model.Figure;
import mvc.model.Heritage;
import mvc.model.InterfaceRealisation;
import mvc.model.Package;
import mvc.model.UseCase;
import mvc.view.Explorer;

public class ExplorerFigureVisitor implements FigureVisitor{

	@Override
	public void visitClass(ClassDraw figureVisited) {
		Explorer.setCurrentIcons(Explorer.CLASS_ICONS);
		Explorer.getInstance().addFigure(figureVisited);
	}

	@Override
	public void visitAction(Action figureVisited) {
		Explorer.setCurrentIcons(Explorer.ACTION_ICONS);
		Explorer.getInstance().addFigure(figureVisited);
	}

	@Override
	public void visitActor(Actor figureVisited) {
		Explorer.setCurrentIcons(Explorer.ACTOR_ICONS);
		Explorer.getInstance().addFigure(figureVisited);
	}

	@Override
	public void visitPackage(Package figureVisited) {
		for(Figure currentFigure : figureVisited.getChildFigures()){
			currentFigure.accept(this);
			//System.out.println("Explor child "+currentFigure);
		}
	}

	@Override
	public void visitUseCase(UseCase figureVisited) {
		Explorer.setCurrentIcons(Explorer.ACTOR_ICONS); // to update
		Explorer.getInstance().addFigure(figureVisited);
	}

	@Override
	public void visitAgregation(Agregation figureVisited) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitComposition(Composition figureVisited) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitAssociation(Association figureVisited) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitDependency(Dependency figureVisited) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitHeritage(Heritage figureVisited) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitInterfaceRealisation(InterfaceRealisation figureVisited) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void visitComment(Comment figureVisited) {
		// TODO Auto-generated method stub
		
	}

}
