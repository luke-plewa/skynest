package edu.calpoly.ai.skynest;



import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class TempManager {
	
	/** Declare the Shared Pref String and tempNotSet */
	private static final String PREFERED_TEMP = "PREFERED_TEMP";
	private static final double TEMP_NOT_SET = -100;
	private SharedPreferences sp;
	
	/** Constructor makes a new mock nest every time possibly change implementation*/
	public TempManager(SharedPreferences sp) {
		this.sp = sp;
	}
	
	/** Sets Preferred temp in the shared preferences*/
	public void setPreferedTemp(double Temp) {
		Editor e = sp.edit();
		e.putLong("PREFERED_TEMP", (long) Temp);
		e.commit();
	}
	
	/** Returns Preferred temp from shared preferences */
	public double getPreferedTemp() {
		if(sp != null && sp.contains(PREFERED_TEMP)) {
			return sp.getLong(PREFERED_TEMP, (long) TEMP_NOT_SET);
		}
		else {
			return TEMP_NOT_SET;
		}
	}
	/** Should this int be a time variable */
	public double getHouseTemp(int Time) {
		// call getTemp Function
		return 0;//dummy for now
	}

}

