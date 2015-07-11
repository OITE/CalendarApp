package ume.oite.jp.calendarapp;

/**
 * Created by Ume on 2015/07/11.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class EditScheduleDialog extends DialogFragment{

    private Date date = null;
    private SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd", Locale.JAPANESE);
    private SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy年MM月dd日",Locale.JAPANESE);

    private TimePicker beginTimePicker = null;
    private TimePicker endTimePicker = null;
    private EditText editText = null;

    private Activity nowActivity = null;
    private CalendarCell nowCell = null;

    EditScheduleDialog(Activity activity){
        this.nowActivity = activity;
    }

    EditScheduleDialog(Activity activity,CalendarCell cell){
        this.nowActivity = activity;
        this.nowCell = cell;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = (LayoutInflater)getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View content = inflater.inflate(R.layout.dialog_setting, null);

        beginTimePicker = (TimePicker)content.findViewById(R.id.schedule_time1);
        endTimePicker   = (TimePicker)content.findViewById(R.id.schedule_time2);
        beginTimePicker.setIs24HourView(true);
        endTimePicker.setIs24HourView(true);
        editText = (EditText)content.findViewById(R.id.schedule_edit);

        builder.setView(content);

        builder.setMessage(sdf2.format(date)+"に予定追加")
                .setNegativeButton("閉じる", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                    }
                });
        builder.setPositiveButton("登録",new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                // 項目のセット
                ContentValues values = new ContentValues();
                values.put("Schedule", editText.getText().toString());
                values.put("Date",sdf1.format(date));
                values.put("BeginTime",beginTimePicker.getCurrentHour()+":"+beginTimePicker.getCurrentMinute());
                values.put("EndTime",endTimePicker.getCurrentHour()+":"+endTimePicker.getCurrentMinute());

                // データの編集
                DatabaseHelper databaseHelper = new DatabaseHelper(nowActivity);
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                db.insert("Sample",null,values);



                MainActivity.listFragment.setListView();
            }
        });

        // Create the AlertDialog object and return it
        return builder.create();
    }

    void setDate(Date date){
        this.date=date;
    }

}
