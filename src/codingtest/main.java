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
public class main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        boolean running = true;
        World world = new World();
        do{
            // menu to le user make is choice
            System.out.println("\tWrite a command\n(WARNING: It's case sensitive)");
            System.out.println("(WARNING: Doors can be created only on top of walls)");
            System.out.println("Create a region (Syntax: 'x y height width ADD')");
            System.out.println("Remove walls (Syntax: 'x y height width  REMOVE')");
            System.out.println("Add a door (Syntax: 'x y DOOR')");
            System.out.println("Type \"EXIT\" to exit");
            // get the user input
            Scanner user_input = new Scanner(System.in);
            
            String command = user_input.nextLine();
            
            //check if the user wants to quit the program
            if(command.equals("EXIT")){
                running = false;
            }else{
                boolean result = runCommand(command,world);
                
                /*
                DEBUGGING CODE
                for(int i = 0; i < World.HEIGHT; i++)
                {
                    for(int j = 0; j < World.WIDTH; j++)
                        System.out.print(world.getGrid()[i][j]);
                    System.out.println("");
                }
                */
                System.out.println("Rooms: "+world.getNumberOfRegions());
                System.out.println("Doors: "+world.getNumberOfDoors());
                
                if(!result){
                    System.out.println("Some error occurred!");
                }
                
            }
        }while(running);
        /*
        DEBUGGING CODE
        World world = new World();
        
        //world.addRegion(1, 1, 4, 5);
        //world.addRegion(3, 1, 4, 4);
        world.addRegion(1, 1, 5, 5);
        world.addRegion(4, 2, 4, 4);
        
        for(int i = 0; i < World.HEIGHT; i++)
        {
            for(int j = 0; j < World.WIDTH; j++)
                System.out.print(world.getGrid()[i][j]);
            System.out.println("");
        }
        
        System.out.println(world.getNumberOfRegions());
         */
    }
    
    
    /**
     * Executes commands based on the command string given as input.
     * @param command String with the instruction necessary to run the operation.
     * @return true if the command has been executed.
     */
    static boolean runCommand(String command, World world)
    {
        int operation = 5;
        int doorOperation = 3;
        
        boolean completed = false;
        String[] tokens = command.split(" ");
          
        if(tokens.length == operation)
        {
            if(tokens[operation-1].equals("ADD"))
            {
                completed = world.addRegion(Integer.parseInt(tokens[0]),Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2]),Integer.parseInt(tokens[3]));
                world.countRegions();
            }
            else if(tokens[operation-1].equals("REMOVE"))
            {
                completed = world.removeWalls(Integer.parseInt(tokens[0]),Integer.parseInt(tokens[1]),Integer.parseInt(tokens[2]),Integer.parseInt(tokens[3]));
                world.countRegions();
            }
        }
        else if(tokens.length == doorOperation)
        {
            if( tokens[doorOperation-1].equals("DOOR"))
            {
                completed = world.addDoor(Integer.parseInt(tokens[0]),Integer.parseInt(tokens[1]));
            }
        }
        
        return completed;
    }
    
}
