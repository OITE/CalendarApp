package ume.oite.jp.calendarapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by Ume on 2015/07/15.
 */
public class HttpPostTask extends AsyncTask<String,String,String> {

    private Activity parentActivity = null;
    private HttpURLConnection connection = null;
    private Map<String,String> params = null;
    private Handler ui_handler = null;
    private ProgressDialog dialog = null;


    public HttpPostTask(Activity parentActivity , Handler ui_handler){
        this.parentActivity = parentActivity;
        this.ui_handler = ui_handler;
        params = new LinkedHashMap<String,String>();
    }

    public void setParam(String key,String value){
        this.params.put(key,value);
    }
    public void setParams(LinkedHashMap<String,String>params){
        this.params.putAll(params);
    }

    @Override
    protected void onPreExecute(){
        //dialog = new ProgressDialog( parentActivity );
        //dialog.setMessage("通信中・・・");
        //dialog.show();
    }

    @Override
    protected String doInBackground(String... ad) {
        String ret = "";
        try{
            String address = ad[0];
            URL url = new URL("http://"+address);

            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));

            connection.connect();

            StringBuilder result = new StringBuilder();
            Set<Map.Entry<String,String>> set = params.entrySet();
            Iterator<Map.Entry<String,String>> it = set.iterator();
            while(it.hasNext()){
                Map.Entry<String,String> e = it.next();
                result.append(e.getKey() + "=" + e.getValue());
                if(it.hasNext())result.append("&");
            }

            writer.write(result.toString());
            writer.flush();

            BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            ret = reader.readLine();

            writer.close();
            os.close();


        }catch(Exception e){
            Log.d("Exception",e.toString());
        }finally{
            Log.d("debug","finnaly");
            connection.disconnect();
        }
        return ret;
    }

    @Override
    protected void onPostExecute(String params) {
        //dialog.dismiss();

        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putBoolean("http_post_success",true);
        bundle.putString("http_response",params);
        message.setData(bundle);

        ui_handler.sendMessage(message);
    }

}


