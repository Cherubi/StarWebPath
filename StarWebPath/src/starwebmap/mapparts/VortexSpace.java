/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starwebmap.mapparts;

import java.util.ArrayList;

/**
 * Is a vortex with the ability to be a space and have neighbors on the web.
 *
 * @author Cherubi
 */

public class VortexSpace extends Space {
	private ArrayList<Integer> paths;
	private ArrayList<Integer> neighboringSpaces;
	
	/**
	 * Takes care of a space between corridors in the game. Can be either room or inner yard.
	 * 
	 * @param id id of the space
	 * @param size size of the space
	 */
	public VortexSpace(int id, int size) {
		super(id, size);
		
		paths = new ArrayList<Integer>();
		neighboringSpaces = new ArrayList<Integer>();
	}
	
	/**
	 * Adds a neighbor and the path to the vortex space.
	 * 
	 * @param pathId id of the path.
	 * @param spaceId id of the neighboring room or inner yard.
	 */
	public void addNeighbor(int pathId, int spaceId) {
		paths.add(pathId);
		neighboringSpaces.add(spaceId);
	}
	
	/**
	 * Returns the neighbors that have been added for the vortex spac.
	 * 
	 * @return A list of the neighboring rooms' and inner yards' ids.
	 */
	public ArrayList<Integer> getNeighbors() {
		return neighboringSpaces;
	}
	
	//TODO test
	/**
	 * Returns the amount of neighbors.
	 * 
	 * @return Amount of neighbors
	 */
	public int getNeighborAmount() {
		return neighboringSpaces.size();
	}
	
	/**
	 * Returns the paths that lead to the vortex space's added neighbors.
	 * 
	 * @return A list of the paths leading to neighboring rooms and inner yards.
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
}
