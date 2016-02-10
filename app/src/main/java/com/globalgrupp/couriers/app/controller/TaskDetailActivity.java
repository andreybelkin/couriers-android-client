package com.globalgrupp.couriers.app.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.globalgrupp.couriers.app.R;
import com.globalgrupp.couriers.app.classes.Address;
import com.globalgrupp.couriers.app.classes.GetTaskByIdOperation;
import com.globalgrupp.couriers.app.classes.Task;

import java.util.List;

/**
 * Created by п on 01.02.2016.
 */
public class TaskDetailActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_task);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (getIntent().hasExtra("taskId")){
            try{
                Long id=getIntent().getLongExtra("taskId",0);
                List<Task> tasks= new GetTaskByIdOperation().execute( id.toString()  ).get();

                LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final LinearLayout llevents=(LinearLayout)findViewById(R.id.llEvents);
                if (llevents.getChildCount() > 0){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            llevents.removeAllViews();
                        }
                    });
                }

                Task task=tasks.get(0);
                List<Address> addressList=task.getAddressList();
                for (int i=0;i<addressList.size();i++){
                    LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.lv_address_item ,null);
                    final Address fTask=addressList.get(i);
                    ((TextView)layout.findViewById(R.id.tvEventsTitle)).setText(fTask.getStreet()+" "+ fTask.getHouseNumber());
                    layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int z=v.getId();
                            Intent detailsIntent=new Intent(getApplicationContext(),ResultActivity.class);
                            detailsIntent.putExtra("taskAddressResultLinkId",fTask.getTaskAddresResultLinkId());
                            detailsIntent.putExtra("address",fTask.getStreet()+" "+fTask.getHouseNumber());
                            detailsIntent.putExtra("porchCount",fTask.getPorchCount());

                            startActivity(detailsIntent);
                        }
                    });
                    llevents.addView(layout);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

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
}
