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


    @Override
    public int getKey(){
        return this.k;
    }

    @Override
    public Point3D getLocation(){
        return this.location;
    }

    @Override
    public void setLocation(Point3D p){
        this.location = new Point3D(p);
    }

    @Override
    public double getWeight(){
        return this.weight;
    }

    @Override
    public void setWeight(double w){
        this.weight = w;
    }

    @Override
    public String getInfo(){
        return this.Information;
    }

    @Override
    public void setInfo(String s){
        this.Information = s;
    }

    @Override
    public int getTag(){
        return this.Tag;
    }

    @Override
    public void setTag(int t){
        this.Tag = t;
    }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public String toString(){
        return "" + this.getKey();
    }
}
