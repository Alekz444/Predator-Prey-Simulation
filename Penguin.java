import java.util.List;
import java.util.Iterator;

/**
 * A penguin eats fish, can move on both water and land.
 * They don't eat at night (only move and procreate).
 * If there is a snowstorm and they are not near other animals,
 * they will "freeze" (die of starvation much faster).
 * 
 * @author Alexandra-Maria Anastase and Ioana-Daria Vasile
 * @version 2021.03.03
 */
public class Penguin extends Animal
{
    // Characteristics shared by all penguins (class variables).

    // The age at which a penguin can start to breed.
    private static final int BREEDING_AGE = 7;
    // The age to which a penguin can live.
    private static final int MAX_AGE = 50;
    // The likelihood of a penguin breeding.
    private static final double BREEDING_PROBABILITY = 0.13;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 2;
    //The maximum food level of a penguin.
    private static final int MAX_FOOD_LEVEL = 12;
    // Food level of predator who eats a penguin gets incremented by this value.
    private static final int FOOD_VALUE = 12;

    // Individual characteristics (instance fields).

    // Whether the penguin can freeze.
    private boolean canFreeze;

    /**
     * Create a new penguin. A penguin may be created with age
     * zero (a new born) or with a random age.
     * 
     * @param randomAge If true, the penguin will have a random age.
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Penguin(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location);        
    }

    /**
     * This is what the penguin does most of the time: it hunts for
     * fish. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param newPenguins A list to return newly born penguins.\
     * @param timer The Timer object of the simulation
     * @param weather The weather object of the simulation
     */
    @Override
    public void act(List<Actor> newPenguins, Timer timer, Weather weather)
    {
        incrementAge();
        incrementHunger();
        canFreeze = weather.isSnowstorm();

        if(!isAlive()) {
            return;
        }       

        giveBirth(newPenguins); 

        // Move towards a source of food if found.
        Location newLocation = null;
        if(timer.isDay()){
            newLocation = findFood();  
        }

        if(canFreeze){
            boolean freeze = true;
            List<Location> adjacentLocations = getField().getFreeAdjacentLocations(getLocation());
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

        if(newLocation == null){
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
     * Check whether or not this penguin is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newPenguins A list to return newly born penguins.
     */
    private void giveBirth(List<Actor> newPenguins)
    {
        // New fish are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();

        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = reproduce();

        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Penguin young = new Penguin(false, field, loc);
            newPenguins.add(young);
        }
    }

    /**
     * @return MAX_FOOD_LEVEL The maximum food level of a penguin
     */
    @Override
    public int getMaxFoodLevel(){
        return MAX_FOOD_LEVEL;
    }

    /**
     * @return BREEDING_AGE The age at which a penguin can start to breed
     */
    @Override
    public int getBreedingAge()
    {
        return BREEDING_AGE;
    }

    /**
     * @return MAX_AGE The maximum age of a penguin
     */
    @Override
    public int getMaxAge()
    {
        return MAX_AGE;
    }

    /**
     * @return MAX_LITTER_SIZE The maximum litter size of a penguin
     * at a time
     */
    @Override
    public int getMaxOffspringSize()
    {
        return MAX_LITTER_SIZE;
    }

    /**
     * @return BREEDING_PROBABILITY The breeding probability of 
     * a penguin
     */
    @Override
    public double getReproductionProbability()
    {
        return BREEDING_PROBABILITY;
    }

    /**
     * @return FOOD_VALUE The food value of the penguin
     */
    @Override
    public int getFoodValue()
    {
        return FOOD_VALUE;
    }

    /**
     * Checks whether the parameter is one of the penguin's food sources.
     * @param actor The animal that needs to be checked as food source
     * @return Whether the animal is one of the penguin's food sources.
     */
    @Override
    public boolean isFood(Actor actor){
        if(actor instanceof Fish){
            return true;
        }
        return false;
    }    
}
