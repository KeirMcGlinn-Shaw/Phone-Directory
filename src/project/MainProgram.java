package project;
import java.io.*;
import java.util.*;

// This MainProgram class tests all of the Directory interface methods in each of the three implementations
// Not there is no explicit test for directory.addEntry() as that is implicitly tested in the fileInput method
public class MainProgram  {
	
	public static void main(String[] args) throws FileNotFoundException {
		testListImplementation();
		System.out.println("\n\n");
		testArrayImplementation();
		System.out.println("\n\n");
		testHashImplementation();
	}
	
	private static void fileInput(Directory directory) { //Takes a Directory as an argument
		Scanner inFile;
		try {
			inFile = new Scanner(new FileReader("Entries.txt"));
		
		while(inFile.hasNextLine()) {
			String entry = inFile.nextLine();
			entry.trim();
			String[] entryArgs = entry.split("\\s+");
			directory.addEntry(entryArgs[0], entryArgs[1], entryArgs[2]);
		}
		inFile.close();
		
		} catch (FileNotFoundException e) {
			System.out.println("Error! File not found. Please ensure a correctly formatted text file of Entries is being used");
			e.printStackTrace();
		}
	}
	
	// Runs all the methods to test the List implementation
	public static void testListImplementation() {
		ListDirectory directory = new ListDirectory();
		fileInput(directory);
		directory.printDirectory();
		testListRemoval(directory);
		testListTelExtLookup(directory);
		testListChangeTelExt(directory);
	}
	// Runs all the methods to test the Array implementation
	public static void testArrayImplementation() {
		ArrayDirectory directory = new ArrayDirectory();
		fileInput(directory);
		directory.printDirectory();
		testArrayRemoval(directory);
		testArrayGetTelExt(directory);
		testArrayChangeTelExt(directory);
	}
	// Runs all the methods to test the Hash implementation
	public static void testHashImplementation() {
		HashDirectory directory = new HashDirectory();
		fileInput(directory);
		directory.printDirectory();
		testHashRemoval(directory);
		testHashGetTelExt(directory);
		testHashChangeTelExt(directory);
	}
	
	public static void testHashChangeTelExt(HashDirectory directory) {
		System.out.println("************************************************************************************");
		final String newNum = "00099";
		Entry read = new Entry("Read", "E.K", "37609");
		
		System.out.println("Before changeNumber is run, getTelExt returns: " + read.getTelExt());
		
		directory.changeNumber(read.getSurname(), read.getInitials(), read.getTelExt(), newNum);
		String returnedTelExt = directory.lookupExtNum(read.getSurname(), read.getInitials());
		System.out.println("After changeTelExt, lookupTelext should now returned the changed telExt '00099'. Actual return value: " + returnedTelExt + "\n");
		System.out.println("************************************************************************************");
	}

	public static void testHashRemoval(HashDirectory directory) {
		System.out.println("************************************************************************************");
		// Creates new Entry object with the same fields as one which is included in the text file and has been added to the directory
		Entry upton = new Entry("Upton", "k", "46287");
		// String constant which will be used to fill arguments to directory.removeEntry() in place of telExt or surname and initials
		final String emptyString = "";
		
		System.out.println("HashDirectoy removeEntry method test. Will test removing an entry using their name fields and then a second entry using their telephone Extension field");
		
		// Call removeEntry() using surname and initials to test the removal of an entry using their name fields
		if (directory.removeEntry(upton.getSurname(), upton.getInitials(),emptyString)) {
			System.out.println("Removal using name fields was successful");
		} else {
			System.out.println("Removal using name fields failed");
		}
		// Call removeEntry() using telExt to test the removal of an entry using their telExt field
		Entry paxman = new Entry(emptyString, emptyString, "86937");
		if (directory.removeEntry(emptyString, emptyString, paxman.getTelExt())) {
			System.out.println("Removal using telExt field was successful");
		} else {
			System.out.println("Removal using telExt field failed");
		}
		System.out.println("************************************************************************************");
	}
	
	public static void testHashGetTelExt(HashDirectory directory) {
		System.out.println("************************************************************************************");
		
		Entry thomas = new Entry("White", "T.J", "01222");
		
		String testTelExt = directory.lookupExtNum(thomas.getSurname(), thomas.getInitials());
		
		System.out.println("HashDirectoy lookupExtNum method test:\n"
				+ "The expected method return value is 01222. The actual value returned is: " + testTelExt);
		System.out.println("************************************************************************************");
	}

	public static void testListRemoval(ListDirectory directory) {
		System.out.println("************************************************************************************");
		// Creates new Entry object with the same fields as one which is included in the text file and has been added to the directory
		Entry upton = new Entry("Upton", "k", "46287");
		// String constant which will be used to fill arguments to directory.removeEntry() in place of telExt or surname and initials
		final String emptyString = "";
		
		System.out.println("ListDirectory removeEntry method test. Will test removing an entry using their name fields and then a second entry using their telephone Extension field");
		
		// Call removeEntry() using surname and initials to test the removal of an entry using their name fields
		if (directory.removeEntry(upton.getSurname(), upton.getInitials(),emptyString)) {
			System.out.println("Removal using name fields was successful");
		} else {
			System.out.println("Removal using name fields failed");
		}
		// Call removeEntry() using telExt to test the removal of an entry using their telExt field
		Entry paxman = new Entry(emptyString, emptyString, "86937");
		if (directory.removeEntry(emptyString, emptyString, paxman.getTelExt())) {
			System.out.println("Removal using telExt field was successful");
		} else {
			System.out.println("Removal using telExt field failed");
		}
		System.out.println("************************************************************************************");
	}
	
	public static void testListTelExtLookup(ListDirectory directory) {
		System.out.println("************************************************************************************");
		
		Entry thomas = new Entry("White", "T.J", "01222");
		
		String testTelExt = directory.lookupExtNum(thomas.getSurname(), thomas.getInitials());
		
		System.out.println("ListDirectory lookupExtNum method test:\n"
				+ "The expected method return value is 01222. The actual value returned is: " + testTelExt);
		System.out.println("************************************************************************************");
		
	}
	
	public static void testListChangeTelExt(ListDirectory directory) {

		System.out.println("************************************************************************************");
		final String newNum = "00099";
		Entry read = new Entry("Read", "E.K", "37609");
		
		System.out.println("Before changeNumber is run, getTelExt returns: " + read.getTelExt());
		
		directory.changeNumber(read.getSurname(), read.getInitials(), read.getTelExt(), newNum);
		String returnedTelExt = directory.lookupExtNum(read.getSurname(), read.getInitials());
		System.out.println("After changeTelExt, lookupTelext should now returned the changed telExt '00099'. Actual return value: " + returnedTelExt + "\n");
		System.out.println("************************************************************************************");
	}
	
	public static void testArrayRemoval(ArrayDirectory directory) {
		System.out.println("************************************************************************************");
		// Creates new Entry object with the same fields as one which is included in the text file and has been added to the directory
		Entry upton = new Entry("Upton", "k", "46287");
		// String constant which will be used to fill arguments to directory.removeEntry() in place of telExt or surname and initials
		final String emptyString = "";
		
		System.out.println("ArrayDirectory removeEntry method test. Will test removing an entry using their name fields and then a second entry using their telephone Extension field");
		
		// Call removeEntry() using surname and initials to test the removal of an entry using their name fields
		if (directory.removeEntry(upton.getSurname(), upton.getInitials(),emptyString)) {
			System.out.println("Removal using name fields was successful");
		} else {
			System.out.println("Removal using name fields failed");
		}
		// Call removeEntry() using telExt to test the removal of an entry using their telExt field
		Entry paxman = new Entry(emptyString, emptyString, "86937");
		if (directory.removeEntry(emptyString, emptyString, paxman.getTelExt())) {
			System.out.println("Removal using telExt field was successful");
		} else {
			System.out.println("Removal using telExt field failed");
		}
		System.out.println("************************************************************************************");
	}
	
	public static void testArrayGetTelExt(ArrayDirectory directory) {
		System.out.println("************************************************************************************");
		
		Entry thomas = new Entry("White", "T.J", "01222");
		
		String testTelExt = directory.lookupExtNum(thomas.getSurname(), thomas.getInitials());
		
		System.out.println("ArrayDirectory lookupExtNum method test:\n"
				+ "The expected method return value is 01222. The actual value returned is: " + testTelExt);
		System.out.println("************************************************************************************");
	}
	
	public static void testArrayChangeTelExt(ArrayDirectory directory) {
		System.out.println("************************************************************************************");
		final String newNum = "00099";
		Entry read = new Entry("Read", "E.K", "37609");
		
		System.out.println("Before changeNumber is run, getTelExt returns: " + read.getTelExt());
		
		directory.changeNumber(read.getSurname(), read.getInitials(), read.getTelExt(), newNum);
		String returnedTelExt = directory.lookupExtNum(read.getSurname(), read.getInitials());
		System.out.println("After changeTelExt, lookupTelext should now returned the changed telExt '00099'. Actual return value: " + returnedTelExt + "\n");
		System.out.println("************************************************************************************");
		
	}
	
	
	
}