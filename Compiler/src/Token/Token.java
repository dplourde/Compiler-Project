package Token;

public class Token {

	private TokenEnum tokenName;
	private String attributeString = null;
	private double attributeDouble;
	private boolean attributeBool;
	
	//Handle capitals in lexem???
	//literals and keywords come in as TokenEnum.ID
	//may want to watch for errors here with wrong data in lexem
	public Token(TokenEnum name, String lexem){
		this.tokenName = name;
		
		//select which lexem to fill based on the contents of lexem
		if(this.tokenName==TokenEnum.ID){
			this.attributeString = lexem;
		}
		if(this.tokenName==TokenEnum.NUM){
			this.attributeDouble = Double.parseDouble(lexem);
		}
		if(this.tokenName==TokenEnum.BLIT){
			this.attributeBool = Boolean.parseBoolean(lexem);
		}
	}
	
	public String getAttributeAsString(){
		String attribute = "";
				
		switch(tokenName){
			case ID:
				attribute = attributeString;
				break;
			case NUM:
				attribute = ""+attributeDouble;
				break;
			case BLIT:
				attribute = ""+attributeBool;
				break;
			default:
				break;
		
		}
		
		return attribute;
	}
	
	public TokenEnum getTokenName(){
		return this.tokenName;
	}
	
	public String toString(){
		String s = "";
		
		s+=this.tokenName+", ";
		
		switch(this.tokenName){
			case ID:
				s+=this.attributeString;
				break;
			case NUM:
				s+=this.attributeDouble;
				break;
			case BLIT:
				s+=this.attributeBool;
				break;
			default:
				s+="no attribute";
				break;
			
		}
		
		
		
		return s;
	}
	
}
