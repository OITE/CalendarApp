package ume.oite.jp.calendarapp;

import android.os.AsyncTask;
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
import java.util.Map;
import java.util.Set;

/**
 * Created by Ume on 2015/07/16.
 */
public class HttpGetTask extends AsyncTask<String,Void,Void> {

    private HttpURLConnection connection = null;
    Map params;



    @Override
    protected Void doInBackground(String... ad) {
        String ret = "";
        try{

            String address = ad[0];
            URL url = new URL("http://"+address);

            connection = (HttpURLConnection)url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            Log.d("debug", "hashmap");

            OutputStream os = connection.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));

            Log.d("debug", "connect");
            connection.connect();

            Log.d("debug","String");
            StringBuilder result = new StringBuilder();
            Set<Map.Entry<String,String>> set = params.entrySet();
            Iterator<Map.Entry<String,String>> it = set.iterator();
            while(it.hasNext()){
                Map.Entry<String,String> e = it.next();
                result.append(e.getKey() + "=" + e.getValue());
                if(it.hasNext())result.append("&");
            }
            Log.d("debug", "" + result);
            writer.write(result.toString());
            writer.flush();

            BufferedInputStream in = new BufferedInputStream(connection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            ret = reader.readLine();

            Log.d("debug",""+ret);

            writer.close();
            os.close();


        }catch(Exception e){
            Log.d("Exception",e.toString());
        }finally{
            Log.d("debug","finnaly");
            connection.disconnect();
        }
        return null;
    }
}
