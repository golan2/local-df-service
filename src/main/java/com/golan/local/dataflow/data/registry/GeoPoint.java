package com.golan.local.dataflow.data.registry;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

class GeoPoint {
    private static final String LATITUDE_FIELD = "latitude";
    private static final String LONGITUDE_FIELD = "longitude";
    private static final String ELEVATION_FIELD = "elevation";
    private static final String ALTITUDE_FIELD = "altitude";

    private final Double latitude;
    private final Double longitude;
    private final Double altitude;
    private final Double elevation;

    private GeoPoint(Double latitude, Double longitude, Double altitude, Double elevation) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.altitude = altitude;
        this.elevation = elevation;
    }

    public static GeoPoint parse(Object value){
        if (value instanceof GeoPoint){
            return (GeoPoint)value;
        }
        else if (value instanceof String){
            return parseString((String)value);
        }
        else if (value instanceof JSONObject){
            return parseJsonObject((JSONObject) value);
        }
        else{
            return null;
        }
    }


    private static GeoPoint parseJsonObject(JSONObject jsonGeoPoint){
        Double localLatitude;
        Double localLongitude;
        Double localAltitude = null;
        Double localElevation = null;
        if (jsonGeoPoint.length() == 2 ){
            localLatitude = optDoubleOrDefaultNull(jsonGeoPoint, LATITUDE_FIELD);
            localLongitude = optDoubleOrDefaultNull(jsonGeoPoint, LONGITUDE_FIELD);
            if (localLatitude == null || localLongitude == null){
                return null;
            }
        }
        else if (jsonGeoPoint.length() == 3) {
            localLatitude = optDoubleOrDefaultNull(jsonGeoPoint, LATITUDE_FIELD);
            localLongitude = optDoubleOrDefaultNull(jsonGeoPoint, LONGITUDE_FIELD);
            localAltitude = optDoubleOrDefaultNull(jsonGeoPoint, ALTITUDE_FIELD);
            localElevation = optDoubleOrDefaultNull(jsonGeoPoint, ELEVATION_FIELD);
            if (localAltitude == null && localElevation == null) {
                return null;
            }
        }
        else {
            return null;
        }

        return new GeoPoint(localLatitude, localLongitude, localAltitude, localElevation);
    }

    private static Double optDoubleOrDefaultNull(JSONObject jsonGeoPoint, String fieldName) {
        Double value = jsonGeoPoint.optDouble(fieldName, Double.NaN);
        if (!value.isNaN()){
            return value;
        }
        else{
            return null;
        }
    }

    private static GeoPoint parseString(String jsonString){
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonString);
            return parseJsonObject(jsonObject);
        }
        catch(JSONException e){
            return null;
        }
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Double getAltitude() {
        return altitude;
    }

    public Double getElevation() {
        return elevation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GeoPoint geoPoint = (GeoPoint) o;
        return Objects.equals(latitude, geoPoint.latitude) &&
                Objects.equals(longitude, geoPoint.longitude) &&
                Objects.equals(altitude, geoPoint.altitude) &&
                Objects.equals(elevation, geoPoint.elevation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude, altitude, elevation);
    }
}
