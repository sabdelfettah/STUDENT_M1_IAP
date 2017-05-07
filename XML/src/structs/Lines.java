package structs;

import java.util.ArrayList;

import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;

public class Lines extends ArrayList<Line>{
	private int index = 0;
	private Element root;
	private Document doc;

	/**
	 * 
	 */
	private static final long serialVersionUID = 927538875966010755L;
	
	private void processLine(Element myRoot){
		int myLevel = get(index).getLevel();
		//System.out.println(get(index).getTag());
		//System.out.println(get(index).getValue()+" _ "+get(index).getIdLocation());
		String s="";
		Element newElement = new Element(get(index).getTag());
		if(get(index).getLevel()==0){
			s=get(index).getIdentifier();
			if(s!=null && s!=""){
				newElement.setAttribute(new Attribute("identifier", s));
			}
		}else{
			s=get(index).getValue();
			if(s!=null && s!=""){
				if(get(index).getIdLocation()==3){
					newElement.setAttribute(new Attribute("idref", s));
				}else{
					newElement.setText(s);
				}
				
			}
		}
		myRoot.addContent(newElement);
		index++;
		while(index < size() && get(index).getLevel() > myLevel){
			processLine(newElement);
		}
	}
	
	public Document linesToDoc(){
		index = 0;
		root = new Element("gedcom");
		doc = new Document(root);
		while(index < size()){
			processLine(root);
		}
		return doc;
	}
	
}
