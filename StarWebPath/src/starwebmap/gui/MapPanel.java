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

public class MapPanel extends JPanel implements ActionListener, MouseMotionListener, MouseListener, MouseWheelListener {
	private int x, y, smallerDimension;
	private Sphere3DConverter sphereConverter;
	private String action = "", helpText;
	
	private Coordinate mouseCoordinate;
	private int mouseCoordinateX, mouseCoordinateY;
	private boolean changeInMouseCoordinates;
	
	/**
	 * A JPanel that paints a view of the map.
	 */
	public MapPanel() {
		this.x = 80;
		this.y = 30;
		this.setBackground(new Color(30, 30, 40));
		
		mouseCoordinate = new Coordinate(0,0);
		this.changeInMouseCoordinates = false;
		helpText = "Click on a button to choose an action.";
		
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		MouseMotionTimer mouseMotionTimer = new MouseMotionTimer(500, this);
	}
	
	/**
	 * Defines the size of the map circle to be as wide as high.
	 */
	public void defineSizeFor3DConversions() {
		this.smallerDimension = Math.min(this.getWidth(), this.getHeight()) - 60;
		
		this.sphereConverter = new Sphere3DConverter(x, y, smallerDimension);
		this.sphereConverter.setCenterCoordinate(new Coordinate(0.2, 0.785));
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
		this.mouseCoordinateX = me.getX();
		this.mouseCoordinateY = me.getY();
		this.changeInMouseCoordinates = true;
		executeAction();
	}
	
	private void executeAction() {
		if (action.length() == 0) {
			return;
		}
		
		if (action.contains("Add")) {
			executeAdding();
		}
		else if (action.contains("Remove")) {
			executeRemoving();
		}
		else if (action.contains("Select")) {
			executeSelecting();
		}
		else if (action.contains("Choose")) {
			executePathChoosing();
		}
	}
	
	private void executeAdding() {
		if (action.contains("rooms")) {
			
		}
		else if (action.contains("corridors")) {
			
		}
		else if (action.contains("inner yards")) {
		
		}
	}
	
	private void executeRemoving() {
		if (action.contains("rooms")) {
			
		}
		else if (action.contains("corridors")) {
			
		}
		else if (action.contains("inner yards")) {
		
		}
	}
	
	private void executeSelecting() {
	
	}
	
	private void executePathChoosing() {
		
	}

	/**
	 * Does nothing.
	 * 
	 * @param me Mouse event
	 */
	@Override
	public void mousePressed(MouseEvent me) {}

	/**
	 * Does nothing.
	 * 
	 * @param me Mouse event
	 */
	@Override
	public void mouseReleased(MouseEvent me) {}

	/**
	 * Does nothing.
	 * 
	 * @param me Mouse event
	 */
	@Override
	public void mouseEntered(MouseEvent me) {}

	/**
	 * Does nothing.
	 * 
	 * @param me Mouse event
	 */
	@Override
	public void mouseExited(MouseEvent me) {}

	/**
	 * Does nothing.
	 * 
	 * @param me Mouse event
	 */
	@Override
	public void mouseDragged(MouseEvent me) {}
	
	/**
	 * Catches the action of the mouse moving.
	 * 
	 * @param me MouseEvent
	 */
	@Override
	public void mouseMoved(MouseEvent me) {
		this.mouseCoordinateX = me.getX();
		this.mouseCoordinateY = me.getY();
		this.changeInMouseCoordinates = true;
	}
	
	/**
	 * Updates the coordinates of the mouse on the sphere on the panel.
	 */
	public void updateMouseCoordinates() {
		if (changeInMouseCoordinates) {
			this.mouseCoordinate = sphereConverter.giveMouseCoordinates(mouseCoordinateX, mouseCoordinateY);
			repaint();
		}
		this.changeInMouseCoordinates = false;
	}
	
	/**
	 * Catches the action of the mouse wheel rolling.
	 * 
	 * @param mwe Mouse wheel event
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent mwe) {
		int zoomAmount = mwe.getUnitsToScroll();
		sphereConverter.zoom(zoomAmount);
		repaint();
	}
	
	/**
	 * Paints the graphics panel.
	 * 
	 * @param g Graphics
	 */
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(Color.BLACK);
		g.fillOval(x, y, smallerDimension, smallerDimension);
		
		drawLines(g);
		drawStars(g);
		drawPaths(g);
		
		paintCoordinateText(g);
		paintHelpText(g);
	}
	
	private void drawLines(Graphics g) {
		g.setColor(Color.BLUE);
		for (int longitude = -180; longitude <= 180; longitude++) {
			for (int latitude = -90; latitude <= 90; latitude += 10) {
				int x = sphereConverter.giveWindowXCoordinate(longitude*Math.PI/180, latitude*Math.PI/180);
				int y = sphereConverter.giveWindowYCoordinate(longitude*Math.PI/180, latitude*Math.PI/180);
				g.drawLine(x, y, x, y);
			}
		}
		
		for (int longitude = -180; longitude <= 180; longitude += 10) {
			for (int latitude = -90; latitude <= 90; latitude++) {
				int x = sphereConverter.giveWindowXCoordinate(longitude*Math.PI/180, latitude*Math.PI/180);
				int y = sphereConverter.giveWindowYCoordinate(longitude*Math.PI/180, latitude*Math.PI/180);
				g.drawLine(x, y, x, y);
			}
		}
		
		sphereConverter.setWindowCoordsUpdated();
	}
	
	private void drawStars(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillOval(sphereConverter.giveWindowXCoordinate(0, 0)-2, sphereConverter.giveWindowYCoordinate(0, 0)-2, 4, 4);
		g.fillOval(sphereConverter.giveWindowXCoordinate(0.785, 0)-2, sphereConverter.giveWindowYCoordinate(0.785, 0)-2, 4, 4);
		g.fillOval(sphereConverter.giveWindowXCoordinate(0, 0.785)-2, sphereConverter.giveWindowYCoordinate(0, 0.785)-2, 4, 4);
		g.fillOval(sphereConverter.giveWindowXCoordinate(0.785, 0.785)-2, sphereConverter.giveWindowYCoordinate(0.785, 0.785)-2, 4, 4);
	}
	
	private void drawPaths(Graphics g) {
		//TODO
	}
	
	private void paintCoordinateText(Graphics g) {
		g.setColor(Color.WHITE);
		
		//int[] longitude = new int[2];
		//longitude[0] = (int)(mouseCoordinate.giveLongitude()/Math.PI * 180);
		//longitude[1] = 
		
		g.drawString(mouseCoordinate.giveLongitude()/Math.PI*180 + ", " + mouseCoordinate.giveLatitude()/Math.PI*180 , 5, 25);
	}
	
	private void paintHelpText(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString(helpText, 390, 25);
	}
}
