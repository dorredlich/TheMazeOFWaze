package Tests;

import algorithms.Graph_Algo;
import dataStructure.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import utils.Point3D;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;


public class Graph_AlgoTest {

    @Test
    public void shortestPath() {
        node_data a1 = new NodeData(new Point3D(1,2,0));
        node_data a2 = new NodeData(new Point3D(2,3,0));
        node_data a3 = new NodeData(new Point3D(3,4,0));
        DGraph d = new DGraph();
        d.addNode(a1);
        d.addNode(a2);
        d.addNode(a3);
        d.connect(a1.getKey(),a2.getKey(),3);
        d.connect(a1.getKey(),a3.getKey(),4);
        Graph_Algo g = new Graph_Algo();
        g.init(d);
        assertEquals(g.shortestPath(a1.getKey(),a3.getKey()).toString(), "[1, 3]");
    }

    @Test
    public void testInit() {
        Graph_Algo ga = new Graph_Algo();
        DGraph dg = new DGraph();

        NodeData a  = new NodeData(new Point3D(1, 3, 0));
        NodeData b  = new NodeData(new Point3D(2, 4, 0));
        NodeData c  = new NodeData(new Point3D(3, 5, 0));
        NodeData d  = new NodeData(new Point3D(4, 6, 0));
        NodeData e  = new NodeData(new Point3D(5, 6, 0));

        dg.addNode(a);
        dg.addNode(b);
        dg.addNode(c);
        dg.addNode(d);
        dg.addNode(e);

        dg.connect(a.getKey(), b.getKey(), 1);
        dg.connect(b.getKey(), c.getKey(), 3);
        dg.connect(c.getKey(), d.getKey(), 3);
        dg.connect(d.getKey(), e.getKey(), 2);
        dg.connect(b.getKey(), d.getKey(), 1);
        dg.connect(c.getKey(), e.getKey(), 2);
        dg.connect(e.getKey(), a.getKey(), 2);
        ga.init(dg);
        ga.save("TestSave");
        Graph_Algo ga2 = new Graph_Algo();
        ga2.init("TestSave");
        assertEquals(ga.isConnected() == ga2.isConnected() , true);

    }

    @Test
    public void testIsConnected() {
        Graph_Algo ga = new Graph_Algo();

        DGraph h = new DGraph();
        ga.init(h);
        NodeData a  = new NodeData(new Point3D(1, 3, 0));
        NodeData b  = new NodeData(new Point3D(2, 4, 0));
        NodeData c  = new NodeData(new Point3D(3, 5, 0));
        NodeData d  = new NodeData(new Point3D(4, 6, 0));
        NodeData e  = new NodeData(new Point3D(5, 6, 0));

        h.addNode(a);
        h.addNode(b);
        h.addNode(c);
        h.addNode(d);
        h.addNode(e);

        h.connect(a.getKey(), b.getKey(), 2);
        h.connect(b.getKey(), c.getKey(), 3);
        h.connect(c.getKey(), d.getKey(), 10);
        h.connect(d.getKey(), e.getKey(), 7);
        h.connect(e.getKey(), a.getKey(), 4);
        h.connect(e.getKey(), d.getKey(), 4);
        h.connect(c.getKey(), e.getKey(), 4);
        h.connect(e.getKey(), b.getKey(), 4);

        assertEquals(true, ga.isConnected());
    }

    @Test
    public void testShortestPathDist() {
        Graph_Algo ga = new Graph_Algo();
        DGraph dg = new DGraph();
        ga.init(dg);
        NodeData a = new NodeData(new Point3D(3, 2, 0));
        NodeData b = new NodeData(new Point3D(5, 3, 0));
        NodeData c = new NodeData(new Point3D(6, 4, 0));
        NodeData d = new NodeData(new Point3D(1, 5, 0));
        NodeData e = new NodeData(new Point3D(4, 6, 0));
        NodeData f = new NodeData(new Point3D(9, 7, 0));

        dg.addNode(a);
        dg.addNode(b);
        dg.addNode(c);
        dg.addNode(d);
        dg.addNode(e);
        dg.addNode(f);

        dg.connect(a.getKey(), b.getKey(), 1);
        dg.connect(b.getKey(), c.getKey(), 1);
        dg.connect(c.getKey(), d.getKey(), 1);
        dg.connect(d.getKey(), e.getKey(), 1);
        dg.connect(e.getKey(), f.getKey(), 1);
        dg.connect(f.getKey(), a.getKey(), 5);
        dg.connect(b.getKey(), e.getKey(), 1);
        dg.connect(c.getKey(), f.getKey(), 3);
        dg.connect(a.getKey(), e.getKey(), 4);
        dg.connect(f.getKey(), b.getKey(), 7);
        assertEquals(3.0, ga.shortestPathDist(a.getKey(), f.getKey()), 0.0);
    }

    @Test
   public void testTSP() {
        Graph_Algo ga = new Graph_Algo();
        DGraph dg = new DGraph();
        NodeData a = new NodeData(new Point3D(3, 2, 0));
        NodeData b = new NodeData(new Point3D(5, 3, 0));
        NodeData c = new NodeData(new Point3D(6, 4, 0));
        NodeData d = new NodeData(new Point3D(1, 5, 0));
        NodeData e = new NodeData(new Point3D(4, 6, 0));
        NodeData f = new NodeData(new Point3D(9, 7, 0));

        dg.addNode(a);
        dg.addNode(b);
        dg.addNode(c);
        dg.addNode(d);
        dg.addNode(e);
        dg.addNode(f);

        dg.connect(1,6,1);
        dg.connect(1,3,34);
        dg.connect(2,1,6);
        dg.connect(2,6,1);
        dg.connect(5,2,13);
        dg.connect(3,5,11);
        dg.connect(4,6,5);
        dg.connect(6,3,2);
        dg.connect(6,5,1);

        ga.init(dg);
        List<Integer> lst = new LinkedList<>();
        lst.add(1);
        lst.add(6);
        lst.add(5);
        lst.add(2);


        List<node_data> path = ga.TSP(lst);
        assertEquals(path.remove(0).getKey(),1);
        assertEquals((path.remove(0)).getKey(),6);
        assertEquals((path.remove(0)).getKey(),5);
        assertEquals((path.remove(0)).getKey(),2);
    }

    @Test
    public void testCopy() {
        Graph_Algo ga = new Graph_Algo();
        DGraph dg = new DGraph();
        ga.init(dg);
        NodeData a = new NodeData(new Point3D(3, 2, 0));
        NodeData b = new NodeData(new Point3D(5, 3, 0));
        NodeData c = new NodeData(new Point3D(6, 4, 0));
        NodeData d = new NodeData(new Point3D(1, 5, 0));
        NodeData e = new NodeData(new Point3D(4, 6, 0));
        NodeData f = new NodeData(new Point3D(9, 7, 0));

        dg.addNode(a);
        dg.addNode(b);
        dg.addNode(c);
        dg.addNode(d);
        dg.addNode(e);
        dg.addNode(f);
        dg.connect(a.getKey(), b.getKey(), 1);
        dg.connect(b.getKey(), c.getKey(), 1);
        dg.connect(c.getKey(), d.getKey(), 1);
        dg.connect(d.getKey(), e.getKey(), 1);
        dg.connect(e.getKey(), f.getKey(), 1);
        dg.connect(f.getKey(), a.getKey(), 5);
        dg.connect(b.getKey(), e.getKey(), 1);
        dg.connect(c.getKey(), f.getKey(), 3);
        dg.connect(a.getKey(), e.getKey(), 4);
        dg.connect(f.getKey(), b.getKey(), 7);
        assertEquals(6, dg.nodeSize());
        assertEquals(10, dg.edgeSize());
    }
}



