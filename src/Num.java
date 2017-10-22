
public class Num {
	private long value;
	private long prime;
	
	public void setValue(long n){
		value = n;
		prime = productOfPrimeFactors(value);
	}
	public long getValue(){
		return value;
	}
	public long getPrime(){
		return prime;
	}
	
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
}
