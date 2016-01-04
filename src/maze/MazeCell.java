package maze;

import util.Direction;

public class MazeCell {
    
    private boolean[] directions = new boolean[6];
    
    public boolean inMaze = false;
    
    public void addDirection(Direction dir) {
        
        directions[dir.dir] = true;
        
    }
    
}
