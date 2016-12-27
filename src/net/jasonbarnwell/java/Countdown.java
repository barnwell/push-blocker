/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package net.jasonbarnwell.java;

import java.util.Timer;
import java.util.TimerTask;



/**
 *
 * @author Jason
 */

public class Countdown extends TimerTask {
   private long currentTime, originalTime;
   private Runnable concurrentMethod = null;  //will be run on every tick
   private Runnable completionMethod = null;  //will be run when time's up
   Timer timer = new Timer();
   
   public Countdown(long count, long interval) {
      this.currentTime = count;
      this.originalTime = count;
      timer.scheduleAtFixedRate(this, interval, interval); 
   }
   
   public Countdown(long count, long interval, Runnable concurrentMethod) {
      this.currentTime = count;
      this.originalTime = count;
      this.concurrentMethod = concurrentMethod;
      timer.scheduleAtFixedRate(this, interval, interval); 
   }
   
    public Countdown(long count, long interval, Runnable concurrentMethod, Runnable completionMethod) {
      this.currentTime = count;
      this.originalTime = count;
      this.concurrentMethod = concurrentMethod;
      this.completionMethod = completionMethod;
       
      timer.scheduleAtFixedRate(this, interval, interval); 
   }
   
   @Override
   public void run() {
      currentTime--;
      if(concurrentMethod!=null){
          concurrentMethod.run();
      }
      if (currentTime == 0) {
          stop();
          if (completionMethod!= null){
              completionMethod.run();
          }
        }
   }

   public void incrementTime(long t){
       currentTime += t;
   }
   
   public void decrementTime(long t){
       currentTime -= t;
   }
   
   public void setTime(long t){
       currentTime = t;
   }
   public long getTime(){
       return currentTime;
   }
   public void stop(){
       cancel();
       timer.cancel();
   }
   
   public void start(){
       run();
   }
   
   public void restart(){
       setTime(originalTime);
   }
   public void destroy(){
       timer = null;
       
   }

}