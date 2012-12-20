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
public class CorridorTest {
	private Corridor corridor;
	
    public CorridorTest() {
    }

    @Before
    public void setUp() {
		corridor = new Corridor(27, 3.4);
    }
	
	//TODO javadoc
	
	
	
	@Test
	public void testThatLengthlessCorridorHasLengthZero() {
		Corridor zeroCorridor = new Corridor(3);
		
		assertEquals("A lengthless corridor's length should be 0.", 0.0, zeroCorridor.getLength(), 0.0001);
	}
	
	@Test
	public void testThatCorridorsLengthIsWhatIsInitialized() {
		Corridor newCorridor = new Corridor(3, 14.3);
		assertEquals("A corridor's length should be what it's initialized with.", 14.3, newCorridor.getLength(), 0.0001);
	}
	
	@Test
	public void testThatInitializedCorridorLengthCantBeNegative() {
		Corridor newCorridor = new Corridor(3, -4.2);
		assertTrue("A corridor shoulnd't be initialized negative.", newCorridor.getLength() >= 0);
	}
	
	@Test
	public void testThatLengthIsWhatIsSet() {
		corridor.setLength(0.1);
		assertEquals("A corridor's length wasn't what it was set to.", 0.1, corridor.getLength(), 0.0001);
	}
	
	@Test
	public void testThatLengthCantBeSetToNegative() {
		corridor.setLength(-0.3);
		assertTrue("A corridor's length can't be set to negative.", corridor.getLength() >= 0);
	}
	
	@Test
	public void testThatOriginalJumpheightIsOne() {
		assertTrue("", corridor.getJumpheight() == 1);
	}
	
	@Test
	public void testThatJumpheightCanBeSet() {
		corridor.setJumpheight(3);
		assertTrue("Jumpheight 3 should have been set on the corridor." + corridor.getJumpheight(), corridor.getJumpheight() == 3);
	}
	
	@Test
	public void testThatJumpheightCannotBeSetToABadValue() {
		corridor.setJumpheight(0);
		assertTrue("Jumpheight can't be 0 or smaller.", corridor.getJumpheight() < 4 && corridor.getJumpheight() > 0);
		
		corridor.setJumpheight(4);
		assertTrue("Jumpheight can't be 4 or larger.", corridor.getJumpheight() < 4 && corridor.getJumpheight() > 0);
	}
	
	@Test
	public void testsThatThereIsNoMatAfterInitialization() {
		assertFalse("Corridor shouldn't have a mat after initialization.", corridor.getMat());
	}
	
	@Test
	public void testsThatAMatCanBeSet() {
		corridor.setMat(0.1 + corridor.getLength());
		assertTrue("After setting a long enough mat the corridor should have a mat.", corridor.getMat());
	}
	
	@Test
	public void testsThatAMatCanBeTakenAway() {
		corridor.removeMat();
		assertFalse("After removing a mat there should be no mat.", corridor.getMat());
	}
	
	@Test
	public void testsThatATooShortMatCannotBeSet() {
		corridor.setMat(corridor.getLength() - 0.1);
		assertFalse("Setting a too short mat on the corridor shouldn't be possible.", corridor.getMat());
	}
	
	@Test
	public void testsThatATooShortMatDoesntRemoveAMat() {
		corridor.setMat(0.1 + corridor.getLength());
		corridor.setMat(corridor.getLength() - 0.1);
		assertTrue("Setting a too short mat on the corridor doesn't remove a mat that's already on the floor.", corridor.getMat());
	}
}