import java.util.List;

/**
 * Kelps are plants that live in water and are consumed
 * only by fish.
 *
 * @author Alexandra-Maria Anastase and Ioana-Daria Vasile
 * @version 2021.03.03
 */
public class Kelp extends Plant
{
    // Characteristics shared by all kelp (class variables).

    // food value of the kelp
    private static final int FOOD_VALUE = 2;
    // growth rate of the kelp
    private static final int GROWTH_RATE = 1;
    // adult age of the kelp / age when it can reproduce
    private static final int ADULT_AGE = 5;
    // maximum number of seeds that a kelp can produce at a time
    private static final int MAX_NUMBER_OF_SEEDS = 15;
    // reproduction probability of the kelp
    private static final double REPRODUCTION_PROBABILITY = 0.082;
    // maximum age of the kelp
    private static final int MAX_AGE = 20;

    /**
     * Constructor for objects of class Kelp
     * @param randomAge Whether a kelp will be initialized with a random age
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Kelp(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location);
    }

    /**
     * The reproduction of a kelp.
     * @param newKelp List where newly created kelps should be added to
     */
    public void plant(List<Actor> newKelp){
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int seeds = reproduce();
        for(int b = 0; b < seeds; b++){
            while(free.size() > 0){
                Location loc = free.remove(0);
                if(!loc.isLand()){
                    Kelp young;
                    young = new Kelp(true, field, loc);  
                    newKelp.add(young);
                }
            }
        }        
    }

    /**
     * The behavior of a kelp.
     * @param newKelp List where newly created kelps should be added to
     * @param timer The timer of the simulation
     * @param weather The weather object of the simulation
     */
    @Override
    public void act(List<Actor> newKelp, Timer timer, Weather weather){
        grow();
        if(isAlive()){
            plant(newKelp);            
        }        
    }

    /**
     * @return FOOD_VALUE The food value of the kelp
     */
    @Override
    public int getFoodValue(){
        return FOOD_VALUE;
    }

    /**
     *  @return ADULT_AGE The adult age/reproductive age of the kelp
     */
    @Override
    public int getBreedingAge(){
        return ADULT_AGE;
    }      

    /**
     * @return GROWTH_RATE The growth rate of the kelp
     */
    @Override
    public int getGrowthRate(){
        return GROWTH_RATE;
    }

    /**
     * @return MAX_NUMBER_OF_SEEDS The maximum number of seeds the kelp
     * can create at a time
     */
    @Override
    public int getMaxOffspringSize(){
        return MAX_NUMBER_OF_SEEDS;
    }

    /**
     * @return REPRODUCTION_PROBABILITY The reproduction probability of 
     * a kelp
     */
    @Override
    public double getReproductionProbability(){
        return REPRODUCTION_PROBABILITY;
    }

    /**
     * @return MAX_AGE The maximum age of a kelp 
     */
    @Override
    public int getMaxAge(){
        return MAX_AGE;
    }
}
