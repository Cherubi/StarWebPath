/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starwebmap.mapparts;

import java.util.ArrayList;
import java.util.Random;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Cherubi
 */
public class InnerYardTest {

    public InnerYardTest() {
    }

	
	
	@Test
	public void testId() {
		Random random = new Random();
		
		for (int i=0; i<10; i++) {
			int id = random.nextInt(10);
			Room room = new Room(id, 50);
			assertTrue("The id given by the InnerYard was wrong.", id == room.getId() );
		}
	}
	
	@Test
	public void testSize() {
		InnerYard innerYard = new InnerYard(28, 5);
		assertTrue("The size given by the InnerYard was wrong. (5)", innerYard.getSize() == 5);
		
		innerYard = new InnerYard(28, 100);
		assertTrue("The size given by the InnerYard was wrong. (100)", innerYard.getSize() == 100);
		
		innerYard = new InnerYard(28, 250);
		assertTrue("The size given by the InnerYard was wrong. (250)", innerYard.getSize() == 250);
	}
	
	@Test
	public void testReceivingNeighborIds() {
		InnerYard innerYard = new InnerYard(28, 50);
		assertTrue("The neighbors given by the room were wrong.\nWasn't supposed to have neighbors.", innerYard.getNeighbors().isEmpty());
	}
	
	@Test
	public void testAddingNeighborId() {
		InnerYard innerYard = new InnerYard(28, 50);
		innerYard.addNeighbor(13, 20);
		ArrayList<Integer> neighbors = innerYard.getNeighbors();
		assertTrue("Adding a neighbor didn't work.", neighbors.size() == 1);
		assertTrue("The added neighbor was wrong.", neighbors.get(0) == 20);
	}
	
	@Test
	public void testAddingTwoNeighborId() {
		InnerYard innerYard = new InnerYard(28, 50);
		innerYard.addNeighbor(13, 20);
		innerYard.addNeighbor(15, 22);
		ArrayList<Integer> neighbors = innerYard.getNeighbors();
		assertTrue("Adding two neighbors didn't result in two added neighbors.", neighbors.size() == 2);
		assertTrue("The first added neighbor was wrong.", neighbors.get(0) == 20);
		assertTrue("The second added neighbor was wrong.", neighbors.get(1) == 22);
	}
	
	@Test
	public void testReceivingPathIds() {
		InnerYard innerYard = new InnerYard(28, 50);
		assertTrue("The neighbors given by the inner yard were wrong.\nWasn't supposed to have neighbors.", innerYard.getPaths().isEmpty());
	}
	
	@Test
	public void testAddingPathId() {
		InnerYard innerYard = new InnerYard(28, 50);
		innerYard.addNeighbor(13, 20);
		ArrayList<Integer> paths = innerYard.getPaths();
		assertTrue("Adding a neighbor didn't work.", paths.size() == 1);
		assertTrue("The added neighbor was wrong.", paths.get(0) == 13);
	}
	
	@Test
	public void testAddingTwoPathId() {
		InnerYard innerYard = new InnerYard(28, 50);
		innerYard.addNeighbor(13, 20);
		innerYard.addNeighbor(15, 22);
		ArrayList<Integer> paths = innerYard.getPaths();
		assertTrue("Adding two neighbors didn't result in two added neighbors.", paths.size() == 2);
		assertTrue("The first added neighbor was wrong.", paths.get(0) == 13);
		assertTrue("The second added neighbor was wrong.", paths.get(1) == 15);
	}
	
	@Test
	public void testFileFormatNeighborsWithoutNeighbors() {
		InnerYard innerYard = new InnerYard(28, 100);
		assertTrue("The neighbor string had non-existing data.", innerYard.neighborToString().equals(""));
	}
	
	@Test
	public void testFileFormatNeighborsWithOne() {
		InnerYard innerYard = new InnerYard(28, 100);
		innerYard.addNeighbor(13, 20);
		assertTrue("Fileformat for a neighbor string was incorrect with one neighbor added.\nFile format was " + innerYard.neighborToString(), innerYard.neighborToString().equals("13 20 "));
	}
	
	@Test
	public void testFileFormatNeighborsWithTwo() {
		InnerYard innerYard = new InnerYard(28, 100);
		innerYard.addNeighbor(13, 20);
		innerYard.addNeighbor(15, 22);
		assertTrue("Fileformat for a neighbor string was incorrect with two neighbors added.", innerYard.neighborToString().equals("13 20 15 22 "));
	}

}