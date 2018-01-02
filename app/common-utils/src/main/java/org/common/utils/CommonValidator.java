package org.common.utils;

import java.io.File;

public class CommonValidator {

	
	public static boolean isDirectoryValid(String directoryPath) {
		
		File file = new File(directoryPath);
		
		return file.exists() && file.isDirectory();
		
	}
}
