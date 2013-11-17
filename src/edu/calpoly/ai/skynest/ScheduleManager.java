package edu.calpoly.ai.skynest;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;


public class ScheduleManager {
	protected Schedule loaded_schedule;
	SharedPreferences sp;
	
	/** constants **/
	private int NUM_DAYS = 7;
	private int NUM_TIMES = 2;

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
	 *  parameter: 	int slot (0-13 : mon_depart, mon_arrive, tue_depart, ...)
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
	private void saveSchedule() {
		Editor e = sp.edit();
    	for(int index=0; index<14; index++) {
    		e.putInt("sched" + index, loaded_schedule.getTime(index/2, index%2));
    	}
		e.commit();
	}
	
	/** reads shared preferences to refresh the the loaded schedule **/
	private void loadSchedule() {
    	for(int index=0; index<14; index++) {
    		if(sp != null && sp.contains("sched" + index))
    			loaded_schedule.setTime(index/2, index%2, sp.getInt("sched" + index, -1));
    	}
	}
	 
	
	
	protected class Schedule {
		private Day[] days = new Day[NUM_DAYS];
		
		public Schedule() {
			int iter;
			
			for(iter=0; iter<NUM_DAYS; iter++){
				days[iter] = new Day();
			}
		}
		
		public Day getDay(int day){
			if(day < 0 || day > NUM_DAYS-1)
				throw new IndexOutOfBoundsException("day argument has range 0 - " + (NUM_DAYS-1));

			return this.days[day];
		}

		public void setDay(int which_day, Day day_obj){
			if(day_obj == null)
				throw new IllegalArgumentException("you cannot set day to null");
			if(which_day < 0 || which_day > NUM_DAYS-1)
				throw new IndexOutOfBoundsException("day argument has range 0 - " + (NUM_DAYS-1));
			
			this.days[which_day] = day_obj;
		}
		
		public int getTime(int dayInt, int timeInt){
			if(dayInt < 0 || dayInt > NUM_DAYS-1)
				throw new IndexOutOfBoundsException("day argument has range 0 - " + (NUM_DAYS-1));
			if(timeInt < 0 || timeInt > NUM_TIMES-1)
				throw new IndexOutOfBoundsException("time argument has range 0 - " + (NUM_TIMES-1));
			
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
			if(dayInt < 0 || dayInt > NUM_DAYS-1)
				throw new IndexOutOfBoundsException("day argument has range 0 - " + (NUM_DAYS-1));
			if(timeInt < 0 || timeInt > NUM_TIMES-1)
				throw new IndexOutOfBoundsException("time argument has range 0 - " + (NUM_TIMES-1));
		
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
