import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;

public class nodeSort<E>  {

	Node first = null;
	Node end = null;
	ArrayList temp = new ArrayList<Long>();
	long[] finalArr;

	//returns true if the first node is empty, else false
	public boolean empty(Node current) {
		return current == null;
	}

	//sets the size of the int[] that the values will eventually be pushed into so they can be sorted
	public void setSize(int size){
		finalArr = new long[size];
	}

	//Takes a node with the product of the primes, and checks if it has the same product as the next node. If it does, it puts the original value
	//(the one that needs to actually be sorted) into that bucket, otherwise the node moves on.
	public void push(long value, String actual) {
		Node storage = null;
		Node pivot = first;
		int count = 0;
		long theNum = Long.parseLong(actual);
		
		//if the list is empty, the node is automatically placed on the list
		if (empty(first) == true){
			Node current = new Node(value, null, 0);
			first = current;
			pushBottom(current, actual, count, value);

		//if the first node has a larger product value than the one we are placing, the node we are placing is put in front of the current
		//first node
		} else if(first.value > value){
			storage = pivot;

			Node current = new Node(value, storage, 0);
			first = current;
			pushBottom(current, actual, count, value);

		//if there is no node directly to the right of the first node, place it there. Otherwise check if the node to the right of the 
		//is bigger, smaller or equal to the product value of the node being placed, and act accordingly
		} else if(first.right == null) {
			if(first.value == value){
				pushBottom(first, actual, count, value);

			} else if(first.value < value){
				Node current = new Node(value, null, 0);
				count = count + first.count;
				first.right = current;
				pushBottom(current, actual, count, value);
			} else {
				Node current = new Node (value, first, 0);
				first = current;
				pushBottom(current, actual, count, value);
			}
		
		//if the product value of the first node is equal to the one being placed, put the original value into the first bucket
		} else if(first.value == value){
			pushBottom(first, actual, count, value);

		} else {
			
			//goes through the node list, and either places the original value in the corresponding bucket if the product value of the original is 
			//equal to that of the node on the list being checked, or otherwise places the current node into the list in the correct position.
			while(pivot.right != null){
				if(pivot.right.right == null){
					if(pivot.right.value < value){
						Node current = new Node(value, null, 0);
						pivot.right.right = current;
						count = count + pivot.count + pivot.right.count;
						pushBottom(current, actual, count, value);
						break;
					} else {
					Node current = new Node(value, pivot.right, 0);
					pivot.right = current;
					count = count + pivot.count;
					pushBottom(current, actual, count, value);
					break;
					}
				}
				if(pivot.right.value < value){
					count = count + pivot.count;
					pivot = pivot.right;

				} else if(pivot.right.value == value){
					count = count + pivot.count;
					pushBottom(pivot.right, actual, count, value);
					break;

				} else{
					storage = pivot.right;

					Node current = new Node(value, storage, 0);
					pivot.right = current;
					count = count + pivot.count;
					pushBottom(current, actual, count, value);
					break;
				}

			}
		}
	}

	//Deals with the values put in each individual bucket
	private void pushBottom(nodeSort<E>.Node top, String actual, int value, long hold) {
		int end = top.count + value;
		long theNum = Long.parseLong(actual);
		
		//takes the values that are placed in each bucket and adds it to the ArrayList at the corresponding position of each bucket.
		temp.add(end, theNum);
		top.count++;
	}

	//sorts each individual bucket
	public void sort() {
		Node current = first;
		int start = 0;
		int end = current.count + start; 
		
		//goes through the ArrayList and uses TimSort to sort each individual bucket in the ArrayList. For example, if a bucket has 5 values 
		//in it, it'll take up indexes 0 - 4 on the ArrayList. TimSort will then go through and only sort indexes 0-4 without touching the other indexes
		while(current.right != null){
			if(end - start == 1){
				start = end;
				current = current.right;
				end = start + current.count;
			} else {
				Arrays.sort(finalArr, start, end);
				start = end;
				current = current.right;
				end = start + current.count;
			}
		}
	}

	//prints the contents of each bucket, used for testing purposes only
	public void print(String[] toSort){
		Node current = first;
		Node bottom = null;

		/*for(int i = 0; i < temp.size(); i++){
			System.out.println(finalArr[i]);
		}*/
		
		while(current.right != null){
			System.out.println(current.count);
			current = current.right;
		}
		System.out.println();
		System.out.println();
		System.out.println();
	}

	//Takes the values in the ArrayList used to fill each bucket and puts them in an int[] so they can be sorted using TimSort 
	public void array() {
		Node current = first;
		Node bottom = null;

		for(int i = 0; i < finalArr.length; i++){
			finalArr[i] = (long) temp.get(i);
		}

	}
	
	//takes the int[] and transfers the values back into a string[] so it can be returned later, because we are too lazy to change 
	//everything in main to make it take an int[] instead of a string[]
	public long[] makeFinal(String[] toSort) {
		for(int i = 0; i < toSort.length; i++){
			toSort[i] = Long.toString(finalArr[i]);
		}
		
		return finalArr;
		
	}


	//makes the node class
	private class Node {
		long value;
		Node right;
		Node bottom;
		int count;

		public Node(long val, Node rght, int num) {
			value = val;
			right = rght;
			count = num;

		}
	}
}








