package edu.calpoly.ai.skynest;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import java.util.ArrayList;
import java.util.List;

public class ScheduleManager {
	protected Schedule loaded_schedule;
	SharedPreferences sp;
	
	/* constants */
	private int NUM_DAYS = 7;

	public ScheduleManager(SharedPreferences sp) {
		this.sp = sp;
	}

	/** returns whole schedule from shared preferences as a Schedule object **/
	public Schedule getSchedule() {
		loadSchedule();
		return loaded_schedule;
	}

	/** returns whole schedule as an array of ints 
	 * 	returns: int[] [mon_depart, mon_arrive, tue_depart, ... **/
	public int[] getScheduleInts() {
		int[] int_schedule = new int[NUM_DAYS*2];
		int dayInt, timeInt;
		loadSchedule();
		
		for(dayInt=0, timeInt=0; dayInt < NUM_DAYS; timeInt++){
			if(timeInt == 2)
			{
				dayInt++;
				timeInt=0;
			}
			int_schedule[dayInt*2 + timeInt] = loaded_schedule.getTime(dayInt, timeInt);
				
		}
		
		return int_schedule;
	}

	/** returns specific integer time from the schedule
	 *  parameters: int dayInt (0-6 : mon, tue, wed, ...)
	 *  			int timeInt (0-1 : departure, arrival) 
	 *  returns: 	int (minutes since start of day) **/
	public int getTime(int dayInt, int timeInt) {
		loadSchedule();
		return loaded_schedule.getTime(dayInt, timeInt);
	}
	
	/** returns specific integer time from the schedule
	 *  parameters: int slot (0-13 : mon_depart, mon_arrive, tue_depart, ...)
	 *  			int timeInt (0-1 : departure, arrival) 
	 *  returns: 	int (minutes since start of day) **/
	public int getTimeSlot(int slot) {
		return this.getTime(slot/2, slot%2);
	}
	
	/** sets specific integer time in the schedule 
	 *  parameters: int dayInt (0-6 : mon, tue, wed, ...)
	 *  			int timeInt (0-1 : departure, arrival)
	 *  			int value (0-1440 : minutes since start of day) **/
	public void setTime(int dayInt, int timeInt, int value) {
		loadSchedule();
		loaded_schedule.setTime(dayInt, timeInt, value);
		saveSchedule();
	}

	/** sets specific integer time in the schedule 
	 *  parameters: int slot (0-13 : mon_depart, mon_arrive, tue_depart, ...)
	 *  			int value (0-1440 : minutes since start of day) **/
	public void setTimeSlot(int slot, int value) {
		this.setTime(slot/2, slot%2, value);
	}
	
	/** saves the loaded schedule into shared preferences **/
	protected void saveSchedule() {
		Editor e = sp.edit();
    	for(int index=0; index<14; index++) {
    		e.putInt("sched" + index, loaded_schedule.getTime(index/2, index%2));
    	}
		e.commit();
	}
	
	/** reads shared preferences to refresh the the loaded schedule **/
	protected void loadSchedule() {
    	for(int index=0; index<14; index++) {
    		if(sp != null && sp.contains("sched" + index))
    			loaded_schedule.setTime(index/2, index%2, sp.getInt("sched" + index, -1));
    	}
	}
	 
	
	
	class Schedule {
		public Day mon, tue, wed, thu, fri, sat, sun;
		
		public Schedule() {
			mon = new Day();
			tue = new Day();
			wed = new Day();
			thu = new Day();
			fri = new Day();
			sat = new Day();
			sun = new Day();
		}
		
		public Day getDay(int day){
			switch(day){
			case(0):
				return this.mon;
			case(1):
				return this.tue;
			case(2):
				return this.wed;
			case(3):
				return this.thu;
			case(4):
				return this.fri;
			case(5):
				return this.sat;
			case(6):
				return this.sun;
			}
			return null;
		}
		
		public Day setDay(int which_day, Day day_obj){
			switch(which_day){
			case(0):
				this.mon = day_obj;
			case(1):
				this.tue = day_obj;
			case(2):
				this.wed = day_obj;
			case(3):
				this.thu = day_obj;
			case(4):
				this.fri = day_obj;
			case(5):
				this.sat = day_obj;
			case(6):
				this.sun = day_obj;
			}
			return null;
		}
		
		public int getTime(int dayInt, int timeInt){
			
			if(this.getDay(dayInt) != null){
				switch(timeInt){
				case(0):
						return this.getDay(dayInt).departure;
				case(1):
						return this.getDay(dayInt).arrival;
				}
			}
			return -2;
		}
		
		public void setTime(int dayInt, int timeInt, int time_value){

			if(this.getDay(dayInt) == null){
				this.setDay(dayInt, new Day());
			}
			
			switch(timeInt){
				case(0):
						this.getDay(dayInt).departure = time_value;
						break;
				case(1):
						this.getDay(dayInt).arrival = time_value;
						break;
			}
		}
		
		private class Day {
			public int departure, arrival;

			public Day(){
				this.departure = -1;
				this.arrival = -1;
			}
			
			public Day(int arrival, int departure){
				this.departure = departure;
				this.arrival = arrival;
			}
		}
	}
}
