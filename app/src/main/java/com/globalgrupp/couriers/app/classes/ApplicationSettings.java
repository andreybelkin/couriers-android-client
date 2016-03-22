package com.globalgrupp.couriers.app.classes;

import android.content.Context;
import android.content.res.AssetManager;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Andrey Belkin on 22.03.2016.
 */
public class ApplicationSettings {

    private static Context applicationContext;
    private static Properties properties;

    public static void setApplicationContext(Context applicationContext1) {
        applicationContext = applicationContext1;
    }

    public static String getProperty(String name) {
        if (properties == null) {
            try{
                AssetManager assetManager = applicationContext.getAssets();
                InputStream inputStream = assetManager.open("app.properties");
                properties = new Properties();
                properties.load(inputStream);

            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return properties.getProperty(name);
    }

    public static String getServerURL() {
        return getProperty("server.url");
    }

    private static GoogleApiClient mGoogleApiClient;

    public static GoogleApiClient getmGoogleApiClient() {
        return mGoogleApiClient;
    }

    public static void setmGoogleApiClient(GoogleApiClient mGoogleApiClient1) {
        mGoogleApiClient = mGoogleApiClient1;
    }


}
