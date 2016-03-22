package com.globalgrupp.couriers.app.classes;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import com.globalgrupp.couriers.app.util.GCMRegistrationHelper;
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
public class GetTasksOperation extends AsyncTask<Context, Void, List<Task>> {

    // Required initialization

    //private final HttpClient Client = new AndroidHttpClient();
    private String Content;
    private String Error = null;
    String data ="";

    @Override
    protected List<Task> doInBackground(Context... params) {
        /************ Make Post Call To Web Server ***********/
        BufferedReader reader=null;
        Log.i("doInBackground service ","doInBackground service ");
        // Send data
        List<Task> result=new ArrayList<Task>();
        try
        {
            String urlString=ApplicationSettings.getServerURL() + "/service/getMyTasks/"+GCMRegistrationHelper.getRegistrationId(params[0]);
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

//                JSONArray jsonCommentsArray= jsonObject.getJSONArray("comments");
//                ArrayList<Comment> comments=new ArrayList<Comment>();
//                for (int k=0;k<jsonCommentsArray.length();k++){
//                    JSONObject comObject=jsonCommentsArray.getJSONObject(k);
//                    Comment com=new Comment();
//                    com.setId(comObject.getLong("id"));
//                    com.setMessage(comObject.getString("message"));
//                    com.setCreateDate(new Date(comObject.getLong("createDate")));
//                    com.setAudioId(!comObject.isNull("audioId")?comObject.getLong("audioId"):null);
//                    com.setVideoId(!comObject.isNull("videoId")?comObject.getLong("videoId"):null);
//                    List<Long> commentPhotoIds=new ArrayList<Long>();
//                    JSONArray commentPhotoArray=comObject.getJSONArray("photoIds");
//                    for (int z=0;z<commentPhotoArray.length(); z++){
//                        Long photoId=commentPhotoArray.getLong(z);
//                        commentPhotoIds.add(photoId);
//                    }
//                    com.setPhotoIds(commentPhotoIds);
//                    comments.add(com);
//                }
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
