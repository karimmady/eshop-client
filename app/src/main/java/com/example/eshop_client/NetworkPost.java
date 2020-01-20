package com.example.eshop_client;

import android.os.AsyncTask;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class NetworkPost extends AsyncTask<String, Void, Boolean> {

    public static int status;
    public static JSONObject jsono;
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Boolean doInBackground(String... urls){
        for(int i = 0 ; i<urls.length;i+=1) {
            try {
                HttpPost httppost = new HttpPost(urls[i]);
                httppost.setEntity(new UrlEncodedFormEntity(DataHolder.getInstance().getPostInfo()));
                HttpClient httpclient = new DefaultHttpClient();
                HttpResponse response = httpclient.execute(httppost);

                // StatusLine stat = response.getStatusLine();
                status = response.getStatusLine().getStatusCode();
                System.out.println(status);
                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity);
                    jsono = new JSONObject(data);
                    return true;
//                    if(i == 0 ) {
//                        try {
//                            SmsManager sms = SmsManager.getDefault();
//                            sms.sendTextMessage("phone:" + jsono.get("phone"), null, "body:" + jsono.get("body"), null, null);
//                        }catch (Exception e){System.out.println("No SMS to send");}
//                        if(jsono.get("ID")=="-1")
//                            return true;
//                        urls[1] += jsono.get("ID");
//                        System.out.println(urls[1]);
//                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }

    protected void onPostExecute(Boolean result) {

    }
}
