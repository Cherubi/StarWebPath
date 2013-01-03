/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package starwebmap.oergi;

/**
 *
 * @author Usagi-chan
 */
public enum Element {
	TREE(1), PLANT(2), EVERGREEN(3), WATERPLANT(4), FISH(5), SEAL(6), 
	CRUSTACEAN(7), BUG(8), INSECT(9), GHOST(10), ANIMAL(11),
	BIRD(12), WATERBIRD(13), WATERREPTILE(14), REPTILE(15), MUSHROOM(16),
	AIR(17), ANIMATED(18), 
	GOLGEDA(19), JAEGEEN(20), NEIREN(21);
	
	private int id;
	
	private Element(int id) {
		this.id = id;
	}
	
	/**
	 * Gives the id of the element.
	 * 
	 * @return 
	 */
	public int getId() {
		return this.id;
	}
}
