/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starwebmap.mapparts;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;

/**
 *
 * @author Cherubi
 */
public class DirtTest {
	private Room room;
	
    public DirtTest() {
    }

    @Before
    public void setUp() {
		room = new Room(45, 100);
    }
	
	
	/**
	 * Tests that a new room is clean.
	 */
	@Test
	public void newRoomIsClean() {
		assertEquals("A new room was supposed to be clean.", room.getDirtiness(), 0.0, 0.0001);
	}
	
	/**
	 * Tests that a room can get dirtier.
	 */
	@Test
	public void roomCanGetDirty() {
		room.dirty();
		assertEquals("A once dirtied room was supposed to be dirty by one unit.", room.getDirtiness(), 0.01, 0.0001);
		
		
		room.dirty();
		assertEquals("A twice dirtied room was supposed to be dirty by one unit.", room.getDirtiness(), 0.02, 0.0001);
		
		
		room.dirty();
		assertEquals("A thrice dirtied room was supposed to be dirty by one unit.", room.getDirtiness(), 0.03, 0.0001);
	}
	
	/**
	 * Tests that a room can't get dirtier than the maximum value.
	 */
	@Test
	public void roomCannotGetDirtierThanMaximum() {
		for (int i=0; i<50; i++) {
			room.dirty();
		}
		
		assertEquals("A room wasn't supposed to get dirtier than the maximum value.", room.getDirtiness(), 0.25, 0.0001);
	}
	
	/**
	 * Tests that a room can get cleaner again.
	 */
	@Test
	public void roomCanGetCleanAgain() {
		room.setDirtiness(0.2);
		assertEquals("Setting the dirtiness to a specific value was supposed to change the dirtiness accordingly.", room.getDirtiness(), 0.2, 0.0001);
		
		room.setDirtiness(0.0);
		assertEquals("Setting the dirtiness to a specific value was supposed to change the dirtiness accordingly.", room.getDirtiness(), 0.0, 0.0001);
	}
	
	/**
	 * Tests that a room's dirtiness can't be set too high or low.
	 * In such cases the dirtiness is set to the maximum / minimum value.
	 */
	@Test
	public void dirtinessCannotBeSetTooHighOrLow() {
		room.setDirtiness(2);
		assertEquals("Setting the dirtiness to a too high value was supposed to set the maximum value.", 0.25, room.getDirtiness(), 0.0001);
		
		room.setDirtiness(-0.1);
		assertEquals("Setting the dirtiness to a too low value was supposed to set the minimum value.", 0.0, room.getDirtiness(), 0.0001);
	}
	
	/**
	 * Tests that cleaning lowers the amount of dirt.
	 */
	@Test
	public void cleaningLowersTheDirtiness() {
		Corridor corridor = new Corridor(13, 4.3);
		
		for(int i=0; i<10; i++) {
			corridor.dirty();
		}
		double dirty = corridor.getDirtiness();
		
		corridor.clean(1.2);
		assertTrue("Corridor was supposed to get cleaner when cleaned.", corridor.getDirtiness() < dirty);
	}
	
	/**
	 * Tests that spending more time on cleaning cleans more.
	 */
	@Test
	public void cleaningMoreCleansMore() {
		Corridor corridorLazy = new Corridor(13, 4.3);
		Corridor corridorHardworking = new Corridor(13, 4.3);
		
		for (int i=0; i<10; i++) {
			corridorLazy.dirty();
			corridorHardworking.dirty();
		}
		
		corridorLazy.clean(0.12);
		corridorHardworking.clean(0.54);
		
		assertTrue("Cleaning more was supposed to clean more.", corridorLazy.getDirtiness() > corridorHardworking.getDirtiness());
	}
	
	/**
	 * Tests that completely cleaning the place is possible.
	 */
	@Test
	public void cleaningGetsTheCorridorClean() {
		Corridor corridor = new Corridor(13, 4.3);
		corridor.setDirtiness(0.2);
		
		corridor.clean(20);
		assertEquals("Cleaning a lot was supposed to get the corridor clean.", 0.0, corridor.getDirtiness(), 0.0001);
	}
}