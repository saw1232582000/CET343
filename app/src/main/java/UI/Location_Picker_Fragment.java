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
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.assignment.R;
import com.mapbox.api.geocoding.v5.MapboxGeocoding;
import com.mapbox.api.geocoding.v5.models.CarmenFeature;
import com.mapbox.api.geocoding.v5.models.GeocodingResponse;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private SearchView searchView;

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
        searchView = view.findViewById(R.id.searchView);
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
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                performSearch(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        return view;
    }

    private void performSearch(String query) {
        MapboxGeocoding mapboxGeocoding = MapboxGeocoding.builder()
                .accessToken(getString(R.string.mapbox_access_token))
                .query(query)
                .build();

        mapboxGeocoding.enqueueCall(new Callback<GeocodingResponse>() {
            @Override
            public void onResponse(Call<GeocodingResponse> call, Response<GeocodingResponse> response) {
                if (response.body() != null && response.body().features().size() > 0) {
                    CarmenFeature feature = response.body().features().get(0);
                    Point point = feature.center();
                    addSymbol(new LatLng(point.latitude(), point.longitude()), feature.placeName());
                    mapboxMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(point.latitude(), point.longitude()), 14));


                }
            }

            @Override
            public void onFailure(Call<GeocodingResponse> call, Throwable t) {
                Log.e("MainActivity", "Geocoding Failure: " + t.getMessage());
            }
        });
    }
    private void addSymbol(LatLng position, String title) {
        symbolManager.create(new SymbolOptions()
                .withLatLng(position)
                .withIconImage("marker-icon")
                .withTextField(title)
                .withTextOffset(new Float[]{0f, -2f})
                .withTextAnchor("top")
                .withIconSize(1.0f)
        );
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
