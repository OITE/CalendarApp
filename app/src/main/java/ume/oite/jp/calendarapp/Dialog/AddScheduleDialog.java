package ume.oite.jp.calendarapp.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.Calendar;

import ume.oite.jp.calendarapp.Database.DatabaseHelper;

/**
 * Created by Ume on 2015/07/20.
 */
public class AddScheduleDialog extends DialogFragment {

    private DialogInterface.OnClickListener okClickListener = null;
    private DialogInterface.OnClickListener cancelClickListener = null;

    private EditText editText = null;
    private TextView textView = null;

    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    private int year=0,month=0,date=0;

    public static AddScheduleDialog getInstance(Calendar calendar){
        AddScheduleDialog dialog = new AddScheduleDialog();
        Bundle args = new Bundle();
        args.putInt("year", calendar.get(Calendar.YEAR));
        args.putInt("month",calendar.get(Calendar.MONTH));
        args.putInt("date", calendar.get(Calendar.DATE));
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle safedInstanceState){

        year = this.getArguments().getInt("year");
        month = this.getArguments().getInt("month")+1;
        date = this.getArguments().getInt("date");

        String title = "Add Schedule" ;
        String message = "at " + year + "/" + (month) + "/" + date;

        settingOkClickListener();
        settingCancelClickListener();

        dbHelper = new DatabaseHelper(this.getActivity().getApplicationContext());
        if(database!=null)database.close();
        database = dbHelper.getReadableDatabase();

        editText = new EditText(this.getActivity().getApplicationContext());
        editText.setHint("Schedule_Name");
        editText.setTextColor(Color.BLACK);

        textView = new TextView(this.getActivity().getApplicationContext());
        textView.setTextColor(Color.BLACK);

        StringBuilder sql = new StringBuilder();
        sql.append(" SELECT");
        sql.append(" _id");
        sql.append(" ,Schedule");
        sql.append(" ,BeginTime");
        sql.append(" ,EndTime");
        sql.append(" FROM Sample;");

        Cursor cursor = database.rawQuery(sql.toString(),null);

        StringBuilder text = new StringBuilder();
        while(cursor.moveToNext()){
            text.append(cursor.getInt(0));
            text.append(","+cursor.getString(1));
            text.append(","+cursor.getString(2));
            text.append(","+cursor.getString(3));
            text.append("\n");
        }
        textView.setText(text);
        cursor.close();

        LinearLayout ll = new LinearLayout(this.getActivity().getApplicationContext());
        ScrollView scroll = new ScrollView(this.getActivity().getApplicationContext());
        scroll.addView(textView);

        ll.setOrientation(LinearLayout.VERTICAL);
        ll.addView(editText);
        ll.addView(scroll);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setView(ll)
                .setPositiveButton("OK", this.okClickListener)
                .setNegativeButton("Cancel", this.cancelClickListener);
        return builder.create();
    }

    public void setOkClickListener(DialogInterface.OnClickListener listener){
        this.okClickListener = listener;
    }

    public void setCancelClickListener(DialogInterface.OnClickListener listener){
        this.cancelClickListener = listener;
    }

    public void settingOkClickListener(){
        this.okClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.close();
                database = dbHelper.getWritableDatabase();
                ContentValues values = new ContentValues();
                String schedule = editText.getText().toString();
                if(schedule == " " || schedule == null)schedule = "empty";
                Log.d("db",schedule);
                values.put("Schedule",schedule);
                values.put("BeginTime",year+"-"+month+"-"+date+" 12:00:00");
                values.put("EndTime",year+"-"+month+"-"+date+" 13:00:00");
                database.insert("Sample",null,values);
            }
        };
    }

    public void settingCancelClickListener(){
        this.cancelClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                database.close();
                database = dbHelper.getWritableDatabase();
                database.execSQL("DELETE FROM Sample;");
                database.execSQL("delete from sqlite_sequence where name='Sample'");
            }
        };
    }




}
