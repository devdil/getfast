package org.common.utils;
import org.console.ui.*;
import org.product.constants.ProductConstants;
import org.product.constants.CommandLineArgs;

import java.util.List;
public class UsageHelper {
	
	public void showUsage() {
		ConsoleUI.displayNewLineWithPound();
		ConsoleUI.displayInfo("Usage ");
		ConsoleUI.displayInfo(" ");
		ConsoleUI.displayInfo(ProductConstants.PRODUCT_NAME+" http://foobar.com/file.zip --argument=<value>");
		ConsoleUI.displayInfo(" ");
		ConsoleUI.displayInfo("The arguments that can be supplied to the utility are:");
		ConsoleUI.displayInfo(" ");
		for (CommandLineArgs commandLineArg: CommandLineArgs.values()) {
			ConsoleUI.displayInfo(commandLineArg.commandLineArgUsage());
			ConsoleUI.displayInfo(" ");
		}
		ConsoleUI.displayNewLineWithPound();
		
	}
	
	public static void main(String args[]) {
		UsageHelper usageHelper = new UsageHelper();
		usageHelper.showUsage();
	}
	
	
}
