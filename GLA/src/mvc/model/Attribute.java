package mvc.model;


public class Attribute extends Declaration{

	private boolean _final;

	public Attribute(String name, int type, short visibility, boolean _static, boolean _final) {
		super(name, type, visibility, _static);
		this._final = _final;
	}

	public boolean is_final() {
		return _final;
	}

	public void set_final(boolean _final) {
		this._final = _final;
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
		if (_static)
			result += " s";
		if (_final)
			result += " f";
		result += " " + name + " : " + Declaration.typeNameWithoutVoid(type);
		return result;
	}

	@Override
	public String Interpret(String language, boolean header) {
		String result = "";
		result += Declaration.visibilityString(visibility);
		if (_static)
			result += " static";
		if (_final){
			if(language.equals("JavaCode")){
				result += " final";
			}else{
				result += " const";
			}
		}
		result += " " + Declaration.typeNameWithoutVoid(type) + " " + name + " ;";
		return result;
	}

}
