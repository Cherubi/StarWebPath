/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package starwebmap.gui;

/**
 *
 * @author Cherubi
 */

public class Coordinate {
	private double lon, lat;
	
	/**
	 * Contains a coordinate in radians.
	 * 
	 * @param lon Longitude in radians
	 * @param lat Latitude in radians
	 */
	public Coordinate(double lon, double lat) {
		this.lon = lon;
		this.lat = lat;
	}
	
	/**
	 * Returns the longitude of the coordinate in radians.
	 * 
	 * @return Longitude
	 */
	public double giveLongitude() {
		return this.lon;
	}
	
	/**
	 * Returns the latitude of the coordinate in radians.
	 * 
	 * @return Latitude
	 */
	public double giveLatitude() {
		return this.lat;
	}
}
