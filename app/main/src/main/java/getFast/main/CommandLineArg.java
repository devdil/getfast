package getFast.main;

import org.product.constants.*;

public class CommandLineArg {

	private String urlToDownload;
	private String downloadLocation;
	private String proxy;
	
	
	public CommandLineArg(String[] args) {
		for(String arg: args) {
			if (arg.startsWith("--")) {
				String argumentValue = arg.split("=")[1];
				String arguement = arg.split("=")[1];
			}
		}
		
	}

	
}
