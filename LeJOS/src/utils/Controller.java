package utils;

public class Controller {

	private static boolean Catched = false;
	private static boolean Catching = false;
	private static boolean Releasing = false;
	private static boolean OkToRelease = false;
	private static boolean OkToMove = false;
	private static boolean Moving = false;
	private static boolean Lost = false;
	private static boolean NeedToCheckPosition = false;
//	private static int count = 0;
//	private static long time;

	public static boolean isCatching() {
		return Catching;
	}

	public static void setCatching(boolean catching) {
		Catching = catching;
		computeOkToMove();
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
		computeOkToMove();
	}

	public static boolean isOkToRelease() {
		return OkToRelease;
	}

	public static void setOkToRealease(boolean okToRelease) {
		OkToRelease = okToRelease;
	}

	public static boolean isMoving() {
		return Moving;
	}

	public static void setMoving(boolean moving) {
		Controller.Moving = moving;
	}

//	public static long getTime() {
//		return time;
//	}
//
//	public static void setTime(long time) {
//		Controller.time = time;
//	}
//
//	public static int getCount() {
//		return count;
//	}
//
//	public static void setCount(int count) {
//		Controller.count = count;
//	}
//
//	public static void pp() {
//		count++;
//	}

	public static void setAllToFalse() {
		Catched = false;
		Catching = false;
		Releasing = false;
		Moving = false;
		OkToRelease = false;
	}

	public static boolean isLost() {
		return Lost;
	}

	public static void setLost(boolean lost) {
		Lost = lost;
		computeOkToMove();
	}

	public static boolean isNeedToCheckPosition() {
		return NeedToCheckPosition;
	}

	public static void setNeedToCheckPosition(boolean needToCheckPosition) {
		NeedToCheckPosition = needToCheckPosition;
		computeOkToMove();
	}

	public static boolean isOkToMove() {
		return OkToMove;
	}

	private static void computeOkToMove() {
		if(!Catching && !Releasing && !NeedToCheckPosition && !Lost){
			OkToMove = true;
		}else{
			OkToMove = false;
		}
	}

}
