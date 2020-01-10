package gui;

import algorithms.Graph_Algo;
import dataStructure.*;
import utils.Point3D;


import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.List;
import java.util.*;

public class Graph_GUI extends JFrame implements ActionListener {

    private graph g;
    private static final long serialVersionUID = 6128157318970002904L;
    LinkedList<Point3D> points = new LinkedList<Point3D>();

    public void Gui_Graph() {
        this.g = null;
        initGUI();
    }

    public void Gui_Graph(graph g2) {
        this.g = g2;
        initGUI();
    }

    private void initGUI() {
        this.setSize(1000, 10000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        // menu up
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

        // graph up
        MenuItem Drawgraph = new MenuItem("Draw graph");
        Drawgraph.addActionListener(this);
        MenuItem isConnected = new MenuItem("is Connected");
        isConnected.addActionListener(this);
        MenuItem shortestPathDist = new MenuItem("shortest Path Dist");
        shortestPathDist.addActionListener(this);
        MenuItem shortestPath = new MenuItem("shortest Path");
        shortestPath.addActionListener(this);
        MenuItem TSP = new MenuItem("TSP");
        TSP.addActionListener(this);
        graph_paint.add(Drawgraph);
        graph_paint.add(isConnected);
        graph_paint.add(shortestPathDist);
        graph_paint.add(shortestPath);
        graph_paint.add(TSP);
    }

    /**
     * this function will paint the graph
     */
    public void paint(Graphics g) {
        super.paint(g);


        if (this.g != null) {
            Collection<node_data> Nodes = this.g.getV();
            for (node_data node_data : Nodes) {
                Point3D p = node_data.getLocation();
                g.setColor(Color.ORANGE);
                g.fillOval(p.ix(), p.iy(), 10, 10);
                g.setColor(Color.BLACK);
                g.drawString(Integer.toString(node_data.getKey()), p.ix() + 1, p.iy() - 2);
                Collection<edge_data> Edge = this.g.getE(node_data.getKey());
                for (edge_data edge_data : Edge) {
                    if (edge_data.getTag() == 100) {
                        edge_data.setTag(0);
                        g.setColor(Color.RED);
                    } else {
                        g.setColor(Color.BLUE);
                    }
                    node_data dest = this.g.getNode(edge_data.getDest());
                    Point3D p2 = dest.getLocation();
                    if (p2 != null) {
                        g.drawLine(p.ix(), p.iy(),
                                p2.ix(), p2.iy());
                        g.drawString(Double.toString(edge_data.getWeight()), (p.ix() + p2.ix()) / 2, (p.iy() + p2.iy()) / 2);
                        g.setColor(Color.MAGENTA);
                        int x_place = ((((((p.ix() + p2.ix()) / 2) + p2.ix()) / 2) + p2.ix()) / 2);
                        int y_place = ((((((p.iy() + p2.iy()) / 2) + p2.iy()) / 2) + p2.iy()) / 2);
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
        Graph_Algo gg = new Graph_Algo();
        gg.init(this.g);
        //		 parent component of the dialog
        JFrame parentFrame = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");
        int userSelection = fileChooser.showSaveDialog(parentFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String file = fileToSave.getAbsolutePath();
            gg.save(file);
            System.out.println("Save as file: " + fileToSave.getAbsolutePath());
        }

    }

    /**
     * this function will load a graph and paint it
     */
    private void Loadgraph() {
        Graph_Algo gg = new Graph_Algo();
        JFrame parentFrame = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to load");
        int userSelection = fileChooser.showOpenDialog(parentFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToLoad = fileChooser.getSelectedFile();
            String file = fileToLoad.getAbsolutePath();
            gg.init(file);
            repaint();
            System.out.println("Load from file: " + fileToLoad.getAbsolutePath());
        }
    }

    /**
     * the function will operate the shortest path dist and return the distance
     */
    private void shortestPathDist() {
        JFrame input = new JFrame();
        String src = JOptionPane.showInputDialog(
                null, "what is the key for src?");
        String dest = JOptionPane.showInputDialog(
                null, "what is the key for dest?");
        try {
            Graph_Algo gg = new Graph_Algo();
            gg.init(this.g);
            double ans = gg.shortestPathDist(Integer.parseInt(src), Integer.parseInt(dest));
            String s = Double.toString(ans);
            JOptionPane.showMessageDialog(input, "the shortest distance is: " + s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * the function will operate the shortest path and return the shortest path to the user
     * and paint the path in red color
     */
    private void shortestPath() {
        JFrame input = new JFrame();
        String s = "";
        String src = JOptionPane.showInputDialog(
                null, "what is the key for src?");
        String dest = JOptionPane.showInputDialog(
                null, "what is the key for dest?");
        if (!src.equals(dest)) {
            try {
                Graph_Algo gg = new Graph_Algo();
                gg.init(this.g);
                ArrayList<node_data> shortPath = new ArrayList<node_data>();
                shortPath = (ArrayList<node_data>) gg.shortestPath(Integer.parseInt(src), Integer.parseInt(dest));
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

//    private void TSP() {
//        JFrame input = new JFrame();
//        Graph_Algo gr = new Graph_Algo();
//        gr.init(this.g);
//        ArrayList<Integer> targets = new ArrayList<Integer>();
//        String src = "";
//        String s = "";
//        do {
//            src = JOptionPane.showInputDialog(
//                    null, "get a key if you want to Exit get in Exit");
//            if ((!src.equals("Exit"))) {
//                targets.add(Integer.parseInt(src));
//            }
//        } while (!src.equals("Exit"));
//        ArrayList<node_data> shortPath = new ArrayList<node_data>();
//        shortPath = (ArrayList<node_data>) gr.TSP(targets);
//        if (shortPath != null) {
//            for (int i = 0; i + 1 < shortPath.size(); i++) {
//                this.g.getEdge(shortPath.get(i).getKey(), shortPath.get(i + 1).getKey()).setTag(100);
//                s += shortPath.get(i).getKey() + "--> ";
//            }
//            s += shortPath.get(shortPath.size() - 1).getKey();
//            repaint();
//            JOptionPane.showMessageDialog(input, "the shortest path is: " + s);
//        }
//        if (shortPath == null) {
//            JOptionPane.showMessageDialog(input, "the shortest path is: null ");
//        }
//    }

    private void isConnected() {
        JFrame input = new JFrame();
        Graph_Algo gr = new Graph_Algo();
        gr.init(this.g);
        boolean ans = gr.isConnected();
        if (ans == true) {
            JOptionPane.showMessageDialog(input, "this graph is connected");
        } else {
            JOptionPane.showMessageDialog(input, "the graph is not connected");
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();

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
            case "is Connected":
                isConnected();
                break;
            case "shortest Path Dist":
                shortestPathDist();
                break;
            case "shortest Path":
                shortestPath();
                break;
//            case "TSP":
//                TSP();
//                break;
            default:
                break;

        }
    }
}

