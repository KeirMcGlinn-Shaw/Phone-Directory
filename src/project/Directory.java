package project;

// Directory interface
public interface Directory {

	// Defines the methods which implementations of this interface must include and their parameters
	boolean addEntry(String surname, String initials, String telExt);
	boolean removeEntry(String surname, String initials, String telExt);
	boolean changeNumber (String surname, String initials, String telExt, String newTelExt);
	String lookupExtNum (String name, String initials);
	void printDirectory();
}
