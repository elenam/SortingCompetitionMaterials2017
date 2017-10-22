import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

//Rocherno de Jongh
//Tsz Hong Lau

public class Group1 {
	private final static BigInteger TWO = new BigInteger("2");
	private final static Random random = new Random();

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
		String[] data = readData(inputFileName);

		String[] toSort = data.clone();

		String[] sorted = sort(toSort);

		// printArray(sorted, 100);

		toSort = data.clone();

		Thread.sleep(10); // to let other things finish before timing; adds
							// stability of runs

		long start = System.currentTimeMillis();

		sorted = sort(toSort);

		long end = System.currentTimeMillis();

		System.out.println(end - start);

		writeOutResult(sorted, outFileName);

	}

	
	private static String[] sort(String[] toSort) {
		//Choosing whether to use quickSort or Arrays.sort
		//depending on the data size
		if(toSort.length >= 4000){
			Arrays.sort(toSort, new PrimesComparator());
		}
		else{
			quickSort(toSort, new PrimesComparator());
		}
		return toSort;
	}

	private static String[] readData(String inFile) throws FileNotFoundException {
		ArrayList<String> input = new ArrayList<>();
		Scanner in = new Scanner(new File(inFile));

		while (in.hasNext()) {
			input.add(in.next());
		}

		in.close();

		// the string array is passed just so that the correct type can be
		// created
		return input.toArray(new String[0]);
	}
	
	//quickSort method, the normal one
	public static void quickSort(String[] arr, Comparator<String> c) {
		quickSort(arr, 0, arr.length - 1, c);
	}

	private static void quickSort(String[] arr, int low, int high, Comparator<String> c) {

		if (high - low < 1) {
			return;
		}
		
		String pivot = arr[high];
		int swap = low;
		for (int curr = low; curr <= high - 1; curr++) {
			if (c.compare(arr[curr], pivot) < 0) {
				String temp = arr[swap];
				arr[swap] = arr[curr];
				arr[curr] = temp;
				swap++;
			}
		}
		String temp = arr[swap];
		arr[swap] = pivot;
		arr[high] = temp;

		quickSort(arr, low, swap - 1, c);
		quickSort(arr, swap + 1, high, c);

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

		// Modified productOfPrimes, we made some modifications such that it can find
		// small primes faster.
		// We put a bound, which we think works well, that if all of the prime factors aren't found in this bound
		// we turn over to our modified Pollard Rho.
		private static long productOfPrimeFactors(long n) {
			long prime1 = 1;
			long prime2 = 1;
			long bound = 401;
			
			if( n == 2 || n == 1){
				return n;
			}
			if (n % 2 == 0) {
				prime1 = 2;
			}

			for (int i = 3; i <= bound; i += 2) {
				if ((n % i) == 0) {
					if (prime1 == 1) {
						prime1 = i;
						if(i == n){
							break;
						}
					} else {
						if (i % prime1 != 0) {
							prime2 = i;
							break;
						}
					}
				}
			}
			
			//Checking whether we should use Pollard Rho
				
			if (n > bound && (prime1 == 1 || prime2 == 1)) {
				BigInteger N = new BigInteger(Long.toString(n));
				BigInteger one = new BigInteger("1");
				LinkedList<BigInteger> list = new LinkedList<BigInteger>();
				list.add(one);
				factor(N, list);
				//Since Pollard Rho is probabilistic, we do a while loop untill we certain
				//the prime factors are right
				while(multList(list, N) != n) {
					list.clear();
					factor(N, list);
				}
				
				prime1 = list.removeFirst().longValue();
				prime2 = list.removeFirst().longValue();
				
			} 

			return prime1 * prime2;
		}
		
		
		//This checks whether all of the prime factors were found, and also at the same time
		//it get the two smallest primes, and add them to the beginning of the list
		public static long multList(LinkedList<BigInteger> list, BigInteger N) {
			BigInteger total = new BigInteger("1");
			BigInteger one = new BigInteger("1");
			if (list.size() != 2) {
				list.remove(one);

				BigInteger p1 = N;
				BigInteger p2 = one;
				for (BigInteger n : list) {
					total = total.multiply(n);
					if (p1.compareTo(n) == 1 && n.compareTo(p2) != 0) {
						p2 = p1;
						p1 = n;
					} else if (n.compareTo(p1) != 0 && p2.compareTo(n) == 1 || p2.compareTo(one) == 0) {
						p2 = n;
					}

				}
				if(p2.compareTo(N) == 0) {
					p2 = one;
				}
				list.addFirst(p1);
				list.addFirst(p2);
			} else {
				for (BigInteger n : list) {
					total = total.multiply(n);
				}
			}

			return total.longValue();
		}
		//Our modified Pollard Rho method
		public static BigInteger rho(BigInteger N) {
			BigInteger divisor;
			BigInteger c = random(new BigInteger("301"), N);
			BigInteger x = random(new BigInteger("301"), N);
			BigInteger y = x;

			
			if (N.mod(TWO).compareTo(BigInteger.ZERO) == 0) {
				return TWO;
			}

			do {
				x = x.multiply(x).mod(N).add(c).mod(N);
				y = y.multiply(y).mod(N).add(c).mod(N);
				y = y.multiply(y).mod(N).add(c).mod(N);
				divisor = x.subtract(y).gcd(N);
			} while (divisor.compareTo(BigInteger.ONE) == 0);

			return divisor;
		}

		public static void factor(BigInteger N, LinkedList<BigInteger> list) {
			if (N.compareTo(BigInteger.ONE) == 0)
				return;
			if (N.isProbablePrime(20)) {
				list.add(N);
				return;
			}
			BigInteger divisor = rho(N);
			factor(divisor, list);
			factor(N.divide(divisor), list);
		}

		public static BigInteger random(BigInteger min, BigInteger max) {
			if (max.compareTo(min) < 0) {
				BigInteger tmp = min;
				min = max;
				max = tmp;
			} else if (max.compareTo(min) == 0) {
				return min;
			}
			max = max.add(BigInteger.ONE);
			BigInteger range = max.subtract(min);
			BigInteger result = new BigInteger(range.bitLength(), random);
			while (result.compareTo(range) >= 0) {
				result = new BigInteger(range.bitLength(), random);
			}
			result = result.add(min);
			return result;
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
			if (productOfPrimeFactors(446817352) != 14138) { // 8 * 7069 * 7901,
																// so product =
																// 2 * 7069 =
																// 14138
				System.out.println("fails on 446817352");
				System.out.println(productOfPrimeFactors(446817352));
			}
			if (productOfPrimeFactors(292) != 146) { // 8 * 7069 * 7901, so
														// product = 2 * 7069 =
														// 14138
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