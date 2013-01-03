/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starwebmap.searchpath;

import java.util.ArrayList;
import java.util.PriorityQueue;
import starwebmap.mapparts.Corridor;
import starwebmap.mapparts.CorridorSet;
import starwebmap.mapparts.VortexSpace;
import starwebmap.mapparts.VortexSpaceSet;
import starwebmap.oergi.Oergi;

/**
 *
 * @author Cherubi
 */

public class DijkstraPath {
	private VortexSpaceSet vortexSpaces;
	private CorridorSet corridors;
	private MinimumHeap vortexHeap;
	private Vortex[] vortexes;
	private Oergi oergi;
	
	public DijkstraPath(VortexSpaceSet vortexSpaces, CorridorSet corridors) {
		this.vortexSpaces = vortexSpaces;
		this.corridors = corridors;
		this.vortexes = new Vortex[vortexSpaces.getVortexAmount()];
	}
	
	/**
	 * Returns the length of the shortest path.
	 * 
	 * @param beginning Beginning vortex's id on the web
	 * @param target Target vortex's id on the web
	 * @param oergi Creature that's supposed to make the journey
	 * @return The length of the path TODO
	 */
	public int findPath(int beginning, int target, Oergi oergi) {
		this.oergi = oergi;
		prioritizeVortexes(beginning);
		
		while (!vortexHeap.isEmpty()) {
			Vortex closestVortex = vortexHeap.remove();
			ArrayList<Integer> neighbors = closestVortex.getNeighbors();
			ArrayList<Integer> paths = closestVortex.getPaths();
			for (int i=0; i<neighbors.size(); i++) {
				relax(closestVortex, paths.get(i), neighbors.get(i));
			}
		}
		
		//TODO
		return -1;
	}
	
	private void prioritizeVortexes(int beginning) {
		this.vortexHeap = new MinimumHeap();
		
		for (VortexSpace vortexSpace : vortexSpaces) {
			int distance = -1;
			if (vortexSpace.getId() == beginning) {
				distance = 0;
			}
			else {
				distance = Integer.MAX_VALUE/3;
			}
			
			Vortex vortex = new Vortex(vortexSpace, distance);
			vortexHeap.add(vortex);
			vortexes[vortexSpace.getId()] = vortex;
			
			//TODO ending
		}
	}
	
	private void relax(Vortex closestVortex, int pathId, int neighborId) {
		double dayTravelLength = oergi.getTravelLength();
		
		Corridor corridor = corridors.getCorridor(pathId);
		if (!corridor.gainPassage(oergi)) {
			return;
		}
		
		double corridorTravelLength = corridor.getTravelingLength();
		if (corridorTravelLength > dayTravelLength) {
			return;
		}
		Vortex nextVortex = vortexes[neighborId];
		
		int newDayDistance = closestVortex.getDayDistance();
		double newDayTravelLength = closestVortex.getDayTravelDistance();
		if (newDayTravelLength + corridorTravelLength > dayTravelLength) {
			newDayDistance++;
			newDayTravelLength = corridorTravelLength;
		}
		else {
			newDayTravelLength += corridorTravelLength;
		}
		
		vortexHeap.heapDecKey(nextVortex, newDayDistance, newDayTravelLength);
	}
}
