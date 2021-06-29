import java.util.List;
import java.util.Iterator;

/**
 * Rabbits eat lichens, move only on land, 
 * can get sick and, during snowstorms,
 * can "freeze" (die of starvation faster)
 * if they are not near other animals.
 * 
 * @author David J. Barnes and Michael Kölling, modified by
 * Alexandra-Maria Anastase and Ioana-Daria Vasile
 * @version 2021.03.03
 */
public class Rabbit extends Animal
{
    // Characteristics shared by all rabbits (class variables).

    // The age at which a rabbit can start to breed.
    private static final int BREEDING_AGE = 3;
    // The age to which a rabbit can live.
    private static final int MAX_AGE = 25;
    // The likelihood of a rabbit breeding.
    private static final double BREEDING_PROBABILITY = 0.12;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    // Food level of predator who eats a rabbit gets incremented by this value.
    private static final int FOOD_VALUE = 9;
    // The maximum food level of a rabbit
    private static final int MAX_FOOD_LEVEL = 8;

    // Individual characteristics (instance fields).

    // Whether the rabbit can freeze or not.
    private boolean canFreeze;

    /**
     * Create a new rabbit. A rabbit may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the rabbit will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Rabbit(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location);        
    }

    /**
     * This is what the rabbit does most of the time - it runs 
     * around. Sometimes it will breed or die of old age.
     * @param newRabbits A list to return newly born rabbits
     * @timer The timer object of the simulation
     * @weather The weather object of the simulation
     */
    @Override
    public void act(List<Actor> newRabbits, Timer timer, Weather weather)
    {
        incrementAge();
        incrementHunger();
        canFreeze = weather.isSnowstorm();

        if(isAlive()){
            actDisease();
        }

        if(!isAlive()) {
            return;
        }            

        giveBirth(newRabbits);                                       
        // Move towards a source of food if found.
        Location newLocation = findFood();
        List<Location> adjacentLocations = getField().getFreeAdjacentLocations(getLocation());

        if(newLocation == null){

            // See if it was possible to move.

            for(Location loc : adjacentLocations){
                if(loc.isLand()){
                    newLocation = loc;
                    break;
                }
            } 
        }     

        if(canFreeze){
            boolean freeze = true;

            for(Location loc : adjacentLocations){
                if(getField().getObjectAt(loc) instanceof Animal){
                    freeze = false;
                }
            }
            if(freeze){
                incrementHunger();
                if(!isAlive()){
                    return;
                }
            }
        }

        // See if it was possible to move.
        if(newLocation != null ) {
            setLocation(newLocation);
        }
        else {
            // Overcrowding.
            setDead();
        }
    }    

    /**
     * Check whether or not this rabbit is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newRabbits A list to return newly born rabbits.
     */
    private void giveBirth(List<Actor> newRabbits)
    {
        // New rabbits are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = reproduce();
        for(int b = 0; b < births; b++){
            while(free.size() > 0){
                Location loc = free.remove(0);
                if(loc.isLand()){
                    Rabbit young;
                    young = new Rabbit(true, field, loc);  
                    newRabbits.add(young);
                }
            }
        }
    }

    /**
     * @return MAX_FOOD_LEVEL The maximum food level of a rabbit
     */
    @Override
    public int getMaxFoodLevel(){
        return MAX_FOOD_LEVEL;
    }

    /**
     * @return BREEDING_AGE The age at which a rabbit can start to breed
     */
    @Override
    public int getBreedingAge()
    {
        return BREEDING_AGE;
    }

    /**
     * @return MAX_AGE The maximum age of a rabbit
     */
    @Override
    public int getMaxAge()
    {
        return MAX_AGE;
    }

    /**
     * @return MAX_LITTER_SIZE The maximum litter size of a rabbit
     * at a time
     */
    @Override
    public int getMaxOffspringSize()
    {
        return MAX_LITTER_SIZE;
    }

    /**
     * @return BREEDING_PROBABILITY The breeding probability of 
     * a rabbit
     */
    @Override
    public double getReproductionProbability()
    {
        return BREEDING_PROBABILITY;
    }

    /**
     * @return FOOD_VALUE The food value of the rabbit
     */
    @Override
    public int getFoodValue(){
        return FOOD_VALUE;
    }

    /**
     * Checks whether the parameter is one of the rabbit's food sources.
     * @param actor The plant that needs to be checked as food source
     * @return Whether the plant is one of the rabbit's food sources.
     */
    @Override
    public boolean isFood(Actor actor){
        if(actor instanceof Lichen){
            return true;
        }
        return false;
    }
}
