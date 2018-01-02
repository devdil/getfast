package org.download.manager;

public class FileTobeDownloadedInfo {

	private String fileName;
	private String fileType;
	private long fileSize;
	private boolean canbeDownloadedParallel;
	private String  destination;
	private String remoteUrl;
	
	FileTobeDownloadedInfo(){
		
	}
	
	FileTobeDownloadedInfo(String fileName, String fileType,long fileSize, boolean canbedownloadedParallel, String destination){
		this.fileName = fileName;
		this.fileType = fileType;
		this.fileSize = fileSize;
		this.canbeDownloadedParallel = canbedownloadedParallel;
		this.destination = destination;
		
	}
	
	public String getFileName() {
		return this.fileName;
	}
	public String getFileType() {
		return this.fileType;
	}
	public long getFileSize() {
		return this.fileSize;
	}
	
	public boolean getCanBeDownloadeParallel() {
		return this.canbeDownloadedParallel;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public void setCanbeDownloadedParallel(boolean canbeDownloadedParallel) {
		this.canbeDownloadedParallel = canbeDownloadedParallel;
	}
	
	public String getDestination() {
		return this.destination;
	}
	
	public void setDestination(String destination) {
		
		this.destination = destination;
	}
	
	public void setRemoteUrl(String remoteUrl) {
		this.remoteUrl = remoteUrl;
	}
	
	public String getRemotUrl() {
		return this.remoteUrl;
	}
	
}
