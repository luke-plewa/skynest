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
		Calendar c = Calendar.getInstance();//get Calendar
		currentTime = (int)(System.currentTimeMillis()/60000);// call time get minutes
		
		/*if((Time = mySchedManager.getTime(c.get(Calendar.DAY_OF_WEEK), 0)) >= currentTime)
		{
			Time = mySchedManager.getTime(c.get(Calendar.DAY_OF_WEEK), 1);
		}*/
		Time = mySchedManager.getTime(c.get(Calendar.DAY_OF_WEEK), 1); //Always get arrival time
		return MockThermostat.getTemp(currentTime, Time, getPreferedTemp());//get current temp
	}

}

