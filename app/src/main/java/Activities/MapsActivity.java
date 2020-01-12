package Activities;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.myapplication.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1010;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private boolean mLocationPermissionGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Gets the location of the location client
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        getLocationPermission();

        //Once you find the location of the user zoom into user
        final Task myLocation = mFusedLocationProviderClient.getLastLocation();


        myLocation.addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    Log.d("MapActivty", "Found Your Location");
                    Location currentLocation = (Location) task.getResult();

                    //Get latitude and longitude of my location
                    LatLng latlngCoords = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlngCoords, 15));
                }
                else {
                    Log.d("MapActivty", "onComplete: current location cannot be found");
                    Toast.makeText(MapsActivity.this,"Where are you?", Toast.LENGTH_SHORT).show();
                }
            }
        });

        mMap.setMyLocationEnabled(true);
        placeMarkers(mMap);

    }

    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void placeMarkers(GoogleMap map) {
        map.addMarker(new MarkerOptions()
                .position(new LatLng(49.2624994, -123.2449820))
                .icon(BitmapDescriptorFactory.defaultMarker(123))
                .title("East Atrium Garbage Bin"));


        map.addMarker(new MarkerOptions()
                .position(new LatLng(49.2623478, -123.2453487))
                .icon(BitmapDescriptorFactory.defaultMarker(322))
                .title("West Atrium Garbage Bin"));

        map.addMarker(new MarkerOptions()
                .position(new LatLng(49.2626845, -123.2442246))
                .icon(BitmapDescriptorFactory.defaultMarker(230))
                .title("Garbage Bin Outside"));

    }



}
