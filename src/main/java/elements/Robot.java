package elements;

import dataStructure.node_data;
import org.json.JSONObject;
import utils.Point3D;

import java.util.List;

public class Robot {
    int value;
    int id;
    int src;
    int dest;
    int speed;
    Point3D point;
    List<node_data> way;
    boolean isFinish;



    public Robot() {
        this.value =0;
        this.id = id;
        this.src = src;
        this.dest = dest;
        this.speed = speed;
        this.point = point;
        this.isFinish = true;
    }

    public Robot(Point3D p,int value,int id,int src,int dest,int speed) {
        this.value =value;
        this.id =id;
        this.src = src;
        this.dest =dest;
        this.speed =speed;
        this.point = p;
    }


    /**
     * This function gets the type, value and point and entered them to the robot
     * @param t - represent by -1 from low to hight and 1 from hight to low
     * @param v
     * @param p - point of the location
     */
    public Robot (int t , int v , Point3D p) {
        this.value =v;
        this.point =p;
    }


    public int getId() { return this.id; }

    public void setId(int id) { this.id = id; }

    public int getSpeed () { return this.speed; }

    public void setSpeed(int SPEED) { this.speed =SPEED; }

    public Point3D getPoint3D () { return this.point; }

    public void setPoint3D (Point3D p) { this.point =p; }

    public int getValue () { return this.value; }

    public int getSrc() { return this.src; }

    public void setSrc(int Src) { this.src = Src; }

    public int getDest() { return this.dest; }

    public void setDest(int Dest) { this.dest = Dest; }

    public boolean isFinish() { return this.isFinish; }

    public void setFinish(boolean finish) { this.isFinish = finish;}

    public List<node_data> getWay() { return this.way;}

    public void setWay(List<node_data> way) {this.way = way;}

    /**
     * This function gets string s and init from her the robot all the information she needs,
     by reading from the json.
     * @param s
     */
    public void init (String s) {
        try {
            double x=0,y=0,z=0,counter=0;
            String k = "";
            JSONObject obj = new JSONObject(s);
            JSONObject Robots =obj.getJSONObject("Robot");
            String point=(String) Robots.get("pos");
            for (int j = 0; j < point.length(); j++) {
                if (point.charAt(j) != ',') {
                    k+=point.charAt(j);
                    if (counter==2 && j == point.length()-1) {
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
            this.point = new Point3D(x,y,z);
            this.value = Robots.getInt("value");
            this.id =  Robots.getInt("id");
            this.src = Robots.getInt("src");
            this.dest = Robots.getInt("dest");
            this.speed = Robots.getInt("speed");
        }
        catch(Exception e) {
            e.printStackTrace();
        }

    }

    public boolean IsDone(node_data src) {
        boolean IsDone=false;
        double x = distans(this.getPoint3D().x(), this.getPoint3D().y(), src.getLocation().x(), src.getLocation().y());
        if(x<=0.000001) {
            IsDone=true;
        }
        return IsDone;
    }

    private double distans(double x, double y, double x2, double y2) {
        double x_dis = Math.pow((x-x2), 2);
        double y_dis = Math.pow((y-y2), 2);
        double dis = Math.sqrt((x_dis + y_dis));
        return dis;

    }

}
