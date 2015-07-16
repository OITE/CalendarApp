package ume.oite.jp.calendarapp.Http;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Ume on 2015/07/16.
 */
public abstract class HttpGetHandler extends Handler {
    public void handleMessage(Message msg){

        boolean isGetSuccess = msg.getData().getBoolean("http_get_success");
        String http_response = msg.getData().get("http_response").toString();

        if(isGetSuccess) {
            onGetCompleted(http_response);
        }else{
            onGetFailed(http_response);
        }
    }
    public abstract void onGetCompleted(String response);
    public abstract void onGetFailed(String response);

}