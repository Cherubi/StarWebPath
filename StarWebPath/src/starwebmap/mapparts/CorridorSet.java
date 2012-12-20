/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starwebmap.mapparts;

import java.util.ArrayList;

/**
 *
 * @author Cherubi
 */

public class CorridorSet {
	private Corridor[] corridors;
	private int onGoingId;
	//corridors have odd numbers
	
	public CorridorSet(String savefile, int savesize) {
		this.corridors = new Corridor[savesize];
		
		String[] corridorList = savefile.split("\n");
		for (int i=0; i<corridorList.length; i++) {
			if (corridorList[i].split(" ").length == 4) {
				makeCorridor(corridorList[i]);
			}
			else {
				makeOuterCorridor(corridorList[i]);
			}
		}
		
		findFreeId();
	}
	
	private void makeCorridor(String information) {
		String[] tidbit = information.split(" ");
		
		int id = Integer.parseInt(tidbit[0]);
		double length = Double.parseDouble(tidbit[1]);
		int jumpheight = Integer.parseInt(tidbit[2]);
		double matLength = Double.parseDouble(tidbit[3]);
		
		Corridor newCorridor = new Corridor(id, length);
		newCorridor.setJumpheigth(jumpheight);
		newCorridor.putMat(matLength);
		
		corridors[(id-1)/2] = newCorridor;
	}
	
	private void makeOuterCorridor(String information) {
		String[] tidbit = information.split(" ");
		
		int id = Integer.parseInt(tidbit[0]);
		double length = Double.parseDouble(tidbit[1]);
		int jumpheight = Integer.parseInt(tidbit[2]);
		
		OuterCorridor newCorridor = new OuterCorridor(id, length, jumpheight);
		
		corridors[(id-1)/2] = newCorridor;
	}
	
	private void findFreeId() {
		for (int i=0; i<corridors.length; i++) {
			if (corridors[i] == null) { //check if null's have to be put
				onGoingId = i;
				return;
			}
		}
		
		onGoingId = corridors.length;
		doubleSize();
	}
	
	private void doubleSize() {
		Corridor[] newVersionOfCorridors = new Corridor[corridors.length*2];
		
		for (int i=0; i<corridors.length; i++) {
			newVersionOfCorridors[i] = corridors[i];
		}
		
		corridors = newVersionOfCorridors;
	}
	
	public void addCorridor(Corridor corridor) {
		if (onGoingId == corridors.length) {
			doubleSize();
		}
		
		corridors[onGoingId] = corridor;
		onGoingId++;
	}
	
	public void removeCorridor(int id) {
		if (id < 0 || id >= corridors.length) {
			return;
		}
		
		corridors[id] = null;
	}
	
	public Corridor getCorridor(int id) {
		if (id < 0 || id >= corridors.length) {
			return null;
		}
		
		return corridors[id];
	}
	
	public String saveString() {
		String savefile = "";
		for (int i=0; i<onGoingId; i++) {
			Corridor corridor = corridors[i];
			if (corridor != null) {
				savefile += (i*2+1) + " " + corridor.giveLength() + " " + corridor.giveJumpheigth() + "\n";
			}
		}
		
		return savefile;
	}
}
