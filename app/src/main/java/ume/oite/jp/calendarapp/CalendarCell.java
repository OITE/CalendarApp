package ume.oite.jp.calendarapp;

/**
 * Created by Ume on 2015/07/11.
 */

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/************************************************************
 * Cellクラス<br>
 * <br>
 * カレンダーのセルを表すクラス。<br>
 * TextViewを所持し、セルとして表示させることができる。<br>
 * @author FuyukiUmeta
 ************************************************************/
public class CalendarCell implements OnTouchListener{

    //テキストビュー
    private TextView cell = null;
    //日付データ
    private Date calendar = null;
    //通常・選択時の枠
    private GradientDrawable normalDrawable=new GradientDrawable();
    private GradientDrawable selectDrawable=new GradientDrawable();
    //カレンダーの色情報
    private CalendarColor color = CalendarColor.getInstance();
    //自分のManagerを登録
    private CellManager cellManager = null;
    //自分のいるActivity
    private MainActivity nowActivity = null;
    //日付のフォーマット
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.JAPANESE);

    EditScheduleDialog editScheduleDialog = null;
    //DialogFragment newFragment = new TestDialogFragment();

    private int task_num = 0;

    /************************************************************
     * コンストラクタ<br>
     * 日付を入手<br>
     ************************************************************/
    public CalendarCell(final Context c){
        cell = new TextView(c);
    }
    public CalendarCell(final Activity activity,final CellManager cellManager,final TextView tv,final Calendar calendar){
        cell = tv;
        nowActivity = (MainActivity)activity;
        setCalendar(calendar);
        setCellManager(cellManager);
        cell.setOnTouchListener(this);
    }

    /************************************************************
     * カレンダー設定<br>
     * 使用する日付に設定されたカレンダーを取得<br>
     ************************************************************/
    public void setCalendar(final Calendar calendar){
        this.calendar=calendar.getTime();
        editScheduleDialog = new EditScheduleDialog(this.nowActivity);
        setting(calendar);
    }

    /************************************************************
     * セルマネージャ設定<br>
     * 自分を登録させる1つのセルマネージャを登録する。<br>
     ************************************************************/
    public void setCellManager(CellManager cellManager){
        this.cellManager=cellManager;
        cellManager.add(this);
    }

    /************************************************************
     * 各種設定<br>
     * セルの各設定をする。<br>
     * Drawableの各設定をする。<br>
     ************************************************************/
    public void setting(Calendar calendar){
        if(calendar!=null){
            cell.setTypeface(Typeface.MONOSPACE);
            cell.setPadding(10,5,0,0);
            cell.setText(String.format("%-2d",calendar.get(Calendar.DATE)));
            normalDrawable.setStroke(1,color.getFrameColor(calendar));
            normalDrawable.setColor(color.getBodyColor(calendar));
            selectDrawable.setStroke(5,color.getFrameColor(calendar));
            selectDrawable.setColor(color.getBodyColor(calendar));
            cell.setBackground(normalDrawable);
            cell.setTextColor(color.getNumberColor(calendar));
        }
    }

    /************************************************************
     * Drawable変更<br>
     * セルのDrawableを選択状態に応じて変更をする。<br>
     ************************************************************/
    public void changeDrawable(){
        if(cell.getBackground()==normalDrawable){
            cell.setBackground(selectDrawable);
        }else{
            cell.setBackground(normalDrawable);
        }
    }

    /************************************************************
     * タッチリスナ<br>
     * タッチされた時の処理。<br>
     ************************************************************/
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getActionMasked()==MotionEvent.ACTION_DOWN){
            if(cellManager.getSelectCell()!=null)if(cellManager.getSelectCell()==this){
                editScheduleDialog.setDate(this.calendar);
                editScheduleDialog.show(nowActivity.getFragmentManager(), "contact_us");
            }
            cellManager.changeSelect(cellManager.indexOf(this));
        }
        return true;
    }

}