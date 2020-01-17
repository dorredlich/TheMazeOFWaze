package gameClient;
import java.awt.Label;

import javax.swing.JLabel;

import Server.game_service;
public class MyThreadClock extends Thread {

    static Thread time;
    public static void timeRun(game_service game) {
        time = new Thread(new Runnable() {

            @Override
            public void run() {
                while(game.isRunning()) {
                    System.out.println(game.timeToEnd()/1000);
                    try{
                        Thread.sleep(1000);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        time.start();
    }

    static Thread moveTime;
    public static void moveTime(game_service game) {
        moveTime= new Thread(new Runnable() {

            @Override
            public void run() {
                while (game.isRunning()) {
                    game.move();
                    try{
                        Thread.sleep(80);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        moveTime.start();
    }

}
