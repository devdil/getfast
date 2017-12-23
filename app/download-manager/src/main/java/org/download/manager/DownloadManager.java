package org.download.manager;

import org.product.constants.CommandLineArgs;

public class DownloadManager {

	private CommandLineArg commandLineArgs;
	
	public DownloadManager(CommandLineArg commandLineArgs){
		this.commandLineArgs = commandLineArgs;
	}
	
	public void download() {
		
		String url = this.commandLineArgs.getUrl().toLowerCase();
		String protocol = "";
		if (url.contains("ftp"))
			protocol ="FTP";
		else if (url.contains("http"))
			protocol = "HTTP";
		else if (url.contains("https"))
			protocol = "HTTPS";
		else
			protocol = "NA";
		switch(url) {
		case "FTP":
			FTPDownloader ftpDownloader = new FTPDownloader(this.commandLineArgs);
			ftpDownloader.download();
		case "HTTP":
			HttpDownloader httpDownloader = new HttpDownloader(this.commandLineArgs);
			httpDownloader.download();
		case "NA":
			//todo
			break;
		default:
			System.out.println("No match");
			break;
			
		}
	}
	
	

	
	
}
