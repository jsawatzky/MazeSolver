package util;

public class Direction {
    
    public static final Direction NORTH = new Direction(0);
    public static final Direction EAST = new Direction(1);
    public static final Direction SOUTH = new Direction(2);
    public static final Direction WEST = new Direction(3);
    public static final Direction UP = new Direction(4);
    public static final Direction DOWN = new Direction(5);
    
    public final int dir;

    private Direction(int dir) {
        
        this.dir = dir;
        
    }
    
    public Direction getOpposite() {
        
        if (dir < 4) {
            return new Direction((dir + 2) % 4);
        } else if (dir == 4) {
            return DOWN;
        } else {
            return UP;
        }
        
    }
    
}
