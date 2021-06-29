import java.util.List;
import java.util.Random;
import java.util.Iterator;
import java.util.HashSet;

/**
 * A class representing shared characteristics of animals.
 * Animals have a food level and can die of hunger. Some
 * animals can also get sick.
 * 
 * @author David J. Barnes and Michael Kölling, modified by
 * Alexandra-Maria Anastase and Ioana-Daria Vasile
 * @version 2021.03.03
 */
public abstract class Animal extends Actor
{    
    // A shared random number generator to control breeding.
    private static final Random rand = Randomizer.getRandom();

    // Whether the animal is sick.
    private boolean sick;
    // The food level of the animal
    private int foodLevel;
    // Keeps track of the disease stage the animal is in
    // and when it reaches 0, the animal is cured.
    private int diseaseDuration;

    /**
     * Create a new animal at location in field.
     * @param randomAge Whether an animal will be initialized with a random age
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Animal(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location);        
        sick = false;
        if(randomAge) {            
            foodLevel = rand.nextInt(getMaxFoodLevel());
        }
        else {           
            foodLevel = getMaxFoodLevel();
        }
    }

    /**
     * @return foodLevel The food level of the animal
     */
    public int getFoodLevel(){
        return foodLevel;
    }

    /**
     * Increase the age.
     * This could result in the animal's death.
     */
    protected void incrementAge()
    {
        super.setAge(super.getAge() + 1);
        if(super.getAge() > getMaxAge()) {
            setDead();
        }
    }

    /**
     * Make this animal more hungry. This could result in the animal's death.
     */
    protected void incrementHunger()
    {
        foodLevel --;
        if(foodLevel <= 0) {
            setDead();            
        }
    }

    /**
     * Make the animal sick by decrementing its foodLevel.
     */
    protected void makeSick(){
        foodLevel = foodLevel - 2;
        if(foodLevel <= 0) {
            setDead();            
        }
    }

    /**
     * Look for food adjacent to the current location.
     * Only the first live food is eaten.
     * @return Where food was found, or null if it wasn't.
     */
    protected Location findFood()
    {
        Field field = getField();
        List<Location> adjacent = field.adjacentLocations(getLocation());
        Iterator<Location> it = adjacent.iterator();

        while(it.hasNext()) {
            Location where = it.next();
            Object object = field.getObjectAt(where);
            if(object instanceof Actor){
                Actor actor = (Actor) object; 
                if(isFood(actor)) {
                    if(actor.isAlive()) { 
                        actor.setDead();                        
                        if(foodLevel + actor.getFoodValue() > getMaxFoodLevel()){                            
                            foodLevel = getMaxFoodLevel();
                        }
                        else{
                            foodLevel = foodLevel +  actor.getFoodValue();
                        }
                        return where;
                    }
                }
            }
        }        
        return null;
    }

    /**
     * Animal is infected based on a probability.
     * @param diseaseStatus Whether the disease is present in the simulation.
     */
    public void infect(boolean diseaseStatus){
        if(diseaseStatus == false){
            return;
        }
        if(rand.nextDouble() <= Disease.getSpreadingProbability()){
            sick = true;
            diseaseDuration = Disease.getDiseaseDuration();
        }   
    }

    /**
     * Decrements the duration of the disease for the animal.
     */
    public void decrementDiseaseDuration(){
        diseaseDuration --;
        if(diseaseDuration <= 0){
            sick = false;
        }
    }

    /**
     * The animal contaminates other animals adjacent to him
     * based on a probability.
     */
    public void contaminate(){
        List<Location> adjacentLocations = getField().adjacentLocations(getLocation());
        for(Location loc : adjacentLocations){
            Object object = getField().getObjectAt(loc);
            if(object instanceof Animal){
                Animal animal = (Animal) object;
                if(!animal.isInfected()){
                    animal.infect(true);
                }
            }
        }
    }

    /**
     * @return sick Whether the animal is sick or not.
     */
    public boolean isInfected(){
        return sick;
    }

    /**
     * If the animal is sick, it contaminates others while the disease
     * affects it as well.
     */
    public void actDisease(){
        if(isInfected()){
            contaminate();
            makeSick();
        } 
    }

    // abstract methods

    /**
     * Checks whether the parameter is one of the animal's food sources.
     * @param actor The animal that needs to be checked as food source
     * @return Whether the animal is one of this animal's food sources.
     */
    abstract public boolean isFood(Actor actor);

    /**
     * @return foodValue The food value of the animal
     */
    abstract public int getFoodValue();    

    /**
     * @return MAX_FOOD_LEVEL The maximum food level of the animal
     */
    abstract public int getMaxFoodLevel();    
}
