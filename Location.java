/**
 * Represent a location in a rectangular grid.
 * 
 * @author David J. Barnes and Michael Kölling modified by 
 * Alexandra-Maria Anastase and Ioana-Daria Vasile
 * @version 2021.03.03
 */
public class Location
{    
    // Row and column positions.
    private int row;
    private int col;

    //Terrain status. True if land, false if water.    
    private boolean land;

    /**
     * Represent a row and column.
     * @param row The row.
     * @param col The column.
     */
    public Location(int row, int col)
    {
        this.row = row;
        this.col = col;
        land = false;
    }

    /**
     * Implement content equality.
     */
    public boolean equals(Object obj)
    {
        if(obj instanceof Location) {
            Location other = (Location) obj;
            return row == other.getRow() && col == other.getCol();
        }
        else {
            return false;
        }
    }

    /**
     * Return a string of the form row,column
     * @return A string representation of the location.
     */
    public String toString()
    {
        return row + "," + col;
    }

    /**
     * Use the top 16 bits for the row value and the bottom for
     * the column. Except for very big grids, this should give a
     * unique hash code for each (row, col) pair.
     * @return A hashcode for the location.
     */
    public int hashCode()
    {
        return (row << 16) + col;
    }

    /**
     * @return The row.
     */
    public int getRow()
    {
        return row;
    }

    /**
     * @return The column.
     */
    public int getCol()
    {
        return col;
    }

    /**
     * Sets the terrain to land.
     */
    public void setLand(){
        land = true;
    }

    /**
     * Returns true if the terrain type is land 
     * and false if it is water.
     * @return land Status of land
     */
    public boolean isLand(){
        return land;
    }    
}
