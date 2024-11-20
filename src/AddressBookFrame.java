import javax.swing.*;
import java.awt.*;

public class AddressBookFrame extends JFrame implements AddressBookView {
    public static final String newAddressBookString = "Create a new AddressBook";
    public static final String displayBuddiesString = "Display the BuddyInfos contained in the AddressBook";
    public static final String exportAddressBookString = "Export the AddressBook";
    public static final String importAddressBookString = "Import an AddressBook";
    public static final String serialExportAddressBookString = "Serially export the AddressBook";
    public static final String serialImportAddressBookString = "Serially import the AddressBook";
    public static final String addBuddyString = "Add a BuddyInfo to the AddressBook";
    public static final String removeBuddyString = "Remove a BuddyInfo from the AddressBook";

    private final JList<BuddyInfo> addressBook;

    private JMenuItem newAddressBook;
    private JMenuItem displayBuddies;
    private JMenuItem exportAddressBook;
    private JMenuItem serialExportAddressBook;
    private JMenuItem serialImportAddressBook;
    private JMenuItem removeBuddy;

    /**
     * Creates a GUI representation of the AddressBook.
     */
    public AddressBookFrame() {
        // initialize JFrame
        super("Address Book");
        setLayout(new BorderLayout());

        // create a model and add a JList of the address book to the JFrame
        AddressBookModel model = new AddressBookModel();
        model.addView(this);
        addressBook = new JList<>(model);
        add(addressBook);

        // create a controller to handle user input
        AddressBookController controller = new AddressBookController(model, this);

        // initialize JMenuBar, JMenus, and JMenuItems
        setupJMenuBar(controller);

        // finish initializing JFrame
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // JFrame pops up in the middle of the screen
        setSize(600, 300);
        setVisible(true);
    }

    /**
     * Initializes a JMenuBar, JMenus, and JMenuItems.
     * @param controller An AddressBookController to handle user input.
     */
    private void setupJMenuBar(AddressBookController controller) {
        // create a JMenuBar and add it to the JFrame
        JMenuBar menuBar = new JMenuBar();
        this.setJMenuBar(menuBar);

        // create JMenus and add them to the JMenuBar
        JMenu addressMenu = new JMenu("AddressBook");
        JMenu buddyMenu = new JMenu("BuddyInfo");
        menuBar.add(addressMenu);
        menuBar.add(buddyMenu);

        // create JMenuItems and add them to the addressMenu
        newAddressBook = new JMenuItem(newAddressBookString);
        displayBuddies = new JMenuItem(displayBuddiesString);
        exportAddressBook = new JMenuItem(exportAddressBookString);
        serialExportAddressBook = new JMenuItem(serialExportAddressBookString);
        JMenuItem importAddressBook = new JMenuItem(importAddressBookString);
        serialImportAddressBook = new JMenuItem(serialImportAddressBookString);
        addressMenu.add(newAddressBook);
        addressMenu.add(displayBuddies);
        addressMenu.add(exportAddressBook);
        addressMenu.add(serialExportAddressBook);
        addressMenu.add(importAddressBook);
        addressMenu.add(serialImportAddressBook);

        // create JMenuItems and add them to the buddyMenu
        JMenuItem addBuddy = new JMenuItem(addBuddyString);
        removeBuddy = new JMenuItem(removeBuddyString);
        buddyMenu.add(addBuddy);
        buddyMenu.add(removeBuddy);

        // listen for JMenuItem selections
        newAddressBook.addActionListener(controller);
        displayBuddies.addActionListener(controller);
        exportAddressBook.addActionListener(controller);
        serialExportAddressBook.addActionListener(controller);
        importAddressBook.addActionListener(controller);
        serialImportAddressBook.addActionListener(controller);
        addBuddy.addActionListener(controller);
        removeBuddy.addActionListener(controller);

        // default JMenuItem enable settings
        newAddressBook.setEnabled(false);
        displayBuddies.setEnabled(false);
        exportAddressBook.setEnabled(false);
        serialExportAddressBook.setEnabled(false);
        importAddressBook.setEnabled(true);
        serialImportAddressBook.setEnabled(true);
        removeBuddy.setEnabled(false);
    }

    /**
     * @return A JList of BuddyInfo objects representing the AddressBook.
     */
    public JList<BuddyInfo> getJList() {
        return addressBook;
    }

    /**
     * Updates the graphical representation of the model.
     * @param model The AddressBookModel to update.
     */
    @Override
    public void update(AddressBookModel model) {
        // update the BuddyInfo objects in the JList
        addressBook.updateUI();

        // enable/disable JMenuItems
        boolean modelIsEmpty = model.isEmpty();
        newAddressBook.setEnabled(!modelIsEmpty);
        displayBuddies.setEnabled(!modelIsEmpty);
        exportAddressBook.setEnabled(!modelIsEmpty);
        serialExportAddressBook.setEnabled(!modelIsEmpty);
        removeBuddy.setEnabled(model.size() > 0);
    }

    public static void main(String[] args) { new AddressBookFrame(); }

}
