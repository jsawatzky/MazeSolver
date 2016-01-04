package maze;

import java.awt.Graphics;
import javax.swing.text.GapContent;

public class Maze {
    
    public final MazeCell[][] grid;

    public Maze(int xSize, int ySize) {
        
        grid = new MazeCell[xSize][ySize];
        
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                grid[i][j] = new MazeCell();
            }
        }
        
    }
    
    public void render(Graphics g) {
        
        
        
    }
    
}
