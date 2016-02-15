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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by п on 01.02.2016.
 */
public class GetTaskResultsOperation extends AsyncTask<String, Void, List<TaskResult>> {

    // Required initialization

    //private final HttpClient Client = new AndroidHttpClient();
    private String Content;
    private String Error = null;
    String data ="";

    @Override
    protected List<TaskResult> doInBackground(String... params) {
        /************ Make Post Call To Web Server ***********/
        BufferedReader reader=null;
        Log.i("doInBackground service ","doInBackground service ");
        // Send data
        List<TaskResult> result=new ArrayList<TaskResult>();
        try
        {
            String urlString="http://192.168.1.33:8081/service/getTaskResult/";
            // Defined URL  where to send data
            JSONObject msg=new JSONObject();
            msg.put("taskAddressResultLinkId",new Long(params[0]));

            URL url = new URL(urlString);

            // Send POST data request

            HttpURLConnection conn =(HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent","Mozilla/5.0");
            conn.setRequestProperty("Accept","*/*");
            conn.setRequestProperty("Content-Type","application/json");
            conn.setRequestProperty("charset", "utf-8");
            conn.setConnectTimeout(20000);
            conn.setReadTimeout(20000);

            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            String str = msg.toString();
            byte[] data=str.getBytes("UTF-8");
            wr.write(data);
            wr.flush();
            wr.close();

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

            JSONArray jsonResponseArray=new JSONArray(sb.toString());
            for (int i=0;i<jsonResponseArray.length();i++){
                JSONObject jsonObject=jsonResponseArray.getJSONObject(i);
                TaskResult e=new TaskResult();
                e.setComment(jsonObject.getString("comment"));
                e.setId(jsonObject.getLong("id"));
                e.setLocation(jsonObject.getString("location"));
                List<Long> photoIds=new ArrayList<Long>();
                JSONArray photoArray=jsonObject.getJSONArray("photoIds");
                for (int k=0;k<photoArray.length(); k++){
                    Long photoId=photoArray.getLong(k);
                    photoIds.add(photoId);
                }
                e.setPhotoIds(photoIds);


                result.add(e);
            }
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
        return result;
    }

}