import java.util.ArrayList;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;
import  java.math.BigInteger;

// Our Quicksort implementation for the sorting competition.
// Francisco Montanez and Luz Lopez
// Worked with Danish Malik and Kyle Debates on the prime template(functions).

public class Group12
{
    public static void main(String[] args) throws InterruptedException, FileNotFoundException
    {

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

        toSort = data.clone();

        Thread.sleep(10); //to let other things finish before timing; adds stability of runs

        long start = System.currentTimeMillis();

        sorted = sort(toSort);

        long end = System.currentTimeMillis();

        System.out.println(end - start);

        writeOutResult(sorted, outFileName);

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

    // Sort method first converts the data of strings to longs
    // then calls necessary functions that sort the array
    // according to the product of their two smallest prime factors
    // and then converts them to an array of strings and returns them
    private static String[] sort(String[] toSort) {
        long[] longs = new long[toSort.length];

        for (int i = 0; i < toSort.length; i++)
        {
            longs[i] = Long.parseLong(toSort[i]);
        }

        quickSort(longs, 0, toSort.length - 1);

        for (int i = 0; i < toSort.length; i++)
        {
            toSort[i] = Long.toString(longs[i]);
        }

        return toSort;
    }

    // Function to check if a given number n is prime or not
    // true if prime, false otherwise
    private static boolean isPrime(long n)
    {
        // false because 0 and 1 are not
        // primes
        if (n == 0 || n == 1)
        {
            return false;
        }
        // try to divide n by first 4 prime numbers
        // false if divisible with no remainder,
        // meaning not prime
        int[] singleDigitPrimes = {2, 3, 5, 7};

        for (long prime : singleDigitPrimes)
        {
            if (n % prime == 0 && n != prime)
            {
                return false;
            }
        }
        return true;
    }

    // This function returns all prime factors of a given number in
    // an array list
    private static ArrayList<Long> primeFactors(long n)
    {
        ArrayList<Long> primes = new ArrayList<>();

        // loop until square root of n plus 1
        // adds primes to array list primes

        for (long i = 2; i <= Math.sqrt(n) + 1; i++)
        {
            while (n % i == 0)
            {
                primes.add(i);
                n = n / i;
            }
        }

        // for prime number, add itself to
        // array list
        if (n > 1)
        {
            primes.add(n);
        }

        return primes;
    }

    // Function to find multiplies of smallest two prime factors
    private static long productOfPrimes(ArrayList<Long> primes)
    {
        long product;

        // for 1, return 1
        if (primes.size() == 0)
        {
            product = 1;
            return product;
        }

        // if number is prime, then return it
        if (primes.size() == 1 && isPrime(primes.get(0)))
        {
            product = primes.get(0);
            return product;
        }

        // for the rest with some duplicates
        if (primes.size() > 1)
        {
            for (int i = 0, j = 1; i < primes.size() - 1; i++)
            {
                if (primes.get(j) != primes.get(0))
                {
                    product = primes.get(j) * primes.get(0);
                    return product;
                }

                if (primes.get(j) == primes.get(0))
                {
                    j++;
                }
            }
        }
        // for just one repeated number
        product = primes.get(0);

        return product;
    }

    // Function that returns the product of the two smallest prime factors
    // of a given number
    private static long finalProductOfPrimes(long n)
    {
        return productOfPrimes(primeFactors(n));
    }

    // Our sorting method (quicksort) which calls parition
    // and sorts according to two smallest prime factors of the numbers
    public static void quickSort(long[] RA, int first, int last)
    {
        if(first < last)
        {
            int q = partition(RA, first, last);
            quickSort(RA, first, q - 1);
            quickSort(RA, q + 1, last);
        }
    }


    // Our partition function that partitions according to product of
    // two smallest primes
    public static int partition(long[] RA, int first, int last)
    {
        long x = finalProductOfPrimes(RA[last]);
        int i = first - 1;
        for(int j = first; j < last; j++)
        {
            if(finalProductOfPrimes(RA[j]) < x)
            {
                i++;
                swap(RA,i,j);
            }
            else if (finalProductOfPrimes(RA[j]) == x)
            {
                long y = RA[last];
                if ((RA[j]) < y)
                {
                    i++;
                    swap(RA,i,j);
                }
            }
        }
        swap(RA, i + 1, last);
        return i + 1;
    }

    // Our swap function, swaps two elements in an array
    public static void swap(long[] list, int i, int j)
    {
        long temp = list[i];
        list[i] = list[j];
        list[j] = temp;
    }

}