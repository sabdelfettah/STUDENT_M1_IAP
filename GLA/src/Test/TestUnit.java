package Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.Driver;

import javax.swing.JMenu;

import mvc.view.MainFrame;

import org.junit.Before;
import org.junit.Test;

public class TestUnit {
	
	@Before
	public void menuBarInit(){
		//JMenu file1 = new JMenu("File1");
		//JMenu file2 = new JMenu("File2");]
		
	}

	@Test
	public void mainFrameShouldBeSingleInstantiated() {
		MainFrame obj1 = MainFrame.getInstance();
		obj1.setTitle("Test obj 1");
		MainFrame obj2 = MainFrame.getInstance();
		obj2.setTitle("Test obj 2");
		assertTrue(obj1 == obj2);
		assertEquals("Test obj 2", obj1.getTitle());
		assertEquals("Test obj 2", obj2.getTitle());
	}
	
	
	
	@Test
	public void menuBarShouldBeSingleInstantiated() {
		JMenu file1 = new JMenu("File1");
		JMenu file2 = new JMenu("File2");
		
		mvc.view.MenuBar obj1 = mvc.view.MenuBar.getInstance();
		mvc.view.MenuBar obj2 = mvc.view.MenuBar.getInstance();
		obj1.add(file1);
		obj2.add(file2);
		assertTrue(obj1 == obj2);
		//assertEquals("File2", obj1);
		//assertEquals("File2", file2.getText());
	}
}
