import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

/**
     ............=~....................................................~~~=..........
     .............~~................................................~~~=.............
     ..............~~=~~~~.......................................=~~~~~==............
     ...............=~~~~~~~...................................~~~~~~==~.............
     ................=~~~~~~~...............................~~~~~~~~=.=~.............
     .................=~~~~~~~:...........................=~~~~~~~=..~...............
     ..................~~~~~~~~~........................~~~~~~~~~...~................
     ...................~~~~~~~~=.....................=~~~~~~~~=..==.................
     ....................~~~~~~~~~...................~=~~~~~=~..~~...................
     .....................=~~~~~~~=................~=~~~~~~..,=~.....................
     ......................=~~~~~~~=..............~~~~~~=..~=~.......................
     ..................?IIII~~~~~~~=~...........==~~~=+.~===IIIIII...................
     ...............IIIIIIIII~=~=~~~==.........~~~~~~===~IIIIIIIIIII.................
     .............IIIIIIIIII??==?~~~~==......===~~~~~=?IIIIIIIIIIIIII?...............
     ...........+IIIIIIIIIIIIII~==~====~....===.====?IIIIIIIIIIIIIIIIII..............
     ..........IIIIIIIIIIIIIIIIII======~~.=~=.+===IIIIIIIIIIIIIIIIIIIII..............
     .........IIIIIIIIIIIIIIIIIIIII========..===+IIIIIIIIIIIIIIIIIIIIIII.............
     ........IIIIIIIIIIIIIIIIIIIIIIII==I.==..=~+IIIIIIIIIIIIIIIIIIIIIIIII............
     ........IIIIIIIIIIIIIIIIIIIIIIIIIII.....IIIIIIIIIIIIIIIIIIIIIIIIIIII............
     .......:IIIIIIIIIIIIIIIIIIIIIIIIIIII....IIIIIIIIIIIIIIIIIIIIIIIIIIII............
     .......IIIIIIIIIIIIIIIIIIIIIIIIIIIII....IIIIIIIIIIIIIIIIIIIIIIIIIIII............
     .......IIIIIIIIIIIIIIIIIIIIIIIIIIIII....IIIIIIIIIIIIIIIIIIIIIIIIIIII,...........
     .......IIIIIIIIIIIIIIIIIIIIIIIIIIIII....IIIIII:,,,,IIIIIIIIIIIIIIIII:...........
     .......IIIIIIII==?IIIIIIIIIIIIIIIIII....IIII,,,,,,,,,IIIIIIIIIIIIIII............
     .......=IIII:,,,,,,:IIIIIIIIIIIIIIII....:II+,,,,,,,,,~IIIIIIIIIIIIII............
     ........III:,,,,,,,,,IIIIIIIIIIIIII......II:,,,,,,,,,,IIIIIIIIIIIII...,,:::::...
     ........+II,,,,,,,,,,IIIIIIIIIIIIII.......I?,,,,,,,,,?IIIIIIIIIIII?=~====~~==~..
     .........II,,,,,,,,,,IIIIIIIIIIIII,,,:,~=:=I=,,,,,,,+IIIIIIIIIIIII+????????I?7..
     ..........I?,,,,,,,,?IIIIIIIIIIIII++~==~~++=III=,~IIIIIIIIIIIIIIIIIIIIIIIIIII7..
     :,,.,:::,I,II,,,,,,IIIIIIIIIIIII7+++?+???????IIIIIIIIIIIIIIIIIIII7IIIIII777777..
     =~I=.7+:?7=?7IIIIIIIIIIIIIIIIII?IIIIIIIIIIII77I7IIIIIIIIIIII?77777777,.+...I77..
     =?,7+..I..I..?~IIIIIIIIIIIIII77777777777777777777777?III77777777777=..777,=777..
     +IIIIIIIIII777777?IIIIIIII?777777777777777777777777777777777777777?..I77777777..
     ?777777=.+:.I777..?777777777777777777777...77I77777777777777777777...777777777..
     I77777..777..77+..777777777777777777777...777777I7=.,77I..7+..+........?777777..
     7I77...7777..77..77777777777:.I7?..I?......,7..77...777:...:,.777...7777777777..
     I77=..77777I=?...7I...:.777I..7.7..:77...777..777..~777...777777I...7777777777..
     77I..~77777+~7..7+..77I..77..==7?..77~..777..I77...777=..7777777...I7777777777..
     77...777777?I..~7..I77..?77..=77..+77..~77,..77...:77I...7IIII77...777777+?II7..
     7?..:77777:+~..77..77,..77..=77~..77...777...~.7..+77?,.I??+I77I..=?IIIII?III7..
     7...777777?7..I77..I...7I+..777...77...I777.:777=7777777I?+??77...7I7777I?II77:.
     7...7I7777.7.,III77?777777777777777777777777777777777II?+?+?I77..7?IIIII???I77I.
     7...7I77::.I?=?I++=I77777I?777I777777777777777777777II??I7+7?7..7??IIIII???I777.
     7I..~777.=I7III:II?I?+~+I7II77II77777777777777777777II?II,..?.=7???IIIII??II777.
     7I7,...~?III?+?,?I?????I????II???I7777777777777777?III+?I+777I7I???IIIII???I777.
     7IIIII=I+III?+I7IIIIII?I:?=II7?++I77I77777II7II??I??II+I???III77IIIIIIIIII?II77.
     7I?+I?+7:7~:?II:IIIIII=I~I,II7??+???I+????????+++??????I?????I777IIIIIIII7?II77.
     7I?+??II+::~IIIIIIIIIIII?II?I7++++++?+?????+++I+??IIIIIIIIIIIIIIIIII??I?????I77.
     7?~+?~~I:??I+77I??+===,=.=,::??++??????IIIII?IIIIIIIIIII7777777777I777I777III7I.
     ?7?7=I7+:+?+I777777I+~,.........................................................
     ................................................................................
     ................................................................................
**/
public class Group17 {

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

        Object [] sorted = sort(toSort);

        //printArray(sorted, 100);

        toSort = data.clone();

        Thread.sleep(10); //to let other things finish before timing; adds stability of runs

        long start = System.currentTimeMillis();

        sorted = sort(toSort);

        long end = System.currentTimeMillis();

        System.out.println(end - start);

        writeOutResult(sorted, outFileName);

    }

    private static Item[] sort(String[] input) {
        Item[] output = new Item[input.length];

        for (int i = 0; i < output.length; i++) {
            output[i] = new Item(input[i]);
        }

        Arrays.sort(output);

        return output;
    }

    private static class Item implements Comparable<Item> {

        private long number = 0;
        private long product = 1;

        private Item(String string) {
            int length = string.length();

            // this beats Long.parseLong because we're guaranteed that the
            // strings are representations of positive base-10 integers

            for (int i = 0; i < length; i++) {
                number *= 10;
                number += string.charAt(i) - '0';
            }

            // leaving this in here for posterity... turns out that it tends to
            // hurt performance when we're pinned to a single core

            // also, there's a 0.003% chance it's a false positive ¯\_(ツ)_/¯

            /*
                if (BigInteger.valueOf(number).isProbablePrime(15)) {
                    product = number;
                    return;
                }
            */

            long f1 = 1;
            long f2 = 1;

            // checking 2 and 3 prior to everything else allows us to do 6x
            // fewer iterations of the loop later on

            if (number % 2 == 0) {
                f1 = 2;
            }

            if (number % 3 == 0) {
                if (f1 == 1) {
                    f1 = 3;
                }
                else {
                    product = 6;
                    return;
                }
            }

            // this next part looks awful, but it's pretty much what Elena did
            // except that all multiples of 2 and 3 are skipped

            // interestingly, it's way more performant to store the factors in
            // separate longs and multiply at the end than it is to adjust the
            // product directly and keep track of how many factors are found...
            // the difference in time between round 2 and the final will show
            // that

            for (long t1 = 5, t2 = 7, m = (long) Math.sqrt(number) + 1; t1 <= m; t1 += 6, t2 += 6) {
                if (number % t1 == 0) {
                    if (f1 == 1) {
                        f1 = t1;
                    }
                    else if (t1 % f1 != 0) {
                        f2 = t1;
                        break;
                    }
                }

                if (number % t2 == 0) {
                    if (f1 == 1) {
                        f1 = t2;
                    }
                    else if (t2 % f1 != 0){
                        f2 = t2;
                        break;
                    }
                }
            }

            if (f1 == 1) {
                f1 = number;
            }
            else if (f2 == 1) {
                long t = number;

                // unhelpful side note: this is the first time i've used a
                // do-while for anything

                do {
                    t /= f1;
                }
                while (t % f1 == 0);

                f2 = t;
            }

            product = f1 * f2;
        }

        @Override
        public int compareTo(Item o) {
            return this.product != o.product
                ? Long.compare(this.product, o.product)
                : Long.compare(this.number, o.number);
        }

        @Override
        public String toString() {
            return String.format("%d", number);
        }

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

    private static void writeOutResult(Object[] sorted, String outputFilename) throws FileNotFoundException {

        PrintWriter out = new PrintWriter(outputFilename);
        for (Object n : sorted) {
            out.println(n);
        }
        out.close();

    }

}
