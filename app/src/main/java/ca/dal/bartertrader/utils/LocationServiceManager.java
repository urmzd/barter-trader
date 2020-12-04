package ca.dal.bartertrader.utils;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import java.util.List;

public class LocationServiceManager {
    public static LocationServiceManager instance = null;

    private LocationManager locationManager;
    private double lat;
    private double lon;
    private Geocoder geocoder;

    private final LocationListener locationListener =  new LocationListener() {
        public void onLocationChanged(Location location) {
            lat = location.getLatitude();
            lon = location.getLongitude();
        }
    };

    public LocationServiceManager(LocationManager manager, Geocoder geoCoder)
    {
        locationManager = manager;
        geocoder = geoCoder;
        instance = this;
    }

    public static LocationServiceManager getInstance()
    {
        return instance;
    }

    public void startRequestingLocationUpdates()
    {
        try
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 8000, 50, locationListener);
        }
        catch (SecurityException e)
        {
            e.printStackTrace();
        }

    }

    public double getCurrentLat() { return lat; }
    public double getCurrentLon() { return lon; }

    private double getFallbackLat() { return 44.6488; }
    private double getFallbackLon() { return -63.5752; }

    public String getCityFromCurrentLocation()
    {
        double latToUse = lat, lonToUse = lon;
        if ( lat == 0 )
        {
            latToUse = getFallbackLat();
        }
        if ( lon == 0 )
        {
            lonToUse = getFallbackLon();
        }
        return getCityFromCoords(lonToUse, latToUse);
    }

    public String getProvinceFromCurrentLocation()
    {
        double latToUse = lat, lonToUse = lon;
        if ( lat == 0 )
        {
            latToUse = getFallbackLat();
        }
        if ( lon == 0 )
        {
            lonToUse = getFallbackLon();
        }
        return getProvinceFromCoords(lonToUse, latToUse);
    }

    public String getCityFromCoords(double longitude, double latitude)
    {
        try
        {
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
            String city = addressList.get(0).getLocality();
            return city;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return "Invalid City";
        }
    }

    public String getProvinceFromCoords(double longitude, double latitude)
    {
        try
        {
            List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
            String province = addressList.get(0).getAdminArea();
            return province;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return "Invalid province";
        }
    }
}
