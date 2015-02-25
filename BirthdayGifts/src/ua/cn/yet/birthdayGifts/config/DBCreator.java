package ua.cn.yet.birthdayGifts.config;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.apache.derby.tools.ij;

public class DBCreator {
	
	
	
	/**
	 * 
	 */
	public DBCreator() {
		super();
		try {
			create();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String [] args) throws IOException {
		
		create();
		
	}

	public static void create() throws IOException {
		FileUtils.deleteDirectory(new File("/tmp/db_gifts"));
		
		URL url = DBCreator.class.getClassLoader().getResource("script.sql");
		
		String [] a = new String [1];
		a[0] = url.getFile();
		
		ij.main(a);
	}

}
