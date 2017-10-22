import java.math.BigInteger;
import static java.math.BigInteger.*;

/**
 * Created by dansa on 9/29/2017.
 */
public class PrintPrimes {
    public static void main(String[] args)
    {
        //BY THE WAY I am using the name memoization incorrectly everywhere in this document.
        //Really I should mean to say precomputation.

        //If there is a possibility of numbers between 1 and 10^17 then according
        //to worlframalpha there are 17,082,666 primes less than sqrt(10^17)
        //
        //This is far more integers than I am comfortable precomputing. But just a note here,
        //!!! Sqrt(10^17) < ((2^32)/2) evaluates to true, so all single smallest prime factors will fit into an integer !!!
        //
        //`primes less than sqrt(10^10)`=9592 integers is too much space to be initialized within one function.
        //You can tun javap -c .../out/GroupX.class to see how many bytes your methods take up.
        //Max method size is 65535 bytes.
        //Each integer added to the buffer increases the byte code by 4 bytes
        //Unfortunately something else seems to change with the byte code as you add more entries.
        //Of course it couldn't be that simple :)

        //8223 numbers were the largest possible number of added entries before it went over the limit for sort's current implementation.
        //So let's keep each array with less than or equal to 8223 numbers.

        //The purpose of this file is to output N arrays of primes to be placed within the primes arrays.
        //And each of the arrays should be a size small enough not to offend the java compiler.

        int numPrimes = 18092; //18092 Is a number between 10^9 and 10^10. I dont remember if it's significant.
        int sorts = 5; //This keeps each array well below 8223 numbers

        BigInteger prime = ONE;
        for(int i = 0; i < numPrimes; i++)
        {
            System.out.print((prime = prime.nextProbablePrime()) + ",");
            if(((i + 1) % (numPrimes/sorts)) == 0)
                System.out.println();
        }



    }
}
