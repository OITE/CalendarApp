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
 * EditTextDialog�N���X<br>
 * <br>
 * �R�s�y�Ȃ̂Ŗ��c��<br>
 * @author FuyukiUmeta
 ************************************************************/
public class EditTextDialog extends DialogFragment {

    //OK�{�^�����N���b�N�������̃��X�i
    private DialogInterface.OnClickListener okClickListener = null;

    //Cancel�{�^�����N���b�N�������̃��X�i
    private DialogInterface.OnClickListener cancelClickListener = null;

    //EditText�^�̃C���X�^���X
    private EditText editText;


    /************************************************************
     * newInstance���\�b�h<br>
     * newInstance���\�b�h�ł����V�����I�u�W�F�N�g���쐬�ł��Ȃ����Ă���B<br>
     ************************************************************/
    public static EditTextDialog newInstance(String title, String message) {

        //�V���ȃt���O�����g���쐬
        EditTextDialog fragment = new EditTextDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("message", message);
        fragment.setArguments(args);

        return fragment;
    }

    /************************************************************
     * onCreateDialog���\�b�h<br>
     * �_�C�A���O���쐬����B<br>
     ************************************************************/
    public Dialog onCreateDialog(Bundle safedInstanceState) {

        //�^�C�g���ƃ��b�Z�[�W�H
        String title = getArguments().getString("title");
        String message = getArguments().getString("message");

        //�A���[�g�_�C�A���O�𐶐��H
        //������editText��{�^���̃��X�i��ݒ�H
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setView(this.editText)
                .setPositiveButton("OK", this.okClickListener)
                .setNegativeButton("Cancel", this.cancelClickListener);

        return builder.create();
    }

    /************************************************************
     * setOnOkClickListener���\�b�h<br>
     * OK�{�^�������������̃��X�i��ԋp<br>
     ************************************************************/
    public void setOnOkClickListener(DialogInterface.OnClickListener listener) {
        this.okClickListener = listener;
    }

    /************************************************************
     * setOnCancelClickListener���\�b�h<br>
     * Cancel�{�^�������������̃��X�i��ԋp<br>
     ************************************************************/
    public void setOnCancelClickListener(DialogInterface.OnClickListener listener) {
        this.cancelClickListener = listener;
    }

    /************************************************************
     * setEditText���\�b�h<br>
     * editText��ݒ肷��B<br>
     ************************************************************/
    public void setEditText(EditText editText) {
        this.editText = editText;
    }
}