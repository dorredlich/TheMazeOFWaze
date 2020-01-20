package gameClient;
import java.time.LocalTime;

import Server.game_service;
public class MyThreadClock extends Thread {

    static Thread time;
    public static void timeRun(game_service game) {
        time = new Thread(new Runnable() {

            @Override
            public void run() {
                while(game.isRunning()) {
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
}
