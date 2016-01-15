package scanner;

import Token.*;
import admin.Admin;

public class Scanner {
	
	private CommentStatusEnum commentStatus = CommentStatusEnum.OUTOFCOMMENT;	//make enum??
	private TokenFactory tokenFactory;
	private Admin admin;
	private boolean needNewChar = true;
	private boolean multiChar = false;	//if token has multiple characters
	private int nextCode=-1;
	private boolean whiteSpace = false;
	
	public Scanner(Admin a){
		this.tokenFactory = new TokenFactory();
		this.admin = a;
	}
	
	public Token getNextToken(){
			
		TokenEnum tokenName = null;
		String lexem = "";
		
		do{	
			//reset whiteSpace each pass
			whiteSpace = false;
			
			//only retrieve if the current nextCode has been used else set needNewChar to true (the character guaranteed to be used in the next pass)
			if(needNewChar){
				nextCode = this.getNextCharCode();}	
			else{
				needNewChar = true;
			}
			
			//check if current nextCode could be part of a multi-character token			
			if(multiChar){
				switch(tokenName){
				
																		//ADD ERROR INFORMATION !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
				
					//when one & or | received tokenName set preemptively to ANDTHEN or ORELSE, changed to error if second character doesn't match
					case ANDTHEN:{
						//if an & is not followed by another then it doesn't match any token
						if(nextCode!='&'){
							tokenName = TokenEnum.ERROR;
						}
						multiChar = false;
						break;
					}
					case ORELSE:{
						//if a | is not followed by another then it doesn't match any token
						if(nextCode!='|'){
							tokenName = TokenEnum.ERROR;
						}
						multiChar = false;
						break;
					}
					
					/*in these cases if a certain character is received after the initial then it matches a different token type otherwise the second character is saved
					to be used in the next token and the tokenName left as is*/
					case LT:{
						if(nextCode!='='){
							needNewChar = false;
							multiChar = false;
						}
						//matching '<='
						else{
							tokenName = TokenEnum.LTEQ;
						}
						multiChar = false;
						break;
					}
					case GT:{
						if(nextCode!='='){
							needNewChar = false;
							multiChar = false;
						}
						//matching '>='
						else{
							tokenName = TokenEnum.GTEQ;
						}
						multiChar = false;
						break;
					}
					case COLON:{
						if(nextCode!='='){
							needNewChar = false;
							multiChar = false;
						}
						//matching ':='
						else{
							tokenName = TokenEnum.ASSIGN;
						}
						multiChar = false;
						break;
					}
					//if the first character was '/' then depending on the next character the token could either be '/=', '/' or the start of a comment
					case DIV:{
						//start of multiline comment
						if(nextCode == '*'){
							//reset and have system ignore this token because this marks a comment until end of comment token (*/)
							commentStatus = CommentStatusEnum.MULTILINE;
							tokenName = null;
							multiChar = false;
							lexem = "";
							continue;
						}
						else if(nextCode!='='){
							needNewChar = false;
							multiChar = false;
						}
						//matching '/='
						else{
							tokenName = TokenEnum.NEQ;
						}
						multiChar = false;
						break;
					}
					//if second character is another '-' then it is the start of a single line comment
					case MINUS:{
						if(nextCode!='-'){
							needNewChar = false;
							multiChar = false;
						}
						else{
							//reset and have system ignore because this marks a comment until a newline
							commentStatus = CommentStatusEnum.SINGLELINE;
							tokenName = null;
							multiChar = false;
							lexem = "";
							continue;
						}
						break;
					}
					case NUM:{
																	//ADD ERROR REPORTING!!!!!!!!!!!!!!!!!!!!!
						if(nextCode>='0' && nextCode<='9'){
							lexem +=(char) nextCode;		//number is not finished so add current char to lexem string and continue on this multi-character lexem
						}
						else if(nextCode == ' ' || nextCode == -1){	//SHOULD ALSO END NUMBERS WHEN SYMBOLS RECIEVED!!!!!!!!!!!!!!!!!!!!!!
							multiChar = false;		//number is finished so stop on this multi-character lexem
							needNewChar=false;
						}
						else{
							tokenName = TokenEnum.ERROR;	//not a valid token	
						}
						break;
					}
					case ID:{
						if((nextCode>='0' && nextCode<='9') || (nextCode>='A'&&nextCode<='Z') || (nextCode>='a'&&nextCode<='z') || (nextCode ==  36) || (nextCode ==  95)){
							lexem +=(char) nextCode;		//identifier is not finished so add current char to lexem string and continue on this multi-character lexem
						}
						else if(nextCode == ' ' || nextCode == -1){//SHOULD ALSO END IDENTIFIERS WHEN SYMBOLS RECIEVED!!!!!!!!!!!!!!!!!!!!!!
							multiChar = false;		//identifier is finished so stop on this multi-character lexem
							needNewChar = false;	
						}
						else{
							tokenName = TokenEnum.ERROR;	//not a identifier token		//need to add error reporting info
							multiChar = false;
						}
						break;
					}
				default:break;
				}
				
			}
			else{
				switch(nextCode){
					//may not work
					case -1:tokenName = TokenEnum.ENDFILE; break;
					case ' ' :
					case '	':whiteSpace = true; break;
					case '+':tokenName = TokenEnum.PLUS; break;
					case '*':tokenName = TokenEnum.MULT; break;
					case ';':tokenName = TokenEnum.SEMI; break;
					case ',':tokenName = TokenEnum.COMMA; break;
					case '(':tokenName = TokenEnum.LPAREN; break;
					case ')':tokenName = TokenEnum.RPAREN; break;
					case '[':tokenName = TokenEnum.LSQR; break;
					case ']':tokenName = TokenEnum.RSQR; break;
					case '{':tokenName = TokenEnum.LCRLY; break;
					case '}':tokenName = TokenEnum.RCRLY; break;
					case '|':tokenName = TokenEnum.ORELSE; multiChar = true; break;
					case '&':tokenName = TokenEnum.ANDTHEN; multiChar = true; break;
					case '-':tokenName = TokenEnum.MINUS; multiChar = true; break;
					case '/':tokenName = TokenEnum.DIV; multiChar = true; break;
					case '<':tokenName = TokenEnum.LT; multiChar = true; break;
					case '>':tokenName = TokenEnum.GT; multiChar = true; break;
					case '=':tokenName = TokenEnum.EQ; break;
					case ':':tokenName = TokenEnum.COLON; multiChar = true; break;
					default:{
						if(nextCode>='0' && nextCode<='9'){
							tokenName = TokenEnum.NUM;
							multiChar = true;
						}
						else if((nextCode>='A'&&nextCode<='Z')||(nextCode>='a'&&nextCode<='z')){
							tokenName = TokenEnum.ID;
							multiChar = true;
						}
						else{
							//not a valid token			ADD ERROR STUFF!!!!!!!!!!!!!!!!!!!!!!
							tokenName = TokenEnum.ERROR;
						}
					}
				}
				if(!whiteSpace){
					lexem += (char)nextCode;
				}
			}//end else
		
			//may not want this in order to allow system to report whole set of symbols that caused problem!!!!!!!!!!!!!!!!!!!!!!!
		}while(multiChar||whiteSpace||commentStatus!=CommentStatusEnum.OUTOFCOMMENT);		

		return tokenFactory.newToken(tokenName, lexem);
	}
	
	//returns character if it is a symbol, letter, number, space, or tab
	//doesn't return character if in comment until end of comment or end of file
	private int getNextCharCode(){
		int returnChar = 0;
				
		//Ignore all characters except escape characters while in comment
		while(commentStatus!=CommentStatusEnum.OUTOFCOMMENT){
			returnChar = admin.getCh();
			switch(commentStatus){
				case SINGLELINE:{
					if(returnChar =='\n'||returnChar == -1){
						commentStatus = CommentStatusEnum.OUTOFCOMMENT;
					}
					break;
				}
				case MULTILINE:{
					if(returnChar =='*'){
						commentStatus = CommentStatusEnum.MULTILINEESCAPE;
					}
					else if(returnChar == -1){
						commentStatus = CommentStatusEnum.OUTOFCOMMENT;		//probably should be error for ==-1 on all of these!!!!!!!!!!!!!
					}
					break;
				}
				case MULTILINEESCAPE:{
					if(returnChar == '/'||returnChar == -1){
						commentStatus = CommentStatusEnum.OUTOFCOMMENT;
					}
					else{
						commentStatus = CommentStatusEnum.MULTILINE;
					}
					break;				
				}
				default://should never get here
			}
		}
		
		do{		//ignore non visible characters except tab(11) and end of file(-1)
			returnChar = admin.getCh();
		}while(returnChar < 32&&returnChar>-1&&returnChar!=11);
		return returnChar;		
	}
}
