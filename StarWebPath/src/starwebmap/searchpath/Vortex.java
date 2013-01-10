/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starwebmap.searchpath;

import java.util.ArrayList;
import starwebmap.mapparts.VortexSpace;

/**
 *
 * @author Cherubi
 */

public class Vortex implements Comparable {
	private int id;
	private int dayDistance;
	private double dayTravel;
	private int previous;
	private ArrayList<Integer> neighbors, paths;
	
	/**
	 * Vortex of the graph includes the information of what it is,
	 * how far it is from the beginning vortex and what was the previous
	 * vortex space on the path. All previous vortex spaces are define
	 * initially as non-existing.
	 * 
	 * @param id id of the 
	 * @param dayDistance 
	 */
	public Vortex(VortexSpace vortexSpace, int dayDistance) {
		this.id = vortexSpace.getId();
		this.dayDistance = dayDistance;
		this.dayTravel = 0.0;
		this.previous = -1;
		
		neighbors = vortexSpace.getNeighbors();
		paths = vortexSpace.getPaths();
	}
	
	public int getId() {
		return id;
	}
	
	public void setPrevious(int previous) {
		this.previous = previous;
	}
	
	//TODO
	public int getPrevious() {
		return this.previous;
	}
	
	/**
	 * Returns the ids of the neighbors of a vortex space.
	 * 
	 * @return List of the ids
	 */
	public ArrayList<Integer> getNeighbors() {
		return neighbors;
	}
	
	/**
	 * Returns the ids of the paths to the neighbors of a vortex space.
	 * 
	 * @return List of the ids
	 */
	public ArrayList<Integer> getPaths() {
		return paths;
	}
	
	/**
	 * Returns the distance to the vortex from the beginning in days.
	 * 
	 * @return Amount of days
	 */
	public int getDayDistance() {
		return dayDistance;
	}
	
	/**
	 * Returns the length of the path walked that day.
	 * 
	 * @return Length of one day's journey
	 */
	public double getDayTravelDistance() {
		return dayTravel;
	}
	
	/**
	 * Sets a new distance to the vortex
	 * 
	 * @param newDayDistance Distance in days
	 * @param newDayTravelDistance Length of the path for the latest day
	 */
	public void setDistance(int newDayDistance, double newDayTravelDistance) {
		this.dayDistance = newDayDistance;
		this.dayTravel = newDayTravelDistance;
	}
	
	//todo test
	/**
	 * Compares this Vortex and another and tells which one is closer.
	 * Returns 1 if this is larger than the other one
	 * Returns -1 if this is smaller than the other one
	 * 
	 * @param other Vortex to be compared
	 * @return Information on which one was larger
	 */
	@Override
	public int compareTo(Object other) {
		if (other instanceof Vortex) {
			Vortex otherVortex = (Vortex)other;
			if (otherVortex.dayDistance > this.dayDistance) {
				return 1;
			}
			else if (otherVortex.dayDistance < this.dayDistance) {
				return -1;
			}
			else {
				if (otherVortex.dayTravel > this.dayTravel) {
					return 1;
				}
				else if (otherVortex.dayTravel < this.dayTravel) {
					return -1;
				}
				else {
					return 0;
				}
			}
		}
		return -1;
	}
}
