package maze;

import util.Direction;

public class MazeCell {
    
    //Feilds
    public final int x, y, z;
    
    public boolean[] directions = new boolean[6];
    
    public int state = OUT;

    //Constructor
    public MazeCell(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    //Add a traversable direction
    public void addDirection(Direction dir) {
        
        directions[dir.dir] = true;
        
    }
    
    //Constants
    public static final int IN = 0;
    public static final int OUT = 1;
    public static final int FRONTIER = 2;
    public static final int SELECTED = 3;
    public static final int VISITED = 4;
    public static final int IN_PATH = 5;
    
}
