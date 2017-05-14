package org.console.ui;
import org.product.constants.*;
/**
 * Hello world!
 *
 */
public class ConsoleUI 
{
	
	private static int displayColumnCount=100;
	private static String themeType="#";
	
    public static void main( String[] args )
    {
    	displayNewLineWithPound();    	
    	displayInfo("ss","left",true,"Product-Version : "+ProductConstants.PRODUCT_VERSION);
    	displayNewLineWithPound();
    }
    
    
    public static  void displayNewLineWithPound(){
    	System.out.println();
    	for(int i=1;i<=displayColumnCount;i++){
    		System.out.print("#");
    	}
    }
    
    public static void displayInfo(String type,String alignment,boolean spaceWithdashed, String content){
    	System.out.println();
    	String lineBeginninAndEnd="# ";
    	int contentLength = content.length();
    	System.out.print(lineBeginninAndEnd);
    	int whiteSpaces = displayColumnCount - contentLength - lineBeginninAndEnd.length()-1;
    	char whiteSpaceCharacterToPrint = (spaceWithdashed == true) ? '-' : ' ';
    		//fill with the spaces with dotted
    	
    		if (type == "newLine"){
    			for(int i=1;i<=displayColumnCount;i++){
    				System.out.print(themeType);
    			}
    		}

    		else if (alignment.equalsIgnoreCase("left")){
    			System.out.print(content);
    			for(int i=1;i<=whiteSpaces;i++){
    				System.out.print(whiteSpaceCharacterToPrint);
    			}
    		}
    	System.out.print(lineBeginninAndEnd);
    	}
        
    	
    }
