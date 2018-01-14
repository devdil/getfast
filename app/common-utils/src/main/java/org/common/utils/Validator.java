package org.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.product.constants.CommandLineArgs;

public class Validator {

	public static boolean areCommandLineArgsValid(String[] commandLineArgsFromTheCommandLine) {

		boolean  commandLineArgMatch = false;
		boolean  showHelpText = false;
		boolean  atleastOneMismatch = false;
		List<String> invalidListofCommandLineArgs = new ArrayList<String>();
		
		for (int i=1; i<=commandLineArgsFromTheCommandLine.length; i++) {
			
			String commandLineArg = commandLineArgsFromTheCommandLine[i];
			// TODO add regex to check if the arg is in the from --key=value
			String commanLineArgKey = commandLineArg.split("=")[0].replaceAll("--","");
			for (CommandLineArgs commandLineArgConstant : CommandLineArgs.values()) {
				if (commanLineArgKey.equalsIgnoreCase(commandLineArgConstant.commandLineArgAbbreviated())) {
					if (commanLineArgKey.equalsIgnoreCase(CommandLineArgs.HELP.commandLineArgAbbreviated())) {
						showHelpText = true;
					}
					commandLineArgMatch = true;
					break;
				}
			}
			
			if (showHelpText) 
				break;
			
			if (!commandLineArgMatch) {
				invalidListofCommandLineArgs.add(commandLineArg);
				if (!commandLineArgMatch)
					atleastOneMismatch = true;
			}
		
			commandLineArgMatch = false;
			
		}
		
		
		if (atleastOneMismatch || showHelpText) {
			UsageHelper usageHelper = new UsageHelper();
			usageHelper.showUsage();
			return false;
		}
		else
			return true;

	}

}
