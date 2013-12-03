/**
 * See http://www.javacodegeeks.com/2011/01/android-proximity-alerts-tutorial.html.
 */

package edu.calpoly.ai.skynest;

import static android.content.Context.MODE_PRIVATE;

import java.util.Calendar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;

public class LocationBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		String key = LocationManager.KEY_PROXIMITY_ENTERING;
		Boolean entering = intent.getBooleanExtra(key, false);
		ScheduleManager schedMan =
				new ScheduleManager(context.getSharedPreferences(MainActivity.PREF_FILE, MODE_PRIVATE));

		// returns constants SUNDAY through SATURDAY, equal to 1 through 7 respectively
		Calendar cal = Calendar.getInstance();
		int day = cal.get(Calendar.DAY_OF_WEEK) - 1; // -1 to make 0-based
		int min = (cal.get(Calendar.HOUR_OF_DAY) * 60) + cal.get(Calendar.MINUTE);
		
		if (entering) {
			// alert schedule manager to change arrival time
			schedMan.updateTime(day, 1, min);
		}
		else {
			// alert schedule manager to change departure time
			schedMan.updateTime(day, 0, min);
		}
	}	
}	
