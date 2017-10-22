
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.TreeSet;


//Ben Goldstein & Aidan Wiltzius
public class Group3 {

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
		
		Long [] sorted = sort(toSort);
		
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
	
	
	/*
	private static String[] sort(String[] toSort) {
		Arrays.sort(toSort, new PrimesComparator());
		return toSort;
	}
	*/
	
	private static Long[] sort(String[] toSort) {
	//	introSort(toSort, new PrimesComparator(), (int) (2 * Math.floor(Math.log(toSort.length))));
		//TODO calculate whether finding the actual max value once
		Long[] convertedToSort = new Long[toSort.length];
		for(int i = 0; i < toSort.length; i++){
			convertedToSort[i] = Long.parseLong(toSort[i]);
		}
		long maxValue = maxElement(convertedToSort); //
		Long[] primeList = sieveOfAtkin((long)Math.ceil(Math.cbrt(maxValue)));	//sieveOfAtkin((long)Math.pow(10, 6))			
//		Long[] primeList = sieveOfAtkin(99999);					
//		System.out.println("done sieving");

		convertedToSort = bucketSort(convertedToSort);
//		System.out.println("done with first pass");
		convertedToSort = primeBucketSort(convertedToSort, primeList);
		
		return convertedToSort;
		
	}

	
	private static long maxElement(Long[] input){
		long output = 0;
		for(int i = 0; i < input.length; i++){
			if(input[i] > output){
				output = input[i];
			}
		}
		return output;
	}
	
	/*
	 * Sorting Algorithm options:
	 * Have to use comparative because data is over such a wide range so best options are:
	 * Quicksort OR Mergesort OR Timsort
	 * 
	 * Other Options:  Introsort (uses Heapsort to back up Quicksort in its bad cases?
	 * 
	 * 
	 * 
	 * 
	 * Spreadsort to order -> bucketsort (Stable) on primes? is linear but somewhat slow
	 * 
	 */
	
	
//	public static String[] introSort(String[] toSort, Comparator<String> comp, int maxDepth){
//		if(toSort.length <= 1){
//			return toSort;
//		}
//		if(maxDepth == 0){
//			heapSort(toSort, comp);
//		} else {
//			partition(toSort);
//			//introSort each half of the partition
//		}
//		
//		
//		
//		
//		
//		return toSort;
//	}
	
	//private Long[] spreadSort(Long[] input, )
	
	
//	private static Long[] countingSort(Long[] input, long maxValue){ //can be replaced with any sort and will replace with spreadsort
//		Long[] keys = new Long[(int) maxValue]; //this will bite me in the ass eventually but hopefully not today TODO fix this if you can EDIT it's today now
//		Long[] output = new Long[input.length];
//		for (long i: input){
//			keys[(int) i]++;
//		}
//		
//		long total = 0;
//		for(int i = 0; i < maxValue; i++){
//			long oldCount = keys[i];
//			keys[i] = total;
//			total += oldCount;
//		}
//		for(long i: input){
//			output[keys[(int)i].intValue()] = i;
//			keys[(int)i]++;
//		}
//		return output;
//	}
	
//	private static Long[] countingSortByPrimes(Long[] input, long maxValue, Long[] primes){ //can be replaced with any stable sort
//		
//		
//		Long[] keys = new Long[(int) maxValue]; //this will bite me in the ass eventually but hopefully not today TODO fix this if you can
//		Long[] output = new Long[input.length];
//		for (long i: input){
//			keys[(int) betterPrimeMultiple(i, primes)]++;
//		}
//		
//		long total = 0;
//		for(int i = 0; i < maxValue; i++){
//			long oldCount = keys[i];
//			keys[i] = total;
//			total += oldCount;
//		}
//		for(long i: input){
//			output[keys[(int)betterPrimeMultiple(i, primes)].intValue()] = i;
//			keys[(int)i]++;
//		}
//		return output;
//		//*/
//		
//		//HashMap<Long, Long> keys = new HashMap<Long, Long>();
//		
//		
//	}
	
	private static Long[] bucketSort(Long[] toSort){ 		// bucketSort, then add them to buckets as longs, then flashSort the buckets 
															//to take advantage of uniform distribution
		
		ArrayList<ArrayList<Long>> buckets = new ArrayList<ArrayList<Long>>();
		
		//Long[] toReturn = new Long[toSort.length];

		double maxValueUsed = maxElement(toSort);
		
		for (int i = 0; i < 100; i++){			//This is the # of buckets
			buckets.add(new ArrayList<Long>());
		}
		
		for (Long i: toSort){
			buckets.get((int) Math.ceil((((double) i) /maxValueUsed)*100) - 1).add(i);
		}
		
		ArrayList<Long> tempOutput = new ArrayList<Long>();
		for (ArrayList<Long> bucket: buckets){
//			if(bucket.size() > 1){
				Long[] sorted = insertionSort(bucket.toArray(new Long[bucket.size()]));
				for(int i = 0; i < sorted.length; ++i){
					tempOutput.add(sorted[i]);
				}
//			} 
//			else if(bucket.size() == 1) {
//				tempOutput.add(bucket.get(0));
//			}
		}
		
		return tempOutput.toArray(new Long[toSort.length]);
	}
	
	private static Long[] primeBucketSort(Long[] toSort, Long[] primes){ 		// bucketSort, then add them to buckets as longs, then flashSort the buckets 
		//to take advantage of uniform distribution

		ArrayList<ArrayList<Long>> buckets = new ArrayList<ArrayList<Long>>(100);

		//Long[] toReturn = new Long[toSort.length];

		long maxValueUsed = maxElement(toSort);

		for (int i = 0; i < 100; i++){			//This is the # of buckets
			buckets.add(new ArrayList<Long>());
		}

		for (Long i: toSort){
			buckets.get((int)((double) betterPrimeMultiple(i, primes)/maxValueUsed)).add(i);
		}

		ArrayList<Long> tempOutput = new ArrayList<Long>();
		for (ArrayList<Long> bucket: buckets){
//			if(bucket.size() > 1){
				Long[] sorted = primeInsertionSort(bucket.toArray(new Long[bucket.size()]), primes);
				for(int i = 0; i < sorted.length; ++i){
					tempOutput.add(sorted[i]);
				}
//			} 
//			else if(bucket.size() == 1) {
//				tempOutput.add(bucket.get(0));
//			}
		}

return tempOutput.toArray(new Long[tempOutput.size()]);
}
	
	private static Long[] primeInsertionSort(Long[] bucket, Long[] primes) {
		long temp;
        for (int i = 1; i < bucket.length; i++) {
            for(int j = i ; j > 0 ; j--){
                if(betterPrimeMultiple(bucket[j], primes) < betterPrimeMultiple(bucket[j-1], primes)){
                    temp = bucket[j];
                    bucket[j] = bucket[j-1];
                    bucket[j-1] = temp;
                }
            }
        }
        
        return bucket;
	}

	private static Long[] insertionSort(Long[] bucket){
		
		long temp;
        for (int i = 1; i < bucket.length; i++) {
            for(int j = i ; j > 0 ; j--){
                if(bucket[j] < bucket[j-1]){
                    temp = bucket[j];
                    bucket[j] = bucket[j-1];
                    bucket[j-1] = temp;
                }
            }
        }
        
        return bucket;
	}
	
//	private static Long[] flashSort(ArrayList<Long> target){
//		Long[] output = new Long[target.size()];
//		long maxValue = maxElement(output);
//		long minValue = minElement(output);
//		
//		for(long i: target){
//			double percentPosition = ((double)i)/output.length;
//			
//		}
//	}
	
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
	
	private static void writeOutResult(Long[] sorted, String outputFilename) throws FileNotFoundException {

		PrintWriter out = new PrintWriter(outputFilename);
		for (Long n : sorted) {
			out.println(n);
		}
		out.close();

	}
	
	private static long betterPrimeMultiple(long input, Long[] primes){ //pass in a list of all primes and only look through those
		long factor1 = input;
		boolean foundFirst = false;
		
//		System.out.println("This should not be null: " + input);
		
		for(int i = 0; i < primes.length; ++i){ //TODO make it only check the primes that are less than an upper bound (sqrt(input))?
			
//			System.out.println("neither should this: " + primes[i]);
			
			if(primes[i] > Math.cbrt(input)){
				break;
			}
			
			if(input % primes[i] == 0){
				if(input == primes[i]){
					return input;
				}
				if(!foundFirst){
					factor1 = primes[i];
					foundFirst = true;
				} else {
					return primes[i] * factor1;
				}

				
			}
		}
		
		if(!foundFirst){
			if(isPrime((long)Math.sqrt(input), primes)){
				return (long) Math.sqrt(input);
			} else {
				return input;
			}
		} else {
			return factor1 * firstPrime(input/factor1, primes);
		}		
	}
	
private static long firstPrime(long input, Long[] primes) {
		for(long check:primes){
			if(check > Math.sqrt(input)){
				return input;
			} else if(input % check == 0){
				return check;
			}
		}
		return input;
	}

//	private static Long[] sieveOfSundaram(long upperBound){ //TODO replace with newer sieve
//		HashSet<Long> targets = new HashSet<Long>();
//		
//		
//		for(long i = 0; i < (upperBound - 2)/2; ++i){ //TODO dear future self: if you're getting off by 1 errors it's here I'm not confident on the rounding on that division
//			targets.add(i);
//		}
//		
//		
//		
//		for(long i = 1; i + i + (2 * i * i) <= upperBound; ++i){
//			for(long j = i; i + j + (2 * i * j) <= upperBound; ++j){
//				targets.remove((Long) i + j + (2 * i * j));
//			}
//		}
//		Long[] targetArray = (Long[])targets.toArray();
//		Long[] output = new Long[targets.size() + 1];
//		output[0] = 2L; //the sieve of Sundaram returns all odd primes so 2 has to be manually included
//		for(int i = 0; i < targetArray.length; ++i){
//			output[i + 1] = targetArray[i];
//		}
//		return output;
//	}
	
	private static boolean isPrime(long target, Long[] primes) {
		for(long check:primes){
			if(check > Math.sqrt(target)){
				return true;
			} else if(target % check == 0){
				return false;
			}
		}
		return true;
	}

	private static Long[] sieveOfAtkin(long upperBound){
		/*
		 * I'll be honest here:
		 * I don't know how this math works or why these particular modulos of 60 are important.
		 * I trust in mathematicians far more learned than me,
		 * and Wikipedia.
		 * 
		 */
		
		/*
		 * Are you wondering why this code is garbage?
		 * It's right here.  It's this, right here.
		 * Really we just kinda ran out of time to optimize--there were better sorting algorithms than bucketsort to
		 * use and we were going to use flashsort (on buckets of number length) then spreadsort, but this is the time hole.
		 * It's not all bad; this scales purely off of K, not of N, so asymptotically this program isn't bad.
		 * In a competition? trash garbage.
		 * In the real world, with a database to store the primes we find? Sick nasty.
		 */


		TreeSet<Long> primes = new TreeSet<Long>();
		primes.add(2L);
		primes.add(3L);
		primes.add(5L);

		for(long sieved = 1; sieved < upperBound; ++sieved){
			switch ((int)(sieved % 60)){
			case 1: case 13: case 17: case 29: case 37: case 41: case 49: case 53:
				for(long x = 1; 4*(Math.pow(x, 2)) + 1 <= sieved; ++x){
					for(long y = 1; 4*(Math.pow(x, 2)) + (Math.pow(y, 2)) <= sieved; y += 2){
						if(4*(Math.pow(x, 2)) + (Math.pow(y, 2)) == sieved){
							if(!(primes.remove(sieved))){ //toggles whether the target is in the set
								primes.add(sieved);
							}
							//System.out.println("case 1 toggled " + sieved);
						}
					}
				}
				break;
			case 7: case 19: case 31: case 43:
				for(long x = 1; 3*(Math.pow(x, 2)) + 1 <= sieved; x += 2){
					for(long y = 2; 3*(Math.pow(x, 2)) + (Math.pow(y, 2)) <= sieved; y += 2){
						if(3*(Math.pow(x, 2)) + (Math.pow(y, 2)) == sieved){
							if(!(primes.remove(sieved))){
								primes.add(sieved);
							}
							//System.out.println("case 2 toggled " + sieved);
						}
					}
				}
				break;
			case 11: case 23: case 47: case 59:
				for(long x = 2; 3*(Math.pow(x, 2)) - 1 <= sieved; ++x){
					for(long y = x - 1; y > 0; y -= 2){
							if(3*(Math.pow(x, 2)) - (Math.pow(y, 2)) == sieved){
								if(!(primes.remove(sieved))){
									primes.add(sieved);
								}
								//System.out.println("case 3 toggled " + sieved);
							}
						}
					}
					break;
				default:
					break;
				}
//				System.out.println("Checked " + sieved);
			}
//			System.out.println("done making initial list of primes");

			ArrayList<Long> results = new ArrayList<Long>();
			while(!(primes.isEmpty())){
				long check = primes.pollFirst();
//				System.out.println("checking " + check);
				results.add(check);

				long checkSquare = (long)Math.pow(check, 2);
				//System.out.println("checksquare = " + checkSquare);

				primes.removeIf((Long x) -> (x % checkSquare) == 0);
			}

			//countingSort(results, upperBound); //we need the results in order in order to find smallest prime factors
		//should not need because treeSets are sorted
			
		return results.toArray(new Long[results.size()]);

	}
	
//	private static Long[] sieveOfSundaram(long upperBound){ //TODO replace with newer sieve
//	//	Boolean[] targets = new Boolean[(int)((upperBound - 2)/2)];
//	//	Arrays.fill(targets, true);
//		int resultCount = 1;
//		
//		for(long i = 1; i + i + (2 * i * i) <= upperBound; ++i){
//			for(long j = i; i + j + (2 * i * j) <= upperBound; ++j){
//				targets[(int) (i + j + (2 * i * j))] = false;
//				++resultCount;
//			}
//		}
//		Long[] output = new Long[resultCount];
//		output[0] = 2L; //the sieve of Sundaram returns all odd primes so 2 has to be manually included
//		int outputPosition = 1;
//		
//		for(int i = 0; i < upperBound; ++i){
//			if(targets[i]){
//				output[outputPosition] = (long)i;
//				outputPosition++;
//				if(outputPosition == resultCount){
//					break;
//				}
//			}
//		}
//		return output;
//
//	}
	
//	private static Long[] sieveOfAtkin(long upperBound){
//		ArrayList<Long> results = new ArrayList();
//		results.add(2L);
//		results.add(3L);
//		results.add(5L);
//		Boolean[] sieveList = new Boolean[(int)upperBound];
//		HashSet<Long> case1 = new HashSet();
//		HashSet<Long> case2 = new HashSet();
//		HashSet<Long> case3 = new HashSet();
//		
//		case1.add(1L);
//		case1.add(13L);
//		case1.add(17L);
//		case1.add(29L);
//		case1.add(37L);
//		case1.add(41L);
//		case1.add(49L);
//		case1.add(53L);
//		
//		case2.add(7L);
//		case2.add(19L);
//		case2.add(31L);
//		case2.add(43L);
//		
//		case3.add(11L);
//		case3.add(23L);
//		case3.add(47L);
//		case3.add(59L);
//		
//		for(int i = 0; i < upperBound; ++i){
//			if(case1.contains(i % 60)){
//				//TODO fill in actual content
//			}
//		}
//		
//	}
	
	private static class PrimesComparator implements Comparator<String> { //not needed, we're doing 2 passes so we can use counting sort

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


