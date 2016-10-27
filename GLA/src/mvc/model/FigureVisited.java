package mvc.model;

import visit.FigureVisitor;

public interface FigureVisited {
	
	public void accept(FigureVisitor figureVisitor);

}
