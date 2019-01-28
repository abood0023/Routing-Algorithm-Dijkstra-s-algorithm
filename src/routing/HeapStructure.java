package routing;

import java.util.Scanner;
import java.util.NoSuchElementException;
import java.util.Random;

public class HeapStructure {

    private static final int d = 2;
    private int heapSize;
    private Vertex[] heap;

    /**
     * Constructor *
     */
    public HeapStructure(int capacity) {
        heapSize = 0;
        heap = new Vertex[capacity + 1];
    }

    public boolean isInHeap(int x) {
        for (int i = 1; i <= heapSize; i++) {
            if (heap[i].getName() == x) {
                return true;
            }
        }
        return false;
    }

    /**
     * Function to check if heap is empty *
     */
    public boolean isEmpty() {
        return heapSize == 0;
    }

    /**
     * Check if heap is full *
     */
    public boolean isFull() {
        return heapSize == heap.length - 1;
    }

    /**
     * Clear heap
     */
    public void makeEmpty() {
        heapSize = 0;
    }

    /**
     * Function to get index parent of i *
     */
    private int parent(int i) {
        return (i) / d;
    }

    public int leftChild(int i) {
        return 2 * i;
    }

    public int rightChild(int i) {
        return 2 * i + 1;
    }

    /**
     * Function to insert element
     */
    public void insert(Vertex x) {
        if (isFull()) {
            System.out.println("heap is full so cann't insert another element");
        } else {
            heap[++heapSize] = x;
        }
    }

    public void maxHeapify(int index) {
        int l = leftChild(index);
        int r = rightChild(index);
        int largest;
        Vertex temp;
        if (l <= heapSize && heap[l].getWeight() > heap[index].getWeight()) {
            largest = l;
        } else {
            largest = index;
        }
        if (r <= heapSize && heap[r].getWeight() > heap[largest].getWeight()) {
            largest = r;
        }
        if (largest != index) {
            temp = heap[largest];
            heap[largest] = heap[index];
            heap[index] = temp;
            maxHeapify(largest);
        }
    }

    public int getHeapSize() {
        return heapSize;
    }

    /**
     * Function to find least element *
     */
    public Vertex findMax() {
        if (isEmpty()) {
            System.out.println("No any item in heap so not exist max element.");
        }
        if (heapSize >= 1) {
            if (heapSize > 1) {
                for (int i = heapSize / 2; i >= 1; i--) {
                    maxHeapify(i);
                }
            }
            return heap[1];
        }
        return null;

    }

    /**
     * Function to delete element at an index *
     */
    public Vertex deleteMax(int index) {
        if (isEmpty()) {
            throw new NoSuchElementException("Underflow Exception");
        }
        maxHeapify(1);
        Vertex keyItem = heap[index];
        heap[index] = heap[heapSize];
        heapSize--;
        maxHeapify(1);
        return keyItem;
    }

}
