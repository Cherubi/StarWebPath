/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starwebmap.gui;

/**
 *
 * @author Cherubi
 */

public class Sphere3DConverter {
	private int centerX, centerY, radius;
	private double longitude, latitude, north;
	
	/**
	 * Converts the window coordinates into coordinates on a sphere.
	 * 
	 * @param x X-coordinate of the left side.
	 * @param y Y-coordinate of the upper side.
	 * @param diameter Diameter of the 2D sphere.
	 */
	public Sphere3DConverter(int x, int y, int diameter) {
		this.radius = diameter/2;
		this.centerX = x + radius;
		this.centerY = y + radius;
		
		this.longitude = 0.0;
		this.latitude = 0.0;
	}
	
	/**
	 * Defines the coordinates for the centermost point of the 2D sphere.
	 * 
	 * @param coordinate Coordinates of the center
	 */
	public void setCenterCoordinate(Coordinate coordinate) {
		this.longitude = coordinate.giveLongitude();
		this.latitude = coordinate.giveLatitude();
	}
	
	/**
	 * Returns the sphere coordinates from the window coordinates.
	 * 
	 * @param x X-coordinate in the window
	 * @param y Y-coordinate in the window
	 * @return Coordinates on the sphere
	 */
	public Coordinate giveMouseCoordinates(int x, int y) {
		double distanceFromCenter = Math.sqrt(Math.pow(centerX-x, 2) + Math.pow(centerY-y, 2));
		double angleFromNorth = Math.tan( (x-centerX) / (y-centerY) );
		
		//TODO
		
		return new Coordinate(0,0);
	}
	
	public int giveWindowXCoordinate(double longitude, double latitude, boolean help) {
		int xDistanceFromCenter = (int)( Math.sin(longitude-this.longitude) * radius
				* Math.cos(latitude) );
		
		if (help)
			System.out.println(xDistanceFromCenter + centerX);
		return xDistanceFromCenter + centerX;
	}
	
	public int giveWindowYCoordinate(double longitude, double latitude, boolean help) {
		double yDistanceFromLatitudeCenter = Math.sin(this.latitude)
				* Math.cos(longitude-this.longitude) * radius
				* Math.cos(latitude);
		
		double yLatitudeDistanceFromCenter = Math.cos(this.latitude)
				* radius * Math.sin(latitude);
		
		
		int yDistanceFromCenter = (int) (yLatitudeDistanceFromCenter + yDistanceFromLatitudeCenter);
		if (help)
			System.out.println(yDistanceFromCenter);
		return centerY - yDistanceFromCenter;
	}
}
