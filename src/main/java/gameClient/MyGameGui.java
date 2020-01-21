package gameClient;

import Server.Game_Server;
import Server.game_service;
import dataStructure.DGraph;
import dataStructure.edge_data;
import dataStructure.node_data;
import elements.Fruit;
import elements.Robot;
import org.json.JSONObject;
import utils.Point3D;
import utils.StdDraw;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;


/**
 *  This class represents the MyGameGui for the client.
 *   Ex3 - Do edit this class.
 *  @author Dor Redlich
 */
public class MyGameGui  {
    game_service game;
    public  DGraph dg = new DGraph();
    Hashtable<Point3D, Fruit> fruits;
    Hashtable<Integer, Robot> robots;
    private static final long serialVersionUID = 6128157318970002904L;
    double x;
    double y;
    public int scenario;
    boolean isRobot = false;
    public KML_Loger KML;


    public MyGameGui(){
        this.game = Game_Server.getServer(this.scenario);
        this.dg =null;
        initGUI();
    }

    /**
     * chooseing thes scenario of the game
     */
    private void chooseScenario() {
        JFrame level = new JFrame();
        String scenario_num = JOptionPane.showInputDialog(level, "insert a level 0 - 23:");
        this.scenario = Integer.parseInt(scenario_num);
    }

    /**
     * This function is play the manual choice and send it to move manual
     */
    public  void PlayManual() {
        try {
                chooseScenario();
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
                for (String sRobot : game.getRobots()) {
                    Robot ro = new Robot();
                    ro.init(sRobot);
                    int id = ro.getId();
                    robots.put(id, ro);
                }
                initGUI();
                moveManual(game);

        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Play Automatic to choose kind of scenario and send to class AutoGame to start play.
     */
    public void Playautomatic() {

            chooseScenario();
            game_service game = Game_Server.getServer(scenario);
            AutoGame Auto= new AutoGame (this);
            Auto.AutoGame(game, scenario);
    }


    /**
     * help function to move the robot on the graph
     * @param game
     * @param gg
     */
    private void updateRobot(game_service game, DGraph gg) {
        robots.clear();
        for (String robo : game.getRobots()) {
            Robot r = new Robot();
            r.init(robo);
            int id = r.getId();
            robots.put(id, r);
        }
    }

    /**
     * help function to move the fruit if it eating
     * @param game
     * @param gg
     */
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
        this.x = x;
        this.y = y;
    }

    /**
     * This function moving the robots manualy by the user choice
     * all the time i will change the location of the robot it will print the node he moved.
     * @param game
     */
    public void moveManual(game_service game) {
        game.startGame();
        MyThreadClock.timeRun(game);
        try {
            List<String> log = game.move();
            Set <Integer> robotLocation = robots.keySet();
            Robot r = new Robot();
            while (game.isRunning()) {
                updateRobot(game, dg);
                upateFruits(game, dg);
                long t = game.timeToEnd();
                if (!isRobot) {
                    for (Integer roboL : robotLocation) {
                        r = robots.get(roboL);
                        Point3D p = r.getPoint3D();
                        double disX = Math.pow((p.x() - this.x), 2);
                        double disY = Math.pow((p.y() - this.y), 2);
                        if(Math.sqrt(disY + disX) <= 0.00025) {
                            isRobot = true;
                            this.x = 0;
                            this.y = 0;
                            break;
                        }
                    }
                }
                else {
                    node_data node = this.dg.getNode(r.getSrc());
                    Collection <edge_data> c = this.dg.getE(node.getKey());
                    for (edge_data edges : c) {
                        node_data node_edge = dg.getNode(edges.getDest());
                        Point3D po = node_edge.getLocation();
                        double disX = Math.pow((po.x()-this.x), 2);
                        double disY = Math.pow((po.y()-this.y), 2);
                        if(Math.sqrt(disY + disX) <= 0.00025) {
                            r.setPoint3D(po);
                            r.setDest(node_edge.getKey());
                            game.chooseNextEdge(r.getId(), node_edge.getKey());
                            isRobot =false;
                            this.x=0;
                            this.y=0;
                            String robot_json = log.get(r.getId());
                            JSONObject line = new JSONObject(robot_json);
                            System.out.println("Turn to node: "+ node_edge.getKey()+"  time to end:"+(t/1000));
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



    /**
     * this function will paint the graph and also the fruit and the robots on the graph.
     */
    public void paint() {
        StdDraw.clear();
        if(this.dg !=null) {
            Collection <node_data> c = this.dg.getV();
            for (node_data node : c) {
                Point3D p = node.getLocation();
                StdDraw.setPenColor(Color.RED);
                StdDraw.filledCircle(p.x(), p.y(), 0.0001); //nodes in orange
                StdDraw.setPenColor(Color.BLACK);

                StdDraw.text(p.x(), p.y()+(p.y() * 0.000004) , (Integer.toString(node.getKey())));
                Collection<edge_data> c2 = this.dg.getE(node.getKey());
                for (edge_data Edge : c2) {
                    if (Edge.getTag() == 100) {
                        Edge.setTag(0);
                        StdDraw.setPenColor(Color.YELLOW);
                    }
                    else {
                        StdDraw.setPenColor(Color.DARK_GRAY);
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
     * fruit location.
     * if the type is -1 it will print banana.
     * if the type is 1 it will print apple.
     */
    public void paintFruit() {
        if (this.fruits != null) {
            Set<Point3D> set = fruits.keySet();
            for (Point3D p3 : set) {
                Fruit fru = fruits.get(p3);
                if (fru.getType() == -1) {
                    StdDraw.setPenColor(Color.RED);
                    StdDraw.picture(p3.x(), p3.y(), "data/banana.png", 0.0006, 0.0004);
                } else {
                    StdDraw.setPenColor(Color.CYAN);
                    StdDraw.picture(p3.x(), p3.y(), "data/apple.png", 0.0006, 0.0004);
                    if (fru != null) {
                        if (fru.getType() == -1) {
                            KML_Loger.createPlacemark(fru.getLocation().x(), fru.getLocation().y(), "banana");
                        } else {
                            KML_Loger.createPlacemark(fru.getLocation().x(), fru.getLocation().y(), "apple");
                        }
                    }
                }
            }
        }
    }

    /**
     * paint robots
     */
    public void paintRobot() {
        if (this.robots != null) {
            Set<Integer> set = robots.keySet();
            for (Integer robot : set) {
                try {
                    Robot robo = robots.get(robot);
                    Point3D p = robo.getPoint3D();
                    StdDraw.setPenColor(Color.GREEN);
                    StdDraw.picture(p.x(), p.y(), "data/robot.png", 0.0008, 0.0004);
                    if (p != null)
                        KML_Loger.createPlacemark(p.x(), p.y(), "ski");
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    }


    /**
     * init the gui window and the kml with the graph.
     */
    public void initGUI() {
        StdDraw.setCanvasSize(800, 700);
        StdDraw.enableDoubleBuffering();
        double X_min = Integer.MAX_VALUE;
        double X_max = Integer.MIN_VALUE;
        double Y_min = Integer.MAX_VALUE;
        double Y_max = Integer.MIN_VALUE;

        if (dg != null) {
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
