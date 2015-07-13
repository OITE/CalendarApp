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
 * Calendar_Fragment�N���X<br>
 * <br>
 * �J�����_�[�̊e�Z����\������B<br>
 * Fragment�N���X���p�����Ă���B<br>
 * res/layout/calendar_flagment�̃��C�A�E�g�t�@�C�����g�p�B<br>
 * @author FuyukiUmeta
 ************************************************************/
public class CalendarFragment extends Fragment{

    //�J�����_�[�̃��C�A�E�g�̕ۊ�
    private View calendarLayout = null;
    //���t�擾�̂��߂̃J�����_�[
    private Calendar calendar = Calendar.getInstance();
    //�J�����_�[�Z���Ǘ���Manager
    private CellManager cellManager;
    //���݂̔N��
    public static int year=0,month=0;

    /************************************************************
     * onCreateView���\�b�h<br>
     * �J�����_�[�̊e�Z���̐ݒ���s���B<br>
     ************************************************************/
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        int year = bundle.getInt("year");
        int month = bundle.getInt("month");

        //���C�A�E�g�t�@�C����View�^�ɕϊ�����
        calendarLayout = inflater.inflate(R.layout.fragment_calendar_2,container,false);

        //�J�����_�[���������i���݂̔N�����ɂ���B�j
        //updateCalendar(0);
        //update_setCalendar(year,month);
        makeCalendar(year,month);

        return calendarLayout;
    }

    private void makeCalendar(int year,int month){

        //�J�����_�[�Ɏw��̔N�������
        calendar.set(year, month, 1);

        //�J�����_�[�̍���[�ֈړ�
        calendar.add(Calendar.DATE, -(calendar.get(Calendar.DAY_OF_WEEK) - 1));

        //�e�T��ViewGroup���擾
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
                // week �̒��� day �̒��� dayText �ɃA�N�Z�X
                if(tv!=null)tv.setText(String.valueOf(calendar.get(Calendar.DATE)));
                calendar.add(Calendar.DATE,+1);
            }
        }
    }

}