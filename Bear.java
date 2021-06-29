import java.util.List;
import java.util.Iterator;
import java.util.Random;

/**
 * Write a description of class Bear here.
 * A bear eats fish and foxes, can move on both land and water,
 * can be either female or male and can get sick. They also
 * can't hunt when there is fog (they can't see their prey) and
 * sleep at night.
 *
 * @author Alexandra-Maria Anastase and Ioana-Daria Vasile
 * @version 2021.03.03
 */
public class Bear extends GenderedAnimal
{
    // Characteristics shared by all bears (class variables).

    // The age at which a bear can start to breed.
    private static final int BREEDING_AGE = 15;
    // The age to which a bear can live.
    private static final int MAX_AGE = 150;
    // The likelihood of a bear breeding.
    private static final double BREEDING_PROBABILITY = 0.6;
    // The maximum number of births.9
    private static final int MAX_LITTER_SIZE = 4;
    //The maximum food level of a bear.
    private static final int MAX_FOOD_LEVEL = 20;
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();
    // The probability of a created bear of being female
    private static final double FEMALE_PROBABILITY = 0.4;
    // The food value of a bear
    private static final int FOOD_VALUE = 15;

    // Individual characteristics (instance fields).

    // Whether the bear can see or not (depending on weather conditions)
    private boolean canSee;

    /**
     * Create a bear. A bear can be created as a new born (age zero
     * and not hungry) or with a random age and food level.
     * 
     * @param randomAge If true, the bear will have random age and hunger level.
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param isFemale Whether the bear is female or not.
     */
    public Bear(boolean randomAge, Field field, Location location, boolean isFemale)
    {
        super(randomAge, field, location, isFemale);       
    }

    /**
     * This is what the bear does most of the time: it hunts for
     * fish and foxes. In the process, it might breed, die of hunger,
     * or die of old age.
     * @param field The field currently occupied.
     * @param newBears A list to return newly born bears.
     */
    @Override
    public void act(List<Actor> newBears, Timer timer, Weather weather)
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

        if(isFemale() && hasMalePartner()){                
            giveBirth(newBears);                
        }  

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
     * Check whether or not this bear is to give birth at this step.
     * New births will be made into free adjacent locations.
     * @param newBears A list to return newly born bears.
     */
    private void giveBirth(List<Actor> newBears)
    {
        // New bears are born into adjacent locations.
        // Get a list of adjacent free locations.
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int births = reproduce();
        for(int b = 0; b < births && free.size() > 0; b++) {
            Location loc = free.remove(0);
            Bear young;
            boolean gender;            
            if(rand.nextDouble() <= FEMALE_PROBABILITY){
                gender = true;
            }
            else{
                gender = false;
            }
            young = new Bear(true, field, loc, gender);  
            newBears.add(young);
        }       
    }

    /**
     * @return MAX_FOOD_LEVEL The maximum food level of a bear
     */
    @Override
    public int getMaxFoodLevel(){
        return MAX_FOOD_LEVEL;
    }

    /**
     * @return BREEDING_AGE The age at which a bear can start to breed
     */
    @Override
    public int getBreedingAge()
    {
        return BREEDING_AGE;
    }

    /**
     * @return MAX_AGE The maximum age of a bear
     */
    @Override
    public int getMaxAge()
    {
        return MAX_AGE;
    }

    /**
     * @return MAX_LITTER_SIZE The maximum litter size of a bear
     * at a time
     */
    @Override
    public int getMaxOffspringSize()
    {
        return MAX_LITTER_SIZE;
    }

    /**
     * @return BREEDING_PROBABILITY The breeding probability of 
     * a bear
     */
    @Override
    public double getReproductionProbability()
    {
        return BREEDING_PROBABILITY;
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
     * @return FOOD_VALUE The food value of the bear
     */
    @Override
    public int getFoodValue(){
        return FOOD_VALUE;
    }

    /**
     * Checks whether the parameter is one of the bear's food sources.
     * @param actor The animal that needs to be checked as food source
     * @return Whether the animal is one of the bear's food sources.
     */
    @Override
    public boolean isFood(Actor actor){
        if(actor instanceof Fish){
            return true;
        }
        else if(actor instanceof Fox){
            return true;
        }
        return false;
    }

    /**
     * Checks whether there is a male bear in the adjacent locations.
     * @return True if there is a male bear in the adjacent locations
     * and false otherwise.
     */
    private boolean hasMalePartner(){        
        List <Location> adjacentLocations = getField().adjacentLocations(getLocation());
        for(Iterator <Location> it = adjacentLocations.iterator(); it.hasNext();){
            Location location = it.next();
            if(getField().getObjectAt(location) instanceof Bear){
                Bear bear = (Bear) getField().getObjectAt(location);
                if(!bear.isFemale()){
                    return true;                             
                }
            }
        }    
        return false;
    }
}
