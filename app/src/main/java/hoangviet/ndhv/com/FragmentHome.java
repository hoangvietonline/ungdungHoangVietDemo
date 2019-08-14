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
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class FragmentHome extends Fragment implements OnMapReadyCallback {
    private static final String ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String ACCESS_COARSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int REQUEST_CODE_PERMISSION = 225;
    private GoogleMap mMap;
    private Marker mMarker;
    private double latitude,longitude;
    private boolean mLocationPermissionGranted = false;
    private static final String TAG = "FragmentHome";
    private FusedLocationProviderClient mfusedLocationProviderClient ;
    Button button ;
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady: có zô đây không nek");
        mMap = googleMap;
            Log.d(TAG, "onMapReady: zô đây không????");
            getDiviceLocation();
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
        button = (Button)view.findViewById(R.id.buttonfind);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nearbyPlaces();
            }
        });
        getLocationPermission();
        return view;

    }
    private void nearbyPlaces(){
        RequestQueue queue = Volley.newRequestQueue(getActivity());
        final String url = getUrl(latitude,longitude,"gas_station");
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                double lat;
                double lng;
                String vicinity;
                String placeName ;
                MarkerOptions markerOptions = new MarkerOptions();

                try {
                    JSONArray results = response.getJSONArray("results");
                    for (int i = 0 ; i < results.length() ; i++){
                        JSONObject jsonResults = results.getJSONObject(i);
                        JSONObject jsonGeometry = jsonResults.getJSONObject("geometry");
                        JSONObject jsonLocation = jsonGeometry.getJSONObject("location");
                        lat = Double.parseDouble(jsonLocation.getString("lat"));
                        lng = Double.parseDouble(jsonLocation.getString("lng"));
                        placeName = jsonResults.getString("name");
                        vicinity = jsonResults.getString("vicinity");
                        LatLng latLng = new LatLng(lat,lng);
                        markerOptions.position(latLng)
                                .title(placeName)
                                .icon(BitmapDescriptorFactory.fromResource(R.drawable.gas_32));
                        mMarker = mMap.addMarker(markerOptions);
                        moveCamera(latLng);
                        Toast.makeText(getActivity(),vicinity, Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Lỗi rồi bạn", Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(objectRequest);

    }

    private String getUrl(double latitude,double longitude,String placeType) {
        StringBuffer googlePlaceUrl = new StringBuffer("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius=10000");
        googlePlaceUrl.append("&type="+placeType);
        googlePlaceUrl.append("&key="+getResources().getString(R.string.browser));
        Log.d(TAG,googlePlaceUrl.toString());
        return googlePlaceUrl.toString();
    }


    private void getDiviceLocation (){
        mfusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this.getActivity());
        try {
            final Task location =mfusedLocationProviderClient.getLastLocation();
            location.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()){
                        Location curentLocation = (Location) task.getResult();
                        latitude = curentLocation.getLatitude();
                        longitude = curentLocation.getLongitude();
                        LatLng latLng = new LatLng(latitude,longitude);
                        moveCamera(latLng);
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.title("current location")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))
                                .position(latLng);

                        mMap.addMarker(markerOptions);
                    }
                }
            });

        }catch (SecurityException e){
            Log.d(TAG, "getDiviceLocation: "+e.getMessage());
        }
    }
    private void moveCamera(LatLng latLng){
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13));
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

