package elements;

import dataStructure.node_data;
import org.json.JSONObject;
import utils.Point3D;

public class Robot {
    int value;
    int id;
    int src;
    int dest;
    int speed;
    Point3D point;



    public Robot() {
        this.value =0;
        this.id = id;
        this.src = src;
        this.dest = dest;
        this.speed = speed;
        this.point = point;
    }

    public Robot(Point3D p,int value,int id,int src,int dest,int speed) {
        this.value =0;
        this.id =id;
        this.src = src;
        this.dest =dest;
        this.speed =speed;
        this.point = point;
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

}
