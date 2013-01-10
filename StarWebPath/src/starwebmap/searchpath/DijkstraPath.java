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
	
	private ArrayList<Integer> path;
	
	public DijkstraPath(VortexSpaceSet vortexSpaces, CorridorSet corridors) {
		this.vortexSpaces = vortexSpaces;
		this.corridors = corridors;
		this.vortexes = new Vortex[Math.max(vortexSpaces.getVortexAmount(), 2)];
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
		this.path = new ArrayList<Integer>();
		
		this.oergi = oergi;
		prioritizeVortexes(beginning);
		
		while (!vortexHeap.isEmpty()) {
			Vortex closestVortex = vortexHeap.remove();
			if (closestVortex.getId() == target) {
				backtrackPath(closestVortex);
				return closestVortex.getDayDistance() + 1;
			}
			ArrayList<Integer> neighbors = closestVortex.getNeighbors();
			ArrayList<Integer> paths = closestVortex.getPaths();
			for (int i=0; i<neighbors.size(); i++) {
				relax(closestVortex, paths.get(i), neighbors.get(i));
			}
		}
		
		return -1;
	}
	
	private void backtrackPath(Vortex closestVortex) {
		int id = closestVortex.getId();
		while (id >= 0) {
			path.add(id);
			id = vortexes[id].getPrevious();
		}
	}
	
	public ArrayList<Integer> givePath() {
		return path;
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
			while (vortexSpace.getId() >= vortexes.length) {
				doubleSize();
			}
			vortexes[vortexSpace.getId()] = vortex;
			
			//TODO ending
		}
	}
	
	private void doubleSize() {
		Vortex[] newVortexes = new Vortex[vortexes.length*2];
		for (int i=0; i<vortexes.length; i++) {
			newVortexes[i] = vortexes[i];
		}
		
		vortexes = newVortexes;
	}
	
	private void relax(Vortex closestVortex, int pathId, int neighborId) {
		double dayTravelLength = oergi.getTravelLength();
		
		Corridor corridor = corridors.getCorridor(pathId);
		if (!corridor.gainPassage(oergi)) {
			return;
		}
		
		//oergi will make sure that travel length might be infinite
		double corridorTravelLength = corridor.getTravelingLength(oergi);
		//TODO check that the target room is accessable
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
