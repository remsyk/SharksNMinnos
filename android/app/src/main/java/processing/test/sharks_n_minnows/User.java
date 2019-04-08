package processing.test.sharks_n_minnows;

import java.util.Random;

/**
 * Created by i77ki on 9/24/2017.
 */
//NEED TO MAKE THIS SINGLETON
public class User {

	private int id;
	private double longitude;
	private double latitude;
	Random r = new Random();
	private int rand = r.nextInt(100000 - 1) + 1;


	public String getId() {
		return String.valueOf(this.id);
	}

	public String getLongitude() {
		return String.valueOf(this.longitude);
	}

	public String getLatitude() {
		return String.valueOf(this.latitude);
	}

	public void setUser( double longitude, double latitude) {
		this.id = this.rand;
		this.longitude = longitude;
		this.latitude = latitude;
	}
}
