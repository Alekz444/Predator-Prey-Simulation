import java.util.Random;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashSet;

/**
 * A simple predator-prey simulator, based on a rectangular field
 * containing rabbits and foxes.
 * 
 * @author David J. Barnes and Michael Kölling modified by
 * Alexandra-Maria Anastase and Ioana-Daria Vasile
 * @version 2021.03.03
 */
public class Simulator
{
    // Constants representing configuration information for the simulation.
    // The default width for the grid.
    private static final int DEFAULT_WIDTH = 120;
    // The default depth of the grid.
    private static final int DEFAULT_DEPTH = 80;

    // List of animals in the field.
    private List<Actor> actors;        
    // The current state of the field.
    private Field field;
    // The current step of the simulation.
    private int step;
    // A graphical view of the simulation.
    private SimulatorView view;
    // A generator for the population of the simulation.
    private PopulationGenerator populationGenerator;
    // A simulator for the weather of the simulation.
    private WeatherSimulator weatherSimulator;
    // Object keeps track of the disease in the simulation.
    private Disease disease;
    // Set of animals infected with the disease.
    private HashSet<Animal> sickAnimals;
    // A generator for the terrain of the simulation.
    private TerrainGenerator terrainGenerator;
    // A timer that keeps track of the time of the day.
    private Timer timer;

    /**
     * Construct a simulation field with default size.
     */
    public Simulator()
    {
        this(DEFAULT_DEPTH, DEFAULT_WIDTH);
    }

    /**
     * Create a simulation field with the given size.
     * @param depth Depth of the field. Must be greater than zero.
     * @param width Width of the field. Must be greater than zero.
     */
    public Simulator(int depth, int width)
    {
        if(width <= 0 || depth <= 0) {
            System.out.println("The dimensions must be greater than zero.");
            System.out.println("Using default values.");
            depth = DEFAULT_DEPTH;
            width = DEFAULT_WIDTH;
        }
        actors = new ArrayList<>();        
        terrainGenerator = new TerrainGenerator();        
        field = new Field(depth, width, terrainGenerator);        
        populationGenerator = new PopulationGenerator();
        weatherSimulator = new WeatherSimulator();
        disease = new Disease();
        sickAnimals = new HashSet<>();

        //we set the time of day to daytime
        timer = new Timer(true);
        // Create a view of the state of each location in the field.
        view = new SimulatorView(depth, width);
        // Set colors for the animals.
        populationGenerator.setActorsColors(view);
        // Generate terrain for the field.
        terrainGenerator.generateTerrain(field.getDepth(), field.getWidth());        
        // Setup a valid starting point.

        reset();
    }

    public static void main(String[] args) {
        // Create a Simulator instance.
        Simulator simulator = new Simulator();
        // Start the simulation.
        simulator.runLongSimulation();
    }

    /**
     * Run the simulation from its current state for a reasonably long period,
     * (4000 steps).
     */
    public void runLongSimulation()
    {
        simulate(4000);
    }

    /**
     * Run the simulation from its current state for the given number of steps.
     * Stop before the given number of steps if it ceases to be viable.
     * @param numSteps The number of steps to run for.
     */
    public void simulate(int numSteps)
    {
        for(int step = 1; step <= numSteps && view.isViable(field); step++) {
            simulateOneStep();            
            // delay(60);   // uncomment this to run more slowly
        }               
    }

    /**
     * Run the simulation from its current state for a single step.
     * Iterate over the whole field updating the state of each
     * actor and of the weather, timer and disease.
     */
    public void simulateOneStep()
    {
        step++;
        timer.incrementTime();

        if(disease.getStatus() == false){
            disease.simulateDisease();
        }

        // Provide space for newly created actors.
        List<Actor> newActors = new ArrayList<>();

        // Let all actors act.
        for(Iterator<Actor> it = actors.iterator(); it.hasNext(); ) {
            Actor actor = it.next();
            Weather weather = weatherSimulator.getWeather();            
            actor.act(newActors, timer, weather);

            if(actor instanceof Animal){
                Animal animal = (Animal) actor;
                animal.infect(disease.getStatus());                   
                if(animal.isInfected()){                   
                    sickAnimals.add(animal);
                }
                if(sickAnimals.contains(animal) && (!animal.isInfected() || !animal.isAlive())){
                    sickAnimals.remove(animal);                    
                }                
            }

            if(! actor.isAlive()) {
                it.remove();
            }
        }

        if(sickAnimals.isEmpty()){
            disease.setStatus(false);
        }

        weatherSimulator.decrementSteps();

        // Add the newly created actors to the main lists.        
        actors.addAll(newActors);

        view.showStatus(step, field);       
    }

    /**
     * Reset the simulation to a starting position.
     */
    public void reset()
    {
        step = 0;
        actors.clear();
        field.clear();
        sickAnimals.clear();
        populationGenerator.populate(field, actors, terrainGenerator);
        weatherSimulator.resetWeather();
        disease.setStatus(false);

        // Show the starting state in the view.
        view.showStatus(step, field);
    }

    /**
     * Pause for a given time.
     * @param millisec  The time to pause for, in milliseconds
     */
    private void delay(int millisec)
    {
        try {
            Thread.sleep(millisec);
        }
        catch (InterruptedException ie) {
            // wake up
        }
    }
}
