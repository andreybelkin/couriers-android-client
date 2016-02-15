package com.globalgrupp.couriers.app.classes;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by п on 02.02.2016.
 */
public class CreateResultOperation extends AsyncTask<TaskResult, Void, Boolean> {

    // Required initialization

    //private final HttpClient Client = new AndroidHttpClient();
    private String Content;
    private String Error = null;
    String data ="";

    @Override
    protected Boolean doInBackground(TaskResult... params) {
        /************ Make Post Call To Web Server ***********/
        BufferedReader reader=null;
        Log.i("doInBackground service ","doInBackground service ");
        // Send data
        try
        {

            String urlString="http://192.168.1.33:8081/service/createResult/";
            // Defined URL  where to send data
            JSONObject msg=new JSONObject();
            msg.put("taskAddressResultLinkId",params[0].getTaskAddressResultLinkId());
            msg.put("comment",params[0].getComment());
            JSONArray array=new JSONArray(params[0].getPhotoIds());
            msg.put("photoIds",array);
            msg.put("location",params[0].getLocation());
            msg.put("correctPlace",params[0].getCorrectPlace());
            msg.put("porch",params[0].getPorch());

            URL url = new URL(urlString);

            // Send POST data request

            HttpURLConnection conn =(HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent","Mozilla/5.0");
            conn.setRequestProperty("Accept","*/*");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("charset", "utf-8");
            conn.setConnectTimeout(3000);
            conn.setReadTimeout(20000);

            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            String str = msg.toString();
            byte[] data=str.getBytes("UTF-8");
            wr.write(data);
            wr.flush();
            wr.close();
            // Get the server response
            InputStream is; //todo conn.getResponseCode() for errors
            try{
                is= conn.getInputStream();
            }
            catch (Exception e){
                e.printStackTrace();
                is=conn.getErrorStream();
                return false;
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

        }
        catch(Exception ex)
        {
            Error = ex.getMessage();
            Log.d(ex.getMessage(),ex.getMessage());
            ex.printStackTrace();
            return false;

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
        return true;
    }

}
