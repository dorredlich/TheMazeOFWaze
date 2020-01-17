package elements;

import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import org.json.JSONObject;
import utils.Point3D;

import java.util.Collection;

public class Fruit {
    static double EPSILON = 0.0000001;
    DGraph graph;
    edge_data edge;
    int type;
    int value;
    Point3D location;
    int dest;
    int src;
    boolean isVisid;

    /**
     *This function is the default counstrctor
     */
    public Fruit () {
        this.graph=graph;
        this.edge = edge;
        this.type = type;
        this.location = location;
        this.value = value;
        this.src = src;
        this.dest = dest;
        this.isVisid = isVisid;
    }

    /**
     * This function is cunstrctor
     * @param t - the type of the fruit (1 or -1)
     * @param v - represent the key value
     * @param p - represent the location f p
     * @param dg - this represent the graph
     */
    public Fruit (int t , int v , Point3D p ,  DGraph dg) {
        this.graph=dg;
        this.edge = edge;
        this.value =v;
        this.type = t;
        this.location = new Point3D(p);
        this.src = src;
        this.dest = dest;
        this.isVisid = isVisid;
    }

    public Point3D getPoint3D () { return this.location; }

    public int getValue () { return this.value; }

    public int getType() { return this.type; }

    public int getSrc () { return this.src; }

    public void setSrc(int Src) { this.src =Src; }

    public int getDest() { return this.dest; }

    public void setDest(int Dest) { this.dest = Dest; }

    public edge_data getEdge() { return this.edge; }

    public void setEdge(edge_data Edge) { this.edge = Edge; }

    public Point3D getLocation() { return this.location;}

    public boolean getVisited() { return this.isVisid; }

    public void setVisited(boolean Visited) { this.isVisid =Visited; }

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
            String point=(String) Fruits2.get("pos");
            for (int j = 0; j < point.length(); j++) {
                if (point.charAt(j) != ',') {
                    k+=point.charAt(j);
                    if (counter == 2 && j == point.length()-1) {
                        z= Double.parseDouble(k);
                        counter=0;
                        k="";
                    }
                }
                else {
                    if (counter==0) {
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
            this.location =new Point3D(p);
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * this function return which node the robot should move, to the closest fruit.
     * @param g the graph we working on
     * @param fruit the fruit that we want to check.
     * @return the node id we need go to.
     */
    public edge_data findFruitPlace(graph g, Fruit fruit) {
        Collection<node_data> gNodes = g.getV();
        for(node_data node: gNodes){
            Collection<edge_data> edges =g.getE(node.getKey());
            if(edges!=null){
                for (edge_data edge:edges) {
                    if(isOnEdge(g,edge,fruit)){
                        System.out.println("edge is:" + edge);
                        return edge;
                    }
                }
            }
        }
        return null;
    }

    /**
     * this function check if the fruit on specific edge.
     * @param g the graph we are workin on
     * @param edge the edge we want to check if the fruit on it.
     * @param f the fruit
     * @return yes if the fruit on the edge false if not.
     */
    private boolean isOnEdge(dataStructure.graph g, edge_data edge, Fruit f){
        node_data src = g.getNode(edge.getSrc());
        node_data dst = g.getNode(edge.getDest());
        double first = Math.sqrt(Math.pow(src.getLocation().x()-f.location.x(),2)+Math.pow(src.getLocation().y()-f.location.y(),2));
        double second = Math.sqrt(Math.pow(dst.getLocation().x()-f.location.x(),2)+Math.pow(dst.getLocation().y()-f.location.y(),2));
        double third = Math.sqrt(Math.pow(src.getLocation().x()-dst.getLocation().x(),2)+Math.pow(src.getLocation().y()-dst.getLocation().y(),2));
        if (first + second > third -EPSILON && first+second<third+EPSILON) return true;
        return false;
    }


    private double distance(double x, double y, double x2, double y2) {
        double x_dis = Math.pow((x-x2), 2);
        double y_dis = Math.pow((y-y2), 2);
        double dis = Math.sqrt((x_dis + y_dis));
        return dis;
    }
}

