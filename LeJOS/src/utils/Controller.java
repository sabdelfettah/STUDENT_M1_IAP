package utils;

public class Controller {

	private static boolean Catched = false;
	private static boolean Catching = false;
	private static boolean Releasing = false;
	private static boolean OkToRelease = false;
	private static boolean OkToMove = false;
	private static boolean Moving = false;
	private static boolean Lost = false;
	private static boolean Exit = false;
	private static boolean NeedToCheckPosition = false;
	private static boolean oktoAdjust = false ; 

	public static boolean isOkToAdjust () {
		return oktoAdjust ;
	} 
	
	public static void setOktoAdjust (boolean b) {
		oktoAdjust = b ;
	}
	
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
	
	public static boolean isCatchingOrReleasing(){
		return Catching || Releasing;
	}

	public static boolean isOkToRelease() {
		return OkToRelease;
	}

	public static void setOkToRealease(boolean okToRelease) {
		OkToRelease = okToRelease;
	}

	public static boolean notExit() {
		return !Exit;
	}

	public static void setExit(boolean exit) {
		Exit = exit;
	}

	public static boolean isMoving() {
		return Moving;
	}

	public static void setMoving(boolean moving) {
		Controller.Moving = moving;
	}

	public static void setAllToFalse() {
		Catched = false;
		Catching = false;
		Releasing = false;
		Moving = false;
		OkToRelease = false;
		computeOkToMove();
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
