//package gameClient;
//import java.util.ArrayList;
//import java.util.Collection;
//
//import dataStructure.node_data;
//import org.json.JSONException;
//import org.json.JSONObject;
//
////import com.sun.corba.se.spi.activation.Server;
//
//import Server.Game_Server;
//import Server.game_service;
//import algorithms.Graph_Algo;
//import dataStructure.DGraph;
//import dataStructure.NodeData;
//import elements.Fruit;
//import elements.Robot;
//import utils.Point3D;
//
//public class AutoGame {
//
//    Graph_Algo graph=new Graph_Algo();
//    game_service game;
//    boolean isRun;
//    int map;
//    ArrayList<Fruit> fruits= new ArrayList<>();
//    ArrayList<Robot> robots=new ArrayList<>();
//
//    public ArrayList<Robot> initStartRobots(DGraph g,ArrayList<Robot> r,ArrayList<Fruit> f,int map){
//        graph.init(g);
//        fruits = f;
//        robots = r;
//        game=Game_Server.getServer(map);
//        int RobotsCount = 0;
//        try {
//            RobotsCount = NumOfRobots(map);
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        double minDis=Double.MAX_VALUE;
//
//        for (int i = 0; i < RobotsCount; i++) {
//            Collection<node_data> c = g.getV();
//            for (node_data node: c) {
//                for (int j = 0; j < fruits.size(); j++) {
//                    if(node.getLocation().distance2D(fruits.get(j).getLocation()) < minDis) {
//                        double x = node.getLocation().x();
//                        double y = node.getLocation().y();
//                        Point3D p = new Point3D(x,y);
//                        r.get(i).setLocation(p);
//                    }
//                }
//            }
//        }
//    return r;
//    }
//
//    public int NumOfRobots(int scenario) throws JSONException {
//        game_service temp=Game_Server.getServer(scenario);
//        JSONObject object = new JSONObject(temp.toString());
//        int num = object.getJSONObject("GameServer").getInt("robots");
//        return num;
//    }
//}
