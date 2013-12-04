package edu.calpoly.ai.skynest;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class ThermostatActivity extends Activity {

	private TempManager tm;
	private double temperature;
	private TextView tempField;
	private TextView targetTempField;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_thermostat);
		
		initLayout();
		fillTextViews();
	}
	
	private void fillTextViews(){
		SharedPreferences sp = this.getSharedPreferences(MainActivity.PREF_FILE, MODE_PRIVATE);
		tm = new TempManager(sp);
		temperature = tm.getHouseTemp(getTime());
		
		double targetTemp = getTargetTemp();
		
		tempField = (TextView)findViewById(R.id.temperature);
		tempField.setText(Double.toString(temperature) + " F");
		
		targetTempField = (TextView)findViewById(R.id.target_temperature);
		targetTempField.setText(Double.toString(targetTemp) + " F");
	}
	
	private void initLayout(){
		final ImageButton home_button = (ImageButton) findViewById(R.id.button_home);
		home_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
        		Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
        		myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(myIntent);
            }
        });
	}
	
	private double getTargetTemp(){
		return tm.getPreferedTemp();
	}
	
	private int getTime(){
		return 0;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.thermostat, menu);
		return true;
	}

}
