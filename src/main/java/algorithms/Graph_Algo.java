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

    @Override
    public void init(graph g) {
        this.GraphAlgo = g;
    }


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

    @Override
    public List<node_data> TSP(List<Integer> targets) {
        graph con = new DGraph();
        if (this.isConnected() == false) {
            graph sub = new DGraph();
            sub = this.copy();
            for (node_data n : this.GraphAlgo.getV()) {// remove all the nodes that not in the targets list
                if (!targets.contains(n.getKey())) {
                    sub.removeNode(n.getKey());
                }
            }
            Graph_Algo subA = new Graph_Algo();
            if (subA.isConnected()) {
                con = sub;
            } else {
                return null;
            }
        } else {
            con = this.GraphAlgo;
        }
        List<node_data> ans = new LinkedList<>();
        int index = 1;
        for (int i = 0; i < targets.size(); i++) {
            while (index < targets.size() && con.getNode(targets.get(index)).getInfo() == "1") {
                index++;
            }
            if (index == targets.size()) {
                break;
            }
            List<node_data> temp = shortestPath(targets.get(i), targets.get(index));
            for (node_data n : temp) {
                if (n.getKey() == targets.get(i) && i != 0) {
                    continue;
                }
                ans.add(n);
                if (targets.contains(n.getKey())) {
                    con.getNode(n.getKey()).setInfo("1");
                }
            }

        }
        return ans;
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

       public void setNodes() {
        Collection<node_data> temp = this.GraphAlgo.getV();
        for (node_data node : temp) {
            node.setTag(0);
            node.setWeight(Double.POSITIVE_INFINITY);
        }
    }

    /**
     * function that set all the weight of the nodes depending of the weight the edge is and
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

