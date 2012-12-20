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

public class InnerYard extends Space {
	private int yield;
	private ArrayList<Integer> paths;
	private ArrayList<Integer> neighboringSpaces;
	
	public InnerYard(int id, int size) {
		super(id, size);
		this.yield = size*0;
		
		paths = new ArrayList<Integer>();
		neighboringSpaces = new ArrayList<Integer>();
	}
	
	/**
	 * Adds a neighbor and the path to the inner yard.
	 * 
	 * @param pathId id of the path.
	 * @param spaceId id of the neighboring room or inner yard.
	 */
	public void addNeighbor(int pathId, int spaceId) {
		paths.add(pathId);
		neighboringSpaces.add(spaceId);
	}
	
	/**
	 * Returns the neighbors of the inner yard.
	 * 
	 * @return A list of the neighboring rooms and inner yards.
	 */
	public ArrayList<Integer> getNeighbors() {
		return neighboringSpaces;
	}
	
	/**
	 * Returns the paths from the inner yard.
	 * 
	 * @return A list of the paths from the inner yard.
	 */
	public ArrayList<Integer> getPaths() {
		return paths;
	}
	
	/**
	 * Returns the fileformat of the path-neighbor list.
	 * 
	 * @return A string format path-neighbor list.
	 */
	public String neighborToString() {
		String string = "";
		for (int i=0; i<neighboringSpaces.size(); i++) {
			string += paths.get(i) + " ";
			string += neighboringSpaces.get(i) + " ";
		}
		return string;
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
}
