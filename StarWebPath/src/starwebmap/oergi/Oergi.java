/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starwebmap.oergi;

/**
 *
 * @author Cherubi
 */

public class Oergi {
	private Element element;
	private double dayTravelLength;
	
	/**
	 * Takes care of defining the creature.
	 */
	public Oergi() {
		this.element = Element.ANIMAL;
		this.dayTravelLength = 14;
	}
	
	/**
	 * Returns the element of the creature.
	 * 
	 * @return Creatures element
	 */
	public Element getElement() {
		return element;
	}
	
	/**
	 * Returns the traveling lenght the creature can walk in one day.
	 * 
	 * @return Traveling length
	 */
	public double getTravelLength() {
		return dayTravelLength;
	}
}
