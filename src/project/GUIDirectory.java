package project;

// Imported utilities

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;

// Class which embeds the ListDirectory implementation of the Directory interface, in a GUI using Javax.swing
public class GUIDirectory {

	// Private class fields
	private JFrame mainMenuFrame;
	private ListDirectory directory;
	private JPanel pane;
	private JTextField surnameField;
	private JTextField initialsField;
	private JTextField telExtField;
	private JTextField newTelExtField;
	private JList<Entry> displayList;
	private Container directoryContentPane;
	private final String successful = "Operation successful!";
	private final String failure = "Operation failed. Please try again";
	//TODO
	public GUIDirectory() throws FileNotFoundException {
		directory = new ListDirectory();
		Scanner inFile = new Scanner(new FileReader("Names.txt"));	
		while (inFile.hasNextLine()) {
			String entry = inFile.nextLine();
			entry.trim();
			String[] entryArgs = entry.split("\\s+");
			directory.addEntry(entryArgs[0], entryArgs[1], entryArgs[2]);
		}
		inFile.close();
		
		generateGUI();

	}

	// A method which includes all the functionality to generate a new GUI
	private void generateGUI() {
		mainMenuFrame = new JFrame("Directory GUI"); //Creates main JFrame which will hold the GUI

		mainMenuFrame.setLayout(new GridLayout(2, 4)); //Sets the layout of the GUI

		mainMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Means that the window-close button will exit the program

		// Defines all the buttons for the GUI main menu
		JButton insertEntryButton = new JButton("Insert new entry");
		JButton deleteEntryByNameButton = new JButton("Delete entry by name");
		JButton deleteEntryByTelExtButton = new JButton("Delete entry by telephone extension");
		JButton lookupTelExtButton = new JButton("Lookup telephone extension");
		JButton changeTelExtButton = new JButton("Change telephone extension");

		// Adds all the generated buttons to the JFrame, which displays them in GUI
		mainMenuFrame.add(insertEntryButton);
		mainMenuFrame.add(deleteEntryByNameButton);
		mainMenuFrame.add(deleteEntryByTelExtButton);
		mainMenuFrame.add(lookupTelExtButton);
		mainMenuFrame.add(changeTelExtButton);

		// Code to initially display the directory as a JList (Visual List element)
		directoryContentPane = mainMenuFrame.getContentPane (); //Defines directoryContentPane

		List<Entry> entryList; //Declares a new List
		entryList = directory.getEntryList(); //Assigns the list to the value returned by directory.getEntryList()
		DefaultListModel<Entry> model = new DefaultListModel<>();//Creates List model

		ListIterator<Entry> iterator = entryList.listIterator(); //Creates iterator object which will be used to iterate over entryList

		//Iterates over entryList and adds each object to the list model
		while (iterator.hasNext()) {
			model.addElement(iterator.next());
		}
		//Sets the value of the display list field to a new list which takes the list model as an argument.
		//This creates a new visual list element which displays all the elements original stored in the directory's entryList field
		displayList = new JList<Entry>(model); 
		displayList.setFont(new Font("monospaced", Font.PLAIN, 10)); //Defines a style for the JList, creating a nice constant format
		
		JScrollPane scrollPane = new JScrollPane(); //Creates a new JScrollPane object (Scroll bar)
		
		//Adds the scroll bar to displayList (the JList we're using to display(print) all the entries)
		scrollPane.setViewportView(displayList); 
		directoryContentPane.add(scrollPane);
		
		//Creates a new action listener for the insertEntryButton
		insertEntryButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				openInsertEntryPanel(); //Action performed is defined by this method
			}
		});

		//Creates a new action listener for the deleteEntryByTelExtButton
		deleteEntryByTelExtButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openDeleteEntryByTelExtPanel();//Action performed is defined by this method
			}
		});
		//Creates a new action listener for the deleteEntryByNameButton
		deleteEntryByNameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openDeleteEntryByNamePanel();//Action performed is defined by this method
			}
		});
		//Creates a new action listener for the lookupTelExtButton
		lookupTelExtButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openLookupTelExtPanel();//Action performed is defined by this method
			}
		});
		//Creates a new action listener for the changeTelExtButton
		changeTelExtButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				openChangeTelExtPanel();//Action performed is defined by this method
			}
		});


		mainMenuFrame.pack(); //Sets the JFrame size so it is big enough to properly display all GUI elements
		mainMenuFrame.setVisible(true); // Makes the the JFrame (and therefore the whole GUI) visible to the user

	}

	//Defines the action performed when someone clicks on the insertEntryButton
	private void openInsertEntryPanel() {
		// Error message stored in a constant
		final String fillAllFieldsErrorMessage = "Error. You must fill in all text fields. Please try again";
		pane = new JPanel(); //Creates a new JPanel that GUI elements can be added to
		pane.setLayout(new GridLayout(0, 2, 2, 2)); //Defines the layout of the Panel

		// Defines the value if three JTextFields which take the user's text input
		surnameField = new JTextField(5);
		initialsField = new JTextField(5);
		telExtField = new JTextField(5);

		//Adds the three textFields and three corresponding labels to the Panel
		pane.add(new JLabel("Please enter surname"));
		pane.add(surnameField);
		pane.add(new JLabel("Please enter initials"));
		pane.add(initialsField);
		pane.add(new JLabel("Please enter telephone extension number (5-digits)"));
		pane.add(telExtField);



		// Creates a new JOptionPane which acts as a pop-up box which takes the user input of surname, initials and telExt
		// Which will be used to create a new Entry object to be inserted
		// Takes are JPanel pane as an argument which forms most of its GUI elements.
		// OK_CANCEL_OPTION defines that it will have an ok and a cancel button
		// Also takes the JFrame object as an argument that defines where it will be displayed and a title message "Please fill in all the fields"
		int option = JOptionPane.showConfirmDialog(mainMenuFrame, pane, "Please fill in all the fields",
				JOptionPane.OK_CANCEL_OPTION);
		try {
			if (option == JOptionPane.OK_OPTION) { //If the users selects the ok button at the end of their interaction with the optionpane
				//Checks to ensure that the user has filled in all the text fields
				if (surnameField.getText().trim().length() == 0 || initialsField.getText().trim().length() == 0
						|| telExtField.getText().trim().length() == 0) {
					throw new IllegalArgumentException(); //If the user has not filled in all text fields, throw IllegalArgumentException
				}
				// The above exception is not thrown. Then these constants will be assigned to the value of the corresponding text fields
				// Trimmed to avoid errors caused by spaces at either end of the text fields
				final String surnameInput = surnameField.getText().trim(); 
				final String initialsInput = initialsField.getText().trim();
				final String telExtInput = telExtField.getText().trim();
				
				if (directory.addEntry(surnameInput, initialsInput, telExtInput)) { // Tries to add Entry to directory and returns true if successful
					printDirectory(); //Redefines the JList being displayed so that it is updated with the new Entry that has been added
					JOptionPane.showMessageDialog(mainMenuFrame, successful); //Displays a pop-up with an affirmative success message
				} else // If directory.addEntry fails then display a pop-up message saying the operation has failed
					JOptionPane.showMessageDialog(mainMenuFrame, failure);
			}
		} catch (IllegalArgumentException e) { // Handle exception thrown if the user does not fill in all text fields in the JPanel
			JOptionPane.showMessageDialog(mainMenuFrame, fillAllFieldsErrorMessage); // Generates pop-up with a prompt to fill in all text fields
		}
	}
	//Defines the action performed when someone clicks on the deleteEntryByTelExtButton
	private void openDeleteEntryByTelExtPanel() {
		final String emptyString = ""; //Empty string which will be used in place of surname and initials when searching Entries by telExt
		final int telExtLength = 5; // The length used to validate that the telExt input by the user is a valid value
		// Error message which is output if the uers input telExt is not valid
		final String badLengthErrMessage = "Error. Telephone extension must be 5 digits in length. Please try again";
		pane = new JPanel(); //Creates a new JPanel that GUI elements can be added to
		pane.setLayout(new GridLayout(0, 2, 10, 20)); //Sets the panel's layout
		telExtField = new JTextField(5); //Defines a text fields where users can input a telExt
		// Adds the text field and a corresponding label to the panel
		pane.add(new JLabel("Please enter telephone extension number (5-digits)"));
		pane.add(telExtField);
		
		// Creates a new JOptionPane which acts as a pop-up box which takes the user input of telephone extension
		// takes the panel we've created as an argument and OK_CANCEL_OPTION as an argument which creates an ok and cancel button
		// Also takes the JFrame object as an argument that defines where it will be displayed and a title message "Please fill in all the fields"
		int option = JOptionPane.showConfirmDialog(mainMenuFrame, pane, "Please fill in all the fields",
				JOptionPane.OK_CANCEL_OPTION);
		try {
			if (option == JOptionPane.OK_OPTION) {//If the users selects the ok button at the end of their interaction with the optionpane
				if (telExtField.getText().trim().length() != telExtLength) { //Checks to ensure that the user has filled in the text field
					throw new IllegalArgumentException(); // If the text field is empty, throw a new IllegalArgumentException
				}
				// Set the value of the constant telExtInput to the value stored in the text field telExtField
				final String telExtInput = telExtField.getText().trim();
				
				// As the user has elected to remove an entry using the Entry's telExt; the two other arguments can be empty strings
				if (directory.removeEntry(emptyString, emptyString, telExtInput)) { // Runs method to remove the entry which returns true if it successfully removes the entry
					printDirectory(); //Redefines the JList being displayed so that it is updated with the Entry removed
					JOptionPane.showMessageDialog(mainMenuFrame, successful); //Displays a pop-up with an affirmative success message
				} else // If directory.removeEntry fails then display a pop-up message saying the operation has failed
					JOptionPane.showMessageDialog(mainMenuFrame, failure); 

			}
		} catch (IllegalArgumentException e) { // Handle exception thrown if the user does not fill in the text field in the JPanel
			JOptionPane.showMessageDialog(mainMenuFrame, badLengthErrMessage); // Generates pop-up with a prompt to fill in all text fields
		}
	}
	
	//Defines the action performed when someone clicks on the openDeleteEntryByNamePanel
	private void openDeleteEntryByNamePanel() {
		// Error message to be output if not all text fields are filled out by the user
		final String fillAllFieldsErrorMessage = "Error. You must fill in all text fields. Please try again";
		// Empty string stored in a constant to be used in place of telExt as an argument in an entry constructor
		final String emptyString = "";
		
		// Defines two text fields to be used in our JPanel for the user to input surname and initials
		surnameField = new JTextField(5);
		initialsField = new JTextField(5);
		
		pane = new JPanel(); // Generate new JPanel which we can add GUI elements to
		pane.setLayout(new GridLayout(0, 2, 2, 2)); // Define the panel's layout
		
		// Add the two text fields and two related labels to the panel
		pane.add(new JLabel("Please enter surname"));
		pane.add(surnameField);
		pane.add(new JLabel("Please enter initials"));
		pane.add(initialsField);

		// Creates a new JOptionPane which acts as a pop-up box which takes the user input of surname and initials
		// takes the panel we've created as an argument and OK_CANCEL_OPTION as an argument which creates an ok and cancel button
		// Also takes the JFrame object as an argument that defines where it will be displayed and a title message "Please fill in all the fields"
		int option = JOptionPane.showConfirmDialog(mainMenuFrame, pane, "Please fill in all the fields",
				JOptionPane.OK_CANCEL_OPTION);
		try {
			if (option == JOptionPane.OK_OPTION) { // If the user selects the ok button at the end of their interaction with the option pane
				//Checks that both the required fields have been filled out
				if (surnameField.getText().trim().length() == 0 || initialsField.getText().trim().length() == 0) {
					throw new IllegalArgumentException(); // If the fields have not been filled, throw a new IllegalArgumentException
				}
				// Assigns the value of two constants to hold the values stored in the two text fields
				// Will be used as constructor arguments when creating a new entry object to be used as a search value
				final String surnameInput = surnameField.getText().trim();
				final String initialsInput = initialsField.getText().trim();
				
				// As the user has elected to remove an entry using the Entry's name; the telExt argument for this method can be an empty string
				if (directory.removeEntry(surnameInput, initialsInput, emptyString)) { // Runs method to remove the entry which returns true if it successfully removes the entry
					printDirectory(); //Redefines the JList being displayed so that it is updated with the Entry removed
					JOptionPane.showMessageDialog(mainMenuFrame, successful); //Displays a pop-up with an affirmative success message
				} else // If directory.removeEntry fails then display a pop-up message saying the operation has failed
					JOptionPane.showMessageDialog(mainMenuFrame, failure); 

			}
		} catch (IllegalArgumentException e) {// Handle exception thrown if the user does not fill in all text fields in the JPanel
			JOptionPane.showMessageDialog(mainMenuFrame, fillAllFieldsErrorMessage); // Generates pop-up with a prompt to fill in all text fields
		}
	}
 
	//Defines the action performed when someone clicks on the lookupTelExtButton
	private void openLookupTelExtPanel() {
		// Error message to be output if not all text fields are filled out by the user
		final String fillAllFieldsErrorMessage = "Error. You must fill in all text fields. Please try again";
		final String outputPrefix = "Telephone extension: "; //Prefix to be used in conjunction with the output of the telExt the user has searched for
		final String returnedTelExt; // Constant that will be used to store the telExt to be output to the user
		
		// Defines two text fields that will hold user text input
		surnameField = new JTextField(5);
		initialsField = new JTextField(5);
		
		pane = new JPanel(); // Creates new JPanel which will hold GUI elements
		pane.setLayout(new GridLayout(0, 2, 2, 2)); // Defines the panel's layout
		
		// Add the two text fields and two related labels to the panel
		pane.add(new JLabel("Please enter surname"));
		pane.add(surnameField);
		pane.add(new JLabel("Please enter initials"));
		pane.add(initialsField);

		// Creates a new JOptionPane which acts as a pop-up box which takes the user input of surname and initials
		// takes the panel we've created as an argument and OK_CANCEL_OPTION as an argument which creates an ok and cancel button
		// Also takes the JFrame object as an argument that defines where it will be displayed and a title message "Please fill in all the fields"
		int option = JOptionPane.showConfirmDialog(mainMenuFrame, pane, "Please fill in all the fields",
				JOptionPane.OK_CANCEL_OPTION);
		try {
			if (option == JOptionPane.OK_OPTION) { // If the user selects the ok button at the end of their interaction with the option pane
				//Checks that both the required fields have been filled out
				if (surnameField.getText().trim().length() == 0 || initialsField.getText().trim().length() == 0) {
					throw new IllegalArgumentException(); // If the fields have not been filled, throw a new IllegalArgumentException
				}
				// Assigns the value of two constants to hold the values stored in the two text fields
				final String surnameInput = surnameField.getText().trim();
				final String initialsInput = initialsField.getText().trim();
				
				// Sets the value of returnedTelExt to the string returned by the directory method looupTelExt
				// Uses the two constants as method arguments
				returnedTelExt = directory.lookupExtNum(surnameInput, initialsInput);
				
				if (!(returnedTelExt.trim().length() == 0)) { // If the String returned by lookupTelExt isn't an empty String
					// Output a pop-up with the returned telExt a message prefix to the user
					JOptionPane.showMessageDialog(mainMenuFrame, (outputPrefix + returnedTelExt));
					// If lookupTelExt fails and returns an empty String then display a pop-up message saying the operation has failed
				} else JOptionPane.showMessageDialog(mainMenuFrame, failure); 
				
			}
		} catch (IllegalArgumentException e) {// Handle exception thrown if the user does not fill in all text fields in the JPanel
			JOptionPane.showMessageDialog(mainMenuFrame, fillAllFieldsErrorMessage); // Generates pop-up with a prompt to fill in all text fields
		}
		
	}
	//Defines the action performed when someone clicks on the changeTelExtButton
	private void openChangeTelExtPanel() {
		// Error message to be output if not all text fields are filled out by the user
		final String fillAllFieldsErrorMessage = "Error. You must fill in all text fields. Please try again";
		
		pane = new JPanel(); // Creates new JPanel that GUI element can be added to
		pane.setLayout(new GridLayout(0, 2, 2, 2)); //Defines the panel's layout

		// Creates four new text fields that will hold user text input for the fields of a currently existing Entry and a new telExt
		surnameField = new JTextField(5);
		initialsField = new JTextField(5);
		telExtField = new JTextField(5);
		newTelExtField = new JTextField(5);

		// Add the text fields and related labels to the panel
		pane.add(new JLabel("Please enter surname"));
		pane.add(surnameField);
		pane.add(new JLabel("Please enter initials"));
		pane.add(initialsField);
		pane.add(new JLabel("Please enter current telephone extension number (5-digits)"));
		pane.add(telExtField);
		pane.add(new JLabel("Please enter new telephone extension number (5-digits)"));
		pane.add(newTelExtField);

		// Creates a new JOptionPane which acts as a pop-up box which takes the user input of the fields and new telExt
		// takes the panel we've created as an argument and OK_CANCEL_OPTION as an argument which creates an ok and cancel button
		// Also takes the JFrame object as an argument that defines where it will be displayed and a title message "Please fill in all the fields"
		int option = JOptionPane.showConfirmDialog(mainMenuFrame, pane, "Please fill in all the fields",
				JOptionPane.OK_CANCEL_OPTION);
		try {
			if (option == JOptionPane.OK_OPTION) { // If the user selects the ok button at the end of their interaction with the option pane
				// Checks that all text fields have been filled by the user
				if (surnameField.getText().trim().length() == 0 || initialsField.getText().trim().length() == 0
						|| telExtField.getText().trim().length() == 0 || newTelExtField.getText().trim().length() == 0) {
					throw new IllegalArgumentException(); // If the fields have not been filled, throw a new IllegalArgumentException
				}
				
				// Declare and assign four new constants to hold the values stored in the text fields
				final String surnameInput = surnameField.getText().trim();
				final String initialsInput = initialsField.getText().trim();
				final String telExtInput = telExtField.getText().trim();
				final String newTelExtInput = newTelExtField.getText().trim();
				
				// Runs the directory method changeNumber() which returns true if it successfully changes the telExt of the Entry defined by the user
				if (directory.changeNumber(surnameInput, initialsInput, telExtInput, newTelExtInput)) {
					printDirectory(); // Updates the JList so that it displays with the newly updated telExt of the entry
					JOptionPane.showMessageDialog(mainMenuFrame, successful); //Displays a pop-up with an affirmative success message
				} else // If changeNumber() fails and returns false, create a pop-up saying the operation has failed
					JOptionPane.showMessageDialog(mainMenuFrame, failure);
			}
		} catch (IllegalArgumentException e) {// Handle exception thrown if the user does not fill in all text fields in the JPanel
			JOptionPane.showMessageDialog(mainMenuFrame, fillAllFieldsErrorMessage); // Generates pop-up with a prompt to fill in all text fields
		}
	}

	private void printDirectory() {
	List<Entry> entryList;
		
		entryList = directory.getEntryList();

		DefaultListModel<Entry> newModel = new DefaultListModel<>();

		ListIterator<Entry> iterator = entryList.listIterator();

		while (iterator.hasNext()) {
			newModel.addElement(iterator.next());
		}
		
		displayList.setModel(newModel);
	}

	public static void main(String[] args) throws FileNotFoundException {
		new GUIDirectory();
	}
}