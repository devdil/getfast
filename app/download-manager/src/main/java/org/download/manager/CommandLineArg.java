package org.download.manager;

import org.common.utils.CommonValidator;
import org.product.constants.*;

public class CommandLineArg {

	private String urlToDownload;
	private String downloadLocation;
	private String proxy;
	private int threads;
	
	
	public CommandLineArg(String[] args) throws Exception {
		
		transformToSelf(args);
	}
	
	private void transformToSelf(String[] args) throws Exception {
		
		for(String arg: args) {
			if (arg.startsWith("--")) {
				String argumentValue = arg.split("=")[1];
				String arguement = arg.split("=")[0].replaceAll("--","");
				CommandLineArgs commandLineEnum = CommandLineArgs.enumCodeForCommand(arguement);
				switch(commandLineEnum) {
				
				case DESTINATION:
					if (CommonValidator.isDirectoryValid(argumentValue))
						this.downloadLocation = argumentValue;
					else
						throw new Exception("Invalid Directory");
					break;
				case PROXY:
					this.proxy = argumentValue;
					break;
				case THREADS:
					this.threads = Integer.valueOf(argumentValue);
					break;
				case URL:
					this.urlToDownload = argumentValue;
				}
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
