package maze;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Maze {
    
    public final MazeCell[][][] grid;
    
    public boolean complete = false;

    public Maze(int xSize, int ySize, int zSize) {
        
        grid = new MazeCell[xSize][ySize][zSize];
        
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                for (int k = 0; k < zSize; k++)
                grid[i][j][k] = new MazeCell(i, j, k);
            }
        }
        
    }
    
    public void render(JPanel canvas, int layer) {
        
        int width = canvas.getWidth()-20;
        int height = canvas.getHeight()-20;
        
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
                
                g2.setColor(colors[grid[i][j][layer].state]);
                g2.fillRect(xLeft, yTop, xCellSize, yCellSize);
                
                g2.setColor(Color.BLACK);
                
                if (!grid[i][j][layer].directions[0]) {
                    g2.drawLine(xLeft, yTop, xRight, yTop);
                }
                if (!grid[i][j][layer].directions[1]) {
                    g2.drawLine(xRight, yTop, xRight, yBottom);
                }
                if (!grid[i][j][layer].directions[2]) {
                    g2.drawLine(xLeft, yBottom, xRight, yBottom);
                }
                if (!grid[i][j][layer].directions[3]) {
                    g2.drawLine(xLeft, yTop, xLeft, yBottom);
                }
            }
        }
        
        
        canvas.getGraphics().drawImage(image, 10, 10, null);
        g2.dispose();
        
    }
    
    public ArrayList<MazeCell> getOutOfMazeNeighbors(MazeCell cell) {
        
        ArrayList<MazeCell> neighbors = new ArrayList<>();
        
        try { if (grid[cell.x-1][cell.y][cell.z].state == MazeCell.OUT) { neighbors.add(grid[cell.x-1][cell.y][cell.z]); } } catch (IndexOutOfBoundsException e) {}
        try { if (grid[cell.x][cell.y-1][cell.z].state == MazeCell.OUT) { neighbors.add(grid[cell.x][cell.y-1][cell.z]); } } catch (IndexOutOfBoundsException e) {}
        try { if (grid[cell.x+1][cell.y][cell.z].state == MazeCell.OUT) { neighbors.add(grid[cell.x+1][cell.y][cell.z]); } } catch (IndexOutOfBoundsException e) {}
        try { if (grid[cell.x][cell.y+1][cell.z].state == MazeCell.OUT) { neighbors.add(grid[cell.x][cell.y+1][cell.z]); } } catch (IndexOutOfBoundsException e) {}
        try { if (grid[cell.x][cell.y+1][cell.z+1].state == MazeCell.OUT) { neighbors.add(grid[cell.x][cell.y+1][cell.z+1]); } } catch (IndexOutOfBoundsException e) {}
        try { if (grid[cell.x][cell.y+1][cell.z-1].state == MazeCell.OUT) { neighbors.add(grid[cell.x][cell.y+1][cell.z-1]); } } catch (IndexOutOfBoundsException e) {}
        
        return neighbors;
        
    }
    
    public ArrayList<MazeCell> getInMazeNeighbors(MazeCell cell) {
        
        ArrayList<MazeCell> neighbors = new ArrayList<>();
        
        try { if (grid[cell.x-1][cell.y][cell.z].state == MazeCell.IN) { neighbors.add(grid[cell.x-1][cell.y][cell.z]); } } catch (IndexOutOfBoundsException e) {}
        try { if (grid[cell.x][cell.y-1][cell.z].state == MazeCell.IN) { neighbors.add(grid[cell.x][cell.y-1][cell.z]); } } catch (IndexOutOfBoundsException e) {}
        try { if (grid[cell.x+1][cell.y][cell.z].state == MazeCell.IN) { neighbors.add(grid[cell.x+1][cell.y][cell.z]); } } catch (IndexOutOfBoundsException e) {}
        try { if (grid[cell.x][cell.y+1][cell.z].state == MazeCell.IN) { neighbors.add(grid[cell.x][cell.y+1][cell.z]); } } catch (IndexOutOfBoundsException e) {}
        try { if (grid[cell.x][cell.y+1][cell.z+1].state == MazeCell.IN) { neighbors.add(grid[cell.x][cell.y+1][cell.z+1]); } } catch (IndexOutOfBoundsException e) {}
        try { if (grid[cell.x][cell.y+1][cell.z-1].state == MazeCell.IN) { neighbors.add(grid[cell.x][cell.y+1][cell.z-1]); } } catch (IndexOutOfBoundsException e) {}
        
        return neighbors;
        
    }
    
}
