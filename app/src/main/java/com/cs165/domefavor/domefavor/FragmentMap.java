package com.cs165.domefavor.domefavor;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.VisibleRegion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xuehanyu on 5/23/16.
 */
public class FragmentMap extends Fragment implements GoogleMap.OnInfoWindowClickListener, View.OnClickListener {
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
        map.setOnMyLocationChangeListener(myLocationChangeListener);

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
        ImageButton botton = (ImageButton)v.findViewById(R.id.mapRefreshButton);
        botton.setOnClickListener(this);
//        Location loc = map.getMyLocation();
//        // Updates the location and zoom of the MapView
//        CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(new LatLng(loc.getLatitude(), loc.getLongitude()), 10);
//        map.animateCamera(cameraUpdate);
        return v;
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

    private GoogleMap.OnMyLocationChangeListener myLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            loc = new LatLng(location.getLatitude(), location.getLongitude());
            if(map != null){
                if(firstTime) {
                    firstTime = false;
//                    map.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 18.0f));
                    ZoomMap(loc);
                    refresh();
  //                addMarker();
                }else  ZoomMap(loc);
//                    map.animateCamera(CameraUpdateFactory.newLatLng(loc));
            }
        }
    };

    public void ZoomMap(LatLng lastLatLng){
        VisibleRegion vr = map.getProjection().getVisibleRegion();
        double left = vr.latLngBounds.southwest.longitude;
        double top = vr.latLngBounds.northeast.latitude;
        double right = vr.latLngBounds.northeast.longitude;
        double bottom = vr.latLngBounds.southwest.latitude;
        double vRange = right - left;
        double hRange = top - bottom;
//        Log.d(TAG, top + " : " + right);
//        Log.d(TAG, bottom + " : " + left);
//        Log.d(TAG, "vRange is " + vRange);
//        Log.d(TAG, "hRange is " + hRange);
        LatLng northeastBound = new LatLng(top -0.1 * vRange , right - 0.1 * hRange );
        LatLng southwestBound = new LatLng(bottom + 0.1 * vRange, left + 0.1 * hRange);

        LatLngBounds bounds = new LatLngBounds(southwestBound,northeastBound);

        if (!bounds.contains(lastLatLng))
            map.animateCamera(CameraUpdateFactory.newLatLngZoom(lastLatLng, 17));

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
}
