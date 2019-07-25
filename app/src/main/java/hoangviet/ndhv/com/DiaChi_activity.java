package hoangviet.ndhv.com;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class DiaChi_activity extends AppCompatActivity implements OnMapReadyCallback {
GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dia_chi);
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragmentMyMap);
        supportMapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng myHouse = new LatLng(15.8921175,108.2496147);
        mMap.addMarker(new MarkerOptions().position(myHouse).title("My house").snippet("Nhà của tôi đó"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myHouse,17));
    }
}
