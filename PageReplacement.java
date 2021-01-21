import java.util.Arrays;
import java.util.Random;

public class PageReplacement {

	public static void main(String[] args) {
		
		System.out.println("Project: Page Replacement Manager");
		System.out.println("By: Mai Pham");
		

		int stringReference [] = new int [30];							// array to hold string reference, length 30
		for (int i = 0; i < 50; i++) {									// do 50 trails
			System.out.println("\nTest Case: " + (i+1));
			generated(stringReference, 8);								// create 1 string from 0 - 7
			
			System.out.println("    First In First Out:");				// use FIFO algorithm
			System.out.println("\t*Page Frame: 3");
			FIFO(stringReference, 3);
			System.out.println("\t*Page Frame: 4");
			FIFO(stringReference, 4);
			System.out.println("\t*Page Frame: 5");
			FIFO(stringReference, 5);
			System.out.println("\t*Page Frame: 6");
			FIFO(stringReference, 6);
			
			System.out.println("\n    Least Recently Used:");			// use LRU algorithm
			System.out.println("\t*Page Frame: 3");
			LRU(stringReference, 3);
			System.out.println("\t*Page Frame: 4");
			LRU(stringReference, 4);
			System.out.println("\t*Page Frame: 5");
			LRU(stringReference, 5);
			System.out.println("\t*Page Frame: 6");
			LRU(stringReference, 6);
			
			System.out.println("\n    Optimal:");						// use Optimal algorithm
			System.out.println("\t*Page Frame: 3");
			Optimal(stringReference, 3);
			System.out.println("\t*Page Frame: 4");
			Optimal(stringReference, 4);
			System.out.println("\t*Page Frame: 5");
			Optimal(stringReference, 5);
			System.out.println("\t*Page Frame: 6");
			Optimal(stringReference, 6);	
		}
		
		
		// simple test cases
		/*
		int string[] = {1, 3, 5, 7, 3, 2, 3, 4, 5, 0, 5, 1, 7, 4, 0};	
		System.out.println("string 1 = " + string.length);
		FIFO(string, 5);
		LRU(string, 5);
		Optimal(string, 5);
		
		int string2[] = {7, 0, 1, 2, 0, 3, 0, 4, 2, 3, 0, 3, 2, 1, 2, 0, 1, 7, 0, 1};	
		System.out.println("string 2 = " + string2.length);
		FIFO(string2, 3);
		LRU(string2, 3);
		Optimal(string2, 3);
	
		int string3[] = {7, 6, 7, 7, 3, 6, 4, 7, 2, 5, 2, 5, 3, 5, 7, 3, 1, 2, 6, 2, 1, 3, 2, 0, 3, 1, 1, 5, 6, 5};
		FIFO(string3, 6);
		LRU(string3, 6);
		Optimal(string3, 6);
		*/
	}
	
	
	// random generated function
	static void generated(int arr[], int pages){
		Random random = new Random();
		
		// fill array with random number from pages range 
		for (int i = 0; i < arr.length; i++)
			arr[i] = random.nextInt(pages);
	} 
	
	
	// First In First Out Algorithm
	static void FIFO(int string[], int pageFrame){
		
		// to hold each page frame as a string
		String memory[] = new String [pageFrame];
		Arrays.fill(memory, "");
		
		// separate current columns to add into memory (string reference)
		int current[] = new int [pageFrame];
		Arrays.fill(current, -1);
		
		int position = 0;
		String pageFault = "";
		boolean inMemory = false;
		
		for (int i = 0; i < string.length; i++) {
			for (int j = 0; j < pageFrame; j++) {
				// if already in memory, copy and move on
				if (current[j] == string[i])	{
					inMemory = true;
					pageFault += " ";
					break;
				}
				else
					inMemory = false;
			}
			
			// else add new page into current array  
			if (inMemory == false) {
				current[position%pageFrame] = string[i];
				//System.out.println("insert " + current[position%pageFrame] + " at " + (position%pageFrame));
				pageFault += "x";
				position++;
			}
			
			// now add current array into memory to create output page frame
			for (int k = 0; k < pageFrame; k++ ) {
				if (current[k] == -1)
					memory[k] += " ";
				else
					memory[k] += current[k];
			}
		}
		
		// call print function for output
		print(string, memory, pageFault);
	}
	
	
	// Least Recently Used Algorithm
	static void LRU(int string[], int pageFrame){
		
		// to hold each page frame as a string
		String memory[] = new String [pageFrame];
		Arrays.fill(memory, "");
		
		//current columns to add into memory (string reference)
		int current[] = new int [pageFrame];
		Arrays.fill(current, -1);
		
		String pageFault = "";
		boolean inMemory = false;
		
		for (int i = 0; i < string.length; i++) {
			for (int j = 0; j < pageFrame; j++) {
				// if already in memory, copy and move on
				if (string[i] == current[j])	{
					inMemory = true;
					pageFault += " ";
					break;
				}
				else
					inMemory = false;
			}

			// else add new page into current array 
			if (inMemory == false) {
				// new array to track which page is least recently used
                int leastUsed[] = new int [pageFrame];
                Arrays.fill(leastUsed, -1);
                
                // store page less recently used index
				for (int w = 0; w < pageFrame; w++) {
					int index = i;
					while (index >= 0) {
						if (current[w] == string[index]) {
							leastUsed[w] = index;
							break;
						}
					index--;
					}
				} 
				
				// pick the most least recently spot to replace it
				int position = leastUsed[pageFrame-1];
				int positionIndex = pageFrame-1;
				for (int m = pageFrame-1; m >= 0; m--) {
					if (leastUsed[m] <= position) {
						position = leastUsed[m];
						positionIndex  = m;
					}		
				}
				
				// replace that spot
				current[positionIndex] = string[i];
				pageFault += "x";
			}
			
			// now add memory column into memory to create output page frame
			for (int m = 0; m < pageFrame; m++) {
				if (current[m] == -1)
					memory[m] += " ";
				else
					memory[m] += current[m];
			}
		}
		
		// call print function for output
		print(string, memory, pageFault);
	}
	
    
    // Optimal Algorithm
	static void Optimal(int string[], int pageFrame){
		
		// to hold each page frame as a string
		String memory[] = new String [pageFrame];
		Arrays.fill(memory, "");
		
		//current columns to add into memory (string reference)
		int current[] = new int [pageFrame];
		Arrays.fill(current, -1);

		String pageFault = "";
		boolean inMemory = false;
		
		for (int i = 0; i < string.length; i++) {
			//System.out.println("---index = " + i);
			for (int j = 0; j < pageFrame; j++) {
				// if already in memory, copy and move on
				if (string[i] == current[j])	{
					inMemory = true;
					pageFault += " ";
					break;
				}
				else
					inMemory = false;
			}
			
			// else add new page into current array 
			if (inMemory == false) {
				// new array to track which page is least futher used
				int optimalSpot[] = new int [pageFrame];
				Arrays.fill(optimalSpot, string.length);
				
                // store page less recently used index
				for (int w = 0; w < pageFrame; w++) {
					int index = i;
					while (index < string.length) {
						if (current[w] == string[index]) {
							optimalSpot[w] = index;
							//System.out.println("optimal spot " + optimalSpot[w] + " at " + w);
							break;
						}
						// else if empty use that spot first
						else if (current[w] == -1)
							optimalSpot[w] = string.length*2;
					index++;
					}
				}
                /* for debugging purpose
				for (int m = 0; m < pageFrame; m++)
					System.out.println("hold " + m + "=" + optimalSpot[m]);
				*/
				
				// pick the most least futher used spot to replace it
				int position = optimalSpot[pageFrame-1];
				int positionIndex = pageFrame-1;
				for (int m = pageFrame-2; m >= 0; m--) {
					if (optimalSpot[m] >= position) {
						position = optimalSpot[m];	
						positionIndex = m;
					}		
				}
                
				// replace that spot
				current[positionIndex] = string[i];	
				pageFault += "x";
				//System.out.println("replace " + string[i] +" at " + positionIndex);
			}
            
			// now add memory column into memory to create output page frame
			for (int m = 0; m < pageFrame; m++ ) {
				if (current[m] == -1)
					memory[m] += " ";
				else
					memory[m] += current[m];
			}
		}
        
		// call print function for output
		print(string, memory, pageFault);
	}
	
	static void print(int string[], String memory[], String pageFault) {
		//int pageFault [] = new int[string.length];
		int fault = 0;
		
		// print the string
		System.out.print("\t    ");
		for (int i = 0; i < string.length; i++ ) 
			System.out.print(string[i] + " ");
		
		// print underline
		System.out.print("\n\t    ");
		for (int i = 0; i < string.length; i++ ) 
			System.out.print("--");
		
		// print page frame
		for (int i = 0; i < memory.length; i++ ) {
			System.out.print("\n\t    ");
			//int temp = memory[i].charAt(i);
			for (int j = 0; j < memory[i].length(); j++ ) 
				System.out.print(memory[i].charAt(j) + " ");
		}
	
		// print x for each page fault
		System.out.print("\n\t    ");
		for (int i = 0; i < pageFault.length(); i++ ) {
			System.out.print(pageFault.charAt(i) + " ");
			if (pageFault.charAt(i) == 'x')
				fault++;
		}
        
		// print total page faults
		System.out.println("\n\t    Pages Fault = " + fault);
	}
}
