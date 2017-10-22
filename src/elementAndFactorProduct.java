public class elementAndFactorProduct {
	public static int[] knownPrimes = new int[]{2,3,5};
	public String element = "";
	public long productOfPrimes = 0;
	
	/*
	 * Constructor: 
	 * Input: String (presumably a number)
	 * Converts the String to a long
	 * Stores the original element value as a long, and the 'productOfPrimes' value as a long
	 */
	public elementAndFactorProduct(String ele){
		element = ele;
		long convertedElement = new Long(element);
		productOfPrimes = productOfPrimeFactors(convertedElement);
	}
	
	/*
	 * Constructor 2: 
	 * Input: String (presumably a number), long
	 * Stores the original element value as a long, and the second input value as a long ('productOfPrimes')
	 */
	public elementAndFactorProduct(String ele, long PoP){
		element = ele;
		productOfPrimes = PoP;
	}
	
	/*
	 * returns the productOfPrimes when called
	 */
	public long getProductofPrimes(){
		
		return productOfPrimes;
	}
	
	/*
	 * Input: A number in long form
	 * Output: The product of the two smallest prime factors of the inputed long
	 */
	public static long productOfPrimeFactors(long n) {
		//If the number is 1, therefore lacks prime factors aside from itself
		if(n == 1) {
			return 1;
		}
		
		long prime1 = 1;
		long prime2 = 1;
		long bound = (long) Math.sqrt(n) + 1;
		long holder = n;
		
		//check the first 3 prime numbers to see if they are factors of the given long
		for (int i = 0; i < knownPrimes.length; i++)
		{
			if (holder % knownPrimes[i] == 0)
			{
				//If this is the first prime factor found
				if (prime1 == 1){
					prime1 = knownPrimes[i];
				} else { // This is the second factor found, so we can output the product now
					prime2 = knownPrimes[i];
					return prime1 * prime2;
				}
			}
		}
		
		//edited to match prime number sequence 1 and 2 (6n+5 and 6n+1 respectively)
		for (int i = 1; (6*i+1) <= bound; i++) {
			if ((holder % (6*i+1)) == 0) { // the first found factor must be prime
				if (prime1 == 1) {
					prime1 = (6*i + 1);
				} else { // the second found factor is a prime or a power of the first one
					if ((6*i+1) % prime1 != 0) { // now we know it's a prime
						prime2 = (6*i + 1);
						break;
					}
				}
			}
			
			if ((holder % (6*i+5)) == 0) { // the first found factor must be prime
				if (prime1 == 1) {
					prime1 = (6*i + 5);
				} else { // the second found factor is a prime or a power of the first one
					if ((6*i+5) % prime1 != 0) { // now we know it's a prime
						prime2 = (6*i + 5);
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
