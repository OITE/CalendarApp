package ume.oite.jp.calendarapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

public class CalendarFragment extends Fragment{

    private View calendarLayout = null;
    private Calendar calendar = Calendar.getInstance();
    public static int year=0,month=0;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        int year = bundle.getInt("year");
        int month = bundle.getInt("month");

        calendarLayout = inflater.inflate(R.layout.fragment_calendar_2,container,false);

        makeCalendar(year,month);

        return calendarLayout;
    }

    private void makeCalendar(int year,int month){

        calendar.set(year, month, 1);

        calendar.add(Calendar.DATE, -(calendar.get(Calendar.DAY_OF_WEEK) - 1));

        ViewGroup[] weeks = new ViewGroup[6];
        weeks[0] = (ViewGroup) calendarLayout.findViewById(R.id.week1);
        weeks[1] = (ViewGroup) calendarLayout.findViewById(R.id.week2);
        weeks[2] = (ViewGroup) calendarLayout.findViewById(R.id.week3);
        weeks[3] = (ViewGroup) calendarLayout.findViewById(R.id.week4);
        weeks[4] = (ViewGroup) calendarLayout.findViewById(R.id.week5);
        weeks[5] = (ViewGroup) calendarLayout.findViewById(R.id.week6);

        Log.d("debug","week1="+weeks[0]);

        for(ViewGroup week : weeks){
            for(int i=0;i<week.getChildCount();i++){
                TextView tv = ((TextView)((ViewGroup)week.getChildAt(i)).getChildAt(0));
                if(tv!=null)tv.setText(String.valueOf(calendar.get(Calendar.DATE)));
                calendar.add(Calendar.DATE,+1);
            }
        }
    }

}