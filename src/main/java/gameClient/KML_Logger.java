package gameClient;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.List;

import org.json.JSONObject;

import dataStructure.edge_data;
import dataStructure.graph;
import dataStructure.node_data;
import Server.game_service;
import de.micromata.opengis.kml.v_2_2_0.Document;
import de.micromata.opengis.kml.v_2_2_0.Folder;
import de.micromata.opengis.kml.v_2_2_0.Icon;
import de.micromata.opengis.kml.v_2_2_0.Kml;
import de.micromata.opengis.kml.v_2_2_0.Placemark;
import de.micromata.opengis.kml.v_2_2_0.Style;
import utils.Point3D;

public class KML_Logger {
    graph g;
    game_service gameS;
    Document doc;
    Kml myKml;

    /**
     * init the graph
     * @param graph
     */
    public KML_Logger(graph graph) {
        this.g = graph;
    }


    /**
     * init the game
     * @param game
     */
    public void setGameS(game_service game) {
        this.gameS = game;
    }

    /**
     * init the kml file to google map  by pass all the nodes, edge_data .
     */
    public void kml_Graph() {
        this.myKml = new Kml();
        this.doc = myKml.createAndSetDocument().withName("new KML").withOpen(true);
        Folder myFloder = doc.createAndAddFolder();
        myFloder.withName("KML new folder").withOpen(true);
        Icon icon = new Icon();
        icon.withHref("http://maps.google.com/mapfiles/kml/paddle/purple-blank.png");
        Style style = doc.createAndAddStyle();
        style.withId("place Node").createAndSetIconStyle().withIcon(icon).withScale(1);
        Collection<node_data> Nodes = g.getV();
        for (node_data node : Nodes) {
            Placemark place_mark = doc.createAndAddPlacemark();
            place_mark.withName("" + node.getKey()).withStyleUrl("#place Node");
            place_mark.createAndSetPoint().addToCoordinates(node.getLocation().x(), node.getLocation().y());

            Style Style_Edge = doc.createAndAddStyle();
            Style_Edge.withId("Edge").createAndSetLineStyle().withColor("ff43b3ff").setWidth(2.5);
            Point3D po = node.getLocation();
            Collection<edge_data> Edegs = g.getE(node.getKey());
            for (edge_data edge : Edegs) {
                Placemark place_Edge = doc.createAndAddPlacemark().withStyleUrl("#Edge");
                Point3D p = g.getNode(edge.getDest()).getLocation();
                place_Edge.createAndSetLineString().withTessellate(true).addToCoordinates(po.x() , po.y()).addToCoordinates(p.x(), p.y());
            }
        }
    }

    /**
     * this function save the file of kml that we create.
     * @param fileName
     */
    public void save (String fileName ) {
        try {
            myKml.marshal(new File(fileName));
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * this function set the fruit by pass all the fruits and
     * each fruit will place at the kml file.
     * @param time
     * @param endTime
     */
    public void setFruit(String time , String endTime) {
        Icon boyIcon = new Icon();
        boyIcon.withHref("http://maps.google.com/mapfiles/kml/shapes/man.png"); // change
        Style style = doc.createAndAddStyle();
        style.withId("place banana").createAndSetIconStyle().withIcon(boyIcon).withScale(1.5);
        Icon grilIcon = new Icon();
        grilIcon.withHref("http://maps.google.com/mapfiles/kml/shapes/woman.png"); // change
        //style
        Style style_girl = doc.createAndAddStyle();
        style_girl.withId("place apple").createAndSetIconStyle().withIcon(grilIcon).withScale(1.5);
        List <String> Fruits = gameS.getFruits();
        for (String string : Fruits) {
            try {
                double x=0,y=0,z=0,counter=0;
                String k = "";
                JSONObject obj = new JSONObject(string);
                JSONObject Fruits2 =obj.getJSONObject("Fruit");
                String point=(String) Fruits2.get("pos");
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
                Point3D p = new Point3D(x,y,z);
                int typeFruit =  Fruits2.getInt("type");
                Placemark place_Fruit = doc.createAndAddPlacemark();
                if(typeFruit ==-1 ) {
                    place_Fruit.setStyleUrl("#place banana");
                }
                else {
                    place_Fruit.setStyleUrl("#place apple");
                }
                place_Fruit.createAndSetPoint().addToCoordinates(x , y);
                place_Fruit.createAndSetTimeSpan().withBegin(time).withEnd(endTime);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * this function set the robot by pass all the robots and
     * each robot will place at the kml file.
     * @param time
     * @param endTime
     */
    public void setRobot (String time , String endTime) {
        //icon for fruits
        Icon robotIcon = new Icon();
        robotIcon.withHref("http://maps.google.com/mapfiles/kml/shapes/cabs.png"); // change
        //style
        Style style = doc.createAndAddStyle();
        style.withId("place robot").createAndSetIconStyle().withIcon(robotIcon).withScale(1.5);
        List<String> Robots = gameS.getRobots();
        for (String string : Robots) {
            try {
                double x=0,y=0,z=0,counter=0;
                String k = "";
                JSONObject obj = new JSONObject(string);
                JSONObject Robot =obj.getJSONObject("Robot");
                String point=(String) Robot.get("pos");
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
                Point3D POINT= new Point3D(x,y,z);
                int ID =  Robot.getInt("id");
                Placemark place_Robot = doc.createAndAddPlacemark();
                place_Robot.setStyleUrl("#place robot");
                place_Robot.createAndSetPoint().addToCoordinates(x , y);
                place_Robot.createAndSetTimeSpan().withBegin(time).withEnd(endTime);
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
