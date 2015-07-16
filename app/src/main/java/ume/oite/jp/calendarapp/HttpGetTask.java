package ume.oite.jp.calendarapp;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Ume on 2015/07/16.
 */
public class HttpGetTask extends AsyncTask<String,Void,String> {

    private Activity parentActivity = null;
    private HttpURLConnection connection = null;
    private Handler ui_handler = null;
    private String returnString = "";

    public HttpGetTask(Activity parentActivity , Handler ui_handler){
        this.parentActivity = parentActivity;
        this.ui_handler = ui_handler;
    }

    @Override
    protected String doInBackground(String... ad) {
        try{

            returnString = "";
            URL url = new URL("http://"+"uumee1234.webcrow.jp/getbbs.php");

            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            connection.connect();

            BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));

            String str ="";
            while((str = reader.readLine())!=null) {
                returnString += (str + "\n");
            }

            in.close();
            reader.close();
            connection.disconnect();

        }catch(Exception e){
            Log.d("Exception",e.toString());
        }
        return returnString;
    }

    @Override
    protected void onPostExecute(String params) {
        //dialog.dismiss();

        Message message = new Message();
        Bundle bundle = new Bundle();
        bundle.putBoolean("http_get_success",true);
        bundle.putString("http_response",params);
        message.setData(bundle);

        ui_handler.sendMessage(message);
    }

}
