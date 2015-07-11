package ume.oite.jp.calendarapp;

import java.util.ArrayList;

/**
 * Created by Ume on 2015/07/11.
 */
public class CellManager{

    //Cellのリスト
    private ArrayList<CalendarCell> cellList ;
    //選択インデックス
    private int selectNum = -1;

    /************************************************************
     * コンストラクタ<br>
     * 選択の初期化等<br>
     ************************************************************/
    public CellManager(){
        selectNum=-1;
        cellList = new ArrayList<CalendarCell>();
    }

    /************************************************************
     * 管理するセルの追加<br>
     * Listにセルをを追加する。<br>
     ************************************************************/
    public void add(CalendarCell c){
        cellList.add(c);
    }

    /************************************************************
     * 選択の変更<br>
     * 選択中のセルを変更する。<br>
     * 引数intは直接Listのindexを指定する。<br>
     * 引数CalendarCellはList内の同一Cellを対象とする。
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
     * 選択中のセル<br>
     * 選択中のセルを返却する。<br>
     ************************************************************/
    public CalendarCell getSelectCell(){
        if(selectNum==-1)return null;
        return cellList.get(selectNum);
    }

    /************************************************************
     * セル番号取得<br>
     * セルからセルの番号を取得する。<br>
     ************************************************************/
    public int indexOf(CalendarCell cell){
        return cellList.indexOf(cell);
    }
}