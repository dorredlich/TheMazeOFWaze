package dataStructure;

import utils.Point3D;

import java.io.Serializable;

public class Edge implements edge_data, Serializable {
    private int src;
    private int dest;
    private double weight;
    private String Information;
    private int Tag;

    public Edge() {
        this.src = 0;
        this.dest = 0;
        this.weight = 0;
        this.Information = null;
        this.Tag = 0;
    }

    /**
     * constructor of the edge
     * @param src the start of the edge
     * @param dest the end of the edge
     * @param weight of the edge
     * @param Information inside the edge
     * @param Tag the  value for temporal marking an edge
     */
    public Edge(int src, int dest, double weight, String Information,int Tag){
        this.src = src;
        this.dest = dest;
        this.Information = Information;
        this.Tag = Tag;
        this.weight = weight;
    }

    /**
     * other constructor
     * @param src the start
     * @param dest the end
     * @param weight of the weight
     */
    public Edge(int src,int dest, double weight){
        if(weight < 0) throw new RuntimeException("Weight of edge cant be negative.");
        this.src = src;
        this.dest = dest;
        this.weight = weight;
        this.Information = "";
        this.Tag = 0;
    }

    /**
     * copy constructor
     * @param e we are init
     */
    public Edge(Edge e){
        this.src = e.src;
        this.dest = e.dest;
        this.weight = e.weight;
        this.Information = e.Information;
        this.Tag = e.Tag;
    }

    /**
     *
     * @return the src of the edge
     */
    @Override
    public int getSrc(){
        return this.src;
    }

    /**
     *
     * @return the dest of the edge
     */
    @Override
    public int getDest(){
        return this.dest;
    }

    /**
     *
     * @return the weight of the edge
     */
    @Override
    public double getWeight(){
        return this.weight;
    }

    /**
     *
     * @return information of the edge
     */
    @Override
    public String getInfo(){
        return this.Information;
    }

    /**
     * set the information of the edge
     * @param s we set
     */
    @Override
    public void setInfo(String s){
        this.Information = s;
    }

    /**
     *
     * @return the value for temporal marking an edge
     */
    @Override
    public int getTag(){
        return this.Tag;
    }

    /**
     * set the value of temporal marking an edge.
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t){
        this.Tag = t;
    }

    /**
     *
     * @return the string of src' dest and the weight of the edge.
     */
    @Override
    public String toString(){
        return  "[" + this.getSrc() + "," + this.getDest() + "," + this.getWeight() + "]";
    }
}
