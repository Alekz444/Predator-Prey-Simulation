import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.awt.Color;

/**
 * Class PopulationGenerator is responsible for the initial creation 
 * of the actors of the simulation. It relies on a Random object,
 * the creation probabilities of the actors and the type of terrain.
 *
 * @author Alexandra-Maria Anastase and Ioana-Daria Vasile
 * @version 2021.03.03
 */
public class PopulationGenerator
{
    // The probability that a rabbit will be created in any given grid position.
    private static final double RABBIT_CREATION_PROBABILITY = 0.06;   

    // The probability that a fox will be created in any given grid position.
    private static final double FOX_CREATION_PROBABILITY = 0.03;

    // The probability that a fish will be created in any given grid position.
    private static final double FISH_CREATION_PROBABILITY = 0.05;

    // The probability that a penguin will be created in any given grid position.
    private static final double PENGUIN_CREATION_PROBABILITY = 0.02;

    // The probability that a bear will be created in any given grid position.
    private static final double BEAR_CREATION_PROBABILITY = 0.011;

    // The probability that a seal will be created in any given grid position.
    private static final double SEAL_CREATION_PROBABILITY = 0.01;

    // The probability that a lichen will be created in any given grid position.
    private static final double LICHEN_CREATION_PROBABILITY = 0.08;   

    // // The probability that a kelp plant will be created in any given grid position.
    private static final double KELP_CREATION_PROBABILITY = 0.09;  

    /**
     * Constructor for objects of class PopulationGenerator
     */
    public PopulationGenerator()
    {

    }

    /**
     * Populates the field with the actors.
     * @param field The field of the simulation
     * @param actors List of actors to fill
     * @param terrainGenerator The terrain generator of the simulation
     */
    public void populate(Field field, List<Actor> actors, TerrainGenerator terrainGenerator){        
        Random rand = Randomizer.getRandom();

        // Each actor can move/be created on either land or water or both.
        for(int row = 0; row < field.getDepth(); row++) {
            for(int col = 0; col < field.getWidth(); col++) {

                Location location = terrainGenerator.getLocation(row, col);

                if(rand.nextDouble() <= FOX_CREATION_PROBABILITY && location.isLand()) {                                        
                    Fox fox;
                    boolean gender;
                    if(rand.nextDouble() <= Fox.getFemaleProbability()){
                        gender = true;
                    }
                    else{
                        gender = false;
                    }
                    fox = new Fox(true, field, location, gender);  
                    actors.add(fox);                   
                }
                else if(rand.nextDouble() <= RABBIT_CREATION_PROBABILITY && location.isLand()) {
                    Rabbit rabbit = new Rabbit(true, field, location);
                    actors.add(rabbit);
                }           
                else if(rand.nextDouble() <= FISH_CREATION_PROBABILITY && !location.isLand()) {                    
                    Fish fish = new Fish(true, field, location);
                    actors.add(fish);
                }  
                else if(rand.nextDouble() <= PENGUIN_CREATION_PROBABILITY) {                   
                    Penguin penguin = new Penguin(true, field, location);
                    actors.add(penguin);
                }  
                else if(rand.nextDouble() <= BEAR_CREATION_PROBABILITY) {                                       
                    Bear bear;
                    boolean gender;
                    if(rand.nextDouble() <= Bear.getFemaleProbability()){
                        gender = true;
                    }
                    else{
                        gender = false;
                    }
                    bear = new Bear(true, field, location, gender);  
                    actors.add(bear);
                }  
                else if(rand.nextDouble() <= SEAL_CREATION_PROBABILITY) {                    
                    Seal seal = new Seal(true, field, location);
                    actors.add(seal);
                }

                // create plants

                if(rand.nextDouble() <= LICHEN_CREATION_PROBABILITY && location.isLand()) {                                        
                    Lichen lichen = new Lichen(true, field, location);
                    actors.add(lichen);
                }           
                else if(rand.nextDouble() <= KELP_CREATION_PROBABILITY && !location.isLand()) {                    
                    Kelp kelp = new Kelp(true, field, location);
                    actors.add(kelp);
                }  
                // else leave the location empty.
            }
        }         
    }

    /**
     * Sets the color that each actor will have in the view of the simulation.
     * @param view The view of the simulation
     */
    public void setActorsColors(SimulatorView view){
        view.setColor(Rabbit.class, Color.PINK);
        view.setColor(Fox.class, Color.ORANGE);
        view.setColor(Fish.class, Color.BLUE);
        view.setColor(Penguin.class, Color.BLACK);
        view.setColor(Bear.class, Color.YELLOW);
        view.setColor(Seal.class, Color.GRAY);
        view.setColor(Kelp.class, Color.CYAN);
        view.setColor(Lichen.class, Color.GREEN);
    }    
}
