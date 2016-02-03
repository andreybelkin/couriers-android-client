package com.globalgrupp.couriers.app.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import com.globalgrupp.couriers.app.util.GCMRegistrationHelper;

/**
 * Created by п on 01.02.2016.
 */
public class GCMUpdateReceiver extends BroadcastReceiver {
    Context currentContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        currentContext=context;
        GCMRegistrationHelper helper=new GCMRegistrationHelper(context);
        helper.registerGCM();
    }
}
