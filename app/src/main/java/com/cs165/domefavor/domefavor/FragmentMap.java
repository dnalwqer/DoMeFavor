package com.cs165.domefavor.domefavor;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuehanyu on 5/23/16.
 */
public class FragmentMap extends Fragment implements GoogleMap.OnInfoWindowClickListener, View.OnClickListener,
        LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleApiClient mGoogleApiClient;
    private MapView mapView;
    private GoogleMap map;
    private boolean firstTime = true;
    private static LatLng loc;
    private List<Marker> markerList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_map, container, false);

        // Gets the MapView from the XML layout and creates it
        mapView = (MapView) v.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);

        // Gets to GoogleMap from the MapView and does initialization stuff
        map = mapView.getMap();
        map.getUiSettings().setMyLocationButtonEnabled(false);
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return v;
        }
        map.setMyLocationEnabled(true);
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(getContext())
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }

        UiSettings settings = map.getUiSettings();
        settings.setCompassEnabled(true);
        settings.setMyLocationButtonEnabled(true);
        settings.setZoomControlsEnabled(true);
        settings.setRotateGesturesEnabled(true);
        settings.setScrollGesturesEnabled(true);
        settings.setTiltGesturesEnabled(false);
        settings.setZoomGesturesEnabled(true);

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        try {
            MapsInitializer.initialize(this.getActivity());
        } catch (Exception e) {
            e.printStackTrace();
        }


        map.setOnInfoWindowClickListener(this);
        ImageButton botton = (ImageButton) v.findViewById(R.id.mapRefreshButton);
        botton.setOnClickListener(this);
//        loc = map.getMyLocation();
//        // Updates the location and zoom of the MapView
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(loc.getLatitude(), loc.getLongitude()), 10);
//        map.animateCamera(cameraUpdate);
        return v;
    }

    private void createLocationRequest() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        if (ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onConnected(Bundle connectionHint) {
        createLocationRequest();
    }

    @Override
    public void onLocationChanged(Location location) {
        loc = new LatLng(location.getLatitude(), location.getLongitude());
        if(firstTime) {
            firstTime = false;
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 18.0f));
            refresh();
        }else
            map.animateCamera(CameraUpdateFactory.newLatLng(loc));
    }

    @Override
    public void onStart() {
        if(mGoogleApiClient != null)
            mGoogleApiClient.connect();
        super.onStart();
    }

    @Override
    public void onStop() {
        if(mGoogleApiClient != null)
            mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        //This will fire when you click the annotation view
        marker.showInfoWindow();
    }

    public void addMarker(List<TaskItem> tasks){
        for(int i = 0 ; i < markerList.size() ; ++i){
            markerList.get(i).remove();
        }
        markerList.clear();
        for(int i = 0 ; i < tasks.size() ; ++i){
            TaskItem task = tasks.get(i);
            Marker m = map.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(task.getLatitude()), Double.parseDouble(task.getLongitude())))
                    .title(task.getTaskName()).snippet(task.getContent()).alpha(0.7f));
            markerList.add(m);
        }
    }

    public static LatLng getLatLng(){
        return loc;
    }

    public void onClick(View v){
       refresh();
    }

    private void refresh(){
        MainActivity_v2 parent = (MainActivity_v2)getActivity();
        FragmentTaskList fragmentTaskList = (FragmentTaskList)parent.getFragment(1);
        fragmentTaskList.refreshData();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    public void onConnectionSuspended(int i) {

    }
}
