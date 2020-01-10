package dataStructure;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DGraph implements graph {

    private HashMap<Integer, node_data> MapNode = new HashMap<>();
    private HashMap<Integer, HashMap<Integer, edge_data>> MapEdge = new HashMap<>();
    private int nodeSize;
    private int edgeSize;
    private int MC;

    public DGraph() {
        this.MapEdge = new HashMap<>();
        this.MapNode = new HashMap<>();
        this.nodeSize = 0;
        this.MC = 0;
        this.edgeSize = 0;
    }

    @Override
    public node_data getNode(int key) {
        if (MapNode.containsKey(key))
            return this.MapNode.get(key);
        return null;
    }

    @Override
    public edge_data getEdge(int src, int dest) {
        try {
            return this.MapEdge.get(src).get(dest); // if edge not exist should throw NullPointerException
        } catch (NullPointerException e) {
            return null;
        }
    }

    @Override
    public void addNode(node_data n) {
        if (!MapNode.containsKey(n)) {
            this.MapNode.put(n.getKey(), n);
            this.nodeSize++;
            this.MC++;
        }
        else {
            System.out.println("Node already exist");
        }
    }

    @Override
    public void connect(int src, int dest, double w) {
        edge_data ed = new Edge(src,dest,w);
        if(this.getNode(src)!=null && this.getNode(dest)!=null) {  // the nodes isnt exist.
            if (this.getEdge(src, dest) == null) {  // the edge isnt exist.
                if(this.MapEdge.get(src) == null) {   // the Hashmap of neighburs isnt exist
                    HashMap<Integer, edge_data> hMe = new HashMap<>();
                    this.MapEdge.put(src,hMe);
                    this.MapEdge.get(src).put(dest,ed);
                }
                else {         //hash map of neighburs exist but the edge isnt.
                    this.MapEdge.get(src).put(dest, ed);
                }
            }
            else{
                this.removeEdge(src,dest);
                this.connect(src,dest,w);
            }
        }
        else {
            throw new RuntimeException("Wrong input, Missing node.");
        }
        this.edgeSize++;
        this.MC++;
    }

    @Override
    public Collection<node_data> getV() {
        return this.MapNode.values();
    }

    @Override
    public Collection<edge_data> getE(int node_id) {
        if (this.MapEdge.get(node_id) != null) { // if there is edges to this node
            return this.MapEdge.get(node_id).values();
        }
        return null;
    }

    @Override
    public node_data removeNode(int key) {
        node_data node = this.getNode(key);
        if (node != null) {
            this.MapNode.remove(key);
            this.nodeSize--;
            if (this.MapEdge.get(key) != null)
                this.MapEdge.remove(key);
            for (Map.Entry m : MapEdge.entrySet()) {
                int OtherKey = (int) m.getKey();
                if (this.MapEdge.get(OtherKey).get(key) != null)
                    removeEdge(OtherKey, key);
            }
        }
        return node;
    }

    @Override
    public edge_data removeEdge(int src, int dest) {
        edge_data e = this.getEdge(src, dest);
        try {
            if (e != null) {//check if their is a edge between src and dest
                this.MapEdge.get(src).remove(dest);
                this.MC++;
                this.edgeSize--;
                return e;
            }
        } catch (Exception ex) {
            System.out.println("Error with the remove edge");

        }
        return e;
    }

    @Override
    public int nodeSize() {

        return this.nodeSize;
    }

    @Override
    public int edgeSize() {

        return this.edgeSize;
    }

    @Override
    public int getMC() {

        return this.MC;
    }
}
