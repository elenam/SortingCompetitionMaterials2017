import java.util.*;

public class MergeSort {

	public static void mergeSort(String[]toSort, Comparator<String> c) {
		String[] destination = new String[toSort.length];
		mergeSort(toSort, destination, 0,toSort.length - 1, c);
	}

	private static void mergeSort(String[]toSort, String[] destination, int start, int right, Comparator<String> c) {
		if (start < right) {
			int middle = (start + right) / 2;
			mergeSort(toSort, destination, start, middle, c);
			mergeSort(toSort, destination, middle + 1, right, c);
			merge(toSort, destination, start, middle + 1, right, c);
		}
	}

	private static void merge(String[]toSort, String[] destination, int start, int right, int rightEnd, Comparator<String> c) {
		int startEnd = right - 1;
		int k = start;
		int num = rightEnd - start + 1;

		while (start <= startEnd && right <= rightEnd)
			if (c.compare(toSort[start],toSort[right]) <= 0)
				destination[k++] =toSort[start++];
			else
				destination[k++] =toSort[right++];

		while (start <= startEnd)
			destination[k++] =toSort[start++];

		while (right <= rightEnd)
			destination[k++] =toSort[right++];

		for (int i = 0; i < num; i++, rightEnd--)
			toSort[rightEnd] = destination[rightEnd];
	}
}