package com.globalgrupp.couriers.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.globalgrupp.couriers.app.classes.ApplicationSettings;
import com.globalgrupp.couriers.app.classes.GetTasksOperation;
import com.globalgrupp.couriers.app.classes.Task;
import com.globalgrupp.couriers.app.controller.TaskDetailActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import java.util.List;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ApplicationSettings.getInstance().setmGoogleApiClient( new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build());
        ApplicationSettings.getInstance().getmGoogleApiClient().connect();


    }

    @Override
    protected void onResume() {
        super.onResume();

        try{
            List<Task> tasks= new GetTasksOperation().execute(getApplicationContext()).get();

            LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            final LinearLayout llevents=(LinearLayout)findViewById(R.id.llEvents);
            if (llevents.getChildCount() > 0){
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        llevents.removeAllViews();
                    }
                });
            }


            for (int i=0;i<tasks.size();i++){
                LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.lv_events_item ,null);
                final Task fTask=tasks.get(i);
                ((TextView)layout.findViewById(R.id.tvEventsTitle)).setText(tasks.get(i).getDescription());
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int z=v.getId();
                        Intent detailsIntent=new Intent(getApplicationContext(),TaskDetailActivity.class);
                        detailsIntent.putExtra("taskId",fTask.getId());
                        startActivity(detailsIntent);
                        //Intent
                    }
                });
                llevents.addView(layout);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem resultMenuItem=menu.findItem(R.id.action_create_result);
        resultMenuItem.setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
