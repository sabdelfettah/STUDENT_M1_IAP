package mvc.model;

import java.util.ArrayList;

public class Method extends Declaration{

	private boolean _abstract;
	private ArrayList<Parameter> arguments;

	public Method(String name, int type, short visibility, boolean _static, boolean _abstract) {
		super(name, type, visibility, _static);
		this._abstract = _abstract;
		arguments = new ArrayList<Parameter>();
	}

	public ArrayList<Parameter> getArguments() {
		return arguments;
	}

	public boolean is_abstract() {
		return _abstract;
	}

	public void set_abstract(boolean _abstract) {
		this._abstract = _abstract;
	}

	public void removeParameterAt(int index) {
		arguments.remove(index);
	}

	public void addParameter(Parameter newParameter) {
		arguments.add(newParameter);
	}

	public void addParameter(String name, int type) {
		arguments.add(new Parameter(name, type));
	}

	public String toString() {
		String result = "";
		switch (visibility) {
		case Declaration.PUBLIC:
			result = "+";
			break;
		case Declaration.PROTECTED:
			result = "#";
			break;
		case Declaration.PRIVATE:
			result = "-";
			break;
		}
		if (_abstract)
			result += " a";
		if (_static)
			result += " s";
		result += " " + name + "(";
		if (arguments.size() > 0) {
			for (int index = 0; index < arguments.size() - 1; index++) {
				result += arguments.get(index).toString() + ", ";
			}
			result += arguments.get(arguments.size() - 1).toString();
		}
		result += ") : " + Declaration.typeNameWithVoid(type);
		return result;
	}

	@Override
	public String Interpret(String language, boolean header) {
		String result = Declaration.visibilityString(visibility);
		if (_static)
			result += " static";
		if (_abstract){
			if(language.equals("JavaCode")){
				result += " abstract";
			}else{
				result += " virtual";
			}
		}
		result += " " + Declaration.typeNameWithVoid(type) + " " + name + " (";
		if (arguments.size() > 0) {
			for (int index = 0; index < arguments.size() - 1; index++) {
				result += arguments.get(index).Interpret(language, header) + ", ";
			}
			result += arguments.get(arguments.size() - 1).Interpret(language, header);
		}
		result += ")";
		return result;
	}

}
