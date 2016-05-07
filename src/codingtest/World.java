/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codingtest;

import java.util.Scanner;

/**
 *
 * @author Pietro
 */
public class World {
    
    //dimensions
    static final int WIDTH = 8;
    static final int HEIGHT = 7;
    
    //rooms tiles
    static final String alphabet = "ABCEFGHIJKLMNOPQRSTUVXYZ";//without W & D
    
    //world elements
    private char[][] grid;
    private int numberOfRegions;
    private int numberOfDoors;
    
    
    /**
     * Create the object with the default dimensions (8 x 7)
     */
    public World()
    {
        grid = new char[HEIGHT][WIDTH];
        
        for(int i = 0; i < HEIGHT; i++)
            for(int j = 0; j < WIDTH; j++)
                grid[i][j] = '_';
        
        numberOfRegions = 0;
        numberOfDoors = 0;
    }
    
    
    
    
    
    /**
     * Create a room and add it to the world.
     * Warning: the rooms can only have the corners in common/intersecting.
     * @param x
     * @param y
     * @param width
     * @param height
     * @return true if successful
     */
    public boolean addRegion(int x, int y, int width, int height)
    {
        if(x < 0 || y < 0 || ((x + width) > WIDTH) || ((y + height) > HEIGHT))
            return false;
            
        for(int i = 0; i < height; i++)
        {
            for(int j = 0; j < width; j++)
            {
                if(i == 0 || j == 0 || i == (height - 1) || j == (width - 1))
                    grid[y + i][x + j] = 'w';
                else
                    grid[y + i][x + j] = alphabet.charAt(numberOfRegions);
            }
        }
        
        // only if is a room and not just walls
        if(width > 2 && height > 2)
            numberOfRegions++;
        
        return true;
    }
    
    
    /**
     * Remove walls in the range given starting at the position given.
     * @param x starting x coordinate
     * @param y starting y coordinate
     * @param width range X-Axis.
     * @param height range Y-Axis.
     * @return true if successful.
     */
    public boolean removeWalls(int x, int y, int width, int height)
    {
        //check bounds
        if(x < 0 || y < 0 || ((x + width) > WIDTH) || ((y + height) > HEIGHT) || (width < 0) || (height < 0))
            return false;
            
        for(int i = 0; i < height; i++)
        {
            for(int j = 0; j < width; j++)
            {
                if(grid[y+i][x+j] == 'D') 
                {
                    numberOfDoors--;
                    grid[y+i][x+j] = 'w';
                }
                
                if(grid[y+i][x+j] == 'w')
                    grid[y+i][x+j] = '_';
                    
            }
        }
        
        return true;
    }
    
    /**
     * Add a door on a wall.
     * @param x
     * @param y 
     */
    public boolean addDoor(int x, int y)
    {
        if(grid[y][x]=='w')
        {
            numberOfDoors++;
            grid[y][x]='D';
            return true;
        }
        else
            return false;
    }
    
    /**
     * Counts the regions in the world 
     */
    public void countRegions()
    {
        Scanner keyboard = new Scanner(System.in);
        numberOfRegions = 0;
        char[][] tmpGrid = new char[HEIGHT][WIDTH];
        
        for(int i = 0; i < HEIGHT; i++)
            for(int j = 0; j < WIDTH; j++)
                tmpGrid[i][j] = grid[i][j];
        
        for(int i = 0; i < HEIGHT; i++)
        {
            for(int j = 0; j < WIDTH; j++)
            {
                if(tmpGrid[i][j] == 'w' || tmpGrid[i][j] == 'D' )
                {
                    
                    int xStart = j, yStart = i;
                    int xCurrent = xStart, yCurrent = yStart;
                    
                    if((grid[yStart][xStart + 1] == 'w' || grid[yStart][xStart + 1] == 'v'))
                    {
                        tmpGrid[yCurrent][xCurrent] = 'v';
                        xCurrent++;
                    }
                    
                    boolean isAnArea = true;
                    boolean completed = false;
                    
                    int direction = 0; //0 = right; 1 = down; 2 = left; 3 = up
                    
                    while(isAnArea && !completed)
                    {                        
                        tmpGrid[yCurrent][xCurrent] = 'v';  //visited
                        /*
                        //DEBUGGING CODE
                        for(int k = 0; k < HEIGHT; k++)
                        {
                            for(int l = 0; l < WIDTH; l++)
                                System.out.print(tmpGrid[k][l]);
                            System.out.println("");
                        }
                        System.out.println(xCurrent +" "+ yCurrent+ "  "+ direction);
                        keyboard.next(); // uncomment to go step by step
                        */
                        direction = getNextNeighbour(tmpGrid, xCurrent, yCurrent, direction);
                        
                        if(direction != -1)
                        {
                            switch(direction)
                            {
                                case 0:
                                    xCurrent++;
                                    break;
                                case 1:
                                    yCurrent++;
                                    break;
                                case 2:
                                    xCurrent--;
                                    break;
                                case 3:
                                    yCurrent--;
                                    break;
                                default:
                                    System.err.println("not handled");
                                    break;
                            }
                            
                            if((xCurrent == xStart && yCurrent == yStart))
                            {
                                completed = true;
                            }
                        }
                        
                        else
                        {
                            isAnArea = false;
                        }
                    }
                    
                    if(completed)
                    {
                        numberOfRegions++;
                        System.out.println("Completed");
                    }
                }
            }
        }
    }
    
    
    /**
     * Checks what direction to take to get to the wall next to a given position.
     * @param grid the world's grid.
     * @param x coordinate.
     * @param y coordinate.
     * @param previousDirection
     * @return the direction toward the next wall will be. 0 = right; 1 = down; 2 = left; 3 = up
     */
    private int getNextNeighbour(char[][] grid, int x, int y, int previousDirection)
    {
        // going right
        if(previousDirection == 0)
        {
            // if last row
            if(y < HEIGHT-1 && (grid[y + 1][x] == 'w' || grid[y + 1][x] == 'v') && (grid[y + 1][x-1] >= alphabet.charAt(numberOfRegions) && (grid[y + 1][x-1]!='w' || grid[y + 1][x-1] != 'v')))
                return 1;
            
            // if last column
            if(x < WIDTH-1 && (grid[y][x + 1] == 'w' || grid[y][x + 1] == 'v') && (grid[y + 1][x] >= alphabet.charAt(numberOfRegions) && (grid[y + 1][x] != 'w' || grid[y + 1][x] != 'v')))
                return 0;
        }
        // going down
        else if(previousDirection == 1)
        {
            if(x > 0 && (grid[y][x - 1] == 'w' || grid[y][x - 1] == 'v')  && (grid[y - 1][x - 1] >= alphabet.charAt(numberOfRegions)  && (grid[y - 1][x - 1]!='w' || grid[y - 1][x - 1] != 'v')))
                return 2;
            
            if(y < HEIGHT - 1 && (grid[y + 1][x] == 'w' || grid[y + 1][x] == 'v')  && (grid[y][x - 1] >= alphabet.charAt(numberOfRegions)  && (grid[y][x - 1]!='w' || grid[y][x - 1] != 'v')))
                return 1;
            
            if(x < WIDTH - 1 && (grid[y][x + 1] == 'w' || grid[y][x + 1] == 'v')  && (grid[y - 1][x] >= alphabet.charAt(numberOfRegions)  && (grid[y - 1][x]!='w' || grid[y - 1][x] != 'v')))
                return 0;
        }
        //going left
        else if(previousDirection == 2)
        {
            //if next one up
            if(y > 0 && (grid[y - 1][x] == 'w' || grid[y - 1][x] == 'v') && (grid[y - 1][x + 1] >= alphabet.charAt(numberOfRegions) && (grid[y - 1][x + 1]!='w' || grid[y - 1][x + 1] != 'v')))
                return 3;
            
            //if next one left
            if(x > 0 && (grid[y][x - 1] == 'w' || grid[y][x - 1] == 'v')  && (grid[y - 1][x] >= alphabet.charAt(numberOfRegions) && (grid[y - 1][x]!='w' || grid[y - 1][x] != 'v')))
                return 2;
            
            //if last row
            if(y < HEIGHT - 1 && (grid[y + 1][x] == 'w' || grid[y + 1][x] == 'v') && (grid[y][x - 1] >= alphabet.charAt(numberOfRegions) && (grid[y][x - 1]!='w' || grid[y][x - 1] != 'v')))
                return 1;
        }
        //going up
        else if(previousDirection == 3)
        {
            
            //if last column
            if(x < WIDTH - 1 && (grid[y][x + 1] == 'w' || grid[y][x + 1] == 'v') && (grid[y + 1][x + 1] >= alphabet.charAt(numberOfRegions) && (grid[y + 1][x + 1]!='w' || grid[y + 1][x + 1] != 'v')))
                return 0;
            
            //if next one up
            if(y > 0 && (grid[y - 1][x] == 'w' || grid[y - 1][x] == 'v') && (grid[y][x + 1] >= alphabet.charAt(numberOfRegions) && (grid[y][x + 1]!='w' || grid[y][x + 1] != 'v')))
                return 3;
            
            // if next one left
            if(x > 0 && (grid[y][x - 1] == 'w' || grid[y][x - 1] == 'v') && (grid[y - 1][x - 1] >= alphabet.charAt(numberOfRegions) && (grid[y - 1][x - 1]!='w' || grid[y - 1][x - 1] != 'v')))
                return 2;
        }
        
        return -1;
    }
    
    public char[][] getGrid(){ return grid; }
    public int getNumberOfRegions(){return numberOfRegions;}
    public int getNumberOfDoors(){return numberOfDoors;}
}
