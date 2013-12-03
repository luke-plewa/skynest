package edu.calpoly.ai.skynest;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ThermostatActivity extends Activity {

	private MockThermostat thermostat;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thermostat);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.thermostat, menu);
		return true;
	}

}
