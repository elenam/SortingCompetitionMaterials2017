import java.util.ArrayList;
import java.util.BitSet;
public class calculatePrimes{
	public static void main(String[] args) {

		int primesLessThan = 1000002;
		long start = System.currentTimeMillis();

		ArrayList<Integer> primes = sieve();

		long end = System.currentTimeMillis();

		System.out.println("Time: " + (end - start) + "ms");

	}

	// Creates a BitSet and returns an ArrayLst of primes up to n + 2
	public static ArrayList sieve() {
		int n = 93179 + 2;
		ArrayList<Long> toReturn = new ArrayList<Long>();

		BitSet candidates = new BitSet(n);

		candidates.set(2, n - 1, true);
		int sqrtN = (int) Math.sqrt(n);
		
		// Sieve out the numbers
		for (int i = 2; i <= sqrtN; i++) {
			if (candidates.get(i)){
				for (int j = i * i; j <= n; j += i){
					candidates.set(j, false);
				}
			}
		}
		
		// if i is true in the candidates list generated, it is added to the ArrayList for later.
		for (int i = 7927; i < n; i++) {
			if (candidates.get(i)){
				toReturn.add((long) i);
			}

		}

		return toReturn;
	}

	// prints the ArrayList, used for testing purposes
	private static void printArrList(ArrayList<Integer> arr) {
		for(int i = 0; i < arr.size(); i++) {
			System.out.println(arr.get(i));
		}
	}


	// Takes the ArrayList created, and checks if each number is actually a prime. If not, it is removed 
	// from the list.
	private static boolean isPrime(int num) {
		if (num < 2) return false;
		if (num == 2) return true;
		if (num % 2 == 0) return false;
		
		//loops over every odd number up to the sqrt of the number passed in, and determines if each i is a prime number
		for (int i = 3; i * i <= num; i += 2)
			if (num % i == 0) return false;
		return true;
	}

	//final check to make sure everything in the list is prime. Without this, there are one or two fringe cases that end up
	//falling through the cracks of the irPrime method
	static boolean allPrime(ArrayList<Integer> arr) {
		int size = arr.size();
		boolean toReturn = true;
		for (int i = 0; i < size; i++) {
			if(!isPrime(arr.get(i))) {
				System.out.println(arr.get(i));
				return false;
			}
		}

		return toReturn;
	}

}
