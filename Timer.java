 
/**
 * Class Timer is responsile for keeping track of the time
 * in the simulation. A day has 24 steps and is separated
 * into day and night. Time of day changes depending
 * on the number of steps in the simulation.
 *
 * @author Alexandra-Maria Anastase and Ioana-Daria Vasile
 * @version 2021.03.03
 */
public class Timer
{
    // Number of steps that have passed
    private int time;
    // Time of the day. True if day, false if night.
    private boolean isDay;

    /**
     * Constructor for objects of class Timer
     * @param isDay Status of daytime
     */
    public Timer(boolean isDay)
    {
        time = 0;
        this.isDay = isDay;
    }
    
    /**
     * Increments time by one and changes the time of day
     * when necessary.
     */
    public void incrementTime(){
        time ++;
        if(time > 12){
            changeTimeOfDay();
            time = 0;
        }        
    }
    
    /** Returns true if it is daytime and false if it is night.
     * @return isDay Status of daytime.
     */
    public boolean isDay(){
        return isDay;
    }
    
    /**
     * Changes the time of day to night if it is day
     * and to day if it is night.
     */
    private void changeTimeOfDay(){
        isDay = !isDay;
    }        
}
