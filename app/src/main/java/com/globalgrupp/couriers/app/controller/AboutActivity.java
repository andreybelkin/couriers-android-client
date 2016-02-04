package com.globalgrupp.couriers.app.controller;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import com.globalgrupp.couriers.app.R;
import com.globalgrupp.couriers.app.classes.Courier;
import com.globalgrupp.couriers.app.classes.GetCourierInfoOperation;
import com.globalgrupp.couriers.app.util.GCMRegistrationHelper;

/**
 * Created by п on 04.02.2016.
 */
public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        try{
            String regId= GCMRegistrationHelper.getRegistrationId(getApplicationContext());
            Courier courier=new GetCourierInfoOperation().execute(regId).get();
            TextView tvId=(TextView)findViewById(R.id.tvId);
            tvId.setText("Курьер "+courier.getId());
            if (courier.getName()!=null){
                TextView tvName=(TextView)findViewById(R.id.tvName);
                tvName.setText(courier.getName());
            }
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
