import java.util.Random;

/**
 * Class Weather is responsible for storing the current 
 * weather of the simulation (snowstorm, fog or normal).
 *
 * @author Alexandra-Maria Anastase and Ioana-Daria Vasile
 * @version 2021.03.03
 */
public class Weather
{       
    // Whether there is an ongoing snowstorm
    private boolean snowstorm;
    // Whether there is ongoing fog
    private boolean fog;

    /**
     * Constructor for objects of class Weather
     */
    public Weather()
    {
        snowstorm = false;
        fog = false;
    }

    /**
     * Returns true if the weather type is snowstorm and false otherwise.
     * @return snowstorm The status of snowstorm
     * 
     */
    public boolean isSnowstorm(){
        return snowstorm;
    } 

    /**
     * Returns true if the weather type is fog and false otherwise.
     * @return fog Status of fog
     * 
     */
    public boolean isFog(){
        return fog;
    } 

    /**
     * Sets the status of the snowstorm to the status of the parameter.
     * @param status Status the snowstorm should be set to
     */
    public void setSnowstorm(boolean status){
        snowstorm = status;
    }     

    /**
     * Sets the status of the fog to the status of the parameter.
     * @param status Status the fog should be set to
     */
    public void setFog(boolean status){
        fog = status;
    }     
}
