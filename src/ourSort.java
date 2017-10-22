
public class ourSort {
	/*
	 * Here we sorted based on quick sort but do some improvement.
	 * 1.For sorting prime numbers, we have quick sort(median pivot) + insert sort + same number together
	 * 2.For sorting common numbers, we have quick sort(median pivot) + insert sort
	 * Note: type should use long
	 * 
	 */
	
	
	/*
	 *  This is quick sort with insert sort, median pivot, same number together--1
	 */
	public static void primeQSort(Num arr[],int low,int high)  
	{  
	    int first = low;  
	    int last = high;  
	  
	    int left = low;  
	    int right = high;  
	  
	    int leftLen = 0;  
	    int rightLen = 0;  
	  
	    if (high - low + 1 < 10)  
	    {  
	    	insertSortPrime(arr,low,high);  
	        return;  
	    }  
	      
	    //first partition  
	    Num key = SelectPivotMedianPrime(arr,low,high);//find the pivot based on the prime
	          
	    while(low < high)  
	    {  
	        while(high > low && arr[high].getPrime() >= key.getPrime())  
	        {  
	            if (arr[high].getPrime() == key.getPrime())
	            {  
	                swap(arr,right,high);  
	                right--;  
	                rightLen++;  
	            }  
	            high--;  
	        }  
	        arr[low] = arr[high];  
	        while(high > low && arr[low].getPrime() <= key.getPrime())  
	        {  
	            if (arr[low].getPrime() == key.getPrime())  
	            {  
	                swap(arr,left,low);  
	                left++;  
	                leftLen++;  
	            }  
	            low++;  
	        }  
	        arr[high] = arr[low];  
	    }  
	    arr[low] = key;  
	  
  
	    //move the elements have same prime with pivot key to their correct position.  
	    int i = low - 1;  
	    int j = first;  
	    while(j < left && arr[i].getPrime() != key.getPrime())  
	    {  
	        swap(arr,i,j);  
	        i--;  
	        j++;  
	    }  
	    i = low + 1;  
	    j = last;  
	    while(j > right && arr[i].getPrime() != key.getPrime())  
	    {  
	        swap(arr,i,j);  
	        i++;  
	        j--;  
	    }  
	    primeQSort(arr,first,low - 1 - leftLen);  
	    primeQSort(arr,low + 1 + rightLen,last);  
	}  
	
	private static Num SelectPivotMedianPrime(Num[] arr,int low,int high)  
	{  
	    int mid = (high - low)/2;
	    if (arr[mid].getPrime() > arr[high].getPrime())
	    {  
	        swap(arr, mid,high);  
	    }  
	    if (arr[low].getPrime() > arr[high].getPrime())
	    {  
	        swap(arr,low,high);  
	    }  
	    if (arr[mid].getPrime() > arr[low].getPrime()) 
	    {  
	        swap(arr,mid,low);  
	    }  
	    return arr[low];  
	}  
	
	
	private static void insertSortPrime(Num[] arr, int low, int high){
		
	        for(int j= low+1;j<= high ;j++){  
	            Num key= arr[j];  
	            int i=j-1;  
	            while(i>=low && arr[i].getPrime()>key.getPrime()){  
	                arr[i+1]=arr[i];
	                i = i-1;        
	            }
	            arr[i+1]=key;   
	        } 
		
		
	}
	
	
	/*
	 * This is quick sort with insert sort, median pivot, same elements together--2
	 */
	
	public static void qInsort(Num[] arr, int low, int high){
		
		if (low < high)  
	    {  
	    	insertSort(arr,low,high);  
	        return;  
	    } 
	    
		
		if (low < high){
			int pivot=partition(arr, low, high);        
	        qInsort(arr, low, pivot-1);                   
	        qInsort(arr, pivot+1, high); 
		}
		
	}
	
	private static int partition(Num[] arr, int low, int high){
	    Num pivot = SelectPivotMedianOfThree(arr,low,high);     
	    while (low<high){
	        while (low<high && arr[high].getValue()>=pivot.getValue()) --high;
	        arr[low]=arr[high];             
	        while (low<high && arr[low].getValue()<=pivot.getValue()) ++low;
	        arr[high] = arr[low];          
	    }
	    arr[low] = pivot;
	    return low;
	}
	
	/*
	public static void qInsort(Num[] arr, int low, int high){
			int first = low;  
			int last = high;  
		  
		    int left = low;  
		    int right = high;  
		  
		    int leftLen = 0;  
		    int rightLen = 0;  
		  
		    if (high - low + 1 < 10)  
		    {  
		    	insertSort(arr,low,high);  
		        return;  
		    }  
		      
		    //first partition  
		    Num key = SelectPivotMedianOfThree(arr,low,high);//find the pivot based on the prime
		          
		    while(low < high)  
		    {  
		        while(high > low && arr[high].getValue() >= key.getValue())  
		        {  
		            if (arr[high].getValue() == key.getValue())
		            {  
		                swap(arr,right,high);  
		                right--;  
		                rightLen++;  
		            }  
		            high--;  
		        }  
		        arr[low] = arr[high];  
		        while(high > low && arr[low].getValue() <= key.getValue())  
		        {  
		            if (arr[low].getValue() == key.getValue())  
		            {  
		                swap(arr,left,low);  
		                left++;  
		                leftLen++;  
		            }  
		            low++;  
		        }  
		        arr[high] = arr[low];  
		    }  
		    arr[low] = key;  
		  
	  
		    //move the elements have same prime with pivot key to their correct position.  
		    int i = low - 1;  
		    int j = first;  
		    while(j < left && arr[i].getValue() != key.getValue())  
		    {  
		        swap(arr,i,j);  
		        i--;  
		        j++;  
		    }  
		    i = low + 1;  
		    j = last;  
		    while(j > right && arr[i].getValue() != key.getValue())  
		    {  
		        swap(arr,i,j);  
		        i++;  
		        j--;  
		    }  
		    qInsort(arr,first,low - 1 - leftLen);  
		    qInsort(arr,low + 1 + rightLen,last);  
	}
	*/
	

	private static Num SelectPivotMedianOfThree(Num[] arr,int low,int high)  
	{  
	    int mid = (high - low)/2;
	    if (arr[mid].getValue() > arr[high].getValue())
	    {  
	        swap(arr, mid,high);  
	    }  
	    if (arr[low].getValue() > arr[high].getValue())
	    {  
	        swap(arr,low,high);  
	    }  
	    if (arr[mid].getValue() > arr[low].getValue()) 
	    {  
	        swap(arr,mid,low);  
	    }  
	    return arr[low];  
	}  
	
	
	private static void insertSort(Num[] arr, int low,int high){
        for(int j= low+1;j<= high ;j++){  
        	
            Num key= arr[j];  
           
            int i=j-1; 
            
            while(i>=low && arr[i].getValue()>key.getValue()){  
            	
                arr[i+1]=arr[i];
           
                i = i-1;        
            }
            arr[i+1]=key; 
        } 
	}
	
	
	/*
	 * helper function: swap elements in an array
	 */
	private static void swap(Num[] a,int index1, int index2){
		Num temp = a[index1];
		a[index1] = a[index2];
		a[index2] = temp;
	}
	
	
}
