package memory;

/**
 * Represents a block of memory.
 */
public class MemoryBlock {

    private int size;
    private Pointer pointer;

    public MemoryBlock(int size, Pointer pointer) {
        this.size = size;
        this.pointer = pointer;
    }

    public MemoryBlock(int size) {
        this.size = size;
    }

    // Get & Sets
    public int getsize() {return size;}
    public Pointer getPointer() {return pointer;}
    public void setPointer(Pointer pointer) {this.pointer = pointer;}
}


