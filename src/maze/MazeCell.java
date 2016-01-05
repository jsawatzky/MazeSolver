package maze;

import util.Direction;

public class MazeCell {
    
    public final int x, y;
    
    public boolean[] directions = new boolean[6];
    
    public boolean inMaze = false;

    public MazeCell(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    public void addDirection(Direction dir) {
        
        directions[dir.dir] = true;
        
    }
    
}
