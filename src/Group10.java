//Courtney Cook and Nathan Beneke

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;

public class Group10 {

	private static int[] knownPrimesInts = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103, 107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557, 563, 569, 571, 577, 587, 593, 599, 601, 607, 613, 617, 619, 631, 641, 643, 647, 653, 659, 661, 673, 677, 683, 691, 701, 709, 719, 727, 733, 739, 743, 751, 757, 761, 769, 773, 787, 797, 809, 811, 821, 823, 827, 829, 839, 853, 857, 859, 863, 877, 881, 883, 887, 907, 911, 919, 929, 937, 941, 947, 953, 967, 971, 977, 983, 991, 997, 1009, 1013, 1019, 1021, 1031, 1033, 1039, 1049, 1051, 1061, 1063, 1069, 1087, 1091, 1093, 1097, 1103, 1109, 1117, 1123, 1129, 1151, 1153, 1163, 1171, 1181, 1187, 1193, 1201, 1213, 1217, 1223};
	private static long[] knownPrimesLongs;
	private static PrimesComparator primesComparator;


    public static void main(String[] args) throws InterruptedException, FileNotFoundException {
		// testing the comparator:
		KVPair.testPrimeFactors();
		
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
        primesComparator = new PrimesComparator();
        KVPair[] unsorted = new KVPair[toSort.length];

        //Throw all the data from the input into a KVPair[]
        for (int i = 0; i < toSort.length; i++){
            unsorted[i] = new KVPair(new Long(toSort[i]));
        }

		quicksort(unsorted, 0, toSort.length - 1, primesComparator);

        //Transfer data back to the String[]
        for (int i = 0; i < toSort.length; i++){
            toSort[i] = unsorted[i].toString();
        }
		return toSort;
	}
	private static void quicksort(KVPair[] input, int lowIndex, int highIndex, PrimesComparator comparator) {

		if (highIndex<=lowIndex) return;

		KVPair pivot1=input[lowIndex];
		KVPair pivot2=input[highIndex];

		int pivotCompare = comparator.compare(pivot1, pivot2);

		if (pivotCompare > 0){
			exchange(input, lowIndex, highIndex);
			pivot1=input[lowIndex];
			pivot2=input[highIndex];
		}
//		else if (pivotCompare == 0){
//			while (pivot1==pivot2 && lowIndex<highIndex){
//				lowIndex++;
//				pivot1=input[lowIndex];
//			}
//		}


		int i=lowIndex+1;
		int lt=lowIndex+1;
		int gt=highIndex-1;

		while (i<=gt){

			if (comparator.compare(input[i], pivot1) < 0){
				exchange(input, i++, lt++);
			}
			else if (comparator.compare(pivot2, input[i]) < 0){
				exchange(input, i, gt--);
			}
			else{
				i++;
			}

		}

		exchange(input, lowIndex, --lt);
		exchange(input, highIndex, ++gt);

		quicksort(input, lowIndex, lt-1, comparator);
		if (primesComparator.compare(pivot1, pivot2) != 0) {
            quicksort(input, lt + 1, gt - 1, comparator);
        }
		quicksort(input, gt+1, highIndex, comparator);

	}

	private static void exchange(KVPair[] arr, int n, int m){
		KVPair nCopy = arr[n];
		arr[n] = arr[m];
		arr[m] = nCopy;
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
	
	private static class PrimesComparator implements Comparator<KVPair> {

		private PrimesComparator() {
		    //Note that this constructor is called at the beginning of every sort, so it's okay to throw 200 ints into
            //a static long array since that array MUST be recomputed at the beginning of every sort.
		    knownPrimesLongs = new long[knownPrimesInts.length];
			int i = 0;
			for(int p : knownPrimesInts){
			    knownPrimesLongs[i++] = (long) p;
            }
        }

        public int compare(KVPair n, KVPair m){
		    if (n.value > m.value){
		        return 1;
            } else if (n.value < m.value){
		        return -1;
            } else if (n.key > m.key){
                return 1;
            } else if (n.key < m.key){
                return - 1;
            } else {
                return 0;
            }
        }
	}

	private static class KVPair {
	    long key;
	    long value;

	    private KVPair(long key){
	        this.key = key;
	        this.value = productOfPrimeFactors(key);
        }

        // Takes a long number and returns the product of its up to two
        // smallest prime factors
        private static long productOfPrimeFactors(long n) {
            long prime1 = 1;
            long prime2 = 1;
            long bound = (long) Math.sqrt(n) + 1;

            //First loop through the list of known primes
            for (long l : knownPrimesLongs){
                if(n % l == 0 && prime1 == 1){
                    prime1 = l;

                    //Now that we know one prime we can lower our bound.
                    int reps = 2;
                    while(n % Math.pow(prime1, reps) == 0){
                        reps++;
                    }
                    //The second prime factor is less than n / (the highest power of prime1 that n is divisible by)
                    //and if it's greater than sqrt(n) then n / (the highest power of prime1 that n is divisible by) is
                    //prime, and the only other prime factor.
                    bound = Math.min(bound, n / (long) Math.pow(prime1, reps - 1));

                } else if (n % l == 0){
                    prime2 = l;
                    return prime1 * prime2;
                }

                if (l > bound){
                    break;
                }
            }

            //Now loop through numbers larger than all known primes, but less than the bound
            for (long i = knownPrimesLongs[knownPrimesLongs.length - 1] + 2; i <= bound; i += 2) {
                if ((n % i) == 0) { // the first found factor must be prime
                    if (prime1 == 1) {
                        prime1 = i;

                        int reps = 2;
                        while(n % Math.pow(prime1, reps) == 0){
                            reps++;
                        }
                        bound = Math.min(bound, n / (long) Math.pow(prime1, reps - 1));
                    } else { // the second found factor is a prime or a multiple of the first one
                        if (i % prime1 != 0) { // now we know it's a prime
                            prime2 = i;
                            break;
                        }
                    }
                }
            }

            // if we didn't find any prime factors, the number itself must be prime
            if (prime1 == 1) {
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
            PrimesComparator comparator = new PrimesComparator();

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

        public String toString(){
	        return key + "";
        }
    }
}

