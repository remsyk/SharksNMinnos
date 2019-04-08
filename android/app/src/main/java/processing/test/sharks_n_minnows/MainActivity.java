package processing.test.sharks_n_minnows;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import processing.android.CompatUtils;
import processing.android.PFragment;
import processing.core.PApplet;

public class MainActivity extends AppCompatActivity {
	private PApplet sketch;
	LocationManager locationManager;
	private static final String TAG = "MainActivity";
	User user = new User();
	GameHandler gameHandler = new GameHandler(this);


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FrameLayout frame = new FrameLayout(this);
		frame.setId(CompatUtils.getUniqueViewId());
		setContentView(frame, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

		locationManager = (LocationManager)getSystemService(MainActivity.this.LOCATION_SERVICE);
		LocationListener locationListener = new MyLocationListener(MainActivity.this);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);

		sketch = new sharks_n_minnows(this);
		PFragment fragment = new PFragment(sketch);
		fragment.setView(frame, this);

		gameHandler.appStart();

	}

	@Override
	protected void onResume(){
		super.onResume();

		locationManager = (LocationManager)getSystemService(MainActivity.this.LOCATION_SERVICE);
		LocationListener locationListener = new MyLocationListener(MainActivity.this);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 10, locationListener);

		Location locationGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

		if (null != locationGPS){
			user.setUser(locationGPS.getLongitude(),locationGPS.getLatitude());
			AsyncT asyncT = new AsyncT(user);
			asyncT.execute();

		}


		Toast toast6 = Toast.makeText(MainActivity.this, "Did It", Toast.LENGTH_SHORT);
		toast6.show();

	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		super.onSaveInstanceState(savedInstanceState);
		savedInstanceState.putString("Restart_Notice", "The Game Never Ends");
		savedInstanceState.putBoolean("gps_on", true);

	}

	@Override
	public void onRestoreInstanceState(Bundle savedInstanceState) {

		super.onRestoreInstanceState(savedInstanceState);
		boolean gps_on = savedInstanceState.getBoolean("gps_on");
		String restart_Notice = savedInstanceState.getString("Restart_Notice");

		if(gps_on ==true){
			Toast toast2 = Toast.makeText(MainActivity.this, "GPS ON", Toast.LENGTH_SHORT);
			toast2.show();

		}
		if(gps_on ==false){
			Toast toast2 = Toast.makeText(MainActivity.this, "GPS OFF", Toast.LENGTH_SHORT);
			toast2.show();

		}

		Toast toast = Toast.makeText(MainActivity.this, restart_Notice, Toast.LENGTH_SHORT);
		toast.show();

	}

	@Override
	public void onNewIntent(Intent intent) {
		if (sketch != null) {
			sketch.onNewIntent(intent);
		}
	}


}


