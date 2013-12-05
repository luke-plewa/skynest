package edu.calpoly.ai.skynest;



import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import java.util.Calendar;

public class TempManager {
	
	/** Declare the Shared Pref String and tempNotSet */
	private static final String PREFERED_TEMP = "PREFERED_TEMP";
	private static final double TEMP_NOT_SET = -100;
	private int currentTime;
	private ScheduleManager mySchedManager;
	private SharedPreferences sp;
	
	/** Constructor makes a new mock nest every time possibly change implementation*/
	public TempManager(SharedPreferences sp) {
		this.sp = sp;
		mySchedManager = new ScheduleManager(sp);
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
	/** returns double of the house temp
	 *  parameters: None
	 *  returns: 	double (temp) 
	 **/
	public double getHouseTemp() {
		int Time;
		Calendar rightNow = Calendar.getInstance();
		Calendar c = Calendar.getInstance();//get Calendar
		long offset = rightNow.get(Calendar.ZONE_OFFSET) +
			    rightNow.get(Calendar.DST_OFFSET);

			long sinceMidnight = (rightNow.getTimeInMillis() + offset) %
			    (24 * 60 * 60 * 1000);
		currentTime = (int)((sinceMidnight%86400000)/60000);// call time get minutes
		
		if((Time = mySchedManager.getTime(c.get(Calendar.DAY_OF_WEEK)-1, 0)) <= currentTime)
		{
			Time = mySchedManager.getTime(c.get(Calendar.DAY_OF_WEEK)-1, 1);
			return MockThermostat.getTemp(currentTime, Time, getPreferedTemp(), true);
		}
		else
		{
			return MockThermostat.getTemp(currentTime, Time, getPreferedTemp(), false);//get current temp
		}
	}

}

