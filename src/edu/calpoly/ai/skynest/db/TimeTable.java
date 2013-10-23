package edu.calpoly.ai.skynest.db;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

/**
 * Class that provides helpful database table accessor variables and manages
 * basic required database functionality.
 * 
 * @author mlerner
 */
public class TimeTable {

	public static final String DATABASE_TABLE_TIME = "time_table";
	
	// Time table column names and IDs for database access
	public static final String TIME_KEY_WEEK_ID = "_id";
	public static final int TIME_COL_WEEK_ID = 0;
	
	public static final String TIME_KEY_SUN_DEP = "sunday_depart";
	public static final int TIME_COL_SUNN_DEP = 1;
	
	public static final String TIME_KEY_SUN_ARR = "sunday_arrive";
	public static final int TIME_COL_SUNN_ARR = 2;
	
	public static final String TIME_KEY_MON_DEP = "monday_depart";
	public static final int TIME_COL_MON_DEP = 3;
	
	public static final String TIME_KEY_MON_ARR = "monday_arrive";
	public static final int TIME_COL_MON_ARR = 4;
	
	public static final String TIME_KEY_TUE_DEP = "tuesday_depart";
	public static final int TIME_COL_TUE_DEP = 5;
	
	public static final String TIME_KEY_TUE_ARR = "tuesday_arrive";
	public static final int TIME_COL_TUE_ARR = 6;
	
	public static final String TIME_KEY_WED_DEP = "wednesday_depart";
	public static final int TIME_COL_WED_DEP = 7;
	
	public static final String TIME_KEY_WED_ARR = "wednesday_arrive";
	public static final int TIME_COL_WED_ARR = 8;
	
	public static final String TIME_KEY_THU_DEP = "thursday_depart";
	public static final int TIME_COL_THU_DEP = 9;
	
	public static final String TIME_KEY_THU_ARR = "thursday_arrive";
	public static final int TIME_COL_THU_ARR = 10;
	
	public static final String TIME_KEY_FRI_DEP = "friday_depart";
	public static final int TIME_COL_FRI_DEP = 11;
	
	public static final String TIME_KEY_FRI_ARR = "friday_arrive";
	public static final int TIME_COL_FRI_ARR = 12;
	
	public static final String TIME_KEY_SAT_DEP = "saturday_depart";
	public static final int TIME_COL_SAT_DEP = 13;
	
	public static final String TIME_KEY_SAT_ARR = "saturday_arrive";
	public static final int TIME_COL_SAT_ARR = 14;
	
	/**
	 * SQLite database creation statement. Auto-increments IDs of inserted
	 * times.
	 */
	public static String DATABASE_CREATE = "create table "
			+ DATABASE_TABLE_TIME + " ("
			+ TIME_KEY_WEEK_ID + " integer primary key autoincrement, "
			+  makeColStringForCreateTable(TIME_KEY_SUN_DEP)
			+  makeColStringForCreateTable(TIME_KEY_SUN_ARR)
			+  makeColStringForCreateTable(TIME_KEY_MON_DEP)
			+  makeColStringForCreateTable(TIME_KEY_MON_ARR)
			+  makeColStringForCreateTable(TIME_KEY_TUE_DEP)
			+  makeColStringForCreateTable(TIME_KEY_TUE_ARR)
			+  makeColStringForCreateTable(TIME_KEY_WED_DEP)
			+  makeColStringForCreateTable(TIME_KEY_WED_ARR)
			+  makeColStringForCreateTable(TIME_KEY_THU_DEP)
			+  makeColStringForCreateTable(TIME_KEY_THU_ARR)
			+  makeColStringForCreateTable(TIME_KEY_FRI_DEP)
			+  makeColStringForCreateTable(TIME_KEY_FRI_ARR)
			+  makeColStringForCreateTable(TIME_KEY_SAT_DEP)
			+  makeColStringForCreateTable(TIME_KEY_SAT_ARR);
			
	/**
	 * SQLite database table removal statement. Only used if upgrading database.
	 */
	public static final String DATABASE_DROP = "drop table if exists "
			+ DATABASE_TABLE_TIME;
	
	/**
	 * Initializes the database.
	 * 
	 * @param database The database to initialize.
	 */
	public static void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}
	
	/**
	 * upgrades the database to a new version.
	 */
	public static void onUpgrade(SQLiteDatabase database, int oldVersion,
			int newVersion) {
		Log.w(TimeTable.class.getName(),
				"Database being upgraded from old version (" + oldVersion
				+ ") to new version(" + newVersion + ").");
		database.execSQL(DATABASE_DROP);
		onCreate(database);
	}
	
	/**
	 *	Makes a string for the SQLite database creation statement that sets
	 *	data type to integer, can't be null, defaults to -1, and must be
	 *	between -1 and 1440. 
	 *
	 * @param key The column key to add to the table.
	 */
	private static String makeColStringForCreateTable(String key) {
		return key + " integer not null default -1 check("
				+ key + ">-2) check(" + key + "<1440)";
	}
}
