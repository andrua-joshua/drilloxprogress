package com.example.drilloxprocessco;

import javafx.scene.media.MediaPlayer;

import java.util.Calendar;
import java.util.TimerTask;

public class Alarm extends TimerTask {
    public static MediaPlayer player;
    public static int Hours;
    public static int Min;

    @Override
    public void run(){
        boolean time = false;
        while(!time){
            if(Calendar.getInstance().get(Calendar.HOUR)==Hours){
                if (Calendar.getInstance().get(Calendar.MINUTE)==Min) time = true;
            }
        }
        player.play();

    }
    public static void stop(){
        player.stop();
    }
}
