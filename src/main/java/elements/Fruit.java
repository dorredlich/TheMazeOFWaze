package elements;

import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import org.json.JSONObject;
import utils.Point3D;

import java.util.Collection;

public class Fruit {
    static double EPSILON = 0.0000001;
    graph g;
    edge_data edge;
    int type;
    int value;
    Point3D location;
    int dest;
    int src;
    boolean isVisit;

    /**
     *This function is the default counstrctor
     */
    public Fruit () {
        this.g = g;
        this.edge = edge;
        this.type = type;
        this.location = location;
        this.value = value;
        this.src = src;
        this.dest = dest;
        this.isVisit = isVisit;
    }

    /**
     * This function is cunstrctor
     * @param t - the type of the fruit (1 or -1)
     * @param v - represent the key value
     * @param p - represent the location f p
     * @param dg - this represent the graph
     */
    public Fruit (int t , int v , Point3D p ,  graph dg) {
        this.g = dg;
        this.edge = edge;
        this.value =v;
        this.type = t;
        this.location = new Point3D(p);
        this.src = src;
        this.dest = dest;
        this.isVisit = isVisit;
    }

    public Point3D getPoint3D () { return this.location; }

    public int getValue () { return this.value; }

    public int getType() { return this.type; }

    public int getSrc () { return this.src; }

    public void setSrc(int Src) { this.src = Src; }

    public int getDest() { return this.dest; }

    public void setDest(int Dest) { this.dest = Dest; }

    public edge_data getEdge() { return this.edge; }

    public void setEdge(edge_data Edge) { this.edge = Edge; }

    public Point3D getLocation() { return this.location;}

    public boolean getVisited() { return this.isVisit; }

    public void setVisited(boolean Visited) { this.isVisit = Visited; }

    /**
     * This function init a fruit from the string s by reading from the json
     * the function reading from the json and init it to fruit struct to the
     * correct field.
     * @param s
     */
    public void init(String s) {
        try {
            double x=0,y=0,z=0,counter=0;
            String k = "";
            JSONObject obj = new JSONObject(s);
            JSONObject Fruits2 =obj.getJSONObject("Fruit");
            String location = (String) Fruits2.get("pos");
            for (int j = 0; j < location.length(); j++) {
                if (location.charAt(j) != ',') {
                    k += location.charAt(j);
                    if (counter == 2 && j == location.length()-1) {
                        z= Double.parseDouble(k);
                        counter=0;
                        k="";
                    }
                }
                else {
                    if (counter== 0) {
                        x= Double.parseDouble(k);
                        counter++;
                        k="";
                    }
                    else if (counter==1) {
                        y= Double.parseDouble(k);
                        counter++;
                        k="";
                    }
                }
            }
            Point3D p = new Point3D(x,y,z);
            this.value = Fruits2.getInt("value");
            this.type =  Fruits2.getInt("type");
            this.location = new Point3D(p);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This function set the dest, src and edge
     * the function pass all the nodes and check for every node if the dest between the 2 nodes is
     * equals to the dest between the first node to the fruit and form the fruit to the next node by the help of the distance function.
     * if it does she will set the edge of the fruit to be the edge between them also the src and dest.
     * @param gg the graph to check
     * @param f the fruit to find his place
     */
    public void findFruitPlace(graph gg , Fruit f) {
        Point3D p = f.getPoint3D();
        Collection <node_data> Nodes = gg.getV();
        for (node_data node_data : Nodes) {
            Collection<edge_data> neighbors=gg.getE(node_data.getKey());
            for (edge_data edge_data : neighbors) {
                double dis1 = distance(node_data.getLocation().x() , node_data.getLocation().y(), p.x() , p.y());
                int dest = edge_data.getDest();
                double dis2 = distance(gg.getNode(dest).getLocation().x() , gg.getNode(dest).getLocation().y() ,p.x() , p.y());
                double dis_All = distance(node_data.getLocation().x() , node_data.getLocation().y() ,gg.getNode(dest).getLocation().x() , gg.getNode(dest).getLocation().y() );
                if (Math.abs((dis1+dis2) - dis_All)<= EPSILON) {
                    f.setDest(dest);
                    f.setSrc(node_data.getKey());
                    f.setEdge(edge_data);
                }
            }
        }
    }


    /**
     * help funtion to fint the distance between to points.
     * @param x
     * @param y
     * @param x2
     * @param y2
     * @return
     */
    private double distance(double x, double y, double x2, double y2) {
        double x_dis = Math.pow((x-x2), 2);
        double y_dis = Math.pow((y-y2), 2);
        double dis = Math.sqrt((x_dis + y_dis));
        return dis;
    }
}

