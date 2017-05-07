package programs;

import java.io.FileOutputStream;

import javax.swing.JOptionPane;

import org.jdom2.Document;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import structs.Line;
import structs.Lines;

public class Methods {
	
	private static String inputText;
	private static String outputText;
	private static String link;
	private static int length;
	
	public static void setLink(String theLink){
		link = theLink;
	}
	
	public static void setInputText(String theText){
		inputText = theText;
		length = inputText.length();
	}
	
	public static String getOutputText(){
		return outputText;
	}
	
	private static boolean isThereAnyIdentifier(int index){
		boolean result = false;
		boolean stop = false;
		int i = index;
		if(i < length && inputText.charAt(i)=='@'){
			i++;
			while(i<length && !stop){
				if(inputText.charAt(i)!=10 && inputText.charAt(i)!= ' ' && inputText.charAt(i)!='@'){
					i++;
				}else{
					// vérifier alphabétique ?!
					stop = true;
					System.out.println(inputText.charAt(i)+" _ "+ (int) inputText.charAt(i+1));
					if(inputText.charAt(i)=='@' && (i+1==length || inputText.charAt(i+1)==' ' || inputText.charAt(i+1)==10 || inputText.charAt(i+1)==13)){
						result = true;
					}
					System.out.println(result);
				}
			}
		}
		return result;
	}
	
	public static boolean gedToXML(){
		if(inputText==null || inputText== ""){
			JOptionPane.showMessageDialog(null, "Aucun fichier sélectionné !", "Erreur lors de la conversion", 0);
			return false;
		}
		Lines lines = new Lines();
		Line line = new Line();
		int index=0;
		int position = 0;
		// position = 0 -> level
		// position = 1 -> identifier
		// position = 2 -> tag
		// position = 3 -> value
		String token = "";
		//System.out.println((int) '\t' + " _ "+ (int) '\r');
		while(index<length){
			if(inputText.charAt(index)==10 || inputText.charAt(index)==13){
				//if(line.getLevel()!=-1){
					line.setTokenAt(token, position);
					lines.add(line);
					line = new Line();
					position = 0;
					token = "";
				//}
				while((index < length) && (inputText.charAt(index)==10 || inputText.charAt(index)==' ' || inputText.charAt(index)==13)){
					index++;
				}
			}else if(inputText.charAt(index)==' ' && position!=3){
				line.setTokenAt(token, position);
				if(position==0){
					if(isThereAnyIdentifier(index+1)){
						line.setIdLocation(position+1);
					}else{
						position ++;
					}	
				}else {
					if(line.getIdLocation()==-1 && position==2 && isThereAnyIdentifier(index+1)){
						line.setIdLocation(position+1);
					}
				}
				position ++;
				token = "";
				index++;
			}else{
				token = token + inputText.charAt(index);
				index++;
			}
		}
		if(line.getLevel()>-1){
			line.setTokenAt(token, position);
			lines.add(line);
		}
		Document doc = lines.linesToDoc();
		try {
			XMLOutputter XML = new XMLOutputter(Format.getPrettyFormat());
			outputText = XML.outputElementContentString(doc.getRootElement());
			//XML.output(doc, System.out);
			XML.output(doc, new FileOutputStream(link));
		} catch(java.io.IOException e){
		}
		return true;
	}

}
