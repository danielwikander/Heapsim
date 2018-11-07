package memory;

/**
 * Represents a block of memory used in the Buddy memory allocation algorithm.
 */
public class BuddyMemoryBlock {

    private int address;
    private int size;
    private BuddyMemoryBlock buddy;

    public BuddyMemoryBlock(int address, int size, BuddyMemoryBlock buddy) {
        this.address = address;
        this.size = size;
        this.buddy = buddy;
    }

    public BuddyMemoryBlock(int address, int size) {
        this.address = address;
        this.size = size;
    }

    // Get & Sets
    public int getAddress() {
        return address;
    }
    public void setAddress(int address) {
        this.address = address;
    }
    public int getSize() {return size;}
    public void setSize(int size) {this.size = size;}
    public void setBuddy(BuddyMemoryBlock buddy) { this.buddy = buddy;}
    public BuddyMemoryBlock getBuddy() { return buddy;}

}


