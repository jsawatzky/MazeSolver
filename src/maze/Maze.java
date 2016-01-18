package maze;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Maze {
    
    public final MazeCell[][][] grid;

    private MazeCell start, end;
    
    public boolean complete = false;
    
    private JPanel canvas;
    private int layer = 0;

    public Maze(int xSize, int ySize, int zSize, JPanel canvas) {
        
        grid = new MazeCell[xSize][ySize][zSize];
        
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                for (int k = 0; k < zSize; k++) {
                    grid[i][j][k] = new MazeCell(i, j, k);
                }
            }
        }
        
        this.canvas = canvas;

        start = grid[0][0][0];
        end = grid[xSize-1][ySize-1][zSize-1];
        
    }
    
    public void render() {
        
        int width = canvas.getWidth()-20;
        int height = canvas.getHeight()-20;
        
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setStroke(new BasicStroke(4));
        
        g2.setColor(new Color(240, 240, 240));
        g2.fillRect(0, 0, width, height);
        
        g2.setStroke(new BasicStroke(1));
        
        int xCellSize = width/grid.length;
        int yCellSize = height/grid[0].length;
        
        Color[] colors = {Color.WHITE, Color.GRAY, Color.BLACK, Color.YELLOW, Color.DARK_GRAY, Color.RED};
        
        for (int i = 0; i < grid.length; i++) {
            
            int xLeft = i*xCellSize;
            int xRight = (i*xCellSize)+xCellSize;
            
            for (int j = 0; j < grid[0].length; j++) {
                
                int yTop = j*yCellSize;
                int yBottom = (j*yCellSize)+yCellSize;
                
                g2.setColor(colors[grid[i][j][layer].state]);
                g2.fillRect(xLeft, yTop, xCellSize, yCellSize);
                
                if (grid[i][j][layer].equals(start)) {
                    g2.setColor(Color.GREEN);
                    g2.fillRect(xLeft, yTop, xCellSize, yCellSize);
                } else if (grid[i][j][layer].equals(end)) {
                    g2.setColor(Color.MAGENTA);
                    g2.fillRect(xLeft, yTop, xCellSize, yCellSize);
                }
                
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
                if (grid[i][j][layer].directions[4] && !grid[i][j][layer].directions[5]) {
                    Polygon upArrow = new Polygon(new int[] {xLeft+xCellSize/2, xLeft+5*xCellSize/6, xLeft+2*xCellSize/3, xLeft+2*xCellSize/3, xLeft+xCellSize/3, xLeft+xCellSize/3, xLeft+xCellSize/6}, 
                            new int[] {yTop+yCellSize/6, yTop+yCellSize/3, yTop+yCellSize/3, yTop+5*yCellSize/6, yTop+5*yCellSize/6, yTop+yCellSize/3, yTop+yCellSize/3}, 7);
                    g2.fill(upArrow);
                }
                if (grid[i][j][layer].directions[5] && !grid[i][j][layer].directions[4]) {
                    Polygon downArrow = new Polygon(new int[] {xLeft+xCellSize/2, xLeft+5*xCellSize/6, xLeft+2*xCellSize/3, xLeft+2*xCellSize/3, xLeft+xCellSize/3, xLeft+2*xCellSize/6, xLeft+xCellSize/6}, 
                            new int[] {yTop+5*yCellSize/6, yTop+2*yCellSize/3, yTop+2*yCellSize/3, yTop+yCellSize/6, yTop+yCellSize/6, yTop+2*yCellSize/3, yTop+2*yCellSize/3}, 7);
                    g2.fill(downArrow);
                }
                if (grid[i][j][layer].directions[4] && grid[i][j][layer].directions[5]) {
                    Polygon doubleArrow = new Polygon(new int[] {xLeft+xCellSize/2, xLeft+5*xCellSize/6, xLeft+2*xCellSize/3, xLeft+2*xCellSize/3, xLeft+5*xCellSize/6, xLeft+xCellSize/2, xLeft+xCellSize/6, xLeft+xCellSize/3, xLeft+xCellSize/3, xLeft+xCellSize/6}, 
                            new int[] {yTop+yCellSize/6, yTop+yCellSize/3, yTop+yCellSize/3, yTop+2*yCellSize/3, yTop+2*yCellSize/3, yTop+5*yCellSize/6, yTop+2*yCellSize/3, yTop+2*yCellSize/3, yTop+yCellSize/3, yTop+yCellSize/3}, 10);
                    g2.fill(doubleArrow);
                }
            }
        }
        
        
        canvas.getGraphics().drawImage(image, 10, 10, null);
        canvas.getGraphics().drawString("Layer: " + layer, 0, 8);
        g2.dispose();
        
    }
    
    public ArrayList<MazeCell> getOutNeighbors(MazeCell cell) {
        
        ArrayList<MazeCell> neighbors = new ArrayList<>();
        
        try { if (grid[cell.x-1][cell.y][cell.z].state == MazeCell.OUT) { neighbors.add(grid[cell.x-1][cell.y][cell.z]); } } catch (IndexOutOfBoundsException e) {}
        try { if (grid[cell.x][cell.y-1][cell.z].state == MazeCell.OUT) { neighbors.add(grid[cell.x][cell.y-1][cell.z]); } } catch (IndexOutOfBoundsException e) {}
        try { if (grid[cell.x+1][cell.y][cell.z].state == MazeCell.OUT) { neighbors.add(grid[cell.x+1][cell.y][cell.z]); } } catch (IndexOutOfBoundsException e) {}
        try { if (grid[cell.x][cell.y+1][cell.z].state == MazeCell.OUT) { neighbors.add(grid[cell.x][cell.y+1][cell.z]); } } catch (IndexOutOfBoundsException e) {}
        try { if (grid[cell.x][cell.y][cell.z+1].state == MazeCell.OUT) { neighbors.add(grid[cell.x][cell.y][cell.z+1]); } } catch (IndexOutOfBoundsException e) {}
        try { if (grid[cell.x][cell.y][cell.z-1].state == MazeCell.OUT) { neighbors.add(grid[cell.x][cell.y][cell.z-1]); } } catch (IndexOutOfBoundsException e) {}
        
        return neighbors;
        
    }
    
    public ArrayList<MazeCell> getInNeighbors(MazeCell cell) {
        
        ArrayList<MazeCell> neighbors = new ArrayList<>();
        
        try { if (grid[cell.x-1][cell.y][cell.z].state == MazeCell.IN) { neighbors.add(grid[cell.x-1][cell.y][cell.z]); } } catch (IndexOutOfBoundsException e) {}
        try { if (grid[cell.x][cell.y-1][cell.z].state == MazeCell.IN) { neighbors.add(grid[cell.x][cell.y-1][cell.z]); } } catch (IndexOutOfBoundsException e) {}
        try { if (grid[cell.x+1][cell.y][cell.z].state == MazeCell.IN) { neighbors.add(grid[cell.x+1][cell.y][cell.z]); } } catch (IndexOutOfBoundsException e) {}
        try { if (grid[cell.x][cell.y+1][cell.z].state == MazeCell.IN) { neighbors.add(grid[cell.x][cell.y+1][cell.z]); } } catch (IndexOutOfBoundsException e) {}
        try { if (grid[cell.x][cell.y][cell.z+1].state == MazeCell.IN) { neighbors.add(grid[cell.x][cell.y][cell.z+1]); } } catch (IndexOutOfBoundsException e) {}
        try { if (grid[cell.x][cell.y][cell.z-1].state == MazeCell.IN) { neighbors.add(grid[cell.x][cell.y][cell.z-1]); } } catch (IndexOutOfBoundsException e) {}
        
        return neighbors;
        
    }
    
    public ArrayList<MazeCell> getUnvisitedNeighbors(MazeCell cell) {
        
        ArrayList<MazeCell> neighbors = new ArrayList<>();
        
        try { if (grid[cell.x-1][cell.y][cell.z].state == MazeCell.IN && grid[cell.x][cell.y][cell.z].directions[3]) { neighbors.add(grid[cell.x-1][cell.y][cell.z]); } } catch (IndexOutOfBoundsException e) {}
        try { if (grid[cell.x][cell.y-1][cell.z].state == MazeCell.IN && grid[cell.x][cell.y][cell.z].directions[0]) { neighbors.add(grid[cell.x][cell.y-1][cell.z]); } } catch (IndexOutOfBoundsException e) {}
        try { if (grid[cell.x+1][cell.y][cell.z].state == MazeCell.IN && grid[cell.x][cell.y][cell.z].directions[1]) { neighbors.add(grid[cell.x+1][cell.y][cell.z]); } } catch (IndexOutOfBoundsException e) {}
        try { if (grid[cell.x][cell.y+1][cell.z].state == MazeCell.IN && grid[cell.x][cell.y][cell.z].directions[2]) { neighbors.add(grid[cell.x][cell.y+1][cell.z]); } } catch (IndexOutOfBoundsException e) {}
        try { if (grid[cell.x][cell.y][cell.z+1].state == MazeCell.IN && grid[cell.x][cell.y][cell.z].directions[4]) { neighbors.add(grid[cell.x][cell.y][cell.z+1]); } } catch (IndexOutOfBoundsException e) {}
        try { if (grid[cell.x][cell.y][cell.z-1].state == MazeCell.IN && grid[cell.x][cell.y][cell.z].directions[5]) { neighbors.add(grid[cell.x][cell.y][cell.z-1]); } } catch (IndexOutOfBoundsException e) {}
        
        return neighbors;
        
    }
    
    public double getDistanceFromEnd(MazeCell cell) {
        
        return Math.hypot(Math.hypot(cell.x - end.x, cell.y - end.y), cell.z - end.z);
        
    }
    
    public double getDistanceBetween(MazeCell cell1, MazeCell cell2) {
        
        return Math.hypot(Math.hypot(cell1.x - cell2.x, cell1.y - cell2.y), cell1.z - cell2.z);
        
    }

    public MazeCell getStart() {
        return start;
    }

    public void setStart(MazeCell start) {
        this.start = start;
    }

    public MazeCell getEnd() {
        return end;
    }

    public void setEnd(MazeCell end) {
        this.end = end;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }
    
}
