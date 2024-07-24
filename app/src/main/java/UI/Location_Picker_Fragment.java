package UI;

import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.assignment.R;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mapbox.mapboxsdk.plugins.annotation.Symbol;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolManager;
import com.mapbox.mapboxsdk.plugins.annotation.SymbolOptions;

import LoactionHandlerWIthMapBox.LocationHandlerWithMapBox;

public class Location_Picker_Fragment extends Fragment {
    private static final String TAG = "LocationPickerFragment";
    private LocationHandlerWithMapBox locationHandlerWithMapBox;
    private MapView mapView;
    private MapboxMap mapboxMap;
    private SymbolManager symbolManager;
    private Symbol selectedLocationSymbol;
    private LatLng selectedLocation;

    private double current_latitude;
    private double current_longitude;
    private static final String ARG_LATITUDE = "latitude";
    private static final String ARG_LONGITUDE = "longitude";


    public Location_Picker_Fragment() {
        // Required empty public constructor
    }
    public Location_Picker_Fragment(double latitude, double longitude) {
        // Required empty public constructor
    }
    public static Location_Picker_Fragment newInstance(double latitude, double longitude) {
        Location_Picker_Fragment fragment = new Location_Picker_Fragment();
        Bundle args = new Bundle();
        args.putDouble(ARG_LATITUDE, latitude);
        args.putDouble(ARG_LONGITUDE, longitude);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            current_latitude = getArguments().getDouble(ARG_LATITUDE);
            current_longitude = getArguments().getDouble(ARG_LONGITUDE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_box_location_picker, container, false);
        mapView = view.findViewById(R.id.mapBoxLocationPicker);
        Button confirmButton = view.findViewById(R.id.location_select_button);

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                Location_Picker_Fragment.this.mapboxMap = mapboxMap;
                LatLng initialLocation = new LatLng(current_latitude, current_longitude);
                Log.d("location after detail update", initialLocation.toString());
//                LatLng initialLocation = new LatLng(0, 0);
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 16.0));
                        symbolManager = new SymbolManager(mapView, mapboxMap, style);
                        symbolManager.setIconAllowOverlap(true);
                        symbolManager.setIconIgnorePlacement(true);
                        SymbolOptions symbolOptions = new SymbolOptions()
                                .withLatLng(initialLocation)
                                .withIconImage("marker-15")
                                .withIconSize(4.0f);
                        Symbol symbol = symbolManager.create(symbolOptions);
                        // Set map click listener

                    }
                });
                mapboxMap.addOnMapClickListener(new MapboxMap.OnMapClickListener() {
                    @Override
                    public boolean onMapClick(@NonNull LatLng point) {
                        if (selectedLocationSymbol != null) {
                            symbolManager.delete(selectedLocationSymbol);
                        }
                        Toast.makeText(Location_Picker_Fragment.this.getActivity(), "Current location: " + String.valueOf(point.getLatitude())+ ", " + String.valueOf(point.getLongitude()), Toast.LENGTH_LONG).show();
                        selectedLocation = point;
                        SymbolOptions symbolOptions = new SymbolOptions()
                                .withLatLng(point)
                                .withIconImage("marker-15")
                                .withIconSize(2.0f);
                        selectedLocationSymbol = symbolManager.create(symbolOptions);
                        return true;
                    }
                });
            }
        });

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (selectedLocation != null) {
                    Intent intent = new Intent();
                    intent.putExtra("latitude", selectedLocation.getLatitude());
                    intent.putExtra("longitude", selectedLocation.getLongitude());
                    getActivity().setResult(Activity.RESULT_OK, intent);
                    getActivity().finish();
                }
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

}
