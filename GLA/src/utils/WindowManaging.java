package utils;

import java.awt.Desktop;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import lang.Langage;
import mvc.model.ModelController;
import mvc.view.MainFrame;

public class WindowManaging {
	
	public static File htmlFile = new File("doc/index.html");

	public static int answerToClose() {
		return JOptionPane.showConfirmDialog(null, Langage.getString("Messages.answerToClose"),
				Langage.getString("Messages.quit") + MainFrame.TITLE, 0);
	}

	public static int answerToSaveBeforeClose() {
		return JOptionPane.showConfirmDialog(null, Langage.getString("Messages.answerToSaveBeforeQuit"),
				Langage.getString("Messages.SaveBeforeQuit") + MainFrame.TITLE, 1, 3);
	}

	public static int answerToSaveBeforeCloseProject(String projectName) {
		return JOptionPane.showConfirmDialog(null, Langage.getString("Messages.answerToSaveBeforeCloseProject")+" "+projectName+" ?",
				Langage.getString("Messages.SaveBeforeQuit") + MainFrame.TITLE, 1, 3);
	}
	
	public static int answerToSaveBeforeCloseDiagram(String diagramName) {
		return JOptionPane.showConfirmDialog(null, Langage.getString("Messages.answerToSaveBeforeCloseDiagram")+" "+diagramName+" ?",
				Langage.getString("Messages.SaveBeforeQuit") + MainFrame.TITLE, 1, 3);
	}

	public static void showWarningMessageOnAttributeName() {
		JOptionPane.showMessageDialog(null, "", "", JOptionPane.WARNING_MESSAGE);
	}

	public static void showWarningMessageOnMathodOrParametersName() {
		JOptionPane.showMessageDialog(null, "", "", JOptionPane.WARNING_MESSAGE);
	}

	public static void showErrorMessageWhenImportFile() {
		JOptionPane.showMessageDialog(null, "", "", JOptionPane.ERROR_MESSAGE);
	}

	public static void showWarningMessageWhenImportFile() {
		JOptionPane.showMessageDialog(null, "", "", JOptionPane.WARNING_MESSAGE);
	}

	public static void showErrorMessageWhenExportFile() {
		JOptionPane.showMessageDialog(null, "", "", JOptionPane.ERROR_MESSAGE);
	}

	public static void showErrorMessageWhenExportToPDF() {
		JOptionPane.showMessageDialog(null, "", "", JOptionPane.ERROR_MESSAGE);
	}

	public static String chooseFolderForGeneratingCode() {
		String path = null;
		JFileChooser codeDirectoryChooser = new JFileChooser("codeGenerating");
		codeDirectoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (codeDirectoryChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
			path = codeDirectoryChooser.getSelectedFile().getAbsolutePath();
		return path;
	}

	public static File chooseFileForXMLExport(boolean isProject) {
		File file = null;
		JFileChooser exportXMLFileChooser = new JFileChooser();
		if (isProject)
			exportXMLFileChooser.setDialogTitle("project");
		else
			exportXMLFileChooser.setDialogTitle("diagram");
		if (exportXMLFileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
			file = exportXMLFileChooser.getSelectedFile();
		return file;
	}

	public static File chooseFileForPDFExport() {
		File file = null;
		JFileChooser exportXMLFileChooser = new JFileChooser();
		if (exportXMLFileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION)
			file = exportXMLFileChooser.getSelectedFile();
		return file;
	}

	public static File chooseFileForXMLImport(boolean isProject) {
		File file = null;
		JFileChooser importXMLFileChooser = new JFileChooser();
		importXMLFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		if (isProject)
			importXMLFileChooser.setDialogTitle("test");
		else
			importXMLFileChooser.setDialogTitle("test2");
		if (importXMLFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
			file = importXMLFileChooser.getSelectedFile();
		return file;
	}

	public static void showAbout(){
		String msg = MainFrame.TITLE+"\nBy :";
		msg += "\n- Salim ABDELFETTAH"; 
		msg += "\n- Abdelazziz BAHRI";
		msg += "\n- Hafiz Budi FIRMANSYAH";
		msg += "\n- Alfonsina GIL BRETON";
		msg += "\n- Mohammed GHRISSI";
		msg += "\n- Van Luan NGUYEN";
		JOptionPane.showMessageDialog(null, msg, Langage.getString("MenuBar.About"), JOptionPane.INFORMATION_MESSAGE);
	}
	
	public static void lunchHelp(){
		try {
			Desktop.getDesktop().open(htmlFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void onClose() {
		if (ModelController.filesChanged().size() > 0) {
			int answer = answerToSaveBeforeClose();
			if (answer == 0) {
				System.exit(0);
			} else if (answer == 1) {
				ModelController.saveCurrentWork(); 
			}
		} else {
			if (answerToClose() == 0) {
				System.exit(0);
			}
		}
	}

	public static void setWindowListener() {
		MainFrame.getInstance().setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		MainFrame.getInstance().addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent arg0) {

			}

			@Override
			public void windowIconified(WindowEvent arg0) {

			}

			@Override
			public void windowDeiconified(WindowEvent arg0) {

			}

			@Override
			public void windowDeactivated(WindowEvent arg0) {

			}

			@Override
			public void windowClosing(WindowEvent arg0) {
				onClose();
			}

			@Override
			public void windowClosed(WindowEvent arg0) {

			}

			@Override
			public void windowActivated(WindowEvent arg0) {

			}
		});
	}

}
