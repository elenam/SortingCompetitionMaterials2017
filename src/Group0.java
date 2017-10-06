import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Group0 {

	public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		// testing the comparator:
		PrimesComparator.testPrimeFactors();
		
		if (args.length < 2) {
			System.out.println("Please run with two command line arguments: input and output file names");
			System.exit(0);
		}

		String inputFileName = args[0];
		String outFileName = args[1];
		
		// read as strings
		String [] data = readData(inputFileName);
		
		String [] toSort = data.clone();
		
		String [] sorted = sort(toSort);
		
		//printArray(sorted, 100);
		
		toSort = data.clone();
		
		Thread.sleep(10); //to let other things finish before timing; adds stability of runs

		long start = System.currentTimeMillis();
		
		sorted = sort(toSort);
		
		long end = System.currentTimeMillis();
		
		System.out.println(end - start);
		
		writeOutResult(sorted, outFileName);

	}
	
	// YOUR SORTING METHOD GOES HERE. 
	// You may call other methods and use other classes. 
	// Note: you may change the return type of the method. 
	// You would need to provide your own function that prints your sorted array to 
	// a file in the exact same format that my program outputs
	private static String[] sort(String[] toSort) {
		Arrays.sort(toSort, new PrimesComparator());
		return toSort;
	}
	
	private static String[] readData(String inFile) throws FileNotFoundException {
		ArrayList<String> input = new ArrayList<>();
		Scanner in = new Scanner(new File(inFile));
		
		while(in.hasNext()) {
			input.add(in.next());
		}
				
		in.close();
		
		// the string array is passed just so that the correct type can be created
		return input.toArray(new String[0]);
	}
	
	private static void writeOutResult(String[] sorted, String outputFilename) throws FileNotFoundException {

		PrintWriter out = new PrintWriter(outputFilename);
		for (String n : sorted) {
			out.println(n);
		}
		out.close();

	}
	
	private static class PrimesComparator implements Comparator<String> {

		@Override
		public int compare(String str1, String str2) {
			long n = new Long(str1);
			long m = new Long(str2);
			
			long product1 = productOfPrimeFactors(n);
			long product2 = productOfPrimeFactors(m);
			
			int result = 0;
			if (product1 < product2) {
				result = -1;
			} else if (product1 > product2) {
				result = 1;
			} else if (n < m) {
				result = -1;				
			} else if (n > m) {
				result = 1;
			}
			
			return result;	
		}
		
		// Takes a long number and returns the product of its up to two 
		// smallest prime factors
		private static long productOfPrimeFactors(long n) {
			long prime1 = 1;
			long prime2 = 1;
			long bound = (long) Math.sqrt(n) + 1;
			
			for (int i = 2; i <= bound; ++i) {
				if ((n % i) == 0) { // the first found factor must be prime
					if (prime1 == 1) {
						prime1 = i;
					} else { // the second found factor is a prime or a power of the first one
						if (i % prime1 != 0) { // now we know it's a prime
							prime2 = i;
							break;
						}
					}
				}
			}

			// if we didn't find any prime factors, the number itself must be prime
			if (prime1 == 1 && prime2 == 1) {
				prime1 = n;
			} else if (prime2 == 1)	{ // if we have only one prime, the other one may be larger than the square root,
									// but only if it's not a power of the other prime
				long candidate = n / prime1;
				while (candidate % prime1 == 0) {
					candidate = candidate / prime1; 
				}
				prime2 = candidate;
			}		
			
			return prime1 * prime2;
		}
		
		public static void testPrimeFactors() {
			if (productOfPrimeFactors(8) != 2) {
				System.out.println("fails on 8");
			}
			if (productOfPrimeFactors(27) != 3) {
				System.out.println("fails on 27");
			}
			if (productOfPrimeFactors(121) != 11) {
				System.out.println("fails on a square: 121");
			}
			if (productOfPrimeFactors(20) != 10) {
				System.out.println("fails on 20");
			}
			if (productOfPrimeFactors(7901) != 7901) {
				System.out.println("fails on prime: 7901");
			}			
			if (productOfPrimeFactors((new Long("1000000000000")).longValue()) != 10) {
				System.out.println("fails on 1000000000000");
			}
			if (productOfPrimeFactors(55852169) != 55852169) {
				System.out.println("fails on product of primes: 55852169");
				System.out.println(productOfPrimeFactors(55852169));
			}
			if (productOfPrimeFactors(446817352) != 14138) { // 8 * 7069 * 7901, so product = 2 * 7069 = 14138
				System.out.println("fails on 446817352");
				System.out.println(productOfPrimeFactors(446817352));
			}
			if (productOfPrimeFactors(292) != 146) { // 8 * 7069 * 7901, so product = 2 * 7069 = 14138
				System.out.println("fails on 292");
				System.out.println(productOfPrimeFactors(292));
			}
			
			if (productOfPrimeFactors(1) != 1) { // definition for 1
				System.out.println("fails on 1");
				System.out.println(productOfPrimeFactors(1));
			}
		}
	}

}
