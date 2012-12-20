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

public class Room extends Space {
	private ArrayList<Integer> paths;
	private ArrayList<Integer> neighboringSpaces;
	
	public Room(int id, int size) {
		super(id, size);
		
		paths = new ArrayList<Integer>();
		neighboringSpaces = new ArrayList<Integer>();
	}
	
	/**
	 * Adds a neighbor to the room along a path.
	 * 
	 * @param pathId The id of the path used.
	 * @param spaceId The id of the neighbor.
	 */
	public void addNeighbor(int pathId, int spaceId) {
		paths.add(pathId);
		neighboringSpaces.add(spaceId);
	}
	
	/**
	 * Returns the neighbors that have been added for the room.
	 * 
	 * @return A list of the neighboring rooms' id's.
	 */
	public ArrayList<Integer> getNeighbors() {
		return neighboringSpaces;
	}
	
	/**
	 * Returns the paths that lead to the room's added neighbors.
	 * 
	 * @return A list of the paths leading to neighboring rooms.
	 */
	public ArrayList<Integer> getPaths() {
		return paths;
	}
	
	/**
	 * Returns a fileformat of the neighbors and the paths to them.
	 * 
	 * @return Fileformat of the neighbors and the paths to them.
	 */
	public String neighborToString() {
		String string = "";
		for (int i=0; i<neighboringSpaces.size(); i++) {
			string += paths.get(i) + " ";
			string += neighboringSpaces.get(i) + " ";
		}
		return string;
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
