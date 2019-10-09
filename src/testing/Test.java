package testing;

// Interface which I've used to help define my ImplementationStopWatch tests
public interface Test {
	// Specifies all the required test methods
	void testAddEntry();
	void testRemoveEntryByTelExt();
	void testRemoveEntryByName();
	void testChangeNumber();
	void testLookupTelExtNum();
	void testPrintDirectory();
}
