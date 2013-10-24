package edu.calpoly.ai.skynest;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.content.Context;
import android.content.Intent;

public class MainActivity extends SherlockFragmentActivity implements LocationListener {

	/** The interactive Google Map fragment. */
	private GoogleMap m_vwMap;
	
	/** The Location Manager for the map. Used to obtain location status, etc. */
	private LocationManager m_locManager;
	
	/** Request codes for starting new Activities. */
	private static final int ENABLE_GPS_REQUEST_CODE = 1;
	
	/** Markers for the home and work locations */
	private double home_lat, home_lng;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        m_vwMap = ((SupportMapFragment)
        		getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
        if(m_vwMap != null){
        	m_vwMap.getUiSettings().setRotateGesturesEnabled(true);
        	m_vwMap.getUiSettings().setCompassEnabled(true);
        	m_vwMap.setMyLocationEnabled(true);
        	m_vwMap.getUiSettings().setMyLocationButtonEnabled(true);
        	m_vwMap.getUiSettings().setZoomControlsEnabled(true);
        }
        initLocationData();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		MenuInflater inflater = this.getSupportMenuInflater();
		inflater.inflate(R.menu.main, menu);
		return true;
	}
	
	/**
     * Initializes all Location-related data.
     */
    private void initLocationData() {
    	m_locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

	@Override
	public void onLocationChanged(Location location) {
	}
	
	public void setHomeLocation(Location location) {
		double home_lat = location.getLatitude();
		double home_lng = location.getLongitude();
		m_vwMap.addMarker(new MarkerOptions()
		.position(new LatLng(home_lat, home_lng))
		.title("Home")
		);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item){
		Intent i;
		switch(item.getItemId()){
			case R.id.menu_enableGPS:
				i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
				startActivityForResult(i, MainActivity.ENABLE_GPS_REQUEST_CODE);
				break;
			case R.id.set_home:
				setHomeLocation(m_locManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
				break;
			default:
				return super.onOptionsItemSelected(item);
		}
		return true;
	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub
		
	}

}
