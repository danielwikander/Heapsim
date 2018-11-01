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

	private LinkedList<MemoryBlock>   freeMemory      = new LinkedList<MemoryBlock>();
	private HashMap<Integer, Pointer> allocatedMemory = new HashMap<Integer, Pointer>();

	/**
	 * Initializes an instance of a first fit-based memory.
	 * 
	 * @param size The number of cells.
	 */
	public FirstFit(int size) {
		super(size);
		Pointer     firstPointer = new Pointer(0, this);
		MemoryBlock initialBlock = new MemoryBlock(size, firstPointer);
		freeMemory.add(initialBlock);

		// TODO: Varför extendar FirstFit Memory?
		// Eftersom FirstFit är ett oallokerat minne,
		// kan firstPointer få (this) som argument?
	}

	/**
	 * Allocates a number of memory cells. 
	 * 
	 * @param size the number of cells to allocate.
	 * @return The address of the first cell.
	 */
	@Override
	public Pointer alloc(int size) {

	    int freeMemoryIndex = 0;
		int memoryAddress = -1;
		// Loop through the freeMemory list to find the first
		// memory address with enough space.
		for(int i = 0; i < freeMemory.size(); i++) {
			if(freeMemory.get(i).getsize() >= size) {
			    freeMemoryIndex = i;
				memoryAddress = freeMemory.get(i).getPointer().pointsAt();
				break;
			}
		}
		// If no space was found, return null
		if(memoryAddress == -1) {
			System.out.println("FirstFit: Not enough memory available.");
			return null;
		}

		// Creates a new pointer to the space to be allocated.
		Pointer newPointer  = new Pointer(memoryAddress, this);

		// Allocates the memory in the allocatedMemory map.
		allocatedMemory.put(memoryAddress, newPointer);

		// Updates the freeMemory, removing the allocated space and changing the size of the available space.
        Pointer newFreeBlockPointer = new Pointer(memoryAddress + size + 1, this);
        int newFreeBlockSize = freeMemory.get(freeMemoryIndex).getsize() - size;
        MemoryBlock nextMemoryBlock = new MemoryBlock(newFreeBlockSize, newFreeBlockPointer);
        freeMemory.remove(freeMemoryIndex);
        freeMemory.push(nextMemoryBlock);


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
		// TODO Implement this!
	}
	
	/**
	 * Prints a simple model of the memory. Example:
	 * 
	 * |    0 -  110 | Allocated
	 * |  111 -  150 | Free
	 * |  151 -  999 | Allocated
	 * | 1000 - 1024 | Free
	 */
	@Override
	public void printLayout() {
		// TODO Implement this!
	}
	
	/**
	 * Compacts the memory space.
	 */
	public void compact() {
		// TODO Implement this!
	}
}
