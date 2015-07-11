package ume.oite.jp.calendarapp;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

/*
 * このアプリは以下のサイトを参考にしました。
 * http://dangoya.jp/?p=302
 *
 * 梅メモ (随時更新)
 * ・　タスク　：　カレンダーの予定のこと。
 * ・　スケジュール　：　タスクのこと。
 * ・　フラグメント　：　カレンダー部分とタスク部分に分けてある。
 * ・　アダプタ　：　これ関連わからない。
 */


/************************************************************
 * MainActivityクラス<br>
 * <br>
 * アプリのメインとなるアクティビティー。<br>
 * ここが各Fragmentを生成し管理する。<br>
 * また、タスクの編集や削除となるメソッドが用意されている。<br>
 *
 * @author FuyukiUmeta
 ************************************************************/
public class MainActivity extends AppCompatActivity {

    //タスクのフラグメント
    public static TaskFragment listFragment = new TaskFragment();
    //カレンダーのフラグメント
    CalendarFragment calendarFragment = new CalendarFragment();

    /************************************************************
     * onCreateメソッド<br>
     * 各フラグメントを表示する。
     ************************************************************/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //月移動ボタンのインスタンス生成
        Button nextButton = (Button)this.findViewById(R.id.nextButton);
        Button prevButton = (Button)this.findViewById(R.id.prevButton);

        //次の月表示ボタン　の　リスナ
        nextButton.setOnTouchListener(new OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getActionMasked()==MotionEvent.ACTION_DOWN){
                    calendarFragment.updateCalendar(1);
                    listFragment.setListView();
                }
                return true;
            }
        });
        //前の月表示ボタン　の　リスナ
        prevButton.setOnTouchListener(new OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getActionMasked()==MotionEvent.ACTION_DOWN){
                    calendarFragment.updateCalendar(-1);
                    listFragment.setListView();
                }
                return true;
            }
        });

        //フラグメントを表示させる。
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_container1, calendarFragment, "calendar_fragment");
        ft.add(R.id.fragment_container2, listFragment,"task_fragment");
        ft.commit();
    }

    //オプションメニュー（未設定）
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //オプションメニューの選択（未設定）
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}