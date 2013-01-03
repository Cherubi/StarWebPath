/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starwebmap.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import javax.swing.JPanel;

/**
 *
 * @author Cherubi
 */

public class MapPanel extends JPanel implements ActionListener, MouseMotionListener, MouseListener {
	private int x, y, smallerDimension;
	private Sphere3DConverter sphereConverter;
	private String action = "", helpText;
	private Coordinate mouseCoordinate;
	
	/**
	 * A JPanel that paints a view of the map.
	 */
	public MapPanel() {
		this.x = 80;
		this.y = 30;
		
		mouseCoordinate = new Coordinate(0,0);
		helpText = "Click on a button to choose an action.";
	}
	
	/**
	 * Defines the size of the map circle to be as wide as high.
	 */
	public void defineSizeFor3DConversions() {
		this.smallerDimension = Math.min(this.getWidth(), this.getHeight());
		
		this.sphereConverter = new Sphere3DConverter(x, y, smallerDimension);
	}

	/**
	 * Listens to the actions of what ought to be done when later the map is clicked.
	 * 
	 * @param ae Action of the buttons to the right of the map.
	 */
	@Override
	public void actionPerformed(ActionEvent ae) {
		action = ((Nappula)ae.getSource()).getText();
	}
	
	/**
	 * Catches the action of the mouse clicking somewhere.
	 * 
	 * @param me MouseEvent
	 */
	@Override
	public void mouseClicked(MouseEvent me) {
		Coordinate actionCoordinate = sphereConverter.giveMouseCoordinates(me.getX(), me.getY());
		executeAction(actionCoordinate);
	}
	
	private void executeAction(Coordinate actionCoordinate) {
		if (action.length() == 0) {
			return;
		}
		
		if (action.contains("Add")) {
			executeAdding(actionCoordinate);
		}
		else if (action.contains("Remove")) {
			executeRemoving(actionCoordinate);
		}
		else if (action.contains("Select")) {
			executeSelecting(actionCoordinate);
		}
		else if (action.contains("Choose")) {
			executePathChoosing(actionCoordinate);
		}
	}
	
	private void executeAdding(Coordinate actionCoordinate) {
		if (action.contains("rooms")) {
			
		}
		else if (action.contains("corridors")) {
			
		}
		else if (action.contains("inner yards")) {
		
		}
	}
	
	private void executeRemoving(Coordinate actionCoordinate) {
		if (action.contains("rooms")) {
			
		}
		else if (action.contains("corridors")) {
			
		}
		else if (action.contains("inner yards")) {
		
		}
	}
	
	private void executeSelecting(Coordinate actionCoordinate) {
	
	}
	
	private void executePathChoosing(Coordinate actionCoordinate) {
		
	}

	@Override
	public void mousePressed(MouseEvent me) {}

	@Override
	public void mouseReleased(MouseEvent me) {}

	@Override
	public void mouseEntered(MouseEvent me) {}

	@Override
	public void mouseExited(MouseEvent me) {}

	@Override
	public void mouseDragged(MouseEvent me) {}
	
	/**
	 * Catches the action of the mouse moving.
	 * 
	 * @param me MouseEvent
	 */
	@Override
	public void mouseMoved(MouseEvent me) {
		mouseCoordinate = sphereConverter.giveMouseCoordinates(me.getX(), me.getY());
		repaint();
	}
	
	/**
	 * Paints the graphics panel.
	 * 
	 * @param g Graphics
	 */
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.BLACK);
		g.fillOval(x, y, smallerDimension-60, smallerDimension-60);
		
		drawStars(g);
		
		paintHelpText(g);
	}
	
	private void drawStars(Graphics g) {
		
	}
	
	private void paintHelpText(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString(helpText, 390, 25);
	}
}
