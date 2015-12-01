package RandomDatabase;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.SortedSet;
import java.util.TreeSet;

public class RandomDatabase {

	/**
	 * This main method reads the database that has approximately 14 million passwords
	 * and it reads 100,000 of them randomly into a text file, so that we can later
	 * use these passwords to generate a database with patterns based on the passwords
	 * we collect from here...
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		
        Scanner userInput = new Scanner( System.in ); // to read the query
        String sizeOfDatabase; //This will be 100,000 for our project!
        int numOfPasswords = 0; //This will be used when we parse the string to an int
        
        // prompt user for input
        System.out.print("Enter <sizeOfDatabase> without spaces:");
        
        // get query particulars
        sizeOfDatabase = userInput.next( );
        
        // close the input stream
        userInput.close();
        
        FileInputStream database;
        //Here we want to access the file...
        try {
        	database = new FileInputStream("Files/rockyou.clean.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        
        //Parse the input into an integer
        try {
        	numOfPasswords = Integer.parseInt(sizeOfDatabase);
        } catch (NumberFormatException e){
        	System.out.println("NumberFormatException: the number is not valid");
        }
        
        //////////////////Get an array of random lines from 1-sizeOfDatabase////////////////
        Random rand = new Random();
        int max = 14338588; //This is the number of passwords in the .txt file
        int min = 0; //This is the first line that has a password...
        // nextInt is normally exclusive of the top value,
        // so add 1 to make it inclusive
        int randomNum;
        //This list will store the line number we want to access in our database
        SortedSet<Integer> list = new TreeSet<Integer>();
        ArrayList<String> randomPasswordDatabase = new ArrayList<String>();
        while (list.size() < numOfPasswords){
        	randomNum = rand.nextInt((max - min) + 1) + min;
        	list.add(randomNum);
        }
        
        /*Now our **list** contains all of the lines that we want to access from 
          the database in order and without duplicates! */
        String lineVariable;
        java.util.Iterator<Integer> iterate = list.iterator();
        Integer lineDestination = 0;
        int lineNum = 0;
        try {
        	BufferedReader databaseReader = new BufferedReader(
            		new InputStreamReader(database,"UTF8"));
        	//We have to use the line number as the next input...
    		while ((lineVariable = databaseReader.readLine()) != null){
	            //System.out.println("Entered the " + lineNum + " line.");
    			if (lineNum == 0){
    				lineDestination = iterate.next(); 
    			}
        		/*lineVariable currently stores the line if it lineNum
        		 * matches lineDestination */
        		if (lineNum == lineDestination){
        			/*Here we know that the line variable contains the password that 
        			 * we want to keep, so we should store this in a new array... 
        			 */
        			randomPasswordDatabase.add(lineVariable);
		            //System.out.println(lineVariable);
		            if (iterate.hasNext())
		            	lineDestination = iterate.next();
		            else 
		            	break;
        		}
        		lineNum++;
    		}
        	//We must close the database!
        	databaseReader.close();
        } catch (Exception e){
        	System.out.println("Exception: we cannot skip to the proper line...");
        	return;
        }
	        
        //We want to see if we returned to the end of the file...
        System.out.println("The file was read properly.");
        
        ////////////////Check randomPasswordDatabase for the correct passwords/////////////
        try {
	        //FileWriter writer = new FileWriter("Files/output.txt"); 
	        BufferedWriter writer = new BufferedWriter(new FileWriter("Files/randomPasswords.txt", true));
	        for(String str: randomPasswordDatabase) {
	          writer.write(str);
	          writer.newLine();
	        }
	        writer.close();
        } catch (Exception e){
        	System.out.println("Exception: we cannot write to the file.");
        }
	}
}
