package ume.oite.jp.calendarapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by Ume on 2015/07/14.
 */
public class CalendarPagerFragment extends Fragment {

    private View calendarpagerLayout = null;

    ViewPager viewPager;
    CalendarPagerAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        calendarpagerLayout = inflater.inflate(R.layout.fragment_calendarpager,container,false);

        adapter = new CalendarPagerAdapter(this.getActivity().getSupportFragmentManager());
        adapter.addAll(getDateList(11));

        viewPager = (ViewPager)calendarpagerLayout.findViewById(R.id.calendar_viewpager);
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(adapter.getCount() / 2-3);
        viewPager.setCurrentItem(adapter.getCount() / 2-2);
        viewPager.setCurrentItem(adapter.getCount() / 2-1);
        viewPager.setCurrentItem(adapter.getCount() / 2-0);

        adapter.notifyDataSetChanged();

        return calendarpagerLayout;

    }

    private ArrayList<SparseArrayCompat<Integer>> getDateList(int num){
        ArrayList<SparseArrayCompat<Integer>> list = new ArrayList<SparseArrayCompat<Integer>>();
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -num / 2);

        for(int i=0;i<num;i++){
            SparseArrayCompat<Integer> item = new SparseArrayCompat<Integer>();
            item.append(0,c.get(Calendar.YEAR));
            item.append(1,c.get(Calendar.MONTH));
            list.add(item);
            c.add(Calendar.MONTH,+1);
        }
        return list;
    }


}
