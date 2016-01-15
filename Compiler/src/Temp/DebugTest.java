package Temp;

import Token.Token;
import Token.TokenEnum;
import admin.Admin;
import scanner.Scanner;

public class DebugTest {
	
	public static void main(String[] args){
		Admin myAdmin = new Admin("testFile.txt");
		Scanner myScan = new Scanner(myAdmin);
		Token t = myScan.getNextToken();
		
		while(t.getTokenName() != TokenEnum.ENDFILE){
			System.out.println(t);
			t = myScan.getNextToken();
		}
		System.out.println(t);
	}
}
