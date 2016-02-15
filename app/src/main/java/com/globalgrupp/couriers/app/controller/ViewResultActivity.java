package com.globalgrupp.couriers.app.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.globalgrupp.couriers.app.R;
import com.globalgrupp.couriers.app.classes.FileDownloadTask;
import com.globalgrupp.couriers.app.classes.TaskResult;

import java.util.List;

/**
 * Created by п on 02.02.2016.
 */
public class ViewResultActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_result);
        if (getIntent().hasExtra("result")){
            TaskResult taskResult=(TaskResult)(getIntent().getSerializableExtra("result"));
            ((TextView)findViewById(R.id.etEventText)).setText(taskResult.getComment());
            if (taskResult.getPhotoIds()!=null&&taskResult.getPhotoIds().size()>0){
                List<Long> photoIds=taskResult.getPhotoIds();
                for (int i=0;i<photoIds.size();i++){
                    try {

                        final String photoFilePath=new FileDownloadTask().execute("http://192.168.1.33:8081/service/getFile/"+photoIds.get(i),"jpg").get();

                        ViewGroup.LayoutParams phLayoutParams = findViewById(R.id.trImageRow).getLayoutParams();
                        phLayoutParams.height =150;
                        findViewById(R.id.trImageRow).setLayoutParams(phLayoutParams);
                        findViewById(R.id.trImageRow).setVisibility(View.VISIBLE);
                        LinearLayout llImages=(LinearLayout)findViewById(R.id.llImages);
                        ImageView ivNew=new ImageView(getApplicationContext());
                        LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(150,150);
                        ivNew.setLayoutParams(layoutParams);
                        ivNew.setPadding(5,5,5,5);
                        llImages.addView(ivNew);
                        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                        Bitmap bitmap = BitmapFactory.decodeFile(photoFilePath.replace("file:", ""), bmOptions);
                        Bitmap bmPhoto= Bitmap.createScaledBitmap(bitmap, 150, 150, true);
                        ivNew.setImageBitmap(bmPhoto);
                        ivNew.setClickable(true);
                        final String path=photoFilePath;
                        ivNew.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent();
                                intent.setAction(Intent.ACTION_VIEW);
                                intent.setDataAndType(Uri.parse("file://"+path), "image/*");
                                startActivity(intent);
                            }
                        });
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                }

            }else {


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
        if (id == R.id.action_create_result) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
