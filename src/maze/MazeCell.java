package maze;

import util.Direction;

public class MazeCell {
    
    public final int x, y;
    
    public boolean[] directions = new boolean[6];
    
    public int state = OUT;

    public MazeCell(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void addDirection(Direction dir) {
        
        directions[dir.dir] = true;
        
    }
    
    public static final int IN = 0;
    public static final int OUT = 1;
    public static final int FRONTIER = 2;
    public static final int SELECTED = 3;
    
    
}
