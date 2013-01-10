/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starwebmap.gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.JPanel;
import starwebmap.Biotype;
import starwebmap.StarWebPath;
import starwebmap.mapparts.*;
import starwebmap.oergi.Oergi;
import starwebmap.searchpath.DijkstraPath;

/**
 *
 * @author Cherubi
 */

public class MapPanel extends JPanel implements ActionListener, MouseMotionListener, MouseListener, MouseWheelListener {
	private int x, y, smallerDimension;
	private Sphere3DConverter sphereConverter;
	private String action = "", helpText;
	
	private StarWebPath main;
	private VortexSpaceSet vortexes;
	private CorridorSet corridors;
	private ArrayList<Integer> dijkstraPath = new ArrayList<Integer>();
	private long dijkstraTime;
	private int dijkstraDistance;
	
	private Coordinate mouseCoordinate;
	private int mouseCoordinateX, mouseCoordinateY;
	private boolean changeInMouseCoordinates;
	
	private int firstChosen = -1, secondChosen = -1;
	
	/**
	 * A JPanel that paints a view of the map.
	 */
	public MapPanel(StarWebPath main, VortexSpaceSet vortexes, CorridorSet corridors) {
		this.x = 80;
		this.y = 30;
		this.setBackground(new Color(30, 30, 40));
		
		this.main = main;
		this.vortexes = vortexes;
		this.corridors = corridors;
		
		mouseCoordinate = new Coordinate(0,0);
		this.changeInMouseCoordinates = false;
		helpText = "Click on a button to choose an action.";
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		MouseMotionTimer mouseMotionTimer = new MouseMotionTimer(100, this);
	}
	
	/**
	 * Defines the size of the map circle to be as wide as high.
	 */
	public void defineSizeFor3DConversions() {
		this.smallerDimension = Math.min(this.getWidth(), this.getHeight()) - 60;
		
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
		System.out.println(":" + action);
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
		executeSphereAction();
	}
	
	private void executeSphereAction() {
		if (action.length() == 0 || !onTheSphere()) {
			return;
		}
		
		if (action.contains("Add")) {
			executeAdding();
			main.save(vortexes, corridors);
		}
		else if (action.contains("Define")) {
			executeDefining();
			main.save(vortexes, corridors);
		}
		else if (action.contains("Remove")) {
			executeRemoving();
			main.save(vortexes, corridors);
		}
		else if (action.contains("Select")) {
			executeSelecting();
		}
		else if (action.contains("Choose")) {
			executePathChoosing();
		}
	}
	
	private boolean onTheSphere() {
		int centerX = x + smallerDimension/2;
		int centerY = y + smallerDimension/2;
		
		double distance = Math.pow(Math.pow(centerX-mouseCoordinateX, 2) + Math.pow(centerY-mouseCoordinateY, 2), 0.5);
		System.out.println(distance <= smallerDimension/2);
		return distance <= smallerDimension;
	}
	
	private void executeAdding() {
		if (action.contains("rooms")) {
			Coordinate coordinate = sphereConverter.giveMouseCoordinates(mouseCoordinateX, mouseCoordinateY);
			if (coordinate.giveLatitude() >= -90 && coordinate.giveLongitude() <= 90) {
				Room room = new Room(-1, 50);
				room.setLocation(coordinate.giveLongitude(), coordinate.giveLatitude());
				room.setBiotype(Biotype.SNOW);
				vortexes.addVortexSpace(room);
				
				repaint();
			}
		}
		//TODO refaktoroi
		else if (action.contains("corridors")) {
			Coordinate coordinate = sphereConverter.giveMouseCoordinates(mouseCoordinateX, mouseCoordinateY);
			int id = vortexes.getSpace(coordinate.giveLongitude(), coordinate.giveLatitude(), sphereConverter);
			if (firstChosen == -1) {
				firstChosen = id;
			}
			else {
				secondChosen = id;
				if (firstChosen != secondChosen) {
					VortexSpace first = vortexes.getSpace(firstChosen);
					VortexSpace second = vortexes.getSpace(secondChosen);
					
					double distance = sphereConverter.giveDistance(first.getLocation()[0], first.getLocation()[1], second.getLocation()[0], second.getLocation()[1]);
					Corridor corridor = new Corridor(-1, distance);
					int newId = corridors.addCorridor(corridor);
					first.addNeighbor(newId, secondChosen);
					second.addNeighbor(newId, firstChosen);
				}
				
				firstChosen = -1;
				secondChosen = -1;
			}
		}
		else if (action.contains("inner yards")) {
		
		}
	}
	
	private void executeDefining() {
		
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
		System.out.println("selecting");
		Coordinate coordinate = sphereConverter.giveMouseCoordinates(mouseCoordinateX, mouseCoordinateY);
		if (coordinate.giveLatitude() >= -90 && coordinate.giveLongitude() <= 90) {
			sphereConverter.setCenterCoordinate(coordinate);
			repaint();
		}
	}
	
	private void executePathChoosing() {
		Coordinate coordinate = sphereConverter.giveMouseCoordinates(mouseCoordinateX, mouseCoordinateY);
		int id = vortexes.getSpace(coordinate.giveLongitude(), coordinate.giveLatitude(), sphereConverter);
		if (firstChosen == -1) {
			firstChosen = id;
		}
		else {
			secondChosen = id;
			VortexSpace first = vortexes.getSpace(firstChosen);
			VortexSpace second = vortexes.getSpace(secondChosen);
			
			DijkstraPath dijkstra = new DijkstraPath(vortexes, corridors);
			long beginningTime = System.currentTimeMillis();
			dijkstraDistance = dijkstra.findPath(firstChosen, secondChosen, new Oergi());
			dijkstraTime = System.currentTimeMillis() - beginningTime;
			dijkstraPath = dijkstra.givePath();
			
			firstChosen = -1;
			secondChosen = -1;
		}
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
		drawPaths(g);
		drawStars(g);
		
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
		//g.fillOval(sphereConverter.giveWindowXCoordinate(0, 0)-2, sphereConverter.giveWindowYCoordinate(0, 0)-2, 4, 4);
		//g.fillOval(sphereConverter.giveWindowXCoordinate(0.785, 0)-2, sphereConverter.giveWindowYCoordinate(0.785, 0)-2, 4, 4);
		//g.fillOval(sphereConverter.giveWindowXCoordinate(0, 0.785)-2, sphereConverter.giveWindowYCoordinate(0, 0.785)-2, 4, 4);
		//g.fillOval(sphereConverter.giveWindowXCoordinate(0.785, 0.785)-2, sphereConverter.giveWindowYCoordinate(0.785, 0.785)-2, 4, 4);
		
		for (VortexSpace space : vortexes) {
			double[] location = space.getLocation();
			int spaceX = sphereConverter.giveWindowXCoordinate(location[0], location[1]);
			int spaceY = sphereConverter.giveWindowYCoordinate(location[0], location[1]);
			g.fillOval(spaceX-2, spaceY-2, 4, 4);
		}
	}
	
	private void drawPaths(Graphics g) {
		for (VortexSpace space : vortexes) {
			double[] location = space.getLocation();
			ArrayList<Integer> neighbors = space.getNeighbors();
			ArrayList<Integer> paths = space.getPaths();
			for (int i=0; i<neighbors.size(); i++) {
				int corridor = paths.get(i);
				g.setColor(new Color(150, 150, 160));
				if (dijkstraPath.contains(corridor)) {
					g.setColor(Color.RED);
				}
				
				double[] neighborLocation = vortexes.getSpace(neighbors.get(i)).getLocation();
				
				int x1 = sphereConverter.giveWindowXCoordinate(location[0], location[1]); 
				int y1 = sphereConverter.giveWindowYCoordinate(location[0], location[1]);
				int x2 = sphereConverter.giveWindowXCoordinate(neighborLocation[0], neighborLocation[1]);
				int y2 = sphereConverter.giveWindowYCoordinate(neighborLocation[0], neighborLocation[1]);
				if (x1>0 && y1>0 && x2>0 && y2>0) {
					g.drawLine(x1, y1, x2, y2);
				}
			}
		}
	}
	
	private void paintCoordinateText(Graphics g) {
		g.setColor(Color.WHITE);
		
		double longitude = mouseCoordinate.giveLongitude()/Math.PI*180;
		if (longitude < 0) {
			longitude = 360 + longitude;
		}
		longitude = 360 - longitude;
		int lonHour = (int)( longitude/360*24 );
		int lonMinute = (int)( (longitude/360*24 - lonHour)*60 );
		
		g.drawString(lonHour + "h " + lonMinute + "min " + ", " + String.format("%,6.2f", mouseCoordinate.giveLatitude()/Math.PI*180) + "°" , 5, 25);
	}
	
	private void paintHelpText(Graphics g) {
		g.setColor(Color.WHITE);
		g.drawString(helpText, 390, 25);
	}
}
