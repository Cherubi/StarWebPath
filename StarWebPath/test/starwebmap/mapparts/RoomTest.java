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
public class RoomTest {

    public RoomTest() {
    }
	
	
	
	/**
	 * Tests whether the given id has been set.
	 */
	@Test
	public void testId() {
		Random random = new Random();
		
		for (int i=0; i<10; i++) {
			int id = random.nextInt(10);
			Room room = new Room(id, 50);
			assertTrue("The id given by the Room was wrong.", id == room.getId() );
		}
	}
	
	/**
	 * Tests if the the given size has been set.
	 */
	@Test
	public void testSize() {
		Room room = new Room(28, 50);
		assertTrue("The size given by the Room was wrong. (50)", room.getSize() == 50);
		
		room = new Room(28, 100);
		assertTrue("The size given by the Room was wrong. (100)", room.getSize() == 100);
		
		room = new Room(28, 150);
		assertTrue("The size given by the Room was wrong. (150)", room.getSize() == 150);
	}
	
	/**
	 * Tests if a room with no added neighbors has no neighbors.
	 */
	@Test
	public void testReceivingNeighborIds() {
		Room room = new Room(28, 50);
		assertTrue("The neighbors given by the room were wrong.\nWasn't supposed to have neighbors.", room.getNeighbors().isEmpty());
	}
	
	/**
	 * Tests if a room with one added neighbor has that specific neighbor.
	 */
	@Test
	public void testAddingNeighborId() {
		Room room = new Room(28, 50);
		room.addNeighbor(13, 20);
		ArrayList<Integer> neighbors = room.getNeighbors();
		assertTrue("Adding a neighbor didn't work.", neighbors.size() == 1);
		assertTrue("The added neighbor was wrong.", neighbors.get(0) == 20);
	}
	
	/**
	 * Tests if a room with two added neighbors has just those as neighbors.
	 */
	@Test
	public void testAddingTwoNeighborId() {
		Room room = new Room(28, 50);
		room.addNeighbor(13, 20);
		room.addNeighbor(15, 22);
		ArrayList<Integer> neighbors = room.getNeighbors();
		assertTrue("Adding two neighbors didn't result in two added neighbors.", neighbors.size() == 2);
		assertTrue("The first added neighbor was wrong.", neighbors.get(0) == 20);
		assertTrue("The second added neighbor was wrong.", neighbors.get(1) == 22);
	}
	
	/**
	 * Tests if a room with no neighbors also has no paths to it.
	 */
	@Test
	public void testReceivingPathIds() {
		Room room = new Room(28, 50);
		assertTrue("The neighbors given by the room were wrong.\nWasn't supposed to have neighbors.", room.getPaths().isEmpty());
	}
	
	/**
	 * Tests if a room with one added neighbor has just that path to it.
	 */
	@Test
	public void testAddingPathId() {
		Room room = new Room(28, 50);
		room.addNeighbor(13, 20);
		ArrayList<Integer> paths = room.getPaths();
		assertTrue("Adding a neighbor didn't work.", paths.size() == 1);
		assertTrue("The added neighbor was wrong.", paths.get(0) == 13);
	}
	
	/**
	 * Tests if a room with two added neighbors has just the paths to those.
	 */
	@Test
	public void testAddingTwoPathId() {
		Room room = new Room(28, 50);
		room.addNeighbor(13, 20);
		room.addNeighbor(15, 22);
		ArrayList<Integer> paths = room.getPaths();
		assertTrue("Adding two neighbors didn't result in two added neighbors.", paths.size() == 2);
		assertTrue("The first added neighbor was wrong.", paths.get(0) == 13);
		assertTrue("The second added neighbor was wrong.", paths.get(1) == 15);
	}
	
	/**
	 * Tests if the form in which the room's neighbors is saved is correct when there are no neighbors.
	 */
	@Test
	public void testFileFormatNeighborsWithoutNeighbors() {
		Room room = new Room(28, 100);
		assertTrue("The neighbor string had non-existing data.", room.neighborToString().equals(""));
	}
	
	/**
	 * Tests if the form in which the room's neighbors is saved is correct when the room has one neighbor.
	 */
	@Test
	public void testFileFormatNeighborsWithOne() {
		Room room = new Room(28, 100);
		room.addNeighbor(13, 20);
		assertTrue("Fileformat for a neighbor string was incorrect with one neighbor added.\nFile format was " + room.neighborToString(), room.neighborToString().equals("13 20 "));
	}
	
	/**
	 * Tests if the form in which the room's neighbors is saved is correct when the room has two neighbors.
	 */
	@Test
	public void testFileFormatNeighborsWithTwo() {
		Room room = new Room(28, 100);
		room.addNeighbor(13, 20);
		room.addNeighbor(15, 22);
		assertTrue("Fileformat for a neighbor string was incorrect with two neighbors added.", room.neighborToString().equals("13 20 15 22 "));
	}
}