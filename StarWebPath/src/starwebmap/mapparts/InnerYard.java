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

public class InnerYard extends VortexSpace {
	private int yield;
	
	/**
	 * Takes care of defining the inner yard in the game.
	 * 
	 * @param id Inner yards id
	 * @param size Inner yards size
	 */
	public InnerYard(int id, int size) {
		super(id, size);
		this.yield = size*0;
	}
	
	//not yet very testable
	@Override
	public boolean gainPassage(Oergi oergi) {
		Element element = oergi.getElement();
		ElementMovability elementMovability = new ElementMovability();
		Movability movability = elementMovability.getMovability(this.getBiotype(), element);
		
		if (movability == Movability.NO) {
			return false;
		}
		else if (movability == Movability.LIMITED) {
			if (this.getBiotype() == Biotype.SNOW || this.getBiotype() == Biotype.SAND || this.getBiotype() == Biotype.COAST || this.getBiotype() == Biotype.SEA) {
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
	
	//clean
	
	//gainProfit
}
