import java.util.Random;
import java.util.List;
import java.util.HashSet;

/**
 * A class representing shared characteristics of actors.
 * An actor can be an animal or a plant, can be alive or dead
 * and occupies a particular location in the field.
 *
 * @author Alexandra-Maria Anastase and Ioana-Daria Vasile
 * @version 2021.03.03
 */
public abstract class Actor
{
    protected static final Random rand = Randomizer.getRandom();

    // Whether the actor is alive or not.
    private boolean alive;
    // The actor's field.
    private Field field;
    // The actor's position in the field.
    private Location location;
    // The actor's age.
    private int age;

    /**
     * Constructor for objects of class Actor
     * @param randomAge Whether an actor will be initialized with a random age
     * @param field The field of the simulation
     * @param location The location the actor should be placed in
     */
    public Actor(boolean randomAge, Field field, Location location)
    {
        alive = true;
        this.field = field;

        age = 0;
        if(randomAge) {
            age = rand.nextInt(getMaxAge());           
        }

        setLocation(location);
    }

    /**
     * Check whether the actor is alive or not.
     * @return true if the actor is still alive.
     */
    protected boolean isAlive()
    {
        return alive;
    }

    /**
     * Indicate that the actor is no longer alive.
     * It is removed from the field.
     */
    protected void setDead()
    {         
        alive = false;
        if(location != null) {
            field.clear(location);
            location = null;
            field = null;
        }
    }

    /**
     * Return the actor's location.
     * @return The actor's location.
     */
    protected Location getLocation()
    {
        return location;
    }

    /**
     * Place the actor at the new location in the given field.
     * @param newLocation The actor's new location.
     */
    protected void setLocation(Location newLocation)
    {
        if(location != null) {
            field.clear(location);
        }
        location = newLocation;
        field.place(this, newLocation);
    }

    /**
     * Return the actor's field.
     * @return The actor's field.
     */
    protected Field getField()
    {
        return field;
    }

    /**
     * @returns True if the actor can reproduce 
     * (if they are of breeding age) and
     * false otherwise.
     */
    private boolean canReproduce(){
        return age >= getBreedingAge();
    }

    /**
     * Generate a number representing the number of offsprings,
     * if it can breed.
     * @return The number of offsprings (may be zero).
     */
    protected int reproduce()
    {
        int offsprings = 0;
        if(canReproduce() && rand.nextDouble() <= getReproductionProbability()) {
            offsprings = rand.nextInt(getMaxOffspringSize()) + 1;
        }
        return offsprings;
    }

    /**
     * Sets the age of the actor.
     * @param number The age the actor should be set to.
     */
    public void setAge(int number){
        age = number;
    }

    /**
     * @return age The age of the actor
     */
    public int getAge(){
        return age;
    }

    // abstract methods

    /**
     * @return The maximum age of the actor
     */
    public abstract int getMaxAge();

    /**
     * @return The food value of the actor
     */
    public abstract int getFoodValue();

    /**
     * The behavior of the actor.
     * @param newActors List newly created actors should be added to
     * @param timer The Timer object of the simulation
     * @param weather The Weather object of the simulation
     */
    abstract public void act(List<Actor> newActors, Timer timer, Weather weather);

    /**
     * @return the breeding age of the actor
     */
    abstract protected int getBreedingAge();

    /**
     * @return the maximum number of offspring the actor
     * can create at a time
     */
    abstract protected int getMaxOffspringSize();

    /**
     * @return the probability of an actor reproducing
     */
    abstract protected double getReproductionProbability();
}
