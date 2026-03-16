package ca.dal.bartertrader.utils;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.util.Log;

import java.util.List;

public class LocationServiceManager {
    private static final String TAG = "LocationServiceManager";
    public static LocationServiceManager instance = null;

    private LocationManager locationManager;
    private double lat;
    private double lon;
    private boolean hasLocation = false;
    private Geocoder geocoder;

    private final LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            lat = location.getLatitude();
            lon = location.getLongitude();
            hasLocation = true;
        }
    };

    public LocationServiceManager(LocationManager manager, Geocoder geoCoder) {
        locationManager = manager;
        geocoder = geoCoder;
        instance = this;
    }

    public static LocationServiceManager getInstance() {
        return instance;
    }

    public void startRequestingLocationUpdates() {
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 8000, 50, locationListener);
        } catch (SecurityException e) {
            Log.e(TAG, "Location permission not granted", e);
        }
    }

    public void stopRequestingLocationUpdates() {
        locationManager.removeUpdates(locationListener);
    }

    public double getCurrentLat() { return lat; }
    public double getCurrentLon() { return lon; }

    private double getFallbackLat() { return 44.6488; }
    private double getFallbackLon() { return -63.5752; }

    public String getCityFromCurrentLocation() {
        double latToUse = hasLocation ? lat : getFallbackLat();
        double lonToUse = hasLocation ? lon : getFallbackLon();
        return getCityFromCoords(lonToUse, latToUse);
    }

    public String getProvinceFromCurrentLocation() {
        double latToUse = hasLocation ? lat : getFallbackLat();
        double lonToUse = hasLocation ? lon : getFallbackLon();
        return getProvinceFromCoords(lonToUse, latToUse);
    }

    public String getCityFromCoords(double longitude, double latitude) {
        try {
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
            if (addressList == null || addressList.isEmpty()) {
                return "Invalid City";
            }
            String city = addressList.get(0).getLocality();
            return city != null ? city : "Invalid City";
        } catch (Exception e) {
            Log.e(TAG, "Failed to get city from coordinates", e);
            return "Invalid City";
        }
    }

    public String getProvinceFromCoords(double longitude, double latitude) {
        try {
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
            if (addressList == null || addressList.isEmpty()) {
                return "Invalid province";
            }
            String province = addressList.get(0).getAdminArea();
            return province != null ? province : "Invalid province";
        } catch (Exception e) {
            Log.e(TAG, "Failed to get province from coordinates", e);
            return "Invalid province";
        }
    }
}
