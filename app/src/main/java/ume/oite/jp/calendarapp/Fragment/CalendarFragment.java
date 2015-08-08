package ume.oite.jp.calendarapp.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Calendar;

import ume.oite.jp.calendarapp.Dialog.AddScheduleDialog;
import ume.oite.jp.calendarapp.R;

public class CalendarFragment extends Fragment{

    private View calendarLayout = null;
    private Calendar calendar = Calendar.getInstance();
    private FragmentManager fm = null;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        fm = this.getActivity().getSupportFragmentManager();

        Bundle bundle = getArguments();
        int year = bundle.getInt("year");
        int month = bundle.getInt("month");

        calendarLayout = inflater.inflate(R.layout.fragment_calendar,container,false);

        makeCalendar(year,month);

        return calendarLayout;
    }




    private void makeCalendar(int year,int month){

        calendar.clear();
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

                ViewGroup dateGroup = (ViewGroup)week.getChildAt(i);
                TextView dateView = (TextView)dateGroup.getChildAt(0);

                dateView.setText(String.valueOf(calendar.get(Calendar.DATE)));
                if(calendar.get(Calendar.MONTH)!=month)dateGroup.setBackgroundResource(R.drawable.background_shape_other);
                if(this.isToday(calendar))dateGroup.setBackgroundResource(R.drawable.background_shape_today);

                dateGroup.setOnTouchListener(new View.OnTouchListener() {

                    int y = calendar.get(Calendar.YEAR);
                    int m = calendar.get(Calendar.MONTH);
                    int d = calendar.get(Calendar.DATE);

                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (v.isFocused() == false) {
                            if(event.getActionMasked() == MotionEvent.ACTION_UP){
                                v.requestFocus();
                            }
                        }else{
                            if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                                Calendar c = Calendar.getInstance(); c.set(y,m,d);
                                AddScheduleDialog.getInstance(c).show(fm, "scheduleAdd");
                            }
                        }
                        return true;
                    }
                });

                calendar.add(Calendar.DATE, +1);
            }
        }
    }


    private boolean isToday(Calendar c) {
        Calendar today = Calendar.getInstance();
        if(c.get(Calendar.YEAR) == today.get(Calendar.YEAR)){
            if(c.get(Calendar.MONTH) == today.get(Calendar.MONTH)){
                if(c.get(Calendar.DATE) == today.get(Calendar.DATE)){
                    return true;
                }
            }
        }
        return false;
    }

}