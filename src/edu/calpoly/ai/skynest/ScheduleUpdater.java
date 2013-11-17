package edu.calpoly.ai.skynest;

import android.content.SharedPreferences;

public class ScheduleUpdater extends ScheduleManager {
	/** constants **/
	double NEW_DATA_WEIGHT = 0.20;
	
	public ScheduleUpdater(SharedPreferences sp) {
		super(sp);
	}

	/** updates shared preferences schedule using the time averaging algorithm.
	 * 	uses constant NEW_DATA_WEIGHT
	 *  parameters: int dayInt (0-6 : mon, tue, wed, ...)
	 *  			int timeInt (0-1 : departure, arrival)
	 *  			int value (0-1440 : minutes since start of day) **/
	public void updateTime(int dayInt, int timeInt, int value) {
		int old_value, new_value;
		
		old_value = loaded_schedule.getTime(dayInt, timeInt);
		new_value = (int) (old_value*(1-NEW_DATA_WEIGHT) + (value*NEW_DATA_WEIGHT));
		
		loaded_schedule.setTime(dayInt, timeInt, new_value);
	}

	/** updates shared preferences schedule using the time averaging algorithm.
	 * 	uses constant NEW_DATA_WEIGHT
	 *  parameters: int slot (0-13 : mon_depart, mon_arrive, tue_depart, ...)
	 *  			int value (0-1440 : minutes since start of day) **/
	public void updateTimeSlot(int slot, int value) {
		this.updateTime(slot/2, slot%2, value);
	}
}
