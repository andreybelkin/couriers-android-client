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
import com.globalgrupp.couriers.app.classes.GetTaskResultsOperation;
import com.globalgrupp.couriers.app.classes.TaskResult;

import java.util.List;

/**
 * Created by п on 01.02.2016.
 */
public class ResultActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
    }

    @Override
    protected void onResume() {
        super.onResume();
        try{
            if (getIntent().hasExtra("taskAddressResultLinkId")){
                Long id=getIntent().getLongExtra("taskAddressResultLinkId",0);
                List<TaskResult> taskResultList= new GetTaskResultsOperation().execute(id.toString()).get();

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

                for (int i=0;i<taskResultList.size();i++){
                    LinearLayout layout = (LinearLayout) inflater.inflate(R.layout.lv_result_item,null);
                    final TaskResult fTask=taskResultList.get(i);
                    ((TextView)layout.findViewById(R.id.tvEventsTitle)).setText(fTask.getComment());
                    ((TextView)layout.findViewById(R.id.tvResultLocation)).setText(fTask.getLocation());
                    layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent detailsIntent=new Intent(getApplicationContext(),ViewResultActivity.class);
                            detailsIntent.putExtra("result",fTask);
                            startActivity(detailsIntent);

                        }
                    });
                    llevents.addView(layout);
                }
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
        resultMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Long id=getIntent().getLongExtra("taskAddressResultLinkId",0);
                Long porchCount=getIntent().getLongExtra("porchCount",0);
                String address=getIntent().getStringExtra("address");
                Intent createIntent=new Intent(getApplicationContext(),CreateResultActivity.class);
                createIntent.putExtra("resId",id);
                createIntent.putExtra("address",address);
                createIntent.putExtra("porchCount",porchCount);
                startActivity(createIntent);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_create_result) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
