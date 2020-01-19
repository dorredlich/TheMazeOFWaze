package algorithms;

import java.io.*;
import java.util.*;

//import com.sun.corba.se.impl.orbutil.graph.Graph;
import dataStructure.*;
import org.w3c.dom.events.EventException;

/**
 * This empty class represents the set of graph-theory algorithms
 * which should be implemented as part of Ex2 - Do edit this class.
 * @author
 *
 */
public class Graph_Algo implements graph_algorithms,Serializable {
    private graph GraphAlgo;

    public Graph_Algo(){
        this.GraphAlgo = new DGraph();
    }
    public Graph_Algo(graph graph) {
        this.GraphAlgo=graph;
    }

    @Override
    public void init(graph g) {
        this.GraphAlgo = g;
    }

    /**
     * the function will to deserialize from a file
     * @param file_name name of the file
     */
    @Override
    public void init(String file_name) {
        try {
            FileInputStream file = new FileInputStream(file_name);
            ObjectInputStream in = new ObjectInputStream(file);
            this.GraphAlgo = (DGraph) in.readObject();
            in.close();
            file.close();

            System.out.println("Object has been deserialized");
        }

        catch (IOException ex) {
            System.out.println("Error with the file");
        }

        catch (ClassNotFoundException ex) {
            System.out.println("ClassNotFoundException is caught");
        }

    }

    /**
     * the function will to serialize to a file
     * @param file_name name of the file
     */
    @Override
    public void save(String file_name) {
        try {
            FileOutputStream file = new FileOutputStream(file_name);
            ObjectOutputStream out = new ObjectOutputStream(file);
            out.writeObject(this.GraphAlgo);

            out.close();
            file.close();

            System.out.println("Object has been serialized");
        } catch (IOException e) {
            System.err.println("Error with save the file.");
        }

    }

    /**
     * the funtion will pass all over the nodes in the graph and check if is connect to neighbor.
     * after passing all the neighbor that is connect to the node i will set the visit of the node to ture that i will not
     * pass again.
     * @return true if connect else false
     */
    @Override
    public boolean isConnected() {
        NodeData n = null;
        Collection<node_data> c = this.GraphAlgo.getV();
        Iterator<node_data> itnd = c.iterator();
        while (itnd.hasNext()) {
            n = (NodeData)itnd.next();
            Iterator<node_data> it1 = c.iterator();
            while (it1.hasNext()) {
                node_data n2 = it1.next();
                if (this.GraphAlgo.getE(n2.getKey()) == null)
                    return false;
                Collection<edge_data> edge = this.GraphAlgo.getE(n2.getKey());
                Iterator<edge_data> ited = edge.iterator();
                while (ited.hasNext()) {//pass all over graph and check if their is connection between src and dest and set the visit to true.
                    edge_data ed = ited.next();
                    if (n.getKey() == ed.getDest())
                        n.setVisited(true);
                    if (n.getKey() == ed.getSrc())
                        n.setVisited(true);
                }
            }
            if (!n.isVisited())
                return false;
        }
        return true;
    }

    /**
     * function that return length of the shortest path between src ans dest using the dijekstra function.
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public double shortestPathDist(int src, int dest) {
        graph g2 = this.GraphAlgo;
        dijekstra(g2, src, dest);
        return g2.getNode(dest).getWeight();
    }

    /**
     * function that return the list of nodes of the shortest path between src ans dest using the dijekstra function.
     * @param src - start node
     * @param dest - end (target) node
     * @return
     */
    @Override
    public List<node_data> shortestPath(int src, int dest) {
        graph g = this.GraphAlgo;
        dijekstra(g, src, dest);
        List<node_data> list = new LinkedList<>();
        node_data tempDest = g.getNode(dest);
        try {
            while (tempDest.getKey() != src) {//begin from dest to src
                list.add(tempDest);
                tempDest = g.getNode(tempDest.getTag());
            }
            list.add(tempDest);
            int i = list.size() - 1;
            List<node_data> ans = new LinkedList<>();
            ans.add(tempDest);
            i--;
            while (i != 0) {//turn the list.
                ans.add(list.get(i));
                i--;
            }
            ans.add(list.get(0));
            return ans;
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * pass all over a sub graph that will get from a list of nodes.
     * @param targets the list of nodes to pass if their is connection
     * @return the list of targest.
     */
    @Override
    public List<node_data> TSP(List<Integer> targets) {
        List<node_data> list = new LinkedList<>();
        Iterator<Integer> i = targets.iterator();
        int src = i.next();
        list.add(0,GraphAlgo.getNode(src));
        while(i.hasNext()) {
            int dest = i.next();
            List<node_data> temp = shortestPath(src,dest);
            if (temp == null) return null;
            temp.remove(0);//avoid duplicates
            list.addAll(temp);
            src = dest;
        }
        return list;
    }

    /**
     * deep copy of the original graph
     * @return the copy graph
     */
    @Override
    public graph copy() {
        graph g = new DGraph();
        for (node_data n : this.GraphAlgo.getV()) {
            if (n != null) {
                node_data n1 = new NodeData(n.getKey(), n.getWeight(),n.getTag(),n.getLocation(),n.getInfo()); // create new node for deep copy
                g.addNode(n1);
            }
        }
        for (node_data n : this.GraphAlgo.getV()) {
            if (this.GraphAlgo.getE(n.getKey()) != null) {
                for (edge_data e : this.GraphAlgo.getE(n.getKey())) {
                    if (e != null) {
                        g.connect(e.getSrc(), e.getDest(), e.getWeight());
                    }
                }
            }
        }
        return g;
    }

    /**
     * helper function to set all the nodes weight to infinity and the tag to a zero.
     */
    public void setNodes() {
        Collection<node_data> temp = this.GraphAlgo.getV();
        for (node_data node : temp) {
            node.setTag(0);
            node.setWeight(Double.POSITIVE_INFINITY);
        }
    }

    /**
     * function that set all the weight of the nodes depending of the weight the edge is connect and
     * help by marking all the visited.
     * @param g the graph
     * @param src the start.
     */
    private void dijekstra(graph g, int src, int dest) {
        Collection<node_data> c = g.getV();
        HeapMin heap = new HeapMin();
        setNodes();
        g.getNode(src).setWeight(0);
        for (node_data nodes : c) {
            heap.insert(nodes);
            NodeData n = (NodeData)nodes;
            n.setVisited(false);
        }
        while (heap.getArray().size() != 0) {
            NodeData node = (NodeData) heap.extractMin();
            if (g.getE(node.getKey()) != null) {
                Collection<edge_data> c2 = g.getE(node.getKey());
                for (edge_data e : c2) {
                    NodeData node2 = (NodeData) g.getNode(e.getDest());
                    if (!node2.isVisited()) {
                        double weight = node.getWeight() + e.getWeight();
                        if (weight < node2.getWeight()) {
                            node2.setWeight(weight);
                            node2.setTag(node.getKey());// to know the previous node with the lowest cost
                            heap.build();
                        }
                    }
                }
                node.setVisited(true);
            }
        }
    }
}

