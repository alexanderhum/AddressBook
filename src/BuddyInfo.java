import java.util.Scanner;

public class BuddyInfo {

    /** A String representing the buddy's name. */
    private final String name;

    /** A String representing the buddy's address. */
    private final String address;

    /** A String representing the buddy's phone number. */
    private final String phoneNumber;

    /**
     * Creates a default BuddyInfo object.
     */
    public BuddyInfo() {
        name = "Bob";
        address = "123 King Street";
        phoneNumber = "(123)456-7890";
    }

    /**
     * Creates a BuddyInfo object with the specified name, address, and phone number.
     * @param name A String representing the buddy's name.
     * @param address A String representing the buddy's address.
     * @param phoneNumber A String representing the buddy's phone number.
     */
    public BuddyInfo(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Creates a BuddyInfo object using the specified string.
     * @param buddyInfoString A String representing a BuddyInfo object.
     * @return A BuddyInfo object that has attributes defined in the specified string.
     */
    public static BuddyInfo importBuddyInfo(String buddyInfoString) {
        Scanner scanner = new Scanner(buddyInfoString).useDelimiter("\\s*#\\s*");
        BuddyInfo buddyInfo = new BuddyInfo(scanner.next(), scanner.next(), scanner.next());
        scanner.close();
        return buddyInfo;
    }

    /**
     * @return A String representing the buddy's attributes separated from one another using '#'.
     */
    @Override
    public String toString() {
        return name + "#" + address + "#" + phoneNumber;
    }
}
