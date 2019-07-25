package hoangviet.ndhv.com;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;

public class FragmentHome extends Fragment implements OnMapReadyCallback {
    private static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int REQUEST_CODE_PERMISSION = 225;
    private GoogleMap mMap;
    private boolean mLocationPermissionGranted = false;
    private static final String TAG = "FragmentHome";
    private FusedLocationProviderClient mfusedLocationProviderClient = null;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: có zô đây không nek");
        mMap = googleMap;

            Log.d(TAG, "onMapReady: zô đây không????");
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION) !=
                    PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            Log.d(TAG, "onMapReady: nói tiếng đi");
        }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        getLocationPermission();
        return view;

    }
   
    private void getDeviceLocation() {
        Log.d(TAG, "getDeviceLocation: đã vào phần xác đinh vị trí thiết bị");
        mfusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getActivity());
        try {
            if (mLocationPermissionGranted) {
                final Task location = mfusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Location locationcurent = (Location) task.getResult();
                            moveCamera(new LatLng(locationcurent.getLatitude(),locationcurent.getLongitude()),15f);
                        }else {
                            Toast.makeText(getActivity(), "tìm vị trí thiết bị không thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        } catch (SecurityException e) {
            Log.d(TAG, "getDeviceLocation: " + e.getMessage());
        }

    }
    private void moveCamera(LatLng latLng,float zoom){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,zoom));
    }
    private void initMap(){
        Log.d(TAG, "initMap: khỏi tạo map");
        SupportMapFragment mapFragment = (SupportMapFragment)getChildFragmentManager().findFragmentById(R.id.fragmentMyMap);
        mapFragment.getMapAsync(this);
    }
    private void getLocationPermission(){
        Log.d(TAG, "getLocationPermission: Vào phần getlocation");
        String[] permission = {Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION};
        if (ContextCompat.checkSelfPermission(this.getActivity(),ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            if (ContextCompat.checkSelfPermission(this.getActivity(),ACCESS_COARSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED){
                Log.d(TAG, "getLocationPermission: thành công getLocation Permission");
                initMap();
            }else{
                ActivityCompat.requestPermissions(this.getActivity(),permission,REQUEST_CODE_PERMISSION);
            }
        }else {
            ActivityCompat.requestPermissions(this.getActivity(),permission,REQUEST_CODE_PERMISSION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       switch (requestCode){
           case REQUEST_CODE_PERMISSION:
            if (grantResults.length > 0){
                for (int i = 0 ;i< grantResults.length ; i++){
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                        Log.d(TAG, "onRequestPermissionsResult: Vào phần not granted");
                        mLocationPermissionGranted = false;
                        return;
                    }
                }
                Log.d(TAG, "onRequestPermissionsResult: chạy init map");
                mLocationPermissionGranted = true;
                initMap();
            }
            break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}

