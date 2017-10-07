# SortingCompetitionMaterials2017

Materials for UMM CSci 3501 "Algorithms and computability" 2017 sorting competition.

# Table of contents
* [Goal of the competition](#goal)
* [The data](#data)
* [How is the data generated](#generating)
* [How do you need to sort the data](#sortingRules)
* [Setup for sorting](#setup)
* [Submision deadlines](#deadlines)
* [Scoring](#scoring)
* [System specs](#specs)
* [Results of the first preliminary round](#round1)


## Goal of the competition <a name="goal"></a>

The Sorting Competition is a multi-lab exercise on developing the fastest sorting algorithm for a given type of data. By "fast" we mean the actual running time and not the Big-Theta approximation. The solutions are developed in Java and will be ran on a single processor.

## The data  <a name="data"></a>

Data for this sorting competition consists of posistive numbers of 17 decimal digits or less, passed to the sorting 
method as an array of strings. 

The length of the numbers is most likely to be 5 or 6 decimal digits (the probability of the length being 5 or 6 is 1/6 each). The probability of lengths decreases similar to a bell curve as the length gets smaller or larger than 5 or 6. More specifically, the chances of each length are as follows, out of 1500 total:
```
{10, 45, 100, 180, 250, 250, 200, 100, 80, 70, 60, 50, 40, 30, 20, 10, 5}
``` 
Here 10 out of 1500 is the probability of a single digit number, 45 out of 1500 is the probability of a two-digit number, etc.

## How is the data generated <a name="generating"></a>

First the length is generated, according to the probabilities listed above. Then the digits are generated, with the most significant digit chosen among non-zero digits (to make sure that the number does indeed have the correct length). More details are in the file [DataGenerator2017.java](src/DataGenerator2017.java).

The generated numbers are written into a data file one per line. Sample data files are: [data1.txt](data1.txt) (1000 elements), [data2.txt](data2.txt) (1000 elements), and [data3.txt](data3.txt) (10000 elements). 

## How do you need to sort the data <a name="sortingRules"></a>

Given a number N, let P(N) be the product of the two smallest different prime factors of N, or the only prime factor of N
if it only has one. P(1) = 1 by definition. For instance:

* P(100) = 10 
* P(8) = 2
* P(17) = 17
* P(121) = 11

The data is sorted by the following comparison:

* If P(N1) < P(N2) then N1 is considered to be smaller than N2
* If P(N1) > P(N2) then N1 is considered to be larger than N2
* If P(N1) = P(N2) then N1 and N2 are compared as regular numbers

For instance:

* 10000 is smaller than 15 since P(10000) = 10, P(15) = 15, and P(10000) < P(15)
* 20 is smaller than 100 since P(20) = P(100) = 10, and 20 < 100
* 1 is smaller than 2 since P(1) = 1 and P(2) = 2 

The file [Group0.java](src/Group0.java) provides a Comparator that implements this comparison and provides some tests. Please
consult it as needed. However, note that this is a very slow implementation, and you should think of a way to make it much faster. 

Once the data is sorted, it is written out to the output file, also one number per line, in the increasing order (according to the comparison given above). The files [out1.txt](out1.txt), [out2.txt](out2.txt), and [out3.txt](out3.txt) have the results of sorting for the three given data files. 

## Setup for sorting <a name="setup"></a>

The file [Group0.java](src/Group0.java) provides a template for the setup for your solution. Your class will be called `GroupN`, where `N` is the group number that is assigned to your group. The template class runs the sorting method once before the timing for the [JVM warmup](https://www.ibm.com/developerworks/library/j-jtp12214/index.html). It also pauses for 10ms before the actual test to let any leftover I/O or garbage collection to finish. Since the warmup and the actual sorting are done on the same array (for no reason other than simplicity), the array is cloned from the same input data. 

The data reading, the array cloning, the warmup sorting, and writing out the output are all outside of the timed portion of the method, and thus do not affect the total time. 

You may not use any global variables that depend on your data. You may, however, have global constants that are initialized to fixed values (no computation!) before the data is being read and stay the same throughout the run. These constants may be arrays of no more than 1000 `long` numberss or equivalent amount of memory. For instance, if you are storing an array of objects that contain two `long` fields, you can only have 500 of them. We consider one `long` to be the same as two `int` numbers, so you can store an array of 2000 `int` numbers.  
If in doubt about specific cases, please discuss with me. 

The method in the [Group0.java](src/Group0.java) files that you may modify is the `sort` method. It must take the array of strings. The return type of the method can be what it is now, which is the same as the parameter type `String []`, or it can be a different array type. If you are sorting in-place, i.e. the sorted result is in the same array, then you can just return a reference to that array, as my prototype method does, or make your sorting method `void`. If you are returning a different type of an array, the following rules have to be followed:
* Your `sort` method return type needs to be changed to whatever  array you are returning, and consequently the type of `sorted` array in `main` needs to be changed. 
* Your return type has to be an array (not an array list!) and it has to have the same number of elements as the original array. 
* You need to supply a method to write out your result into a file. The file has to be exactly the same as in the prototype implementation; they will be compared using `diff` system command. 

If you are not changing the return type, you don't need to modify anything other than `sort` method. 

Even though you are not modifying anything other than the `sort` method, you still need to submit your entire class: copy the template, rename the Java class to your group number, and change the`sort` method. You may use supplementary classes, just don't forget to submit them. Make sure to add your names in comments when you submit. 

Your program must print **the only value**, which is the **time** (as it currently does). Any other printed output disqualifies your submission. If you use test prints, make sure to turn them off for submission. 

**Important:** if the sorting times may be too small to distinguish groups based on just one run of the sorting, so I may loop over the sorting section multiple times. If this is the case, I will let you know no later than a day after the preliminary competition and will modify `Group0` file accordingly.  

## Submision deadlines <a name="deadlines"></a>

*Friday, Oct 6* in class (1pm) is the *first preliminary* competition. Please send me all your materials no later than 10pm on Thursday, Oct 5. This is required for everyone in the class. Groups remain anonymous after this phase, but all the solutions (in bytecode) become available. 

*The second preliminary competition* is done remotely on *Saturday October 14*. Any submission sent before noon on that day will be included. Earlier submissions are certainly welcome. I will run the latest working (i.e. compiling and not going into an infinite loop) submissison I have for each group. 

*Thursday, Oct 19* in the lab (2pm) is the *final* competition. All source code is posted immediately after that. Those in class will have their names revealed, others may choose to remain anonymous (but the code will still be posted). 

Note that there are several more parts of the Algorithms assignment, including presentations and a write-up. I will post the dates for those later. Obviously, these are only for students in the class. 

## Scoring <a name="scoring"></a>

The programs are tested on a few (between 1 and 3) data sets. For each data set each group's program is run three times, the median value counts. The groups are ordered by their median score for each data file and assigned places, from 1 to N. 

The final score is given by the sum of places for all data sets. If the sum of places is equal for two groups, the sum of median times for all the runs resolves the tie. So if one group was first for one data set and third for the other one (2 sets total being run), it scored better than a group that was third for the first data set and second for the other. However, if one group was first for the first set and third for the other one, and the second group was second in both, the sum of times determines which one of them won since the sum of places is the same (1 + 3 = 2 + 2). 

If a program has a compilation or a runtime error, doesn't sort correctly, or prints anything other than the total time in milliseconds, it gets a penalty of 1000000ms for that run. 

## System specs <a name="specs"></a>

The language used is Java 8 (as installed in the CSci lab). It's ran on a single CPU core.  

I will post a script for running this program (with a correctness check and all), but for now a couple of things to know: run your program out of `/tmp` directory to avoid overhead of communications with the file server, and pin your program to a single core, i.e. run it like this:
``taskset -c 0 java GroupN``

## Results of the first preliminary round <a name="round1"></a>

The results of the first preliminary round are posted in the folder [round1/bin](round1/bin). The folder has all the `.class` files. Groups that didn't have a submission (or their submission didn't compile or would go into an infinite loop) were replaced by a file that just had a `main` method printing `100000`. 

Each data file was ran three times for each group, and the median result was used for scoring. 

The data files were: [prelim1.txt](round1/bin/prelim1.txt) (700 numbers) and [prelim2.txt](round1/bin/prelim2.txt) (400 numbers). The correct output is in files [outRun1Group0.txt](round1/bin/outRun1Group0.txt) and [outRun2Group0.txt](round1/bin/outRun2Group0.txt).

The files [results1.txt](round1/bin/results1.txt) and [results2.txt](round1/bin/results2.txt) have the complete timing results for the two data sets. The file [scoreboard.txt](round1/bin/scoreboard.txt) has the places that each team got.  

The ruby script [run_all.rb](round1/bin/run_all.rb) was used to run the programs. If you want to reproduce the results or try them on a different set, so the following:
* Create a directory in `/tmp` directory on a lab machine. 
* Copy the entire `bin` folder from github into that directory. 
* Remove the output files, results1.txt, results2.txt, and the scoreboard.txt. 
* If you want to run the programs on different data sets, call your data files `prelim1.txt` and 'prelim2.txt` and copy them into the same folder - or copy then by different names, abnd then open the  script and change the files in the `inFileNames`. 
* Type `taskset -c 0 ruby run_all.rb` to run the script. 

