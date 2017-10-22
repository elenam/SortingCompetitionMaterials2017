
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Group4 {

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
			
			long product1 = primeFactors(n);
			long product2 = primeFactors(m);
			
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
		static long primeFactors(long n) {
			long p = 0;
			long p2 = 0;
			if(n==1 ) return n;
			long n1 = n;
			int counter = 0;
			//check with 2.
			while (n%2 == 0)
			{   
				if(p==0){
					p = 2;
				}
				n = n/2;
				counter++;
			}
			//if it's a perfect square, it will always be 2
			if(n1 == Math.pow(2, counter)){
				return 2;
			}
			counter = 0;
			// check with 3 to sqrt(n) numbers.
			for (int i = 3; i <= (long) Math.sqrt(n)+1; i = i+2)
			{
				 if(n%i==0){
					 //if p1 isn't set, set it
					if(p == 0){
						p = i;
					}else {
						p2 =i;
						//can break because both factors are set
						break;
					}
				}
				while (n%i == 0)
				{
					n = n/i;
				}
			}
			//if p2 was never set, it was the product of 2 primes so it will be set here
			if(p2 == 0){
				p2 = n;
			}
			
			long result = p2*p;
			//if one of the factors was zero it was a prime number, so return it
			if(result == 0){
				return n;
			}
			//map.put(n, result);
			return result;

		}
	
		
		public static void testPrimeFactors() {
//			if (primeFactors(8) != 2) {
//				System.out.println("fails on 8");
//			}
//			if (primeFactors(27) != 3) {
//				System.out.println("fails on 27");
//			}
//			if (primeFactors(121) != 11) {
//				System.out.println("fails on a square: 121");
//			}
//			if (primeFactors(20) != 10) {
//				System.out.println("fails on 20");
//			}
//			if (primeFactors(7901) != 7901) {
//				System.out.println("fails on prime: 7901");
//			}			
//			if (primeFactors((new Long("1000000000000")).longValue()) != 10) {
//				System.out.println("fails on 1000000000000");
//			}
//			if (primeFactors(55852169) != 55852169) {
//				System.out.println("fails on product of primes: 55852169");
//				System.out.println(productOfPrimeFactors(55852169));
//			}
//			if (primeFactors(446817352) != 14138) { // 8 * 7069 * 7901, so product = 2 * 7069 = 14138
//				System.out.println("fails on 446817352");
//				System.out.println(productOfPrimeFactors(446817352));
//			}
//			if (primeFactors(292) != 146) { // 8 * 7069 * 7901, so product = 2 * 7069 = 14138
//				System.out.println("fails on 292");
//				System.out.println(productOfPrimeFactors(292));
//			}
//			if (primeFactors(49) != 7) { // 8 * 7069 * 7901, so product = 2 * 7069 = 14138
//				System.out.println("fails on 49");
//				System.out.println(productOfPrimeFactors(49));
//			}
//			
//			if (primeFactors(1) != 1) { // definition for 1
//				System.out.println("fails on 1");
//				System.out.println(productOfPrimeFactors(1));
//			}
		}
	}


}