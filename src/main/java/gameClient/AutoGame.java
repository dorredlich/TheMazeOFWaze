package gameClient;
import java.util.*;

import dataStructure.node_data;
import org.json.JSONException;
import org.json.JSONObject;

//import com.sun.corba.se.spi.activation.Server;

import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import elements.Fruit;
import elements.Robot;
import utils.Point3D;

import javax.swing.*;

public class AutoGame {
    MyGameGui myGame;

    public AutoGame(MyGameGui gui){
        this.myGame = gui;
    }

    /**
     * This function is playing the automatic choice, and sent it to move automatic
     */
    public void AutoGame(game_service game, int senario) {
        String g = game.getGraph();
        DGraph gg = new DGraph();
        gg.init(g);
        myGame.dg = gg;
        String info = game.toString();
        if (myGame.fruits == null ) {
            myGame.fruits= new Hashtable<>();
        }
        for (String  fruit : game.getFruits()) {
            Fruit f = new Fruit();
            f.init(fruit);
            Point3D p_f	= f.getPoint3D();
            f.findFruitPlace(gg , f);
            myGame.fruits.put(p_f, f);
        }
        Robot r=new Robot();
        JSONObject obj = null;
        try {
            obj = new JSONObject(info);
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
        JSONObject Robot = null;
        try {
            Robot = obj.getJSONObject("GameServer");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        int amountRobot = 0;
        try {
            amountRobot = Robot.getInt("robots");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Collection<node_data> node = myGame.dg.getV();
        int size = node.size();
        int rnd = 0;
        if (myGame.robots == null ) {
            myGame.robots = new Hashtable<>();
        }
        int counter=0;
        Set <Point3D> allFruits = myGame.fruits.keySet();
        for (Point3D Point : allFruits) {
            if (counter < myGame.fruits.size()) {
                if (counter >= amountRobot ) {
                    break;
                }
                Fruit placeFruit = myGame.fruits.get(Point);
                r.setSrc(placeFruit.getSrc());
                game.addRobot(placeFruit.getSrc());
                counter++;
            }
            else {
                while (counter < amountRobot) {
                    rnd = (int) (Math.random() * size);
                    if (myGame.dg.getNode(rnd) != null) {
                        game.addRobot(rnd);
                        counter++;
                    }
                }
            }
        }
        for (String robo : game.getRobots()) {
            Robot ro = new Robot();
            ro.init(robo);
            int id = ro.getId();
            myGame.robots.put(id, ro);
        }
        myGame.initGUI();
        startGame(game, gg , senario);
    }


    /**
     * This function gets game and graph start the game and while the game run
     * she send the parameters to move robots
     * @param game
     * @param gg
     */
    public void startGame(game_service game , DGraph gg , int sen) {
        JFrame input = new JFrame();
        game.startGame();
        MyThreadClock.timeRun(game);
        Long timeB = game.timeToEnd();
        while(game.isRunning()) {
            if(timeB-game.timeToEnd() > 70)
            {
                game.move();
                timeB = game.timeToEnd();
            }
            playSmart(game , gg);
        }
        try {
            String info = game.toString();
            System.out.println(info);
            JSONObject obj = new JSONObject(info);
            JSONObject GameServer =obj.getJSONObject("GameServer");
            int grade = GameServer.getInt("grade");
            JOptionPane.showMessageDialog(input, "the game is finished! \n"+ "your score is: " + grade);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * This function is by play smart- she made a path for every robot by the help of shortestpath and shortspathdist
     * @param game
     * @param dg
     */
    private void playSmart(game_service game, DGraph dg) {
        List<node_data> ListGr;
        Graph_Algo ga = new Graph_Algo(dg);
        try {
            String info = game.toString();
            JSONObject obj = new JSONObject(info);
            JSONObject Robot = obj.getJSONObject("GameServer");
            int amountRobot = Robot.getInt("robots");
            int counter = 0;
            while ( counter < amountRobot) {
                Robot ro = myGame.robots.get(counter);
                ListGr = SetPath( game,  dg, ro,  ga);
                MoveRobot( game,  dg,  ro,  ga, ListGr);
                counter++;
            }
        }
        catch (JSONException e) {e.printStackTrace();}
        updateRobot(game , dg);
        updateFruits( game, dg);
        myGame.paint();
    }


    /**
     *  This function set the path for the robot by pass all the fruit and check which
     * 	fruit in the closest fruit by the shortestpathdist (graph_algo) after this the function
     * 	will set the closest fruit as visited so the other robots won't go there
     * @param game
     * @param gg
     * @param ro the robot to check the shortestPath to the fruit.
     * @param graphA
     * @return
     */
    private List<node_data> SetPath (game_service game, dataStructure.graph gg, Robot ro, Graph_Algo graphA) {
        double min =Double.POSITIVE_INFINITY , temp = 0;
        List<node_data> list = new ArrayList<>();
        Set <Point3D> allFruits = myGame.fruits.keySet();
        Fruit temp_f = new Fruit();
        for (Point3D point3d : allFruits) {
            Fruit fo = myGame.fruits.get(point3d);
            try {
                if (ro.getSrc() == fo.getDest())
                    continue;
                temp = graphA.shortestPathDist(ro.getSrc(), fo.getDest());
                if (temp < min && !fo.getVisited()) {
                    temp_f = fo;
                    min = temp;
                    fo.setVisited(true);
                    list = graphA.shortestPath(ro.getSrc(), fo.getDest());
                    list.add(gg.getNode(fo.getSrc()));
                }
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    /**
     * this function will move the robots by the path she gets
     * @param game
     * @param gg
     * @param ro
     * @param graphA
     * @param ListGr
     */
    private void MoveRobot(game_service game, DGraph gg, Robot ro, Graph_Algo graphA, List<node_data> ListGr) {
        Fruit temp_f = new Fruit();
        temp_f.setVisited(true);
        if (ListGr.size() != 0 ) {
            ListGr.remove(0);
        }
        for (int j = 0; j < ListGr.size(); j++) {
            node_data temp_node = ListGr.get(j);
            int destGo = temp_node.getKey();
            Point3D po = temp_node.getLocation();
            ro.setPoint3D(po);
            ro.setSrc( temp_node.getKey());
            game.chooseNextEdge(ro.getId(), destGo);
        }
    }

    /**
     * help function to move the robot on the graph
     * @param game
     * @param gg
     */
    private void updateRobot(game_service game, DGraph gg) {
        myGame.robots.clear();
        for (String robo : game.getRobots()) {
            Robot r = new Robot();
            r.init(robo);
            int id = r.getId();
            myGame.robots.put(id, r);
        }
    }

    /**
     * help function to move the fruit if it eating
     * @param game
     * @param gg
     */
    private void updateFruits(game_service game, DGraph gg) {
        myGame.fruits.clear();
        for (String  fruit : game.getFruits()) {
            Fruit f = new Fruit();
            f.init(fruit);
            Point3D p_f	=f.getPoint3D();
            f.setVisited(false);
            f.findFruitPlace(gg , f);
            myGame.fruits.put(p_f, f);
        }
    }
}
