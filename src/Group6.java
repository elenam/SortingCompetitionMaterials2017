import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

/*	CSci 3501 Sorting Competition
 	Danish Malik & Kyle DeBates

 	This program is our implementation of Quick Sort to sort an array of numbers based on the product of their two
 	smallest prime factors. Worked with Francisco & Luz on the 'prime' functions. 
 */

public class Group6 {

        public static void main(String[] args) throws InterruptedException, FileNotFoundException {


            if (args.length < 2) {
                System.out.println("Please run with two command line arguments: input and output file names");
                System.exit(0);
            }

            String inputFileName = args[0];
            String outFileName = args[1];

            // read as strings
            String [] data = readData(inputFileName);

            String [] toSort = data.clone();

            Thread.sleep(10); //to let other things finish before timing; adds stability of runs

            long start = System.currentTimeMillis();

            String [] sorted = sort(toSort);

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

        /*
        Function  to see if n is prime or not
        true if prime, false otherwise
        */
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

        /*
        Function to find all prime factors of a number
         */
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

        /*
        Function to find the product of two smallest primes
        */
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

        /*
        Function to find the product of 2 smallest primes
         */
        private static long finalProductOfPrimes(long n)
        {
            return productOfPrimes(primeFactors(n));
        }

        private static String[] sort(String[] arr) {
            long[] longs = new long[arr.length];

            for (int i = 0; i < arr.length; i++)
            {
                longs[i] = Long.parseLong(arr[i]);
            }

            quickSort(longs, 0, arr.length - 1);

            for (int i = 0; i < arr.length; i++)
            {
                arr[i] = Long.toString(longs[i]);
            }

            return arr;
        }

        /*
        Function to swap to elements in an array
         */
        public static void swap(long[] list, int i, int j)
        {
            long temp = list[i];
            list[i] = list[j];
            list[j] = temp;
        }


        /*
        Our Quick Sort implementation that takes an array of type long,
        firstIndex 0 and the final index length of array - 1.
        */
        public static void quickSort(long[] arr, int firstIndex, int secondIndex) {

            if (firstIndex < secondIndex) {
                int q = partition(arr, firstIndex, secondIndex);
                quickSort(arr, firstIndex, q - 1);
                quickSort(arr, q + 1, secondIndex);
            }
        }

        /*
        Our Quick Sort implementation that takes an array of type long,
        firstIndex 0 and the final index length of array - 1.
        */
        public static Integer partition(long[] arr1, int firstIndex1, int secondIndex1) {

            long pivot = finalProductOfPrimes(arr1[secondIndex1]);
            Integer i = (firstIndex1 - 1);

            for (int j = firstIndex1; j < secondIndex1; ++j) {
                if ((finalProductOfPrimes(arr1[j]) < (pivot))) {
                    ++i;
                    swap(arr1, i, j);
                }
                else if (finalProductOfPrimes(arr1[j]) == (pivot)){
                    long piv = arr1[secondIndex1];
                    if ((arr1[j]) <= piv)
                    {
                        i++;
                        swap(arr1,i,j);
                    }
                }
            }
            swap(arr1,i+1,secondIndex1);

            return i + 1;
        }

    }
