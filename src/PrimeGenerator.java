import java.lang.Math;
import java.util.ArrayList;
import java.util.List;

public class PrimeGenerator {
    public static void main(String[] args){
        System.out.println(listPrimes(200000));
    }

    public static ArrayList<Long> listPrimes(int i){
        ArrayList<Long> primes = new ArrayList<Long>();

        primes.add((long) 2);
        int j = 1;
        int k = 3;
        while(j <= i){
            if (isPrime(k, primes.subList(0, j - 1))){
                primes.add((long) k);
                j++;
            }
            k++;
        }

        return primes;
    }

//    public ArrayList<Long> primesLessThan(long max, long[] primes){
//        long k = primes[primes.length - 1] + 2;
//        while(k <= max){
//            if (isPrime(k, primes)){
//                primes[k] ;
//            }
//            k+=2;
//        }
//
//        return primes;
//    }

    public static boolean isPrime(long n, long[] lesserPrimes){
        for(Long i : lesserPrimes){
            if(n % i == 0){
                return false;
            } else if (i > Math.sqrt(n)){
                break;
            }
        }
        return true;
    }

    //Takes an integer and an int array of all primes less than that integer.
    //returns true if that integer is prime and false otherwise
    public static boolean isPrime(long n, List<Long> lesserPrimes){
        for(Long i : lesserPrimes){
            if(n % i == 0){
                return false;
            } else if (i > Math.sqrt(n)){
                break;
            }
        }
        return true;
    }
}