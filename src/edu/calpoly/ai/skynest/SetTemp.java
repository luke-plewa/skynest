package edu.calpoly.ai.skynest;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

public class SetTemp extends Activity implements OnSeekBarChangeListener {
	
	private double temperature;
	private TempManager tm;
	private SharedPreferences sp;
	private TextView tempField;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_set_temp);
		sp = this.getSharedPreferences(MainActivity.PREF_FILE, MODE_PRIVATE);
		tm = new TempManager(sp);
		temperature = tm.getPreferedTemp();
		
		tempField = (TextView)findViewById(R.id.editText2);
		tempField.setText(Double.toString(temperature));
		
		SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar1);
		seekBar.setProgress((int) temperature);
		seekBar.setOnSeekBarChangeListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.set_temp, menu);
		return true;
	}

	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		temperature = seekBar.getProgress();
		tempField.setText(Double.toString(temperature));
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		temperature = seekBar.getProgress();
		tm.setPreferedTemp(temperature);
		tempField.setText(Double.toString(temperature));
	}

}
