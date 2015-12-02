package FourGram;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;

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
	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		//We first write code that tests whether we correctly create four tables
		//1)Here we create a table of uni-grams based on the passwords as input...
		double numberOfLines = 0;
		double lineSize = 0;
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
        String set1 = "";
        String set2 = "";
        String set3 = "";
        HashMap<String, Percentages> uniGram = new HashMap<String, Percentages>();
        HashMap<String, HashMap<String, Percentages>> diGram = new HashMap<String, HashMap<String, Percentages>>();
        HashMap<String, HashMap<String, Percentages>> triGram = new HashMap<String, HashMap<String, Percentages>>();
        HashMap<String, HashMap<String, Percentages>> quadGram = new HashMap<String, HashMap<String, Percentages>>();
        //The following will be used so that we can create a database of passwords using linkedHashMap's
        HashMap<String, Double> uniDB = new LinkedHashMap<String, Double>();
        HashMap<String, LinkedHashMap<String, Double>> diDB = new HashMap<String, LinkedHashMap<String, Double>>();
        HashMap<String, LinkedHashMap<String, Double>> triDB = new HashMap<String, LinkedHashMap<String, Double>>();
        HashMap<String, LinkedHashMap<String, Double>> quadDB = new HashMap<String, LinkedHashMap<String, Double>>();
        
        try {
        	BufferedReader databaseReader = new BufferedReader(
            		new InputStreamReader(database, "UTF8"));
			//We want to anaylze every password from the text file...
    		while ((lineVariable = databaseReader.readLine()) != null){
    			numberOfLines++;
    			/*We have to store a linkedList of two different strings */
    			//This lineVariable is our string, now we perform our uni-grams
    			for(char c : lineVariable.toCharArray()) {
    			    // process c
    				String cString = Character.toString(c);
    				//////////////This is for unigrams////////////
    				if (uniGram.containsKey(cString)){
    					//Increase count
    					uniGram.get(cString).updateCount();
    				} else {
    				//If not, then we add this as a string
    				uniGram.put(cString, new Percentages());
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
	    						diGram.get(set1).get(cString).updateCount();
	    					} else {
	    						//Then we have to create the transition...
	    	    				diGram.get(set1).put(cString, new Percentages());
	    					}
	    				} else {
	    				//If not, then we add this as a string
	    				diGram.put(set1, new HashMap<String, Percentages>());
	    				diGram.get(set1).put(cString, new Percentages());
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
	    						triGram.get(triGramSet).get(cString).updateCount();
	    					} else {
	    						//Then we have to create the transition...
	    						triGram.get(triGramSet).put(cString, new Percentages());
	    					}
	    				} else {
	    					//If not, then we add this as a string
	    					triGram.put(triGramSet, new HashMap<String, Percentages>());
	    					triGram.get(triGramSet).put(cString, new Percentages());
	    				}
    				}
    				//////////////This is for quadgrams////////////
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
	    						quadGram.get(quadGramSet).get(cString).updateCount();
	    					} else {
	    						//Then we have to create the transition...
	    						quadGram.get(quadGramSet).put(cString, new Percentages());
	    					}
	    				} else {
	    					//If not, then we add this as a string
	    					quadGram.put(quadGramSet, new HashMap<String, Percentages>());
	    					quadGram.get(quadGramSet).put(cString, new Percentages());
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
        
        /* Before we print these values to the screen, we have to learn how to store the
         * probabilities assoiciated with each password alongside the count...
         *
         * 1)Scroll through each set in quad-grams and find the corresponding count of trigrams with
         *   the set and transition, and create the probability based on the number of occurences of
         *   this string divided by the number of occurences of the string-1.
         * 2)^^The same for tri-grams, digrams and unigrams...
         */
        //We need a special symbol.... we will use `
        String symbol = "¢";
        //Quadgrams
        for (Map.Entry<String, HashMap<String, Percentages>> letterEntry : triGram.entrySet()) {
        	for (Map.Entry<String, Percentages> nameEntry : letterEntry.getValue().entrySet()) {
            	String newSet = letterEntry.getKey() + nameEntry.getKey();
            	/*We now have to check if this string is a set in quadgrams... if it is, then
            	 * we must change the percentage of quadrams based on the counts of each, and 
            	 * if it isn't, then we have to add a special symbol for the transition
            	 * associated with this set in Quadgrams */
            	if (quadGram.containsKey(newSet)){
            		/*Then we update the percentages. We know that letterEntry.getKey has
            		 * the associated count for ca->cb-1, so now we only need to find the
            		 * count associated with quadgrams in this case. */
                	double denominator = triGram.get(letterEntry.getKey()).get(nameEntry.getKey()).getCount();
                	for (Map.Entry<String, Percentages> nextTable : quadGram.get(newSet).entrySet()) {
                		double numerator = nextTable.getValue().getCount();
                    	nextTable.getValue().updatePercentage(numerator/denominator);
                	}
            	} else {
            		//we have to add a special symbol for this transition!
					quadGram.put(newSet, new HashMap<String, Percentages>());
					quadGram.get(newSet).put(symbol, new Percentages());            	
				}	
        	}
        }
        //tri-grams
        for (Map.Entry<String, HashMap<String, Percentages>> letterEntry : diGram.entrySet()) {
        	for (Map.Entry<String, Percentages> nameEntry : letterEntry.getValue().entrySet()) {
            	String newSet = letterEntry.getKey() + nameEntry.getKey();
            	/*We now have to check if this string is a set in quadgrams... if it is, then
            	 * we must change the percentage of quadrams based on the counts of each, and 
            	 * if it isn't, then we have to add a special symbol for the transition
            	 * associated with this set in Quadgrams */
            	if (triGram.containsKey(newSet)){
            		/*Then we update the percentages. We know that letterEntry.getKey has
            		 * the associated count for ca->cb-1, so now we only need to find the
            		 * count associated with quadgrams in this case. */
                	double denominator = diGram.get(letterEntry.getKey()).get(nameEntry.getKey()).getCount();
                	for (Map.Entry<String, Percentages> nextTable : triGram.get(newSet).entrySet()) {
                		double numerator = nextTable.getValue().getCount();
                    	nextTable.getValue().updatePercentage(numerator/denominator);
                	}
            	} else {
            		//we have to add a special symbol for this transition!
					triGram.put(newSet, new HashMap<String, Percentages>());
					triGram.get(newSet).put(symbol, new Percentages());            	
				}	
        	}
        }
        //For digrams
        for (Map.Entry<String, Percentages> letterEntry : uniGram.entrySet()) {
        	String newSet = letterEntry.getKey();
        	if (diGram.containsKey(newSet)){
            	double denominator = uniGram.get(letterEntry.getKey()).getCount();
            	for (Map.Entry<String, Percentages> nextTable : diGram.get(newSet).entrySet()) {
            		double numerator = nextTable.getValue().getCount();
                	nextTable.getValue().updatePercentage(numerator/denominator);
            	}
        	} else {
        		//we have to add a special symbol for this transition!
				diGram.put(newSet, new HashMap<String, Percentages>());
				diGram.get(newSet).put(symbol, new Percentages());            	
			}	
        }
        
        //For unigrams
        double characterCount = 0;
        for (Map.Entry<String, Percentages> letterEntry : uniGram.entrySet()) {
        	/*I first need to iterate through the whole set and get the count
        	  of each character! */
        	characterCount += letterEntry.getValue().getCount();
        }
    	//The following contains the transition that we want to check inside the uni-grams
    	for (Map.Entry<String, Percentages> nameEntry : uniGram.entrySet()) {
        	//At this point we have the probability of ca->cb-1
        	double numerator = nameEntry.getValue().getCount();
        	//Now we have p(ca->cb)
        	nameEntry.getValue().updatePercentage(numerator/characterCount);
        	//Now we have the percentage stored for each value. :D
    	}
        /////////////////////////////////////////////////////////////////////////////
        ////////////////////////////printing to the screen///////////////////////////
        //////////////////////////////////Uni-Grams//////////////////////////////////
    	//The next step is printing to the screen based on the probabilities...
    	
        try {
	        BufferedWriter writer = new BufferedWriter(new FileWriter("Files/unigrams.txt", true));
	        //create a new map that has no Percentages objects
	        HashMap<String, Double> unigramTemp = new HashMap<String, Double>();
	        Iterator<Map.Entry<String, Percentages>> unigramIterator = uniGram.entrySet().iterator();
	        while (unigramIterator.hasNext()){
	            Map.Entry<String, Percentages> entry = unigramIterator.next();
	        	unigramTemp.put(entry.getKey(),entry.getValue().getPercentage());
	        }
	        @SuppressWarnings("unchecked")
			Map<String, Double> map = sortByValues(unigramTemp);

	        Double cumulativePercentage = 0.0;
	        Iterator<Map.Entry<String, Double>> uniGramIt = map.entrySet().iterator();
	        while (uniGramIt.hasNext()) {
	            Map.Entry<String, Double> entry = uniGramIt.next();
	            cumulativePercentage += entry.getValue();
	            //later we may need to update this to use this value if we underflow...
	            uniGram.get(entry.getKey()).updateCumulativePercentage(cumulativePercentage);
	            uniDB.put(entry.getKey(), cumulativePercentage);
	            //System.out.println("Key = "+entry.getKey()+", Value = "+entry.getValue()+" "+cumulativePercentage);
	            String str = entry.getKey()+" "+entry.getValue()+" "+cumulativePercentage;
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
	        for (Map.Entry<String, HashMap<String, Percentages>> letterEntry : diGram.entrySet()) {
            	//System.out.println("Set = " + letterEntry.getKey());
		        //create a new map that has no Percentages objects
		        HashMap<String, Double> digramTemp = new HashMap<String, Double>();
		        for (Map.Entry<String, Percentages> digramSort : letterEntry.getValue().entrySet()){
		        	digramTemp.put(digramSort.getKey(),digramSort.getValue().getPercentage());
		        }
		        @SuppressWarnings("unchecked")
				Map<String, Double> map = sortByValues(digramTemp);
		        diDB.put(letterEntry.getKey(), new LinkedHashMap<String, Double>());
		        
		        Double cumulativePercentage = 0.0;
		        Iterator<Map.Entry<String, Double>> uniGramIt = map.entrySet().iterator();
		        while (uniGramIt.hasNext()) {
		            Map.Entry<String, Double> entry = uniGramIt.next();
		            cumulativePercentage += entry.getValue();
		            //later we may need to update this to use this value if we underflow...
		            diGram.get(letterEntry.getKey()).get(entry.getKey()).updateCumulativePercentage(cumulativePercentage);
			        diDB.get(letterEntry.getKey()).put(entry.getKey(), cumulativePercentage);
		            String str = letterEntry.getKey() + " " + entry.getKey() + " " + entry.getValue()+" "+
		            			 cumulativePercentage;
	            	//System.out.println("      Transition = " + entry.getKey() + ", and percent = " + entry.getValue());
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
	        for (Map.Entry<String, HashMap<String, Percentages>> letterEntry : triGram.entrySet()) {
            	//System.out.println("Set = " + letterEntry.getKey());
		        //create a new map that has no Percentages objects
		        HashMap<String, Double> trigramTemp = new HashMap<String, Double>();
		        for (Map.Entry<String, Percentages> trigramSort : letterEntry.getValue().entrySet()){
		        	trigramTemp.put(trigramSort.getKey(),trigramSort.getValue().getPercentage());
		        }
		        @SuppressWarnings("unchecked")
				Map<String, Double> map = sortByValues(trigramTemp);
		        //We add this list to the following DB for later
		        triDB.put(letterEntry.getKey(), new LinkedHashMap<String, Double>());
		        
		        Double cumulativePercentage = 0.0000000000000000;
		        Iterator<Map.Entry<String, Double>> triGramIt = map.entrySet().iterator();
		        while (triGramIt.hasNext()) {
		            Map.Entry<String, Double> entry = triGramIt.next();
		            cumulativePercentage += entry.getValue();
		            //later we may need to update this to use this value if we underflow...
		            triGram.get(letterEntry.getKey()).get(entry.getKey()).updateCumulativePercentage(cumulativePercentage);
			        triDB.get(letterEntry.getKey()).put(entry.getKey(), cumulativePercentage);
		            String str = letterEntry.getKey() + " " + entry.getKey() + " " + entry.getValue()+" "+
		            			 cumulativePercentage;
	            	//System.out.println("      Transition = " + entry.getKey() + ", and percent = " + entry.getValue());
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
	        for (Map.Entry<String, HashMap<String, Percentages>> letterEntry : quadGram.entrySet()) {
            	//System.out.println("Set = " + letterEntry.getKey());
		        //create a new map that has no Percentages objects
		        HashMap<String, Double> quadgramTemp = new HashMap<String, Double>();
		        for (Map.Entry<String, Percentages> quadgramSort : letterEntry.getValue().entrySet()){
		        	quadgramTemp.put(quadgramSort.getKey(),quadgramSort.getValue().getPercentage());
		        }
		        @SuppressWarnings("unchecked")
				Map<String, Double> map = sortByValues(quadgramTemp);
		        //We add this list to the following DB for later
		        quadDB.put(letterEntry.getKey(), new LinkedHashMap<String, Double>());
		        
		        Double cumulativePercentage = 0.0;
		        Iterator<Map.Entry<String, Double>> quadGramIt = map.entrySet().iterator();
		        while (quadGramIt.hasNext()) {
		            Map.Entry<String, Double> entry = quadGramIt.next();
		            cumulativePercentage += entry.getValue();
		            //later we may need to update this to use this value if we underflow...
		            quadGram.get(letterEntry.getKey()).get(entry.getKey()).updateCumulativePercentage(cumulativePercentage);
			        quadDB.get(letterEntry.getKey()).put(entry.getKey(), cumulativePercentage);
		            String str = letterEntry.getKey() + " " + entry.getKey() + " " + entry.getValue()+" "+
		            			 cumulativePercentage;
	            	//System.out.println("      Transition = " + entry.getKey() + ", and percent = " + entry.getValue());
		            writer.write(str);
		            writer.newLine();
		        }
	        }
	        writer.close();
        } catch (Exception e){
        	System.out.println("Exception: we cannot write to the file.");
        }
        //We want to see if we returned to the end of the file...
        System.out.println("Porgram executed properly.");
        
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        /////////////////////////////Here is our database Creation method//////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        ///////////////////////////////////////////////////////////////////////////////////////////////////
        //We will store all these vales in a new map for what we do below.
        HashMap<String, Double> newDatabase = new HashMap<String, Double>();
        LinkedHashMap<String,String> Database = new LinkedHashMap<String,String>();
		Random randomGenerator = new java.util.Random(System.currentTimeMillis());
		lineSize = numberOfLines;
		while (numberOfLines>=0){
			String startingState = "¢¢¢";
			String finalString = "";
			Double stringProbability = 1.0;
			//The count will store the size of the set to decide which table to search from
			int count = 0;
			while(true){
				//The following produces a random number between 0 & 1.
				Double random = randomGenerator.nextDouble();
				Double tempProbability = 1.0;
				//System.out.println(random);
				//System.out.println("The random is: " + random);
				String set = "";
				String transition = "";
				count = 0;
				//Now we need to find i through binary search...
				for(char c : startingState.toCharArray()) {
					if (c != '¢'){
						//This will create the set that we have to search for...
						count += 1;
						if(count > 3){
							set = set.substring(0) + Character.toString(c);
							assert(set.length()<=3 && set.length()!= 0);
						}
						else
							set = set + Character.toString(c);
					}
				}
				//Now we have the set based on the startingState string...
				if (count == 0){
			        for (Map.Entry<String, Double> letterEntry : uniDB.entrySet()) {
			        	//I first need to iterate through the whole set and get the count of each character!
			        	if (letterEntry.getKey() == "¢"){
			        		transition = letterEntry.getKey();
			        		break;
			        	}else if (letterEntry.getValue() > random){
			        		transition = letterEntry.getKey();
			        		break;
			        	}
			        }
				} else if (count == 1){
			        for (Map.Entry<String, Double> entry : diDB.get(set).entrySet()) {
			        	if (entry.getKey() == "¢"){
			        		transition = entry.getKey();
			        		break;
			        	} else if (entry.getValue() > random){
			        		transition = entry.getKey();
			        		break;
			        	}
			        }
				} else if (count == 2){
			        for (Map.Entry<String, Double> entry : triDB.get(set).entrySet()) {
			        	if (entry.getKey() == "¢"){
			        		transition = entry.getKey();
			        		break;
			        	} else if (entry.getValue() > random){
			        		transition = entry.getKey();
			        		break;
			        	}
			        }
				} else{
			        for (Map.Entry<String, Double> entry : quadDB.get(set).entrySet()) {
			        	if (entry.getKey() == "¢"){
			        		transition = entry.getKey();
			        		break;
			        	} else if (entry.getValue() > random){
			        		transition = entry.getKey();
			        		break;
			        	}
			        }
				}
				//System.out.println("The transition is: " + transition);
				if (transition == "¢" || transition == "")
					break;
				else{
					//Here we update the probability
					if (count == 0){
						tempProbability = uniGram.get(transition).getPercentage();
					} else if (count == 1){
						tempProbability = diGram.get(set).get(transition).getPercentage();
					} else if (count == 2){
						tempProbability = triGram.get(set).get(transition).getPercentage();
					} else if (count == 3){
						tempProbability = quadGram.get(set).get(transition).getPercentage();
					} else {
						assert(false);
					}
					finalString = finalString + transition;
					startingState = startingState.substring(1) + transition;
					stringProbability *= tempProbability;
				}
			}
			System.out.println("The final string is: " + finalString);
			System.out.println("The probability for the string is: " + stringProbability);
			//Here we add the string, the probability, and the associated accumulative probability
			newDatabase.put(finalString, stringProbability);
			finalString = "";
			numberOfLines--;
		}
		Double cumulativeProbability = 0.0;
		HashMap<String, Double> finalDatabase = sortByValues(newDatabase);
        Iterator<Map.Entry<String, Double>> addCumulProb = finalDatabase.entrySet().iterator();
        while (addCumulProb.hasNext()) {
            Map.Entry<String, Double> entry = addCumulProb.next();
            cumulativeProbability += 1/(lineSize*entry.getValue());
        	Database.put(entry.getKey(), entry.getValue() + " " + cumulativeProbability);
        }
        //Now the Database contains the right string and probability!!! WOOOHOOO
        //This is our last print statement to print out the database!!!
        try {
	        BufferedWriter writer = new BufferedWriter(new FileWriter("Files/finalDatabase.txt", true));
	        for (Map.Entry<String, String> letterEntry : Database.entrySet()) {
	            String str = letterEntry.getKey() + " " + letterEntry.getValue();
	            writer.write(str);
	            writer.newLine();
	        }
	        writer.close();
        } catch (Exception e){
        	System.out.println("Exception: we cannot write to the file.");
        }
	}
	
	/**
	 * This method helps us to create an ordered mapping so that we can compute the 
	 * cumulative percentage for each transition in our n-gram tables.
	 * @param map
	 * @return A descending ordered map
	 */
	  @SuppressWarnings({ "unchecked", "rawtypes" })
	private static HashMap sortByValues(HashMap map) { 
	       List list = new LinkedList(map.entrySet());
	       // Defined Custom Comparator here
	       Collections.sort(list, new Comparator() {
	            public int compare(Object o1, Object o2) {
	               return ((Comparable) ((Map.Entry) (o2)).getValue())
	                  .compareTo(((Map.Entry) (o1)).getValue());
	            }
	       });

	       // Here I am copying the sorted list in HashMap
	       // using LinkedHashMap to preserve the insertion order
	       HashMap sortedHashMap = new LinkedHashMap();
	       for (Iterator it = list.iterator(); it.hasNext();) {
	              Map.Entry entry = (Map.Entry) it.next();
	              sortedHashMap.put(entry.getKey(), entry.getValue());
	       } 
	       return sortedHashMap;
	  }

}