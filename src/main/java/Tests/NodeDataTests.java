package Tests;

import dataStructure.NodeData;
import dataStructure.node_data;
import org.junit.Before;
import org.junit.Test;
import utils.Point3D;

import static org.junit.Assert.assertEquals;

public class NodeDataTests {

    static node_data[] node = new node_data[6];
    @Before
    public void BeforeEach(){
        node[0]= new NodeData(new Point3D(12,6,0));
        node[1]=new NodeData(new Point3D(13,1,0));
        node[2]=new NodeData(new Point3D(14,9,0));
        node[3]=new NodeData(new Point3D(15,15,0));
        node[4]=new NodeData(new Point3D(16,21,0));
        node[5]=new NodeData(new Point3D(17,3,0));
    }

    @Test
    public void getKey() {
        int ans[]= {1,2,3,4,5,6};
        for (int i = 0; i < node.length ; i++) {
            assertEquals(ans[i], node[i].getKey());
        }
    }

    @Test
    public void getLocation() {
        Point3D ans[]= {new Point3D(12,6,0),new Point3D(13,1,0),new Point3D(14,9,0),new Point3D(15,15,0),new Point3D(16,21,0),new Point3D(17,3,0)};
        for (int i = 0; i < node.length ; i++) {
            assertEquals(ans[i], node[i].getLocation());
        }
    }

    @Test
    public void setLocation() {
        Point3D ans[] = new Point3D[6];
        int x= 12;
        int y =14;
        for (int i = 0; i < node.length ; i++) {
            ans[i]=new Point3D(x,y,0);
            x--;
            y--;
        }
        for (int i = 0; i < node.length ; i++) {
            node[i].setLocation(ans[i]);
        }
        for (int i = 0; i < node.length ; i++) {
            assertEquals(node[i].getLocation(),ans[i]);
        }
    }

    @Test
    public void getWeight() {
        double ans=0;
        for(int i = 0; i < node.length ; i++) {
            assertEquals(ans, node[i].getWeight(),0.01);

        }
    }

    @Test
    public void setWeight() {
        double ans[]= {15.3,11,6.5,1.9,21,10};
        for (int i = 0; i < node.length ; i++) {
            node[i].setWeight(ans[i]);
        }
        for (int i = 0; i < node.length ; i++) {
            assertEquals(ans[i], node[i].getWeight(),0.01);
        }
    }

    @Test
    public void getInfo() {
        String ans="";
        for (int i = 0; i < node.length ; i++) {
            assertEquals(ans, node[i].getInfo());
        }
    }

    @Test
    public void setInfo() {
        String ans[] = {"f","e","d","c","b","a"};
        for (int i = 0; i < node.length ; i++) {
            node[i].setInfo(ans[i]);
        }
        for (int i = 0; i < node.length ; i++) {
            assertEquals(ans[i], node[i].getInfo());
        }
    }

    @Test
    public void getTag() {
        int ans=0;
        for (int i = 0; i < node.length ; i++) {
            assertEquals(ans, node[i].getTag());
        }
    }

    @Test
    public void setTag() {
        int ans[] = {2,3,4,5,6,7};
        for (int i = 0; i < node.length ; i++) {
            node[i].setTag(ans[i]);
        }
        for (int i = 0; i < node.length ; i++) {
            assertEquals(ans[i], node[i].getTag());
        }
    }
}
