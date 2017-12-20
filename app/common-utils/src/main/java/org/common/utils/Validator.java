package org.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.product.constants.CommandLineArgs;

public class Validator {

	public static boolean areCommandLineArgsValid(String[] args) {

		boolean  foundMatch = false;
		boolean  invalidMatchFound = false;
		List<String> invalidListofCommandLineArgs = new ArrayList<String>();
		for (String arg : args) {

			for (CommandLineArgs commandLineArgConstant : CommandLineArgs.values()) {
				if (arg.equalsIgnoreCase(commandLineArgConstant.commandLineArgAbbreviated())) {
					foundMatch = true;
					break;
				}
			}
			
			if (!foundMatch) {
				invalidListofCommandLineArgs.add(arg);
				if (!invalidMatchFound)
					invalidMatchFound = true;
			}
		
			foundMatch = false;
			
		}
		
		
		if (invalidMatchFound) {
			UsageHelper usageHelper = new UsageHelper();
			usageHelper.showUsage();
			return false;
		}
		
		else
			return true;

	}

}
