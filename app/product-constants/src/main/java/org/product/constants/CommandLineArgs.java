package org.product.constants;

public enum CommandLineArgs {

	
	DESTINATION("destination", "The download location to the file .e.g. in Linux /home/johndoe/myfiles/"),
	PROXY("proxy","Proxy address to be used if you are behing a firewall or restricted access"),
	THREADS("threads","Parallel threads to maximize the download perfomance. The max threads is now 10");
	
	private String commandLineArgument;
	private String usageDescription;
	
	CommandLineArgs(String commandLineArg, String usageDescription){
		this.commandLineArgument = commandLineArg;
		this.usageDescription = usageDescription;
	}
	
	public String commandLineArgAbbreviated() {
		return this.commandLineArgument;
	}
	
	public String commandLineArgUsage() {
		return "--"+this.commandLineArgument+'='+this.usageDescription;
	}
	
	public static String commandLineArgUsage(String command) {
		return "";
	}
	
	
	
}
