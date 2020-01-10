//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package gameClient;

import Server.Game_Server;
import Server.game_service;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import oop_dataStructure.OOP_DGraph;
import oop_dataStructure.oop_edge_data;
import oop_dataStructure.oop_graph;
import org.json.JSONException;
import org.json.JSONObject;

public class SimpleGameClient {
    public SimpleGameClient() {
    }

    public static void main(String[] a) {
        test1();
    }

    public static void test1() {
        int scenario_num = 2;
        game_service game = Game_Server.getServer(scenario_num);
        String g = game.getGraph();
        OOP_DGraph gg = new OOP_DGraph();
        gg.init(g);
        String info = game.toString();

        try {
            JSONObject line = new JSONObject(info);
            JSONObject ttt = line.getJSONObject("GameServer");
            int rs = ttt.getInt("robots");
            System.out.println(info);
            System.out.println(g);
            Iterator f_iter = game.getFruits().iterator();

            while(f_iter.hasNext()) {
                System.out.println((String)f_iter.next());
            }
            int src_node = 0;

            for(int a = 0; a < rs; ++a) {
                game.addRobot(src_node + a);
            }
        } catch (JSONException var11) {
            var11.printStackTrace();
        }

        game.startGame();

        while(game.isRunning()) {
            moveRobots(game, gg);
        }

        String results = game.toString();
        System.out.println("Game Over: " + results);
    }

    private static void moveRobots(game_service game, oop_graph gg) {
        List<String> log = game.move();
        if (log != null) {
            long t = game.timeToEnd();

            for(int i = 0; i < log.size(); ++i) {
                String robot_json = (String)log.get(i);

                try {
                    JSONObject line = new JSONObject(robot_json);
                    JSONObject ttt = line.getJSONObject("Robot");
                    int rid = ttt.getInt("id");
                    int src = ttt.getInt("src");
                    int dest = ttt.getInt("dest");
                    if (dest == -1) {
                        dest = nextNode(gg, src);
                        game.chooseNextEdge(rid, dest);
                        System.out.println("Turn to node: " + dest + "  time to end:" + t / 1000L);
                        System.out.println(ttt);
                    }
                } catch (JSONException var12) {
                    var12.printStackTrace();
                }
            }
        }

    }

    private static int nextNode(oop_graph g, int src) {
        int ans = -1;
        Collection<oop_edge_data> ee = g.getE(src);
        Iterator<oop_edge_data> itr = ee.iterator();
        int s = ee.size();
        int r = (int)(Math.random() * (double)s);

        for(int i = 0; i < r; ++i) {
            itr.next();
        }

         ans = itr.next().getDest();
        return ans;
    }
}
