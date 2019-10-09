package project;

import java.util.*;

// Imported utilities

// HashMap implementation of the Directory interface
public class HashDirectory implements Directory {

	// CDecleration of Class field of type Map which will form the main storage in
	// this implementation
	private Map<String, List<Entry>> entryMap;

	public HashDirectory() {
		entryMap = new HashMap<String, List<Entry>>(); // Initialises class field as a new HashMap for use in the
														// created object
	}

	public Map<String, List<Entry>> getEntryMap() {
		return Collections.unmodifiableMap(entryMap);
	}


	// Implementation of the interface method addEntry
	@Override
	public boolean addEntry(String surname, String initials, String telExt) { // Takes three strings as parameters which
																				// will be used as arguments to
		Entry entry = new Entry(surname, initials, telExt); // Creates new Entry object for use as a search value
		final String searchKey = entry.getSurname().substring(0, 1); // Creates a searchKey from the first letter of an
																		// Entry's surname. This will be used to search
																		// through entryMap's keys
		if (!(entryMap.containsKey(searchKey))) {// If the Map does not have a key for that letter,
			entryMap.put(searchKey, new LinkedList<Entry>()); // create a new key and value pair with the searchKey and
																// a new Linked list to store entries that start with
																// the same letter as searchKey
			return addEntryToList(entry, entryMap.get(searchKey)); // Inserts the newEntry into the newly created List
		} else
			return addEntryToList(entry, entryMap.get(searchKey)); // If entryMap contains a key which equals searchKey:
																	// insert the new Entry into the corresponding value
																	// (List)
	}

	// Method which works in a similar way to the addEntry method in ListDirectory.
	// In this implementation it is used to insert Entries into the List which
	// makes-up the value in a key-value pair in EntryMap
	private boolean addEntryToList(Entry newEntry, List<Entry> entryList) { // Takes an Entry object (created in
		// Generates a new Entry object out of the method parameters
				// If the list is empty: insert the Entry object into the list in the first
				// position
				if (entryList.isEmpty()) {
					entryList.add(newEntry);
					return true; // Return true to signal that the operation was successful
				}

				int index = binarySearch(entryList, newEntry); //Binary search which returns the index where the new Entry should be inserted
				entryList.add(index, newEntry); //Adds the entry at the specified index
				return true; // Return true to signal that the operation was successful
	}
	
	private int binarySearch(List<Entry> entries, Entry key) { //Takes a List to search and an Entry object to be used as a key, as parameters

		int mid; // Variable used to store the middle index in a search
		int left = 0; // Variable to store the leftmost index of a search
		int right = entries.size() - 1; // Variable to store the rightmost index of a search

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

	// Implementation of the interface method removeEntry
	@Override
	public boolean removeEntry(String surname,String initials, String telExt) { // Three strings as parameters which will be used as arguments to create a new entry object
		Entry entry = new Entry(surname, initials, telExt);
		final String emptyString = ""; // Empty string used to check for empty fields in the Entry passed to the method as an argument
		
		// Checks if surname is null or empty. If neither, then the Entry to remove can be searched for using inbuilt Map methods
		if (entry.getSurname() != null && !entry.getSurname().equals(emptyString)) { 
			final String searchKey = entry.getSurname().substring(0, 1); //Creates a searchKey from the first letter of the Entry's surname
			//Checks to see the map contains a key which is equal (Ensures that no exceptions are thrown if the following operation is attempted on a key which does not exist
			if ((entryMap.containsKey(searchKey))) { 
				return removeEntryFromList(entry, entryMap.get(searchKey)); //Calls a method to remove the entry from the list stored in the map value related to searchKey
			}
		
		// If the surname = null or emptyString, the Map method 'containsKey()' cannot be used(Like in the above code).
		// So check to ensure that the telExt field is not null or empty either. If it is not, then linearly search for an equal telExt in entryMap
		// Ensures entry.TelExt is not null or empty to avoid exceptions when the program tries to access that field and use it for comparisons
		} else if (entry.getTelExt() != null && !entry.getTelExt().equals(emptyString)) {
			for (List<Entry> list : entryMap.values()) { //For each loop linearly compares all Entries in entryMap with the search value
				if (removeEntryFromList(entry, list)) { //All comparison and subsequent removal of an Entry from a List (Value) in entryMap, is handled by this function
					return true; // If the above method call returns true to signal a successful removal then return true to signal the same to any calling code of this method
				}
			}
		}
		return false; //If neither of the above methods for removal are run or neither return true then return false to signal an unsuccessful operation
	}

	// Method to remove an Entry from a List in hashMap
	//Takes an Entry object (which will be used as search value) and a List (which is the List stored as the value in a key-value pair)
	private boolean removeEntryFromList(Entry entry, List<Entry> entryList) { 
		ListIterator<Entry> iterator = entryList.listIterator();// Creates new iterator object to iterate over the list

		while (iterator.hasNext()) { // Iterates over each Entry in the list while there are more Entries in it
			Entry listEntry = iterator.next(); //Stores a reference to the Entry stored in the List at the iterator's position
			// Compares the search value Entry with the Entry at the iterator's position by name and by telExt
			if (entry.compareTo(listEntry) == 0 || entry.compareToByTelExt(listEntry) == 0) {
				entryList.remove(listEntry); // If the Entry at the iterator's position has the same name or telEXt, then remove it
				return true; // Return true to signal that the operation was successful
			}
		}
		// If the search value doesn't have the same name(surname + initials) or same telExt as any Entry in the list, return false
		return false; 
	}

	// Implementation of the interface method changeNumber
	@Override
	public boolean changeNumber(String surname, String initials, String telExt, String newTelExt) {
		// Takes fours String parameters: Surname, Initials, currentTelExt and newTelExt
		Entry entry = new Entry(surname, initials, telExt); // Generates new Entry object to be used as a search value
		final String searchKey = entry.getSurname().substring(0, 1); //Creates a searchKey from the first letter of the Entry's surname
		if ((entryMap.containsKey(searchKey))) {
			return changeNumberInList(entry, newTelExt, entryMap.get(searchKey));
		}
		return false; // Return false to signal that the operation was unsuccessful
	}

	private boolean changeNumberInList(Entry entry, String newTelExt, List<Entry> entryList) {	
			int index = binarySearch(entryList, entry);
			if (entry.equals(entryList.get(index))) {													
				entryList.get(index).setTelExt(newTelExt);
			return true; // Return true to signal that the operation was successful
		} else
			return false; // Return false to signal that the operation was unsuccessful
	}

	// Implementation of the interface method lookupTelExt
	@Override
	public String lookupExtNum(String surname, String initials) {// Takes surname and initials as parameters because this method searches for an entry's telExt using only a name
		final String emptyString = ""; //Empty string constant to be returned if no matching Key in entryMap is found
		final String searchKey = surname.substring(0, 1); //Create searchKey from the first letter of the surname argument passed to the method
		if ((entryMap.containsKey(searchKey))) { //Checks to see if the map contains an equal key
			//Sets the value of a string constant to the String value returned by lookupInList which will search the list in the value related to searchKey for a matching entry
//			final String returnedTelExt = lookupInList(surname, initials, entryMap.get(searchKey)); //TODO
//			return returnedTelExt; //Returns the value returned by 
			return lookupInList(surname, initials, entryMap.get(searchKey));
		}
		return emptyString; //Return empty string instead of null to avoid throwing exceptions
	}

	// Method to search Lists in map key-value pairs using surname and initial
	// Strings for an entry with matching fields
	private String lookupInList(String surname, String initials, List<Entry> entryList) { // Takes two strings and the
																							// List that will be
																							// searched, as parameters
		final String emptyString = ""; // Empty string stored in constant. Will be used to generate an entry object
		// with an empty string for its telExt argument
		Entry entry = new Entry(surname, initials, emptyString); // Generates new Entry object to be used as a search
		// value. Only the name fields are required

		int index = binarySearch(entryList, entry);

		if (entry.compareTo(entryList.get(index)) == 0) {
			return entryList.get(index).getTelExt();
		} else
			return emptyString;
	}

	// Implementation of the interface method changeNumber
	@Override
	public void printDirectory() {
		for (List<Entry> list : entryMap.values()) { //Iterates over each List stored as a value in entryMap
			ListIterator<Entry> iterator = list.listIterator(); 
			while (iterator.hasNext()) { //Iterates over each Entry in the list
				System.out.println(iterator.next()); //Prints the Entry (Implicitly calls Entry.toString()
			}
		}
	}
}
