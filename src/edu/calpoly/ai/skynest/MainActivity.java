package edu.calpoly.ai.skynest;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

import android.os.Bundle;
import android.content.Intent;

public class MainActivity extends SherlockFragmentActivity{
	
	private final static String PROX_ALERT_INTENT = "edu.calpoly.ai.skynest.ProximityAlert";
	private final static long POINT_RADIUS = 200; // in Meters

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	@Override
	public void onResume() {
		/*
		if (homeLocation has not been set) {
			askUserForHomeLocation();
			
			Intent intent = new Intent(PROX_ALERT_INTENT);
        	PendingIntent proximityIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        	locationManager.addProximityAlert(getHomeLatitude(), getHomeLongitude(),
            	POINT_RADIUS, -1, proximityIntent);
            	
       		IntentFilter filter = new IntentFilter(PROX_ALERT_INTENT); 
       		registerReceiver(new LocationBroadcastReceiver(), filter);
		}
		 */
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = this.getSupportMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		switch(item.getItemId()){
			case R.id.menu_maps:
				Intent myIntent = new Intent(getApplicationContext(), MapsActivity.class);
				myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);
				break;
			default:
				return super.onOptionsItemSelected(item);
		}
		return true;
	}

}
