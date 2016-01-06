package maze;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
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
        
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        
        g2.setColor(new Color(240, 240, 240));
        g2.fillRect(0, 0, width, height);
        
        g2.setStroke(new BasicStroke(1));
        
        int xCellSize = width/grid.length;
        int yCellSize = height/grid[0].length;
        
        Color[] colors = {Color.WHITE, Color.GRAY, Color.RED, Color.YELLOW};
        
        for (int i = 0; i < grid.length; i++) {
            
            int xLeft = i*xCellSize;
            int xRight = (i*xCellSize)+xCellSize;
            
            for (int j = 0; j < grid[0].length; j++) {
                
                int yTop = j*yCellSize;
                int yBottom = (j*yCellSize)+yCellSize;
                
                g2.setColor(colors[grid[i][j].state]);
                g2.fillRect(xLeft, yTop, xCellSize, yCellSize);
                
                g2.setColor(Color.BLACK);
                
                if (!grid[i][j].directions[0]) {
                    g2.drawLine(xLeft, yTop, xRight, yTop);
                }
                if (!grid[i][j].directions[1]) {
                    g2.drawLine(xRight, yTop, xRight, yBottom);
                }
                if (!grid[i][j].directions[2]) {
                    g2.drawLine(xLeft, yBottom, xRight, yBottom);
                }
                if (!grid[i][j].directions[3]) {
                    g2.drawLine(xLeft, yTop, xLeft, yBottom);
                }
            }
        }
        
        g.drawImage(image, x, y, null);
        g2.dispose();
        
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
