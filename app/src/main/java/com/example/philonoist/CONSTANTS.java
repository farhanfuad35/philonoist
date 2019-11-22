package com.example.philonoist;

import com.backendless.BackendlessUser;
import com.backendless.geo.GeoPoint;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

public class CONSTANTS {
    final private static String TAG_MAPS_SHOW_TUITIONS = "#Maps_Show_Tuitions";
    private static String TAG_MAPS_GETGEOPOINTS = "Get GeoPoints";
    private static double LAT_MIN = 23.680563;
    private static double LAT_MAX = 23.892885;
    private static double LNG_MIN = 90.332829;
    private static double LNG_MAX = 90.451891;
    private static int ACTIVITY_ID_MAPS_SHOW_TUITIONS = 65;
    private static int ACTIVITY_ID_NOTIFICATIONSPAGE = 95;
    private static int ACTIVITY_ID_MYOFFERS = 85;
    private static int ACTIVITY_ID_TUITIONLIST = 75;
    final private static BackendlessUser currentUser = new BackendlessUser();       // To be used as distinct property of the current user
    private static List<GeoPoint> geoPointList;
    private static BackendlessUser currentSavedUser;                                // To be saved as a whole BackendlessUser from login activity

    public static BackendlessUser getCurrentSavedUser() {
        return currentSavedUser;
    }

    public static void setCurrentSavedUser(BackendlessUser currentSavedUser) {
        CONSTANTS.currentSavedUser = currentSavedUser;
    }

    public static int getActivityIdMapsShowTuitions() {
        return ACTIVITY_ID_MAPS_SHOW_TUITIONS;
    }

    public static int getActivityIdTuitionlist() {
        return ACTIVITY_ID_TUITIONLIST;
    }

    public static List<GeoPoint> getGeoPointList() {
        return geoPointList;
    }

    public static void setGeoPointList(List<GeoPoint> geoPointList) {
        CONSTANTS.geoPointList = geoPointList;
    }

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

    public static int getActivityIdMyoffers() {
        return ACTIVITY_ID_MYOFFERS;
    }


    public static int getActivityIdNotificationspage() {
        return ACTIVITY_ID_NOTIFICATIONSPAGE;
    }



    public  static List<Offer> offers;


}
