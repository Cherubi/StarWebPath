/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starwebmap.mapparts;

import starwebmap.Biotype;
import starwebmap.oergi.Element;
import starwebmap.oergi.ElementMovability;
import starwebmap.oergi.Movability;
import starwebmap.oergi.Oergi;

/**
 *
 * @author Cherubi
 */

public class Corridor extends Space {
	private double length;
	private boolean matOnTheFloor;
	//private Mat mat;
	private int jumpheight;
	
	/**
	 * Makes a corridor with the given id. The length of the corridor
	 * will be 0 (as with multiple rooms and between inner yards and rooms.)
	 * 
	 * @param id Given id.
	 */
	public Corridor(int id) {
		this(id, 0);
	}
	
	/**
	 * Makes a corridor with the given id and length.
	 * 
	 * @param id Given id.
	 * @param length Corridor's length.
	 */
	public Corridor(int id, double length) {
		super(id, 0);
		if (length < 0) {
			length = 0;
		}
		this.length = length;
		this.jumpheight = 1;
	}
	
	/**
	 * Gives the corridor's length.
	 * 
	 * @return Corridor's length.
	 */
	public double getLength() {
		return length;
	}
	
	/**
	 * Sets the length of the corridor to the given positive value.
	 * 
	 * @param length Length of the corridor.
	 * @return Tells if the value was set.
	 */
	public boolean setLength(double length) {
		if (length > 0) {
			this.length = length;
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Give the outer heigth of the corridor. "Horses" jump over them.
	 * A value between 1-3.
	 * 
	 * @return Jumpheight of the corridor.
	 */
	public int getJumpheight() {
		return jumpheight;
	}
	
	/**
	 * Sets the outher height of the corridor. A value between 1-3.
	 * 
	 * @param jumpheight New jumpheight.
	 * @return Tells whether the jumpheight was set.
	 */
	public boolean setJumpheight(int jumpheight) {
		if (jumpheight > 0 && jumpheight < 4) {
			this.jumpheight = jumpheight;
			return true;
		}
		else {
			return false;
		}
	}
	
	//TODO, will tell if a certain creature has access to the corridor.
	@Override
	public boolean gainPassage(Oergi oergi) {
		Element element = oergi.getElement();
		ElementMovability elementMovability = new ElementMovability();
		Movability movability = elementMovability.getMovability(this.getBiotype(), element);
		
		if (movability == Movability.NO) {
			return false;
		}
		else if (movability == Movability.LIMITED) {
			if (this.getBiotype() == Biotype.CLOUD || this.getBiotype() == Biotype.TREE) {
				return false;
			}
			else {
				return true;
			}
		}
		else {
			return true;
		}
	}
	
	/**
	 * Tells if there is a mat in the corridor.
	 * 
	 * @return If a mat is in the corridor.
	 */
	public boolean getMat() {
		return matOnTheFloor;
	}
	
	/**
	 * Sets a mat in the corridor if the mat is at least as long as the corridor.
	 * 
	 * @param length The length of the mat.
	 * @return Whether a mat was set.
	 */
	public boolean setMat(double length/*, Mat mat*/) {
		if (length >= this.length && !matOnTheFloor) {
			matOnTheFloor = true;
			//this.mat = mat;
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Removes a mat from the corridor.
	 * 
	 * @return Whether a mat was removed.
	 */
	public boolean removeMat() {
		if (matOnTheFloor) {
			matOnTheFloor = !matOnTheFloor;
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * Spends a certain amount of time cleaning the corridor.
	 * 
	 * @param time Spent time.
	 */
	public void clean(double time) {
		if (time <= 0) {
			return;
		}
		
		this.setDirtiness(this.getDirtiness() - time/length);
	}
	
	/**
	 * Tells what the traveling length of the corridor is with the mat and the dirt.
	 * 
	 * TODO test
	 * 
	 * @return The traveling length.
	 */
	public double getTravelingLength() {
		if (matOnTheFloor) {
			//if the mat gets dirty it starts slow down faster
			return length * (1 + this.getDirtiness()) * (0.8 * Math.pow((1 + this.getDirtiness()), 2) );
		}
		else {
			return length * (1 + this.getDirtiness());
		}
	}
	
	/**
	 * Tells what the traveling length of the corridor is for a specific creature.
	 * Infinite if the creature cannot pass. Mat and dirt are accounted for.
	 * 
	 * TODO test
	 * 
	 * @param oergi The creature that is supposed to travel through the corridor.
	 * @return The traveling length of the corridor.
	 */
	public double getTravelingLength(Oergi oergi) {
		if (this.gainPassage(oergi)) {
			return this.getTravelingLength();
		}
		else {
			return Integer.MAX_VALUE / 3;
		}
	}
}
