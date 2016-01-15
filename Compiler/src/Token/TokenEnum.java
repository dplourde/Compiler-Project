package Token;
public enum TokenEnum { 
	//Not sure about these (should have string as variable)
	AND("and"),
	BOOL("bool"),
	BRANCH("branch"),
	CASE("case"),
	CONTINUE("continue"),
	DEFAULT("default"),
	ELSE("else"),
	END("end"),
	EXIT("exit"),
	IF("if"),
	INT("int"),
	LOOP("loop"),
	MOD("mod"),
	NOT("not"),
	OR("or"),
	REF("ref"),
	RETURN("return"),
	VOID("void"),
	ID(""),
	NUM(""),
	BLIT(""),
	ENDFILE("\0"),
	ERROR(""),		
	PLUS("+"),
	MINUS("-"),
	MULT("*"),
	DIV("/"),
	ANDTHEN("&&"),
	ORELSE("||"),
	LT("<"),
	LTEQ("<="),
	GT(">"),
	GTEQ(">="),
	EQ("="),
	NEQ("/="),
	ASSIGN(":="),
	COLON(":"),
	SEMI(";"),
	COMMA(","),
	LPAREN("("),
	RPAREN(")"),
	LSQR("["),
	RSQR("]"),
	LCRLY("{"),
	RCRLY("}");
	
	private String lexem;
	
	private TokenEnum(String lex){
		this.lexem = lex;
	}
	
	public String getLexem(){
		return this.lexem;
	}
	
	//needs testing
	public static TokenEnum[] getWordTokens(){
		TokenEnum[] wordTokens = new TokenEnum[19];
		TokenEnum[] allTokens = TokenEnum.values();
		wordTokens[0] = TokenEnum.BLIT;
		for(int i = 0; i <18; i++){
			wordTokens[i+1] = allTokens[i];
		}
		
		return wordTokens;
	}
}

