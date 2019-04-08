package processing.test.sharks_n_minnows;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.Random;

/**
 * Created by i77ki on 9/11/2017.
 */

class MyLocationListener extends AppCompatActivity implements LocationListener {

	private static final String TAG = "MyLocationListener";
	Random r = new Random();
	int rand = r.nextInt(100000 - 1) + 1;
	User user = new User();

	MyLocationListener(Context context){


		if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			// ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			Log.d(TAG,"MY_GPS_PASSED_PERMISSION_CHECK");



			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				requestPermissions(new String[]{
						Manifest.permission.ACCESS_FINE_LOCATION,
						Manifest.permission.ACCESS_COARSE_LOCATION,
						Manifest.permission.INTERNET},0);
						Log.d(TAG,"MY_GPS_HAS_PERMISSION");
			}
			// to handle the case where the User grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return;
		}
	}


	@Override
	public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
		switch (requestCode) {
			case 0: {
				// If request is cancelled, the result arrays are empty.
				if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					//locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);
					Log.d(TAG,"MY_GPS_WORKS");
					// permission was granted, yay! Do the
					// contacts-related task you need to do.

				} else {
					Log.d(TAG,"MY_GPS_FAIL_EXCEPTION");
					// permission denied, boo! Disable the
					// functionality that depends on this permission.
				}
				return;
			}
		}
		// other 'case' lines to check for other
		// permissions this app might request
	}


	@Override
	public void onLocationChanged(Location loc) {

		user.setUser(loc.getLongitude(),loc.getLatitude());
		AsyncT asyncT = new AsyncT(user);
		asyncT.execute();
	}


	@Override
	public void onStatusChanged(String s, int i, Bundle bundle) {}

	@Override
	public void onProviderEnabled(String s) {
		Log.v(TAG, "GPS_IS_ON");


	}

	@Override
	public void onProviderDisabled(String s) {
		Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		startActivity(intent);
		Log.v(TAG, "GPS_IS_OFF");
	}

}
