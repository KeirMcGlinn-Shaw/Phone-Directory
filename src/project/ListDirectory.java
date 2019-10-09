package project;

// Imported utilities
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

// ListDirectory implements Directory interface, so must have all of its methods
public class ListDirectory implements Directory {

	// Private List field that will be used as the main storage of Entry objects in
	// this implementation
	private List<Entry> entryList;
//	private List<Entry> entryList = new LinkedList<Entry>();

	// Constructor of this implementation. Assignment of the entryList field
	public ListDirectory() {
		entryList = new LinkedList<Entry>();
	}

	// Get method which returns the class field entryList as an unmodifiableList
	public List<Entry> getEntryList() {
		return Collections.unmodifiableList(entryList);
	}

	
	private int binarySearch(List<Entry> entries, Entry key) { //Takes an List to search and an Entry object to be used as a key, as parameters

		int mid; // Variable used to store the middle index in a search
		int left = 0; // Variable to store the leftmost index of a search
		int right = entries.size(); // Variable to store the rightmost index of a search

		while (left < right) { // Runs iteratively until the left and right index positions cross
			mid = (left + right) / 2; // Sets the value of mid to halfway between left and right
			// Compares the key to the Entry stored in the middle index of the List 
			// (Note. This comparison only uses Surname and initials fields as telExt fields are not sorted an therefore cannot be used in a binary search function)
			int result = key.compareTo(entries.get(mid)); 
			if (result == 0) { //If the two Entries have the same surname+initials (Are equal) then return this index
				return mid; 
			} else if (result < 0) { //If the search value is less than the Entry stored in mid then it cannot be in the half of the List which is greater than mid
				right = mid; // Eliminates the right side of the List and resets 'right' so that the half that is less than mid, is searched next
			} else { //If the search value is greater than the Entry stored in mid then it cannot be in the half of the List which is less than mid
				left = mid + 1; // Eliminates the left side of the List and resets 'left; so that the half which is greater than mid, is searched next
			}
		}
		return left; //When the two indexes cross, return left 
	}
	
	// Implementation of the interface method addEntry
	@Override
	public boolean addEntry(String surname, String initials, String telExt) { // Takes three strings as parameters which
																				// will be used as arguments to
																				// construct a new Entry object
		// Generates a new Entry object out of the method parameters
		Entry newEntry = new Entry(surname, initials, telExt);
		// If the list is empty: insert the Entry object into the list in the first
		// position
		if (entryList.isEmpty()) {
			entryList.add(newEntry);
			return true; // Return true to signal that the operation was successful
		}

		int index = binarySearch(entryList, newEntry);
		entryList.add(index, newEntry);
		return true;
	}

	// Implementation of the interface method removeEntry
	@Override
	public boolean removeEntry(String surname, String initials, String telExt) { // Three strings as parameters which will be used as arguments to create a new entry object
		Entry entry = new Entry(surname, initials, telExt);
		ListIterator<Entry> iterator = entryList.listIterator(); // Creates new iterator object for iterating over
																	// entryList

		while (iterator.hasNext()) { // Linearly iterates over ever Entry in entryList
			Entry listEntry = iterator.next(); // Increments to the next Entry to be compared to the search value
			if (entry.compareTo(listEntry) == 0) { // Checks to see if the Entries have the same names and initials
													// (When the user elects to remove and entry using the name to
													// search)
				entryList.remove(listEntry); // If Entry equals search value, remove it from entryList
				return true; // Return true to signal that the operation was successful
			} else if (entry.compareToByTelExt(listEntry) == 0) { // Check to see if the Entries have the same
																	// telExt (When the user elects to remove and entry
																	// using their telephone extension for the search)
				entryList.remove(listEntry);
				return true; // Return true to signal that the operation was successful
			}
		}
		return false; // If this point is reached then no matching Entry has been found so removal has
						// failed. Return false to signal this
	}
	
	@Override
	public String lookupExtNum(String surname, String initials) { // Takes surname and initials as parameters because
																	// this method searches for an entry's telExt using
																	// only a name
		final String emptyString = ""; // Empty string stored in constant. Will be used to generate an entry object
										// with an empty string for its telExt argument
		Entry entry = new Entry(surname, initials, emptyString); // Generates new Entry object to be used as a search
																	// value. Only the name fields are required
		int index = binarySearch(entryList, entry);
		
		if(entry.compareTo(entryList.get(index)) == 0) {
			return entryList.get(index).getTelExt();
		} else return emptyString;
	}

	// Implementation of the interface method changeNumber
	@Override
	public boolean changeNumber(String surname, String initials, String telExt, String newTelExt) {
		// Takes fours String parameters: Surname, Initials, currentTelExt and newTelExt
		Entry entry = new Entry(surname, initials, telExt); // Generates new Entry object to be used as a search value
		int index = binarySearch(entryList, entry);
		if (entry.equals(entryList.get(index))) {													
			entryList.get(index).setTelExt(newTelExt);
			return true; // Return true to signal that the operation was successful
		} else
			return false; // Return false if no matching entry is found

	}

	// Implementation of the interface method printDirectory
	@Override
	public void printDirectory() {
		ListIterator<Entry> iterator = entryList.listIterator(); //Creates new iterator object to iterate over entryList
		while (iterator.hasNext()) { //While there is another entry in the list:
			System.out.println(iterator.next()); //Print the Entry at list position iterator.next() (Implicitly calls Entry.toString())
		}

	}

}