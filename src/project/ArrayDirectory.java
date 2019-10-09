package project;

//Imported utility
import java.util.Arrays;

//ArrayDirectory implements the interface Directory
public final class ArrayDirectory implements Directory {

	//Class fields
	private Entry[] entryArray; //Declaration of the main array which will be used to store Entry objects in this implementation
	private final String emptyString = ""; //Empty string which will be used by methods to return an empty string instead of null, to avoid null related exceptions
	
	//Class constructor
	public ArrayDirectory() {
		entryArray = new Entry[25]; //Defines the main array
	}

	// A get method which returns the main class field which stores Entry objects in this implementation
	public Entry[] getEntryArray() {
		return entryArray;
	}

	// Implementation of the interface method addEntry
	@Override
	public boolean addEntry(String surname, String initials, String telExt) { // Takes three strings as parameters
		Entry newEntry = new Entry(surname, initials, telExt); //Method parameters are used as fields to create a new Entry object
		// Calls the insert method to insert the new Entry object into the passed array, and assigns entryArray to the methods return value
		entryArray = insert(entryArray, newEntry); 
		return true; // Return true to signal that the operation was successful
	}

	private Entry[] insert(Entry[] array, Entry newEntry) {
		final int last = array.length - 1; // Sets last to the value of the last object stored in the array passed to the method

		//Sets the value of insertIndex to the index where the newEntry should be inserted. This value is returned by the binarySearch method
		final int insertIndex = binarySearch(array, newEntry); 

		if (array[last] != null) { // Checks to see if the array is full. If it is, then it copies the array into
									// an array that is *1.5 the size of the original
			array = Arrays.copyOf(array, array.length + (array.length / 2));
		}
		if (array[insertIndex] == null) {
			array[insertIndex] = newEntry; //Inserts the newEntry into the now-free index in the array
			return array; //Returns the edited array
		}
		//Shifts all elements in the array that are to the right of the insertIndex by 1 to the right
		System.arraycopy(array, insertIndex, array, insertIndex + 1, array.length - insertIndex - 1);  
		array[insertIndex] = newEntry; //Inserts the newEntry into the now-free index in the array
		return array; //Returns the edited array
	}

	// Implementation of the interface method removeEntry
	@Override
	public boolean removeEntry(String surname, String initials, String telExt) { // Three strings as parameters which will be used as arguments to create a new entry object
		Entry entry = new Entry(surname, initials, telExt);
		if (entry.getSurname().equals(emptyString) || entry.getSurname() == null) { //If true, then the following code will search for the entry by its telExt	
			if (entry.getTelExt().equals(emptyString)) { //Checks to ensure that at least a telExt has been provided
				return false; // The user has provided no information that could be turned into object fields
								// and therefore nothing that can be used to search
			} else {
				for (int count = 0; count < entryArray.length; count++) { //Iteratively compares the search value entry to all Entries in entryArray by their telExts
					if (entry.compareToByTelExt(entryArray[count]) == 0) { //If both objects have the same telephone extension
						//Shifts all the entries to the right of the searched entry one place to the left so that the searched entry is overwritten
						System.arraycopy(entryArray, count + 1, entryArray, count, entryArray.length - count - 1);
						return true; // Return true to signal that the operation was successful
					}
				}
			}	
		} 
		// If the above code is not run because the user has selected to remove the entry by its name, then the following code will search for the entry using
		// the binarySearch method. This is faster than the above code but cannot be implemented when searching by telephone extension as they are not sorted but names are
		final int index = binarySearch(entryArray, entry);

		// Checks to see if the binarySearch function is returning the index of the
		// Entry we're looking for or if it is returning the position our search Entry
		// should be inserted in(Because it is not present in the array). This prevents
		// this function from inadvertently removing an Entry in the position the new
		// Entry should be inserted.
		if (!(entry.compareTo(entryArray[index]) == 0 || entry.compareToByTelExt(entryArray[index]) == 0)) { 
			//If the entry in the index returned does not have the same name and initials, or the same telExt. 
			//Return false because the entry searched for is not present in the array
			return false;
		}

		if (index >= 0) { //Ensures the index is valid and not negative
			// Shifts all the entries to the right of the searched entry one place to the left so that the searched entry is overwritten
			System.arraycopy(entryArray, index + 1, entryArray, index, entryArray.length - index - 1); 
			return true; // Return true to signal that the operation was successful
		}

		return false; //Returns false as default
	}

	// Implementation of the interface method lookupTelExt
	@Override
	public String lookupExtNum(String surname, String initials) { //Takes two strings as parameters which will be used to create an Entry object

		Entry entry = new Entry(surname, initials, emptyString); //Entry object to be used as a search value
		
		// Returns the index the search entry should be inserted
		int index = binarySearch(entryArray, entry); 
		
		// Checks to see if the binarySearch function is returning the index of the Entry we're looking for or if it 
		// is returning the position our search Entry should be inserted in(Because it is not present in the array)
		if (!(entry.compareTo(entryArray[index]) == 0)) {
			//If the entry in the index returned does not have the same name and initials, or the same telExt. 
			//Return false because the entry searched for is not present in the array
			return emptyString;
		}
		return entryArray[index].getTelExt(); //Returns the telExt of the matching entry in entryArray
	}

	// Implementation of the interface method changeNumber
	@Override
	public boolean changeNumber(String surname, String initials, String currentTelExt, String newTelExt) { //Takes four String parameters which will be used to construct a new entry object as a search value and the newTelExt
		
		// Creates a new Entry object to be used a search value to find the Entry we want to change the telExt of.
		Entry searchEntry = new Entry(surname, initials, currentTelExt); 

		// Call binary search to return the index that the searchEntry is stored
		int index = binarySearch(entryArray, searchEntry);
		
		// Checks to see if the binarySearch function is returning the index of the Entry we're looking for or if it 
		// is returning the position our search Entry should be inserted in(Because it is not present in the array)
		if (searchEntry.equals(entryArray[index])) {
			entryArray[index].setTelExt(newTelExt); // Changes the entry's telExt field
			return true;  // Return true to signal that the operation was successful
		} else return false; //If binarySearch has returned the position the entry should be inserted (Because it is not present in the array): return false
	}

	// Implementation of the interface method printDirectory
	@Override
	public void printDirectory() {
		for (Entry entry : getEntryArray()) { //For each loop that prints every Entry in the array. Does not print null values
			if (entry != null) {
				System.out.println(entry);
			}
		}
	}

	// Binary search method which returns the index a specified Entry object is present in or should be inserted into
	private int binarySearch(Entry[] array, Entry key) { //Takes an array to search and an Entry object to be used as a key, as parameters

		int mid; // Variable used to store the middle index in a search
		int left = 0; // Variable to store the leftmost index of a search
		int right = array.length - 1; // Variable to store the rightmost index of a search

		while (left < right) { // Runs iteratively until the left and right index positions cross
			mid = (left + right) / 2; // Sets the value of mid to halfway between left and right
			// Compares the key to the Entry stored in the middle index of the array 
			// (Note. This comparison only uses Surname and initials fields as telExt fields are not sorted an therefore cannot be used in a binary search function)
			int result = key.compareTo(array[mid]); 
			if (result == 0) { //If the two Entries have the same surname+initials (Are equal) then return this index
				return mid; 
			} else if (result < 0) { //If the search value is less than the Entry stored in mid then it cannot be in the half of the array which is greater than mid
				right = mid; // Eliminates the right side of the array and resets 'right' so that the half that is less than mid, is searched next
			} else { //If the search value is greater than the Entry stored in mid then it cannot be in the half of the array which is less than mid
				left = mid + 1; // Eliminates the left side of the array and resets 'left; so that the half which is greater than mid, is searched next
			}
		}
		return left; //When the two indexes cross, return left 
	}

}
