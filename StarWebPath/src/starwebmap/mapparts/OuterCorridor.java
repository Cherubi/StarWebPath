/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starwebmap.mapparts;

import starwebmap.mapparts.Corridor;
import starwebmap.oergi.Oergi;

/**
 *
 * @author Cherubi
 */

public class OuterCorridor extends Corridor {
	public OuterCorridor(int id, double length, int jumpheight) {
		super(id, length);
		this.setJumpheigth(jumpheight);
	}
	
	@Override
	public boolean gainPassage(Oergi oergi) {
		//TODO salli vain patevat ratsut
		return false;
	}
	
	@Override
	public boolean getMat() {
		return false;
	}
	
	@Override
	public boolean putMat(double length/*, Mat mat*/) {
		return false;
	}
	
	@Override
	public boolean takeMat() {
		return true;
	}
	
	@Override
	public void clean(double time) {
		return;
	}
	
	@Override
	public double getTravelingLength() {
		return this.giveLength();
	}
	
	@Override
	public double getTravelingLength(Oergi oergi) {
		if (this.gainPassage(oergi)) {
			return this.giveLength();
		}
		else {
			return Integer.MAX_VALUE / 3;
		}
	}
}
