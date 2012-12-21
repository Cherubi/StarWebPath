/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starwebmap.mapparts;

/**
 *
 * @author Cherubi
 */

public class SpaceSet {
	private Space[] spaces;
	private int onGoingId;
	//rooms and yards have even numbers
	
	//TODO tests
	
	/**
	 * Opens all spaces sans corridors from a String.
	 * 
	 * @param savefile Information of the spaces sans corridors.
	 * @param savesize Amount of all the spaces.
	 */
	public SpaceSet(String savefile, int savesize) {
		this.spaces = new Space[savesize];
		
		String[] spaceList = savefile.split("\n");
		for (int i=0; i<spaceList.length; i++) {
			if (spaceList[i].split(" ")[2].matches("[\\p{ASCII}]")) {
				makeRoom(spaceList[i]);
			}
			else {
				makeYard(spaceList[i]);
			}
		}
		
		findFreeId();
	}
	
	private void makeRoom(String information) {
		String[] informationTypes = information.split("|");
		String[] tidbit = informationTypes[0].split(" ");
		String[] neighbors = informationTypes[1].split(" ");
		
		Room newRoom = makeLonelyRoom(tidbit);
		addContacts(newRoom, neighbors);
		
		spaces[newRoom.getId()/2] = newRoom;
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
		
		return new Room(id, size);
	}
	
	private void addContacts(Room room, String[] neighbors) {
		for (int i=0; i<neighbors.length; i=i+2) {
			int pathId = Integer.parseInt(neighbors[i]);
			int neighborId = Integer.parseInt(neighbors[i+1]);
			room.addNeighbor( pathId, neighborId );
		}
	}
	
	private void makeYard(String information) {
		String[] informationTypes = information.split("|");
		String[] tidbit = informationTypes[0].split(" ");
		String[] contacts = informationTypes[1].split(" ");
		
		int id = Integer.parseInt(tidbit[0]);
		int size = Integer.parseInt(tidbit[1]);
		InnerYard innerYard = new InnerYard(id, size);
		
		addContacts(innerYard, contacts);
		
		spaces[innerYard.getId()/2] = innerYard;
	}
	
	private void addContacts(InnerYard innerYard, String[] neighbors) {
		for (int i=0; i<neighbors.length; i=i+2) {
			int pathId = Integer.parseInt(neighbors[i]);
			int neighborId = Integer.parseInt(neighbors[i+1]);
			innerYard.addNeighbor( pathId, neighborId );
		}
	}
	
	private void findFreeId() {
		for (int i=spaces.length-1; i>=0; i--) {
			if (spaces[i] != null) { //check if null's have to be put
				onGoingId = i;
				return;
			}
		}
		
		onGoingId = spaces.length;
		doubleSize();
	}
	
	private void doubleSize() {
		Space[] newVersionOfSpaces = new Space[spaces.length*2];
		
		for (int i=0; i<spaces.length; i++) {
			newVersionOfSpaces[i] = spaces[i];
		}
		
		spaces = newVersionOfSpaces;
	}
	
	/**
	 * Adds a space to the set.
	 * 
	 * @param space Space to be added.
	 */
	public void addSpace(Space space) {
		if (onGoingId == spaces.length) {
			doubleSize();
		}
		
		//TODO id
		spaces[onGoingId] = space;
		onGoingId++;
	}
	
	/**
	 * Removes a Space by the id. Also removes corridors leading to it.
	 * 
	 * @param id id of the space.
	 */
	public void removeSpace(int id) {
		if (id < 0 || id >= spaces.length) {
			return;
		}
		
		//TODO remove also neighbors and paths
		//ArrayList<Integer> paths = spaces[id]
		
		spaces[id] = null;
	}
	
	/**
	 * Returns the requested space.
	 * 
	 * @param id id of the space.
	 * @return Requested space.
	 */
	public Space getSpace(int id) {
		if (id < 0 || id >= spaces.length) {
			return null;
		}
		
		return spaces[id];
	}
	
	/**
	 * Makes a String of the set to be saved.
	 * 
	 * @return String with the information.
	 */
	public String saveString() {
		String savefile = "";
		for (int i=0; i<onGoingId; i++) {
			Space space = spaces[i];
			if (space != null) {
				if (space instanceof Room) {
					Room room = (Room)space;
					savefile += room.getId() + " " + "constallation" + " " + "star" + " " + "23 10 -24 -3" + " " + room.getSize() + "|";
					savefile += room.neighborToString() + "\n";
				}
				else if (space instanceof InnerYard) {
					InnerYard yard = (InnerYard)space;
					savefile += yard.getId() + " " + "23 10 -24 -3" + " " + yard.getSize() + "|";
					savefile += yard.neighborToString() + "\n";
				}
			}
		}
		
		return savefile;
	}
}
