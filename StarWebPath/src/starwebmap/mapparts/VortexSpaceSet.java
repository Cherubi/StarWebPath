/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starwebmap.mapparts;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.NoSuchElementException;
import starwebmap.gui.Sphere3DConverter;

/**
 *
 * @author Cherubi
 */

public class VortexSpaceSet implements Iterable<VortexSpace>, Iterator<VortexSpace> {
	private VortexSpace[] vortexSpaces;
	private int count;
	//rooms and yards have even numbers
	
	//TODO tests
	
	/**
	 * Opens all spaces sans corridors from a String.
	 * 
	 * @param savefile Information of the spaces sans corridors.
	 * @param savesize Amount of all the spaces.
	 */
	public VortexSpaceSet(String savefile, int savesize) {
		this.vortexSpaces = new VortexSpace[savesize];
		System.out.println(savefile);
		
		String[] spaceList = savefile.split("\n");
		if (savefile.length() == 0) {
			return;
		}
		for (int i=0; i<spaceList.length; i++) { //last empty line not counted
			System.out.println(i + ": " + spaceList[i]);
			if (spaceList[i].split(" ")[2].matches("[\\p{ASCII}]")) {
				makeRoom(spaceList[i]);
			}
			else {
				makeYard(spaceList[i]);
			}
		}
	}
	
	private void makeRoom(String information) {
		String[] informationTypes = information.split("%");
		System.out.println(informationTypes[0]);
		String[] tidbit = informationTypes[0].split(" ");
		
		Room newRoom = makeLonelyRoom(tidbit);
		if (informationTypes.length > 1) {
			String[] neighbors = informationTypes[1].split(" ");
			addContacts(newRoom, neighbors);
			//TODO same for inner yard
		}
		
		System.out.println("id: " + newRoom.getId()/2);
		vortexSpaces[newRoom.getId()/2] = newRoom;
	}
	
	private Room makeLonelyRoom(String[] info) {
		int id = Integer.parseInt(info[0]);
		String constallation = info[1];
		String star = info[2];
		
		int xDegree = Integer.parseInt(info[3]);
		int xDegreeMinute = Integer.parseInt(info[4]);
		int yDegree = Integer.parseInt(info[5]);
		int yDegreeMinute = Integer.parseInt(info[6]);
		
		int size = Integer.parseInt(info[7]);
		
		Room room = new Room(id, size);
		room.setName(constallation, star);
		room.setPreciseLocation(xDegree, xDegreeMinute, yDegree, yDegreeMinute);
		return room;
	}
	
	private void addContacts(Room room, String[] neighbors) {
		for (int i=0; i<neighbors.length; i=i+2) {
			int pathId = Integer.parseInt(neighbors[i]);
			int neighborId = Integer.parseInt(neighbors[i+1]);
			room.addNeighbor( pathId, neighborId );
		}
	}
	
	private void makeYard(String information) {
		String[] informationTypes = information.split("%");
		String[] tidbit = informationTypes[0].split(" ");
		String[] contacts = informationTypes[1].split(" ");
		
		int id = Integer.parseInt(tidbit[0]);
		int size = Integer.parseInt(tidbit[1]);
		InnerYard innerYard = new InnerYard(id, size);
		
		addContacts(innerYard, contacts);
		
		vortexSpaces[innerYard.getId()/2] = innerYard;
	}
	
	private void addContacts(InnerYard innerYard, String[] neighbors) {
		for (int i=0; i<neighbors.length; i=i+2) {
			int pathId = Integer.parseInt(neighbors[i]);
			int neighborId = Integer.parseInt(neighbors[i+1]);
			innerYard.addNeighbor( pathId, neighborId );
		}
	}
	
	private int findFreeId() {
		for (int i=vortexSpaces.length-1; i>=0; i--) {
			if (vortexSpaces[i] == null) { //check if null's have to be put
				return i;
			}
		}
		
		int onGoingId = vortexSpaces.length;
		doubleSize();
		
		return onGoingId;
	}
	
	private void doubleSize() {
		if (vortexSpaces.length == 0) {
			vortexSpaces = new VortexSpace[2];
			return;
		}
		
		VortexSpace[] newVersionOfSpaces = new VortexSpace[vortexSpaces.length*2];
		
		for (int i=0; i<vortexSpaces.length; i++) {
			newVersionOfSpaces[i] = vortexSpaces[i];
		}
		
		vortexSpaces = newVersionOfSpaces;
	}
	
	/**
	 * Adds a space to the set.
	 * 
	 * @param space Space to be added.
	 */
	public void addVortexSpace(VortexSpace space) {
		int onGoingId = findFreeId();
		space.setId(onGoingId*2);
		vortexSpaces[onGoingId] = space;
	}
	
	/**
	 * Removes a Space by the id. Also removes corridors leading to it.
	 * 
	 * @param id id of the space.
	 */
	public void removeSpace(int id) {
		if (id < 0 || id >= vortexSpaces.length) {
			return;
		}
		
		//TODO remove also neighbors and paths
		ArrayList<Integer> paths = vortexSpaces[id].getPaths();
		ArrayList<Integer> neighbors = vortexSpaces[id].getNeighbors();
		
		vortexSpaces[id/2] = null;
	}
	
	/**
	 * Returns the requested space.
	 * 
	 * @param id id of the space.
	 * @return Requested space.
	 */
	public VortexSpace getSpace(int id) {
		if (id < 0 || id/2 >= vortexSpaces.length || id%2 != 0) {
			return null;
		}
		
		return vortexSpaces[id/2];
	}
	
	/**
	 * Returns the space closest to the asked location (in radians).
	 * 
	 * @param longitude Chosen longitude in radians
	 * @param latitude Chosen latitude in radians
	 * @return The id of the vortex space closest to the asked location
	 */
	public int getSpace(double longitude, double latitude, Sphere3DConverter sphereConverter) {
		int closest = -1;
		double closestDistance = Integer.MAX_VALUE;
		for (int i=0; i<vortexSpaces.length; i++) {
			//System.out.println("hep");
			if (vortexSpaces[i] != null) {
				//System.out.println("not null");
				double[] coords = vortexSpaces[i].getLocation();
				double distance = sphereConverter.giveDistance(longitude, latitude, coords[0], coords[1]);
				//System.out.println(distance + " > " + closestDistance);
				if (distance < closestDistance) {
					closestDistance = distance;
					closest = i*2;
				}
			}
		}
		return closest;
	}
	
	/**
	 * Returns the upper limit on how many spaces there might be.
	 * TODO test
	 * 
	 * @return Maximum amount of spaces
	 */
	public int getVortexAmount() {
		return vortexSpaces.length;
	}
	
	/**
	 * Makes a String of the set to be saved.
	 * 
	 * @return String with the information.
	 */
	public String saveString() {
		String savefile = "";
		for (int i=0; i<vortexSpaces.length; i++) {
			Space space = vortexSpaces[i];
			if (space != null) {
				if (space instanceof Room) {
					Room room = (Room)space;
					int[] location = room.getPreciseLocation();
					savefile += room.getId() + " " + room.getConstallation() + " " + room.getStarName() + " " + location[0] + " " + location[1] + " " + location[2] + " " + location[3] + " " + room.getSize() + "%";
					savefile += room.neighborToString() + "\n";
				}
				else if (space instanceof InnerYard) {
					InnerYard yard = (InnerYard)space; //TODO
					savefile += yard.getId() + " " + "23 10 -24 -3" + " " + yard.getSize() + "%";
					savefile += yard.neighborToString() + "\n";
				}
			}
		}
		
		return savefile;
	}
	
	//TEST
	/**
	 * Tells whether or not there are more vortexes to follow (for each).
	 * 
	 * @return Whether or not there are more vortexes
	 */
	@Override
	public boolean hasNext() {
		while (count < vortexSpaces.length && vortexSpaces[count] == null) {
			count++;
		}
		
		if (count < vortexSpaces.length) {
			return true;
		}
		count = 0;
		return false;
	}
	
	/**
	 * Gives the next VortexSpace in for each.
	 * 
	 * @return Next vortex space
	 */
	@Override
	public VortexSpace next() {
		if (count == vortexSpaces.length)
			throw new NoSuchElementException();
		
		count++;
		return vortexSpaces[count-1];
	}
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException("Not supported yet.");
	}

	// This method implements Iterable.
	public Iterator<VortexSpace> iterator() {
		return this;
	}
}
