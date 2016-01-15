package Token;

import java.util.Hashtable;



public class TokenFactory {

	private Hashtable<String,Token> wordTokenTable;
	private int currentIDNumber = 0;
	
	public TokenFactory(){
		wordTokenTable = new Hashtable<String,Token>();
		insertKeyWordTokens();
		//set up initial hashmap with keywords
	}
	
	//inserts all of language's key words into hash table as well as boolean literals
	private void insertKeyWordTokens(){
		TokenEnum[] wordTokens = TokenEnum.getWordTokens();
		Token t = new Token(wordTokens[0],"true");
		wordTokenTable.put(t.getAttributeAsString(), t);	//Not implemented
		t = new Token(wordTokens[0],"false");
		wordTokenTable.put(t.getAttributeAsString(), t);	//Not implemented
		
		for(int i = 1; i < wordTokens.length; i++){
			t = new Token(wordTokens[i],"");
			wordTokenTable.put(wordTokens[i].getLexem(), t);
		}
		
	}
	//Creates new Token and inserts in table if Token doesn't exist already within Hashtable else returns existing
	public Token newToken(TokenEnum tokenName, String lexem){
		Token returnToken;
		//need to handle index if not yet in table
		if(tokenName == TokenEnum.ID){
			returnToken = wordTokenTable.get(lexem);
			if(returnToken==null){
				returnToken = new Token(tokenName, ""+currentIDNumber++);	//ID inserted into table with unique ID number (incremented after used)
				wordTokenTable.put(lexem, returnToken);
			}
		}
		else if(tokenName == TokenEnum.NUM){
			returnToken = new Token(tokenName, lexem);
		}
		else{
			returnToken = new Token(tokenName, "");		//Worth creating early then just reusing???
		}
		return returnToken;
	}
	
}
