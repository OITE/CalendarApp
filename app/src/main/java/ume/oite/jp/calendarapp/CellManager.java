package ume.oite.jp.calendarapp;

import java.util.ArrayList;

/**
 * Created by Ume on 2015/07/11.
 */
public class CellManager{

    //Cell�̃��X�g
    private ArrayList<CalendarCell> cellList ;
    //�I���C���f�b�N�X
    private int selectNum = -1;

    /************************************************************
     * �R���X�g���N�^<br>
     * �I���̏�������<br>
     ************************************************************/
    public CellManager(){
        selectNum=-1;
        cellList = new ArrayList<CalendarCell>();
    }

    /************************************************************
     * �Ǘ�����Z���̒ǉ�<br>
     * List�ɃZ������ǉ�����B<br>
     ************************************************************/
    public void add(CalendarCell c){
        cellList.add(c);
    }

    /************************************************************
     * �I���̕ύX<br>
     * �I�𒆂̃Z����ύX����B<br>
     * ����int�͒���List��index���w�肷��B<br>
     * ����CalendarCell��List���̓���Cell��ΏۂƂ���B
     ************************************************************/
    public void changeSelect(int select){
        if(select<cellList.size()){
            if(selectNum!=-1)cellList.get(selectNum).changeDrawable();
            selectNum=select;
        }
        cellList.get(select).changeDrawable();
    }
    public void changeSelect(CalendarCell cell){
        if(selectNum!=-1)cellList.get(selectNum).changeDrawable();
        selectNum=cellList.indexOf(cell);
        cellList.get(selectNum).changeDrawable();
    }

    /************************************************************
     * �I�𒆂̃Z��<br>
     * �I�𒆂̃Z����ԋp����B<br>
     ************************************************************/
    public CalendarCell getSelectCell(){
        if(selectNum==-1)return null;
        return cellList.get(selectNum);
    }

    /************************************************************
     * �Z���ԍ��擾<br>
     * �Z������Z���̔ԍ����擾����B<br>
     ************************************************************/
    public int indexOf(CalendarCell cell){
        return cellList.indexOf(cell);
    }
}