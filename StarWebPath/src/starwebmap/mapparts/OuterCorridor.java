/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starwebmap.mapparts;

import starwebmap.mapparts.Corridor;
import starwebmap.oergi.Oergi;

/**
 *
 * @author Cherubi
 */

public class OuterCorridor extends Corridor {
	/**
	 * Outer path between two inner yards. Can only be passed by a "horse".
	 * 
	 * @param id id of the corridor.
	 * @param length Length of the corridor.
	 * @param jumpheight Height of the corridor that has to be jumped.
	 */
	public OuterCorridor(int id, double length, int jumpheight) {
		super(id, length);
		this.setJumpheight(jumpheight);
	}
	
	//TODO test, traveling length could actually be a function of the inner yards' cleanness
	
	/**
	 * Tells wheter a creature can pass the corridor.
	 * 
	 * @param oergi The creature about to pass the corridor.
	 * @return Whether creature could pass the corridor.
	 */
	@Override
	public boolean gainPassage(Oergi oergi) {
		//TODO salli vain patevat ratsut
		return false;
	}
	
	/**
	 * Outer corridors can't have a mat.
	 * 
	 * @return No mat on the corridor.
	 */
	@Override
	public boolean getMat() {
		return false;
	}
	
	/**
	 * No mat can be set on the outer corridor.
	 * 
	 * @param length Length of the mat.
	 * @return No mat on the corridor.
	 */
	@Override
	public boolean setMat(double length/*, Mat mat*/) {
		return false;
	}
	
	/**
	 * Removes a mat from the outer corridor.
	 * 
	 * @return No mat on the corridor to be removed.
	 */
	@Override
	public boolean removeMat() {
		return false;
	}
	
	/**
	 * Cleaning an outer corridor isn't possible.
	 * 
	 * @param time Time spent on cleaning.
	 */
	@Override
	public void clean(double time) {
		return;
	}
	
	/**
	 * Returns the traveling length of the corridor.
	 * 
	 * @return The traveling length of the corridor. 
	 */
	@Override
	public double getTravelingLength() {
		//TODO
		return this.getLength();
	}
	
	/**
	 * Returns the traveling length of the corridor for a specific creature.
	 * 
	 * @param oergi Creature wanting to travel the corridor.
	 * @return The length of the path for the creature.
	 */
	@Override
	public double getTravelingLength(Oergi oergi) {
		if (this.gainPassage(oergi)) {
			return this.getLength();
		}
		else {
			return Integer.MAX_VALUE / 3;
		}
	}
}
