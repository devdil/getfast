package org.meta.file.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.download.manager.DownloadProgress;
import org.download.manager.FileTobeDownloadedInfo;

public class FileAppenderService implements AppendService{

	public boolean append(FileTobeDownloadedInfo fileTobeDownloadedInfo, DownloadProgress downloadProgress, long workerThreads) {
		
		// preparing to append files
		// prepare the final file extension
		String fileExtension = FilenameUtils.getExtension(fileTobeDownloadedInfo.getFileName());
		String fileName = FilenameUtils.getBaseName(fileTobeDownloadedInfo.getFileName());
		FileOutputStream finalFileOutputStream = null;
		try {
			finalFileOutputStream = new FileOutputStream(fileName+fileExtension);
		}
		catch (FileNotFoundException fileNotFoundException) {
			
		}
		String finalFileName = fileName + fileExtension;
		File finalFile = new File(finalFileName);
		
		try {
			FileOutputStream tempFileStream = new FileOutputStream(finalFile);
			}
			catch(FileNotFoundException fileNotFoundException) {
				
		}
		
		for (int i=1; i < workerThreads; i++) {
			// temp files are stored in the form of fFil
			String temporaryFileName = fileName + ".temp" + i; 
			FileInputStream tempFileInputStream= null;
			try {
			 tempFileInputStream = new FileInputStream(fileTobeDownloadedInfo.getDestination() + temporaryFileName);
			}
			catch(FileNotFoundException fileNotFoundException) {
				
			}
			try {
			IOUtils.copy(tempFileInputStream, finalFileOutputStream);
			}
			catch (IOException ioexception) {
				
			}
			
		}	
		return true;
	}
	
	

}
