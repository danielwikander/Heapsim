package memory;

import java.util.HashMap;
import java.util.LinkedList;


/**
 * This memory model allocates memory cells based on the first-fit method.
 *
 * @author "Johan Holmberg, Malmö university"
 * @since 1.0
 */
public class FirstFit extends Memory {

    private LinkedList<MemoryBlock> freeMemory = new LinkedList<MemoryBlock>();
    private HashMap<Integer, Integer> allocatedMemory = new HashMap<Integer, Integer>(); // address -> size
    private int size; // size of memory

    /**
     * Initializes an instance of a first fit-based memory.
     *
     * @param size The number of cells.
     */
    public FirstFit(int size) {
        super(size);
        this.size = size;
        MemoryBlock initialBlock = new MemoryBlock(0, size);
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
        // TODO OK if algorithm allocates on first available memory in list instead of first available physical space?
        int freeMemoryIndex = 0;
        int memoryAddress = -1;
        // Loop through the freeMemory list to find the first
        // memory address with enough space.
        for (int i = 0; i < freeMemory.size(); i++) {
            if (freeMemory.get(i).getSize() >= size) {
                freeMemoryIndex = i;
                memoryAddress = freeMemory.get(i).getAddress();
                break;
            }
        }
        // If no space was found, return null
        if (memoryAddress == -1) {
            System.out.println("FirstFit: Not enough memory available.");
            return null;
        }

        // Creates a new pointer and block for the space to be allocated.
        Pointer newPointer = new Pointer(memoryAddress, this);

        // Allocates the memory in the allocatedMemory map.
        allocatedMemory.put(memoryAddress, size);

        // Updates the freeMemory, changing the size of the available space.
        int newFreeBlockSize = freeMemory.get(freeMemoryIndex).getSize() - size;

        // Checks if the size is more than 0. If not, removes it from the list.
        if (newFreeBlockSize > 0) {
            freeMemory.get(freeMemoryIndex).setSize(newFreeBlockSize);
            freeMemory.get(freeMemoryIndex).setAddress(memoryAddress + size);
        } else {
            freeMemory.remove(freeMemoryIndex);
        }

        // Returns a pointer to the newly allocated memory.
        return newPointer;
    }

    /**
     * Releases a number of data cells
     *
     * @param p The pointer to release.
     */
    @Override
    public void release(Pointer p) {

        boolean mergePreviousBlock = false;
        boolean mergeSubsequentBlock = false;
        int previousMergeIndex = 0;
        int allocatedMemoryBlockSize = allocatedMemory.get(p.pointsAt());

        // The following loops check for available blocks before and after the
        // allocated memory. If found, it merges the block with the newly released block.

        // Checks for an available block before the allocated memory.
        for (int i = 0; i < freeMemory.size(); i++) {
            // If there is an available memoryblock whose pointer + size matches the pointer
            // of the memory to be released, merge the two blocks.
            if (p.pointsAt() == freeMemory.get(i).getAddress() + freeMemory.get(i).getSize()) {
                freeMemory.get(i).setSize(freeMemory.get(i).getSize() + allocatedMemoryBlockSize);
                mergePreviousBlock = true;
                previousMergeIndex = i;
                break;
            }
        }

        // Checks for an available block after the allocated memory.
        for (int i = 0; i < freeMemory.size(); i++) {
            // If there is an available memoryblock at the end of the memory to be released
            // it merges the blocks.
            if (p.pointsAt() + allocatedMemoryBlockSize == freeMemory.get(i).getAddress()) {
                if(mergePreviousBlock) {
                    freeMemory.get(previousMergeIndex).setSize(freeMemory.get(previousMergeIndex).getSize() + freeMemory.get(i).getSize());
                    freeMemory.remove(i);
                } else {
                    freeMemory.get(i).setAddress(p.pointsAt());
                    freeMemory.get(i).setSize(freeMemory.get(i).getSize() + allocatedMemoryBlockSize);
                }
                mergeSubsequentBlock = true;
            }
        }

        // If it didn't merge previous or subsequent blocks, add new space to freeMemory.
        if (!mergePreviousBlock && !mergeSubsequentBlock) {
            MemoryBlock newFreeMemoryBlock = new MemoryBlock(p.pointsAt(), allocatedMemoryBlockSize);
            freeMemory.addFirst(newFreeMemoryBlock);
        }

        // Finally remove the memory from the allocated list.
        allocatedMemory.remove(p.pointsAt());
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

    /**
     * Compacts the memory space.
     */
    public void compact() {
        // TODO Implement this!
    }
}
