import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * A Model in the MVC pattern for the AddressBook.
 */
public class AddressBookModel extends DefaultListModel<BuddyInfo> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /** A graphical representation of the AddressBookModel. */
    private final List<AddressBookView> views; // never assume that there will be a singular view, can have many

    /**
     * Constructs an empty AddressBook with the specified view.
     */
    public AddressBookModel() {
        super();
        views = new ArrayList<>();
    }

    /**
     * Subscribes the specified AddressBookView to this AddressBookModel.
     * @param view An AddressBookView to subscribe to this AddressBookModel.
     */
    public void addView(AddressBookView view) { views.add(view); }

    /**
     * Updates AddressBookView when a change is made.
     */
    public void notifyViews() {
        for (AddressBookView view : views) {
            view.update(this);
        }
    }

    /**
     * Adds a specified BuddyInfo to the end of the AddressBook.
     * @param buddy A BuddyInfo to add to the AddressBook.
     */
    public void addBuddy(BuddyInfo buddy) {
        if (buddy != null) {
            addElement(buddy);
            notifyViews();
        }
    }

    /**
     * Removes the BuddyInfo at the specified index.
     * @param index The index of the BuddyInfo object to remove.
     */
    public void removeBuddy(int index) {
        if (index >= 0 && index < size()) {
            removeElementAt(index);
            notifyViews();
        }
    }

    /**
     * Removes all buddies from the AddressBook.
     */
    public void removeAllBuddies() {
        removeAllElements();
        notifyViews();
    }

    /**
     * Saves each buddy on a separate line in the specified file.
     * @param fileName A String representing the name of the file to store the buddies in.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    public void save(String fileName) throws IOException {
        // create a file output stream to write to the specified file
        try (FileOutputStream fos = new FileOutputStream(fileName)) {
            fos.write(toString().getBytes()); // write buddies to file
            // file output stream closes automatically, will release any system resources associated with the stream
        }
    }

    /**
     * Saves the AddressBookModel using serialization.
     * @param fileName A String representing the name of the file to store the AddressBookModel in.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    public void serialSave(String fileName) throws IOException {
        writeObject(new ObjectOutputStream(new FileOutputStream(fileName)));
    }

    /**
     * Saves the AddressBookModel using serialization.
     * @param out An ObjectOutputStream used to write to a file.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     */
    @Serial
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeObject(toString());
    }

    /**
     * Reads the specified file and recreates the AddressBookModel.
     * @param fileName A String representing the name of the file to recreate the AddressBook from.
     */
    public void importAddressBook(String fileName) {
        removeAllBuddies();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String buddyInfoString = bufferedReader.readLine();
            while (buddyInfoString != null) {
                addBuddy(BuddyInfo.importBuddyInfo(buddyInfoString));
                buddyInfoString = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads the specified file and recreates the AddressBookModel using serialization.
     * @param fileName A String representing the name of the file to recreate the AddressBookModel from.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     * @throws ClassNotFoundException Thrown when an app tries to load in a class through a string unsuccessfully.
     */
    public void serialImportAddressBook(String fileName) throws IOException, ClassNotFoundException {
        readObject(new ObjectInputStream(new FileInputStream(fileName)));
    }

    /**
     * Reads the specified file and recreates the AddressBookModel using serialization.
     * @param in An ObjectInputStream used to read from a file.
     * @throws IOException Signals that an I/O exception of some sort has occurred.
     * @throws ClassNotFoundException Thrown when an app tries to load in a class through a string unsuccessfully.
     */
    @Serial
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        removeAllBuddies();
        String addressBookModelString = (String) in.readObject();
        String[] addressBookModelStringArray = addressBookModelString.split("\n");
        for (String s : addressBookModelStringArray) {
            addBuddy(BuddyInfo.importBuddyInfo(s));
        }
    }

    /**
     * @return A String representing the AddressBook.
     */
    @Override
    public String toString() {
        StringBuilder addressBookString = new StringBuilder();
        for (Enumeration<BuddyInfo> myBuddies = elements(); myBuddies.hasMoreElements(); ) {
            addressBookString.append(myBuddies.nextElement().toString()).append("\n");
        }
        return addressBookString.toString();
    }
}
