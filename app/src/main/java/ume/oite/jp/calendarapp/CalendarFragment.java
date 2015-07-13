package ume.oite.jp.calendarapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Calendar;

/************************************************************
 * Calendar_Fragmentクラス<br>
 * <br>
 * カレンダーの各セルを表示する。<br>
 * Fragmentクラスを継承している。<br>
 * res/layout/calendar_flagmentのレイアウトファイルを使用。<br>
 * @author FuyukiUmeta
 ************************************************************/
public class CalendarFragment extends Fragment{

    //カレンダーのレイアウトの保管
    private View calendarLayout = null;
    //日付取得のためのカレンダー
    private Calendar calendar = Calendar.getInstance();
    //カレンダーセル管理のManager
    private CellManager cellManager;
    //現在の年月
    public static int year=0,month=0;

    /************************************************************
     * onCreateViewメソッド<br>
     * カレンダーの各セルの設定を行う。<br>
     ************************************************************/
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        int year = bundle.getInt("year");
        int month = bundle.getInt("month");

        //レイアウトファイルをView型に変換する
        calendarLayout = inflater.inflate(R.layout.fragment_calendar_2,container,false);

        //カレンダーを初期化（現在の年月日にする。）
        //updateCalendar(0);
        //update_setCalendar(year,month);
        makeCalendar(year,month);

        return calendarLayout;
    }

    private void makeCalendar(int year,int month){

        //カレンダーに指定の年月を入力
        calendar.set(year, month, 1);

        //カレンダーの左上端へ移動
        calendar.add(Calendar.DATE, -(calendar.get(Calendar.DAY_OF_WEEK) - 1));

        //各週のViewGroupを取得
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
                // week の中の day の中の dayText にアクセス
                if(tv!=null)tv.setText(String.valueOf(calendar.get(Calendar.DATE)));
                calendar.add(Calendar.DATE,+1);
            }
        }
    }



    /************************************************************
     * updateCalendarメソッド<br>
     * カレンダーの表示を更新する。<br>
     * 引数monthに0を入力すると、現実で現在の日付のカレンダー表示。<br>
     * 引数monthに整数を入力すると、その数だけ月が移動する。<br>
     * MainActivityのprev,nextボタンのリスナで使用している。
     ************************************************************/
    public void updateCalendar(int month){

        //CellManagerインスタンス
        cellManager= new CellManager();
        //年月のTextView
        TextView dateText = (TextView)this.getActivity().findViewById(R.id.DateField);

        //カレンダーの日付等の設定
        // 引数monthが0なら、初期処理として、カレンダーを現在の月日に初期化
        // それ以外なら、monthの値分、月をずらす。
        if(month==0){
            calendar.clear();
            calendar.set(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), 1);
        }else{
            calendar.add(Calendar.MONTH,month);
            calendar.set(Calendar.DATE,1);
        }
        //年月の表示の更新
        dateText.setText((this.year=calendar.get(Calendar.YEAR))+"年　"+(this.month=(calendar.get(Calendar.MONTH)+1))+"月");
        //カレンダーの左上端へ移動
        calendar.add(Calendar.DATE,-(calendar.get(Calendar.DAY_OF_WEEK)-1));
        //レイアウトファイルのlayoutをViewGroupとして読み込む
        ViewGroup calendarGroup = (ViewGroup)calendarLayout.findViewById(R.id.calendar);

        //6週間分ループさせる
        for(int week=1;week<calendarGroup.getChildCount();week++){
            //各週のlayoutをViewGroupとして読み込む
            ViewGroup weeks = (ViewGroup) calendarGroup.getChildAt(week);

            //7日分ループさせる
            for(int day=0;day<weeks.getChildCount();day++){

                //LayoutのTextViewをCalendarCellとする。
                CalendarCell cell = new CalendarCell(this.getActivity(),cellManager,(TextView)(weeks.getChildAt(day)),calendar);
                //CalendarManagerへCellを追加する。
                cellManager.add(cell);
                //カレンダーを1日増やす
                calendar.add(Calendar.DATE, 1);

            }

        }
        //カレンダーを増やしていくと、本来の月の　次の月の途中まで進んでしまうので
        //そのための、設定
        calendar.add(Calendar.MONTH,-1);
        calendar.set(Calendar.DATE,1);

    }

    public void update_setCalendar(int year,int month){
        TextView dateText = (TextView)this.getActivity().findViewById(R.id.DateField);
        dateText.setText((this.year=calendar.get(Calendar.YEAR))+"年　"+(this.month=(calendar.get(Calendar.MONTH)+1))+"月");
        int plus_year  = year-calendar.get(Calendar.YEAR);
        int plus_month = month-calendar.get(Calendar.MONTH) + plus_year*12;
        this.updateCalendar(plus_month);
    }



}