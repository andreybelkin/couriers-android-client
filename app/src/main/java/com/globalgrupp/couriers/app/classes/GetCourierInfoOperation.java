package com.globalgrupp.couriers.app.classes;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by п on 01.02.2016.
 */
public class GetCourierInfoOperation extends AsyncTask<String, Void, Courier> {

    // Required initialization

    //private final HttpClient Client = new AndroidHttpClient();
    private String Content;
    private String Error = null;
    String data ="";

    @Override
    protected Courier doInBackground(String... params) {
        /************ Make Post Call To Web Server ***********/
        BufferedReader reader=null;
        Log.i("doInBackground service ","doInBackground service ");
        // Send data
        Courier courier=new Courier();
        try
        {
            String urlString="http://188.227.16.166:8081/service/getCourierInfo/"+params[0];
            // Defined URL  where to send data
//            JSONObject msg=new JSONObject();
//            msg.put("app_id","Asdfafd");

            URL url = new URL(urlString);

            HttpURLConnection conn =(HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("User-Agent","Mozilla/5.0");
            conn.setRequestProperty("Accept","*/*");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("charset", "utf-8");
            conn.setConnectTimeout(20000);
            conn.setReadTimeout(20000);

            InputStream is; //todo conn.getResponseCode() for errors
            try{
                is= conn.getInputStream();
            }
            catch (Exception e){
                e.printStackTrace();
                is=conn.getErrorStream();
            }
            reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line + "\n");
            }


            JSONObject jsonObject=new JSONObject(sb.toString());

            courier.setId(jsonObject.getLong("id"));
            courier.setName(jsonObject.getString("name"));
            courier.setDescription(jsonObject.getString("description"));
            courier.setAppPushId(jsonObject.getString("appPushId"));
            return courier;
        }
        catch(Exception ex)
        {
            Error = ex.getMessage();
            Log.d(ex.getMessage(),ex.getMessage());
            ex.printStackTrace();
        }
        finally
        {
            try
            {
                reader.close();
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }
        }
        return courier;
    }

}
