package dataStructure;

import utils.Point3D;

import java.io.Serializable;

public class NodeData implements node_data, Serializable {
    private int k;
    private double weight;
    private String Information;
    private int Tag;
    private Point3D location;
    private boolean visited;
    static private int KCount = 1;

    /**
     * constructor
     */
    public NodeData(){
        this.k = KCount++;
        this.k = 0;
        this.weight = 0;
        this.Information = null;
        this.location = null;
        this.Tag = 0;
        this.setVisited(false);
    }

    public NodeData(int k, double weight, int Tag, Point3D point, String Information){
        this.k = KCount++;
        this.k = k;
        this.Tag = Tag;
        this.location = new Point3D(point);
        this.Information = Information;
        this.weight = weight;
        this.setVisited(false);
    }

    public NodeData(int k ,Point3D location) {
        this.k = k;
        this.location = location;
        this.weight =0;
        this.Information ="";
        this.Tag = 0;
    }

    public NodeData(NodeData n){
        this.k = n.k;
        this.Tag = n.Tag;
        this.location = new Point3D(n.location);
        this.Information = n.Information;
        this.weight = n.weight;
    }

    public NodeData(Point3D point){
        this.k = KCount++;
        this.location = point;
        this.Information = "";
    }

    /**
     *
     * @return the key of the node
     */
    @Override
    public int getKey(){
        return this.k;
    }

    /**
     *
     * @return the point with x and y of the node
     */
    @Override
    public Point3D getLocation(){
        return this.location;
    }

    /**
     * set the point of the node
     * @param p - new new location  (position) of this node.
     */
    @Override
    public void setLocation(Point3D p){
        this.location = new Point3D(p);
    }

    /**
     *
     * @return the weight of the node
     */
    @Override
    public double getWeight(){
        return this.weight;
    }

    /**
     * set the weight of the node.
     * @param w - the new weight
     */
    @Override
    public void setWeight(double w){
        this.weight = w;
    }

    /**
     *
     * @return the information of the node
     */
    @Override
    public String getInfo(){
        return this.Information;
    }

    /**
     * set the information
     * @param s the new information
     */
    @Override
    public void setInfo(String s){
        this.Information = s;
    }

    /**
     *
     * @return temporal value of the node
     */
    @Override
    public int getTag(){
        return this.Tag;
    }

    /**
     *
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t){
        this.Tag = t;
    }

    /**
     *
     * @return true if we visit in past on the node
     */
    public boolean isVisited() {
        return visited;
    }

    /**
     *  mark the node to true that we finish to true that i will not pass in again
     * @param visited the node we set.
     */
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public String toString(){
        return "" + this.getKey();
    }
}
