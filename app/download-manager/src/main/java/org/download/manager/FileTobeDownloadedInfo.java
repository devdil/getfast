package org.download.manager;

public class FileTobeDownloadedInfo {

	private String fileName;
	private String fileType;
	private long fileSize;
	private boolean canbeDownloadedParallel;
	
	
	FileTobeDownloadedInfo(String fileName, String fileType,long fileSize, boolean canbedownloadedParallel){
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileSize = fileSize;
		this.canbeDownloadedParallel = canbedownloadedParallel;
		
	}
	
	public String getFileName() {
		return this.getFileName();
	}
	public String getFileType() {
		return this.getFileType();
	}
	public long getFileSize() {
		return this.getFileSize();
	}
	
	public boolean getCanBeDownloadeParallel() {
		return this.canbeDownloadedParallel;
	}
	
}
