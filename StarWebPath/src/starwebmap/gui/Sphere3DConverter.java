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
	private int centerX, centerY, radius, originalRadius;
	private double longitude, latitude;
	private double[] centerCoordinates;
	private double[][][] windowCoords;
	private boolean windowCoordsUpdated;
	
	/**
	 * Converts the window coordinates into coordinates on a sphere.
	 * 
	 * @param x X-coordinate of the left side.
	 * @param y Y-coordinate of the upper side.
	 * @param diameter Diameter of the 2D sphere.
	 */
	public Sphere3DConverter(int x, int y, int diameter) {
		this.radius = diameter/2;
		this.originalRadius = this.radius;
		this.centerX = x + radius;
		this.centerY = y + radius;
		
		this.longitude = 0.0; //in radians
		this.latitude = 0.0; //in radians
		updateCenterCoordinates();
		
		this.windowCoords = new double[36][19][2];
		this.windowCoordsUpdated = false;
	}
	
	/**
	 * Defines the coordinates for the centermost point of the 2D sphere in radians.
	 * 
	 * @param coordinate Coordinates of the center in radians
	 */
	public void setCenterCoordinate(Coordinate coordinate) {
		this.longitude = coordinate.giveLongitude();
		this.latitude = -coordinate.giveLatitude();
		this.windowCoordsUpdated = false;
		
		updateCenterCoordinates();
	}
	
	public void updateCenterCoordinates() {
		centerCoordinates = this.coordinatesInRelationToSphereCenter(this.longitude, this.latitude);
	}
	
	/**
	 * Sets window coordinates to the state "updated" when the sphere
	 * has been moved and the new xyz-coordinates have been identified
	 * from the longitude-latitude-coordinates.
	 */
	public void setWindowCoordsUpdated() {
		this.windowCoordsUpdated = true;
	}
	
	/**
	 * Zooms the sphere in and out.
	 * 
	 * @param amount Amount of zooming
	 */
	public void zoom(int amount) {
		int direction = amount / Math.abs(amount);
		
		for (int i=1; i<=Math.abs(amount); i++) {
			if (radius > originalRadius || direction > 0) {
				radius = radius + 30*direction;
			}
		}
		
		this.windowCoordsUpdated = false;
		updateCenterCoordinates();
	}
	
	/**
	 * Returns the sphere coordinates from the window coordinates.
	 * 
	 * @param x X-coordinate in the window
	 * @param y Y-coordinate in the window
	 * @return Coordinates on the sphere
	 */
	public Coordinate giveMouseCoordinates(double x, double y) {
		x = x-centerX;
		y = -(y-centerY);
		double z = Math.pow(Math.pow(radius, 2) - Math.pow(x, 2) - Math.pow(y, 2), 0.5);
		
		//rearrange longitude 0° to point towards the screen
		/*double r = Math.pow(Math.pow(x, 2) + Math.pow(z, 2), 0.5);
		double aboveAngleOfMouse = giveAngleFromFront(x, z, r);
		
		aboveAngleOfMouse = aboveAngleOfMouse - this.longitude;
		x = r * Math.sin(aboveAngleOfMouse);
		z = r * Math.cos(aboveAngleOfMouse);
		
		//rearrange latitude 0° to point towards the screen
		r = Math.pow(Math.pow(z, 2) + Math.pow(y, 2), 0.5);
		double sideAngleOfMouse = giveAngleFromFront(z, y, r);
		
		sideAngleOfMouse = sideAngleOfMouse - this.latitude;
		z = r * Math.cos(sideAngleOfMouse);
		y = r * Math.sin(sideAngleOfMouse);
		
		//double distanceFromCenter = Math.sqrt(Math.pow(centerX-x, 2) + Math.pow(centerY-y, 2));
		//double angleFromNorth = Math.tan( (x-centerX) / (y-centerY) );
		
		/*System.out.println("0, 90: " + windowCoords[0][18][0] + ", " + windowCoords[0][18][1]);
		
		double bestLon = 0, bestLat = 0, bestDistance = Integer.MAX_VALUE;
		for (int lon = 0; lon < windowCoords.length; lon++) {
			for (int lat = 0; lat < windowCoords[0].length; lat++) {
				double distance = Math.pow( Math.pow(x - windowCoords[lon][lat][0], 2) + Math.pow(y - windowCoords[lon][lat][1], 2) , 0.5 );
				if (distance < bestDistance) {
					//bestLon = lon *10.0 /180 *Math.PI;
					//bestLat = (lat *10.0 -90) /180 *Math.PI;
					bestLon = lon;
					bestLat = lat;
					bestDistance = distance;
				}
			}
		}
		System.out.println(bestDistance + ": " + bestLon + ", " + bestLat);
		*/
		
		/*System.out.println(x + ", " + y + ", " + z);*/
		//return giveCoordinates(x, y, z);
		//return giveCoordinates(scalarProjection(90, 0, x, y, z), scalarProjection(0, 90, x, y, z), scalarProjection(0, 0, x, y, z));
		
		double[] originalCoords = turn(x, y, z);
		return giveCoordinates(originalCoords[0], originalCoords[1], originalCoords[2]);
	}
	
	private double[] turn(double x, double y, double z) {
		//double xDirection = countWindowXCoordinate(0, 0)-centerX;
		//double yDirection = -(countWindowYCoordinate(0, 0)-centerY);
		//double zDirection = Math.pow(Math.pow(radius, 2) - Math.pow(xDirection, 2) - Math.pow(yDirection, 2), 0.5);
		
		//turn latitude
		double[][] turnMatrix = new double[][]{{1, 0, 0}, {0, Math.cos(latitude), -Math.sin(latitude)}, {0, Math.sin(latitude), Math.cos(latitude)}};
		double[] coords = matrix(turnMatrix, new double[]{x, y, z});
		//System.out.println(y + "->" + coords[1]);
		
		//turn longitude
		turnMatrix = new double[][]{{Math.cos(longitude), 0, Math.sin(longitude)}, {0, 1, 0}, {-Math.sin(longitude), 0, Math.cos(longitude)}};
		coords = matrix(turnMatrix, coords);
		
		return coords;
	}
	
	private double[] matrix(double[][] turn, double[] vector) {
		double[] newVector = new double[3];
		
		for (int j=0; j<3; j++) {
			double sum = 0;
			for (int i=0; i<3; i++) {
				//System.out.print(turn[j][i] + " ");
				sum += turn[j][i] * vector[i];
			}
			//System.out.println();
			newVector[j] = sum;
		}
		
		return newVector;
	}
	
	private double scalarProjection(int lon, int lat, double x, double y, double z) {
		double xDirection = countWindowXCoordinate(lon/180.0*Math.PI, lat/180.0*Math.PI)-centerX;
		double yDirection = -(countWindowYCoordinate(lon/180.0*Math.PI, lat/180.0*Math.PI)-centerY);
		double zDirection = Math.pow(Math.pow(radius, 2) - Math.pow(xDirection, 2) - Math.pow(yDirection, 2), 0.5);
		System.out.println(lon + ", " + lat + ": " + xDirection + ", " + yDirection + ", " + zDirection);
		if (!isVisible(lon, lat)) {
			zDirection = -zDirection;
		}
		
		double scalarProjection = (xDirection*x + yDirection*y + zDirection*z)/radius;
		return scalarProjection;
	}
	
	private Coordinate giveCoordinates(double x, double y, double z) {
		double latitude = Math.asin(y/radius);
		double longitude = Math.asin(x/(radius*Math.cos(latitude)));
		if (z<0) {
			longitude = Math.PI - longitude;
		}
		
		return new Coordinate(longitude, latitude);
	}
	
	private double giveAngleFromFront(double x, double z, double r) {
		double angle = Math.asin(x/r);
		
		if (z < 0) {
			angle = Math.PI - angle;
		}
		return angle;
	}
	
	/**
	 * Gives an x-coordinate on the screen for a coordinate on the sphere.
	 * Returns negative for coordinates that are on the unseen side of the sphere.
	 * 
	 * @param longitude Longitude of the wanted coordinate (radians)
	 * @param latitude Latitude of the wanted coordinate (radians)
	 * @param windowRecognition Whether or not the user wants the program to print coordinates TODO
	 * @return X-coordinate on the screen matching the coordinate on the sphere.
	 */
	public int giveWindowXCoordinate(double longitude, double latitude) {
		if (!isVisible(longitude, latitude)) {
			return -10;
		}
		
		return (int)countWindowXCoordinate(longitude, latitude);
	}
	
	private double countWindowXCoordinate(double longitude, double latitude) {
		int xDistanceFromCenter = (int)( Math.sin(longitude-this.longitude) * radius
				* Math.cos(latitude) );
		return xDistanceFromCenter + centerX;
	}
	
	/**
	 * Gives a y-coordinate on the screen for a coordinate on the sphere.
	 * Returns negative for coordinates that are on the unseen side of the sphere.
	 * 
	 * @param longitude Longitude of the wanted coordinate (radians)
	 * @param latitude Latitude of the wanted coordinate (radians)
	 * @param help Whether of not the user wants the program to print coordinates TODO
	 * @return Y-coordinate on the screen matching the coordinate on the sphere.
	 */
	public int giveWindowYCoordinate(double longitude, double latitude) {
		if (!isVisible(longitude, latitude)) {
			return -10;
		}
		
		return (int)countWindowYCoordinate(longitude, latitude);
	}
	
	private double countWindowYCoordinate(double longitude, double latitude) {
		double yDistanceFromLatitudeCenter = Math.sin(this.latitude)
				* Math.cos(longitude-this.longitude) * radius
				* Math.cos(latitude);
		
		double yLatitudeDistanceFromCenter = Math.cos(this.latitude)
				* radius * Math.sin(latitude);
		
		
		int yDistanceFromCenter = (int) (yLatitudeDistanceFromCenter + yDistanceFromLatitudeCenter);
		
		return centerY - yDistanceFromCenter;
	}
	
	private boolean isVisible(double longitude, double latitude) {
		latitude = -latitude;
		return straightDistance(longitude, latitude) < radius/Math.sin(Math.PI/4);
	}
	
	private double straightDistance(double longitude, double latitude) {
		double[] pointCoordinates = coordinatesInRelationToSphereCenter(longitude, latitude);
		
		return Math.pow( Math.pow(centerCoordinates[0]-pointCoordinates[0], 2)  +
				Math.pow(centerCoordinates[1]-pointCoordinates[1], 2) +
				Math.pow(centerCoordinates[2]-pointCoordinates[2], 2), 0.5);
	}
	
	private double[] coordinatesInRelationToSphereCenter(double longitude, double latitude) {
		double[] coordinatesInXyz = new double[3];
		
		coordinatesInXyz[1] = radius * Math.sin(latitude);
		double latitudeRadius = radius * Math.cos(latitude);
		coordinatesInXyz[0] = latitudeRadius * Math.sin(longitude);
		coordinatesInXyz[2] = latitudeRadius * Math.cos(longitude);
		
		return coordinatesInXyz;
	}
	
	/**
	 * Returns the distance of two coordinates along the surface of the sphere.
	 * Radius of the sphere is set to 100.
	 * 
	 * @param longitude1 Longitude of the first coordinate in radians
	 * @param latitude1 Latitude of the first coordinate in radians
	 * @param longitude2 Longitude of the second coordinate in radians
	 * @param latitude2 Latitude of the second coordinate in radians
	 * @return The distance of the two coordinates along the surface of the sphere
	 */
	public double giveDistance(double longitude1, double latitude1, double longitude2, double latitude2) {
		double halfAngle = Math.asin(straightDistance(longitude1, latitude1, longitude2, latitude2)/radius);
		return 2*Math.PI*100*(2*halfAngle/(2*Math.PI));
	}
	
	private double straightDistance(double lon1, double lat1, double lon2, double lat2) {
		double x1 = giveWindowXCoordinate(lon1, lat1)-centerX;
		double y1 = -(giveWindowYCoordinate(lon1, lat1)-centerY);
		double z1 = Math.pow(radius*radius - x1*x1 - y1*y1, 0.5);
		if (!isVisible(lon1, lat1)) {
			z1 = -z1;
		}
		
		//TODO refaktoroi
		double x2 = giveWindowXCoordinate(lon2, lat2)-centerX;
		double y2 = -(giveWindowYCoordinate(lon2, lat2)-centerY);
		double z2 = Math.pow(radius*radius - x2*x2 - y2*y2, 0.5);
		if (!isVisible(lon2, lat2)) {
			z2 = -z2;
		}
		
		return Math.pow((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2) + (z1-z2)*(z1-z2), 0.5);
	}
}
