package algorithms;


import dataStructure.node_data;

import java.util.ArrayList;

public class HeapMin {

    private ArrayList<node_data> array;
    private int arraySize;

    public ArrayList<node_data> getArray(){
        return array;
    }

    public HeapMin(){
        this.array = new ArrayList<node_data>();
    }

    /**
     * Constructor
     * @param ar
     */
    public HeapMin(ArrayList<node_data> ar) {
        this.array = ar;
        build();
    }

    /**
     * insert the nodes to a min heep
     * @param node we want to add
     */
    public void insert(node_data node) {
        array.add(node);
        int i = array.size() - 1;
        int parent = parent(i);
        while (parent != i && array.get(i).getWeight() < array.get(parent).getWeight()) {
            swap(i, parent);
            i = parent;
            parent = parent(i);
        }
    }

    /**
     * Build Min Heap
     */
    public void build() {
        for (int i = (array.size() / 2) - 1; i >= 0; i--) {
            minHeapify(i);
        }
    }

    /**
     * order the heap to be min heap
     * @param i we get, if he bigger with one of the child we swap until i find is place
     */
    private void minHeapify(int i) {
        int l = left(i);
        int r = right(i);
        int smallest = -1;
        if (l <= array.size()-1 && array.get(l).getWeight() < array.get(i).getWeight()) {
            smallest = l;
        } else {
            smallest = i;
        }
        if (r <= array.size()-1 && array.get(r).getWeight() < array.get(smallest).getWeight()) {
            smallest = r;
        }
        if (smallest != i) {
            swap(i,smallest);
            minHeapify(smallest);
        }
    }


    /**
     * Returns the min node of the heap
     */
    public node_data extractMin() {
        if (array.size() == 0) {
            throw new IllegalStateException("Min-Heap undeflow!");
        }
        if(array.size() == 1){
            node_data min = array.get(0);
        }
        node_data min = array.get(0);
        array.set(0, array.get(array.size() - 1));
        array.remove(array.size() - 1);
        minHeapify(0);
        return min;
    }

    /**
     * the parent of a nodes
     * @param i the index the we check
     * @return the parent
     */
    private int parent(int i) {
        return (i - 1) / 2;
    }

    /**
     *
     * @param i
     * @return the left child of the parent
     */
    private int left(int i) {
        return 2 * i + 1;
    }

    /**
     *
     * @param i
     * @return right child of the parent
     */
    private int right(int i) {
        return 2 * i + 2;
    }


    public boolean isEmpty() {
        return array.size() == 0;
    }

    public node_data getMin() { return array.get(0); }


    /**
     * helper function to swap between nodes
     * @param i
     * @param parent the parent that i need to fund is place.
     */
    private void swap(int i, int parent) {
        node_data temp = array.get(parent);
        array.set(parent, array.get(i));
        array.set(i, temp);
    }
}
