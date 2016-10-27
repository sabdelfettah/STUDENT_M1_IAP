package mvc.view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import mvc.model.ModelController;


public class MainFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3766732151463498726L;
	public static final String TITLE = "DiversityUML v1.2";
	private static MainFrame instance = null;
	
	public static MainFrame getInstance(){
		if(instance == null)
			instance = new MainFrame();
		return instance;
	}
	
	private MainFrame(){
		super();
		Explorer.getInstance().setVisible(false);
		//
		Toolbox.getInstance().setVisible(false);
		//
		Modifier.getInstance().setVisible(false);
		//
		DrawZone.getInstance().setFocusable(true);
		DrawZone.getInstance().requestFocus(true);
		DrawZone.getInstance().requestFocusInWindow();
		//
		setTitle(TITLE);
		double width = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		double height = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		setSize((int) width*99/100, (int) height*95/100);
		setLocationRelativeTo(null);
		setJMenuBar(MenuBar.getInstance());
		setLayout(new BorderLayout());
		//
		add(DrawZone.getInstance(), BorderLayout.CENTER);
		//
		JPanel westPanel = new JPanel(new BorderLayout());
		Dimension westpanelSize = new Dimension((int) (width/10), (int) height);
		westPanel.setPreferredSize(westpanelSize);
		westPanel.setMinimumSize(westpanelSize);
		westPanel.setMaximumSize(westpanelSize);
		westPanel.setSize(westpanelSize);
		westPanel.add(Toolbox.getInstance(), BorderLayout.CENTER);
		add(westPanel, BorderLayout.WEST);
		//
		JPanel eastPanel = new JPanel(new BorderLayout());
		Dimension eastpanelSize = new Dimension((int) (width/6), (int) height);
		eastPanel.setPreferredSize(eastpanelSize);
		eastPanel.setMinimumSize(eastpanelSize);
		eastPanel.setMaximumSize(eastpanelSize);
		eastPanel.setSize(eastpanelSize);
		eastPanel.add(Explorer.getInstance(), BorderLayout.CENTER);
		eastPanel.add(Modifier.getInstance(), BorderLayout.SOUTH);
		add(eastPanel, BorderLayout.EAST);
		//
	}
	
	public void updateTitle(){
		String newTitle = TITLE;
		if(ModelController.getFile() != null){
			newTitle += " - ";
			if(ModelController.getFile().getWorkingOnDiagram() != null){
				newTitle += ModelController.getFile().getWorkingOnDiagram().toExtendedString();
			}else{
				newTitle += ModelController.getFile().toExtendedString();
			}
			if(ModelController.isChanged()){
				newTitle += " *";
			}
		}
		instance.setTitle(newTitle);
	}
	
	public static void addButtons(JPanel panel, JButton[] buttons) {
		for (int i = 0; i < buttons.length; i++) {
			panel.add(buttons[i]);
		}
	}
	
	public static void addLabels(JPanel panel, JLabel[] labels) {
		for (int i = 0; i < labels.length; i++) {
			panel.add(labels[i]);
		}
	}
	
	public static void addComponents(JPanel panel, Component[] components) {
		for (int i = 0; i < components.length; i++) {
			panel.add(components[i]);
		}
	}

}
