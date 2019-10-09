package testing;

import project.HashDirectory;
import uk.ac.ncl.teach.ex.project.StopWatch;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class HashDirectoryStopWatchTest implements Test{
private StopWatch stopWatch = new StopWatch();
	
	public HashDirectoryStopWatchTest() {
		testPrintDirectory();
		System.out.println("\n\n");
		testAddEntry();
		System.out.println("\n\n");
		testRemoveEntryByTelExt();
		System.out.println("\n\n");
		testRemoveEntryByName();
		System.out.println("\n\n");
		testChangeNumber();
		System.out.println("\n\n");
		testLookupTelExtNum();
	}
	
	// Calculates the average timing stored in a List passed as an argument
		private Long getAvarageTime(List<Long> timings) {
			Long sum = 0L;
			
			if(!timings.isEmpty()) { // Check to ensure List is not empty
				for (Long timeElapsed : timings) { // For loop sums all the times stored in timings
					sum += timeElapsed;
				}
				return sum / timings.size(); // Divides the sum by number of elements to get average time
			}
			return sum; // Default return if list is empty
		}
		
		// Calculates the best timing stored in a List passed as an argument
		private Long getBestTime(List<Long> timings) {
			Long fastest = 0L;
			
			if(!timings.isEmpty()) { // Check to ensure List is not empty
				fastest = timings.get(0); // Sets the initial value of fastest timing to the first element in the list
				for (Long timeElapsed : timings) { // Iterates over each element in the list
					if (timeElapsed < fastest) { // If the element being compared to fastest is smaller
						fastest = timeElapsed;   // Assign fastest to the value of the element being compared
					}
				}
				return fastest; // Return the timing which has been calculated as the fastest
			}
			return fastest; // Default return statement. Returned if the List 'timings' is empty
		}
		
		// Calculates the worst timings stored in the List passed to the method as an arguments
		private Long getWorstTime(List<Long> timings) {
			Long worst = 0L;
			
			if(!timings.isEmpty()) { // Check to ensure List is not empty
				worst = timings.get(0); // Sets the initial value of worst to the first element in the list
				for (Long timeElapsed : timings) { // Iterates over each element in the list
					if (timeElapsed > worst) { // If the element being compared to worst is larger
						worst = timeElapsed;   // Assign worst to the value of the element being compared
					}
				}
				return worst; // Return the worst timing found
			}
			return worst; // Default return statement. Returned if the List 'timings' is empty
		}


	
	@Override
	public void testAddEntry() {
		HashDirectory directory;
		Long timeElapsed; //Stores an individual timing from Stopwatch.getElapsedTime()
		Long avarage; // Stores the returned average time from getAvarageTime()
		Long best; // Stores the returned best time from getBestTime()
		Long worst; // Stores the worst time which is returned by getWorstTime()
		List<Long> timings = new ArrayList<Long>(); //Used to store multiple timings so they can be compared
		directory = new HashDirectory();
		Scanner inFile;
		try {
			inFile = new Scanner(new FileReader("names.txt"));
		
		// Loop which reads in data from a text file. Stores it in an array
		// and uses the array elements as arguments for directory.addEntry()
		while (inFile.hasNextLine()) {
			String entry = inFile.nextLine(); // Stores each line of data in a string
			entry.trim(); //Trims the string of whitespace
			String[] entryArgs = entry.split("\\s+"); //Stores the string in an array using \\s+ as a delimiter

			// Calculates the speed of directory.addEntry()
			stopWatch.start();
			directory.addEntry(entryArgs[0], entryArgs[1], entryArgs[2]);
			stopWatch.stop();
			//Stores the value from the above calculation, in the variable timeElapsed
			timeElapsed = stopWatch.getElapsedTime();
			
			// Adds the current value of timeElapsed to the list of timings
			timings.add(timeElapsed);
			// resets the stopWatch for the next operation
			stopWatch.reset();
		}
		inFile.close();
	
		avarage = getAvarageTime(timings);
		best = getBestTime(timings);
		worst = getWorstTime(timings);
		System.out.format("Timings for HashDirectory addEntry method:\nAvarage: %d\nBest: %d\nWorst: %d", avarage, best, worst);
		
		} catch (FileNotFoundException e) {
			System.out.println("Error! File not found. Please ensure that an appropriate text file is available");
			e.printStackTrace();
		}
	}

	@Override
	public void testRemoveEntryByTelExt() {
		HashDirectory directory; //ArrayDirectory object that will be used for the tests
		Long timeElapsed; //Stores an individual timing from Stopwatch.getElapsedTime()
		List<Long> timings = new ArrayList<Long>(); //Used to store multiple timings so they can be compared
		Long avarage; // Stores the returned average time from getAvarageTime()
		Long best; // Stores the returned best time from getBestTime()
		Long worst; // Stores the worst time which is returned by getWorstTime()
		final String emptyString = "";

		directory = new HashDirectory(); //Instantiates the ArrayDirectory
		Scanner inFile;
		try {
			inFile = new Scanner(new FileReader("Names.txt"));
		
		// Loop which reads in data from a text file. Stores it in an array
		// and uses the array elements as arguments for directory.removeEntry()
		while (inFile.hasNextLine()) {
			String entry = inFile.nextLine(); // Stores each line of data in a string
			entry.trim(); //Trims the string of whitespace
			String[] entryArgs = entry.split("\\s+"); //Stores the string in an array using \\s+ as a delimiter

			// Calculates the speed of directory.removeEntry()
			stopWatch.start();
			directory.removeEntry(emptyString, emptyString, entryArgs[2]);
			stopWatch.stop();
			//Stores the value from the above calculation, in the variable timeElapsed
			timeElapsed = stopWatch.getElapsedTime();
			
			// Adds the current value of timeElapsed to the list of timings
			timings.add(timeElapsed);
			// resets the stopWatch for the next operation
			stopWatch.reset();
		}
		inFile.close();

		// Calls methods to return the average, best and worst times recorded by textAddEntry and store them in variables
		avarage = getAvarageTime(timings);
		best = getBestTime(timings);
		worst = getWorstTime(timings);
		
		// Print the average, best and worst timings
		System.out.format("Timings for HashDirectory removeEntry using telExt to search method:\nAvarage: %d\nBest: %d\nWorst: %d", avarage, best, worst);
		} catch (FileNotFoundException e) {
			System.out.println("Error! File not found. Please ensure that an appropriate text file is available");
			e.printStackTrace();
		}

		
	}

	@Override
	public void testRemoveEntryByName() {
		HashDirectory directory; //ArrayDirectory object that will be used for the tests
		Long timeElapsed; //Stores an individual timing from Stopwatch.getElapsedTime()
		List<Long> timings = new ArrayList<Long>(); //Used to store multiple timings so they can be compared
		Long avarage; // Stores the returned average time from getAvarageTime()
		Long best; // Stores the returned best time from getBestTime()
		Long worst; // Stores the worst time which is returned by getWorstTime()
		final String emptyString = "";

		directory = new HashDirectory(); //Instantiates the ArrayDirectory
		Scanner inFile;
		try {
			inFile = new Scanner(new FileReader("Names.txt"));
	
		// Loop which reads in data from a text file. Stores it in an array
		// and uses the array elements as arguments for directory.removeEntry()
		while (inFile.hasNextLine()) {
			String entry = inFile.nextLine(); // Stores each line of data in a string
			entry.trim(); //Trims the string of whitespace
			String[] entryArgs = entry.split("\\s+"); //Stores the string in an array using \\s+ as a delimiter

			// Calculates the speed of directory.removeEntry()
			stopWatch.start();
			directory.removeEntry(entryArgs[0], entryArgs[1], emptyString);
			stopWatch.stop();
			//Stores the value from the above calculation, in the variable timeElapsed
			timeElapsed = stopWatch.getElapsedTime(); // Adds the current value of timeElapsed to the list of timings
			timings.add(timeElapsed);
			// resets the stopWatch for the next operation
			stopWatch.reset();
			
			
		}
		inFile.close();

		// Calls methods to return the average, best and worst times recorded by textAddEntry and store them in variables
		avarage = getAvarageTime(timings);
		best = getBestTime(timings);
		worst = getWorstTime(timings);
		
		// Print the average, best and worst timings
		System.out.format("Timings for HashDirectory removeEntry using name to search method:\nAvarage: %d\nBest: %d\nWorst: %d", avarage, best, worst);
		} catch (FileNotFoundException e) {
			System.out.println("Error! File not found. Please ensure that an appropriate text file is available");
			e.printStackTrace();
		}


		
	}

	@Override
	public void testChangeNumber() {
		HashDirectory directory; //ArrayDirectory object that will be used for the tests
		Long timeElapsed; //Stores an individual timing from Stopwatch.getElapsedTime()
		List<Long> timings = new ArrayList<Long>(); //Used to store multiple timings so they can be compared
		Long avarage; // Stores the returned average time from getAvarageTime()
		Long best; // Stores the returned best time from getBestTime()
		Long worst; // Stores the worst time which is returned by getWorstTime()

		directory = new HashDirectory(); //Instantiates the ArrayDirectory
		Scanner inFile;
		try {
			inFile = new Scanner(new FileReader("names.txt"));
		
		
		// Populates the directory with Entries from a text file
		while (inFile.hasNextLine()) {
			String entry = inFile.nextLine(); // Stores each line of data in a string
			entry.trim(); //Trims the string of whitespace
			String[] entryArgs = entry.split("\\s+"); //Stores the string in an array using \\s+ as a delimiter
			directory.addEntry(entryArgs[0], entryArgs[1], entryArgs[2]); // Adds a new Entry to the directory
		}
		inFile.close();
		// Bounds used to define a 5-digit number which will be used as an argument for directory.changeNumber()
		int min = 10000;
		int max = 99999;
		inFile = new Scanner(new FileReader("Names.txt"));
		while (inFile.hasNextLine()) {
			String entry = inFile.nextLine(); // Stores each line of data in a string
			entry.trim(); //Trims the string of whitespace
			String[] entryArgs = entry.split("\\s+"); //Stores the string in an array using \\s+ as a delimiter
			
			// Generates a random 5-digit integer number
			int randomTelExt = ThreadLocalRandom.current().nextInt(min, max + 1);
			// Converts the randomly created integer number to a String so it can be passed as the newTelExt argument in directory.changeNumber();
			String newTelExt = Integer.toString(randomTelExt);
			// Test the time of each operation
			stopWatch.start();
			directory.changeNumber(entryArgs[0], entryArgs[1], entryArgs[2], newTelExt); // Adds a new Entry to the directory
			stopWatch.stop();
			// Stores the individual timing in a List
			timeElapsed = stopWatch.getElapsedTime();
			timings.add(timeElapsed);
			// resets the stopWatch for the next operation
			stopWatch.reset();
		}
		inFile.close();
		// Calls methods to return the average, best and worst times recorded by textAddEntry and store them in variables
		avarage = getAvarageTime(timings);
		best = getBestTime(timings);
		worst = getWorstTime(timings);
		
		// Print the average, best and worst timings
		System.out.format("Timings for HashDirectory changeNumber method:\nAvarage: %d\nBest: %d\nWorst: %d", avarage, best, worst);
		} catch (FileNotFoundException e) {
			System.out.println("Error! File not found. Please ensure that an appropriate text file is available");
			e.printStackTrace();
		}
		
	}

	@Override
	public void testLookupTelExtNum() {
		HashDirectory directory; //ArrayDirectory object that will be used for the tests
		Long timeElapsed; //Stores an individual timing from Stopwatch.getElapsedTime()
		List<Long> timings = new ArrayList<Long>(); //Used to store multiple timings so they can be compared
		Long avarage; // Stores the returned average time from getAvarageTime()
		Long best; // Stores the returned best time from getBestTime()
		Long worst; // Stores the worst time which is returned by getWorstTime()

		directory = new HashDirectory(); //Instantiates the ArrayDirectory
		Scanner inFile;
		try {
			inFile = new Scanner(new FileReader("names.txt"));
		
		// Populates the directory with Entries from a text file
		while (inFile.hasNextLine()) {
			String entry = inFile.nextLine(); // Stores each line of data in a string
			entry.trim(); //Trims the string of whitespace
			String[] entryArgs = entry.split("\\s+"); //Stores the string in an array using \\s+ as a delimiter
			directory.addEntry(entryArgs[0], entryArgs[1], entryArgs[2]); // Adds a new Entry to the directory
		}
		inFile.close();
		
		inFile = new Scanner(new FileReader("names.txt"));
		while (inFile.hasNextLine()) {
			String entry = inFile.nextLine(); // Stores each line of data in a string
			entry.trim(); //Trims the string of whitespace
			String[] entryArgs = entry.split("\\s+"); //Stores the string in an array using \\s+ as a delimiter
			
			stopWatch.start();
			directory.lookupExtNum(entryArgs[0], entryArgs[1]); // Adds a new Entry to the directory
			stopWatch.stop();
			timeElapsed = stopWatch.getElapsedTime();
			timings.add(timeElapsed);
			stopWatch.reset();
		}
		inFile.close();

		// Calls methods to return the average, best and worst times recorded by textAddEntry and store them in variables
		avarage = getAvarageTime(timings);
		best = getBestTime(timings);
		worst = getWorstTime(timings);
		
		// Print the average, best and worst timings
		System.out.format("Timings for HashDirectory lookupExtNum method:\nAvarage: %d\nBest: %d\nWorst: %d", avarage, best, worst);
		} catch (FileNotFoundException e) {
			System.out.println("Error! File not found. Please ensure that an appropriate text file is available");
			e.printStackTrace();
		}
		
	}

	@Override
	public void testPrintDirectory() {
		HashDirectory directory = new HashDirectory();
		Scanner inFile;
		try {
			inFile = new Scanner(new FileReader("names.txt"));
		
		
		// Populates the directory with Entries from a text file
		while (inFile.hasNextLine()) {
			String entry = inFile.nextLine(); // Stores each line of data in a string
			entry.trim(); //Trims the string of whitespace
			String[] entryArgs = entry.split("\\s+"); //Stores the string in an array using \\s+ as a delimiter
			directory.addEntry(entryArgs[0], entryArgs[1], entryArgs[2]); // Adds a new Entry to the directory
		}
		inFile.close();
		directory.printDirectory();
		} catch (FileNotFoundException e) {
			System.out.println("Error! File not found. Please ensure that an appropriate text file is available");
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		new HashDirectoryStopWatchTest();
	}
	
}
