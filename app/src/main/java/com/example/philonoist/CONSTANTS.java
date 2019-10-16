package com.example.philonoist;

import com.backendless.BackendlessUser;

public class CONSTANTS {
    final private static String TAG_MAPS_SHOW_TUITIONS = "#Maps_Show_Tuitions";
    private static String TAG_MAPS_GETGEOPOINTS = "Get GeoPoints";
    private static double LAT_MIN = 23.680563;
    private static double LAT_MAX = 23.892885;
    private static double LNG_MIN = 90.332829;
    private static double LNG_MAX = 90.451891;
    final private static BackendlessUser currentUser = new BackendlessUser();

    public static String getCurrentUserEmail() {
        return currentUser.getEmail();
    }

    public static void setCurrentUserEmail(String email) {
        currentUser.setEmail(email);
    }

    public static String getTagMapsGetgeopoints() {
        return TAG_MAPS_GETGEOPOINTS;
    }

    public static String getTAG_MAPS_SHOW_TUITIONS() {
        return TAG_MAPS_SHOW_TUITIONS;
    }

    public static void setLngMax(double lngMax) {
        LNG_MAX = lngMax;
    }


    public static double getLatMin() {
        return LAT_MIN;
    }

    public static void setLatMin(double latMin) {
        LAT_MIN = latMin;
    }

    public static void setLatMax(double latMax) {
        LAT_MAX = latMax;
    }

    public static void setLngMin(double lngMin) {
        LNG_MIN = lngMin;
    }

    public static double getLatMax() {
        return LAT_MAX;
    }

    public static double getLngMin() {
        return LNG_MIN;
    }

    public static double getLngMax() {
        return LNG_MAX;
    }


}
