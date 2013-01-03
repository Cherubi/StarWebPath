/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starwebmap.oergi;

import starwebmap.Biotype;

/**
 *
 * @author Cherubi
 */

public class ElementMovability {
	private Movability[][] movability;
	
	/**
	 * Defines the movabilities of the elements in different biotypes.
	 */
	public ElementMovability() {		  //tree			  //plant			  //evergreen		  //waterplant		  //fish			  //seal			  //crustacean		  //bug				  //insect			  //ghost			  //animal			  //bird			  //waterbird		  //waterreptile	  //reptile			  //mushroom		  //air				  //animated		  //golgeda			  //jaegeen			  //neiren
		movability =  new Movability[][]{{Movability.NO     , Movability.NO     , Movability.NO     , Movability.NO     , Movability.NO     , Movability.NO     , Movability.NO     , Movability.NO     , Movability.NO     , Movability.NO     , Movability.NO     , Movability.NO     , Movability.NO     , Movability.NO     , Movability.NO     , Movability.NO     , Movability.NO     , Movability.NO     , Movability.NO     , Movability.NO     , Movability.NO     }, 
						  /*CAVE*/		 {Movability.NO     , Movability.NO     , Movability.LIMITED, Movability.NO     , Movability.NO     , Movability.LIMITED, Movability.LIMITED, Movability.LIMITED, Movability.NO     , Movability.LIMITED, Movability.LIMITED, Movability.NO     , Movability.NO     , Movability.LIMITED, Movability.YES    , Movability.LIMITED, Movability.NO     , Movability.YES    , Movability.LIMITED, Movability.LIMITED, Movability.LIMITED}, 
						  /*SNOW*/		 {Movability.YES    , Movability.NO     , Movability.YES    , Movability.NO     , Movability.NO     , Movability.YES    , Movability.NO     , Movability.NO     , Movability.NO     , Movability.LIMITED, Movability.YES    , Movability.YES    , Movability.NO     , Movability.NO     , Movability.NO     , Movability.NO     , Movability.YES    , Movability.YES    , Movability.YES    , Movability.YES    , Movability.NO     }, 
						  /*CLOUD*/		 {Movability.NO     , Movability.NO     , Movability.LIMITED, Movability.LIMITED, Movability.NO     , Movability.LIMITED, Movability.LIMITED, Movability.LIMITED, Movability.YES    , Movability.YES    , Movability.LIMITED, Movability.YES    , Movability.YES    , Movability.NO     , Movability.LIMITED, Movability.NO     , Movability.YES    , Movability.NO     , Movability.LIMITED, Movability.LIMITED, Movability.NO     }, 
						  /*TREE*/		 {Movability.YES    , Movability.YES    , Movability.YES    , Movability.LIMITED, Movability.NO     , Movability.NO     , Movability.LIMITED, Movability.YES    , Movability.YES    , Movability.YES    , Movability.YES    , Movability.YES    , Movability.LIMITED, Movability.LIMITED, Movability.YES    , Movability.YES    , Movability.YES    , Movability.YES    , Movability.YES    , Movability.YES    , Movability.LIMITED}, 
						  /*SAND*/		 {Movability.YES    , Movability.YES    , Movability.NO     , Movability.YES    , Movability.NO     , Movability.NO     , Movability.YES    , Movability.YES    , Movability.YES    , Movability.YES    , Movability.YES    , Movability.YES    , Movability.YES    , Movability.YES    , Movability.YES    , Movability.LIMITED, Movability.YES    , Movability.LIMITED, Movability.YES    , Movability.LIMITED, Movability.YES    }, 
						  /*COAST*/		 {Movability.LIMITED, Movability.LIMITED, Movability.LIMITED, Movability.YES    , Movability.YES    , Movability.YES    , Movability.YES    , Movability.LIMITED, Movability.YES    , Movability.YES    , Movability.LIMITED, Movability.LIMITED, Movability.YES    , Movability.YES    , Movability.YES    , Movability.NO     , Movability.YES    , Movability.LIMITED, Movability.YES    , Movability.LIMITED, Movability.YES},
						  /*SEA*/		 {Movability.NO     , Movability.NO     , Movability.LIMITED, Movability.NO     , Movability.YES    , Movability.LIMITED, Movability.YES    , Movability.NO     , Movability.NO     , Movability.YES    , Movability.LIMITED, Movability.NO     , Movability.NO     , Movability.YES    , Movability.LIMITED, Movability.NO     , Movability.NO     , Movability.LIMITED, Movability.YES    , Movability.NO     , Movability.YES}};
	}
	
	/**
	 * Gives the movability of a specific element in a specific biotype.
	 * 
	 * @param biotype Biotype
	 * @param element Element of the creature
	 * @return The movability
	 */
	public Movability getMovability(Biotype biotype, Element element) {
		return movability[biotype.getId()][element.getId()];
	}
}
