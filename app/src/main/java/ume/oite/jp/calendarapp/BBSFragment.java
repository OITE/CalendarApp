package ume.oite.jp.calendarapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import ume.oite.jp.calendarapp.HttpPost.HttpPostHandler;
import ume.oite.jp.calendarapp.HttpPost.HttpPostTask;

/**
 * Created by Ume on 2015/07/14.
 */
public class BBSFragment extends Fragment {

    Activity activity;
    View bbsLayout = null;
    TextView tv;
    EditText addressEdit,nameEdit,bodyEdit;
    LinearLayout textLayout ;
    Button bbsButton ;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        bbsLayout = inflater.inflate(R.layout.fragment_bbs,container,false);
        activity = this.getActivity();

        addressEdit = (EditText)bbsLayout.findViewById(R.id.addressEdit);
        nameEdit = (EditText)bbsLayout.findViewById(R.id.nameEdit);
        bodyEdit = (EditText)bbsLayout.findViewById(R.id.bodyEdit);
        textLayout = (LinearLayout)bbsLayout.findViewById(R.id.textLayout);
        bbsButton = (Button)bbsLayout.findViewById(R.id.bbsButton);
        bbsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exec_post();
            }
        });

        return bbsLayout;
    }

    private void addText(String str){
        TextView tv = new TextView(this.getActivity());
        tv.setText(str);
        textLayout.addView(tv);
    }

    private void exec_post() {

        Log.d("debug","exec_post()");

        HttpPostTask task = new HttpPostTask(
                this.getActivity(),
                "uumee1234.webcrow.jp"+"/bbs.php",

                new HttpPostHandler(){

                    @Override
                    public void onPostCompleted(String response) {
                        addText(response);
                    }

                    @Override
                    public void onPostFailed(String response) {
                        //tv.setText( response );
                        Toast.makeText(getActivity().getApplicationContext(),"エラーが発生しました。",Toast.LENGTH_LONG).show();
                    }
                }
        );
        Log.d("debug","task="+task);

        task.addPostParam("name", nameEdit.getText().toString());
        task.addPostParam("body", bodyEdit.getText().toString());

        task.execute();

    }

}