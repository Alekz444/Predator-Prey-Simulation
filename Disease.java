import java.util.Random;

/**
 * Class Disease is responsible for storing the information about the 
 * disease present in the simulation and its status.
 *
 * @author Alexandra-Maria Anastase and Ioana-Daria Vasile
 * @version 2021.03.03
 */
public class Disease
{
    private static final Random rand = Randomizer.getRandom();

    // The probability of the disease to appear
    private static final double DISEASE_PROBABILITY = 0.02;
    // The probability of the disease to spread
    private static final double SPREADING_PROBABILITY = 0.02;
    // The duration of the sickness of an animal
    private static final int DISEASE_DURATION = 3;

    // Status that sets the disease as present or not
    private boolean status;

    /**
     * Constructor for objects of class DiseaseGenerator
     */
    public Disease()
    {
        status = false;
    }

    /**
     * Returns the spreading probability of the disease 
     * @return SPREADING_PROBABILITY The spreading probability
     * of the disease
     */
    public static double getSpreadingProbability(){
        return SPREADING_PROBABILITY;
    }

    /**
     * Returns the disease duration
     * @return DISEASE_DURATION The disease duration
     */
    public static int getDiseaseDuration(){
        return DISEASE_DURATION;
    }

    /**
     * Simulates the disease (decides whether the disease is present)
     */
    public void simulateDisease(){
        if(rand.nextDouble() <= DISEASE_PROBABILITY) {
            status = true;            
        }
    }

    /**
     * Returns true if the disease is present and false otherwise
     * @return status The disease status
     */
    public boolean getStatus(){
        return status;
    }

    /**
     * Sets the statue of the disease.
     * @param value The value that the status of the disease 
     * should be set to
     */
    public void setStatus(boolean value){
        status = value;
    }    
}
