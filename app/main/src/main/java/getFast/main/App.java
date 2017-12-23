package getFast.main;

import org.common.utils.Validator;
import org.download.manager.CommandLineArg;
import org.download.manager.DownloadManager;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main(String[] commandLineargs )
    {
        // This is the entry point of the application.
  
    	if (Validator.areCommandLineArgsValid(commandLineargs)){
    		CommandLineArg commandLineArg = new CommandLineArg(commandLineargs);
    		DownloadManager downloadManager = new DownloadManager(commandLineArg);
    	}
    }
}
