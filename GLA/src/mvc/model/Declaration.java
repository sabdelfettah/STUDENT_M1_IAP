package mvc.model;

public abstract class Declaration extends Parameter {

	public static final short PUBLIC = 0;
	public static final short PROTECTED = 1;
	public static final short PRIVATE = 2;
	public static final int INT = 0;
	public static final int FLOAT = 1;
	public static final int DOUBLE = 2;
	public static final int CHAR = 3;
	public static final int STRING = 4;
	public static final int BOOLEAN = 5;
	public static final int VOID = 6;
	protected short visibility;
	protected boolean _static;

	public Declaration(String name, int type, short visibility, boolean Static) {
		super(name, type);
		this.visibility = visibility;
		this._static = Static;
	}

	public int getVisibility() {
		return visibility;
	}

	public void setVisibility(short visibility) {
		this.visibility = visibility;
	}

	public boolean isStatic() {
		return _static;
	}

	public void setStatic(boolean Static) {
		this._static = Static;
	}

	public static String typeNameWithoutVoid(int type) {
		switch (type) {
		case INT:
			return "int";
		case FLOAT:
			return "float";
		case DOUBLE:
			return "double";
		case CHAR:
			return "char";
		case STRING:
			return "String";
		case BOOLEAN:
			return "boolean";
		}
		return "else";
	}

	public static String typeNameWithVoid(int type) {
		switch (type) {
		case INT:
			return "int";
		case FLOAT:
			return "float";
		case DOUBLE:
			return "double";
		case CHAR:
			return "char";
		case STRING:
			return "String";
		case BOOLEAN:
			return "boolean";
		case VOID:
			return "void";
		}
		return "else";
	}

	public static int typeIntWithoutVoid(String type) {
		if (type.equals("int")) {
			return INT;
		} else if (type.equals("float")) {
			return FLOAT;
		} else if (type.equals("double")) {
			return DOUBLE;
		} else if (type.equals("char")) {
			return CHAR;
		} else if (type.equals("String")) {
			return STRING;
		} else if (type.equals("boolean")) {
			return BOOLEAN;
		} else {
			return INT;
		}
	}
	
	public static int typeIntWithVoid(String type) {
		if (type.equals("int")) {
			return INT;
		} else if (type.equals("float")) {
			return FLOAT;
		} else if (type.equals("double")) {
			return DOUBLE;
		} else if (type.equals("char")) {
			return CHAR;
		} else if (type.equals("String")) {
			return STRING;
		} else if (type.equals("boolean")) {
			return BOOLEAN;
		} else if (type.equals("void")) {
			return VOID;
		} else {
			return INT;
		}
	}

	public static String visibilityString(short visibility) {
		switch (visibility) {
		case PUBLIC:
			return "public";
		case PROTECTED:
			return "protected";
		case PRIVATE:
			return "private";
		default:
			return "";
		}
	}

	public static short visibilityShort(String visibility) {
		if (visibility.equals("public")) {
			return PUBLIC;
		} else if (visibility.equals("protected")) {
			return PROTECTED;
		} else {
			return PRIVATE;
		}
	}

	public static String isToString(boolean value) {
		if (value)
			return "true";
		else
			return "false";
	}
	
	public static boolean StringToIs(String value) {
		if (value.equals("true"))
			return true;
		else
			return false;
	}

}
