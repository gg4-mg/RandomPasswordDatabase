package FourGram;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ngrams {

	/**
	 * This main method creates four different tables that take in the random
	 * generated passwords as input, and print out to another file, the uni-gram,
	 * di-grams, tri-grams, and quad-grams in separate files. These are stored
	 * using state and transition vectors.
	 * 
	 *  An example is first used to show that the implementation works correctly,
	 *  and then the actual implementation is below.
	 * @param args
	 * @return four databases containing uni-grams, di-grams, tri-grams and quad-grams
	 */
	public static void main(String[] args) {
		//We first write code that tests whether we correctly create four tables
		//1)Here we create a table of uni-grams based on the passwords as input...
		
        FileInputStream database;
        //Here we want to access the file...
        try {
        	database = new FileInputStream("Files/randomPasswords.txt");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        /*Now we want to read each string, and calculate the n-grams associated with each.
          remember that each Set and Transition will be stored in each graph. This means
          we will store the State as a string, and the transition as a string as well. */
        String lineVariable;
        HashMap<String, Integer> uniGram = new HashMap<String, Integer>();
        String set1 = "";
        String set2 = "";
        String set3 = "";
        HashMap<String, HashMap<String, Integer>> diGram = new HashMap<String, HashMap<String, Integer>>();
        HashMap<String, HashMap<String, Integer>> triGram = new HashMap<String, HashMap<String, Integer>>();
        HashMap<String, HashMap<String, Integer>> quadGram = new HashMap<String, HashMap<String, Integer>>();
        
        try {
        	BufferedReader databaseReader = new BufferedReader(
            		new InputStreamReader(database, "UTF8"));
			//We want to anaylze every password from the text file...
    		while ((lineVariable = databaseReader.readLine()) != null){
    			/*We have to store a linkedList of two different strings */
    			//This lineVariable is our string, now we perform our uni-grams
    			for(char c : lineVariable.toCharArray()) {
    			    // process c
    				String cString = Character.toString(c);
    				//////////////This is for unigrams////////////
    				if (uniGram.containsKey(cString)){
    					//Increase count
    					uniGram.put(cString,uniGram.get(cString) + 1);
    				} else {
    				//If not, then we add this as a string
    				uniGram.put(cString, 1);
    				}
    				//////////////This is for digrams////////////
    				if (set1 != ""){
    					/*Now we are checking if the digram has set1 as a
    					 gdfadfg* c-string, which was the most previously written
    					 * character. If not, we then move into the transition 
    					 * string and check that... */
	    				if ((diGram.containsKey(set1))){
	    					//Now check for the transition...
	    					if (diGram.get(set1).containsKey(cString)){
	    						//If the transition also exists, update the number
	    	    				diGram.get(set1).put(cString,diGram.get(set1).get(cString)+1);
	    					} else {
	    						//Then we have to create the transition...
	    	    				diGram.get(set1).put(cString, 1);
	    					}
	    				} else {
	    				//If not, then we add this as a string
	    				diGram.put(set1, new HashMap<String, Integer>());
	    				diGram.get(set1).put(cString, 1);
	    				}
    				}
    				//////////////This is for trigrams////////////
    				if (set2 != ""){
    					/*Now we are checking if the digram has set1 as a
    					 * c-string, which was the most previously written
    					 * character. If not, we then move into the transition 
    					 * string and check that... */
    					String triGramSet = set2+set1;
	    				if ((triGram.containsKey(triGramSet))){
	    					//Now check for the transition...
	    					if (triGram.get(triGramSet).containsKey(cString)){
	    						//If the transition also exists, update the number
	    						triGram.get(triGramSet).put(cString,triGram.get(triGramSet).get(cString)+1);
	    					} else {
	    						//Then we have to create the transition...
	    						triGram.get(triGramSet).put(cString, 1);
	    					}
	    				} else {
	    					//If not, then we add this as a string
	    					triGram.put(triGramSet, new HashMap<String, Integer>());
	    					triGram.get(triGramSet).put(cString, 1);
	    				}
    				}
    				//////////////This is for unigrams////////////
    				if (set3 != ""){
    					/*Now we are checking if the digram has set1 as a
    					 * c-string, which was the most previously written
    					 * character. If not, we then move into the transition 
    					 * string and check that... */
    					String quadGramSet = set3+set2+set1;
	    				if ((quadGram.containsKey(quadGramSet))){
	    					//Now check for the transition...
	    					if (quadGram.get(quadGramSet).containsKey(cString)){
	    						//If the transition also exists, update the number
	    						quadGram.get(quadGramSet).put(cString,quadGram.get(quadGramSet).get(cString)+1);
	    					} else {
	    						//Then we have to create the transition...
	    						quadGram.get(quadGramSet).put(cString, 1);
	    					}
	    				} else {
	    					//If not, then we add this as a string
	    					quadGram.put(quadGramSet, new HashMap<String, Integer>());
	    					quadGram.get(quadGramSet).put(cString, 1);
	    				}
    				}
    				//////Assign the set's of characters//////////
    				set3 = set2;
    				set2 = set1;
    				set1 = cString;
    			}
    			//Reset the sets to nothing...
    			set1 = "";
    			set2 = "";
    			set3 = "";
    		}
        	//We must close the database!
        	databaseReader.close();
        } catch (Exception e){
        	System.out.println("Exception: we cannot skip to the proper line...");
        	return;
        }
        
        //////////////////////////////////Uni-Grams//////////////////////////////////
        try {
	        BufferedWriter writer = new BufferedWriter(new FileWriter("Files/unigrams.txt", true));
	        Iterator<Map.Entry<String, Integer>> uniGramIt = uniGram.entrySet().iterator();
	        while (uniGramIt.hasNext()) {
	            Map.Entry<String, Integer> entry = uniGramIt.next();
	            System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
	            String str = entry.getKey() + " " + entry.getValue().toString();
	            writer.write(str);
	            writer.newLine();
	        }
	        writer.close();
        } catch (Exception e){
        	System.out.println("Exception: we cannot write to the file.");
        }
        //////////////////////////////////Di-Grams//////////////////////////////////
        try {
	        BufferedWriter writer = new BufferedWriter(new FileWriter("Files/digrams.txt", true));
	        for (Map.Entry<String, HashMap<String, Integer>> letterEntry : diGram.entrySet()) {
	            System.out.println("Set = " + letterEntry.getKey());
	            for (Map.Entry<String, Integer> nameEntry : letterEntry.getValue().entrySet()) {
		            String str = letterEntry.getKey() + " " + nameEntry.getKey() + " " + nameEntry.getValue().toString();
	            	System.out.println("      Transition = " + nameEntry.getKey() + ", and Count = " + nameEntry.getValue());
		            writer.write(str);
		            writer.newLine();
	            }
	        }
	        writer.close();
        } catch (Exception e){
        	System.out.println("Exception: we cannot write to the file.");
        }
        //////////////////////////////////Tri-Grams//////////////////////////////////
        try {
	        BufferedWriter writer = new BufferedWriter(new FileWriter("Files/trigrams.txt", true));
	        for (Map.Entry<String, HashMap<String, Integer>> letterEntry : triGram.entrySet()) {
	            System.out.println("Set = " + letterEntry.getKey());
	            for (Map.Entry<String, Integer> nameEntry : letterEntry.getValue().entrySet()) {
		            String str = letterEntry.getKey() + " " + nameEntry.getKey() + " " + nameEntry.getValue().toString();
	            	System.out.println("      Transition = " + nameEntry.getKey() + ", and Count = " + nameEntry.getValue());
		            writer.write(str);
		            writer.newLine();
	            }
	        }
	        writer.close();
        } catch (Exception e){
        	System.out.println("Exception: we cannot write to the file.");
        }
        //////////////////////////////////Quad-Grams//////////////////////////////////
        try {
	        BufferedWriter writer = new BufferedWriter(new FileWriter("Files/quadgrams.txt", true));
	        for (Map.Entry<String, HashMap<String, Integer>> letterEntry : quadGram.entrySet()) {
	            System.out.println("Set = " + letterEntry.getKey());
	            for (Map.Entry<String, Integer> nameEntry : letterEntry.getValue().entrySet()) {
		            String str = letterEntry.getKey() + " " + nameEntry.getKey() + " " + nameEntry.getValue().toString();
	            	System.out.println("      Transition = " + nameEntry.getKey() + ", and Count = " + nameEntry.getValue());
		            writer.write(str);
		            writer.newLine();
	            }
	        }
	        writer.close();
        } catch (Exception e){
        	System.out.println("Exception: we cannot write to the file.");
        }
        
        System.out.println("///////////////////////////////////////////////");        
        //We want to see if we returned to the end of the file...
        System.out.println("Porgram executed properly.");
	}

}