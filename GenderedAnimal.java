
/**
 * A class representing shared characteristics of animals
 * who can be either female or male.
 *
 * @author Alexandra-Maria Anastase and Ioana-Daria Vasile
 * @version 2021.03.03
 */
public abstract class GenderedAnimal extends Animal
{
    // Whether the animal is female.
    // (if not, then it is male)
    private boolean isFemale;

    /**
     * Create a new gendered animal at location in field.
     * @param randomAge Whether an animal will be initialized with a random age
     * @param field The field currently occupied.
     * @param location The location within the field.
     * @param isFemale Whether the animal is a female or not
     */
    public GenderedAnimal(boolean randomAge, Field field, Location location, boolean isFemale){
        super(randomAge, field, location);
        this.isFemale = isFemale;
    }

    /**
     * @return isFemale Whether the animal is female or not.
     */
    protected boolean isFemale(){
        return isFemale;
    }
}
