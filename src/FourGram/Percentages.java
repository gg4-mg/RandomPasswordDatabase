package FourGram;

public class Percentages {
	private double count;
	private double percentage;
	private double percentageLog;
	private double cumulativePercentageLog;
	private double log2 = Math.log(2);
	
	/**
	 * This contructor creates a new Percentages object that takes three paramaters
	 * accessible by the three corresponding methods to return the values associated
	 * with each object in the Hashmap from ngrams.
	 * @param count
	 * @param percentage
	 * @param cumulativePercentage
	 */
	public Percentages(){
		this.count = 1;
		this.percentage = 0.0;
	}
	
	public void updateCount(){
		this.count += 1;
	}
	
	public double getCount(){
		return count;
	}
	
	public void updatePercentage(double percentage){
		this.percentage = percentage;
		this.percentageLog = (Math.log(percentage)/log2);
	}
	
	public double getPercentage(){
		return percentage;
	}
	
	public double getPercentageLog(){
		return percentageLog;
	}
	
	/**
	 * This will store the cumulative percentage and create the new logarithmic
	 * cumulative percentage
	 * @param cumulativePercentage in non-logarithm, standard integer (double) format
	 */
	public void updateCumulativePercentage(double cumulativePercentage){
		this.cumulativePercentageLog = cumulativePercentage;
	    //(Math.log(cumulativePercentage)/log2);
	}
	
	/**
	 * @return The logarithmic(base2) cumulative percentage!
	 */
	public double getCumulativePercentage(){
		return cumulativePercentageLog;
	}
}
