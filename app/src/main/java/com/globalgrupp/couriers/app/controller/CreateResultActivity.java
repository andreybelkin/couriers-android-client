package com.globalgrupp.couriers.app.controller;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.globalgrupp.couriers.app.R;
import com.globalgrupp.couriers.app.classes.ApplicationSettings;
import com.globalgrupp.couriers.app.classes.CreateResultOperation;
import com.globalgrupp.couriers.app.classes.TaskResult;
import com.globalgrupp.couriers.app.classes.UploadFileOperation;
import com.google.android.gms.location.LocationServices;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by п on 02.02.2016.
 */
public class CreateResultActivity extends AppCompatActivity {
    private String country="Россия";
    private String city="Пермь";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_result);

        NumberPicker numberPicker=(NumberPicker)findViewById(R.id.numberPicker);
        numberPicker.setMinValue(1);

        numberPicker.setValue(1);
        Long porchCount=getIntent().getLongExtra("porchCount",1);;
        numberPicker.setMaxValue(porchCount.intValue());

        Button sendButton=(Button)findViewById(R.id.btnSend);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    String myLocation=country+" "+city+" "+getIntent().getStringExtra("address");
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());//Locale.getIso
                    List<Address> addresses = geocoder.getFromLocationName(myLocation, 1);
                    Address address = addresses.get(0);
                    double longitude = address.getLongitude();
                    double latitude = address.getLatitude();

                    Location mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                            ApplicationSettings.getInstance().getmGoogleApiClient());
                    Geocoder gc=new Geocoder(getApplicationContext(), Locale.getDefault());
                    List<Address> addres= gc.getFromLocation(mLastLocation.getLatitude(),mLastLocation.getLongitude(),1);//по идее хватит и одного адреса
                    Address eAddres=addres.get(0);

                    boolean correctPlace=true;
                    if (200<distFrom(latitude,longitude,mLastLocation.getLatitude(),mLastLocation.getLongitude())){
                        correctPlace=false;
                    }

                    List<Long> photoIds=new ArrayList<Long>();
                    if (photoPathList.size()>0){
                        for (int i=0;i<photoPathList.size();i++){
                            Long phId=new UploadFileOperation().execute(photoPathList.get(i)).get();
                            photoIds.add(phId);
                        }
                    }


                    TaskResult result=new TaskResult();
                    EditText etComment=(EditText)findViewById(R.id.etEventText);
                    result.setComment(etComment.getText().toString());
                    result.setPhotoIds(photoIds);
                    result.setCorrectPlace(correctPlace);
                    NumberPicker numberPicker=(NumberPicker)findViewById(R.id.numberPicker);
                    result.setPorch(String.valueOf(numberPicker.getValue()));
                    result.setLocation(eAddres.getThoroughfare()+" "+ eAddres.getSubThoroughfare());
                    Long resId=getIntent().getLongExtra("resId",0);
                    result.setTaskAddressResultLinkId(resId);

                    new CreateResultOperation().execute(result).get();
                    finish();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        Button photoButton=(Button)findViewById(R.id.btnPhoto);
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });


    }

    private static double distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371000; //meters
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                        Math.sin(dLng/2) * Math.sin(dLng/2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = (double) (earthRadius * c);

        return dist;
    }



    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {

            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                //mCurrentPhotoPath=Uri.fromFile(photoFile).toString();
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile));
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath =  image.getAbsolutePath();//"file:" +
        return image;
    }


    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_VIDEO_CAPTURE = 2;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            try{
                setPic();
            }catch(Exception e){
                Log.e("photoActivityResult",e.getMessage());
            }
        }
    }

    String mCurrentPhotoPath;
    ArrayList<String> photoPathList=new ArrayList<String>();

    private void setPic() {
        try {
            ViewGroup.LayoutParams phLayoutParams = findViewById(R.id.trImageRow).getLayoutParams();
            phLayoutParams.height = 150;
            findViewById(R.id.trImageRow).setLayoutParams(phLayoutParams);
            findViewById(R.id.trImageRow).setVisibility(View.VISIBLE);
            LinearLayout llImages=(LinearLayout)findViewById(R.id.llImages);
            ImageView ivNew=new ImageView(getApplicationContext());
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(150,150);
            ivNew.setLayoutParams(layoutParams);
            //ivNew.setBackgroundColor(Color.parseColor("#D8D8DA"));
            ivNew.setPadding(5,5,5,5);
            llImages.addView(ivNew);
            BitmapFactory.Options bmOptions = new BitmapFactory.Options();
            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath.replace("file:", ""), bmOptions);
            String filePath=mCurrentPhotoPath.replace("JPEG","qwer");
            Bitmap bmp= Bitmap.createScaledBitmap(bitmap,(int)(bitmap.getWidth()*0.6), (int)(bitmap.getHeight()*0.6), true);
            File file = new File(filePath);
            FileOutputStream fOut = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 85, fOut);
            fOut.flush();
            fOut.close();


            Bitmap bmPhoto= Bitmap.createScaledBitmap(bitmap, 150, 150, true);
            ivNew.setImageBitmap(bmPhoto);
            ivNew.setClickable(true);
            mCurrentPhotoPath=filePath;
            final String path=mCurrentPhotoPath;
            ivNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse("file://"+path), "image/*");
                    startActivity(intent);
                }
            });
            photoPathList.add(mCurrentPhotoPath);
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
        if (id == R.id.action_create_result) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
