package ume.oite.jp.calendarapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

import ume.oite.jp.calendarapp.R;

public class CalendarFragment extends Fragment{

    private View calendarLayout = null;
    private Calendar calendar = Calendar.getInstance();

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

        for(ViewGroup week : weeks){
            if(calendar.get(Calendar.MONTH)>month && week==weeks[5]){
                ((LinearLayout)calendarLayout).removeView(week);
                break;
            }
            for(int i=0;i<week.getChildCount();i++){
                ((TextView)((ViewGroup)week.getChildAt(i)).getChildAt(0)).setText(String.valueOf(calendar.get(Calendar.DATE)));
                if(calendar.get(Calendar.MONTH)!=month)week.getChildAt(i).setBackgroundResource(R.drawable.background_shape_other);
                if(Calendar.getInstance().get(Calendar.MONTH)==month && Calendar.getInstance().get(Calendar.YEAR)==year && Calendar.getInstance().get(Calendar.DATE)==calendar.get(Calendar.DATE))week.getChildAt(i).setBackgroundResource(R.drawable.background_shape_today);
                calendar.add(Calendar.DATE,+1);
            }
        }
    }

}