package project;

import java.util.Comparator;

final class Entry implements Comparable<Entry> { // Made class final so that it cannot be inherited from (Defensive
													// programming)
													// Implements Comparable, making it a Comparable type
	// Private Class fields
	private String surname;
	private String initials;
	private String telExt;

	// Class constructor
	Entry(String surname, String initials, String telExt) {
		// Class fields assigned values without use of setMethod(). As this class has no
		// set methods (Further defensive programming)
		this.surname = surname;
		this.initials = initials;
		this.telExt = telExt;
	}

	// Method returns surname class field, so field is not directly accessed by
	// external code
	String getSurname() {
		return surname;
	}
	

	// Method returns initials class field, so field is not directly accessed by
	// external code
	String getInitials() {
		return initials;
	}

	// Method returns telExt class field, so field is not directly accessed by
	// external code
	String getTelExt() {
		return telExt;
	}

	// Method used to set the telExt class field
	void setTelExt(String telExt) {
		this.telExt = telExt;
	}
	
	// Overridden toString method defines how Entries are printed
	@Override
	public String toString() {
		return String.format("\t%15s\t%15s\t%15s", getInitials(), getSurname(), getTelExt());
	}

	// Overridden equals method defines equality of two Entry objects
	@Override
	public boolean equals(Object obj) {
		if (this == obj) { // Check instance identity (If they are the same object)
			return true;
		}
		if (!(obj instanceof Entry)) { // Check if the argument passed to the method is an instance of Entry. If not,
										// then the two objects cannot be equal
			return false;
		}
		Entry entry = (Entry) obj; // Cast obj to Entry. So that Entry class methods can be called on it. I.e. entry.getSurname()

		// If the two entry objects have the same surname, initials and telExt, then they are equal
		// Use use of .equalsIgnoreCase() to prevent errors caused by searches for entries that are incorrectly cased
		return getSurname().equalsIgnoreCase(entry.getSurname()) && getInitials().equalsIgnoreCase(entry.getInitials())
				&& getTelExt().equalsIgnoreCase(entry.getTelExt()); 
	}

	// Overridden hashCode method defines unique hashCodes for Entry objects, that matches the overridden equals method
	@Override
	public int hashCode() {
		int prime = 17;
		int multiplier = 37;

		int hc = (multiplier * (prime * getSurname().toLowerCase().hashCode()))
				+ (multiplier * (prime * getInitials().toLowerCase().hashCode()))
				+ (multiplier * (prime * telExt.hashCode()));

		return hc;
	}
	
	// Null safe Comparator for Strings which will be used by the entryComparator to compare fields
	private static Comparator<String> nullSafeStringComparator = Comparator.nullsFirst(String::compareToIgnoreCase);
	
	// Comparator, which uses the nullSafeStringComparator, for comparing  Entry objets
	private static Comparator<Entry> entryComparator = Comparator.comparing(Entry::getSurname, nullSafeStringComparator)
			.thenComparing(Entry::getInitials, nullSafeStringComparator);
	
	// Null safe Entry comparator defines how Entry objects are ordered if they are null(For use in Array implementation)
	// Uses entryComparator
	private static Comparator<Entry> nullSafeEntryComparator = Comparator.nullsLast(entryComparator);

	// Defines how to compare two Entry objects
	@Override
	public int compareTo(Entry that) {
		return nullSafeEntryComparator.compare(this, that); // Implements a comparator for the actual comparason, which returns an integer value
	}

	// Defines how to compare two entries purely by their telephone extensions
	private static Comparator<Entry> entryByTelExtComparator = Comparator.comparing(Entry::getTelExt,
			nullSafeStringComparator);
	// Null safe comparator which uses entryByTelExtComparator to compare entries by their telExt. Specifies order of null objects so that no errors are thrown
	private static Comparator<Entry> nullSafeEntryComparatorByTelExt = Comparator.nullsLast(entryByTelExtComparator);

	// CompareTo method which only uses the Entry's telExt. Used for when a user wants to delete and Entry by entering the telephone extension
	// Allows methods to compare (and therefore search) Entries by their telephone extension
	public int compareToByTelExt(Entry that) {
		return nullSafeEntryComparatorByTelExt.compare(this, that);
	}

}
