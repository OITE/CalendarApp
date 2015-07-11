package ume.oite.jp.calendarapp;

/**
 * Created by Ume on 2015/07/11.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;

/************************************************************
 * EditTextDialogクラス<br>
 * <br>
 * コピペなので未把握<br>
 * @author FuyukiUmeta
 ************************************************************/
public class EditTextDialog extends DialogFragment {

    //OKボタンをクリックした時のリスナ
    private DialogInterface.OnClickListener okClickListener = null;

    //Cancelボタンをクリックした時のリスナ
    private DialogInterface.OnClickListener cancelClickListener = null;

    //EditText型のインスタンス
    private EditText editText;


    /************************************************************
     * newInstanceメソッド<br>
     * newInstanceメソッドでしか新しいオブジェクトを作成できなくしている。<br>
     ************************************************************/
    public static EditTextDialog newInstance(String title, String message) {

        //新たなフラグメントを作成
        EditTextDialog fragment = new EditTextDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("message", message);
        fragment.setArguments(args);

        return fragment;
    }

    /************************************************************
     * onCreateDialogメソッド<br>
     * ダイアログを作成する。<br>
     ************************************************************/
    public Dialog onCreateDialog(Bundle safedInstanceState) {

        //タイトルとメッセージ？
        String title = getArguments().getString("title");
        String message = getArguments().getString("message");

        //アラートダイアログを生成？
        //そこにeditTextやボタンのリスナを設定？
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setView(this.editText)
                .setPositiveButton("OK", this.okClickListener)
                .setNegativeButton("Cancel", this.cancelClickListener);

        return builder.create();
    }

    /************************************************************
     * setOnOkClickListenerメソッド<br>
     * OKボタンをおした時のリスナを返却<br>
     ************************************************************/
    public void setOnOkClickListener(DialogInterface.OnClickListener listener) {
        this.okClickListener = listener;
    }

    /************************************************************
     * setOnCancelClickListenerメソッド<br>
     * Cancelボタンをおした時のリスナを返却<br>
     ************************************************************/
    public void setOnCancelClickListener(DialogInterface.OnClickListener listener) {
        this.cancelClickListener = listener;
    }

    /************************************************************
     * setEditTextメソッド<br>
     * editTextを設定する。<br>
     ************************************************************/
    public void setEditText(EditText editText) {
        this.editText = editText;
    }
}