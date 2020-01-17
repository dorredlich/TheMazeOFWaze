package gameClient;

import Server.Game_Server;
import Server.game_service;
import algorithms.Graph_Algo;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.node_data;
import elements.Fruit;
import elements.Robot;
import org.json.JSONException;
import org.json.JSONObject;
import utils.Point3D;
import utils.StdDraw;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;


public class MyGameGui implements ActionListener  {
    public  DGraph dg = new DGraph();
    Hashtable<Point3D, Fruit> fruits;
    Hashtable<Integer, Robot> robots;
    private static final long serialVersionUID = 6128157318970002904L;
    LinkedList<Point3D> points = new LinkedList<Point3D>();
    double X_min = Integer.MAX_VALUE;
    double X_max = Integer.MIN_VALUE;
    double Y_min = Integer.MAX_VALUE;
    double Y_max = Integer.MIN_VALUE;
    double x;
    double y;
    boolean isRobot = false;
   // KML_Logger KML;

    /**
     * This function is a default constructor
     */
    public MyGameGui(){
        this.dg =null;
        initGUI();
    }

    public MyGameGui(DGraph g) {
        this.dg = g;
        initGUI();
    }

    public MyGameGui(DGraph g , Fruit f , Robot r) {
        this.dg =g;
        initGUI();
    }



    /**
     * This function is play the manual choice and send it to move manual
     */
    public  void PlayManual() {
        try {
            JFrame input = new JFrame();
            String s ="";
            s = JOptionPane.showInputDialog(
                    null, "Please enter a Scenario number between 0-23");
            int sen=Integer.parseInt(s);
            if(sen<0 || sen>23) {
                JOptionPane.showMessageDialog(input, "The number that you entered isn't a Scenario number " );
            }
            else {
                game_service game = Game_Server.getServer(sen); // you have [0,23] games
                String g = game.getGraph();
                DGraph dg2 = new DGraph();
                dg2.init(g);
                this.dg = dg2;
                String info = game.toString();
                // fruit
                if (fruits == null ) {
                    fruits= new Hashtable<>();
                }
                for (String  fruit : game.getFruits()) {
                    Fruit f = new Fruit();
                    f.init(fruit);
                    Point3D p_f	= f.getPoint3D();
                    f.findFruitPlace(dg2 , f);
                    fruits.put(p_f, f);
                }
                //robot
                Robot r = new Robot();
                JSONObject obj = new JSONObject(info);
                JSONObject Robot =obj.getJSONObject("GameServer");
                int amountRobot = Robot.getInt("robots");
                Collection<node_data> node = dg.getV();
                int size = node.size();
                int rnd = 0;
                if (robots == null ) {
                    robots = new Hashtable<Integer, Robot>();
                }
                int counter = 0;
                Set <Point3D> allFruits = fruits.keySet();
                for (Point3D Point : allFruits) {
                    if (counter < fruits.size()) {
                        if (counter >= amountRobot ) {
                            break;
                        }
                        Fruit placeFruit = fruits.get(Point);
                        game.addRobot(placeFruit.getSrc());
                        counter++;
                    }
                    else {
                        while (counter < amountRobot) {
                            rnd = (int) (Math.random()*size);
                            if (dg.getNode(rnd) != null) {
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
                    robots.put(id, ro);
                }
                initGUI();
                //startGameNow(game, dg , sen);
                moveManual(game);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * This function is playing the automatic choice, and sent it to moveautomatic
     */
    public void Playautomatic() {
        try {
            JFrame input = new JFrame();
            String s = "";
            s = JOptionPane.showInputDialog(
                    null, "Please enter a Scenario number between 0-23");
            int scenario = Integer.parseInt(s);
            if(scenario < 0 || scenario > 23) {
                JOptionPane.showMessageDialog(input, "The number that you entered isn't a Scenario number " );
            }
            else {
                game_service game = Game_Server.getServer(scenario); // you have [0,23] games
                String g = game.getGraph();
                DGraph dg2 = new DGraph();
                dg2.init(g);
                this.dg = dg2;
                String info = game.toString();
                // fruit
                if (fruits == null ) {
                    fruits= new Hashtable<>();
                }
                for (String  fruit : game.getFruits()) {
                    Fruit f = new Fruit();
                    f.init(fruit);
                    Point3D p_f	=f.getPoint3D();
                    f.findFruitPlace(dg2 , f);
                    fruits.put(p_f, f);
                }

                //robot
                Robot r = new Robot();
                JSONObject obj = new JSONObject(info);
                JSONObject Robot =obj.getJSONObject("GameServer");
                int amountRobot = Robot.getInt("robots");
                Collection<node_data> node = dg.getV();
                int size = node.size();
                int rnd = 0;
                if (robots == null ) {
                    robots = new Hashtable<Integer, Robot>();
                }
                int counter=0;
                Set <Point3D> allFruits = fruits.keySet();
                for (Point3D Point : allFruits) {
                    if (counter < fruits.size()) {
                        if (counter >= amountRobot ) {
                            break;
                        }
                        Fruit placeFruit = fruits.get(Point);
                        r.setSrc(placeFruit.getSrc());
                        game.addRobot(placeFruit.getSrc());
                        counter++;
                    }
                    else {
                        while (counter < amountRobot) {
                            rnd = (int) (Math.random()*size);
                            if (dg.getNode(rnd) != null) {
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
                    robots.put(id, ro);
                }
                initGUI();
                startGameNow(game, dg2 , scenario);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * This function gets game and graph start the game and while the game run
     * she send the parameters to move robots
     * @param game
     * @param gg
     */
    public void startGameNow(game_service game ,DGraph gg , int sen) {
        JFrame input = new JFrame();
        game.startGame();
//        KML.setGame(game);
//        MyThreadClock.moveKml(game, KML);
        MyThreadClock.moveTime(game);
        MyThreadClock.timeRun(game);
        while(game.isRunning()) {
            smartMove(game , gg);
        }
        try {
            //KML.save(sen + ".kml");
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
     * This function is the smart move- she made a path for every robot by the shortestpath and shortspathdist
     *
     * @param game
     * @param dg
     */
    private void smartMove(game_service game, DGraph dg) {
        List<node_data> ListGr = new ArrayList<node_data>();
        Graph_Algo ga = new Graph_Algo();
        ga.init(dg);
        try {
            String info = game.toString();
            JSONObject obj = new JSONObject(info);
            JSONObject Robot =obj.getJSONObject("GameServer");
            int amountRobot = Robot.getInt("robots");
            int counter=0;
            while ( counter < amountRobot) {
                Robot ro = robots.get(counter);
                ListGr = SetPath ( game,  dg, ro,  ga);
                MoveRobot( game,  dg,  ro,  ga, ListGr);
                counter++;
            }
        }
        catch (JSONException e) {e.printStackTrace();}
        // robot move
        updateRobot(game , dg);
        // fruit move if its eaten
        upateFruits( game, dg);
        paint();
    }


    private List<node_data> SetPath (game_service game, DGraph gg, Robot ro, Graph_Algo graphA) {
        double min =Integer.MAX_VALUE , temp=0;
        List<node_data> list = new ArrayList<>();
        Set <Point3D> allFruits = fruits.keySet();
        Fruit temp_f = new Fruit();
        for (Point3D point3d : allFruits) {
            Fruit fo = fruits.get(point3d);
            temp = graphA.shortestPathDist(ro.getSrc(), fo.getSrc());
            if ( temp < min && !fo.getVisited()) {
                temp_f = fo;
                min = temp;
                fo.setVisited(true);//mark as visited
                list = graphA.shortestPath(ro.getSrc(), fo.getSrc());
                list.add(gg.getNode(fo.getDest()));
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

    private void updateRobot(game_service game, DGraph gg) {
        robots.clear();
        for (String robo : game.getRobots()) {
            Robot r=new Robot();
            r.init(robo);
            int id = r.getId();
            robots.put(id, r);
        }
    }

    private void upateFruits(game_service game, DGraph gg) {
        fruits.clear();
        for (String  fruit : game.getFruits()) {
            Fruit f = new Fruit();
            f.init(fruit);
            Point3D p_f	=f.getPoint3D();
            f.setVisited(false);
            f.findFruitPlace(gg , f);
            fruits.put(p_f, f);
        }
    }

    /**
     * This function gets the point that the user click on her in the screen
     * @param x
     * @param y
     */
    public void setXY(double x , double y) {
        this.x=x;
        this.y=y;
    }

    /**
     * This function moving the robots manualy by the user choice
     * while the game is running she will change the robots ocation by the location the user entered
     * and se will print the changes on the screen
     * @param game
     */

    public void moveManual(game_service game) {
        game.startGame();
        MyThreadClock.timeRun(game);
        try {
            List<String> log = game.move();
            Set <Integer> roboLoc = robots.keySet();
            Robot r = new Robot();
            while (game.isRunning()) {
                //re build the robots
                updateRobot(game, dg);
                // fruit move if its eaten
                upateFruits(game, dg);
                long t = game.timeToEnd();

                if (!isRobot) {
                    for (Integer roboL : roboLoc) {
                        r = robots.get(roboL);
                        Point3D p = r.getPoint3D();
                        double disX=Math.pow((p.x()-this.x), 2);
                        double disY=Math.pow((p.y()-this.y), 2);
                        if(Math.sqrt(disY+disX)<=0.00025) {
                            isRobot =true;
                            this.x=0;
                            this.y=0;
                            break;
                        }
                    }
                }
                else {
                    node_data node = dg.getNode(r.getSrc());
                    Collection <edge_data> edges = dg.getE(node.getKey());
                    for (edge_data edge_data : edges) {
                        node_data node_edge = dg.getNode(edge_data.getDest());
                        Point3D po = node_edge.getLocation();
                        double disX=Math.pow((po.x()-this.x), 2);
                        double disY=Math.pow((po.y()-this.y), 2);
                        if(Math.sqrt(disY+disX)<=0.00025) {
                            r.setPoint3D(po);
                            r.setDest(node_edge.getKey());
                            game.chooseNextEdge(r.getId(), node_edge.getKey());
                            isRobot =false;
                            this.x=0;
                            this.y=0;
                            String robot_json = log.get(r.getId());
                            JSONObject line = new JSONObject(robot_json);
                            System.out.println("Turn to node: "+node_edge.getKey()+"  time to end:"+(t/1000));
                            break;
                        }
                    }
                }
                game.move();
                paint();
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            JFrame input = new JFrame();
            String info = game.toString();
            JSONObject obj = new JSONObject(info);
            JSONObject GameServer =obj.getJSONObject("GameServer");
            int grade = GameServer.getInt("grade");
            JOptionPane.showMessageDialog(input, "the game is finished! \n"+ "your score is: " + grade);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drawFunctions() {
        StdDraw.setCanvasSize(1000, 1000);
        int X_min = Integer.MIN_VALUE;
        int X_max = Integer.MAX_VALUE;
        int Y_min = Integer.MIN_VALUE;
        int Y_max = Integer.MAX_VALUE;

        // rescale the coordinate system
        StdDraw.setXscale(  X_min, X_max);
        StdDraw.setYscale(Y_min, Y_max);
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        menuBar.add(menu);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }


    /**
     * this function will paint the graph
     */
    public void paint() {
        StdDraw.clear();
        if(this.dg !=null) {
            Collection <node_data> c = this.dg.getV();
            for (node_data node : c) {
                Point3D p = node.getLocation();
                StdDraw.setPenColor(Color.ORANGE);
                StdDraw.filledCircle(p.x(), p.y(), 0.0001); //nodes in orange
                StdDraw.setPenColor(Color.BLACK);

                StdDraw.text(p.x(), p.y()+(p.y() * 0.000004) , (Integer.toString(node.getKey())));
                Collection<edge_data> c2 = this.dg.getE(node.getKey());
                for (edge_data Edge : c2) {
                    if (Edge.getTag() ==100) {
                        Edge.setTag(0);
                        StdDraw.setPenColor(Color.RED);
                    }
                    else {
                        StdDraw.setPenColor(Color.BLUE);
                    }
                    node_data dest = dg.getNode(Edge.getDest());
                    Point3D p2 = dest.getLocation();
                    if (p2 != null) {
                        StdDraw.line(p.x(), p.y(), p2.x(), p2.y());
                        StdDraw.setPenColor(Color.MAGENTA);
                        double x_place =((((((p.x()+p2.x())/2)+p2.x())/2)+p2.x())/2);
                        double y_place = ((((((p.y()+p2.y())/2)+p2.y())/2)+p2.y())/2);
                        StdDraw.filledCircle(x_place, y_place, 0.0001);
                        StdDraw.setPenColor(Color.BLUE);
                        //cut the number to only 1 digit after the point
                        String toShort = Double.toString(Edge.getWeight());
                        int i=0;
                        while(i < toShort.length()) {
                            if(toShort.charAt(i)=='.') {
                                toShort = toShort.substring(0, i+2);
                            }
                            i++;
                        }
                        StdDraw.text(x_place, y_place-(y_place * 0.000004),toShort );
                    }
                }
            }
            paintFruit();
            paintRobot();
            StdDraw.show();
        }
    }

    /**
     * This function will paint the fruit on the screen by pass over the
     * fruit location .
     */
    public void paintFruit() {
        if ( this.fruits != null ) {
            Set <Point3D> set = fruits.keySet();
            for (Point3D p3 : set) {
                Fruit fru = fruits.get(p3);
                if (fru.getType() == -1) {
                    StdDraw.setPenColor(Color.RED);
                    StdDraw.picture(p3.x(), p3.y(), "data/banana.png", 0.0006, 0.0004);
                    //					StdDrawGame.filledCircle(p3.x(), p3.y(), 0.00015);
                }
                else {
                    StdDraw.setPenColor(Color.CYAN);
                    StdDraw.picture(p3.x(), p3.y(), "data/apple.png", 0.0006, 0.0004);
                    //					StdDrawGame.filledCircle(p3.x(), p3.y(), 0.00015);
                }
            }
        }
    }

    /**
     * This function paint the robots by
     */
    public void paintRobot() {
        if(this.robots!=null) {
            Set <Integer> set = robots.keySet();
            for (Integer robot : set) {
                Robot robo = robots.get(robot);
                Point3D p = robo.getPoint3D();
                StdDraw.setPenColor(Color.GREEN);
                StdDraw.picture(p.x(), p.y(), "data/robot.png", 0.0008, 0.0004);
                //				StdDrawGame.picture(x, Y_max, filename, scaledWidth, scaledHeight);
                //				StdDrawGame.filledCircle(p.x(), p.y(), 0.00025);
            }

        }
    }

    public void initGUI() {
        StdDraw.setCanvasSize(800, 600);
        StdDraw.enableDoubleBuffering();
        double X_min = Integer.MAX_VALUE;
        double X_max = Integer.MIN_VALUE;
        double Y_min = Integer.MAX_VALUE;
        double Y_max = Integer.MIN_VALUE;

        // rescale the coordinate system
        if (dg != null) {
//            KML = new KML_Logger(dg);
//            KML.kml_Graph();
            Collection<node_data> nodes = dg.getV();
            for(node_data node: nodes) {
                if(node.getLocation().x() > X_max) {
                    X_max = (node.getLocation().x());
                }
                if(node.getLocation().x() < X_min) {
                    X_min = (node.getLocation().x());
                }
                if(node.getLocation().y() > Y_max) {
                    Y_max = (node.getLocation().y());
                }
                if(node.getLocation().y() < Y_min) {
                    Y_min = (node.getLocation().y());
                }

            }
        }
        StdDraw.setXscale(X_min, X_max);
        StdDraw.setYscale(Y_min, Y_max);
        StdDraw.setGuiGraph(this);
        paint();
    }

    public static void main(String[] args) {
        MyGameGui gg = new MyGameGui();
    }
}
