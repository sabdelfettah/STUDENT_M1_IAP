package programs;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Actions {
	
	private static void Close(){
		System.exit(0);
	}
	
	public static void PerformeAction(int ActionNumber){
		switch(ActionNumber){
		// Exit
		case 0: Close(); break;
		// Load a Gedcom file
		case 1: {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Charger un fichier Gedcom");
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Gedcom files", "ged");
			fileChooser.setFileFilter(fileFilter);
			int response = fileChooser.showOpenDialog(null);
			if(response == JFileChooser.APPROVE_OPTION){
				File file = fileChooser.getSelectedFile();
				Methods.setLink(file.getAbsolutePath().substring(0, file.getAbsolutePath().length()-3)+"xml");
				String content = null;
				try {
					FileReader reader = new FileReader(file);
				    char[] chars = new char[(int) file.length()];
				    reader.read(chars);
				    content = new String(chars);
				    reader.close();
				} catch (IOException e) {
				    e.printStackTrace();
				}
				if(content != null){
					Methods.setInputText(content);
					MainProgram.mf.getInputContainer().setText(content);
					MainProgram.mf.getOutputContainer().setText("");
					MainProgram.mf.setTitle(file.getName()+" - "+MainProgram.title);
				}
			}
		} break;
		// Convertion
		case 2: {
			if(Methods.gedToXML()){
				MainProgram.mf.getOutputContainer().setText(Methods.getOutputText());
			}
		}break;
		// A propos
		case 3: {
			JOptionPane.showMessageDialog(null, "Réalisé par :\n- ABDELFETTAH Salim\n- GACI Hassane", "A propos de l'application", 1);
		}break;
		}
	}
	
}
