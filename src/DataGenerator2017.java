import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Random;

public class DataGenerator2017 {
	/**
	 * See Readme for an overview of the data generating process. The result is
	 * being written to the file given as the first command line argument.
	 * 
	 * @param args
	 * 
	 * 
	 * 
	 *            Author: Elena Machkasova
	 */

	// relative probabilities of each length up to maxLength; add up to 1500:
	private static int [] ranges = {10, 45, 100, 180, 250, 250, 200, 100, 80, 70, 60, 50, 40, 30, 20, 10, 5};
	private static int maxLength = 17;
	private static int [] thresholds;
	private static Random rand = new Random();

	public static void main(String[] args) throws FileNotFoundException {

		String filename = "nofile";
		PrintWriter out = null;
		
		// Reading main args
		
		// if there is a file specified, open a PrintWriter
		if (args.length >= 1) {
			filename = args[0];			
			out = new PrintWriter(filename);
		}
				
		// add the command line arguments to take the number of elements - maybe? 

		if (!validRanges()) {
			throw new RuntimeException("Invalid ranges");
		}
		
		computeTresholds();
		
		generateData(400, out);
		
		if (out != null) {
			out.close();
		}
	
	}
	

	// Methods for generating the test data: 
	
	private static int generateLength() {
		
		int r = rand.nextInt(1500);
		
		// find the slot that it fits in:
		for (int i = 0; i < thresholds.length - 1; ++i) { // checking until the second-to-last since the last is 1500
			if (r < thresholds[i]) {
				return (i + 1); // < thresholds[0] corresponds to length = 1, etc. 
			}
		}
		
		return maxLength; // a.k.a. thresholds.length + 1
	}
	
	// generate data and print it to a file:
	
	private static void generateData(int numElements, PrintWriter out) {
		// numbers are generated digit-by-digit.
		// An array to store the digits:
		int[] digits = new int[maxLength];

		for (int i = 0; i < numElements; ++i) {
			// generate length:
			int length = generateLength();

			// The leading digit isn't 0:
			digits[0] = makeNonZeroDigit();
			
			if(digits[0] == 0) {
				throw new RuntimeException("Leading digit is zero");
			}
			
			// The rest can be zero:
			for (int j = 1; j < length; ++j) {
				digits[j] = makeAnyDigit();
			}
			
			// print the result:
			if (out == null) { // no file specified, so print to standard output
				for (int j = 0; j < length; ++j) {
					System.out.print(digits[j]);
				}
				System.out.println();
			} else { // print to the print writer:
				for (int j = 0; j < length; ++j) {
					out.print(digits[j]);
				}
				out.println();
			}
		}
	}
	
	private static int makeNonZeroDigit() {
		return  1 + rand.nextInt(9);
	}
	
	private static int makeAnyDigit() {
		return rand.nextInt(10);
	}
	
	// Helper methods to check validity of ranges and generate thresholds: 
	
	private static boolean validRanges() {
		int sum = 0;
		for (int n: ranges) {
			sum += n;
		}
		return (ranges.length == maxLength) && (sum == 1500);
	}
	
	private static void computeTresholds() {
		thresholds = new int[ranges.length];
		
		thresholds[0] = ranges[0];
		//System.out.println("thresholds[0] = " + thresholds[0] );
		for (int i = 1; i < ranges.length; ++i) {
			thresholds[i] = thresholds[i-1] + ranges[i];
			//System.out.println("thresholds[" + i + "] = " + thresholds[i] );
		}
		
		// just checking:
		//System.out.println(thresholds[thresholds.length - 1]);
	}
	

}
