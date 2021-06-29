import java.util.List;
import java.util.ArrayList;

/**
 * Class TerrainGenerator is responsible for creating the locations 
 * of the field, along with setting the type of terrain of each 
 * location. The terrain can be either land or water.
 *
 * @author Alexandra-Maria Anastase and Ioana-Daria Vasile
 * @version 2021.03.03
 */
public class TerrainGenerator
{
    // list of locations in our simulator
    List<Location> locations;

    /**
     * Constructor for objects of class TerrainGenerator
     */
    public TerrainGenerator()
    {
        locations = new ArrayList<Location>();       
    }

    /**
     * Creates the locations of the field and sets their
     * type of terrain.
     * @param depth The depth of the field
     * @param width The width of the field
     */
    public void generateTerrain(int depth, int width){
        for(int row = 0; row < depth; row++) {
            for(int col = 0; col < width; col++) {
                Location location = new Location(row, col);
                locations.add(location);

                if(!(row < col && row + col < width)){
                    location.setLand();
                }               
            }        
        }
    }

    /**
     * Returns a location by its row and column values.
     * @param row The row of the location to be returned
     * @param col The column of the location to be returned
     * @return The location based on its position in the field
     * or null if there is no such location
     */
    public Location getLocation(int row, int col){
        for(Location loc : locations){
            if(loc.getRow() == row && loc.getCol() == col){
                return loc;
            }
        }
        return null;
    }   
}
