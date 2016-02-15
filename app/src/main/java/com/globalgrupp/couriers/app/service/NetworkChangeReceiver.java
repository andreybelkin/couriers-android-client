package com.globalgrupp.couriers.app.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.globalgrupp.couriers.app.MainActivity;
import com.globalgrupp.couriers.app.classes.CreateResultOperation;
import com.globalgrupp.couriers.app.classes.SimpleGeoCoords;
import com.globalgrupp.couriers.app.classes.TaskResult;
import com.globalgrupp.couriers.app.classes.UploadFileOperation;
import com.globalgrupp.couriers.app.controller.CreateResultActivity;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Created by Lenovo on 15.02.2016.
 */
public class NetworkChangeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null) {
            if (info.isConnected()) {
                //start service
//                Intent intent = new Intent(this, ItemServiceManager.class);
                startService(context);
            }
            else {
                //stop service
//                Intent intent = new Intent(this, ItemServiceManager.class);
                stopService(context);
            }
        }
    }

    private void startService(Context context){

        SharedPreferences prefs = context.getSharedPreferences(
                MainActivity.class.getSimpleName(), Context.MODE_PRIVATE);
        String queueString = prefs.getString("messageQueue","[]");
        Gson gson = new Gson();
        Type listType = new TypeToken<ArrayList<TaskResult>>() {
        }.getType();
        List<TaskResult> queueResults=gson.fromJson(queueString,listType);

        Iterator<TaskResult> iter = queueResults.iterator();
        while(iter.hasNext()){
            TaskResult res=iter.next();
            try{
                boolean IsServerConnectionOk=true;
                List<Long> photoIds=new ArrayList<Long>();
                List<String> photoPathList=res.getPhotoPathList();

                if (res.getCorrectPlace()==null){
                    Geocoder geocoder = new Geocoder(context, Locale.getDefault());//Locale.getIso
                    List<Address> addresses = geocoder.getFromLocationName(res.getAddressString(), 1);
                    Address address = addresses.get(0);
                    SimpleGeoCoords addressGeoCoords=new SimpleGeoCoords();
                    addressGeoCoords.setLongitude(address.getLongitude());
                    addressGeoCoords.setLatitude(address.getLatitude());

                    Boolean correctPlace=true;
                    if (200< CreateResultActivity.distFrom(addressGeoCoords.getLatitude(),addressGeoCoords.getLongitude(),res.getCourierCoords().getLatitude(),res.getCourierCoords().getLongitude())){
                        correctPlace=false;
                    }
                    res.setCorrectPlace(correctPlace);

                    List<Address> addres= geocoder.getFromLocation(res.getCourierCoords().getLatitude(),res.getCourierCoords().getLongitude(),1);//по идее хватит и одного адреса
                    Address courierAddres=addres.get(0);
                    res.setLocation(courierAddres.getThoroughfare()+" "+ courierAddres.getSubThoroughfare());
                }

                if (photoPathList.size()>0){
                    for (int i=0;i<photoPathList.size();i++){
                        Long phId=new UploadFileOperation().execute(photoPathList.get(i)).get();
                        if (phId==null){
//                                throw new Exception("Server not available");
                            IsServerConnectionOk=false;
                            break;
                        }
                        photoIds.add(phId);
                    }
                }
                res.setPhotoIds(photoIds);
                if (!IsServerConnectionOk){
                    break; //connection problem again?
                }

                Boolean result=new CreateResultOperation().execute(res).get();
                if (result){
                    iter.remove();
                }else{
                    break; //connection problem again?
                }


            }catch (Exception e){
                e.printStackTrace();
                break; //connection problem again?
            }
        }
        SharedPreferences.Editor prefsEditor = prefs.edit();
        String json = gson.toJson(queueResults);
        prefsEditor.putString("messageQueue", "[]");
        prefsEditor.commit();
    }
    private void stopService(Context context){

    }
}
