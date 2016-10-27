package mvc.model;

public class Box {
	
	private int X;
	private int Y;
	private int WIDTH;
	private int HEIGHT;
	
	public Box(int x, int y, int width, int height){
		X = x;
		Y = y;
		WIDTH = width;
		HEIGHT = height;
	}
	
	public int getX() {
		return X;
	}
	public void setX(int x) {
		X = x;
	}
	public int getY() {
		return Y;
	}
	public void setY(int y) {
		Y = y;
	}
	public int getWIDTH() {
		return WIDTH;
	}
	public void setWIDTH(int wIDTH) {
		WIDTH = wIDTH;
	}
	public int getHEIGHT() {
		return HEIGHT;
	}
	public void setHEIGHT(int hEIGHT) {
		HEIGHT = hEIGHT;
	}

}
