package ume.oite.jp.calendarapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.util.SparseArrayCompat;

import java.util.ArrayList;

/**
 * Created by Ume on 2015/07/12.
 */
public class CalendarPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<SparseArrayCompat<Integer>> dateList;

    public CalendarPagerAdapter(FragmentManager fm){
        super(fm);
        dateList = new ArrayList<SparseArrayCompat<Integer>>();
    }

    @Override
    public int getCount() {
        return dateList.size();
    }

    @Override
    public Fragment getItem(int position) {

        SparseArrayCompat<Integer> item = dateList.get(position);

        Bundle bundle = new Bundle();
        bundle.putInt("page",position);
        bundle.putInt("year",item.get(0));
        bundle.putInt("month",item.get(1));

        CalendarFragment fragment = new CalendarFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    public void add(SparseArrayCompat<Integer> item){
        dateList.add(item);
    }

    public void addAll(ArrayList<SparseArrayCompat<Integer>> list){
        dateList.addAll(list);
    }

}
