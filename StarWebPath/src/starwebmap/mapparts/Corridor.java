/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starwebmap.mapparts;

import starwebmap.Biotype;
import starwebmap.oergi.Element;
import starwebmap.oergi.ElementMovability;
import starwebmap.oergi.Movability;
import starwebmap.oergi.Oergi;

/**
 *
 * @author Cherubi
 */

public class Corridor extends Space {
	private double length;
	private boolean matOnTheFloor;
	//private Mat mat;
	private int jumpheight;
	
	public Corridor(int id) {
		this(id, 0);
	}
	
	public Corridor(int id, double length) {
		super(id, 0);
		this.length = length;
		this.jumpheight = 1;
	}
	
	public double giveLength() {
		return length;
	}
	
	public boolean setLength(double length) {
		if (length > 0) {
			this.length = length;
			return true;
		}
		else {
			return false;
		}
	}
	
	public int giveJumpheigth() {
		return jumpheight;
	}
	
	public boolean setJumpheigth(int jumpheigth) {
		if (jumpheight > 0 && jumpheight < 4) {
			this.jumpheight = jumpheight;
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public boolean gainPassage(Oergi oergi) {
		Element element = oergi.getElement();
		ElementMovability elementMovability = new ElementMovability();
		Movability movability = elementMovability.getMovability(this.getBiotype(), element);
		
		if (movability == Movability.NO) {
			return false;
		}
		else if (movability == Movability.LIMITED) {
			if (this.getBiotype() == Biotype.CLOUD || this.getBiotype() == Biotype.TREE) {
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
	
	public boolean getMat() {
		return matOnTheFloor;
	}
	
	public boolean putMat(double length/*, Mat mat*/) {
		if (length >= this.length && !matOnTheFloor) {
			matOnTheFloor = true;
			//this.mat = mat;
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean takeMat() {
		if (matOnTheFloor) {
			matOnTheFloor = !matOnTheFloor;
			return true;
		}
		else {
			return false;
		}
	}
	
	public void clean(double time) {
		if (time <= 0) {
			return;
		}
		
		this.setDirtiness(this.getDirtiness() - time/length);
	}
	
	public double getTravelingLength() {
		if (matOnTheFloor) {
			//if the mat gets dirty it starts slow down faster
			return length * (1 + this.getDirtiness()) * (0.8 * Math.pow((1 + this.getDirtiness()), 2) );
		}
		else {
			return length * (1 + this.getDirtiness());
		}
	}
	
	public double getTravelingLength(Oergi oergi) {
		if (this.gainPassage(oergi)) {
			return this.giveLength();
		}
		else {
			return Integer.MAX_VALUE / 3;
		}
	}
}
