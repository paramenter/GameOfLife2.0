import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Map {
    private Set<Cell> map;  // Use Set for efficient lookup

    public Map() {
        map = new HashSet<>();
    }

    // Add a pixel (cell) to the map if it's not already present
    public void addPixel(int x, int y) {
        map.add(new Cell(x, y)); // HashSet ensures uniqueness
    }

    // Return true if the cell is alive, false otherwise
    public boolean getPixel(int x, int y) {
        return map.contains(new Cell(x, y));
    }

    // Count the number of living neighbors around a cell
    public int getArround(int x, int y) {
        int arround = 0;
        if (getPixel(x - 1, y - 1)) arround++;
        if (getPixel(x, y - 1)) arround++;
        if (getPixel(x + 1, y - 1)) arround++;
        if (getPixel(x - 1, y)) arround++;
        if (getPixel(x + 1, y)) arround++;
        if (getPixel(x - 1, y + 1)) arround++;
        if (getPixel(x, y + 1)) arround++;
        if (getPixel(x + 1, y + 1)) arround++;
        return arround;
    }

    // Get all neighbors of live cells
    public List<Cell> updateList() {
        Set<Cell> neighbors = new HashSet<>();
        for (Cell cell : map) {
            neighbors.add(new Cell(cell.getX() - 1, cell.getY() - 1));
            neighbors.add(new Cell(cell.getX() - 1, cell.getY()));
            neighbors.add(new Cell(cell.getX() - 1, cell.getY() + 1));
            neighbors.add(new Cell(cell.getX(), cell.getY() - 1));
            neighbors.add(new Cell(cell.getX(), cell.getY()));
            neighbors.add(new Cell(cell.getX(), cell.getY() + 1));
            neighbors.add(new Cell(cell.getX() + 1, cell.getY() - 1));
            neighbors.add(new Cell(cell.getX() + 1, cell.getY()));
            neighbors.add(new Cell(cell.getX() + 1, cell.getY() + 1));
        }
        return new ArrayList<>(neighbors); // Return as a list
    }

    public Set<Cell> getMap() {
        return map;
    }

    public void setMap(Set<Cell> map) {
        this.map = map;
    }

    // Advance to the next generation
    public void nextGeneration() {
        List<Cell> possibleCells = updateList(); // Get all relevant cells
        Set<Cell> nextGen = new HashSet<>(); // Store next generation cells

        // Iterate through all possible cells and apply the rules
        for (Cell cell : possibleCells) {
            int arround = getArround(cell.getX(), cell.getY());

            // Apply Conway's Game of Life rules
            if (getPixel(cell.getX(), cell.getY())) {
                // Cellule vivante
                if (arround == 2 || arround == 3) {
                    nextGen.add(cell);
                }
            } else {
                // Cellule morte
                if (arround == 3) {
                    nextGen.add(cell);
                }
            }
        }

        // Update map to the new generation
        map = nextGen;
    }

}
