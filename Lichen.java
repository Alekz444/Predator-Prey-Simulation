import java.util.List;

/**
 * Lichens are plants that live on land and are consumed
 * only by rabbits. Lichens do not grow during
 * snowstorms (but can still reproduce).
 *
 * @author Alexandra-Maria Anastase and Ioana-Daria Vasile
 * @version 2021.03.03
 */
public class Lichen extends Plant
{
    // the food value of a lichen
    private static final int FOOD_VALUE = 6;
    // the growth rate of a lichen
    private static final int GROWTH_RATE = 1;
    // adult age of the kelp / age when it can reproduce 
    private static final int ADULT_AGE = 6;
    // the maximum number of seeds a lichen can produce at a time
    private static final int MAX_NUMBER_OF_SEEDS = 4;
    // the reproduction probability of a lichen
    private static final double REPRODUCTION_PROBABILITY = 0.012;  
    // the maximum age of a lichen
    private static final int MAX_AGE = 50;

    // Individual characteristics (instance fields).

    // whether the lichen can grow (depending on weather conditions)
    private boolean canGrow;

    /**
     * Constructor for objects of class Lichen
     * @param randomAge Whether a lichen will be initialized with a random age
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Lichen(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location);
    }

    /**
     * The reproduction of a lichen.
     * @param newLichen List where newly created lichens should be added to
     */
    public void plant(List<Actor> newLichens){
        Field field = getField();
        List<Location> free = field.getFreeAdjacentLocations(getLocation());
        int seeds = reproduce();
        for(int b = 0; b < seeds; b++){
            while(free.size() > 0){
                Location loc = free.remove(0);
                if(loc.isLand()){
                    Lichen young;
                    young = new Lichen(true, field, loc);  
                    newLichens.add(young);
                }
            }
        }        
    }

    /**
     * The behavior of a lichen.
     * @param newLichen List where newly created lichens should be added to
     * @param timer The timer of the simulation
     * @param weather The weather object of the simulation
     */
    @Override
    public void act(List<Actor> newLichens, Timer timer, Weather weather){
        canGrow = !weather.isSnowstorm();
        if(canGrow){
            grow();
        }
        if(isAlive()){
            plant(newLichens);            
        }        
    }

    /**
     * @return FOOD_VALUE The food value of the lichen
     */
    @Override
    public int getFoodValue(){
        return FOOD_VALUE;
    }

    /**
     *  @return ADULT_AGE The adult age/reproductive age of the lichen
     */
    @Override
    public int getBreedingAge(){
        return ADULT_AGE;
    }

    /**
     * @return GROWTH_RATE The growth rate of the lichen
     */
    @Override
    public int getGrowthRate(){
        return GROWTH_RATE;
    }

    /**
     * @return MAX_NUMBER_OF_SEEDS The maximum number of seeds the lichen
     * can create at a time
     */
    @Override
    public int getMaxOffspringSize(){
        return MAX_NUMBER_OF_SEEDS;
    }

    /**
     * @return REPRODUCTION_PROBABILITY The reproduction probability of 
     * a lichen
     */
    @Override
    public double getReproductionProbability(){
        return REPRODUCTION_PROBABILITY;
    }

    /**
     * @return MAX_AGE The maximum age of a lichen 
     */
    @Override
    public int getMaxAge(){
        return MAX_AGE;
    }
}
