package com.globalgrupp.couriers.app.classes;

import android.os.AsyncTask;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by п on 01.02.2016.
 */
public class GetTaskByIdOperation extends AsyncTask<String, Void, List<Task>> {

    // Required initialization

    //private final HttpClient Client = new AndroidHttpClient();
    private String Content;
    private String Error = null;
    String data ="";

    @Override
    protected List<Task> doInBackground(String... params) {
        /************ Make Post Call To Web Server ***********/
        BufferedReader reader=null;
        Log.i("doInBackground service ","doInBackground service ");
        // Send data
        List<Task> result=new ArrayList<Task>();
        try
        {
            String urlString=ApplicationSettings.getServerURL() + "/service/getTaskById/"+params[0];
            // Defined URL  where to send data
//            JSONObject msg=new JSONObject();
//            msg.put("app_id","Asdfafd");

            URL url = new URL(urlString);

            // Send POST data request

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

            JSONArray jsonResponseArray=new JSONArray(sb.toString());
            for (int i=0;i<jsonResponseArray.length();i++){
                JSONObject jsonObject=jsonResponseArray.getJSONObject(i);
                Task e=new Task();
                e.setDescription(jsonObject.getString("description"));
                e.setId(jsonObject.getLong("id"));

                List<Long> photoIds=new ArrayList<Long>();

                JSONArray addresses= jsonObject.getJSONArray("taskAddressResultLinks");
//                ArrayList<Comment> comments=new ArrayList<Comment>();
                ArrayList<Address> addressArrayList=new ArrayList<Address>();
                for (int k=0;k<addresses.length();k++){

                    JSONObject comObject=addresses.getJSONObject(k);
                    JSONObject addressObject=comObject.getJSONObject("address");
                    Address address=new Address();
                    address.setStreet(addressObject.getString("street"));
                    address.setHouseNumber(addressObject.isNull("houseNumber")?"":addressObject.getString("houseNumber"));
                    address.setTaskAddresResultLinkId(comObject.getLong("id"));
                    address.setPorchCount(addressObject.isNull("porchCount")?15:addressObject.getLong("porchCount"));
//                    address.setPorchCount(addressObject.getLong("porchCount"));
                    addressArrayList.add(address);
                }
                e.setAddressList(addressArrayList);
//                e.setComments(comments);
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
