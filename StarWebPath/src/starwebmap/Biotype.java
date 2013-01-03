/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starwebmap;

/**
 *
 * @author Cherubi
 */

public enum Biotype {
	NONE(0), CAVE(1), SNOW(2), CLOUD(3), TREE(4), SAND(5), COAST(6), SEA(7);
	
	private int id;
	
	private Biotype(int id) {
		this.id = id;
	}
	
	/**
	 * Returns the id of the biotype.
	 * 
	 * @return Biotype id
	 */
	public int getId() {
		return this.id;
	}
}
