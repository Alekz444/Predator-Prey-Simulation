import java.util.List;

/**
 * A class representing shared characteristics of plants.
 * Plants are a food source for some animals, they grow 
 * and reproduce.
 *
 * @author Alexandra-Maria Anastase and Ioana-Daria Vasile
 * @version 2021.03.03
 */
public abstract class Plant extends Actor
{

    /**
     * Constructor for objects of class Plant
     * @param randomAge Whether a plant will be initialized with a random age
     * @param field The field currently occupied.
     * @param location The location within the field.
     */
    public Plant(boolean randomAge, Field field, Location location)
    {
        super(randomAge, field, location);    
    }

    /**
     * Make plant grow and checks whether it is ready to eat.
     */        
    protected void grow(){
        super.setAge(super.getAge() + getGrowthRate());
    }    

    /**
     * @return GROWTH_RATE The growth rate of the plant. 
     */
    public abstract int getGrowthRate();       
}
