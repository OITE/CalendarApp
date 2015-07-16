package ume.oite.jp.calendarapp.Http;

import android.os.Handler;
import android.os.Message;

/**
 * Created by Ume on 2015/07/16.
 */
public abstract class HttpPostHandler extends Handler {

    public void handleMessage(Message msg){

        boolean isPostSuccess = msg.getData().getBoolean("http_post_success");
        String http_response = msg.getData().get("http_response").toString();

        if(isPostSuccess) {
            onPostCompleted(http_response);
        }else{
            onPostFailed(http_response);
        }
    }
    public abstract void onPostCompleted(String response);
    public abstract void onPostFailed( String response );

}
