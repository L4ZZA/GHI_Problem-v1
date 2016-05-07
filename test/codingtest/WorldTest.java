/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codingtest;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Pietro
 */
public class WorldTest {
    
    public WorldTest() {
    }

    /**
     * Test of addRegion method, of class World.
     */
    @Test
    public void testAddRegion() {
        System.out.println("addRegion");
        World w = new World();
        boolean expResult = false;
        boolean result = w.addRegion(0, 0, 0, 0);
        assertEquals(expResult, result);
        assertEquals(w.getNumberOfRegions(), 0);
        assertEquals(w.getNumberOfRegions(), 0);
        
        expResult = true;
        result = w.addRegion(0, 0, 5, 5);
        assertEquals(expResult, result);
        assertEquals(w.getNumberOfRegions(), 1);
        assertEquals(w.getNumberOfRegions(), 1);
        
        expResult = true;
        result = w.addRegion(3, 1, 3, 3);
        assertEquals(expResult, result);
        assertEquals(w.getNumberOfRegions(), 2);
        assertEquals(w.getNumberOfRegions(), 2);
    }

    /**
     * Test of removeWalls method, of class World.
     */
    @Test
    public void testRemoveWalls() {
        System.out.println("removeWalls");
        
        World w = new World();
        boolean expResult = false;
        boolean result = w.removeWalls(0,0,0,0);
        assertEquals(expResult, result);
        
        expResult = true;
        result = w.removeWalls(0,0,2,2);
        assertEquals(expResult, result);
    }

    /**
     * Test of addDoor method, of class World.
     */
    @Test
    public void testAddDoor() {
        System.out.println("addDoor");
        World w = new World();
        boolean expResult = true;
        w.addRegion(0, 0, 5, 5);
        boolean result = w.addDoor(0, 0);
        assertEquals(expResult, result);
        assertEquals(w.getNumberOfDoors(), 1);
        
        expResult = false;
        result = w.addDoor(1, 1);
        assertEquals(expResult, result);
        assertEquals(w.getNumberOfDoors(), 1);
        
    }

    /**
     * Test of countRegions method, of class World.
     */
    @Test
    public void testCountRegions() {
        System.out.println("countRegions");
        World w = new World();
        w.countRegions();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
