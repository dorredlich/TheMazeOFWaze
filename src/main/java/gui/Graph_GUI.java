package gui;

import algorithms.Graph_Algo;
import dataStructure.*;
import utils.Point3D;
import utils.Range;


import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.*;

public class Graph_GUI extends JFrame implements ActionListener {
    private graph g = new DGraph();
    private Graph_Algo ga = new Graph_Algo();
    Range scaleR;
    private static final long serialVersionUID = 1L;


    public  Graph_GUI(graph g2) {
        this.g = g2;
        this.ga.init(g);
        initGUI();
    }

    private void initGUI() {
        this.setSize(100, 1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);

        // graph setting
        MenuBar menuBar = new MenuBar();
        Menu menu = new Menu("Menu");
        menuBar.add(menu);
        Menu graph_paint = new Menu("Graph commands");
        menuBar.add(graph_paint);
        this.setMenuBar(menuBar);

        MenuItem save = new MenuItem("Save graph");
        save.addActionListener(this);

        MenuItem load = new MenuItem("Load graph");
        load.addActionListener(this);

        menu.add(save);
        menu.add(load);

        MenuItem DrawGraph = new MenuItem("Draw graph");
        DrawGraph.addActionListener(this);
        MenuItem isConnected = new MenuItem("is Connected");
        isConnected.addActionListener(this);
        MenuItem shortestPathDist = new MenuItem("shortest Path Dist");
        shortestPathDist.addActionListener(this);
        MenuItem shortestPath = new MenuItem("shortest Path");
        shortestPath.addActionListener(this);
        MenuItem TSP = new MenuItem("TSP");
        TSP.addActionListener(this);
        graph_paint.add(DrawGraph);
        graph_paint.add(isConnected);
        graph_paint.add(shortestPathDist);
        graph_paint.add(shortestPath);
        graph_paint.add(TSP);
    }

    private void DrawVertex(Graphics g, Point3D min, Point3D max) {
        if(this.g != null){
            Collection<node_data> c = this.g.getV();
        for (node_data node: c) {//draw all the vertex in the window
                g.setColor(Color.BLACK);
                double x = node.getLocation().x();
                x = scale(x, min.x(), max.x(), 100, 650);
                double y = node.getLocation().y();
                y = scale(y, min.y(), max.y(), 100, 650);
                g.fillOval((int) x, (int) y, 14, 14);//for locating the vertex in the window
                g.drawString("" + node.getKey(), (int) (x + 10), (int) (y + 4));//for show the key of the vertex
            }
        }
    }


    public double find_max_x () {

        double ans=Double.MIN_VALUE;
        for (node_data node: g.getV()) {
            if (node.getLocation().x()>ans) {
                ans=node.getLocation().x();
            }
        }
        return ans;
    }

    public double find_min_x () {
        double ans=Double.MAX_VALUE;
        for (node_data node: g.getV() ) {
            if (node.getLocation().x()<ans) {
                ans=node.getLocation().x();
            }
        }
        return ans;
    }

    public double find_max_y () {
        double ans=Double.MIN_VALUE;
        for (node_data node: g.getV()) {
            if (node.getLocation().y()>ans) {
                ans=node.getLocation().y();
            }
        }
        return ans;
    }

    public double find_min_y () {
        double ans=Double.MAX_VALUE;
        for (node_data node: g.getV() ) {
            if (node.getLocation().y()<ans) {
                ans=node.getLocation().y();
            }
        }
        return ans;
    }

    /**
     * this function will paint the graph
     */
    public void paint(Graphics g) {
        super.paint(g);
        if (this.g != null) {
            Point3D p = new Point3D(find_min_x(),find_min_y());
            Point3D p2 = new Point3D(find_max_x(),find_max_y());
            DrawVertex(g,p,p2);
            Collection<node_data> c = this.g.getV();
            for (node_data node: c) {
                int x1 = (int)scale(node.getLocation().x(),find_min_x(),find_max_x(),100,650);
                int y1 = (int)scale(node.getLocation().y(),find_min_y(),find_max_y(),100,650);
                g.setColor(Color.YELLOW);
                g.fillOval(x1, y1, 10, 10);
                Collection<edge_data> Edge = this.g.getE(node.getKey());
                for (edge_data e : Edge) {
                    if (e.getTag() == 100) {
                        e.setTag(0);
                        g.setColor(Color.RED);
                    } else {
                        g.setColor(Color.BLUE);
                    }
                    node_data dest = this.g.getNode(e.getDest());
                    Point3D p3 = dest.getLocation();
                    if (p3 != null) {
                        int x2 = (int)scale(dest.getLocation().x(),find_min_x(),find_max_x(),100,650);
                        int y2 = (int)scale(dest.getLocation().y(),find_min_y(),find_max_y(),100,650);
                        g.drawLine(x1, y1, x2, y2);
                        if(this.g.getEdge(node.getKey(),dest.getKey()) == this.g.getEdge(dest.getKey(),node.getKey())) {
                            g.drawString(Double.toString(e.getWeight()), ((x1 + y1) / 4), ((x2 + y2) / 4));
                        }
                        g.drawString(Double.toString(e.getWeight()), ((x1 + x2) / 2), ((y1 + y2) / 2));
                        g.setColor(Color.MAGENTA);
                        int x_place = ((((((x1 + x2) / 2) + x2) / 2) + x2) / 2);
                        int y_place = ((((((y1 + y2) / 2) + y2) / 2) + y2) / 2);
                        g.fillOval(x_place, y_place, 5, 5);

                    }
                }
            }
        }
    }


    /**
     * this function will save a graph to file
     */
    private void Savegraph() {
        Graph_Algo ga = new Graph_Algo();
        ga.init(this.g);
        //		 parent component of the dialog
        JFrame parentFrame = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        int userSelection = fileChooser.showSaveDialog(parentFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String file = fileToSave.getAbsolutePath();
            ga.save(file);
            System.out.println("Save as file: " + fileToSave.getAbsolutePath());
        }

    }

    /**
     * this function will load a graph and paint it
     */
    private void Loadgraph() {
        Graph_Algo ga = new Graph_Algo();
        JFrame parentFrame = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to load");
        int userSelection = fileChooser.showOpenDialog(parentFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToLoad = fileChooser.getSelectedFile();
            String file = fileToLoad.getAbsolutePath();
            ga.init(file);
            repaint();
            System.out.println("Load from file: " + fileToLoad.getAbsolutePath());
        }
    }

    /**
     * the function will operate the shortest path dist and return the distance
     */
    private void shortestPathDist() {
        JFrame input = new JFrame();
        String src = JOptionPane.showInputDialog(null, "the key for src");
        String dest = JOptionPane.showInputDialog(null, "the key for dest");
        try {
            Graph_Algo ga = new Graph_Algo();
            ga.init(this.g);
            double ans = ga.shortestPathDist(Integer.parseInt(src), Integer.parseInt(dest));
            String s = Double.toString(ans);
            JOptionPane.showMessageDialog(input, "the shortest distance is: " + s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void isConnected() {
        JFrame input = new JFrame();
        Graph_Algo ga = new Graph_Algo();
        ga.init(this.g);
        boolean ans = ga.isConnected();
        if (ans == true) {
            JOptionPane.showMessageDialog(input, "this graph is connected");
        } else {
            JOptionPane.showMessageDialog(input, "the graph is not connected");
        }
    }

    /**
     * the function will operate the shortest path and return the shortest path to the user
     *
     */
    private void shortestPath() {
        JFrame input = new JFrame();
        String s = "";
        String src = JOptionPane.showInputDialog(
                null, "the key for src?");
        String dest = JOptionPane.showInputDialog(
                null, "the key for dest?");
        if (!src.equals(dest)) {
            try {
                Graph_Algo ga = new Graph_Algo();
                ga.init(this.g);
                List<node_data> shortPath = ga.shortestPath(Integer.parseInt(src), Integer.parseInt(dest));
                for (int i = 0; i + 1 < shortPath.size(); i++) {
                    this.g.getEdge(shortPath.get(i).getKey(), shortPath.get(i + 1).getKey()).setTag(100);
                    s += shortPath.get(i).getKey() + "--> ";
                }
                s += shortPath.get(shortPath.size() - 1).getKey();
                repaint();
                JOptionPane.showMessageDialog(input, "the shortest path is: " + s);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(input, "the shortest path is: null");
            }
        }
    }

    /**
     * the function will operate the tsp function, return the user the shortest path and paint
     * the path in red color
     */
    private void TSP() {
        JFrame input = new JFrame();
        Graph_Algo gr = new Graph_Algo();
        gr.init(this.g);
        ArrayList<Integer> targets = new ArrayList<>();
        String src = "";
        String s = "";
        do {
            src = JOptionPane.showInputDialog(null, "get a key if you want to Exit get in Exit");
            if ((!src.equals("Exit"))) {
                targets.add(Integer.parseInt(src));
            }
        } while (!src.equals("Exit"));
        List<node_data> shortPath =  gr.TSP(targets);
        if (shortPath != null) {
            for (int i = 0; i + 1 < shortPath.size(); i++) {
                this.g.getEdge(shortPath.get(i).getKey(), shortPath.get(i + 1).getKey()).setTag(100);
                s += shortPath.get(i).getKey() + "--> ";
            }
            s += shortPath.get(shortPath.size() - 1).getKey();
            repaint();
            JOptionPane.showMessageDialog(input, "the shortest path is: " + s);
        }
        if (shortPath == null) {
            JOptionPane.showMessageDialog(input, "the shortest path is: null ");
        }
    }

    /**
     *
     * @param data denote some data to be scaled
     * @param r_min the minimum of the range of your data
     * @param r_max the maximum of the range of your data
     * @param t_min the minimum of the range of your desired target scaling
     * @param t_max the maximum of the range of your desired target scaling
     * @return
     */
    public double scale(double data, double r_min, double r_max,
                        double t_min, double t_max)
    {

        double res = ((data - r_min) / (r_max-r_min)) * (t_max - t_min) + t_min;
        return res;
    }


    @Override
    public void actionPerformed(ActionEvent a) {
        String str = a.getActionCommand();

        switch (str) {
            case "Draw graph":
                repaint();
                break;
            case "Save graph":
                Savegraph();
                break;
            case "Load graph":
                Loadgraph();
                break;
            case "shortest Path Dist":
                shortestPathDist();
                break;
            case "is Connected":
                isConnected();
                break;
            case "shortest Path":
                shortestPath();
                break;
            case "TSP":
                TSP();
                break;
            default:
                break;
        }
    }

    public static void main(String[] args) {
        Point3D p = new Point3D(2,3,0);
        Point3D p2 = new Point3D(3,4,0);
        Point3D p3 = new Point3D(1,1,0);
        Point3D p4 = new Point3D(4,3,0);
        Point3D p5 = new Point3D(7,1,0);
        Point3D p6 = new Point3D(5,2,0);
        Point3D p7 = new Point3D(2,2,0);
        Point3D p8 = new Point3D(6,5,0);
        NodeData a1 = new NodeData(p);
        NodeData a2 = new NodeData(p2);
        NodeData a3 = new NodeData(p3);
        NodeData a4 = new NodeData(p4);
        NodeData a5 = new NodeData(p5);
        NodeData a6 = new NodeData(p6);
        NodeData a7 = new NodeData(p7);
        NodeData a8 = new NodeData(p8);
        DGraph d = new DGraph();
        d.addNode(a1);
        d.addNode(a2);
        d.addNode(a3);
        d.addNode(a4);
        d.addNode(a5);
        d.addNode(a6);
        d.addNode(a7);
        d.addNode(a8);
        d.connect(1,2,5);
        d.connect(1,5,2);
        d.connect(1,3,6);
        d.connect(1,6,5);
        d.connect(3,4,7);
        d.connect(2,8,8);
        d.connect(2,7,3);
        d.connect(5,1,5);
        d.connect(5,6,2);
        d.connect(6,1,3);
        d.connect(6,5,3);
        d.connect(6,7,3);
        d.connect(7,6,3);
        d.connect(2,3,40);
        d.connect(2,4,5);
        d.connect(4,6,7);
        d.connect(4,3,6);
        d.connect(8,3,6);
        Graph_GUI gg = new Graph_GUI(d);

    }
}

