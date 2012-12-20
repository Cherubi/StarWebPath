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

public class Space {
	private int id;
	private int size;
	private double dirtiness;
	private Biotype biotype;
	
	/**
	 * A defined space/area/room
	 * 
	 * @param id defining integer
	 * @param size size of the space
	 */
	public Space(int id, int size) {
		this.id = id;
		this.size = size;
		this.dirtiness = 0;
		this.biotype = Biotype.NONE;
	}
	
	/**
	 * Returns the id of this object.
	 * 
	 * @return id of the object.
	 */
	public int getId() {
		return this.id;
	}
	
	/**
	 * Returns the volume of this object.
	 * 
	 * @return the volume of the object (abstract amount)
	 */
	public int getSize() {
		return this.size;
	}
	
	/**
	 * Returns the biotype of the space/room/area/etc...
	 * 
	 * @return Biotype of the space.
	 */
	public Biotype getBiotype() {
		return biotype;
	}
	
	/**
	 * Sets a biotype to the space.
	 * 
	 * @param biotype Biotype to be set.
	 */
	public void setBiotype(Biotype biotype) {
		this.biotype = biotype;
	}
	
	/**
	 * Makes the space dirtier by one unit. Cannot make the room dirtier than the maximum value.
	 */
	public void dirty() {
		if (dirtiness < 0.25) {
			dirtiness = dirtiness + 0.01;
		}
	}
	
	/**
	 * Returns the amount of dirtiness in the space in units.
	 * 
	 * @return Amount of dirt.
	 */
	public double getDirtiness() {
		return dirtiness;
	}
	
	/**
	 * Sets the dirtiness of a space. Can be used in cleaning.
	 * 
	 * @param dirtiness The new amount of dirtiness.
	 */
	protected void setDirtiness(double dirtiness) {
		this.dirtiness = dirtiness;
		if (this.dirtiness < 0) {
			this.dirtiness = 0;
		}
		else if (this.dirtiness > 0.25) {
			this.dirtiness = 0.25;
		}
	}
	
	//will tell whether the given creature can enter the space or not
	public boolean gainPassage(Oergi oergi) {
		Element element = oergi.getElement();
		ElementMovability elementMovability = new ElementMovability();
		Movability movability = elementMovability.getMovability(this.getBiotype(), element);
		
		if (movability == Movability.NO) {
			return false;
		}
		else {
			return true;
		}
	}
}
