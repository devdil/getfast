package org.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.product.constants.CommandLineArgs;

public class Validator {

	public static boolean areCommandLineArgsValid(String[] commandLineArgsFromTheCommandLine) {

		boolean  commandLineArgMatch = false;
		boolean  atleastOneMismatch = false;
		List<String> invalidListofCommandLineArgs = new ArrayList<String>();
		for (String commandLineArg : commandLineArgsFromTheCommandLine) {

			String commanLineArgKey = commandLineArg.split("=")[0].replaceAll("--","");
			for (CommandLineArgs commandLineArgConstant : CommandLineArgs.values()) {
				if (commanLineArgKey.equalsIgnoreCase(commandLineArgConstant.commandLineArgAbbreviated())) {
					commandLineArgMatch = true;
					break;
				}
			}
			
			if (!commandLineArgMatch) {
				invalidListofCommandLineArgs.add(commandLineArg);
				if (!commandLineArgMatch)
					atleastOneMismatch = true;
			}
		
			commandLineArgMatch = false;
			
		}
		if (atleastOneMismatch) {
			UsageHelper usageHelper = new UsageHelper();
			usageHelper.showUsage();
			return false;
		}
		else
			return true;

	}

}
