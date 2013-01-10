/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starwebmap;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import starwebmap.gui.MapPanel;
import starwebmap.gui.Nappula;
import starwebmap.mapparts.CorridorSet;
import starwebmap.mapparts.VortexSpaceSet;
import starwebmap.mapparts.savefile.FileSaver;
import starwebmap.mapparts.savefile.SaveFileOpener;

/**
 *
 * @author Cherubi
 */
public class StarWebPath implements Runnable {
	private JFrame frame;
	private JPanel menu;
	private MapPanel map;
	
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new StarWebPath());
    }
	
	/**
	 * Initializes the program and opens the graphics window.
	 */
	@Override
	public void run() {
		this.frame = new JFrame();
		
		createComponents();
		
		this.frame.setTitle("StarWeb Path 1.0");
		this.frame.setSize(800, 600);
		this.frame.getContentPane().setBackground(new Color(30, 30, 40));
		this.frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
		this.frame.setVisible(true);
		
		defineSizeFor3DConversions();
	}
	
	private void createComponents() {
		this.frame.setLayout(new BorderLayout());
		
		map = new MapPanel(this, openVortexSpaceSet(), openCorridorSet()); //TODO
		this.frame.add(map, BorderLayout.CENTER);
		
		menu = new JPanel();
		menu.setBackground(Color.BLACK);
		menu.setLayout(new GridLayout(9,1));
		Color violet = new Color(100, 30, 255);
		Color blue = new Color(70, 70, 255);
		Color cyane = new Color(30, 100, 255);
		menu.add(makeButton("Add rooms", violet, Color.BLACK));
		menu.add(makeButton("Add corridors", violet, Color.BLACK));
		menu.add(makeButton("Add inner yards", violet, Color.BLACK));
		
		menu.add(makeButton("Define biotype", cyane, Color.BLACK));
		
		menu.add(makeButton("Remove rooms", blue, Color.BLACK));
		menu.add(makeButton("Remove corridors", blue, Color.BLACK));
		menu.add(makeButton("Remove inner yards", blue, Color.BLACK));
		
		menu.add(makeButton("Select new center.", Color.GREEN , Color.BLACK));
		
		menu.add(makeButton("Choose path", Color.RED, Color.BLACK));
		this.frame.add(menu, BorderLayout.EAST);
	}
	
	private Nappula makeButton(String text, Color textColor, Color backgroundColor) {
		Nappula button = new Nappula(text, textColor, backgroundColor);
		
		button.addActionListener(map);
		
		return button;
	}
	
	private void defineSizeFor3DConversions() {
		map.defineSizeFor3DConversions();
	}
	
	private VortexSpaceSet openVortexSpaceSet() {
		SaveFileOpener opener = new SaveFileOpener("Vortexes.txt", frame);
		return new VortexSpaceSet(opener.readFile(), opener.estimateAmount()*2);
	}
	
	private CorridorSet openCorridorSet() {
		SaveFileOpener opener = new SaveFileOpener("Corridors.txt", frame);
		return new CorridorSet(opener.readFile(), opener.estimateAmount()*2);
	}
	
	/**
	 * Saves the changes in vortexes and corridors.
	 * 
	 * @param vortexes Vortexset
	 * @param corridors Corridorset
	 */
	public void save(VortexSpaceSet vortexes, CorridorSet corridors) {
		FileSaver saver = new FileSaver(frame);
		saver.saveFile("Vortexes.txt", vortexes.saveString());
		
		saver.saveFile("Corridors.txt", corridors.saveString());
	}
}
