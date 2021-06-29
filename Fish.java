import java.util.List;
import java.util.Iterator;

/**
 * A fish eats kelp and can only move in water.
 *
 * @author Alexandra-Maria Anastase and Ioana-Daria Vasile
 * @version 2021.03.03
 */
public class Fish extends Animal
{
    // Characteristics shared by all fish (class variables).

    // The age at which a fish can start to breed.
    private static final int BREEDING_AGE = 4;
    // The age to which a fish can live.
    private static final int MAX_AGE = 20;
    // The likelihood of a fish breeding.
    private static final double BREEDING_PROBABILITY = 0.15;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 20;
    // Food level of predator who eats a fish gets incremented by this value.
    private static final int FOOD_VALUE = 4;
    // The maximum food level of a fish
    private static final int MAX_FOOD_LEVEL = 8;

    // Individual characteristics (instance fields).

    // The fish's age.
    private int age;
    // The fish's food level
    private int foodLevel;

    /**
     * Create a new fish. A fish may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the fish will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Fish(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location);        
    }

    /**
     * This is what the fish does most of the time - it swims 
     * around. Sometimes it will breed or die of old age.
     * @param newFish A list to return newly born fish.
     * @timer The timer object of the simulation
     * @weather The weather object of the simulation
     */
    @Override
    public void act(List<Actor> newFish, Timer timer, Weather weather)
    {
        incrementAge();
        incrementHunger();

        if(!isAlive()) {
            return;
        }          

        giveBirth(newFish);                                       
        // Move towards a source of food if found.
        Location newLocation = findFood();

        if(newLocation == null){                
            List<Location> adjacentLocations = getField().getFreeAdjacentLocations(getLocation()); 

            // See if it was possible to move.

            for(Location loc : adjacentLocations){
                if(!loc.isLand()){
                    newLocation = loc;
                    break;
                }
            }
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
     * Check whether or not this fish is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newFish A list to return newly born fish.
     */
    private void giveBirth(List<Actor> newFish)
    {
        // New fish are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = reproduce();
        for(int b = 0; b < births; b++){
            while(free.size() > 0){
                Location loc = free.remove(0);
                if(!loc.isLand()){
                    Fish young;
                    young = new Fish(true, field, loc);  
                    newFish.add(young);                   
                }
            }
        }
    }

    /**
     * @return MAX_FOOD_LEVEL The maximum food level of a fish
     */
    @Override
    public int getMaxFoodLevel(){
        return MAX_FOOD_LEVEL;
    }

    /**
     * @return BREEDING_AGE The age at which a fish can start to breed
     */
    @Override
    public int getBreedingAge()
    {
        return BREEDING_AGE;
    }

    /**
     * @return MAX_AGE The maximum age of a fish
     */
    @Override
    public int getMaxAge()
    {
        return MAX_AGE;
    }

    /**
     * @return MAX_LITTER_SIZE The maximum litter size of a fish
     * at a time
     */
    @Override
    public int getMaxOffspringSize()
    {
        return MAX_LITTER_SIZE;
    }

    /**
     * @return BREEDING_PROBABILITY The breeding probability of 
     * a fish
     */
    @Override
    public double getReproductionProbability()
    {
        return BREEDING_PROBABILITY;
    }

    /**
     * @return FOOD_VALUE The food value of the fish
     */
    @Override
    public int getFoodValue()
    {
        return FOOD_VALUE;
    }

    /**
     * Checks whether the parameter is one of the fish's food sources.
     * @param actor The plant that needs to be checked as food source
     * @return Whether the plant is one of the fish's food sources.
     */
    @Override
    public boolean isFood(Actor actor){
        if(actor instanceof Kelp){
            return true;
        }
        return false;
    }    
}
