package memory;

/**
 * Represents a block of memory.
 */
public class MemoryBlocktest {

    private int address;
    private int size;

    public MemoryBlocktest(int address, int size) {
        this.address = address;
        this.size = size;
    }

    //  public MemoryBlocktest(int size) {
    //   this.size = size;
    //}

    // Get & Sets
    public int getAddress() {
        return address;
    }
    public void setAddress(int address) {
        this.address = address;
    }
    public int getSize() {return size;}
    public void setSize(int size) {this.size = size;}

}


