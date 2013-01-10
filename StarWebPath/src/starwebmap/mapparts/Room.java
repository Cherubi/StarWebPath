/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starwebmap.mapparts;

import starwebmap.mapparts.Corridor;
import java.util.ArrayList;
import starwebmap.Biotype;
import starwebmap.oergi.Element;
import starwebmap.oergi.ElementMovability;
import starwebmap.oergi.Movability;
import starwebmap.oergi.Oergi;

/**
 *
 * @author Cherubi
 */

public class Room extends VortexSpace {
	private String constallation, star;
	
	/**
	 * Takes care of defining a room in the game.
	 * 
	 * @param id Room's id
	 * @param size Room's size
	 */
	public Room(int id, int size) {
		super(id, size);
		constallation = "?";
		star = "?";
	}
	
	public boolean setName(String constallation, String star) {
		if (constallation.contains("%") || star.contains("%")) {
			return false;
		}
		this.constallation = constallation;
		this.star = star;
		return true;
	}
	
	public String getConstallation() {
		return constallation;
	}
	
	public String getStarName() {
		return star;
	}
	
	//so far isn't very testable, lack of a document is a reminder to test
	@Override
	public boolean gainPassage(Oergi oergi) {
		Element element = oergi.getElement();
		ElementMovability elementMovability = new ElementMovability();
		Movability movability = elementMovability.getMovability(this.getBiotype(), element);
		
		if (movability == Movability.NO) {
			return false;
		}
		else if (movability == Movability.LIMITED) {
			if (this.getBiotype() == Biotype.TREE) {
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
}
