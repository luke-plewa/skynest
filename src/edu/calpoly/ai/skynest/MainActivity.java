package edu.calpoly.ai.skynest;

import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.view.Menu;

public class MainActivity extends SherlockFragmentActivity implements LocationListener {

	/** The interactive Google Map fragment. */
	private GoogleMap m_vwMap;
	
	/** The list of locations, each having a latitude and longitude. */
	private ArrayList<LatLng> m_arrPathPoints;
	
	/** The continuous set of lines drawn between points on the map. */
	private Polyline m_pathLine;
	
	/** The Location Manager for the map. Used to obtain location status, etc. */
	private LocationManager m_locManager;

	/** The radius of a Circle drawn on the map, in meters. */
	private static final int CIRCLE_RADIUS = 1;
	
	/** Markers for the home and work locations */
	private Marker home;
	private Marker work;

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
        	m_pathLine = m_vwMap.addPolyline(new PolylineOptions());
        	m_pathLine.setColor(Color.GREEN);
        }
        initLocationData();
	}
	
	/**
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	*/
	
	/**
     * Initializes all Location-related data.
     */
    private void initLocationData() {
    	m_locManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    	m_arrPathPoints = new ArrayList<LatLng>();
    }

	@Override
	public void onLocationChanged(Location location) {
		double lat = location.getLatitude();
		double lng = location.getLongitude();
		LatLng l = new LatLng(lat, lng);
		m_arrPathPoints.add(l);
		m_vwMap.animateCamera(CameraUpdateFactory.newLatLng(l));
		m_pathLine.setPoints(m_arrPathPoints);
		m_vwMap.addCircle(new CircleOptions()
			    .center(l)
			    .radius(CIRCLE_RADIUS) // In meters
				.fillColor(Color.CYAN)
				.strokeColor(Color.BLUE));
	}
	
	public void setHomeLocation(Location location) {
		double lat = location.getLatitude();
		double lng = location.getLongitude();
		LatLng l = new LatLng(lat, lng);
		m_vwMap.addCircle(new CircleOptions()
			    .center(l)
			    .radius(CIRCLE_RADIUS) // In meters
				.fillColor(Color.CYAN)
				.strokeColor(Color.BLUE));
	}
	
	public void setWorkLocation(Location location) {
		double lat = location.getLatitude();
		double lng = location.getLongitude();
		LatLng l = new LatLng(lat, lng);
		m_vwMap.addCircle(new CircleOptions()
			    .center(l)
			    .radius(CIRCLE_RADIUS) // In meters
				.fillColor(Color.CYAN)
				.strokeColor(Color.BLUE));
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
