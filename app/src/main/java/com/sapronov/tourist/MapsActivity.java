package com.sapronov.tourist;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sapronov.tourist.model.Coordinates;
import com.sapronov.tourist.room.DatabaseCallback;
import com.sapronov.tourist.room.RoomDb;

import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, DatabaseCallback {

    private GoogleMap map;
    private Location location;
    private List<Coordinates> coordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        Button current = findViewById(R.id.current);
        current.setOnClickListener(this::getCurrentLocation);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        RoomDb.getDb(this).getAllCoordinates(this);
        LocationListener loc = new LocationListener() {
            @Override
            public void onLocationChanged(Location lct) {
                location = lct;
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

            }
        };
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 0, loc);
    }

    public void getCurrentLocation(View view) {
        if (location != null) {
            Coordinates coordinate=new Coordinates(location.getLatitude(), location.getLongitude());
            RoomDb.getDb(this).addCoordinates(coordinate);
            LatLng current = new LatLng(location.getLatitude(), location.getLongitude());
            MarkerOptions marker = new MarkerOptions().position(current).title("I'm here!");
            marker.flat(true);
            map.addMarker(marker);
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(current, 18));
            RoomDb.getDb(this).getAllCoordinates(this);
        }
    }

    @Override
    public void onCoordinatesLoaded(List<Coordinates> coordinates) {
        this.coordinates=coordinates;
        for (Coordinates coord:coordinates){
            addMarkerToMap(coord);
        }
    }

    private void addMarkerToMap(Coordinates coordinates){
        LatLng current = new LatLng(coordinates.getLatitude(), coordinates.getLongitude());
        MarkerOptions marker = new MarkerOptions().position(current).title("I'm here!");
        marker.flat(true);
        map.addMarker(marker);
    }
}
