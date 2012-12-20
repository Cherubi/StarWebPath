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
import starwebmap.Biotype;

/**
 *
 * @author Cherubi
 */
public class BiotypeTest {
	private Space space;
	
    public BiotypeTest() {
		space = new Space(5, 50);
    }

	
	/**
	 * Tests if the biotype of the space has been set to NONE.
	 */
	@Test
	public void testBiotypeBeforeSetting() {
		assertTrue("The biotype of the space was supposed to be yet unknown.", space.getBiotype() == Biotype.NONE);
	}
	
	/**
	 * Tests if the biotype of the room has been set to NONE.
	 */
	@Test
	public void testBiotypeOfARoomBeforeSetting() {
		Room room = new Room(5, 100);
		assertTrue("The biotype of the room was supposed to be yet unknown.", room.getBiotype() == Biotype.NONE);
	}
	
	/**
	 * Tests if the biotype of a space has been set to the wanted biotype.
	 */
	@Test
	public void testBiotypeAfterSetting() {
		space.setBiotype(Biotype.SAND);
		assertTrue("The biotype of the space was supposed to be set to sand.", space.getBiotype() == Biotype.SAND);
		
		space.setBiotype(Biotype.SNOW);
		assertTrue("The biotype of the space was supposed to be set to snow.", space.getBiotype() == Biotype.SNOW);
	}
	
	/**
	 * Tests if the biotype of a room has been set to the wanted biotype.
	 */
	@Test
	public void testBiotypeAfterSettingARoom() {
		Room room = new Room(7, 150);
		room.setBiotype(Biotype.COAST);
		assertTrue("The biotype of the space was supposed to be set to coast.", room.getBiotype() == Biotype.COAST);
		
	}
}