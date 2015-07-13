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

//calendarcell
public class CalendarCell implements OnTouchListener{

    //�e�L�X�g�r���[
    private TextView cell = null;
    //���t�f�[�^
    private Date calendar = null;
    //�ʏ�E�I�����̘g
    private GradientDrawable normalDrawable=new GradientDrawable();
    private GradientDrawable selectDrawable=new GradientDrawable();
    //�J�����_�[�̐F���
    private CalendarColor color = CalendarColor.getInstance();
    //������Manager��o�^
    private CellManager cellManager = null;
    //�����̂���Activity
    private MainActivity nowActivity = null;
    //���t�̃t�H�[�}�b�g
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.JAPANESE);

    EditScheduleDialog editScheduleDialog = null;
    //DialogFragment newFragment = new TestDialogFragment();

    private int task_num = 0;

    /************************************************************
     * �R���X�g���N�^<br>
     * ���t�����<br>
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
     * �J�����_�[�ݒ�<br>
     * �g�p������t�ɐݒ肳�ꂽ�J�����_�[���擾<br>
     ************************************************************/
    public void setCalendar(final Calendar calendar){
        this.calendar=calendar.getTime();
        editScheduleDialog = new EditScheduleDialog(this.nowActivity);
        setting(calendar);
    }

    /************************************************************
     * �Z���}�l�[�W���ݒ�<br>
     * ������o�^������1�̃Z���}�l�[�W����o�^����B<br>
     ************************************************************/
    public void setCellManager(CellManager cellManager){
        this.cellManager=cellManager;
        cellManager.add(this);
    }

    /************************************************************
     * �e��ݒ�<br>
     * �Z���̊e�ݒ������B<br>
     * Drawable�̊e�ݒ������B<br>
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
     * Drawable�ύX<br>
     * �Z����Drawable��I����Ԃɉ����ĕύX������B<br>
     ************************************************************/
    public void changeDrawable(){
        if(cell.getBackground()==normalDrawable){
            cell.setBackground(selectDrawable);
        }else{
            cell.setBackground(normalDrawable);
        }
    }

    /************************************************************
     * �^�b�`���X�i<br>
     * �^�b�`���ꂽ���̏����B<br>
     ************************************************************/
    public boolean onTouch(View v, MotionEvent event) {
        if(event.getActionMasked()==MotionEvent.ACTION_DOWN){
            if(cellManager.getSelectCell()!=null)if(cellManager.getSelectCell()==this){
                editScheduleDialog.setDate(this.calendar);
                editScheduleDialog.show(nowActivity.getSupportFragmentManager(), "contact_us");
            }
            cellManager.changeSelect(cellManager.indexOf(this));
        }
        return true;
    }

}