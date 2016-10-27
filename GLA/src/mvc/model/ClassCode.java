package mvc.model;

public class ClassCode implements Expression {

	private ClassDraw workingOnClass;

	public ClassCode(ClassDraw theClass) {
		workingOnClass = theClass;
	}

	@Override
	public String Interpret(String language, boolean header) {
		String result = Declaration.visibilityString(workingOnClass.getVisibility());
		if(language.equals("JavaCode")){
		switch (workingOnClass.getStereotype()) {
		case ClassDraw.NO_ABSTRACT_NO_INTERFACE_STEREOTYPE:
			result += " class";
			break;
		case ClassDraw.ABSTRACT_STEREOTYPE:
			result += " abstract class";
			break;
		case ClassDraw.INTERFACE_STEREOTYPE:
			result += " interface";
			break;
		default:
			break;
		}
		}else{
			result += " class";
		}
		result += " " + workingOnClass.getName()+"\n{\n";
		for (Attribute attribute : workingOnClass.getAttributes()) {
			result += "\n" + attribute.Interpret(language, header);
		}
		for (Method method : workingOnClass.getMethods()) {
			result += "\n" + method.Interpret(language, header);
			if (language.equals("CppCode") && header)
				result += " ;";
			else
				result += "\n{\n\n}";
		}
		result += "\n}";
		return result;
	}
}
