package dataStructure;

import utils.Point3D;

public class Edge implements edge_data {
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

    public Edge(int src, int dest, double weight, String Information,int Tag){
        this.src = src;
        this.dest = dest;
        this.Information = Information;
        this.Tag = Tag;
        this.weight = weight;
    }

    public Edge(int src,int dest, double weight){
        this.src = src;
        this.dest = dest;
        this.weight = weight;
        this.Information = "";
        this.Tag = 0;
    }

    public Edge(Edge e){
        this.src = e.src;
        this.dest = e.dest;
        this.weight = e.weight;
        this.Information = e.Information;
        this.Tag = e.Tag;
    }

    @Override
    public int getSrc(){
        return this.src;
    }

    @Override
    public int getDest(){
        return this.dest;
    }

    @Override
    public double getWeight(){
        return this.weight;
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

    @Override
    public String toString(){
        return  "[" + this.getSrc() + "," + this.getDest() + "," + this.getWeight() + "]";
    }
}
