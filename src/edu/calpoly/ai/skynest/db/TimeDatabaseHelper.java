package edu.calpoly.ai.skynest.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Class that hooks up to the TimeTableContentProvider for initialization
 * and maintenance. Uses TimeTable for assistance.
 * 
 * @author mlerner
 */
public class TimeDatabaseHelper extends SQLiteOpenHelper {
	
	public static final String DATABASE_NAME = "timedatabase.db";
	
	// Starting version of database 
	public static final int DATABASE_VERSION = 1;

	public TimeDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		TimeTable.onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		TimeTable.onUpgrade(db, oldVersion, newVersion);
	}
}
