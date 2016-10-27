package mvc.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyController implements KeyListener{
	
	private static KeyController instance = null;
	
	public static KeyController getInstance(){
		if(instance == null)
			instance = new KeyController();
		return instance;
	}
	
	private KeyController(){
		
	}
	
	@Override
	public void keyPressed(KeyEvent arg0) {
//		System.out.println("----------------------------");
//		System.out.println("Pressed _ "+arg0.getKeyChar());
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
//		System.out.println("Released _ "+arg0.getKeyChar());
//		System.out.println("----------------------------");
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
//		System.out.println("Typed _ "+arg0.getKeyChar());
	}

}
