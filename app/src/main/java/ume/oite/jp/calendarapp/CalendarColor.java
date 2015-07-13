package ume.oite.jp.calendarapp;

/**
 * Created by Ume on 2015/07/11.
 */

import android.graphics.Color;

import java.util.Calendar;

/************************************************************
 * CalendarColor�N���X<br>
 * <br>
 * �J�����_�[�̊e�Z���̐F���`����B<br>
 * �����F�A�g�F�A�g���F����`����Ă���B<br>
 * Singleton�p�^�[����K�p���Ă���B�C���X�^���X�̓v���W�F�N�g�Ɉ�B<br>
 * @author FuyukiUmeta
 ************************************************************/
public class CalendarColor {

    //Singleton�p�^�[��
    private static CalendarColor instance;
    private CalendarColor(){}
    public static CalendarColor getInstance(){
        if(instance==null)instance=new CalendarColor();
        return instance;
    }


    /************************************************************
     * getNumberColor���\�b�h<br>
     * �����F�̒�`<br>
     ************************************************************/
    public int getNumberColor(Calendar calendar){
        switch(calendar.get(Calendar.DAY_OF_WEEK)){
            case Calendar.SUNDAY:return Color.rgb(255, 0, 0);
            case Calendar.SATURDAY:return Color.rgb(0, 0, 255);
            default:return Color.rgb(0, 0, 0);
        }
    }

    /************************************************************
     * getFrameColor���\�b�h<br>
     * �g�F�̒�`<br>
     ************************************************************/
    public int getFrameColor(Calendar calendar){
        switch(calendar.get(Calendar.DAY_OF_WEEK)){
            case Calendar.SUNDAY:return Color.rgb(255,0,0);
            case Calendar.SATURDAY:return Color.rgb(0, 0, 255);
            default: return Color.rgb(0, 0, 0);
        }
    }

    /************************************************************
     * getBodyColor���\�b�h<br>
     * �g���F�̒�`<br>
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
