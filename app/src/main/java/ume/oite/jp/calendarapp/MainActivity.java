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
 * ���̃A�v���͈ȉ��̃T�C�g���Q�l�ɂ��܂����B
 * http://dangoya.jp/?p=302
 *
 * �~���� (�����X�V)
 * �E�@�^�X�N�@�F�@�J�����_�[�̗\��̂��ƁB
 * �E�@�X�P�W���[���@�F�@�^�X�N�̂��ƁB
 * �E�@�t���O�����g�@�F�@�J�����_�[�����ƃ^�X�N�����ɕ����Ă���B
 * �E�@�A�_�v�^�@�F�@����֘A�킩��Ȃ��B
 */


/************************************************************
 * MainActivity�N���X<br>
 * <br>
 * �A�v���̃��C���ƂȂ�A�N�e�B�r�e�B�[�B<br>
 * �������eFragment�𐶐����Ǘ�����B<br>
 * �܂��A�^�X�N�̕ҏW��폜�ƂȂ郁�\�b�h���p�ӂ���Ă���B<br>
 *
 * @author FuyukiUmeta
 ************************************************************/
public class MainActivity extends AppCompatActivity {

    //�^�X�N�̃t���O�����g
    public static TaskFragment listFragment = new TaskFragment();
    //�J�����_�[�̃t���O�����g
    CalendarFragment calendarFragment = new CalendarFragment();

    /************************************************************
     * onCreate���\�b�h<br>
     * �e�t���O�����g��\������B
     ************************************************************/
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //���ړ��{�^���̃C���X�^���X����
        Button nextButton = (Button)this.findViewById(R.id.nextButton);
        Button prevButton = (Button)this.findViewById(R.id.prevButton);

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

        //�t���O�����g��\��������B
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment_container1, calendarFragment, "calendar_fragment");
        ft.add(R.id.fragment_container2, listFragment,"task_fragment");
        ft.commit();
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