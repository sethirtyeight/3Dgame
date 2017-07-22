package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

public class FileUtil {

	public static String getFileAsString(String iv_fileName) {
		StringBuilder result = new StringBuilder("");
		
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(ShaderProgram.class.getClassLoader().getResourceAsStream(iv_fileName)));
			String line ;
			while ((line = reader.readLine()) != null )
			{
				result.append(line).append("\n");
			}
			reader.close();
		}
		catch (Exception e) {
			System.err.println("Error loading source code: " + iv_fileName);
			e.printStackTrace();
		}
		
		return result.toString();
		
	}
	
    public static String[] getFileAsArray(String iv_filename) { 
    	return getFileAsString(iv_filename).split("\n");
    }
}
