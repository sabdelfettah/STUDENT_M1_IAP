package test;

public class TestController {
	
	private static int count = 0;

	public static int getCount() {
		return count;
	}

	public static void setCount(int newCount) {
		count = newCount;
	}

	public static void pp() {
		count ++;
	}

}
