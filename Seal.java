import java.util.List;
import java.util.Iterator;

/**
 * A seal eats penguins and fish, can move on land and 
 * water, can get sick, can not see prey in the fog.
 * They also sleep at night.
 *
 * @author Alexandra-Maria Anastase and Ioana-Daria Vasile
 * @version 2021.03.03
 */
public class Seal extends Animal
{
    // Characteristics shared by all seals (class variables).

    // The age at which a seal can start to breed.
    private static final int BREEDING_AGE = 10;
    // The age to which a seal can live.
    private static final int MAX_AGE = 50;
    // The likelihood of a seal breeding.
    private static final double BREEDING_PROBABILITY = 0.065;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    // The maximum food level of a seal.
    private static final int MAX_FOOD_LEVEL = 20;
    // The food value of the seal.
    private static final int FOOD_VALUE = 10;

    // Individual characteristics (instance fields).

    // Whether the seal can see or not.
    private boolean canSee;

    /**
     * Create a seal. A seal can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the seal will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Seal(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location);        
    }

    /**
     * This is what the seal does most of the time: it hunts for
     * fish and penguins. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param newSeals A list to return newly born seals.
     * @timer The timer object of the simulation
     * @weather The weather object of the simulation
     */
    public void act(List<Actor> newSeals, Timer timer, Weather weather)
    {
        incrementAge();
        canSee = !weather.isFog();

        if(isAlive()){
            actDisease(); 
        }

        if(!timer.isDay()){
            return;
        }

        incrementHunger();

        if(!isAlive()) {
            return;
        }

        giveBirth(newSeals); 

        // Move towards a source of food if found.
        Location newLocation = null;
        if(canSee){
            newLocation = findFood();
        }

        if(newLocation == null) { 
            // No food found - try to move to a free location.
            newLocation = getField().freeAdjacentLocation(getLocation());
        }

        // See if it was possible to move.
        if(newLocation != null) {
            setLocation(newLocation);
        }
        else {
            // Overcrowding.
            setDead();                    
        }
    }

    /**
     * Check whether or not this seal is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newSeals A list to return newly born seals.
     */
    private void giveBirth(List<Actor> newSeals)
    {
        // New seals are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = reproduce();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Seal young = new Seal(false, field, loc);
            newSeals.add(young);
        }
    }

    /**
     * @return BREEDING_AGE The age at which a seal can start to breed
     */
    @Override
    public int getBreedingAge()
    {
        return BREEDING_AGE;
    }

    /**
     * @return MAX_AGE The maximum age of a seal
     */
    @Override
    public int getMaxAge()
    {
        return MAX_AGE;
    }

    /**
     * @return MAX_LITTER_SIZE The maximum litter size of a seal
     * at a time
     */
    @Override
    public int getMaxOffspringSize()
    {
        return MAX_LITTER_SIZE;
    }

    /**
     * @return BREEDING_PROBABILITY The breeding probability of 
     * a seal
     */
    @Override
    public double getReproductionProbability()
    {
        return BREEDING_PROBABILITY;
    }

    /**
     * @return FOOD_VALUE The food value of the seal
     */
    @Override
    public int getFoodValue()
    {
        return FOOD_VALUE;
    }

    /**
     * @return MAX_FOOD_LEVEL The maximum food level of a seal
     */
    @Override
    public int getMaxFoodLevel(){
        return MAX_FOOD_LEVEL;
    }

    /**
     * Checks whether the parameter is one of the seal's food sources.
     * @param actor The animal that needs to be checked as food source
     * @return Whether the animal is one of the seal's food sources.
     */
    @Override
    public boolean isFood(Actor actor){
        if(actor instanceof Fish){
            return true;
        }
        else if(actor instanceof Penguin){
            return true;
        }
        return false;
    }
}
