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
		this.smallerDimension = Math.min(this.getWidth(), this.getHeight()) - 60;
		
		this.sphereConverter = new Sphere3DConverter(x, y, smallerDimension);
		this.sphereConverter.setCenterCoordinate(new Coordinate(0, 0.785));
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
		g.fillOval(x, y, smallerDimension, smallerDimension);
		
		drawLines(g);
		drawStars(g);
		
		paintHelpText(g);
	}
	
	private void drawLines(Graphics g) {
		g.setColor(Color.YELLOW);
		for (int longitude = -90; longitude <= 90; longitude++) {
			for (int latitude = -90; latitude <= 90; latitude += 10) {
				int x = sphereConverter.giveWindowXCoordinate(longitude*Math.PI/180, latitude*Math.PI/180, false);
				int y = sphereConverter.giveWindowYCoordinate(longitude*Math.PI/180, latitude*Math.PI/180, false);
				g.drawLine(x, y, x, y);
			}
		}
		
		for (int longitude = -90; longitude <= 90; longitude += 10) {
			for (int latitude = -90; latitude <= 90; latitude++) {
				int x = sphereConverter.giveWindowXCoordinate(longitude*Math.PI/180, latitude*Math.PI/180, false);
				int y = sphereConverter.giveWindowYCoordinate(longitude*Math.PI/180, latitude*Math.PI/180, false);
				g.drawLine(x, y, x, y);
			}
		}
	}
	
	private void drawStars(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval(sphereConverter.giveWindowXCoordinate(0, 0, true)-2, sphereConverter.giveWindowYCoordinate(0, 0, true)-2, 4, 4);
		g.setColor(Color.MAGENTA);
		g.fillOval(sphereConverter.giveWindowXCoordinate(0.785, 0, true)-2, sphereConverter.giveWindowYCoordinate(0.785, 0, true)-2, 4, 4);
		g.setColor(Color.RED);
		g.fillOval(sphereConverter.giveWindowXCoordinate(0, 0.785, true)-2, sphereConverter.giveWindowYCoordinate(0, 0.785, true)-2, 4, 4);
		g.setColor(Color.GREEN);
		g.fillOval(sphereConverter.giveWindowXCoordinate(0.785, 0.785, true)-2, sphereConverter.giveWindowYCoordinate(0.785, 0.785, true)-2, 4, 4);
	}
	
	private void paintHelpText(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString(helpText, 390, 25);
	}
}
