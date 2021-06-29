import java.util.Random;

/**
 * Class WeatherSimulator is responsible for simulating the weather
 * of the simulation. There are three types of weather: snowstorm, 
 * fog and normal weather (when there is neither of the former types).
 * It uses a Weather object to keep track of the type of weather.
 *
 * @author Alexandra-Maria Anastase and Ioana-Daria Vasile
 * @version 2021.03.03
 */
public class WeatherSimulator
{
    protected static final Random rand = Randomizer.getRandom();

    // The probability of a snowstorm to happen
    private static final double SNOWSTORM_PROBABILITY = 0.05;
    // The probability of fog to happen
    private static final double FOG_PROBABILITY = 0.04;

    // The maximum duration of a snowstorm
    private static final int SNOWSTORM_MAX_STEPS = 10;
    // The maximum duration of fog
    private static final int FOG_MAX_STEPS = 9;

    // Variable that keeps track of the duration of a type of weather.
    private int steps;
    // Object that keeps track of the type of weather.
    private Weather weather;

    /**
     * Constructor for objects of class WeatherSimulator
     */
    public WeatherSimulator()
    {
        weather = new Weather();
    }

    /**
     * Resets the weather by setting the values of the weather types
     * to false and simulating a new type of weather.
     */
    public void resetWeather(){
        weather.setSnowstorm(false);
        weather.setFog(false);
        simulateWeather();
    }  

    /**
     * Decrements the number of steps the weather has left
     * for acting.
     */
    public void decrementSteps(){
        steps --;
        if(steps <= 0){
            resetWeather();           
        }
    }

    /**
     * Returns the updated weather object.
     * @return weather The weather object of the simulation
     */
    public Weather getWeather(){
        return weather;
    }

    /**
     * Simulates a new type of weather based on probability.
     */
    private void simulateWeather(){        
        if(rand.nextDouble() <= SNOWSTORM_PROBABILITY) {
            weather.setSnowstorm(true);
            steps = rand.nextInt(SNOWSTORM_MAX_STEPS);
        }
        else if(rand.nextDouble() <= FOG_PROBABILITY){
            weather.setFog(true);
            steps = rand.nextInt(FOG_MAX_STEPS);
        }        
    }
}
