package edu.calpoly.ai.skynest;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;

public class MainActivity extends SherlockFragmentActivity{
	
	private final static String PROX_ALERT_INTENT = "edu.calpoly.ai.skynest.ProximityAlert";
	private final static long POINT_RADIUS = 200; // in Meters
	public final static String PREF_FILE = "Preferences";
	public SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		sp = this.getSharedPreferences(MainActivity.PREF_FILE, MODE_PRIVATE);
		initLayout();
	}
	
	private void initLayout(){
		final Button maps_button = (Button) findViewById(R.id.menu_maps);
		final Button set_temp_button = (Button) findViewById(R.id.button_set_temp);
		maps_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	startMapsActivity();
            }
        });
		set_temp_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
        		Intent myIntent = new Intent(getApplicationContext(), SetTemp.class);
        		myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);
            }
        });
	}
	
	@Override
	public void onResume() {
		super.onResume();
		if (hasHomeLocation()) {
			Intent intent = new Intent(PROX_ALERT_INTENT);
        	PendingIntent proximityIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        	LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        	lm.addProximityAlert(getHomeLatitude(), getHomeLongitude(),
            	POINT_RADIUS, -1, proximityIntent);
            	
       		IntentFilter filter = new IntentFilter(PROX_ALERT_INTENT); 
       		registerReceiver(new LocationBroadcastReceiver(), filter);
		}
		else {
			startMapsActivity();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = this.getSupportMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}
	
	private boolean hasHomeLocation(){
		if (sp != null &&
			sp.contains(MapsActivity.HOME_LAT) &&
			sp.contains(MapsActivity.HOME_LNG))
			return true;
		return false;
	}
	
	private long getHomeLatitude(){
		if(sp != null && sp.contains(MapsActivity.HOME_LAT))
			return sp.getLong(MapsActivity.HOME_LAT, (long) 0);
		else return 0;
	}
	
	private long getHomeLongitude(){
		if(sp != null && sp.contains(MapsActivity.HOME_LNG))
			return sp.getLong(MapsActivity.HOME_LNG, (long) 0);
		else return 0;
	}
	
	private void startMapsActivity(){
		Intent myIntent = new Intent(getApplicationContext(), MapsActivity.class);
		myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(myIntent);
	}

}
