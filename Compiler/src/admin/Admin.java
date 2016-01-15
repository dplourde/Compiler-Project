package admin;

//import java.io.BufferedReader;
import java.io.FileNotFoundException;
//import java.io.FileReader;
import java.io.IOException;
import java.io.FileInputStream;

public class Admin {
	
	private String fileIn;
	private FileInputStream fIS;
	
	public Admin(String fIn){
		this.fileIn = fIn;
		try {
			this.fIS = new FileInputStream(this.fileIn);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public int getCh(){
		int c = 'c';
		try {
			c = this.fIS.read();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return c;
	}

}
