//package UI;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//
//import androidx.annotation.Nullable;
//import androidx.fragment.app.FragmentActivity;
//
//import com.example.assignment.R;
//import com.google.android.gms.maps.CameraUpdateFactory;
//import com.google.android.gms.maps.GoogleMap;
//import com.google.android.gms.maps.OnMapReadyCallback;
//import com.google.android.gms.maps.SupportMapFragment;
//import com.google.android.gms.maps.model.LatLng;
//import com.google.android.gms.maps.model.Marker;
//import com.google.android.gms.maps.model.MarkerOptions;
//
//public class MapActivity extends FragmentActivity implements OnMapReadyCallback {
//
//    private GoogleMap mMap;
//    private LatLng selectedLocation;
//    private Marker marker;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_map);
//
//        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
//        if (mapFragment != null) {
//            mapFragment.getMapAsync(this);
//        }
//
//        Button btnConfirm = findViewById(R.id.btnConfirm);
//        btnConfirm.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (selectedLocation != null) {
//                    Intent resultIntent = new Intent();
//                    resultIntent.putExtra("latitude", selectedLocation.latitude);
//                    resultIntent.putExtra("longitude", selectedLocation.longitude);
//                    setResult(RESULT_OK, resultIntent);
//                    finish();
//                }
//            }
//        });
//    }
//
//    @Override
//    public void onMapReady(GoogleMap googleMap) {
//        mMap = googleMap;
//        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
//            @Override
//            public void onMapClick(LatLng latLng) {
//                selectedLocation = latLng;
//                if (marker != null) {
//                    marker.remove();
//                }
//                marker = mMap.addMarker(new MarkerOptions().position(latLng).title("Selected Location"));
//                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15));
//            }
//        });
//    }
//}
//
package UI;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;

import com.example.assignment.R;

public class MapActivity extends FragmentActivity {


    public  MapActivity() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        if (savedInstanceState == null) {
            Intent intent = getIntent();
            String latitude = intent.getStringExtra("latitude");
            String longitude = intent.getStringExtra("latitude");
            Log.d("location after detail update", latitude+", "+longitude);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, Location_Picker_Fragment.newInstance(Float.valueOf(latitude),Float.valueOf(longitude)))
                    .commit();
        }
    }
}

