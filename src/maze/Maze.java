package maze;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Polygon;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import javax.swing.JPanel;

public class Maze {
    
    //Fields
    public final MazeCell[][][] grid;

    private MazeCell start, end;
    
    public boolean complete = false;
    
    private final JPanel canvas;
    private int layer = 0;

    //Constructor
    public Maze(int xSize, int ySize, int zSize, JPanel canvas) {
        
        //Create a new grid and fill it with new cells
        grid = new MazeCell[xSize][ySize][zSize];
        
        for (int i = 0; i < xSize; i++) {
            for (int j = 0; j < ySize; j++) {
                for (int k = 0; k < zSize; k++) {
                    grid[i][j][k] = new MazeCell(i, j, k);
                }
            }
        }
        
        this.canvas = canvas;

        //Set defualt start and end points
        start = grid[0][0][0];
        end = grid[xSize-1][ySize-1][zSize-1];
        
    }
    
    public void render() {
        
        //Get the dimensions of the canvas
        int width = canvas.getWidth();
        int height = canvas.getHeight();
        
        //Create a new buffered image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = (Graphics2D) image.getGraphics();
        g2.setStroke(new BasicStroke(2));
        
        //Set the background to match the window
        g2.setColor(new Color(214 ,217 ,223));
        g2.fillRect(0, 0, width, height);
        
        //Show the current layer in the top left
        g2.setColor(Color.BLACK);
        g2.drawString("Layer: " + (layer+1), 0, 10);
        
        
        //Add 10px padding all the way around the canvas
        width -= 20;
        height -= 20;
        
        //Find the maximum cell side length
        int xCellSize = width/grid.length;
        int yCellSize = height/grid[0].length;
        int cellSize = Math.min(xCellSize, yCellSize);
        
        //Find the offset need to center the maze
        int xOffset = ((width - (cellSize*grid.length)) / 2) + 10;
        int yOffset = ((height - (cellSize*grid[0].length)) / 2) + 10;
        
        //Colors for the cells
        Color[] colors = {Color.WHITE, Color.GRAY, Color.BLACK, Color.YELLOW, Color.DARK_GRAY, Color.RED};
        
        for (int i = 0; i < grid.length; i++) {
            
            //Get the x-coordinates
            int xLeft = i*cellSize + xOffset;
            int xRight = (i*cellSize)+cellSize + xOffset;
            
            for (int j = 0; j < grid[0].length; j++) {
                
                //get the y-coordinates
                int yTop = j*cellSize + yOffset;
                int yBottom = (j*cellSize)+cellSize + yOffset;
                
                //Fill the cell with the appropriate colour
                g2.setColor(colors[grid[i][j][layer].state]);
                g2.fillRect(xLeft, yTop, cellSize, cellSize);
                
                //Draw the start and end points is the maze is done generating
                if (complete) {
                    if (grid[i][j][layer].equals(start)) {
                        g2.setColor(Color.GREEN);
                        g2.fillRect(xLeft, yTop, cellSize, cellSize);
                    } else if (grid[i][j][layer].equals(end)) {
                        g2.setColor(Color.MAGENTA);
                        g2.fillRect(xLeft, yTop, cellSize, cellSize);
                    }
                }
                
                g2.setColor(Color.BLACK);
                
                //Draw the north wall
                if (!grid[i][j][layer].directions[0]) {
                    g2.drawLine(xLeft, yTop, xRight, yTop);
                }
                //Draw the east wall
                if (!grid[i][j][layer].directions[1]) {
                    g2.drawLine(xRight, yTop, xRight, yBottom);
                }
                //Draw the south wall
                if (!grid[i][j][layer].directions[2]) {
                    g2.drawLine(xLeft, yBottom, xRight, yBottom);
                }
                //Draw the west wall
                if (!grid[i][j][layer].directions[3]) {
                    g2.drawLine(xLeft, yTop, xLeft, yBottom);
                }
                if (grid[i][j][layer].directions[4] && !grid[i][j][layer].directions[5]) {
                    //Up arrow polygon
                    Polygon upArrow = new Polygon(new int[] {xLeft+cellSize/2, xLeft+5*cellSize/6, xLeft+2*cellSize/3, xLeft+2*cellSize/3, xLeft+cellSize/3, xLeft+cellSize/3, xLeft+cellSize/6}, 
                            new int[] {yTop+cellSize/6, yTop+cellSize/3, yTop+cellSize/3, yTop+5*cellSize/6, yTop+5*cellSize/6, yTop+cellSize/3, yTop+cellSize/3}, 7);
                    g2.fill(upArrow);
                }
                if (grid[i][j][layer].directions[5] && !grid[i][j][layer].directions[4]) {
                    //Down arrow polygon
                    Polygon downArrow = new Polygon(new int[] {xLeft+cellSize/2, xLeft+5*cellSize/6, xLeft+2*cellSize/3, xLeft+2*cellSize/3, xLeft+cellSize/3, xLeft+2*cellSize/6, xLeft+cellSize/6}, 
                            new int[] {yTop+5*cellSize/6, yTop+2*cellSize/3, yTop+2*cellSize/3, yTop+cellSize/6, yTop+cellSize/6, yTop+2*cellSize/3, yTop+2*cellSize/3}, 7);
                    g2.fill(downArrow);
                }
                if (grid[i][j][layer].directions[4] && grid[i][j][layer].directions[5]) {
                    //Bidirectional arrow polygon
                    Polygon doubleArrow = new Polygon(new int[] {xLeft+cellSize/2, xLeft+5*cellSize/6, xLeft+2*cellSize/3, xLeft+2*cellSize/3, xLeft+5*cellSize/6, xLeft+cellSize/2, xLeft+cellSize/6, xLeft+cellSize/3, xLeft+cellSize/3, xLeft+cellSize/6}, 
                            new int[] {yTop+cellSize/6, yTop+cellSize/3, yTop+cellSize/3, yTop+2*cellSize/3, yTop+2*cellSize/3, yTop+5*cellSize/6, yTop+2*cellSize/3, yTop+2*cellSize/3, yTop+cellSize/3, yTop+cellSize/3}, 10);
                    g2.fill(doubleArrow);
                }
            }
        }
        
        //Draw the image on the screen
        Graphics g = canvas.getGraphics();
        g.drawImage(image, 0, 0, null);
        g2.dispose();
        
    }
    
    //Reset all cells to IN state
    public void reset() {
        
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                for (int k = 0; k < grid[0][0].length; k++) {
                    grid[i][j][k].state = MazeCell.IN;
                }
            }
        }
        
    }
    
    //Finds all direct neighbors not in maze
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
    
    //Finds all direct neighbors in the maze
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
    
    //Finds all traversable neighbors that have not yet been visited
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
    
    //Gets distance from given cell to end
    public double getDistanceFromEnd(MazeCell cell) {
        
        return Math.hypot(Math.hypot(cell.x - end.x, cell.y - end.y), cell.z - end.z);
        
    }
    
    //Gets distance between to cells
    public double getDistanceBetween(MazeCell cell1, MazeCell cell2) {
        
        return Math.hypot(Math.hypot(cell1.x - cell2.x, cell1.y - cell2.y), cell1.z - cell2.z);
        
    }
    
    
    public MazeCell getCellAt(int x, int y) {
        
        //Do opposite of render to find cell
        
        x -= 10;
        y -= 10;
        
        int width = canvas.getWidth()-20;
        int height = canvas.getHeight()-20;
        
        int xCellSize = width/grid.length;
        int yCellSize = height/grid[0].length;
        
        int cellSize = Math.min(xCellSize, yCellSize);
        
        int xOffset = (width - (cellSize*grid.length)) / 2;
        int yOffset = (height - (cellSize*grid[0].length)) / 2;
        
        int gridX = (x-xOffset) / cellSize;
        int gridY = (y-yOffset) / cellSize;
        
        try {
            return grid[gridX][gridY][layer];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
        
    }

    //Getters and Setters
    
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
