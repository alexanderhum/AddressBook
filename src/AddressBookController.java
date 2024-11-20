import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * A Controller in the MVC pattern for the AddressBook.
 */
public class AddressBookController extends JOptionPane implements ActionListener {

    /** A Model in the MVC pattern for the AddressBook. */
    private final AddressBookModel model;

    /** A GUI representation of the AddressBook. */
    private final AddressBookFrame frame;

    /**
     * Creates an AddressBookController with the specified AddressBookModel and AddressBookFrame.
     * @param model An AddressBookModel in the MVC pattern for the AddressBook.
     * @param frame A GUI representation of the AddressBook.
     */
    public AddressBookController(AddressBookModel model, AddressBookFrame frame) {
        this.model = model;
        this.frame = frame;
    }

    private String getUserInput(String message) {
        String fileName = "";
        while (fileName.equals("")) {
            fileName = showInputDialog(message);
        }
        return fileName;
    }

    /**
     * @param buddyInfoParam A String representing a parameter of BuddyInfo that the user will input.
     * @return A String representing the Buddy's inputted info.
     */
    private String addBuddyInfo(String buddyInfoParam) {
        return getUserInput("Enter the Buddy's " + buddyInfoParam + ": ");
    }

    /**
     * Handles user input.
     * @param e The ActionEvent to be processed.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        if (actionCommand.equals(AddressBookFrame.newAddressBookString)) {
            // AddressBookFrame already creates a new AddressBookModel during setup
            // if clicked, should remove all buddies from the address book
            model.removeAllBuddies();
        } else if (actionCommand.equals(AddressBookFrame.displayBuddiesString)) {
            model.notifyViews();
        } else if (actionCommand.equals(AddressBookFrame.exportAddressBookString)) {
            try {
                // in case an I/O exception of some sort occurs
                model.save(getUserInput("Enter the name of the file you would like to export this address book to: "));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (actionCommand.equals(AddressBookFrame.serialExportAddressBookString)) {
            try {
                // in case an I/O exception of some sort occurs
                model.serialSave(getUserInput("Enter the name of the file you would like to export this address book to: "));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        } else if (actionCommand.equals(AddressBookFrame.importAddressBookString)) {
            model.importAddressBook(getUserInput("Enter the name of the file you would like to import an address book from: "));
        } else if (actionCommand.equals(AddressBookFrame.serialImportAddressBookString)) {
            try {
                // in case an I/O or ClassNotFound exception of some sort occurs
                model.serialImportAddressBook(getUserInput("Enter the name of the file you would like to import an address book from: "));
            } catch (IOException | ClassNotFoundException ex) {
                throw new RuntimeException(ex);
            }
        } else if (actionCommand.equals(AddressBookFrame.addBuddyString)) {
            String name = addBuddyInfo("Name");
            String address = addBuddyInfo("Address");
            String phoneNumber = addBuddyInfo("Phone Number");
            model.addBuddy(new BuddyInfo(name, address, phoneNumber));
        } else if (actionCommand.equals(AddressBookFrame.removeBuddyString)) {
            model.removeBuddy(frame.getJList().getSelectedIndex());
        }
    }
}
