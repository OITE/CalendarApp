package ume.oite.jp.calendarapp;

/**
 * Created by Ume on 2015/07/11.
 */

import android.graphics.Color;

import java.util.Calendar;

/************************************************************
 * CalendarColorクラス<br>
 * <br>
 * カレンダーの各セルの色を定義する。<br>
 * 数字色、枠色、枠内色が定義されている。<br>
 * Singletonパターンを適用している。インスタンスはプロジェクトに一つ。<br>
 * @author FuyukiUmeta
 ************************************************************/
public class CalendarColor {

    //Singletonパターン
    private static CalendarColor instance;
    private CalendarColor(){}
    public static CalendarColor getInstance(){
        if(instance==null)instance=new CalendarColor();
        return instance;
    }


    /************************************************************
     * getNumberColorメソッド<br>
     * 数字色の定義<br>
     ************************************************************/
    public int getNumberColor(Calendar calendar){
        switch(calendar.get(Calendar.DAY_OF_WEEK)){
            case Calendar.SUNDAY:return Color.rgb(255, 0, 0);
            case Calendar.SATURDAY:return Color.rgb(0, 0, 255);
            default:return Color.rgb(0, 0, 0);
        }
    }

    /************************************************************
     * getFrameColorメソッド<br>
     * 枠色の定義<br>
     ************************************************************/
    public int getFrameColor(Calendar calendar){
        switch(calendar.get(Calendar.DAY_OF_WEEK)){
            case Calendar.SUNDAY:return Color.rgb(255,0,0);
            case Calendar.SATURDAY:return Color.rgb(0, 0, 255);
            default: return Color.rgb(0, 0, 0);
        }
    }

    /************************************************************
     * getBodyColorメソッド<br>
     * 枠内色の定義<br>
     ************************************************************/
    public int getBodyColor(Calendar calendar){
        if(calendar.get(Calendar.MONTH)!=CalendarFragment.month-1)return Color.GRAY;
        switch(calendar.get(Calendar.DAY_OF_WEEK)){
            case Calendar.SUNDAY:return Color.rgb(255,200,200);
            case Calendar.SATURDAY:return Color.rgb(200,200,255);
            default:return Color.rgb(255,255,255);
        }
    }
}
