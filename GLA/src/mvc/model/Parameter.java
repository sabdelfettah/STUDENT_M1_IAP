package mvc.model;

public class Parameter implements Expression{
	
	//
	protected String name;
	protected int type;

	public Parameter(String name, int type) {
		this.setName(name);
		this.setType(type);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}
	
	public String toString(){
		System.out.println("result param = "+Declaration.typeNameWithoutVoid(type)+" "+name);
		return Declaration.typeNameWithoutVoid(type)+" "+name;
	}

	@Override
	public String Interpret(String language, boolean header) {
		return Declaration.typeNameWithoutVoid(type)+" "+name;
	}

}
