package ume.oite.jp.calendarapp.Dialog;

/**
 * Created by Ume on 2015/07/11.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.EditText;


public class EditTextDialog extends DialogFragment {

    private DialogInterface.OnClickListener okClickListener = null;

    private DialogInterface.OnClickListener cancelClickListener = null;

    private EditText editText;


    public static EditTextDialog newInstance(String title, String message) {
        EditTextDialog fragment = new EditTextDialog();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("message", message);
        fragment.setArguments(args);
        return fragment;
    }

    public Dialog onCreateDialog(Bundle safedInstanceState) {

        String title = getArguments().getString("title");
        String message = getArguments().getString("message");

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(title)
                .setMessage(message)
                .setView(this.editText)
                .setPositiveButton("OK", this.okClickListener)
                .setNegativeButton("Cancel", this.cancelClickListener);

        return builder.create();
    }

    public void setOnOkClickListener(DialogInterface.OnClickListener listener) {
        this.okClickListener = listener;
    }

    public void setOnCancelClickListener(DialogInterface.OnClickListener listener) {
        this.cancelClickListener = listener;
    }

    public void setEditText(EditText editText) {
        this.editText = editText;
    }


}