/**
 * See http://www.javacodegeeks.com/2011/01/android-proximity-alerts-tutorial.html.
 */

package edu.calpoly.ai.skynest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

public class LocationBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String key = LocationManager.KEY_PROXIMITY_ENTERING;
		Boolean entering = intent.getBooleanExtra(key, false);
		if (entering) {
			// TODO: alert schedule manager to change arrival time, dependent on API
		}
		else {
			// TODO: alert schedule manager to change departure time, dependent on API
		}
	}	
}	
