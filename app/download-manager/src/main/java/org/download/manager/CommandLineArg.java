package org.download.manager;

import org.product.constants.*;

public class CommandLineArg {

	private String urlToDownload;
	private String downloadLocation;
	private String proxy;
	private int threads;
	
	
	public CommandLineArg(String[] args) {
		
		transformToSelf(args);
	}
	
	private void transformToSelf(String[] args) {
		
		for(String arg: args) {
			if (arg.startsWith("--")) {
				String argumentValue = arg.split("=")[1];
				String arguement = arg.split("=")[1];
				CommandLineArgs commandLineEnum = CommandLineArgs.enumCodeForCommand(arguement);
				switch(commandLineEnum) {
				
				case DESTINATION:
					this.downloadLocation = argumentValue;
					break;
				case PROXY:
					this.proxy = argumentValue;
					break;
				case THREADS:
					this.threads = threads;
					break;
				}
			}
			else {
				//this should be the file to download
				this.urlToDownload = arg;
			}
		}
		
	}
	
	public String getUrl() {
		return this.urlToDownload;
	}
	
	public String getProxy() {
		return this.proxy;
	}
	
	public String getDownloadLocation() {
		return this.downloadLocation;
	}
	
	public int getThreads() {
		return this.threads;
	}
}
