package utils;

public class Controller {

	private static boolean Catched = false;
	private static boolean Catching = false;
	private static boolean Releasing = false;
	private static boolean OkToRelease = false;
	private static boolean moving =false;

	public static boolean isCatching() {
		return Catching;
	}

	public static void setCatching(boolean catching) {
		Catching = catching;
	}

	public static boolean isCatched() {
		return Catched;
	}

	public static void setCatched(boolean catched) {
		Catched = catched;
	}

	public static boolean isReleasing() {
		return Releasing;
	}

	public static void setReleasing(boolean releasing) {
		Releasing = releasing;
	}

	public static boolean isOkToRelease() {
		return OkToRelease;
	}

	public static void setOkToRealease(boolean okToRelease) {
		OkToRelease = okToRelease;
	}

	public static boolean isMoving() {
		return moving;
	}

	public static void setMoving(boolean moving) {
		Controller.moving = moving;
	}

}
