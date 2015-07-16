package ume.oite.jp.calendarapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by Ume on 2015/07/14.
 */
public class BBSFragment extends Fragment {

    Activity activity;
    View bbsLayout = null;
    EditText addressEdit,nameEdit,bodyEdit;
    LinearLayout textLayout ;
    Button bbsButton ;
    ScrollView bbsScroll;
    Handler handler = null;

    LinearLayout bbsTextLayout = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        bbsLayout = inflater.inflate(R.layout.fragment_bbs, container, false);
        activity = this.getActivity();

        bbsTextLayout = (LinearLayout)inflater.inflate(R.layout.bbs_text, null);

        addressEdit = (EditText)bbsLayout.findViewById(R.id.addressEdit);
        bbsScroll = (ScrollView)bbsLayout.findViewById(R.id.bbsScroll);
        nameEdit = (EditText)bbsLayout.findViewById(R.id.nameEdit);
        bodyEdit = (EditText)bbsLayout.findViewById(R.id.bodyEdit);
        textLayout = (LinearLayout)bbsLayout.findViewById(R.id.textLayout);
        bbsButton = (Button)bbsLayout.findViewById(R.id.bbsButton);
        bbsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int index = 0;
                String name = nameEdit.getText().toString();
                if (name == "") name = "nanashi";
                String body = bodyEdit.getText().toString();
                if (body.equals("")) body = "nobody";
                String address = addressEdit.getText().toString();
                HttpPostTask task = new HttpPostTask(
                        activity,
                        new HttpPostHandler() {

                            @Override
                            public void onPostCompleted(String response) {
                                updateBBS();
                            }

                            @Override
                            public void onPostFailed(String response) {

                            }
                        }
                );
                task.setParam("index", String.valueOf(index));
                task.setParam("name", name);
                task.setParam("body", body);
                task.execute(address);
            }
        });

        handler = new Handler(){
            @Override
            public void dispatchMessage(Message msg){
                if(msg.what==1){
                    updateBBS();
                    handler.sendEmptyMessageDelayed(1,5*1000);
                }else{
                    super.dispatchMessage(msg);
                }
            }
        };

        handler.sendEmptyMessage(1);

        return bbsLayout;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler = null;
    }

    private void updateBBS(){
        HttpGetTask getTask = new HttpGetTask(
                activity,
                new HttpGetHandler(){

                    @Override
                    public void onGetCompleted(String response) {
                        String s[] = response.split("\n");
                        int index = Integer.parseInt(s[0]);
                        textLayout.removeAllViews();
                        if(index - textLayout.getChildCount() > 0) {
                            for (int i = index; i >= 1; i--) {
                                addText(s[i]);
                            }
                        }
                    }

                    @Override
                    public void onGetFailed(String response) {
                    }
                }
        );
        getTask.execute();
    }

    private void addText(String text){

        String[] params = text.split("_");

        Context ctx = this.getActivity().getApplicationContext();

        LinearLayout view = new LinearLayout(ctx);
        view.setOrientation(LinearLayout.VERTICAL);
        LinearLayout layout_1 = new LinearLayout(ctx);
        layout_1.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout layout_2 = new LinearLayout(ctx);
        layout_2.setOrientation(LinearLayout.HORIZONTAL);

        TextView idText = new TextView(ctx);
        idText.setText(params[0]);
        idText.setTextColor(Color.BLACK);
        TextView nameText1 = new TextView(ctx);
        nameText1.setText(" 名前:");
        nameText1.setTextColor(Color.BLACK);
        TextView nameText2 = new TextView(ctx);
        nameText2.setText(params[1]);
        nameText2.setTextColor(Color.BLACK);
        TextView dateText1 = new TextView(ctx);
        dateText1.setText(" : ");
        dateText1.setTextColor(Color.BLACK);
        TextView dateText2 = new TextView(ctx);
        dateText2.setText(params[2]);
        dateText2.setTextColor(Color.BLACK);
        TextView bodyText = new TextView(ctx);
        bodyText.setText(params[3]);
        bodyText.setTextColor(Color.BLACK);

        layout_1.addView(idText);
        layout_1.addView(nameText1);
        layout_1.addView(nameText2);
        layout_1.addView(dateText1);
        layout_1.addView(dateText2);
        layout_2.addView(bodyText);

        view.addView(layout_1);
        view.addView(layout_2);

        textLayout.addView(view);

    }

}