package guis;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.KeyStroke;

import programs.Actions;
import programs.MainProgram;

public class MainFrame extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9052665033365740724L;
	private static MainMenu mm = new MainMenu();
	private static MainPanel mp= new MainPanel();
	
	public TextArea getInputContainer(){
		return mp.getLTA();
	}
	
	public TextArea getOutputContainer(){
		return mp.getRTA();
	}
	
	public MainFrame(){
		setTitle(MainProgram.title);
		double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().getHeight();
		double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().getWidth();
		setSize((int) screenWidth, (int) (screenHeight*0.95));
		setMinimumSize(new Dimension(100, 200));
		setPreferredSize(getSize());
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setJMenuBar(mm);
		add(mp);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		int action_s_number = -1;
		Actions.PerformeAction(action_s_number);
	}

}



// <---- MenuBar Class ---->


class MainMenu extends JMenuBar implements ActionListener{
	private static JMenu Files = new JMenu("Fichier");
	private static JMenu Action = new JMenu("Actions");
	private static JMenu Help = new JMenu("Aide");
	private static JMenuItem Open = new JMenuItem("Ouvrir un fichier Gedcom");
	private static JMenuItem Quit = new JMenuItem("Quitter");
	private static JMenuItem Convert = new JMenuItem("Convertir en un fichier XML");
	private static JMenuItem About = new JMenuItem("A propos");
	private static JSeparator sep1 = new JSeparator(); 
	/**
	 * 
	 */
	private static final long serialVersionUID = -53541617674679684L;
	
	public MainMenu(){
		Open.addActionListener(this);
		Open.setAccelerator(KeyStroke.getKeyStroke('O', 2));
		Quit.addActionListener(this);
		Quit.setAccelerator(KeyStroke.getKeyStroke('Q', 2));
		Convert.addActionListener(this);
		Convert.setAccelerator(KeyStroke.getKeyStroke('C', 2));
		About.addActionListener(this);
		About.setAccelerator(KeyStroke.getKeyStroke('A', 2));
		Files.add(Open);
		Files.add(sep1);
		Files.add(Quit);
		Action.add(Convert);
		Help.add(About);
		add(Files);
		add(Action);
		add(Help);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		int action_s_number = -1;
		Object Source = arg0.getSource();
		if(Source.equals(Quit)){
			action_s_number = 0;
		}else if (Source.equals(Open)){
			action_s_number = 1;
		}else if (Source.equals(Convert)){
			action_s_number = 2;
		}else if (Source.equals(About)){
			action_s_number = 3;
		}
		Actions.PerformeAction(action_s_number);
	}

}



//<---- Panel Class ---->


class MainPanel extends JPanel implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1961934411663558671L;
	private static TextArea lta = new TextArea("");
	private static TextArea rta = new TextArea("");
	private static JPanel cp=new JPanel(new GridLayout(1, 2));
	private static JPanel lp=new JPanel(new BorderLayout());
	private static JPanel rp=new JPanel(new BorderLayout());
	private static JPanel up=new JPanel(new FlowLayout());
	private static JButton Open = new JButton("Ouvrir un fichier Gedcom");
	private static JButton Convert = new JButton("Convertir en un fichier XML");
	private static JButton Quit = new JButton("Quitter");
	
	public TextArea getLTA(){
		return lta;
	}
	
	public TextArea getRTA(){
		return rta;
	}
	
	public MainPanel(){
		lta.setFocusable(false);
		rta.setFocusable(false);
		lp.add("Center", lta);
		rp.add("Center", rta);
		cp.add(lp);
		cp.add(rp);
		Open.addActionListener(this);
		Convert.addActionListener(this);
		Quit.addActionListener(this);
		up.add(Open);
		up.add(Convert);
		up.add(Quit);
		((FlowLayout) up.getLayout()).setAlignment(FlowLayout.LEFT);
		setLayout(new BorderLayout());
		add("Center", cp);
		add("North", up);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		int action_s_number = -1;
		Object Source = arg0.getSource();
		if(Source.equals(Quit)){
			action_s_number = 0;
		}else if (Source.equals(Open)){
			action_s_number = 1;
		}else if (Source.equals(Convert)){
			action_s_number = 2;
		}
		Actions.PerformeAction(action_s_number);
	}
}

