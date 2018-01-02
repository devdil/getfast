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
    		CommandLineArg commandLineArg;
			try {
				commandLineArg = new CommandLineArg(commandLineargs);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return;
			}
    		DownloadManager downloadManager = new DownloadManager(commandLineArg);
    		downloadManager.download();
    	}
    }
    
}
