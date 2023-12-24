package dev.skydynamic.fakedebug;

import dev.skydynamic.fakedebug.config.Config;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

 public class FpsRandomTimer {
     public static void runTimer() {
         Timer timer = new Timer();
         final int MAX = Config.INSTANCE.getMaxFps(), MIN = Config.INSTANCE.getMinFps();
         TimerTask timerTask = new TimerTask() {
             public void run() {
               Random random = new Random();
               FpsRandom.currentFps = random.nextInt(MAX - MIN + 1) + MIN;
             }
         };
         timer.schedule(timerTask, 1000L, 1000L);
     }
 }
