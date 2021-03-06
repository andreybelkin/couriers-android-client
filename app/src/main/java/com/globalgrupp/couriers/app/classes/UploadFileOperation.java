package com.globalgrupp.couriers.app.classes;

import android.os.AsyncTask;
import android.util.Log;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by п on 31.12.2015.
 */
public class UploadFileOperation extends AsyncTask<String, Void, Long> {
    @Override
    protected Long doInBackground(String... params) {

        String attachmentName = "audio";
        String attachmentFileName = "audio.3gp";
        String crlf = "\r\n";
        String twoHyphens = "--";
        String boundary =  "*****";
        /************ Make Post Call To Web Server ***********/
        BufferedReader reader=null;
        Log.i("doInBackground service ","doInBackground service ");
        // Send data

        try
        {

            String urlString=ApplicationSettings.getServerURL() + "/service/uploadFile";



            URL url = new URL(urlString);

            // Send POST data request

            HttpURLConnection conn =(HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(3000);
            conn.setRequestMethod("PUT");
            conn.setRequestProperty("User-Agent","Mozilla/5.0");
            conn.setRequestProperty("Accept","*/*");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Cache-Control", "no-cache");
            conn.setRequestProperty(
                    "Content-Type", "multipart/form-data;boundary=" + boundary);

            DataOutputStream wr = new DataOutputStream(conn.getOutputStream());
            File file=new File(params[0]);
            byte[] data = new byte[(int) file.length()];
            try {
                new FileInputStream(file).read(data);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
            }

            reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line = null;

            // Read Server Response
            while((line = reader.readLine()) != null)
            {
                // Append server response in string
                sb.append(line);
            }

            Long result=new Long(sb.toString());
            return result;
        }
        catch(Exception ex)
        {
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

        return null;
    }
}
