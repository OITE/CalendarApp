package ume.oite.jp.calendarapp;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.util.SparseArrayCompat;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.Calendar;

/************************************************************
 * MainActivity�N���X<br>
 * <br>
 * �A�v���̃��C���ƂȂ�A�N�e�B�r�e�B�[�B<br>
 * �������eFragment�𐶐����Ǘ�����B<br>
 * �܂��A�^�X�N�̕ҏW��폜�ƂȂ郁�\�b�h���p�ӂ���Ă���B<br>
 *
 * @author FuyukiUmeta
 ************************************************************/
public class MainActivity extends FragmentActivity {

    //�^�X�N�̃t���O�����g
    public static TaskFragment listFragment = new TaskFragment();
    //�J�����_�[�̃t���O�����g
    CalendarFragment calendarFragment = new CalendarFragment();
    //ViewPager
    ViewPager viewPager ;

    /************************************************************
     * onCreate���\�b�h<br>
     * �e�t���O�����g��\������B
     ************************************************************/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //���ړ��{�^���̃C���X�^���X����
        //Button nextButton = (Button)this.findViewById(R.id.nextButton);
        //Button prevButton = (Button)this.findViewById(R.id.prevButton);

        /*
        //���̌��\���{�^���@�́@���X�i
        nextButton.setOnTouchListener(new OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getActionMasked()==MotionEvent.ACTION_DOWN){
                    calendarFragment.updateCalendar(1);
                    listFragment.setListView();
                }
                return true;
            }
        });
        //�O�̌��\���{�^���@�́@���X�i
        prevButton.setOnTouchListener(new OnTouchListener(){
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getActionMasked()==MotionEvent.ACTION_DOWN){
                    calendarFragment.updateCalendar(-1);
                    listFragment.setListView();
                }
                return true;
            }
        });

        */
        viewPager = (ViewPager)this.findViewById(R.id.calendar_viewpager);

        //�t���O�����g��\��������B
        FragmentManager fm = this.getSupportFragmentManager();

        CalendarPagerAdapter adapter = new CalendarPagerAdapter(fm);
        adapter.addAll(getDateList());

        viewPager.setAdapter(adapter);

        //FragmentTransaction ft = fm.beginTransaction();
        //ft.add(R.id.fragment_container1, calendarFragment, "calendar_fragment");
        //ft.add(R.id.fragment_container2, listFragment,"task_fragment");
        //ft.commit();
    }

    private ArrayList<SparseArrayCompat<Integer>> getDateList(){
        ArrayList<SparseArrayCompat<Integer>> list = new ArrayList<SparseArrayCompat<Integer>>();

        Calendar c = Calendar.getInstance();
        c.add(Calendar.YEAR,-1);

        for(int i=0;i<36;i++){
            SparseArrayCompat<Integer> item = new SparseArrayCompat<Integer>();
            item.append(0,c.get(Calendar.YEAR));
            item.append(1,c.get(Calendar.MONTH));
            list.add(item);
            c.add(Calendar.MONTH,+1);
        }
        return list;
    }

    //�I�v�V�������j���[�i���ݒ�j
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //�I�v�V�������j���[�̑I���i���ݒ�j
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