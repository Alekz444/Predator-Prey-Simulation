import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * Foxes eat rabbits, can move on land and can be either
 * female or male. They don't eat at night (they only move
 * and procreate).
 * 
 * @author David J. Barnes and Michael Kölling, modified by
 * Alexandra-Maria Anastase and Ioana-Daria Vasile
 * @version 2021.03.03
 */
public class Fox extends GenderedAnimal
{
    // Characteristics shared by all foxes (class variables).

    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // The age at which a fox can start to breed.
    private static final int BREEDING_AGE = 15;
    // The age to which a fox can live.
    private static final int MAX_AGE = 150;
    // The likelihood of a fox breeding.
    private static final double BREEDING_PROBABILITY = 0.175;
    // The maximum number of births.
    private static final int MAX_LITTER_SIZE = 4;
    //The maximum food level of a fox.
    private static final int MAX_FOOD_LEVEL = 15;    
    // Food value of a fox
    private static final int FOOD_VALUE = 15;
    // Probability that a created fox is female
    private static final double FEMALE_PROBABILITY = 0.6;

    /**
     * Create a fox. A fox can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the fox will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param isFemale Whether the fox is female or not.
     */
    public Fox(boolean randomAge, Field field, Location location, boolean isFemale)
    {
        super(randomAge, field, location, isFemale);        
    }

    /**
     * This is what the fox does most of the time: it hunts for
     * rabbits. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param newFoxes A list to return newly born foxes
     * @param timer The timer object of the simulation
     * @weather The weather object of the simulation
     */
    @Override
    public void act(List<Actor> newFoxes, Timer timer, Weather weather)
    {
        incrementAge();
        incrementHunger();

        if(isAlive()){
            actDisease();
        }

        if(!isAlive()) { 
            return;
        }   

        if(isFemale() && hasMalePartner()){                
            giveBirth(newFoxes);                
        }  

        // Move towards a source of food if found.
        Location newLocation = null;
        if(timer.isDay()){
            newLocation = findFood();  
        }

        if(newLocation == null){
            List<Location> adjacentLocations = getField().getFreeAdjacentLocations(getLocation());                                    
            // See if it was possible to move.                        
            for(Location loc : adjacentLocations){
                if(loc.isLand()){
                    newLocation = loc;
                    break;
                }
            }
        }        

        if(newLocation != null) {
            setLocation(newLocation);
        }
        else {
            // Overcrowding.
            setDead();                
        }            
    }

    /**
     * Check whether or not this fox is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newFoxes A list to return newly born foxes.
     */
    private void giveBirth(List<Actor> newFoxes)
    {
        // New foxes are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());

        int births = reproduce();
        for(int b = 0; b < births; b++){
            while(free.size() > 0){
                Location loc = free.remove(0);
                if(loc.isLand()){
                    Fox young;
                    boolean gender;
                    if(rand.nextDouble() <= FEMALE_PROBABILITY){
                        gender = true;
                    }
                    else{
                        gender = false;
                    }
                    young = new Fox(true, field, loc, gender);  
                    newFoxes.add(young);
                }
            }
        }
    }

    /**
     * @return MAX_FOOD_LEVEL The maximum food level of a fox
     */
    @Override
    public int getMaxFoodLevel(){
        return MAX_FOOD_LEVEL;
    }

    /**
     * @return BREEDING_AGE The age at which a fox can start to breed
     */
    @Override
    public int getBreedingAge()
    {
        return BREEDING_AGE;
    }

    /**
     * @return MAX_AGE The maximum age of a fox
     */
    @Override
    public int getMaxAge()
    {
        return MAX_AGE;
    }

    /**
     * @return MAX_LITTER_SIZE The maximum litter size of a fox
     * at a time
     */
    @Override
    public int getMaxOffspringSize()
    {
        return MAX_LITTER_SIZE;
    }

    /**
     * @return BREEDING_PROBABILITY The breeding probability of 
     * a fox
     */
    @Override
    public double getReproductionProbability()
    {
        return BREEDING_PROBABILITY;
    }

    /**
     * @return FOOD_VALUE The food value of the fox
     */
    @Override
    public int getFoodValue()
    {
        return FOOD_VALUE;
    }

    /**
     * @return FEMALE_PROBABILITY The probability of a created 
     * bear is female.
     */
    public static double getFemaleProbability()
    {
        return FEMALE_PROBABILITY;
    }

    /**
     * Checks whether the parameter is one of the fox's food sources.
     * @param actor The animal that needs to be checked as food source
     * @return Whether the animal is one of the fox's food sources.
     */
    @Override
    public boolean isFood(Actor actor){
        if(actor instanceof Rabbit){
            return true;
        }
        return false;
    }

    /**
     * Checks whether there is a male fox in the adjacent locations.
     * @return True if there is a male fox in the adjacent locations
     * and false otherwise.
     */
    private boolean hasMalePartner(){        
        List <Location> adjacentLocations = getField().adjacentLocations(getLocation());
        for(Iterator <Location> it = adjacentLocations.iterator(); it.hasNext();){
            Location location = it.next();
            if(getField().getObjectAt(location) instanceof Fox){
                Fox fox = (Fox) getField().getObjectAt(location);
                if(!fox.isFemale()){
                    return true;                             
                }
            }
        }    
        return false;
    }
}
