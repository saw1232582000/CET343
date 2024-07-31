package LoactionHandlerWIthMapBox;
import android.content.Context;
import android.location.Location;
import android.os.Looper;
import android.util.Log;
import androidx.annotation.NonNull;


import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineCallback;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.location.LocationEngineRequest;
import com.mapbox.android.core.location.LocationEngineResult;

public class LocationHandlerWithMapBox {
    private static final long DEFAULT_INTERVAL_IN_MILLISECONDS = 1000L;
    private static final long DEFAULT_MAX_WAIT_TIME = DEFAULT_INTERVAL_IN_MILLISECONDS * 5;
    private LocationEngine locationEngine;
    private Context context;
    private LocationCallback locationCallback;


    public interface LocationCallback {
        void onLocationResult(Location location);
        void onLocationError(Exception exception);
    }

    public LocationHandlerWithMapBox(Context context) {
        this.context = context;
        initializeLocationEngine();
    }

    @SuppressWarnings("MissingPermission")
    private void initializeLocationEngine() {
        locationEngine = LocationEngineProvider.getBestLocationEngine(context);

        LocationEngineRequest request = new LocationEngineRequest.Builder(DEFAULT_INTERVAL_IN_MILLISECONDS)
                .setPriority(LocationEngineRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(DEFAULT_MAX_WAIT_TIME).build();

        locationEngine.requestLocationUpdates(request, locationEngineCallback, Looper.getMainLooper());
        locationEngine.getLastLocation(locationEngineCallback);
    }

    private final LocationEngineCallback<LocationEngineResult> locationEngineCallback = new LocationEngineCallback<LocationEngineResult>() {
        @Override
        public void onSuccess(LocationEngineResult result) {
            Location location = result.getLastLocation();
            if (location != null) {
                if (locationCallback != null) {
                    locationCallback.onLocationResult(location);
                }
            }
        }

        @Override
        public void onFailure(@NonNull Exception exception) {
            if (locationCallback != null) {
                locationCallback.onLocationError(exception);
            }
        }
    };

    public void setLocationCallback(LocationCallback callback) {
        this.locationCallback = callback;
    }

    public void removeLocationUpdates() {
        if (locationEngine != null) {
            locationEngine.removeLocationUpdates(locationEngineCallback);
        }
    }
}
