package org.console.ui;
import org.product.constants.*;
/**
 * Hello world!
 *
 */
public class ConsoleUI 
{
	
	private static int displayColumnCount=100;   
    
    public static  void displayNewLine(){
    	System.out.println();
    	}
    }
    
    public static void displayInfo(String content){
    	System.out.println();
    	String lineBeginninAndEnd=" ";
    	int contentLength = content.length();
    	System.out.print(lineBeginninAndEnd);
    	char whiteSpaceCharacterToPrint = ' ';
    		//fill with the spaces with dotted
    			System.out.print(content);
    	System.out.print(lineBeginninAndEnd);
    }
        
}
