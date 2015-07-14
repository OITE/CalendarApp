package ume.oite.jp.calendarapp.HttpPost;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class HttpPostTask extends AsyncTask<Void, Void, Void> {


    private String request_encoding = "UTF-8";
    private String response_encoding = "UTF-8";

    private Activity parent_activity = null;
    private String post_url = null;
    private Handler ui_handler = null;
    private List<NameValuePair> post_params = null;

    private ResponseHandler<Void> response_handler = null;
    private String http_err_msg = null;
    private String http_ret_msg = null;
    private ProgressDialog dialog = null;


    public HttpPostTask( Activity parent_activity, String post_url, Handler ui_handler ) {
        this.parent_activity = parent_activity;
        this.post_url = post_url;
        this.ui_handler = ui_handler;

        Log.d("debug","post_params10="+post_params);
        post_params = new ArrayList<NameValuePair>();
        Log.d("debug","post_params11="+post_params);
    }




    public void addPostParam( String post_name, String post_value )
    {
        Log.d("debug","post_params2="+post_params);
        post_params.add(new BasicNameValuePair(post_name, post_value));
    }




    protected void onPreExecute() {
        dialog = new ProgressDialog( parent_activity );
        dialog.setMessage("通信中・・・");
        dialog.show();

        response_handler = new ResponseHandler<Void>() {

            @Override
            public Void handleResponse(HttpResponse response) throws IOException
            {
                Log.d(
                        "posttest",
                        "レスポンスコード：" + response.getStatusLine().getStatusCode()
                );

                switch (response.getStatusLine().getStatusCode()) {
                    case HttpStatus.SC_OK:
                        Log.d("posttest", "レスポンス取得に成功");

                        HttpPostTask.this.http_ret_msg = EntityUtils.toString(
                                response.getEntity(),
                                HttpPostTask.this.response_encoding
                        );
                        break;

                    case HttpStatus.SC_NOT_FOUND:
                        Log.d("posttest", "存在しない");
                        HttpPostTask.this.http_err_msg = "404 Not Found";
                        break;

                    default:
                        Log.d("posttest", "通信エラー");
                        HttpPostTask.this.http_err_msg = "通信エラーが発生";
                }

                return null;
            }

        };
    }


    protected Void doInBackground(Void... unused) {

        Log.d("posttest", "postします");

        URI url = null;
        try {
            url = new URI( post_url );
            Log.d("posttest", "URLはOK");
        } catch (URISyntaxException e) {
            e.printStackTrace();
            http_err_msg = "不正なURL";
            return null;
        }

        HttpPost request = new HttpPost( url );
        try {
            request.setEntity(new UrlEncodedFormEntity(post_params, request_encoding));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
            http_err_msg = "不正な文字コード";
            return null;
        }

        DefaultHttpClient httpClient = new DefaultHttpClient();
        Log.d("posttest", "POST開始");
        try {
            httpClient.execute(request, response_handler);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
            http_err_msg = "プロトコルのエラー";
        } catch (IOException e) {
            e.printStackTrace();
            http_err_msg = "IOエラー";
        }

        httpClient.getConnectionManager().shutdown();

        return null;
    }


    protected void onPostExecute(Void unused) {
        dialog.dismiss();

        Message message = new Message();
        Bundle bundle = new Bundle();
        if (http_err_msg != null) {
            bundle.putBoolean("http_post_success", false);
            bundle.putString("http_response", http_err_msg);
        } else {
            bundle.putBoolean("http_post_success", true);
            bundle.putString("http_response", http_ret_msg);
        }
        message.setData(bundle);

        ui_handler.sendMessage(message);
    }

}