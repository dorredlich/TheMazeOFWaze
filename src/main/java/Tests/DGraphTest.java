package Tests;

import dataStructure.*;
import org.junit.Before;
import org.junit.Test;
import utils.Point3D;

import static org.junit.Assert.*;

public class DGraphTest {
    static DGraph Graph = new DGraph();
    static Point3D point[] = new Point3D[6];
    static NodeData d[] = new NodeData[6];

    @Before
    public void BeforeEach() {
        point[0] = new Point3D(7, 2, 0);
        point[1] = new Point3D(5, 3, 0);
        point[2] = new Point3D(1, 12, 0);
        point[3] = new Point3D(0, 8, 0);
        point[4] = new Point3D(2, 16, 0);
        point[5] = new Point3D(4, 12, 0);
        d[0] = new NodeData(1, 20, 0, point[0], "a");
        d[1] = new NodeData(2, 10, 0, point[1], "b");
        d[2] = new NodeData(3, 15, 0, point[2], "c");
        d[3] = new NodeData(4, 5, 0, point[3], "d");
        d[4] = new NodeData(5, 0, 0, point[4], "e");
        d[5] = new NodeData(6, 25, 0, point[5], "f");
        for (int i = 0; i < point.length; i++) {
            Graph.addNode(d[i]);
        }

        Graph.connect(1, 4, 20);
        Graph.connect(2, 1, 10);
        Graph.connect(6, 1, 5);
        Graph.connect(1, 6, 6);
        Graph.connect(4, 3, 440);
        Graph.connect(5, 6, 1);
        Graph.connect(6, 3, 22);
        Graph.connect(3, 4, 20);
        Graph.connect(2, 5, 10);
    }

    @Test
    public void getNode() {
        for (int i = 0; i < d.length; i++) {
            assertEquals(d[i], Graph.getNode(i + 1));
        }
    }

    @Test
    public void getEdge() {
        edge_data ans[] = new edge_data[9];
        ans[0] = new Edge(1, 4, 20);
        ans[1] = new Edge(2, 1, 10);
        ans[2] = new Edge(6, 1, 5);
        ans[3] = new Edge(1, 6, 6);
        ans[4] = new Edge(4, 3, 440);
        ans[5] = new Edge(5, 6, 1);
        ans[6] = new Edge(6, 3, 22);
        ans[7] = new Edge(3, 4, 20);
        ans[8] = new Edge(2, 5, 10);

        for (int i = 0; i < ans.length; i++) {
            assertEquals(Graph.getEdge(ans[i].getSrc(), ans[i].getDest()).toString(), ans[i].toString());
            System.out.println(Graph.getEdge(ans[i].getSrc(), ans[i].getDest()).toString() + "," + ans[i].toString());
        }
    }
    @Test
    public void addNode() {
        DGraph d = new DGraph();
        node_data nd = new NodeData(new Point3D(5,5,8));
        node_data nd2 = new NodeData(new Point3D(10,2,3));
        d.addNode(nd);
        d.addNode(nd2);
        assertEquals(d.getNode(7), nd);
        assertEquals(d.getNode(8), nd2);
    }

    @Test
    public void connect() {
        DGraph d = new DGraph();
        node_data nd = new NodeData(new Point3D(7,8,3));
        node_data nd2 = new NodeData(new Point3D(1,5,3));
        d.addNode(nd);
        d.addNode(nd2);
        d.connect(nd.getKey(),nd2.getKey(),1);
        d.getEdge(nd.getKey(),nd2.getKey());
        boolean flag1 = d.getE(nd.getKey()).contains(d.getEdge(nd.getKey(),nd2.getKey()));
        assertEquals(flag1,true);
    }

    @Test
    public void removeNode() {
        DGraph d = new DGraph();
        node_data nd = new NodeData(new Point3D(6,9,0));
        node_data nd2 = new NodeData(new Point3D(20,10,3));
        node_data nd3 = new NodeData(new Point3D(3,3,3));
        d.addNode(nd);
        d.addNode(nd2);
        d.addNode(nd3);
        d.removeNode(1);
        d.removeNode(2);
        assertEquals(null,d.getNode(1));
        assertEquals(null,d.getNode(2));

    }

    @Test
    public void getMC() {
        DGraph d = new DGraph();
        node_data nd = new NodeData(new Point3D(1,2,3));
        node_data nd2 = new NodeData(new Point3D(1,2,3));
        d.addNode(nd);
        d.addNode(nd2);
        d.connect(nd.getKey(),nd2.getKey(),10);
        d.removeEdge(nd.getKey(),nd2.getKey());
        assertEquals(4 , d.getMC());
    }

    @Test
    public void removeEdge() {
        DGraph p = new DGraph();
        node_data a1 = new NodeData(new Point3D(1,5,3));
        node_data a2 = new NodeData(new Point3D(1,2,8));
        p.addNode(a1);
        p.addNode(a2);
        p.connect(a1.getKey(),a2.getKey(), 1);
        p.removeEdge(a1.getKey(),a2.getKey());
        assertEquals(p.getEdge(a1.getKey(),a2.getKey()),null);
    }

    @Test
    public void nodeSize() {
        DGraph p = new DGraph();
        node_data a1 = new NodeData(new Point3D(3,2,3));
        node_data a2 = new NodeData(new Point3D(1,4,8));
        p.addNode(a1);
        p.addNode(a2);
        assertEquals(2,p.nodeSize());
        p.removeNode(a1.getKey());
        assertEquals(1,p.nodeSize());
    }

    @Test
    public void edgeSize() {
        DGraph p = new DGraph();
        node_data a1 = new NodeData(new Point3D(1,2,0));
        node_data a2 = new NodeData(new Point3D(1,2,0));
        p.addNode(a1);
        p.addNode(a2);
        p.connect(a1.getKey(),a2.getKey(),10);
        assertEquals(p.edgeSize(),1);
        p.removeEdge(a1.getKey(),a2.getKey());
        assertEquals(p.edgeSize(),0);
    }
}
