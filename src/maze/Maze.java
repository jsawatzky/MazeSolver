package maze;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;

public class Maze {
    
    public final MazeCell[][] grid;

    public Maze(int xSize, int ySize) {
        
        grid = new MazeCell[xSize][ySize];
        
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                grid[i][j] = new MazeCell(i, j);
            }
        }
        
    }
    
    public void render(Graphics g, int x, int y, int width, int height) {
        
        g.setColor(Color.WHITE);
        g.fillRect(x, y, width, height);
        
        int xCellSize = width/grid.length;
        int yCellSize = height/grid[0].length;
        
        Color[] colors = {Color.WHITE, Color.GRAY, Color.RED};
        
        for (int i = 0; i < grid.length; i++) {
            
            int xLeft = x+i*xCellSize;
            int xRight = x+(i*xCellSize)+xCellSize;
            
            for (int j = 0; j < grid[0].length; j++) {
                
                g.setColor(colors[grid[i][j].state]);
                g.fillRect(x+i*xCellSize, y+j*yCellSize, xCellSize, yCellSize);
                
                int yTop = y+j*yCellSize;
                int yBottom = y+(j*yCellSize)+yCellSize;
                
                g.setColor(Color.BLACK);
                
                if (!grid[i][j].directions[0]) {
                    g.drawLine(xLeft, yTop, xRight, yTop);
                }
                if (!grid[i][j].directions[1]) {
                    g.drawLine(xRight, yTop, xRight, yBottom);
                }
                if (!grid[i][j].directions[2]) {
                    g.drawLine(xLeft, yBottom, xRight, yBottom);
                }
                if (!grid[i][j].directions[3]) {
                    g.drawLine(xLeft, yTop, xLeft, yBottom);
                }
            }
        }
        
    }
    
    public ArrayList<MazeCell> getOutOfMazeNeighbors(MazeCell cell) {
        
        ArrayList<MazeCell> neighbors = new ArrayList<>();
        
        try { if (grid[cell.x-1][cell.y].state == MazeCell.OUT) { neighbors.add(grid[cell.x-1][cell.y]); } } catch (IndexOutOfBoundsException e) {}
        try { if (grid[cell.x][cell.y-1].state == MazeCell.OUT) { neighbors.add(grid[cell.x][cell.y-1]); } } catch (IndexOutOfBoundsException e) {}
        try { if (grid[cell.x+1][cell.y].state == MazeCell.OUT) { neighbors.add(grid[cell.x+1][cell.y]); } } catch (IndexOutOfBoundsException e) {}
        try { if (grid[cell.x][cell.y+1].state == MazeCell.OUT) { neighbors.add(grid[cell.x][cell.y+1]); } } catch (IndexOutOfBoundsException e) {}
        
        return neighbors;
        
    }
    
    public ArrayList<MazeCell> getInMazeNeighbors(MazeCell cell) {
        
        ArrayList<MazeCell> neighbors = new ArrayList<>();
        
        try { if (grid[cell.x-1][cell.y].state == MazeCell.IN) { neighbors.add(grid[cell.x-1][cell.y]); } } catch (IndexOutOfBoundsException e) {}
        try { if (grid[cell.x][cell.y-1].state == MazeCell.IN) { neighbors.add(grid[cell.x][cell.y-1]); } } catch (IndexOutOfBoundsException e) {}
        try { if (grid[cell.x+1][cell.y].state == MazeCell.IN) { neighbors.add(grid[cell.x+1][cell.y]); } } catch (IndexOutOfBoundsException e) {}
        try { if (grid[cell.x][cell.y+1].state == MazeCell.IN) { neighbors.add(grid[cell.x][cell.y+1]); } } catch (IndexOutOfBoundsException e) {}
        
        return neighbors;
        
    }
    
}
