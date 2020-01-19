package gameClient;
import java.awt.Label;
import java.time.LocalTime;

import javax.swing.JLabel;

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

    static Thread Kml_file;
    public static void moveKml(game_service game , KML_Logger KML) {
        Kml_file= new Thread(new Runnable() {

            @Override
            public void run() {
                while (game.isRunning()) {
                    try{
                        Thread.sleep(100);
                        String time = java.time.LocalDate.now()+"T"+java.time.LocalTime.now();
                        LocalTime end = java.time.LocalTime.now();
                        end = end.plusNanos(100*1000000);
                        String endTime = java.time.LocalDate.now()+"T"+end;
                        KML.setFruit(time , endTime);
                        KML.setRobot(time , endTime);

                    }
                    catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        });
        Kml_file.start();
    }

}
