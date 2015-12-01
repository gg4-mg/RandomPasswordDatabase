package NewDatabase;

public class DatabaseLookup {
	
	/**
	 * This class creates a new database given the patterns from the 
	 * n-grams in step 2. These passwords are then given a probability,
	 * and organized accordingly!
	 * @param args
	 */
	public static void main(String[] args) {
		/*Because we are using n-grams, our state starts with three spaces or 
		 * special characters. We know that when searching for characters, if 
		 * we ever find a special character we terminate the string, and start
		 * a new line.
		 * 1)Our program will start without a loop and create one password...
		 */
		String startingState = "¢¢¢";
		String finalString = "";
		while(true){
			//The following produces a random number between 0 & 1.
			double random = Math.random();
			//Now we need to find i through binary search...
		}
	}
}
