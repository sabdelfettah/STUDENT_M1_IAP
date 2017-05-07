package structs;

public class Line {
	
	private int level;
	private String identifier;
	private String tag;
	private String value;
	private int idLocation;
	
	public Line(){
		initialiseLine();
	}
	
	public Line(int lev, String id, String tag, String val){
		level = lev;
		identifier = id;
		this.tag = tag;
		value = val;
	}
	
	public void initialiseLine(){
		level = -1;
		idLocation = -1;
		identifier = null;
		tag = null;
		value = null;
	}
	
	public void setTokenAt(String tok, int pos){
		//System.out.println("Before - pos="+pos+" ; lev="+level+" ; id ="+identifier+" ; tag="+tag+" ; +val="+value);
		if(tok!=""){
			switch(pos){
			case 0: level = Integer.parseInt(tok);break;
			case 1: identifier = tok.substring(1, tok.length()-1); break;
			case 2: tag = tok; break;
			case 3: {
				if(idLocation==3){
					value = tok.substring(1, tok.length()-1);
				}else{
					value = tok; break;
				}
			}
			}
		}
		//System.out.println("After  - pos="+pos+" ; lev="+level+" ; id ="+identifier+" ; tag="+tag+" ; val="+value);
	}
	
	public void setIdLocation(int location){
		idLocation = location;
	}
	
	public int getIdLocation(){
		return idLocation;
	}
	
	public int getLevel(){
		return level;
	}
	
	public String getIdentifier(){
		return identifier;
	}
	
	public String getTag(){
		return tag;
	}
	
	public String getValue(){
		return value;
	}

}
