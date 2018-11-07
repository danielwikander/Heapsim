package memory;

import java.util.HashMap;
import java.util.LinkedList;

/**
 * This memory model allocates memory cells based on the buddy method.
 *
 * @author "Johan Holmberg, Malmö university"
 * @since 1.0
 */
public class Buddy extends Memory {

    private LinkedList<BuddyMemoryBlock> freeMemory = new LinkedList<BuddyMemoryBlock>();
    private HashMap<Integer, Integer> allocatedMemory = new HashMap<Integer, Integer>(); // address -> size
    private int size; // size of memory

    /**
     * Initializes an instance of a buddy-based memory.
     *
     * @param size The number of cells.
     */
    public Buddy(int size) {
        super(size);
        BuddyMemoryBlock initialBlock = new BuddyMemoryBlock(0, size);
        freeMemory.add(initialBlock);
    }

    /**
     * Allocates a number of memory cells.
     *
     * @param size the number of cells to allocate.
     * @return The address of the first cell.
     */
    @Override
    public Pointer alloc(int size) {
        // TODO Implement this!


        return null;
    }

    /**
     * Releases a number of data cells
     *
     * @param p The pointer to release.
     */
    @Override
    public void release(Pointer p) {
        // TODO Implement this!
    }

    /**
     * Prints a simple model of the memory. Example:
     * <p>
     * |    0 -  110 | Allocated
     * |  111 -  150 | Free
     * |  151 -  999 | Allocated
     * | 1000 - 1024 | Free
     */
    @Override
    public void printLayout() {
        String format = "| %4d - %4d | %s \n";
        int currentAddress = 0;

        while (currentAddress < size) {
            boolean foundInFree = false;
            // Search through the freeMemory list for a block allocated on the current address.
            for (int j = 0; j < freeMemory.size(); j++) {
                if (freeMemory.get(j).getAddress() == currentAddress) {
                    System.out.format(format, currentAddress, currentAddress + freeMemory.get(j).getSize(), "Free");
                    currentAddress += freeMemory.get(j).getSize();
                    foundInFree = true;
                    break;
                }
            }
            // If the block was found in the freeMemory list, fetch it from the allocated memory map.
            if (!foundInFree) {
                int BlockSize = allocatedMemory.get(currentAddress);
                System.out.format(format, currentAddress, currentAddress + BlockSize, "Allocated");
                currentAddress += BlockSize;
            }
        }
    }
}
