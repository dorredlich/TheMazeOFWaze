package Tests;

import dataStructure.Edge;
import dataStructure.edge_data;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class EdgeTest {
    static edge_data edge[]= new edge_data[6];

    @Before
    public void BeforeEach(){
        edge[0]=new Edge(1,6,6,"a",1);
        edge[1]=new Edge(2,7,9,"b",2);
        edge[2]=new Edge(3,8,56,"c",3);
        edge[3]=new Edge(4,9,17,"d",4);
        edge[4]=new Edge(5,10,26,"e",5);
        edge[5]=new Edge(6,11,2,"f",6);
    }
    @Test
    public void getSrc() {
        int ans[]={1,2,3,4,5,6,};
        for (int i = 0; i < edge.length ; i++) {
            assertEquals(ans[i], edge[i].getSrc());
        }
    }


    @Test
    public void getTag() {
        int ans[]= {1,2,3,4,5,6};
        for (int i = 0; i < edge.length ; i++) {
            for (int j = 0; j < edge.length; j++) {
                assertEquals(ans[i], edge[i].getTag());
            }
        }
    }

    @Test
    public void setTag() {
        int ans[]= {7,8,9,10,11,12};
        for (int i = 0; i < edge.length ; i++) {
            edge[i].setTag(ans[i]);
        }
        for (int i = 0; i < edge.length ; i++) {
            assertEquals(ans[i], edge[i].getTag());
        }
    }

    @Test
    public void getDest() {
        int ans[]= {6,7,8,9,10,11};
        for (int i = 0; i < edge.length ; i++) {
            assertEquals(ans[i], edge[i].getDest());
        }
    }

    @Test
    public void setInfo() {
        String ans[] = {"f","e","d","c","b","a"};
        for (int i = 0; i < edge.length ; i++) {
            edge[i].setInfo(ans[i]);
        }
        for (int i = 0; i < edge.length ; i++) {
            assertEquals(ans[i], edge[i].getInfo());
        }
    }

    @Test
    public void getInfo() {
        String ans[] = {"a","b","c","d","e","f"};
        for (int i = 0; i < edge.length ; i++) {
            assertEquals(ans[i], edge[i].getInfo());
        }
    }

    @Test
    public void getWeight() {
        double ans[] =  {6,9,56,17,26,2};
        for (int i = 0; i < edge.length ; i++) {
            assertEquals(ans[i], edge[i].getWeight(),0.01);
        }
    }



}
