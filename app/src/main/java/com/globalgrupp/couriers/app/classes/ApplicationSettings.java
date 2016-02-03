package com.globalgrupp.couriers.app.classes;

import com.google.android.gms.common.api.GoogleApiClient;

/**
 * Created by Lenovo on 14.01.2016.
 */
public class ApplicationSettings {
    private static ApplicationSettings instance =null;
    protected ApplicationSettings(){

    }
    public static ApplicationSettings getInstance(){
        if(instance == null) {
            instance = new ApplicationSettings();
        }
        return instance;
    }

    private GoogleApiClient mGoogleApiClient;

    public GoogleApiClient getmGoogleApiClient() {
        return mGoogleApiClient;
    }

    public void setmGoogleApiClient(GoogleApiClient mGoogleApiClient) {
        this.mGoogleApiClient = mGoogleApiClient;
    }


}
