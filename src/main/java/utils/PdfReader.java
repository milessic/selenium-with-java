package utils;

import java.io.File;
import java.io.IOException;
import java.lang.Thread;


public class PdfReader{
	public String filePath;
	public File file;
	private final int timeout = 30;

	public PdfReader(String filePath){
		this.filePath = filePath;
		this.file = new File(filePath);
	}

	public boolean exists(){
		System.out.println("looking for '" + this.filePath + "'");
		for ( int i = 0; i < this.timeout; i++){
			if ( this.file.exists() && !this.file.isDirectory()){
				System.out.println("Found file '" + this.filePath + "'");
				return true;
			}
			try {
    			Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
		}
		System.out.println("Didn't found the file! - '" + this.filePath + "'");
		return false;
	}

	public boolean delete(){
		if ( this.file.delete() ){
			System.out.println("Deleted '" + this.filePath + "'.");
			return true;
		} 
		System.out.println("Could not delete! - '" + this.filePath + "'.");
		return false;
	}
}
