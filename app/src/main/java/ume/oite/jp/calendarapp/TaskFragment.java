package ume.oite.jp.calendarapp;

/**
 * Created by Ume on 2015/07/11.
 */


import android.app.ListFragment;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/************************************************************
 * Task_Fragmentクラス<br>
 * <br>
 * タスクをリストとして表示する。<br>
 * ListFragmentクラスを継承している。<br>
 * MainActivityのタスク編集・削除のメソッドを使用している。<br>
 *
 * @author FuyukiUmeta
 ************************************************************/
public class TaskFragment extends ListFragment{

    //スケジュールを管理するリスト
    protected List<Schedule> list;
    //スケジュールのアダプタ
    protected ArrayAdapter<Schedule> adapter;

    /************************************************************
     * onActivityCreatedメソッド<br>
     * リストを設定し、各項目にクリック時にポップアップを表示させるようにする。<br>
     ************************************************************/
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        this.setListView();

        // リストクリック（コピペ）
        getListView().setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position ,long id) {
                // 選択アイテムの取得
                final Schedule listItem = adapter.getItem(position);

                // ポップアップ
                PopupMenu popupMenu = new PopupMenu(getActivity(), v);
                popupMenu.inflate(R.menu.menu_popup);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.mail_edit:
                                // 編集
                                edit_item(listItem.getId(), listItem.getSchedule());
                                break;
                            case R.id.mail_delete:
                                // 削除
                                delete_item(listItem.getId());
                                break;
                            default:
                        }
                        return true;
                    }
                });
                popupMenu.show();
            };
        });

    }

    /************************************************************
     * setListViewメソッド<br>
     * リストを設定する。（コピペ）<br>
     ************************************************************/
    public void setListView() {

        //空のスケジュールリスト生成
        list = new ArrayList<Schedule>();

        //読み込み専用データベースの用意
        DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        //SQL文の定義
        String query = "SELECT * FROM Sample;";

        //DBからデータを取得するためのカーソルを生成
        Cursor c = db.rawQuery(query, null);

        //カーソルからデータを取得し、空のスケジュールリストに追加していく。
        if (c.moveToFirst()) {
            do {
                Schedule listItem = new Schedule();
                listItem.setId(c.getInt(c.getColumnIndex("_id")));
                listItem.setBeginTime(c.getString(c.getColumnIndex("BeginTime")));
                listItem.setEndTime(c.getString(c.getColumnIndex("EndTime")));
                listItem.setDate(c.getString(c.getColumnIndex("Date")));
                listItem.setSchedule(c.getString(c.getColumnIndex("Schedule")));
                if(isSameDate(listItem.getDate())==true)list.add(listItem);
            } while (c.moveToNext());
        }
        c.close();

        // アダプターのセット
        adapter = new ListAdapter(getActivity(), list);
        setListAdapter(adapter);
    }

    boolean isSameDate(Date date){
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        if(c.get(Calendar.YEAR)== CalendarFragment.year){
            if(c.get(Calendar.MONTH)== CalendarFragment.month-1){
                return true;
            }
        }
        return false;
    }

    /************************************************************
     * ListAdapterクラス<br>
     * <br>
     * Adapter関連を勉強中（コピペ）<br>
     *
     * @author FuyukiUmeta
     ************************************************************/
    private class ListAdapter extends ArrayAdapter<Schedule> {
        private LayoutInflater mInflater;

        public ListAdapter(Context context, List<Schedule> objects) {
            super(context, 0, objects);
            mInflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;

            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.listfragment_item, parent, false);
                holder = new ViewHolder();
                holder.name_text = (TextView)convertView.findViewById(R.id.name);
                holder.date_text = (TextView)convertView.findViewById(R.id.date);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder)convertView.getTag();
            }
            final Schedule item = this.getItem(position);

            Calendar c = Calendar.getInstance();
            c.setTime(item.getDate());
            int year = c.get(Calendar.YEAR) , month = (c.get(Calendar.MONTH)+1) , date = c.get(Calendar.DATE);
            c.setTime(item.getBeginTime());
            int bhour = c.get(Calendar.HOUR) , bminute = c.get(Calendar.MINUTE);
            c.setTime(item.getEndTime());
            int ehour = c.get(Calendar.HOUR) , eminute = c.get(Calendar.MINUTE);


            //holder.tvName.setText(c.get(Calendar.YEAR)+"/"+(c.get(Calendar.MONTH)+1)+"/"+c.get(Calendar.DATE)+" "+String.valueOf(item.getSchedule()));
            holder.date_text.setText(year+"/"+String.format("%02d",month)+"/"+String.format("%02d",date)+
                    " ["+String.format("%02d",bhour)+":"+String.format("%02d",bminute)+"～"+String.format("%02d",ehour)+":"+String.format("%02d",eminute)+"] ");
            holder.name_text.setText(String.valueOf(item.getSchedule()));

            return convertView;
        }
    }


    void delete_item(int id){
        DatabaseHelper databaseHelper = new DatabaseHelper(this.getActivity());
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        db.delete("Sample", "_id = " + id, null);

        Toast.makeText(this.getActivity(), "データを削除しました。", Toast.LENGTH_SHORT).show();

        this.setListView();
    }
    void edit_item(final int id,String name){
        // 入力用EditText
        final EditText editText = new EditText(this.getActivity());
        editText.setText(name);

        // ダイアログの表示
        // app_name:ダイアログのタイトル ; dialog_message:ダイアログの説明文
        EditTextDialog dialogFragment = EditTextDialog.newInstance("予定変更","予定を変更します。");

        // ダイアログのOKボタンのリスナ
        //　　ボタンを押すとデータベースにアクセスし、
        dialogFragment.setOnOkClickListener(new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // 項目のセット
                ContentValues values = new ContentValues();
                values.put("Schedule", editText.getText().toString());

                // データの編集
                DatabaseHelper databaseHelper = new DatabaseHelper(getActivity());
                SQLiteDatabase db = databaseHelper.getWritableDatabase();
                db.update("Sample", values, "_id = " + id, null);

                Toast.makeText(getActivity(), "データを編集しました。", Toast.LENGTH_SHORT).show();

                setListView();
            }
        });
        dialogFragment.setEditText(editText);
        dialogFragment.show(getFragmentManager(), "dialog_fragment");
    }


    /************************************************************
     *　ViewHolderクラス<br>
     * <br>
     * Adapter関連を勉強中（コピペ）<br>
     *
     * @author FuyukiUmeta
     ************************************************************/
    private class ViewHolder {
        TextView name_text;
        TextView date_text;
    }


    /************************************************************
     * Scheduleクラス<br>
     * <br>
     * 各タスクのデータを所持する。DBの要素と同じにする。<br>
     *
     * @author FuyukiUmeta
     ************************************************************/
    private class Schedule {
        private int id = 0;
        private Date date = new Date();
        private Date beginTime = new Date();
        private Date endTime   = new Date();
        private String schedule= "";
        //日付フォーマット
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.JAPANESE);
        SimpleDateFormat stf = new SimpleDateFormat("HH:mm",Locale.JAPANESE);

        public Schedule(){}

        public void setId(int id){
            this.id=id;
        }
        public void setBeginTime(String beginTime){
            try {
                this.beginTime = stf.parse(beginTime);
            } catch (ParseException e) {}
        }
        public void setEndTime(String endTime){
            try {
                this.endTime = stf.parse(endTime);
            } catch (ParseException e) {}
        }
        public void setDate(String Date){
            try {
                this.date=sdf.parse(Date);
            } catch (ParseException e) {}
        }
        public void setSchedule(String schedule) {
            this.schedule = schedule;
        }

        public int getId(){
            return id;
        }
        public Date getDate(){
            return date;
        }
        public Date getBeginTime(){
            return beginTime;
        }
        public Date getEndTime(){
            return endTime;
        }
        public String getSchedule() {
            return schedule;
        }

    }

}


